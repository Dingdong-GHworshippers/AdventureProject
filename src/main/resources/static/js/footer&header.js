class AppHeader extends HTMLElement {
    connectedCallback() {
        this.innerHTML = `
      <header>
        <nav>
          <a href="booking.html">Home</a>
          <a href="about.html">About</a>
          <a href="contact.html">Contact</a>
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
        <p>&copy; 2025 Wishify ApS — All rights reserved</p>
      </footer>
    `;
    }
}
customElements.define('app-footer', AppFooter);
