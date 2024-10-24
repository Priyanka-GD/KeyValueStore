package com.KeyValueStoreWithChord.service;

import com.KeyValueStoreWithChord.model.Node;
import com.KeyValueStoreWithChord.model.User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;

@Service
public class KeyValueStoreService {
    private TreeMap<Integer, Node> nodes = new TreeMap<>(); // Using TreeMap for ordered nodes by their hash ranges

    // Initializing nodes with default ranges in ascending order
    public KeyValueStoreService () {
        nodes.put(10, new Node("Node1", 0, 10));
        nodes.put(40, new Node("Node2", 11, 40));
        nodes.put(70, new Node("Node3", 41, 70));
        nodes.put(100, new Node("Node4", 71, 100));
    }

    // Add a new node with range, adjust ranges of neighboring nodes
    public String addNode (String nodeId, int startRange, int endRange) {
        Node newNode = new Node(nodeId, startRange, endRange);

        // Find the overlapping node and redistribute users accordingly
        Map.Entry<Integer, Node> overlappingCeilingNodeEntry = nodes.ceilingEntry(endRange); // Find the node that overlaps with the range
        if (overlappingCeilingNodeEntry != null) {
            Node overlappingNode = overlappingCeilingNodeEntry.getValue();
            moveUsersToNewNode(overlappingNode, newNode);  // Move users between the ranges
            adjustNodeRangeCeling(overlappingNode, newNode);     // Adjust the range of the existing node
        }

        Map.Entry<Integer, Node> overlappingFloorNodeEntry = nodes.floorEntry(endRange);
        if (overlappingFloorNodeEntry != null) {
            Node overlappingNode = overlappingFloorNodeEntry.getValue();
            moveUsersToNewNode(overlappingNode, newNode);
            adjustNodeRangeFloor(overlappingNode, newNode);
        }
        nodes.put(endRange, newNode);
        return "Node " + nodeId + " added with range " + startRange + " to " + endRange;
    }

    private void adjustNodeRangeFloor (Node existingNode, Node newNode) {
        int existingStart = existingNode.getStartRange();
        int existingEnd = existingNode.getEndRange();
        int newStart = newNode.getStartRange();

        // Only adjust if the new node’s start range is within the existing node's range
        if (existingStart < newStart && newStart >= existingEnd) {
            newNode.setStartRange(existingEnd + 1);
        }
    }

    // Adjust the existing node's range after adding a new node
    private void adjustNodeRangeCeling(Node existingNode, Node newNode) {
        int existingStart = existingNode.getStartRange();
        int existingEnd = existingNode.getEndRange();
        int newStart = newNode.getStartRange();
        int newEnd = newNode.getEndRange();

        // Only adjust if the new node’s start range is within the existing node's range
        if (existingStart < newStart && newStart <= existingEnd) {
            existingNode.setStartRange(newEnd + 1);
        }
    }
    // Move users from the overlapping range of the existing node to the new node
    private void moveUsersToNewNode (Node existingNode, Node newNode) {
        Map<String, User> usersToMove = existingNode.getUserMap();
        for (Map.Entry<String, User> entry : usersToMove.entrySet()) {
            User user = entry.getValue();
            int hash = Math.abs(user.getUserId().hashCode() % 100); // Calculate the user's hash

            // If user's hash falls within the new node's range, move it to the new node
            if (hash >= newNode.getStartRange() && hash <= newNode.getEndRange()) {
                existingNode.removeUser(user.getUserId()); // Remove user from the existing node
                newNode.addUser(user.getUserId(), user);    // Add user to the new node
            }
        }
    }

    // Remove a node and redistribute its values to the next appropriate node in the ring
    public void removeNode (String nodeId) {
        Node nodeToRemove = null;
        int nodeHash = -1;

        // Find the node to remove by nodeId and get its hash (i.e., end range)
        for (Map.Entry<Integer, Node> entry : nodes.entrySet()) {
            if (entry.getValue().getNodeId().equals(nodeId)) {
                nodeToRemove = entry.getValue();
                nodeHash = entry.getKey();
                break;
            }
        }

        if (nodeToRemove != null) {
            // Get the next node in the ring to receive the values of the removed node
            Map.Entry<Integer, Node> nextNodeEntry = nodes.higherEntry(nodeHash);

            if (nextNodeEntry != null) {
                Node nextNode = nextNodeEntry.getValue();

                // Move users from the removed node to the next node
                for (Map.Entry<String, User> entry : nodeToRemove.getUserMap().entrySet()) {
                    User user = entry.getValue();
                    nextNode.addUser(user.getUserId(), user);
                }

                // Expand the range of the next node to include the removed node's range
                nextNode.setStartRange(nodeToRemove.getStartRange());
            }

            nodes.remove(nodeHash); // Remove the node from the ring
        }
    }

    // Get a node by finding the node responsible for a given hash value
    public Node getNodeByHash (int hash) {
        Map.Entry<Integer, Node> nodeEntry = nodes.ceilingEntry(hash); // Find the node responsible for this hash
        if (nodeEntry == null) {
            // If the hash is beyond the last node, wrap around to the first node (circular ring)
            nodeEntry = nodes.firstEntry();
        }
        return nodeEntry.getValue();
    }

    // Add new user based on hash
    public String addUser (User user) {
        int hash = Math.abs(user.getUserId().hashCode() % 100);
        Node assignedNode = getNodeByHash(hash);

        if (assignedNode != null) {
            assignedNode.addUser(user.getUserId(), user);
            return "User added to " + assignedNode.getNodeId();
        } else {
            return "No node found for hash value: " + hash;
        }
    }

    public User getUser (String userId) {
        // Traverse all nodes to find the user
        for (Node node : nodes.values()) {
            if (node.getUserMap().containsKey(userId)) {
                System.out.println("User in Node :" + node.getNodeId());
                return node.getUserMap().get(userId);
            }
        }
        return null; // Return null if user is not found
    }
}
