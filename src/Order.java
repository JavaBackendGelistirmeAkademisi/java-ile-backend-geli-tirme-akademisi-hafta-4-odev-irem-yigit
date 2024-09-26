
public class Order {
    private Customer customer;
    private Product product;
    private int quantity;

    public Order(Customer customer, Product product, int quantity) {
        this.customer = customer;
        this.product = product;
        this.quantity = quantity;
    }

    public void displayOrderInfo(){
        System.out.println("Customer name: " + customer.getName());
        System.out.println("Customer surname: " + customer.getSurname());
        System.out.println("Product name: " + product.getName());
        System.out.println("Quantity: " + quantity);
    }

}
