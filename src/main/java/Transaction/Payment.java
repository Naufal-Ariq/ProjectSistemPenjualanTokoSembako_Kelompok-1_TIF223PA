package Transaction;

import java.time.LocalDateTime;

public class Payment {
    private int orderId;                // ID dari pesanan
    private double amount;              // Jumlah pembayaran
    private String paymentstatus;              // Status pembayaran (misalnya: "Pending", "Completed")
    private LocalDateTime paymentDate;  // Tanggal dan waktu pembayaran

    // Konstruktor untuk kelas Payment
    public Payment(int orderId, double amount, String paymentstatus, LocalDateTime paymentDate) {
        this.orderId = orderId;
        this.amount = amount;
        this.paymentstatus = paymentstatus;
        this.paymentDate = paymentDate;
    }

    // Getter untuk orderId
    public int getOrderId() {
        return orderId;
    }

    // Setter untuk orderId
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    // Getter untuk amount
    public double getAmount() {
        return amount;
    }

    // Setter untuk amount
    public void setAmount(double amount) {
        this.amount = amount;
    }

    // Getter untuk status
    public String getpaymentStatus() {
        return paymentstatus;
    }

    // Setter untuk status
    public void setpaymentStatus(String paymentstatus) {
        this.paymentstatus = paymentstatus;
    }

    // Getter untuk paymentDate
    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    // Setter untuk paymentDate
    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    // Metode untuk menampilkan informasi pembayaran
    @Override
    public String toString() {
        return "Payment{" +
                "orderId=" + orderId +
                ", amount=" + amount +
                ", paymentstatus='" + paymentstatus + '\'' +
                ", paymentDate=" + paymentDate +
                '}';
    }
}
