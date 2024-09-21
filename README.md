# Restkeeper

#### Description
Restkeeper is a scalable Restaurant Management SAAS platform built with microservices architecture. It is designed to streamline restaurant operations, enhance service efficiency, and provide powerful data insights to improve decision-making for restaurant owners and managers.

# Restkeeper-super

#### Software Architecture
The software architecture of Restkeeper is based on a microservices approach, allowing each service to operate independently while communicating through RESTful APIs. The core components include:

- **Authentication Service**: Manages user login, registration, and role-based access control.
- **Order Management Service**: Handles order creation, updates, tracking, and notifications.
- **Inventory Management Service**: Manages stock levels, purchase orders, and supplier information.
- **Customer Relationship Management (CRM) Service**: Tracks customer interactions, feedback, and loyalty programs.
- **Reporting and Analytics Service**: Provides detailed reports on sales, inventory levels, and customer data for better insights.
- **Payment Gateway Integration**: Supports multiple payment methods and ensures secure transactions.
- **Deployment**: The platform is containerized using Docker, orchestrated with Kubernetes, and deployed on AWS for high availability and scalability.

#### Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/cqftx001/Scalable-Restaurant-Management-SAAS-with-Microservices.git
   cd Scalable-Restaurant-Management-SAAS-with-Microservices

2. **Set up environment variables:**
  Create a .env file in the root directory and configure your environment variables as per the .env.example file.

3. **Run Docker Compose:**
   
   ```bash docker-compose up -d```

4. **Access the Application:**
  Open your browser and navigate to http://localhost:8000 to access the main dashboard.

5. **User Registration and Login:**
  Register as a new user or log in using your existing credentials to access different services based on your role.

6. **Manage Orders and Inventory:**
  Use the dashboard to manage orders, update inventory levels, and view reports.


