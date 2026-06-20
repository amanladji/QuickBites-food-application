<%@ page import="java.util.List, com.tap.model.CartItem, com.tap.model.User" %>
<%
List<CartItem> cart = (List<CartItem>) request.getAttribute("cart");
Double cartTotal = (Double) request.getAttribute("cartTotal");
Integer restaurantId = (Integer) request.getAttribute("restaurantId");
if (cartTotal == null) cartTotal = 0.0;
User user = (User) session.getAttribute("user");
String userName = user != null ? user.getUserName() : null;
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Checkout &mdash; QuickBites</title>
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
      <a href="orders">ORDERS</a>
    </nav>
  </div>
</header>

<main>
  <div class="page-header">
    <h1>Checkout</h1>
    <p>Almost there &mdash; confirm your order.</p>
  </div>

  <section class="section section-fog">
    <div class="container" style="max-width:640px;">
      <% if (cart == null || cart.isEmpty()) { %>
        <div class="empty-state">
          <h2>Your cart is empty</h2>
          <p>Add items before checking out.</p>
          <a href="restaurants" class="btn btn-primary mt-20">VIEW MENU</a>
        </div>
      <% } else { %>
        <div class="order-summary">
          <h2>Order Summary</h2>
          <% for (CartItem item : cart) { %>
          <div class="detail-row">
            <span><%= item.getItemName() %> &times; <%= item.getQuantity() %></span>
            <span>&#8377;<%= String.format("%.0f", item.getItemTotal()) %></span>
          </div>
          <% } %>
          <div class="detail-row">
            <span>Total</span>
            <span>&#8377;<%= String.format("%.0f", cartTotal) %></span>
          </div>
        </div>

        <form action="checkout" method="post" class="form-card" style="margin-top:30px;">
          <h1 style="margin-bottom:0;">Payment</h1>

          <div class="form-group">
            <label class="form-label" for="paymentMethod">Method</label>
            <select class="form-input" id="paymentMethod" name="paymentMethod" required>
              <option value="COD">Cash on Delivery</option>
              <option value="Card">Credit / Debit Card</option>
              <option value="UPI">UPI</option>
            </select>
          </div>

          <button type="submit" class="btn btn-primary" style="width:100%;justify-content:center;">PLACE ORDER</button>
        </form>
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
