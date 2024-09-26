import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ECommerceApp {

    public static final String orderFilePath = "orders.txt";  // Siparişlerin saklanacağı dosya adı
    public static final String FILE_NAME = "products.txt";   // Ürünlerin saklanacağı dosya adı
    private static final String customerFilePath = "customers.txt";   // Müşterilerin saklanacağı dosya adı


    public static void main(String[] args) {

        Product laptop = new Product(1,"Laptop", 1000, 20);
        Product phone = new Product(2,"Phone",500,30);

        Scanner scanner = new Scanner(System.in);

        boolean continueProgram = true;

        while (continueProgram) {
            System.out.println("\nE- Ticaret sistemine hoşgeldiniz... Yapmak istediğiniz işlemi seçin:");
            System.out.println("1. Ürün Ekle");
            System.out.println("2. Ürünleri Listele");
            System.out.println("3. Ürün Güncelle");
            System.out.println("4. Müşterileri Listele");
            System.out.println("5. Düşük Stokları Kontrol Et");
            System.out.println("6. Sipariş oluştur");
            System.out.println("7 Çıkış");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    listProducts();
                    break;
                case 3:
                    updateProduct();
                    break;
                case 4:
                    listCustomers();
                    break;
                case 5:
                    checkLowStock();
                    break;
                case 6:
                    createOrder();
                    break;
                case 7:
                    continueProgram = false;
                    System.out.println("Sistemden çıkılıyor...");
                    break;
                default:
                    System.out.println("Geçersiz seçim! Lütfen tekrar deneyin.");
            }
        }

        // Ürün ekleme
        public void addProduct(Product product) {
            products.add(product);
            System.out.println("Product added: " + product);
        }

        // Dosyadaki mevcut ürünleri okuma ve listeleme metodu
        public void listProducts() throws ProductNotFoundException{
            ArrayList<String> products = readProductsFromFile();
            if (products.isEmpty()){
                throw new ProductNotFoundException("Ürün bulunamadı.");
            }
            else {
                System.out.println(" \n*** Ürünler ***");
                for (int i = 0; i < products.size(); i++){
                    System.out.println((i+1) + ". " + products.get(i));
                }
            }
        }


        // Belirli bir ürünü güncelleme metodu
        public void updateProducts(){
            ArrayList<String> products = readProductsFromFile();
        }

        // Dosyadaki ürünleri okuyan ve bir liste olarak dönen metot
        private ArrayList<String> readProductsFromFile(){
            ArrayList<String> products = new ArrayList<>();
            File file = new File(FILE_NAME);

            // Dosya yoksa, ürün listesi boş döner
            if (!file.exists()){
                System.out.println("Product dosyası bulunamadı. Yeni dosya oluşturulacak.");
                return products;
            }

            // Dosya varsa, ürünler okunuyor.
            try(BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))){
                String line;
                while ((line = reader.readLine()) != null){
                    products.add(line);     // Okunan satır product listesine eklenir.
                }
            } catch (IOException e) {
                System.out.println("Ürünler okunurken bir hata oluştu.");
                e.printStackTrace();
            }
            return products;
        }

        // Ürün listesini dosyaya yazan metot
        private void writeProductsToFile(ArrayList<String> products){
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))){
                for (String product : products ){
                    writer.write(product);
                    writer.newLine();
                }
            }catch (IOException e){
                System.out.println("Ürünler dosyaya yazılırken bir hata oluştu.");
                e.printStackTrace();
            }
        }

        // Sipariş oluşturma
        public void createOrder(int customerId, int productId, int quantity) {
            try {
                Customer customer = findCustomerById(customerId);
                Product product = findProductById(productId);
                if (product.getStock() < quantity) {
                    throw new IllegalArgumentException("Not enough stock for product: " + product.getName());
                }
                Order order = new Order(customer, product, quantity);
                orders.add(order);

                // Stok güncelle
                product.setStock(product.getStock() - quantity);

                // Siparişi dosyaya yaz
                writeOrderToFile(order);

                System.out.println("Sipariş oluşturuldu: " + order);
            } catch (Exception e) {
                System.out.println("Error creating order: " + e.getMessage());
            }
        }

        // Sipariş bilgilerini dosyaya yaz
        private void writeOrderToFile(Order order) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(orderFilePath, true))) {
                writer.write(order.toString());
                writer.newLine();
            } catch (IOException e) {
                System.out.println("Error writing to order file: " + e.getMessage());
            }
        }

        // Ürün bul
        private Product findProductById(int productId) throws Exception {
            for (Product product : products) {
                if (product.getId() == productId) {
                    return product;
                }
            }
            throw new Exception("Product not found with ID: " + productId);
        }

        // Müşteri bul
        private Customer findCustomerById(int customerId) throws Exception {
            for (Customer customer : customers) {
                if (customer.getId() == customerId) {
                    return customer;
                }
            }
            throw new Exception("Customer not found with ID: " + customerId);
        }

        // Ürünleri dosyadan oku
        public void loadProductsFromFile(String filePath) {
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    int stock = Integer.parseInt(parts[2]);
                    double price = Double.parseDouble(parts[3]);
                    Product product = new Product(id, name, price, stock);
                    addProduct(product);
                }
            } catch (IOException e) {
                System.out.println("Error reading product file: " + e.getMessage());
            }
        }

    }
}