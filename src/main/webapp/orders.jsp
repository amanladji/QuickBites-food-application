<%@ page import="java.util.List, com.tap.model.Order, com.tap.model.User" %>
<%
List<Order> orderList = (List<Order>) request.getAttribute("orderList");
User user = (User) session.getAttribute("user");
String userName = user != null ? user.getUserName() : null;
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>My Orders &mdash; QuickBites</title>
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
      <a href="cart">CART</a>
      <a href="logout">LOGOUT</a>
    </nav>
  </div>
</header>

<main>
  <div class="page-header">
    <h1>My Orders</h1>
    <p>Everything you've ordered &mdash; at a glance.</p>
  </div>

  <section class="section section-fog">
    <div class="container" style="max-width:720px;">
      <% if (orderList == null || orderList.isEmpty()) { %>
        <div class="empty-state">
          <h2>No orders yet</h2>
          <p>Place your first order and it'll appear here.</p>
          <a href="restaurants" class="btn btn-primary mt-20">VIEW MENU</a>
        </div>
      <% } else { %>
        <div class="table-wrap">
          <table class="data-table">
            <thead>
              <tr>
                <th>Order</th>
                <th>Date</th>
                <th>Total</th>
                <th>Status</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              <% for (Order o : orderList) { %>
              <tr>
                <td><strong>#<%= o.getOrderId() %></strong></td>
                <td><%= o.getOrderDate() != null ? o.getOrderDate().toString().substring(0,10) : "&mdash;" %></td>
                <td>&#8377;<%= String.format("%.0f", o.getTotalAmount()) %></td>
                <td><span class="status-badge"><%= o.getStatus() %></span></td>
                <td><a href="orderItems?orderId=<%= o.getOrderId() %>" class="btn btn-sm btn-outline">VIEW</a></td>
              </tr>
              <% } %>
            </tbody>
          </table>
        </div>
      <% } %>
    </div>
  </section>
</main>

<footer class="site-footer">
  <div class="footer-links">
    <a href="restaurants">RESTAURANTS</a>
    <a href="cart">CART</a>
  </div>
  <p>QuickBites &mdash; fresh food delivered</p>
</footer>
</body>
</html>
