package hotelPackage;

public class Suite extends Room implements displayable {
    private boolean hasJacuzzi;

    public Suite(int roomNumber, double pricePerNight, boolean hasJacuzzi) {
        super(roomNumber, pricePerNight);
        this.hasJacuzzi = hasJacuzzi;
    }

    public boolean isHasJacuzzi() {
        return hasJacuzzi;
    }

    public void displayInfo() {
        System.out.println("Suite{" +
                "hasJacuzzi=" + hasJacuzzi +
                ", roomNumber=" + roomNumber +
                ", pricePerNight=" + pricePerNight +
                ", isAvailable=" + isAvailable +
                '}');
    }

    @Override
    public String getRoomType() {
        return "Suite";
    }

    @Override
    public double calculateCost(int nights) {
        return pricePerNight*nights;
    }
}