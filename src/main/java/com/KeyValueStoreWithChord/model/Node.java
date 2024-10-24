package com.KeyValueStoreWithChord.model;


import java.util.HashMap;
import java.util.Map;

public class Node {
    private String nodeId;
    private int startRange;
    private int endRange;
    private Map<String, User> userMap = new HashMap<>();

    public Node (String nodeId, int startRange, int endRange) {
        this.nodeId = nodeId;
        this.startRange = startRange;
        this.endRange = endRange;
    }

    public String getNodeId () {
        return nodeId;
    }

    public void setNodeId (String nodeId) {
        this.nodeId = nodeId;
    }

    public int getStartRange () {
        return startRange;
    }

    public void setStartRange (int startRange) {
        this.startRange = startRange;
    }

    public int getEndRange () {
        return endRange;
    }

    public void setEndRange (int endRange) {
        this.endRange = endRange;
    }

    public Map<String, User> getUserMap () {
        return userMap;
    }

    public void addUser (String key, User user) {
        this.userMap.put(key, user);
    }

    public void removeUser (String key) {
        this.userMap.remove(key);
    }
}
