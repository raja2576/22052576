package com.question2.question2.Modal;

public class Post {
    private int id;
    private int userId;
    private String content;
    private int commentCount;

    // Default constructor
    public Post() {}

    // Parameterized constructor
    public Post(int id, int userId, String content, int commentCount) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.commentCount = commentCount;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }

    public int getCommentCount() {
        return commentCount;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    // Override toString() for easy debugging
    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                ", commentCount=" + commentCount +
                '}';
    }
}
