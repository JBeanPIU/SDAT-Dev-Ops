// Class: LibraryManagement, the main user interface for being able to log in, view book availability, search, & checkout.
// By: Cameron Beanland
// Date: Feb 7th, 2025

package org.example;

import java.util.*;
import java.util.stream.Collectors;

public class LibraryManagement {
    private Map<String, Books> catalog;
    private Map<String, User> users;
    private User currentUser;

    public LibraryManagement() {
        catalog = new HashMap<>();
        users = new HashMap<>();
    }

    public void addBook(Books book) {
        catalog.put(book.getTitle().toLowerCase(), book);
    }

    public void registerUser(User user) {
        users.put(user.getUsername().toLowerCase(), user);
    }

    /* ======================================================= */
    // Methods section, contains important methods required for book borrowing, username creation, logging in, etc.
    public boolean issueBook(String username, String bookTitle) {
        User user = users.get(username.toLowerCase());
        Books book = catalog.get(bookTitle.toLowerCase());
        if (user != null && book != null && book.isUpForGrabs() && user.canBorrow()) {
            user.borrowBook(book);
            book.setUpForGrabs(false); // Sets book to unavailable after being borrowed
            return true;
        }
        return false;
    }


    public void returnBook(String username, String bookTitle) {
        User user = users.get(username.toLowerCase());
        Books book = catalog.get(bookTitle.toLowerCase());
        if (user != null && book != null && user.getBorrowedBooks().contains(book)) { // Check if user is borrowing book
            user.returnBook(book);
            System.out.println("Book returned successfully!");
        } else {
            System.out.println("You can't return a book you don't have.");
        }
    }

    public List<Books> searchCatalog(String query) {
        String queryLower = query.toLowerCase();
        return catalog.values().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(queryLower) || book.getAuthor().toLowerCase().contains(queryLower))
                .collect(Collectors.toList());
    }

    public boolean loginUser(String username, String password) {
        User user = users.get(username.toLowerCase());
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            return true;
        }
        return false;
    }

    public void createUser(String username, String password) {
        User newUser = new User(username, password, 2);
        users.put(username.toLowerCase(), newUser);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    /* ======================================================= */
    // User menu integration, this is the main loop of the program and allows you to sign in, create accounts, logout, etc.
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LibraryManagement library = new LibraryManagement();

        library.addBook(new Books("We Share Everything", "Robert Munsch"));
        library.addBook(new Books("Moloka'i", "Alan Brennett"));
        library.addBook(new Books("Water for Elephants", "Sara Gruen"));

        library.issueBook("JBean", "We Share Everything");
        library.issueBook("pachi", "Water for Elephants");

        library.returnBook("pachi", "Water for Elephants");

        User user1 = new User("JBean", "abc123", 5);
        User user2 = new User("icecold", "pword", 4);
        User user3 = new User("pachi", "broccoli", 6);

        library.registerUser(user1);
        library.registerUser(user2);
        library.registerUser(user3);

        while (true) {
            System.out.println("===== \uD83D\uDCDA Library Management System \uD83D\uDD8A\uFE0F =====");
            System.out.println("1. Sign In!");
            System.out.println("2. Create New User?");
            System.out.println("3. Exit..");
            System.out.print("Select an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();

                    if (library.loginUser(username, password)) {
                        System.out.println("Login successful!");
                        showUserMenu(scanner, library);
                    } else {
                        System.out.println("Invalid username or password, please try again.");
                    }
                    break;

                case 2:
                    System.out.print("Enter a new username: ");
                    String newUsername = scanner.nextLine();
                    System.out.print("Enter a new password: ");
                    String newPassword = scanner.nextLine();

                    library.createUser(newUsername, newPassword);
                    System.out.println("New user created successfully!");
                    break;

                case 3:
                    System.out.println("Thanks for using our library services. Come again!");
                    return;
            }
        }
    }

    private static void showUserMenu(Scanner scanner, LibraryManagement library) {
        while (true) {
            System.out.println("\n===== User Menu =====");
            System.out.println("1. Search Catalog");
            System.out.println("2. Borrow Book");
            System.out.println("3. Return Book");
            System.out.println("4. Logout");
            System.out.print("Select an option: ");
            int userOption = scanner.nextInt();
            scanner.nextLine();

            switch (userOption) {
                case 1:
                    System.out.print("Enter search query: ");
                    String query = scanner.nextLine();
                    List<Books> searchResults = library.searchCatalog(query);
                    if (searchResults.isEmpty()) {
                        System.out.println("No books found by that name. Maybe next time!");
                    } else {
                        for (Books book : searchResults) {
                            System.out.println(book.getTitle() + " by " + book.getAuthor());
                        }
                    }
                    break;

                case 2:
                    System.out.print("Enter title to borrow: ");
                    String borrowTitle = scanner.nextLine();
                    if (library.issueBook(library.getCurrentUser().getUsername(), borrowTitle)) {
                        System.out.println("Book borrowed successfully!");
                    } else {
                        System.out.println("Unable to borrow book. Either this book is unavailable or you've reached your borrowing limit!");
                    }
                    break;

                case 3:
                    System.out.print("Enter title to return: ");
                    String returnTitle = scanner.nextLine();
                    library.returnBook(library.getCurrentUser().getUsername(), returnTitle);
                    System.out.println("Book returned successfully!");
                    break;

                case 4:
                    library.currentUser = null;
                    System.out.println("Logged out successfully.");
                    return;
            }
        }
    }
}

