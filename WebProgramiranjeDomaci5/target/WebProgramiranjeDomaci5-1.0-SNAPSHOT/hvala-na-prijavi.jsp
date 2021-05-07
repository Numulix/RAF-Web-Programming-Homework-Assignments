<html>
<head>
    <title>Title</title>
    <%@include file="styles.jsp"%>
    <link rel="stylesheet" href="<%= application.getContextPath() %>/style.css">
</head>
<body>
<%@include file="navbar.jsp"%>

<div class="container">
    <%
        if (request.getSession().getAttribute("imeTima") != null) {
    %>
    <div class="row col-6 mx-auto">
        <h1 class="text-center"><%= request.getSession().getAttribute("imeTima") %>, hvala na prijavi!</h1>
        <h2 class="text-center">Nas tim pregleda prijavu i obavestice vas o konacnoj odluci do 11. decembra na email: mail@mail.com</h2>
        <h3 class="text-center">Za sva dodatna pitanja mozete pisati na: mail@raf.rs</h3>
    </div>
    <%
        }
    %>
</div>
</body>
</html>
