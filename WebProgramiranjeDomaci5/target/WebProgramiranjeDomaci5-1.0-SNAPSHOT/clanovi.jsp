<html>
<head>
    <title>Prijava tima</title>
    <%@include file="styles.jsp"%>
    <link rel="stylesheet" href="<%= application.getContextPath() %>/style.css">
</head>

<body>
<%@include file="navbar.jsp"%>
<%! int num = 3; %>

<div class="container">
    <div class="row">
        <%
            if (request.getSession().getAttribute("prijavljen") != null &&
                    request.getSession().getAttribute("prijavljen").equals("DA")) {
        %>

        <h1 class="col-6 mx-auto">Vas tim, <%= request.getSession().getAttribute("imeTima") %>, je vec prijavljen.</h1>

        <%
            } else {
        %>

        <form action="/proveri-prijavu" method="post">

            <%
                if (request.getSession().getAttribute("brojClanova") == null) {
            %>

            <h1 class="col-6 mx-auto">Neuspesno prosledjen parametar</h1>

            <%
                } else {
            %>

            <% num = (int)request.getSession().getAttribute("brojClanova");
                for (int i = 0; i < num; i++) {
            %>

            <h1 class="mb-3 col-6 mx-auto">Haker <%= i+1 %></h1>
            <div class="form-group mb-3 col-6 mx-auto">
                <input class="form-control" required type="text" placeholder="Ime" id="clan-ime-<%= i+1 %>" name="clan-ime-<%= i+1 %>">
            </div>
            <div class="form-group mb-3 col-6 mx-auto">
                <input class="form-control" required type="text" placeholder="Prezime" name="clan-prezime-<%= i+1 %>">
            </div>
            <div class="form-group mb-3 col-6 mx-auto">
                <input class="form-control" required type="email" placeholder="Email" name="clan-email-<%= i+1 %>" >
            </div>
            <div class="form-group mb-3 col-6 mx-auto">
                <input class="form-control" required type="text" placeholder="Uloga u timu (npr. developer, dizajner...)" name="clan-uloga-<%= i+1 %>" >
            </div>
            <div class="form-group mb-3 col-6 mx-auto">
                <input class="form-control" required type="url" placeholder="LinkedIn" name="clan-linkedin-<%= i+1 %>" >
            </div>

            <%
                }
            %>
            <div class="form-group mb-3 col-6 mx-auto">
                <input type="submit" class="btn btn-primary btn-lg" value="Posalji prijavu">
            </div>
        </form>

        <%
                }
            }
        %>
    </div>
</div>

</body>
</html>