// Class: LibraryManagementTesting, this class is for JUnit 5 testing
// By: Cameron Beanland
// Date: Feb 7th, 2025

package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class LibraryManagementTest {
    private LibraryManagement library;
    private User user;

    /* ===================== */
    @BeforeEach
    void setUp() {
        library = new LibraryManagement();

        // Adding new books to catalog, just for some variety tbh
        library.addBook(new Books("The Hobbit", "J.R.R. Tolkien"));
        library.addBook(new Books("Harry Potter and the Sorcerer's Stone", "J.K. Rowling"));
        library.addBook(new Books("Spider-Man: Into the Spider-Verse", "Marvel Comics"));

        // Creating and registering user with a borrow limit of 2
        user = new User("testUser", "password", 2);
        library.registerUser(user);
    }

    /* ===================== */
    @Test
    void testSearchCatalog() { // This checks whether the search function can find a book by its title, in this case it's 'The Hobbit'
        List<Books> results = library.searchCatalog("The Hobbit");
        assertEquals(1, results.size());
        assertEquals("The Hobbit", results.get(0).getTitle());
    }

    /* ===================== */
    @Test // Verifies that a user can borrow a book if available
    void testIssueBook_Success() { // I hate snake-case LOL but wanted it to look different from the others to know it's from testing
        boolean issued = library.issueBook("testUser", "The Hobbit");
        assertTrue(issued);
    }

    /* ===================== */
    @Test // Makes sure that a user can't borrow more than one book at the same time
    void testIssueBook_AlreadyBorrowed() {
        library.issueBook("testUser", "The Hobbit");
        boolean secondAttempt = library.issueBook("testUser", "The Hobbit");
        assertFalse(secondAttempt);
    }

    /* ===================== */
    @Test // Used to prevent users from exceeding their borrow limit. New users only get 2 max limit, base accounts have 4-6
    void testIssueBook_BorrowingLimitReached() {
        library.issueBook("testUser", "The Hobbit");
        library.issueBook("testUser", "Harry Potter and the Sorcerer's Stone");
        boolean thirdAttempt = library.issueBook("testUser", "Spider-Man: Into the Spider-Verse");
        assertFalse(thirdAttempt);
    }

    /* ===================== */
    @Test // This makes it so you can borrow a book again, so long as you returned it
    void testReturnBook_Success() {
        library.issueBook("testUser", "The Hobbit");
        library.returnBook("testUser", "The Hobbit");
        boolean reborrow = library.issueBook("testUser", "The Hobbit"); // Should now be available
        assertTrue(reborrow);
    }

    /* ===================== */
    @Test // If you're trying to return a book you aren't borrowing then that's a separate issue (prevents you from doing so)
    void testReturnBook_NotBorrowed() {
        library.returnBook("testUser", "The Hobbit"); // User never borrowed this
        boolean borrowAttempt = library.issueBook("testUser", "The Hobbit"); // Should still be available
        assertTrue(borrowAttempt);
    }
}

