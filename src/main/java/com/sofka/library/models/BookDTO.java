package com.sofka.library.models;

public class BookDTO {


    private String id;
    private String name;
    private String category;
    private Boolean borrowed;

    public BookDTO(String id, String name, String category, Boolean borrowed) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.borrowed = borrowed;
    }

    public Boolean getBorrowed() {
        return borrowed;
    }

    public void setBorrowed(Boolean borrowed) {
        this.borrowed = borrowed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
