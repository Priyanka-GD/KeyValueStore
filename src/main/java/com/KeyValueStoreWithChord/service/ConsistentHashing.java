package com.KeyValueStoreWithChord.service;



import com.KeyValueStoreWithChord.model.Node;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.SortedMap;
import java.util.TreeMap;

public class ConsistentHashing {
    private final SortedMap<Integer, Node> ring = new TreeMap<>();
    private final int numberOfReplicas;

    public ConsistentHashing (int numberOfReplicas) {
        this.numberOfReplicas = numberOfReplicas;
    }

    // Adds a node to the hash ring
    public void addNode (Node node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            int hash = getHash(node.getNodeId() + i);
            System.out.println("Hash : "+ hash +  " Node " + node.toString());
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
        int nodeHash = tailMap.isEmpty() ? ring.firstKey() : tailMap.firstKey();
        System.out.println("Hash : "+ nodeHash);
        System.out.println("Get Value : "+ ring.get(nodeHash).toString());
        return ring.get(nodeHash);
    }

    // Hash function using MD5
    private int getHash (String value) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(value.getBytes(StandardCharsets.UTF_8));
            return Math.abs(new String(digest).hashCode()) % 100; // Hash value in range of 0-99
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
