package com.example.picturesq;

public class Post {
    private String PostImageSrc;
    private String profileImageSrc;
    private String username;
    private String date;
    private String nbLikes;
    private String nbComments;

    public String getPostImageSrc() {
        return PostImageSrc;
    }

    public String getProfileImageSrc() {
        return profileImageSrc;
    }

    public String getUsername() {
        return username;
    }

    public String getDate() {
        return date;
    }

    public String getNbLikes() {
        return nbLikes;
    }

    public String getNbComments() {
        return nbComments;
    }

    public void setPostImageSrc(String postImageSrc) {
        PostImageSrc = postImageSrc;
    }

    public void setProfileImageSrc(String profileImageSrc) {
        this.profileImageSrc = profileImageSrc;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setNbLikes(String nbLikes) {
        this.nbLikes = nbLikes;
    }

    public void setNbComments(String nbComments) {
        this.nbComments = nbComments;
    }

    @Override
    public String toString() {
        return "Post{" +
                "PostImageSrc='" + PostImageSrc + '\'' +
                ", profileImageSrc='" + profileImageSrc + '\'' +
                ", username='" + username + '\'' +
                ", date='" + date + '\'' +
                ", nbLikes='" + nbLikes + '\'' +
                ", nbComments='" + nbComments + '\'' +
                '}';
    }
}