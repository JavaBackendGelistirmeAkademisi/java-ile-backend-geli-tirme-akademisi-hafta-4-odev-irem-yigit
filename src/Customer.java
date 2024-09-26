public class Customer {
    private int id;
    private String name;
    private String surname;
    private String address;

    public Customer(int id, String name, String surname, String address) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void displayCustomerInfo(){
        System.out.println("Customer id: " + getId());
        System.out.println("Customer name: " + getName());
        System.out.println("Customer surname: " + getSurname());
        System.out.println("Customer address: " + getAddress());

    }
}
