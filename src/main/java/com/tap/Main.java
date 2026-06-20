package com.tap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

import com.tap.controller.CartServlet;
import com.tap.controller.CheckoutServlet;
import com.tap.controller.LoginServlet;
import com.tap.controller.LogoutServlet;
import com.tap.controller.MenuServlet;
import com.tap.controller.OrderItemServlet;
import com.tap.controller.OrderServlet;
import com.tap.controller.Register;
import com.tap.controller.RestaurantServlet;
import com.tap.utility.DBConnection;

public class Main {

    public static void main(String[] args) {
        try {
            String webPort = (args.length > 0) ? args[0] : System.getenv("PORT");
            if (webPort == null || webPort.isEmpty()) {
                webPort = "9090";
            }

            String webappDir = locateWebappDir();

            Path baseDir = Files.createTempDirectory("quickbite-tomcat-");
            Tomcat tomcat = new Tomcat();
            tomcat.setBaseDir(baseDir.toFile().getAbsolutePath());
            tomcat.setPort(Integer.parseInt(webPort));
            tomcat.getConnector();

            Context ctx = tomcat.addWebapp("", webappDir);

            addServlet(ctx, "register",   new Register(),         "/register");
            addServlet(ctx, "login",      new LoginServlet(),     "/login");
            addServlet(ctx, "logout",     new LogoutServlet(),    "/logout");
            addServlet(ctx, "restaurants",new RestaurantServlet(),"/restaurants");
            addServlet(ctx, "menu",       new MenuServlet(),      "/menu");
            addServlet(ctx, "cart",       new CartServlet(),      "/cart");
            addServlet(ctx, "checkout",   new CheckoutServlet(),  "/checkout");
            addServlet(ctx, "orders",     new OrderServlet(),     "/orders");
            addServlet(ctx, "orderItems", new OrderItemServlet(), "/orderItems");

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                DBConnection.close();
                try { Files.walk(baseDir).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete); } catch (Exception ignored) {}
            }));

            System.out.println("========================================");
            System.out.println("  QuickBite starting on port " + webPort);
            System.out.println("  Webapp dir: " + webappDir);
            System.out.println("========================================");

            tomcat.start();

            System.out.print("Connecting to MongoDB... ");
            DBConnection.getDatabase();
            System.out.println("connected.");

            System.out.println("SERVER_READY — http://localhost:" + webPort + "/");
            tomcat.getServer().await();
            Thread.currentThread().join();
        } catch (Exception e) {
            try {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                String trace = sw.toString();
                System.err.println("FATAL: " + trace);
                Files.writeString(Path.of(System.getProperty("java.io.tmpdir"), "quickbite_error.log"), trace);
            } catch (Exception ignored) {}
            try { Thread.currentThread().join(); } catch (InterruptedException ignored) {}
        }
    }

    private static void addServlet(Context ctx, String name, jakarta.servlet.http.HttpServlet servlet, String mapping) {
        Tomcat.addServlet(ctx, name, servlet);
        ctx.addServletMappingDecoded(mapping, name);
    }

    private static String locateWebappDir() throws Exception {
        URL jarUrl = Main.class.getProtectionDomain().getCodeSource().getLocation();
        File jarFile = new File(jarUrl.toURI());

        if (jarFile.isDirectory()) {
            File devDir = new File(jarFile, "webapp");
            if (devDir.exists()) return devDir.getAbsolutePath();
            devDir = new File("src/main/webapp");
            if (devDir.exists()) return devDir.getAbsolutePath();
            return "src/main/webapp";
        }

        Path tempDir = Files.createTempDirectory("quickbite-webapp-");
        try (JarFile jar = new JarFile(jarFile)) {
            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String name = entry.getName();
                if (!name.startsWith("webapp/")) continue;
                String relative = name.substring("webapp/".length());
                if (relative.isEmpty()) continue;
                File target = new File(tempDir.toFile(), relative);
                if (entry.isDirectory()) {
                    target.mkdirs();
                } else {
                    target.getParentFile().mkdirs();
                    try (InputStream in = jar.getInputStream(entry);
                         OutputStream out = new FileOutputStream(target)) {
                        byte[] buf = new byte[8192];
                        int len;
                        while ((len = in.read(buf)) > 0) {
                            out.write(buf, 0, len);
                        }
                    }
                }
            }
        }
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                Files.walk(tempDir)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
            } catch (Exception ignored) {}
        }));
        return tempDir.toFile().getAbsolutePath();
    }
}
