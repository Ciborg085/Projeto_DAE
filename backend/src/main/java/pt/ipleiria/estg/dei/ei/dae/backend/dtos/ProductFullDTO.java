package pt.ipleiria.estg.dei.ei.dae.backend.dtos;

import pt.ipleiria.estg.dei.ei.dae.backend.entities.Product;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class ProductFullDTO implements Serializable {
    private long id;
    private String name;
    private String brand;
    private float price;
    private String category;
    private int maxQuantityPerVolume;
    private String typeOfPackage;
    private long order_id;

    public ProductFullDTO() { }

    public ProductFullDTO(long id, String name, String brand, float price, String category, int maxQuantityPerVolume, String typeOfPackage, long order_id) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.category = category;
        this.maxQuantityPerVolume = maxQuantityPerVolume;
        this.typeOfPackage = typeOfPackage;
        this.order_id = order_id;
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

    public long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(long order_id) {
        this.order_id = order_id;
    }

    public static ProductFullDTO from(Product product) {
        return new ProductFullDTO(
                product.getId(),
                product.getName(),
                product.getBrand(),
                product.getPrice(),
                product.getCategory(),
                product.getMaxQuantityPerVolume(),
                product.getTypeOfPackage(),
                product.getOrder().getId()
        );
    }
    public static List<ProductFullDTO> from(List<Product> products) {
        return products.stream().map(ProductFullDTO::from).collect(Collectors.toList());
    }
}
