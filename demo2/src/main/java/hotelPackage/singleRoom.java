package hotelPackage;

public class singleRoom extends Room implements displayable{

    public singleRoom(int roomNumber, double pricePerNight) {
        super(roomNumber, pricePerNight);
    }

    public void displayInfo() {
        System.out.println( "singleRoom{" +
                "roomNumber=" + roomNumber +
                ", pricePerNight=" + pricePerNight +
                ", isAvailable=" + isAvailable +
                '}');
    }

    @Override
    public String getRoomType() {
        return "Single Room";
    }
    @Override
    public double calculateCost(int nights) {
        return pricePerNight * nights;
    }
}

