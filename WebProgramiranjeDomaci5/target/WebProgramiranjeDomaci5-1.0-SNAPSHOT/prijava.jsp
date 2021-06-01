<html>
<head>
    <title>Prijava tima</title>
    <%@include file="styles.jsp"%>
    <link rel="stylesheet" href="<%= application.getContextPath() %>/style.css">
</head>
<body>
    <%@include file="navbar.jsp"%>

    <%
        if (request.getSession().getAttribute("prijavljen") != null &&
                request.getSession().getAttribute("prijavljen").equals("DA")) {
    %>

    <h1 class="col-6 mx-auto text-center">Vas tim, <%= request.getSession().getAttribute("imeTima") %>, je vec prijavljen.</h1>

    <%
    } else {
    %>

    <div class="container h-100">
        <div class="row h-50 justify-content-center align-items-center">
            <form action="/proveri-tim" method="post">
                <div class="form-group mt-3 mb-3 col-6 mx-auto">
                    <label for="imeTima">Ime tima:</label>
                    <input type="text" id="imeTima" class="form-control" name="imeTima" placeholder="Ime tima" required>
                </div>
                <div class="form-group mb-3 col-6 mx-auto">
                    <label for="kontaktMejl">Kontakt mejl:</label>
                    <input type="email" id="kontaktMejl" class="form-control" name="kontaktMejl" placeholder="Kontakt mejl" required>
                </div>
                <div class="form-group mb-3 col-6 mx-auto">
                    <label for="kontaktTelefon">Kontakt telefon:</label>
                    <input type="tel" id="kontaktTelefon" class="form-control" name="kontaktTelefon" placeholder="Kontakt telefon" required>
                </div>
                <div class="form-group mb-3 col-6 mx-auto">
                    <label for="motoTima">Moto tima:</label>
                    <input type="tel" id="motoTima" class="form-control" name="motoTima" placeholder="Moto tima" required>
                </div>
                <div class="form-group mb-3 col-6 mx-auto">
                    <label for="saznanjeZaHakaton">Kako ste culi za hakaton?</label>
                    <select id="saznanjeZaHakaton" name="saznanjeZaHakaton" class="form-select" required>
                        <option name="Facebook">Facebook</option>
                        <option name="Instagram">Instagram</option>
                        <option name="Fakultet">Preko fakulteta</option>
                        <option name="Drugo">Drugo</option>
                    </select>
                </div>
                <div class="form-group mb-3 col-6 mx-auto">
                    <label for="brojClanova">Broj clanova tima</label>
                    <select id="brojClanova" name="brojClanova" class="form-select">
                        <option name="3">3</option>
                        <option name="4">4</option>
                    </select>
                </div>
                <div class="form-group mb-3 col-6 mx-auto">
                    <input type="submit" class="btn btn-primary btn-lg" value="Clanovi">
                </div>
            </form>
        </div>
    </div>

    <%
        }
    %>

</body>
</html>
