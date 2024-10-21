package com.KeyValueStoreWithChord.model;

import java.util.HashMap;

public class Node {
    private String nodeId;
    private int rangeStart;
    private int rangeEnd;
    private HashMap<String, String> keyValueStore;

    public Node (String nodeId, int rangeStart, int rangeEnd) {
        this.nodeId = nodeId;
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
        this.keyValueStore = new HashMap<>();
    }

    public String getNodeId () {
        return nodeId;
    }

    public int getRangeStart () {
        return rangeStart;
    }

    public int getRangeEnd () {
        return rangeEnd;
    }

    public HashMap<String, String> getKeyValueStore () {
        return keyValueStore;
    }

    public void putKeyValue (String key, String value) {
        keyValueStore.put(key, value);
    }

    public String getValue (String key) {
        return keyValueStore.get(key);
    }

    public boolean isInRange (int hash) {
        return hash >= rangeStart && hash <= rangeEnd;
    }
}
