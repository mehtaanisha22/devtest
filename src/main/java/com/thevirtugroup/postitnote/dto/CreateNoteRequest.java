package com.thevirtugroup.postitnote.dto;

import javax.validation.constraints.NotNull;

public class CreateNoteRequest {
    @NotNull
    private String name;
    @NotNull
    private String text;
    @NotNull
    private Long userId;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
