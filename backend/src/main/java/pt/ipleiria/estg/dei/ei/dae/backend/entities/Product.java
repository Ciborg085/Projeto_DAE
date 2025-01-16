package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "product")
@NamedQueries({
        @NamedQuery(
                name = "getAllProducts",
                query = "select p from Product p ORDER BY p.id ASC"
        )
})
public class Product {
    @Id
    private long id;
    @NotBlank
    private String name;
    @NotBlank
    private String brand;
    @NotNull
    private float price;
    @NotNull
    private int quantityOrdered;
    @NotNull
    private String category;
    @NotNull
    private int maxQuantityPerVolume;
    @NotBlank
    private String typeOfPackage;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Version
    private int version;

    public Product() {
    }

    public Product(long id, String name, String brand, float price,int quantityOrdered, String category, int maxQuantityPerVolume, String typeOfPackage, Order order) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.quantityOrdered = quantityOrdered;
        this.category = category;
        this.maxQuantityPerVolume = maxQuantityPerVolume;
        this.typeOfPackage = typeOfPackage;
        this.order = order;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantityOrdered() {
        return quantityOrdered;
    }

    public void setQuantityOrdered(int quantityOrdered) {
        this.quantityOrdered = quantityOrdered;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getMaxQuantityPerVolume() {
        return maxQuantityPerVolume;
    }

    public void setMaxQuantityPerVolume(int maxQuantityPerVolume) {
        this.maxQuantityPerVolume = maxQuantityPerVolume;
    }

    public String getTypeOfPackage() {
        return typeOfPackage;
    }

    public void setTypeOfPackage(String typeOfPackage) {
        this.typeOfPackage = typeOfPackage;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
