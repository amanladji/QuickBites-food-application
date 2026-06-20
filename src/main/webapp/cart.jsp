<%@ page import="java.util.List, com.tap.model.CartItem, com.tap.model.User" %>
<%
List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
Double cartTotal = (Double) request.getAttribute("cartTotal");
if (cartTotal == null) cartTotal = 0.0;
if (cart == null) cart = new java.util.ArrayList<>();
User user = (User) session.getAttribute("user");
String userName = user != null ? user.getUserName() : null;
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Cart &mdash; QuickBites</title>
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
    </nav>
  </div>
</header>

<main>
  <div class="page-header">
    <h1>Your Cart</h1>
    <p>Review your selections before ordering.</p>
  </div>

  <section class="section section-fog">
    <div class="container" style="max-width:640px;">
      <% if (cart.isEmpty()) { %>
        <div class="empty-state">
          <h2>Your cart is empty</h2>
          <p>Add some items from our menu and come back here.</p>
          <a href="restaurants" class="btn btn-primary mt-20">VIEW MENU</a>
        </div>
      <% } else { %>
        <div class="cart-card">
          <% for (CartItem item : cart) { %>
          <div class="cart-item">
            <div class="cart-item-info">
              <span class="cart-item-name"><%= item.getItemName() %></span>
              <span class="cart-item-meta">Qty: <%= item.getQuantity() %> &times; &#8377;<%= String.format("%.0f", item.getPrice()) %></span>
            </div>
            <div style="display:flex;align-items:center;gap:12px;">
              <span class="cart-item-total">&#8377;<%= String.format("%.0f", item.getItemTotal()) %></span>
              <form action="cart" method="post" style="display:inline;">
                <input type="hidden" name="action" value="remove">
                <input type="hidden" name="menuId" value="<%= item.getMenuId() %>">
                <button type="submit" class="btn btn-sm" style="background:transparent;border:2px solid var(--color-fog);color:var(--color-graphite);border-radius:26px;padding:6px 12px;cursor:pointer;">&#10005;</button>
              </form>
            </div>
          </div>
          <% } %>
        </div>

        <div class="cart-summary" style="max-width:640px;margin:20px auto 0;padding:0 var(--card-padding);">
          <span class="cart-summary-label">Total</span>
          <span class="cart-summary-value">&#8377;<%= String.format("%.0f", cartTotal) %></span>
        </div>

        <div style="display:flex;gap:12px;margin-top:24px;justify-content:flex-end;max-width:640px;margin-left:auto;margin-right:auto;">
          <a href="restaurants" class="btn btn-outline btn-sm">CONTINUE ORDERING</a>
          <a href="checkout" class="btn btn-primary">CHECKOUT</a>
        </div>
      <% } %>
    </div>
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
