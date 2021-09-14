package com.thevirtugroup.postitnote.dto;

import javax.validation.constraints.NotNull;

public class UpdateNoteRequest {
    @NotNull
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String text;

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
}
