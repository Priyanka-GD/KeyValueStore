package com.KeyValueStoreWithChord.controller;

import com.KeyValueStoreWithChord.model.User;
import com.KeyValueStoreWithChord.service.KeyValueStoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class KeyValueStoreControllerTest {

    private MockMvc mockMvc;

    @Mock
    private KeyValueStoreService keyValueStoreService;

    @InjectMocks
    private KeyValueStoreController keyValueStoreController;

    @BeforeEach
    public void setUp () {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(keyValueStoreController).build();
    }

    @Test
    public void testAddUser () throws Exception {
        User user = new User("1", "John Doe", "123 Main St", "john.doe@example.com");

        Map<String, String> expectedResponse = new HashMap<>();
        expectedResponse.put("message", "User added to Node4");

        ResponseEntity<Map<String, String>> response = keyValueStoreController.addUser(user);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void testAddNode () throws Exception {
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("nodeId", "Node1");
        requestData.put("startRange", 0);
        requestData.put("endRange", 100);

        String expectedResponse = "Node Node1 added with range 0 to 100";

        String response = keyValueStoreController.addNode(requestData);
        assertEquals(expectedResponse, response);
        verify(keyValueStoreService, times(1)).addNode("Node1", 0, 100);
    }

    @Test
    public void testRemoveNode () throws Exception {
        String nodeId = "Node1";
        String expectedResponse = "Node Node1 removed and values redistributed.";

        String response = keyValueStoreController.removeNode(nodeId);
        assertEquals(expectedResponse, response);
        verify(keyValueStoreService, times(1)).removeNode(nodeId);
    }

    @Test
    public void testGetUserFound () throws Exception {
        String userId = "1";
        User user = new User(userId, "John Doe", "123 Main St", "john.doe@example.com");

        when(keyValueStoreService.getUser(userId)).thenReturn(user);

        ResponseEntity<User> response = keyValueStoreController.getUser(userId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void testGetUserNotFound () throws Exception {
        String userId = "2";

        when(keyValueStoreService.getUser(userId)).thenReturn(null);

        ResponseEntity<User> response = keyValueStoreController.getUser(userId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }
}
