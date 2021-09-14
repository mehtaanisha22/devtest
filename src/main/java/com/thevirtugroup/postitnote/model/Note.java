package com.thevirtugroup.postitnote.model;

public class Note {
    private Long id;
    private String name;
    private String text;
    private String createdAt;
    private Long userId;

    public Note() {
    }

    public Note(Long id, String name, String text, String createdAt, Long userId) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.createdAt = createdAt;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                '}';
    }
}
