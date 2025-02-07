// Class: User, provides information for any user accessing the program. Takes note of name, username, and a list of books borrowed
// By: Cameron Beanland
// Date: Feb 7th, 2025

package org.example;

import java.util.ArrayList;
import java.util.List;

public class User {
    // Private objects to prevent modifications from outside class
    private String username;
    private String password; // Added for user verification
    private int borrowLimit;
    private List<Books> borrowedBooks;

    // This constructor allows creation of a Books object and initializes fields
    public User(String username, String password, int borrowLimit) {
        this.username = username;
        this.password = password;
        this.borrowLimit = borrowLimit;

        // A small method to make sure borrow limit stays positive
        if (borrowLimit <= 0) {
            throw new IllegalArgumentException("Borrow limit must be positive.");
        }

        this.borrowedBooks = new ArrayList<>();
    }

    /* ============================================================================ */
    // 'Get' methods used to obtain/read values from outside this class
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password; // Added a getter method for password functionality
    }

    public List<Books> getBorrowedBooks() {
        return borrowedBooks;
    }

    public boolean canBorrow() {
        return borrowedBooks.size() < borrowLimit;
    }

    public void borrowBook(Books book) {
        if (canBorrow() && book.isUpForGrabs()) {
            borrowedBooks.add(book);
            book.setUpForGrabs(false);
        }
    }

    public void returnBook(Books book) {
        borrowedBooks.remove(book);
        book.setUpForGrabs(true);
    }
}




