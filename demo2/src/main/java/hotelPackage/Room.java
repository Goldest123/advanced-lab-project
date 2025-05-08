package hotelPackage;

public abstract class Room implements Comparable<Room> {
    protected int roomNumber;
    protected double pricePerNight;
    protected boolean isAvailable;

    public int getRoomNumber() {
        return roomNumber;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public abstract String getRoomType();

    public Room(int roomNumber, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.pricePerNight = pricePerNight;
        this.isAvailable = true;
    }

    public void book() { isAvailable = false; }
    public void release() { isAvailable = true; }

    public boolean isAvailable() { return isAvailable; }

    @Override
    public int compareTo(Room other) {
        return Double.compare(this.pricePerNight, other.pricePerNight);
    }

    public abstract double calculateCost(int nights);
}
