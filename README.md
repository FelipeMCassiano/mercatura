# Mercatura

Welcome to **Mercatura** – a robust, modern, and developer-friendly E-Commerce API built with Java.

---

## 🚀 Features

- **Modern Java Backend:** Built with Java 21 for performance, reliability, and scalability.
- **Container-Ready:** Seamless Docker and Docker Compose support for hassle-free development, testing, and deployment.
- **Easy Database Setup:** Includes an `init.sql` script for automatic database schema and demo data initialization.
- **Environment Management:** Sample `.env-example` for straightforward environment configuration.
- **Maven Powered:** Dependency management and builds via Maven (`pom.xml`, `mvnw` scripts included).
- **Secure Payments:** Integrated Stripe payment gateway for real-world commerce scenarios.

---

## 🏁 Getting Started

### Prerequisites

- **Java 21**
- **Maven 3.8+**
- **Docker** & **Docker Compose**

### Quick Start

1. **Clone the repository**
    ```sh
    git clone https://github.com/FelipeMCassiano/mercatura.git
    cd mercatura/Mercatura
    ```

2. **Set up environment variables**
    ```sh
    cp .env-example .env
    # Edit .env with your preferred configuration
    ```

3. **Start the application using Docker Compose**
    ```sh
    docker-compose up --build
    ```

---

## 📝 Project Structure & Scripts

- **`init.sql`** — Initializes database schema and seed data.
- **`docker-compose.yml`** — Orchestrates containers for the API and database.
- **`Dockerfile`** — Container image build instructions.
- **`.env-example`** — Example environment variable file for configuration reference.

---

## 🤝 Contributing

Contributions and feedback are welcome!  
If you’d like to make major changes, please [open an issue](https://github.com/FelipeMCassiano/mercatura/issues) first to discuss your ideas.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -am 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a pull request

---

## 📄 License

This project is licensed under the [MIT License](LICENSE).

---

## 📚 Resources

- [E-Commerce API project requirements](https://roadmap.sh/projects/ecommerce-api)

---
# mercatura
