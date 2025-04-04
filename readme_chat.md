Chat Service Overview

The Chat Service is responsible for handling real‑time messaging between users using WebSocket (STOMP) and also secures its endpoints using JWT-based authentication. It persists chat messages in the database and supports broadcasting messages, user joining, and disconnection events.

──────────────────────────────

Configuration Package (com.example.chatapp_v2.configuration) • WebSocketConfig.java
 – Configures the WebSocket message broker.
 – Registers the STOMP endpoint ("/ws") with SockJS support.
 – Sets the application destination prefix ("/app") for messages and enables a simple broker on "/topic" for client subscriptions.

────────────────────────────── 2. Controller Package (com.example.chatapp_v2.controller) • ChatController.java
 – Handles incoming WebSocket messages from clients.
 – Provides an endpoint ("/sendMessage") for sending a chat message, saving it to the database, and broadcasting it to subscribers.
 – Provides an endpoint ("/addUser") to handle user join events by storing the username in the session and broadcasting a join message along with previous messages.

────────────────────────────── 3. Event Listeners Package (com.example.chatapp_v2.event_listeners) • WebSocketEventListener.java
 – Listens for WebSocket session disconnect events.
 – When a user disconnects, it creates a “leave” message and broadcasts it to all subscribers on the public topic.

────────────────────────────── 4. Security Package (com.example.chatapp_v2.security) • JwtAuthFilter.java
 – A servlet filter that intercepts incoming HTTP requests and checks for a valid JWT token in the Authorization header.
 – Extracts the token, validates it using the secret key, and sets the username attribute in the request if valid.
 – Returns an HTTP 401 status if token validation fails.

• SecurityConfig.java
 – Configures Spring Security for the Chat Service.
 – Loads the JWT secret from application properties and registers the JwtAuthFilter to secure endpoints (e.g., any request under "/chat/**" requires authentication).
 – Disables CSRF (appropriate for a stateless, JWT‑based system) and defines which endpoints require authentication.