package com.question2.question2.Modal;

public class User {
    public int id;
    private String name;
    public int postCount;

    // Default constructor
    public User() {}

    // Parameterized constructor
    public User(int id, String name, int postCount) {
        this.id = id;
        this.name = name;
        this.postCount = postCount;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPostCount() {
        return postCount;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }

    // Override toString() for easy debugging
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", postCount=" + postCount +
                '}';
    }
}
