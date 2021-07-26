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

    @OneToOne
    private AdImage adImage;

    @ManyToOne // binds the relations from OneToMany in user.java, making it a relationship
    @JoinColumn(name="user_id")
    private User user;

    // constructor
    public Ad(long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public Ad(long id, String title, String description, AdImage adImage, User user) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.adImage = adImage;
        this.user = user;
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

    public AdImage getAdImage() {
        return adImage;
    }

    public void setAdImage(AdImage adImage) {
        this.adImage = adImage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
