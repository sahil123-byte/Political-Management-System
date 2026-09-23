import "./Footer.css";

function Footer() {
  const currentYear = new Date().getFullYear();

  return (
    <footer className="footer">
      <p>
        © {currentYear} Political Management System | Developed by Sahil
      </p>
    </footer>
  );
}

export default Footer;