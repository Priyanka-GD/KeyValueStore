package com.KeyValueStoreWithChord.controller;


import com.KeyValueStoreWithChord.model.KeyValuePair;
import com.KeyValueStoreWithChord.model.Node;
import com.KeyValueStoreWithChord.service.ConsistentHashing;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/nodeApi")
public class NodeController {

    private ConsistentHashing consistentHashing;
    private List<Node> nodes;

    public NodeController () {
        this.consistentHashing = new ConsistentHashing(3);
        this.nodes = new ArrayList<>();

        // Create and add nodes with assigned ranges
        Node node1 = new Node("Node-1", 0, 29);
        Node node2 = new Node("Node-2", 30, 59);
        Node node3 = new Node("Node-3", 60, 79);
        Node node4 = new Node("Node-4", 80, 99);

        consistentHashing.initializeNode(node1);
        consistentHashing.initializeNode(node2);
        consistentHashing.initializeNode(node3);
        consistentHashing.initializeNode(node4);

        nodes.add(node1);
        nodes.add(node2);
        nodes.add(node3);
        nodes.add(node4);
    }

    @PostMapping("/addKeyValuePair")
    @ResponseStatus(HttpStatus.CREATED)
    public KeyValuePair addKeyValuePair (@RequestBody KeyValuePair keyValuePair) {
        System.out.println("addNode called with key: " + keyValuePair.getKey());
        Node node = consistentHashing.getNode(keyValuePair.getKey());
        node.putKeyValue(keyValuePair.getKey(), keyValuePair.getValue());
        return keyValuePair;
    }

    @GetMapping("/getKey")
    public String getKeyValue (@RequestParam String key) {
        Node node = consistentHashing.getNode(key); // Retrieve the node responsible for the key
        String value = node.getValue(key);
        return value != null ? "Key: " + key + " found in " + node.getNodeId() + " with value: " + value : "Key not found!";
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/addNewNode")
    @ResponseStatus(HttpStatus.CREATED)
    public Node addNewNode (@RequestBody Node newNode) {
        consistentHashing.addNode(newNode);
        return newNode;
    }
}

