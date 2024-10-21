### Summary of Changes in **NodeController** with Consistent Hashing (Step-wise):

1. **Initial Setup of the Node Class**:
    - A `NodeController` was created to manage nodes in the distributed key-value store.
    - A `HashMap<String, String>` is added to each node to store key-value pairs.
    - Each node is assigned a unique `nodeId` using consistent hashing.

2. **Consistent Hashing Implementation**:
    - Consistent hashing allows for a distributed system where keys are mapped to nodes.
    - We implemented the logic to calculate the hash for each node based on its `nodeId`.
    - The system assigns keys to specific nodes based on the hash value of the keys. This distributes the key-value pairs across multiple nodes in a balanced manner.

3. **Handling POST Requests in `NodeController`**:
    - The `NodeController` exposes an endpoint (`/api/addNode`) where key-value pairs are sent via a POST request.
    - Each key-value pair is inserted into the correct node based on the result of consistent hashing.
    - We integrated consistent hashing in such a way that when a key-value pair is sent, the key is hashed, and the node with the corresponding range of hashes is selected to store the value.

4. **Node Addition and Consistent Hashing Logic**:
    - Multiple nodes are defined, each listening on a different port (e.g., 8081, 8082, 8083, and 8084). These nodes form the distributed system.
    - Each node runs independently, and consistent hashing ensures that data is distributed across these nodes.
    - When a new node is added, the system recalculates the hashing ranges for keys and reassigns some of the keys to the new node if needed (data migration).

5. **Key Hashing Logic**:
    - When the user sends a POST request with a key-value pair, the key is hashed using a consistent hashing algorithm (e.g., MD5 or SHA-1).
    - The resulting hash determines which node will store the value, based on the pre-defined range of hash values for each node.

6. **Adding Data via Postman**:
    - Using Postman, we can send key-value pairs to `/api/addNode`. The system uses consistent hashing to determine which node should store the key-value pair.

---

### Project Aims

This project simulates a **Distributed Key-Value Store** using **Spring Boot** and **consistent hashing**. The goal is to create a distributed, fault-tolerant system where key-value pairs are stored across multiple independent nodes, making the system scalable and resilient. The main features include:

1. **Distributed Storage Across Multiple Nodes**:
    - The system distributes key-value pairs across multiple nodes (servers) using consistent hashing.
    - Each node can store data independently, reducing the risk of data loss and increasing system availability.

2. **Consistent Hashing**:
    - Consistent hashing ensures an even distribution of data across nodes and handles the dynamic addition or removal of nodes with minimal reallocation of data.
    - It allows for efficient lookup and retrieval of key-value pairs.

3. **Fault Tolerance and Scalability**:
    - New nodes can be added to the system dynamically, and data is redistributed accordingly.
    - If a node fails or is removed, the system ensures that data is still accessible from the other nodes.

4. **API-Driven Operations**:
    - The application provides a REST API (`/api/addNode`) to allow users to add key-value pairs via POST requests.
    - The system dynamically assigns the data to the appropriate node based on consistent hashing.

5. **High Availability and Load Balancing**:
    - Since data is distributed across multiple nodes, the system can handle high loads, and failure in one node will not cause complete system failure.

This project serves as a foundation for distributed systems, illustrating the concepts of consistent hashing, scalability, and fault tolerance. It is especially useful for scenarios where data must be distributed across a large number of machines with minimal central coordination.