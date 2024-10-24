### Summary of Changes (Step-wise):

1. **Node Management:**
   - **`addNode(String nodeId, int startRange, int endRange)`**:
      - Adds a new node to the system by specifying its ID and range.
      - Finds overlapping nodes, redistributes users from the existing node to the new one, and adjusts the ranges of both the new and existing nodes.
      - Uses methods `adjustNodeRangeCeling()` and `adjustNodeRangeFloor()` to handle range adjustments and prevent overlaps between nodes.

   - **`removeNode(String nodeId)`**:
      - Removes a node from the system by its ID.
      - Redistributes users from the removed node to the next node in the ring and expands the next node's range to cover the removed node's range.
      - Ensures consistency in node ranges after node removal.

2. **User Operations:**
   - **`addUser(User user)`**:
      - Adds a user to the appropriate node based on the user's hash value.
      - Calculates the hash using the user's `userId` and assigns the user to a node responsible for that hash.
      - Calls `getNodeByHash(int hash)` to determine which node to add the user to.

   - **`getUser(String userId)`**:
      - Retrieves a user by `userId`, iterating over all nodes to find the user.
      - Returns the user and the node they belong to or `null` if not found.

3. **Internal Logic:**
   - **`moveUsersToNewNode(Node existingNode, Node newNode)`**:
      - Moves users from the existing node to the new node if their hash falls within the new node’s range.
      - Ensures correct redistribution of users during node additions.

   - **`adjustNodeRangeCeling(Node existingNode, Node newNode)` and `adjustNodeRangeFloor(Node existingNode, Node newNode)`**:
      - Adjusts the range of existing nodes when a new node is added to prevent overlapping ranges.

4. **Controller Operations:**
   - **`/addUser` (POST)**:
      - Allows the addition of a new user by sending a `User` object in the request body.
      - Invokes the `addUser` service method to handle user assignment to nodes.

   - **`/addNode` (POST)**:
      - Adds a new node by specifying the node's ID, start range, and end range through the request body.
      - Calls the `addNode` service method to handle node insertion and range adjustment.

   - **`/removeNode/{nodeId}` (DELETE)**:
      - Removes a node from the system based on the node ID provided in the URL path.
      - Triggers the `removeNode` method in the service to handle redistribution of the node’s data and adjust ranges.

   - **`/getUser/{userId}` (GET)**:
      - Fetches a user by their `userId`.
      - Returns the user and their details if found or a `404 Not Found` status if the user does not exist in any node.