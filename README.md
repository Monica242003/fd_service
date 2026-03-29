# Fixed Deposit (FD) Microservice

This is a Spring Boot microservice for managing Fixed Deposit Products and Customer FD Accounts.

## Technologies Used
- **Spring Boot 3.2.x**
- **Java 17**
- **Spring Data JPA**
- **SQLite** (Primary Database)
- **H2 Console** (For database inspection)
- **Swagger / OpenAPI 3** (API Documentation)

## Features Built
1. **CRUD for FD Products**: Create, read, update, delete FD product definitions.
2. **Open FD Account**: Validates the principal amount and tenor against the selected product bounds.
3. **List/Search FD Accounts**: Filter by customerId or status.
4. **Accrual Action**: Accrues interest based on the elapsed months and compounding type.
5. **Close Account**: Premature closure handling with a 1% penalty on principal and final payout calculation.

## Steps to Run in Eclipse

1. **Import the Project**:
   - Open Eclipse IDE.
   - Go to `File` > `Import`.
   - Select `Maven` > `Existing Maven Projects` and click `Next`.
   - Browse to the directory containing this project (`fd-service`) and select it.
   - Ensure the `pom.xml` is checked and click `Finish`.

2. **Run the Application**:
   - Locate the `FdMicroserviceApplication.java` file in `src/main/java/com/bank/fd/`.
   - Right-click on the file -> `Run As` -> `Java Application` (or `Spring Boot App` if you have Spring Tools installed).
   - The application will start on port `8080`.

3. **Access the Application**:
   - **Swagger UI**: `http://localhost:8080/swagger-ui.html`
   - **H2 Console**: `http://localhost:8080/h2-console`
     - *JDBC URL*: `jdbc:sqlite:fd_service.db` (The DB file will be created in the project root directory).
     - *Username/Password*: Leave default or blank as SQLite doesn't strictly require it here.

## API Endpoints

### 1. Products (`/api/products`)
- `GET /api/products`: List all products
- `POST /api/products`: Create a new product
- `GET /api/products/{id}`: Get product details
- `PUT /api/products/{id}`: Update a product
- `DELETE /api/products/{id}`: Delete a product

### 2. Accounts (`/api/accounts`)
- `POST /api/accounts`: Open an account
- `GET /api/accounts`: List accounts (Optional query params: `customerId`, `status`)
- `GET /api/accounts/{id}`: Get account details
- `POST /api/accounts/{id}/accrue`: Trigger manual interest accrual
- `POST /api/accounts/{id}/close`: Close account (handles maturity or premature closure with penalty)

Enjoy your Microservice!
