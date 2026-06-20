<%@ page import="com.tap.model.User" %>
<%
String message = (String) request.getAttribute("message");
String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Register &mdash; QuickBites</title>
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=DM+Sans:wght@400;500;600;700&family=Nunito+Sans:wght@400;500;600;700&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="./style.css">
</head>
<body>

<header class="site-header">
  <div class="header-inner">
    <a href="/" class="header-logo">
      <span class="header-logo-icon"><svg viewBox="0 0 24 24"><rect x="3" y="10" width="4" height="12" rx="1"/><rect x="10" y="4" width="4" height="18" rx="1"/><rect x="17" y="7" width="4" height="15" rx="1"/></svg></span>
      <span class="header-logo-text">QuickBites</span>
    </a>
    <nav class="header-nav">
      <a href="/">HOME</a>
      <a href="restaurants">RESTAURANTS</a>
      <a href="login.jsp">SIGN IN</a>
    </nav>
  </div>
</header>

<main>
  <div class="page-header">
    <h1>Create Account</h1>
    <p>Join QuickBites and start ordering.</p>
  </div>

  <section class="section section-fog" style="padding:60px 24px;">
    <form class="form-card" action="register" method="post">
      <h1>Register</h1>
      <% if (error != null) { %><p class="form-error"><%= error %></p><% } %>
      <% if (message != null) { %><p class="form-success"><%= message %></p><% } %>

      <div class="form-group">
        <label class="form-label" for="userName">Name</label>
        <input class="form-input" type="text" id="userName" name="userName" placeholder="Your name" required>
      </div>

      <div class="form-group">
        <label class="form-label" for="email">Email</label>
        <input class="form-input" type="email" id="email" name="email" placeholder="you@example.com" required>
      </div>

      <div class="form-group">
        <label class="form-label" for="password">Password</label>
        <input class="form-input" type="password" id="password" name="password" placeholder="Enter your password" required>
      </div>

      <div class="form-group">
        <label class="form-label" for="address">Address</label>
        <textarea class="form-input" id="address" name="address" placeholder="Your delivery address" rows="3"></textarea>
      </div>

      <input type="hidden" name="role" value="customer">

      <button type="submit" class="btn btn-primary" style="width:100%;justify-content:center;">REGISTER</button>

      <p class="form-footer">Already have an account? <a href="login.jsp">Sign in here</a></p>
    </form>
  </section>
</main>

<footer class="site-footer">
  <div class="footer-links">
    <a href="restaurants">RESTAURANTS</a>
    <a href="login.jsp">SIGN IN</a>
  </div>
  <p>QuickBites &mdash; fresh food delivered</p>
</footer>
</body>
</html>
