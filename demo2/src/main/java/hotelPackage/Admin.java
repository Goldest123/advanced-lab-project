package hotelPackage;

public class Admin extends User implements displayable {
    public Admin(String name, String email) {
        super(name, email);
    }

    public void displayInfo() {
        System.out.println("Admin: " + name + ", Email: " + email);
    }

}
