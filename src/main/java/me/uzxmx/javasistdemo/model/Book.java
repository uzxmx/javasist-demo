package me.uzxmx.javasistdemo.model;

import javax.persistence.Entity;

@Entity
public class Book extends Base {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
