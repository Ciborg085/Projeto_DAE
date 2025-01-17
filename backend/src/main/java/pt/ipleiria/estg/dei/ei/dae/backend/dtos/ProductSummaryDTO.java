package pt.ipleiria.estg.dei.ei.dae.backend.dtos;

import pt.ipleiria.estg.dei.ei.dae.backend.entities.Product;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class ProductSummaryDTO implements Serializable {
    private long id;
    private String name;
    private String brand;

    private String category;
    private int quantityOrdered;

    public ProductSummaryDTO() {
    }

    public ProductSummaryDTO(long id,String name, String brand, String category, int quantityOrdered) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.quantityOrdered = quantityOrdered;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantityOrdered() {
        return quantityOrdered;
    }

    public void setQuantityOrdered(int quantityOrdered) {
        this.quantityOrdered = quantityOrdered;
    }

    public static ProductSummaryDTO from(Product product) {
        return new ProductSummaryDTO(
            product.getId(),
            product.getName(),
            product.getBrand(),
            product.getCategory(),
            product.getQuantityOrdered()
        );
    }
    public static List<ProductSummaryDTO> from(List<Product> products) {
        return products.stream().map(ProductSummaryDTO::from).collect(Collectors.toList());
    }


}
