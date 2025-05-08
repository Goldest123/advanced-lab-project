package hotelPackage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Hotel {
    public List<Room> getRooms() {
        return rooms;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    private List<Room> rooms;
    private List<Booking> bookings = new ArrayList<>();

    public Hotel() {
        rooms = new ArrayList<>();
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public void showAllRooms() {
        for (Room room : rooms) {
            System.out.println(room);
        }
    }

    public void sortRoomsByPrice() {
        Collections.sort(rooms);
    }
    public void bookRoom(int roomNumber) {
        for (Room room : rooms) {
            if ((room.roomNumber == roomNumber)&& room.isAvailable()) {
                room.book();
                System.out.println("Room " + roomNumber + " booked successfully.");
                return;
            }
        }
        System.out.println("Room " + roomNumber + " is not available.");
    }
    public boolean bookRoom(Room room, Customer customer, int nights) {
        if (room.isAvailable()) {
            Booking booking = new Booking(room, customer, nights);
            bookings.add(booking);
            customer.addBooking(booking);
            room.book(); // Mark room as unavailable
            return true;
        }
        return false;
    }


    public static void main(String[] args) {
            // Create hotel instance
            Hotel hotel = new Hotel();

            // Create room instances and add them to the hotel
            Room singleRoom = new singleRoom(101, 100.0);
            Room doubleRoom = new doubleRoom(102, 150.0);
            Room suite = new Suite(103, 250.0, true);

            hotel.addRoom(singleRoom);
            hotel.addRoom(doubleRoom);
            hotel.addRoom(suite);

            // Create a customer and an admin
            User customer = new Customer("John Doe", "john.doe@example.com");
            User admin = new Admin("Jane Smith", "jane.smith@example.com");

            // Display customer and admin info
            customer.displayInfo();
            admin.displayInfo();

            // Make a booking for the customer
            System.out.println("\nBooking a room for customer...");
            boolean isBooked = hotel.bookRoom(doubleRoom, (Customer) customer, 3); // 3 nights
            if (isBooked)
                System.out.println("Room booked successfully.");
             else
                System.out.println("Booking failed. Room might be unavailable.");
        }

    public List<Room> getRoomsByType(String roomType) {
        return rooms.stream()
                .filter(room -> room.getRoomType().equalsIgnoreCase(roomType))
                .collect(Collectors.toList());
    }

    public List<Room> getRoomsByPriceRange(double minPrice, double maxPrice) {
        return rooms.stream()
                .filter(room -> room.getPricePerNight() >= minPrice && room.getPricePerNight() <= maxPrice)
                .collect(Collectors.toList());
    }

}

