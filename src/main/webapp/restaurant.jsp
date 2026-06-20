<%@ page import="java.util.List, com.tap.model.Restaurant" %>
<%
List<Restaurant> list = (List<Restaurant>) request.getAttribute("restaurantList");
String userName = session.getAttribute("user") != null ? ((com.tap.model.User)session.getAttribute("user")).getUserName() : null;
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Restaurants &mdash; QuickBites</title>
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
      <a href="restaurants" class="active">RESTAURANTS</a>
      <% if (userName != null) { %><a href="orders">ORDERS</a><a href="logout">LOGOUT</a><% } else { %><a href="login.jsp">SIGN IN</a><% } %>
      <a href="cart">CART</a>
    </nav>
    <a href="restaurants" class="btn-header">Get Started <svg viewBox="0 0 24 24"><line x1="5" y1="12" x2="19" y2="12"/><polyline points="12 5 19 12 12 19"/></svg></a>
  </div>
</header>

<main>
  <div class="page-header">
    <h1>Restaurants</h1>
    <p>Choose your craving &mdash; we'll handle the rest.</p>
  </div>

  <section class="section section-fog">
    <div class="container">
      <% if (list == null || list.isEmpty()) { %>
        <div class="empty-state">
          <h2>No restaurants available</h2>
          <p>New spots are being added &mdash; check back soon.</p>
        </div>
      <% } else { %>
        <div class="restaurant-grid">
          <% for (Restaurant r : list) { %>
          <a href="menu?restaurantId=<%= r.getRestaurantId() %>" class="restaurant-card">
            <img class="restaurant-card-image" src="https://images.unsplash.com/photo-1504674900247-0877df9cc836?w=400&h=300&fit=crop" alt="<%= r.getName() %>" width="400" height="300" loading="lazy">
            <div class="restaurant-card-body">
              <span class="restaurant-card-name"><%= r.getName() %></span>
              <span class="restaurant-card-cuisine"><%= r.getCuisineType() %></span>
              <span class="restaurant-card-meta">&#9733; <%= r.getRating() %> &middot; <%= r.getDeliveryTime() %> mins &middot; min &#8377;100</span>
            </div>
          </a>
          <% } %>
        </div>
      <% } %>
    </div>
  </section>
</main>

<footer class="site-footer">
  <div class="footer-links">
    <a href="restaurants">RESTAURANTS</a>
    <a href="login.jsp">SIGN IN</a>
    <a href="register.jsp">REGISTER</a>
    <a href="cart">CART</a>
  </div>
  <p>QuickBites &mdash; fresh food delivered</p>
</footer>
</body>
</html>
