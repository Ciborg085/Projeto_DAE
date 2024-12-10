package pt.ipleiria.estg.dei.ei.dae.backend.dtos;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Product;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDTO implements Serializable {
    private long id;
    private String name;
    private String brand;
    private float price;
    private int maxQuantityPerVolume;
    private String typeOfPackage;

    public ProductDTO() { }

    public ProductDTO(long id,String name, String brand, float price, int maxQuantityPerVolume, String typeOfPackage) {
        this.id = id;
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

    public static ProductDTO from(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getBrand(),
                product.getPrice(),
                product.getMaxQuantityPerVolume(),
                product.getTypeOfPackage()
        );
    }
    public static List<ProductDTO> from(List<Product> products) {
        return products.stream().map(ProductDTO::from).collect(Collectors.toList());
    }
}
