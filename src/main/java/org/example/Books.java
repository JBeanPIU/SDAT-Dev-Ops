// Class: Books, used for book information storage. This file includes name of book, author, & book availability.
// By: Cameron Beanland
// Date: Feb 7th, 2025

package org.example;

public class Books {
    // Start off with the basics (private objects to prevent modifications from outside class)
    private String title;
    private String author;
    private boolean upForGrabs;

    // This constructor allows creation of an object from this class into
    public Books(String title, String author) {
        this.title = title;
        this.author = author;
        this.upForGrabs = true; // book is available by default
    }

    // Get Set (GO), a small section to include any setters/getters we'll need for retrieving data
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isUpForGrabs() {
        return upForGrabs;
    }

    public void setUpForGrabs(boolean upForGrabs) {
        this.upForGrabs = upForGrabs; // the only setter we had to create, just to update availability
    }
}

