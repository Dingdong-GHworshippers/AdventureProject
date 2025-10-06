class AppHeader extends HTMLElement {
    connectedCallback() {
        this.innerHTML = `
      <header>
        <nav>
          <a href="index.html">Forside</a>
          <a href="booking.html">Booking</a>
          <a href="contact.html">Kontakt</a>
          <a href="gallery.html">Galleri</a>

        </nav>
      </header>
    `;
    }
}
customElements.define('app-header', AppHeader);