package com.example.demo2;

import hotelPackage.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HotelBookingGUI extends JFrame {
    private final Hotel hotel = new Hotel();
    private final List<Customer> customers = new ArrayList<>();
    private Customer currentCustomer; // To track the logged-in customer
    private final JTextField nameField = new JTextField(15);
    private final JTextField emailField = new JTextField(15);
    private final JTextArea bookingOutput = new JTextArea(5, 30);
    private final JPanel roomPanel;
    private JComboBox<String> roomTypeFilter;
    private JSlider priceRangeSlider;

    public HotelBookingGUI() {
        setTitle("Bookib - Hotel Booking");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        initHotelRooms();

        // Left panel: Customer Info & Sign In/Sign Up/Sign Out
        JPanel leftPanel = createCustomerInfoPanel();
        add(leftPanel, BorderLayout.WEST);

        // Right panel: Rooms
        roomPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        roomPanel.setBorder(BorderFactory.createTitledBorder("Available Rooms"));
        JScrollPane scrollPane = new JScrollPane(roomPanel);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom panel: Booking Output and Search Filters
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bookingOutput.setEditable(false);
        bookingOutput.setBorder(BorderFactory.createTitledBorder("Booking Summary"));
        bottomPanel.add(new JScrollPane(bookingOutput), BorderLayout.CENTER);

        // Search Filters Panel
        JPanel filterPanel = createFilterPanel();
        bottomPanel.add(filterPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.SOUTH);

        pack();
        setSize(900, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initHotelRooms() {
        hotel.addRoom(new singleRoom(101, 100.0));
        hotel.addRoom(new doubleRoom(102, 150.0));
        hotel.addRoom(new Suite(103, 250.0, true));
        hotel.addRoom(new doubleRoom(104, 140.0));
        hotel.addRoom(new singleRoom(105, 90.0));
    }

    private JPanel createCustomerInfoPanel() {
        JPanel leftPanel = new JPanel(new GridLayout(7, 1, 5, 5));
        leftPanel.setBorder(BorderFactory.createTitledBorder("Customer Info"));

        leftPanel.add(new JLabel("Name:"));
        leftPanel.add(nameField);
        leftPanel.add(new JLabel("Email:"));
        leftPanel.add(emailField);

        JButton signUpBtn = new JButton("Sign Up");
        signUpBtn.addActionListener((ActionEvent e) -> signUp());

        JButton signInBtn = new JButton("Sign In");
        signInBtn.addActionListener((ActionEvent e) -> signIn());

        JButton signOutBtn = new JButton("Sign Out");
        signOutBtn.addActionListener((ActionEvent e) -> signOut());

        leftPanel.add(signUpBtn);
        leftPanel.add(signInBtn);
        leftPanel.add(signOutBtn);

        return leftPanel;
    }

    // Sign Up: Create a new customer
    private void signUp() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();

        if (name.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both name and email.");
            return;
        }

        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, "Invalid email address. Please provide a valid email.");
            return;
        }

        if (isCustomerExists(name, email)) {
            JOptionPane.showMessageDialog(this, "A customer with the same name and email already exists.");
        } else {
            currentCustomer = new Customer(name, email);
            customers.add(currentCustomer); // Add the customer to the list
            bookingOutput.append("Customer signed up: " + name + " <" + email + ">\n");
        }
    }

    // Sign In: Authenticate an existing customer
    private void signIn() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();

        if (name.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both name and email.");
            return;
        }

        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, "Invalid email address. Please provide a valid email.");
            return;
        }

        currentCustomer = findCustomer(name, email);

        if (currentCustomer != null) {
            bookingOutput.append("Customer signed in: " + currentCustomer.getName() + " <" + currentCustomer.getEmail() + ">\n");
        } else {
            JOptionPane.showMessageDialog(this, "No customer found with the provided name and email.");
        }
    }

    // Sign Out: Log out the current customer
    private void signOut() {
        if (currentCustomer != null) {
            bookingOutput.append("Customer signed out: " + currentCustomer.getName() + "\n");
            currentCustomer = null; // Log out
        } else {
            JOptionPane.showMessageDialog(this, "No customer is currently signed in.");
        }
    }

    // Helper method to check if customer exists
    private boolean isCustomerExists(String name, String email) {
        for (Customer c : customers) {
            if (c.getName().equals(name) && c.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    // Helper method to find a customer by name and email
    private Customer findCustomer(String name, String email) {
        for (Customer c : customers) {
            if (c.getName().equals(name) && c.getEmail().equals(email)) {
                return c;
            }
        }
        return null;
    }

    // Method to validate email format
    private boolean isValidEmail(String email) {
        // Regex to match a valid email format (not "null" or "null@gmail.com")
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches()) {
            return false;
        }

        // Check for "null" emails or emails like "null@gmail.com"
        if (email.toLowerCase().contains("null")) {
            return false;
        }

        return true;
    }

    private JPanel createFilterPanel() {
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Room Type Filter
        roomTypeFilter = new JComboBox<>(new String[]{"Any", "Single Room", "Double Room", "Suite"});
        roomTypeFilter.addActionListener(e -> renderRoomCards());
        filterPanel.add(new JLabel("Filter by Room Type:"));
        filterPanel.add(roomTypeFilter);

        // Price Range Filter
        priceRangeSlider = new JSlider(0, 500, 250);
        priceRangeSlider.setMajorTickSpacing(100);
        priceRangeSlider.setMinorTickSpacing(50);
        priceRangeSlider.setPaintTicks(true);
        priceRangeSlider.setPaintLabels(true);
        priceRangeSlider.addChangeListener(e -> renderRoomCards());
        filterPanel.add(new JLabel("Filter by Price:"));
        filterPanel.add(priceRangeSlider);

        return filterPanel;
    }

    private void renderRoomCards() {
        roomPanel.removeAll();
        String selectedRoomType = (String) roomTypeFilter.getSelectedItem();
        double maxPrice = priceRangeSlider.getValue();

        // Filter the rooms based on the selected criteria
        List<Room> filteredRooms = hotel.getRoomsByPriceRange(0, maxPrice);
        if (!selectedRoomType.equals("Any")) {
            filteredRooms = hotel.getRoomsByType(selectedRoomType);
        }

        // Display the filtered rooms
        for (Room room : filteredRooms) {
            JPanel card = createRoomCard(room);
            roomPanel.add(card);
        }

        roomPanel.revalidate();
        roomPanel.repaint();
    }

    private JPanel createRoomCard(Room room) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        card.setBackground(room.isAvailable() ? Color.WHITE : Color.LIGHT_GRAY);
        card.setPreferredSize(new Dimension(200, 120));

        JLabel typeLabel = new JLabel("Type: " + room.getRoomType());
        JLabel numberLabel = new JLabel("Room #: " + room.getRoomNumber());
        JLabel priceLabel = new JLabel("Price: $" + room.getPricePerNight());
        JLabel statusLabel = new JLabel("Status: " + (room.isAvailable() ? "Available" : "Booked"));

        JButton bookBtn = new JButton("Book Now");
        bookBtn.setEnabled(room.isAvailable());
        bookBtn.addActionListener(e -> {
            if (currentCustomer == null) {
                JOptionPane.showMessageDialog(this, "Please sign in first.");
                return;
            }
            boolean booked = hotel.bookRoom(room, currentCustomer, 1); // Assume 1 night
            if (booked) {
                bookingOutput.append("✅ Booked Room " + room.getRoomNumber() + " for " + currentCustomer.getName() + "\n");
                renderRoomCards(); // Refresh UI
            } else {
                bookingOutput.append("❌ Failed to book Room " + room.getRoomNumber() + "\n");
            }
        });

        card.add(typeLabel);
        card.add(numberLabel);
        card.add(priceLabel);
        card.add(statusLabel);
        card.add(bookBtn);

        return card;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HotelBookingGUI::new);
    }
}
