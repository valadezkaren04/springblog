package com.codeup.springblog.models;

import org.hibernate.annotations.Tables;

import javax.persistence.*;

@Entity
@Table(name="ads") //creates table
public class Ad {
    @Id // this ID is going to be the main identifier within this class
    @GeneratedValue(strategy = GenerationType.IDENTITY) // strategy: primary key? ; generated value: auto incremented
    private long id;

    // creating columns ; columns need annotations ; nullable = no empties
    @Column(nullable = false, length = 155)
    private String title;

    @Column(nullable = false)
    private String description;

    // constructor
    public Ad(long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    // empty constructor takes care of the red Ad
    public Ad() {

    }

    // getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
