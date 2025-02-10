package Dashboard;

public class Customer extends User {
    public Customer(int id, String name, String email, String phone, String address) {
        super(id, name, email);
    }

    @Override
    public void displayInfo() {
        System.out.println("Customer ID: " + getId());
        System.out.println("Name: " + getName());
        System.out.println("Email: " + getEmail());
    }
}
