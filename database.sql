create table Admin_Account
id           int
username     varchar
password     varchar
role         varchar
created_at   timestamp
email        varchar

create table cart
id              int
customer_email  varchar
brand_name      varchar
product_name    varchar
quantity        int
price           decimal

create table customers
id     int 
name   varchar 
email  varchar
phone  int
address text

create table orders
order_id        int
customer_email  varchar 
total_price     double 
order_date      timestamp
stock           int

create table order_items
item_id   int
order_id  int
name      varchar
quantity  int
price     double
stok      int

create table payment
payment_id      int
order_id        int
amount          decimal
payment_date    datetamp
payment_status  varchar

create table product
id    int
name  varchar
price double
stock int

create table users
id         int
username   varchar
password   varchar
email      varchar
created_at time stamp