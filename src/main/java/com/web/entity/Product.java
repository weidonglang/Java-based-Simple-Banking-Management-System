package com.web.entity;

public class Product {
    private int id;            // id编号
    private String name;       // 产品名称
    private String description;// 产品描述
    private double price;      // 价格
    private double discount;   // 折扣（0~1，小数）
    private int stock;         // 库存
    private int rating;        // 评分（1~5）
    private String image;      // 产品图片（相对路径）

    public Product() {}

    public Product(int id, String name, String description,
                   double price, double discount, int stock, int rating, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.stock = stock;
        this.rating = rating;
        this.image = image;
    }

    // --- getter / setter 省略可由 IDE 生成 ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public double getDiscount() { return discount; }
    public void setDiscount(double discount) { this.discount = discount; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", discount=" + discount +
                ", stock=" + stock +
                ", rating=" + rating +
                ", image='" + image + '\'' +
                '}';
    }
}
