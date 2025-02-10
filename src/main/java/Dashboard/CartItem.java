package Dashboard;

public class CartItem {
    private final String brandName;
    private final String productName;
    private int quantity;
    private final double price;

    public CartItem(String brandName, String productName, int quantity, double price) {
        if (brandName == null || brandName.isEmpty()) {
            throw new IllegalArgumentException("Brand name cannot be empty");
        }
        if (productName == null || productName.isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }

        this.brandName = brandName;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public double getTotalPrice() {
        return quantity * price;
    }

    public void setQuantity(int newQuantity) {
        if (newQuantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        this.quantity = newQuantity;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "brandName='" + brandName + '\'' +
                ", productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", total=" + getTotalPrice() +
                '}';
    }
}
