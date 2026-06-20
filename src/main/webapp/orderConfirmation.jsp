<%@ page import="com.tap.model.Order, com.tap.model.User" %>
<%
Order order = (Order) request.getAttribute("order");
User user = (User) session.getAttribute("user");
String userName = user != null ? user.getUserName() : null;
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Order Confirmed &mdash; QuickBites</title>
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
      <% if (userName != null) { %><a href="orders">ORDERS</a><% } %>
    </nav>
  </div>
</header>

<main>
  <section class="section section-fog" style="padding:80px 24px;">
    <div class="confirmation-card">
      <span class="check-icon"><svg viewBox="0 0 24 24"><polyline points="20 6 9 17 4 12"/></svg></span>
      <h1>Order Confirmed!</h1>
      <p>Your order has been placed successfully.</p>
      <div class="order-id">#<%= order != null ? order.getOrderId() : "&mdash;" %></div>
      <p>We'll start preparing it right away. Track your order in the Orders section.</p>
      <div style="display:flex;gap:12px;justify-content:center;flex-wrap:wrap;">
        <a href="restaurants" class="btn btn-primary">ORDER MORE</a>
        <a href="orders" class="btn btn-outline">VIEW ORDERS</a>
      </div>
    </div>
  </section>
</main>

<footer class="site-footer">
  <div class="footer-links">
    <a href="restaurants">RESTAURANTS</a>
    <a href="orders">ORDERS</a>
  </div>
  <p>QuickBites &mdash; fresh food delivered</p>
</footer>
</body>
</html>
