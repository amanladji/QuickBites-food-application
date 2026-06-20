<%@ page import="java.util.List, com.tap.model.Menu, com.tap.model.Restaurant" %>
<%
Restaurant rest = (Restaurant) request.getAttribute("restaurant");
List<Menu> menuList = (List<Menu>) request.getAttribute("menuList");
String userName = session.getAttribute("user") != null ? ((com.tap.model.User)session.getAttribute("user")).getUserName() : null;
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title><%= rest != null ? rest.getName() : "Menu" %> &mdash; QuickBites</title>
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
      <% if (userName != null) { %><a href="orders">ORDERS</a><a href="logout">LOGOUT</a><% } else { %><a href="login.jsp">SIGN IN</a><% } %>
      <a href="cart">CART</a>
    </nav>
  </div>
</header>

<main>
  <div class="page-header">
    <h1><%= rest != null ? rest.getName() : "Menu" %></h1>
    <p><%= rest != null ? rest.getCuisineType() : "" %></p>
  </div>

  <section class="section section-fog">
    <div class="container" style="max-width:720px;">
      <% if (menuList == null || menuList.isEmpty()) { %>
        <div class="empty-state">
          <h2>No items on the menu yet</h2>
          <p>Something delicious is being prepared. Check back soon.</p>
          <a href="restaurants" class="btn btn-primary mt-20">BACK TO RESTAURANTS</a>
        </div>
      <% } else { %>
        <div class="menu-list">
          <% for (Menu m : menuList) { %>
          <div class="menu-item">
            <div class="menu-item-info">
              <span class="menu-item-name"><%= m.getItemName() %></span>
              <p class="menu-item-desc"><%= m.getDescription() != null ? m.getDescription() : "" %></p>
              <span class="menu-item-price">&#8377;<%= m.getPrice() %></span>
            </div>
            <div>
              <form action="cart" method="post" style="display:flex;align-items:center;gap:8px;">
                <input type="hidden" name="action" value="add">
                <input type="hidden" name="menuId" value="<%= m.getMenuId() %>">
                <input type="hidden" name="restaurantId" value="<%= m.getRestaurantId() %>">
                <button type="button" class="qty-btn" onclick="var q=this.nextElementSibling;if(parseInt(q.value)>1)q.value=parseInt(q.value)-1">&minus;</button>
                <input type="number" name="quantity" value="1" min="1" max="20" style="width:28px;text-align:center;border:none;background:transparent;font:inherit;font-weight:700;color:var(--color-graphite);" readonly>
                <button type="button" class="qty-btn" onclick="this.previousElementSibling.value=parseInt(this.previousElementSibling.value)+1">+</button>
                <button type="submit" class="btn btn-primary btn-sm">ADD</button>
              </form>
            </div>
          </div>
          <% } %>
        </div>
      <% } %>
    </div>
  </section>
</main>

<footer class="site-footer">
  <div class="footer-links">
    <a href="restaurants">RESTAURANTS</a>
    <a href="cart">CART</a>
    <a href="login.jsp">SIGN IN</a>
  </div>
  <p>QuickBites &mdash; fresh food delivered</p>
</footer>
</body>
</html>
