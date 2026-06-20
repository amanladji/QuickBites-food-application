<%@ page import="java.util.List, com.tap.model.OrderItem, com.tap.model.User" %>
<%
List<OrderItem> orderItemList = (List<OrderItem>) request.getAttribute("orderItemList");
User user = (User) session.getAttribute("user");
String userName = user != null ? user.getUserName() : null;
String orderIdParam = request.getParameter("orderId");
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Order Items &mdash; QuickBites</title>
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
      <a href="orders">ORDERS</a>
      <a href="logout">LOGOUT</a>
    </nav>
  </div>
</header>

<main>
  <div class="page-header">
    <h1>Order #<%= orderIdParam != null ? orderIdParam : "&mdash;" %></h1>
    <p>Items in this order.</p>
  </div>

  <section class="section section-fog">
    <div class="container" style="max-width:640px;">
      <% if (orderItemList == null || orderItemList.isEmpty()) { %>
        <div class="empty-state">
          <h2>No items found</h2>
          <p>This order doesn't have any items.</p>
          <a href="orders" class="btn btn-primary mt-20">BACK TO ORDERS</a>
        </div>
      <% } else { %>
        <div class="cart-card">
          <% for (OrderItem oi : orderItemList) { %>
          <div class="cart-item">
            <div class="cart-item-info">
              <span class="cart-item-name"><%= oi.getItemName() != null ? oi.getItemName() : "Item #" + oi.getMenuId() %></span>
              <span class="cart-item-meta">Qty: <%= oi.getQuantity() %></span>
            </div>
            <span class="cart-item-total">&#8377;<%= String.format("%.0f", oi.getItemTotal()) %></span>
          </div>
          <% } %>
        </div>
        <div class="text-center mt-40">
          <a href="orders" class="btn btn-primary">BACK TO ORDERS</a>
        </div>
      <% } %>
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
