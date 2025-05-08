package hotelPackage;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User implements displayable {
    private final List<Booking> bookings = new ArrayList<>();

    public Customer(String name, String email) {
        super(name, email);
    }

    public void displayInfo() {
        System.out.println("Customer: " + name + ", Email: " + email);
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public List<Booking> getBookings() {
        return bookings;
    }


}
