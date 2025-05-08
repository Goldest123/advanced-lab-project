package hotelPackage;

public class doubleRoom extends Room implements displayable {

    public doubleRoom(int roomNumber, double pricePerNight) {
        super(roomNumber, pricePerNight);
    }

    public void displayInfo() {
        System.out.println( "doubleRoom{" +
                "roomNumber=" + roomNumber +
                ", pricePerNight=" + pricePerNight +
                ", isAvailable=" + isAvailable +
                '}');
    }

    @Override
    public String getRoomType() {
        return "Double Room";
    }
    @Override
    public double calculateCost(int nights) {
        return pricePerNight * nights;
    }
}
