package pt.ipleiria.estg.dei.ei.dae.backend.dtos;

import pt.ipleiria.estg.dei.ei.dae.backend.entities.Product;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class ProductSummaryDTO implements Serializable {
    private long id;
    private String name;
    private String brand;
    private int quantity;

    public ProductSummaryDTO() {
    }

    public ProductSummaryDTO(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public static ProductSummaryDTO from(Product product) {
        return new ProductSummaryDTO(
            product.getId()
        );
    }
    public static List<ProductSummaryDTO> from(List<Product> products) {
        return products.stream().map(ProductSummaryDTO::from).collect(Collectors.toList());
    }


}
