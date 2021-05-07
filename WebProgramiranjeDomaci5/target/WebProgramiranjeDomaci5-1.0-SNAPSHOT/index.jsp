<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>RAF Hackathon</title>
    <%@include file="styles.jsp"%>
    <link rel="stylesheet" href="<%= application.getContextPath() %>/style.css">
</head>
<body>
<%@include file="navbar.jsp"%>

<div class="container">
    <div class="row">
        <div class="col-12">
            <div class="swiper-container hero-slider text-center">
                <div class="swiper-wrapper d-flex justify-content-center">
                    <div class=" slide-content d-flex align-items-center">
                        <div class="single-slide">
                            <h1>Da li možeš da osvojiš RAF?</h1>
                            <h2>RAF Hakaton, 21. Decembar.</h2>

                            <%
                                if (request.getSession().getAttribute("prijavljen") != null &&
                                        request.getSession().getAttribute("prijavljen").equals("DA")) {
                            %>

                            <h2>Vas tim, <%= request.getSession().getAttribute("imeTima") %>, je vec prijavljen.</h2>

                            <%
                                } else {
                            %>

                            <form action="prijava.jsp">
                                <input type="submit" class="btn btn-primary btn-lg mb-5" value="Prijavi se!">
                            </form>

                            <%
                                }
                            %>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>

    <div class="row">
        <div class="col">
            <div class="card text-white bg-primary mb-3" style="max-width: 18rem;">
                <div class="card-header">Vreme</div>
                <div class="card-body">
                    <p class="card-text">Takmičenje će početi u subotu, 21. decembra, o tačnoj satnici takmičari će biti naknadno obavešteni. Hakaton traje 24 sata od trenutka prezentovanja teme. </p>
                </div>
            </div>
        </div>

        <div class="col">
            <div class="card text-white bg-primary mb-3" style="max-width: 18rem;">
                <div class="card-header">Mesto</div>
                <div class="card-body">
                    <p class="card-text">Hakaton će se održati na Računarskom fakultetetu. Fakultet se nalazi u samom centru Beograda, u Knez Mihailovoj 6/VI. Svakom timu će biti dodeljena posebna učionica za rad. </p>
                </div>
            </div>
        </div>

        <div class="col">
            <div class="card text-white bg-primary mb-3" style="max-width: 18rem;">
                <div class="card-header">Tema</div>
                <div class="card-body">
                    <p class="card-text">Tema će biti poznata na dan takmičenja. Tada će timovi dobiti sve neophodne informacije i biće upoznati sa zadatkom. Tačno 12 sati nakon početka, svim takmičarima biće predstavljen još jedan zahtev koji bi trebalo da ispune. </p>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col">
            <div class="card text-white bg-primary mb-3" style="max-width: 18rem;">
                <div class="card-header">Učešće</div>
                <div class="card-body">
                    <p class="card-text">Takmičenje je otvorenog tipa, a pravo na učešće imaju svi od 18 do 27 godina. Na hakatonu će učestvovati najboljih 8 timova sa 3 do 4 člana. </p>
                </div>
            </div>
        </div>

        <div class="col">
            <div class="card text-white bg-primary mb-3" style="max-width: 18rem;">
                <div class="card-header">Ocenjivanje</div>
                <div class="card-body">
                    <p class="card-text">Rešenja će biti ocenjena od strane stručnog žirija u više različitih kategorija. Sa tačnim načinom ocenjivanja takmičari će biti upoznati pred početak takmičenja. Žiri će, u toku trajanja takmičenja, nadgledati radove takmičara i po završetku Hakatona doneti konačnu odluku. </p>
                </div>
            </div>
        </div>

        <div class="col">
            <div class="card text-white bg-primary mb-3" style="max-width: 18rem;">
                <div class="card-header">Resursi</div>
                <div class="card-body">
                    <p class="card-text">U toku trajanja hakatona, takmičarima će biti obezbeđena hrana, piće, internet i prostor za zabavu i odmor. Takmičari su u obavezi da donesu laptopove na kojima će raditi, dok će im dodatni resursi kao što su monitori, tastature, miševi, papiri itd. biti obezebeđeni. </p>
                </div>
            </div>
        </div>
    </div>

    <section class="pricing-table" id="nagrade">
        <div class="container">
            <div class="title text-center">
                <h6>Osvoji</h6>
                <h1 class="title-blue">Nagrade</h1>
            </div>
            <div class="row no-gutters">
                <div class="col-md-4 order-2 order-md-1 reward-custom-padding">
                    <div class="single-pricing text-center second-place" data-aos="fade-up" data-aos-delay="300"
                         data-aos-duration="600">
                        <span>2.</span>
                        <h2 class="mb-3">Drugo mesto</h2>
                        <!-- <p class="desc">Here goes some description</p> -->
                        <img src="https://hakaton.raf.edu.rs/assets/images/crypto/ethereum_cricle.png" alt="Ethereum" class="img-fluid crypto-img">
                        <p class="price mt-3" style="line-height:0.8em;">50 000</p>
                        <p class="currency " style="line-height:0.8em;">dinara</p>
                        <p style="font-size:1.3em;" class="mt-5">U Ethereum kriptovaluti</p>
                    </div>
                </div>
                <div class="col-md-4 order-1 order-md-2 reward-custom-padding">
                    <div class="single-pricing text-center first-place" data-aos="fade-up" data-aos-delay="0"
                         data-aos-duration="600">
                        <span>1.</span>
                        <h2 class="mb-3">Prvo mesto</h2>
                        <img src="https://hakaton.raf.edu.rs/assets/images/crypto/bitcoin_circle.png" alt="Bitcoin" class="img-fluid crypto-img">
                        <p class="price mt-3" style="line-height:0.8em;">100 000</p>
                        <p class="currency " style="line-height:0.8em;">dinara</p>
                        <p style="font-size:1.3em;" class="mt-5">U Bitcoin kriptovaluti</p>
                    </div>
                </div>
                <div class="col-md-4 order-3 order-md-3 reward-custom-padding">
                    <div class="single-pricing text-center third-place" data-aos="fade-up" data-aos-delay="600"
                         data-aos-duration="600">
                        <span>3.</span>
                        <h2 class="mb-3">Treće mesto</h2>
                        <img src="https://hakaton.raf.edu.rs/assets/images/crypto/ethereum_cricle.png" alt="Ethereum" class="img-fluid crypto-img">
                        <p class="price mt-3" style="line-height:0.8em;">25 000</p>
                        <p class="currency " style="line-height:0.8em;">dinara</p>
                        <p style="font-size:1.3em;" class="mt-5">U Ethereum kriptovaluti</p>
                    </div>
                </div>

            </div>
            <div class="col-12 mt-5 text-center" style="font-size:1.3em;">
                <p>
                    Vrednost se obračunava po kursu na dan dodele nagrada.
                </p>
            </div>
        </div>
    </section>
</div>

</body>
</html>