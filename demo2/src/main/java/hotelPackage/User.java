package hotelPackage;

public abstract class User implements displayable{
    protected String name;
    protected String email;
    protected String password;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public abstract void displayInfo();

    public Object getPassword() {
        return password;
    }
}
