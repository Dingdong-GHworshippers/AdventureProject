class AppHeader extends HTMLElement {
    connectedCallback() {
        this.innerHTML = `
      <header>
        <nav>
          <img src="/img/AA.png" alt="Adventure Alley Logo" class="logo">
          <div class="nav-links">
          <a href="index.html">Forside</a>
          <a href="see-activities.html">Booking</a>
          <a href="about-us.html">Kontakt</a>
          <a href="gallery.html">Galleri</a>
          <a href="products.html">Produkter</a>
         </div>
        </nav>
      </header>
    `;
    }
}
customElements.define('app-header', AppHeader);

class AppFooter extends HTMLElement {
    connectedCallback() {
        this.innerHTML = `
      <footer>
        <div class="footerMainDiv">
  <div class="column">
    <a href="AboutUs.html"> Kort om os </a>
    <p>
     Hos Adventure Alley brænder vi for at skabe uforglemmelige oplevelser for både private og virksomheder.
    Vi tilbyder actionfyldte aktiviteter som gokart, paintball, sumobrydning og minigolf – alt sammen designet til at styrke fællesskab, konkurrence og grin.
    Vores mål er at give dig en dag fyldt med energi, sammenhold og sjove udfordringer, uanset om du kommer alene, med familien eller som del af et team.
    </p>
  </div>

  <div id="center" class="column">
    <a href="gallery.html"> Se nogle billeder fra glade familier her! </a>
    <img id="padding-for-volunteer-image" src="/img/gocart.jpg">
  </div>

  <div class="socials-column">
    <div class="socials-column-text-box">
      <h1> Følg os på sociale medier </h1>
    </div>

    <div class="socials-column-row">
      <a href="https://www.facebook.com/" target="_blank">
        <div class="image-container-socials">
          <img src="/img/fb.png">
        </div>
      </a>

      <a href="https://www.instagram.com/" target="_blank">
        <div class="image-container-socials">
          <img src="/img/insta.png">
        </div>
      </a>


      <a href="https://www.tiktok.com/" target="_blank">
        <div class="image-container-socials">
          <img src="/img/tiktok.png">
        </div>
      </a>

    </div>

    <div class="socials-column-row">
      <a href="https://youtube.com/" target="_blank">
        <div class="image-container-socials">
          <img src="/img/youtube.png">
        </div>
      </a>


      <a href="https://x.com/" target="_blank">
        <div class="image-container-socials">
          <img src="/img/x.png">
        </div>
      </a>


      <a href="https://linkedin.com/" target="_blank">
        <div class="image-container-socials">
          <img src="/img/linkedin.png">
        </div>
      </a>
    </div>

    <div class="socials-column-row">
      <a href="https://github.com/itsHarning/Projekt3_Gruppe_3" target="_blank">
        <div class="image-container-socials">
          <img src="/img/github.png">
        </div>
      </a>


      <a href="https://bsky.app/" target="_blank">
        <div class="image-container-socials">
          <img src="/img/bluesky.png">
        </div>
      </a>

      <a href="https://whatsapp.com/" target="_blank">
        <div class="image-container-socials">
          <img src="/img/whatsapp.png">
        </div>
      </a>
    </div>

  </div>

  <div class="last-column">
    <h2> Kontakt os </h2>
    <h1> Adventure XP </h1>
    <h1> Tlf: 69 69 69 69 </h1>
    <h1> E-mail: info@adventurexp.dk </h1>
    <p> Guldbergsgade 29N </p>
    <p> 2200 København N </p>
    <p> CVR: 420 1337 </p>
  </div>
</div>
      </footer>
    `;
    }
}
customElements.define('app-footer', AppFooter);
