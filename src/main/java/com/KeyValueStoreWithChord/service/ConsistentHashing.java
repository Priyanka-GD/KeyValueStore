package com.KeyValueStoreWithChord.service;

import com.KeyValueStoreWithChord.model.Node;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

public class ConsistentHashing {
    private final SortedMap<Integer, Node> ring = new TreeMap<>();
    private final int numberOfReplicas;

    public ConsistentHashing (int numberOfReplicas) {
        this.numberOfReplicas = numberOfReplicas;
    }

    // Adds a node to the hash ring
    public void initializeNode (Node node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            int hash = getHash(node.getNodeId() + i);
            System.out.println("Hash : " + hash + " Node " + node.getNodeId());
            ring.put(hash, node);
        }
    }

    // Returns the node responsible for the key based on hash
    public Node getNode (String key) {
        if (ring.isEmpty()) {
            return null;
        }

        int hash = getHash(key);
        SortedMap<Integer, Node> tailMap = ring.tailMap(hash);

        int nodeHash = hash;
        for (Map.Entry<Integer, Node> entry : tailMap.entrySet()) {
            Node node = entry.getValue();
            if (node.isInRange(hash)) {
                nodeHash = entry.getKey();
            }
        }
        return ring.get(nodeHash);
    }

    // Adds a new node with a specific range
    public void addNode (Node newNode) {
        int newStart = newNode.getRangeStart();
        int newEnd = newNode.getRangeEnd();

        for (Map.Entry<Integer, Node> entry : ring.entrySet()) {
            Node existingNode = entry.getValue();
            if (existingNode.isInRange(newStart)) {
                moveValues(existingNode, newNode, newStart, newEnd);
                existingNode.setRangeEnd(newStart - 1);
                break;
            }
        }

        // Add the new node to the ring
        ring.put(getHash(newNode.getNodeId()), newNode);
        //replicas
        initializeNode(newNode);
    }

    // Moves values from the existing node's map to the new node's map
    private void moveValues (Node existingNode, Node newNode, int newStart, int newEnd) {
        HashMap<String, String> existingMap = existingNode.getKeyValueStore();
        HashMap<String, String> newMap = newNode.getKeyValueStore();

        // Move key-value pairs from existing node to new node's HashMap
        for (int key = newStart; key <= newEnd; key++) {
            for (String keyName : existingMap.keySet()) {
                int hash = getHash(keyName);
                if (key == hash) {
                    newMap.put(keyName, existingMap.get(keyName));
                    existingMap.remove(keyName);
                }
            }
        }
    }

    // Remove a node from the ring
    public void removeNode (Node node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            int hash = getHash(node.getNodeId() + i);
            ring.remove(hash);
        }
    }

    // Get the range of a node
    public String getNodeRange (Node node) {
        return "Node ID: " + node.getNodeId() + ", Range: [" + node.getRangeStart() + ", " + node.getRangeEnd() + "]";
    }

    // Check if two ranges overlap
    public boolean rangesOverlap (Node node1, Node node2) {
        return node1.getRangeStart() <= node2.getRangeEnd() && node2.getRangeStart() <= node1.getRangeEnd();
    }

    // Hash function using MD5 and converting to an integer value
    private int getHash (String value) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(value.getBytes(StandardCharsets.UTF_8));

            // Convert the MD5 bytes into an integer hash value
            int hash = ((digest[0] & 0xFF) << 24) | ((digest[1] & 0xFF) << 16)
                    | ((digest[2] & 0xFF) << 8) | (digest[3] & 0xFF);

            // Return a positive hash value within the range of 0-99
            return Math.abs(hash) % 100;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
