# Final Proyek Pemrograman Berorientasi Obyek 1
<ul>
  <li>Mata Kuliah: Pemrograman Berorientasi Obyek 1</li>
  <li>Dosen Pengampu: <a href="https://github.com/Muhammad-Ikhwan-Fathulloh">Muhammad Ikhwan Fathulloh</a></li>
</ul>

## Kelompok
<ul>
  <li>Kelompok: {1}</li>
  <li>Proyek: {Sistem Penjualan Toko Sembako}</li>
  <li>Anggota:</li>
  <ul>
    <li>Ketua: <a href="">Naufal Ariq Fauziy</a></li>
    <li>Anggota 1: <a href="">Arrafi Hilmi</a></li>
    <li>Anggota 2: <a href="">Rendra Hairul Assyfa</a></li>
  </ul>
</ul>

## Judul Studi Kasus
<p>Sistem Penjualan Toko Sembako.</p>

## Penjelasan Studi Kasus
<p>Sistem yang dikembangkan adalah aplikasi berbasis e-commerce untuk pengelolaan penjualan dan stok barang pada toko sembako. Aplikasi ini dirancang untuk membantu pemilik toko dalam mengelola transaksi penjualan, dan pengendalian stok barang. Aplikasi ini bertujuan untuk meningkatkan efisiensi operasional toko sembako dengan menggantikan sistem pencatatan manual yang sering kali rentan terhadap kesalahan dan keterlambatan.Aplikasi ini memungkinkan pemilik toko untuk melakukan pencatatan transaksi penjualan secara otomatis dan real-time. Selain itu, sistem ini juga menyediakan fitur untuk memantau stok barang yang tersedia di toko.</p>

## Penjelasan 4 Pilar OOP dalam Studi Kasus

### 1. Inheritance
<p> Digunakan untuk mengelompokkan sifat atau fungsi umum dari entitas, seperti "Barang" atau "Transaksi," yang dapat diturunkan ke kelas lebih spesifik.</p>

### AdminAccount.Java

'''java

    public class AdminAccount extends User {
      private String role;

    public AdminAccount(int id, String username, String password, Timestamp createdAt, String email, String role) {
        super(id, username, password, createdAt, email);
        this.role = role;
    }

      // Getter dan Setter untuk role
      public String getRole() {
          return role;
      }
  
      public void setRole(String role) {
          this.role = role;
      }
  
      @Override
      public void displayInfo() {
          System.out.println("Admin Account - ID: " + getId() + ", Username: " + getUsername() + ", Role: " + role);
      }
    }
    
'''

### Customer.Java

    public class Customer extends User {
    private String name;
    private String phone;
    private String address;

    public Customer(int id, String username, String password, Timestamp createdAt, String email, String name, String phone, String address) {
        super(id, username, password, createdAt, email);
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    // Getter dan Setter untuk name, phone, dan address
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public void displayInfo() {
        System.out.println("Customer - ID: " + getId() + ", Username: " + getUsername() + ", Name: " + name);
    }
    }


### 2. Encapsulation
Data penting, seperti harga atau stok barang, dilindungi melalui atribut privat dan hanya bisa diakses melalui metode tertentu
   
  ### User.java
        private int id;
        private String username;
        private String password;
        private Timestamp createdAt;
        private String email;
        
        public int getId() {
            return id;
        }
        
        public void setId(int id) {
            this.id = id;
        }
        
        // Getter dan Setter lainnya


### 3. Polymorphism
<p>Berbagai metode yang memiliki nama sama namun perilaku berbeda diterapkan, misalnya metode untuk menampilkan laporan dalam format yang berbeda.</p>

    @Override
        public void displayInfo() {
            System.out.println("Admin Account - ID: " + getId() + ", Username: " + getUsername() + ", Role: " + role);
        }
    }
    
     @Override
        public void displayInfo() {
            System.out.println("Customer - ID: " + getId() + ", Username: " + getUsername() + ", Name: " + name);
        }
    }
        
### 4. Abstract
<p>Fokus pada fitur utama sistem dengan menyembunyikan detail implementasi yang kompleks agar sistem mudah dipahami.</p>

  ###User.Java
    
    public abstract class User {
    private int id;
    private String username;
    private String password;
    private Timestamp createdAt;
    private String email;

    public User(int id, String username, String password, Timestamp createdAt, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.createdAt = createdAt;
        this.email = email;
    }

    // Getter dan Setter

    // Metode abstrak
    public abstract void displayInfo();
    }

  

## Struktur Tabel Aplikasi
[Tabel Database] ![Screenshot 2025-02-11 174155](https://github.com/user-attachments/assets/7e714929-5858-451f-bf2d-09469bd264f7)

## Tampilan Aplikasi
 ![](https://github.com/user-attachments/assets/b7e7f37d-b2d1-4fdd-9f2a-7f94df5bcd71)

 ![](https://github.com/user-attachments/assets/34a58889-3234-4f05-9453-62d49b980230)

[Tampilan Admin Dashboard] ![](https://github.com/user-attachments/assets/a00be17a-143a-4729-bf86-becc6eec9e33)

[Tampilan Admin Dashboard Fitur Tambah Produk] ![](https://github.com/user-attachments/assets/6364510b-adc7-42a7-b61f-63c82033e77f)

![](https://github.com/user-attachments/assets/94b7b0fc-68eb-4144-ba2b-cc9741bdf399)

[Tampilan Admin Dasboard Fitur Akun admin] ![](https://github.com/user-attachments/assets/8f673376-761e-4826-ac8f-f514e03c9507)

[Tampilan Customer Portal Fitur Purchase] ![](https://github.com/user-attachments/assets/1af3176c-9998-4883-9939-4812cf4dbd87)

![](https://github.com/user-attachments/assets/13e08529-c3d9-41b5-89d8-e29d1f265517)

## Demo Proyek
<ul>
  <li>Github: <a href="https://github.com/Naufal-Ariq/ProjectSistemPenjualanTokoSembako_Kelompok-1_TIF223PA">Github</a></li>
  <li>Youtube: <a href="">Youtube</a></li>
</ul>
