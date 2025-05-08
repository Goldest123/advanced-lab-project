package hotelPackage;

public class Booking {
    private final Room room;
    private Customer customer;
    private int nights;

    public Booking(Room room, Customer customer, int nights) {
        this.room = room;
        this.customer = customer;
        this.nights = nights;
    }

    public double getTotalCost() {
        return room.calculateCost(nights);
    }

    public Room getRoom() {
        return room;
    }
}
