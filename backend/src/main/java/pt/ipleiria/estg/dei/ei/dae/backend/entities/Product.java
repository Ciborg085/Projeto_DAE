package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllProducts",
                query = "select p from Product p ORDER BY p.id ASC"
        )
})
public class Product {
    @Id
    private long id;
    @NotNull
    private String name;
    @NotNull
    private String brand;
    @NotNull
    private float price;
    @NotNull
    private int maxQuantityPerVolume;
    private String typeOfPackage;

    @Version
    private int version;

    public Product() {
    }

    public Product(String name, String brand, float price, int maxQuantityPerVolume, String typeOfPackage) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.maxQuantityPerVolume = maxQuantityPerVolume;
        this.typeOfPackage = typeOfPackage;
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
}
