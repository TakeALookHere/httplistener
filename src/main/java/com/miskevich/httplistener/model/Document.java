package com.miskevich.httplistener.model;

import java.util.List;

public class Document {
    private String id;
    private List<String> tokens;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getTokens() {
        return tokens;
    }

    public void setTokens(List<String> tokens) {
        this.tokens = tokens;
    }

    @Override
    public String toString() {
        return "Document{" +
                "id='" + id + '\'' +
                ", tokens=" + tokens +
                '}';
    }
}
