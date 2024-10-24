package com.KeyValueStoreWithChord.service;


import com.KeyValueStoreWithChord.model.Node;
import com.KeyValueStoreWithChord.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class KeyValueStoreServiceTest {

    private KeyValueStoreService keyValueStoreService;

    @BeforeEach
    public void setUp() {
        keyValueStoreService = new KeyValueStoreService();
    }

    @Test
    public void testAddNode() {
        String nodeId = "Node5";
        int startRange = 101;
        int endRange = 150;

        String response = keyValueStoreService.addNode(nodeId, startRange, endRange);
        assertEquals("Node Node5 added with range 101 to 150", response);
    }

    @Test
    public void testRemoveNode() {
        String nodeId = "Node1"; // Remove an existing node
        keyValueStoreService.removeNode(nodeId);

        // Check that Node1 has been removed
        Node removedNode = keyValueStoreService.getNodeByHash(5); // Hash for Node1 is 5
        assertNotEquals("Node1", removedNode.getNodeId());
    }

    @Test
    public void testAddUser() {
        User user = new User("1", "John Doe", "123 Main St", "john.doe@example.com");

        String response = keyValueStoreService.addUser(user);
        assertNotNull(response);

        // Check if the user is in the node
        int userHash = Math.abs(user.getUserId().hashCode() % 100);
        Node node = keyValueStoreService.getNodeByHash(userHash);

        // Debugging outputs
        System.out.println("Node ID: " + node.getNodeId());
        System.out.println("User map contents: " + node.getUserMap());

        assertTrue(node.getUserMap().containsKey(user.getUserId()));
    }
    @Test
    public void testGetUserFound() {
        User user = new User("1", "John Doe", "123 Main St", "john.doe@example.com");
        keyValueStoreService.addUser(user);

        User foundUser = keyValueStoreService.getUser(user.getUserId());
        assertNotNull(foundUser);
        assertEquals(user.getName(), foundUser.getName());
    }

    @Test
    public void testGetUserNotFound() {
        User user = keyValueStoreService.getUser("unknownUserId");
        assertNull(user);
    }
}
