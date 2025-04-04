# Chat Application
## For the subject Microservices and Architecture

### Currently contains:
1) Chat Microservice (port: 8080)
2) Login/Signup Microservice (port: 8081)
3) API Gateway service (port: 9090)
4) Eureka Server service (port: 8671)

### URLs:
1) Chat Service
   - Base URL: http://localhost:8080/
   - WebSocket Connection: ws://localhost:8080/ws (??)
   - Send Message: POST http://localhost:8080/chat/send
   - Fetch Chat History: GET http://localhost:8080/chat/history/{userId}
   - Fetch User Chats: GET http://localhost:8080/chat/conversations
2) Auth Service (Login/Signup)
   - Base URL: http://localhost:8081/
   - Authentication
     - User Registration: POST http://localhost:8081/api/auth/signup
     - User Login: POST http://localhost:8081/api/auth/login
     - Validate Token: GET http://localhost:8081/api/auth/validate
     - Fetch User Profile: GET http://localhost:8081/api/users/{userId}
   - Testing
     - Public Access: GET http://localhost:8081/api/test/all (Accessible by everyone)
     - User Access: GET http://localhost:8081/api/test/user (Requires USER, MODERATOR, or ADMIN role)
     - Moderator Access: GET http://localhost:8081/api/test/mod (Requires MODERATOR role)
     - Admin Access: GET http://localhost:8081/api/test/admin (Requires ADMIN role)
3) API Gateway
    - Base URL: http://localhost:9090/
   - Proxy to Chat Service: http://localhost:9090/api/chat/**
   - Proxy to Auth Service: http://localhost:9090/api/api/**
4) Eureka Server
    - Dashboard: http://localhost:8761/
   - View Registered Services: http://localhost:8761/eureka/apps

### Setup:
1) Add environment variables:
   - setx DB_USER "root"
   - setx DB_PASS "mypassword"


### Description of files:
1)