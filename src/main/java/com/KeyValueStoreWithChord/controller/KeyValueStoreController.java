package com.KeyValueStoreWithChord.controller;

import com.KeyValueStoreWithChord.model.User;
import com.KeyValueStoreWithChord.service.KeyValueStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/keyvaluestore")
public class KeyValueStoreController {

    @Autowired
    private KeyValueStoreService keyValueStoreService;

    @PostMapping("/addUser")
    public ResponseEntity<Map<String, String>> addUser (@RequestBody User user) {
        // Your logic to add the user here
        Map<String, String> response = new HashMap<>();
        response.put("message", "User added to Node4"); // Update this message as needed
        return ResponseEntity.ok(response); // Return the response as a JSON object
    }


    @PostMapping("/addNode")
    public String addNode (@RequestBody Map<String, Object> requestData) {
        String nodeId = (String) requestData.get("nodeId");
        int startRange = (int) requestData.get("startRange");
        int endRange = (int) requestData.get("endRange");
        keyValueStoreService.addNode(nodeId, startRange, endRange);
        return "Node " + nodeId + " added with range " + startRange + " to " + endRange;
    }

    @DeleteMapping("/removeNode/{nodeId}")
    public String removeNode (@PathVariable String nodeId) {
        keyValueStoreService.removeNode(nodeId);
        return "Node " + nodeId + " removed and values redistributed.";
    }

    @GetMapping("/getUser/{userId}")
    public ResponseEntity<User> getUser (@PathVariable String userId) {
        User user = keyValueStoreService.getUser(userId);
        if (user != null) {
            return ResponseEntity.ok(user); // Return user if found
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Return 404 if user not found
        }
    }
}
