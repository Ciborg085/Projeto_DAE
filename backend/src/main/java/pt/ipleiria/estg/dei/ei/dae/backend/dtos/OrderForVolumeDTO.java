package pt.ipleiria.estg.dei.ei.dae.backend.dtos;

import java.util.List;

public class OrderForVolumeDTO {
    private long order_id;
    private String client_username;
    private List<ProductFullDTO> products;
    private String destination;

    public OrderForVolumeDTO() {
    }

    public OrderForVolumeDTO(long order_id, String client_username, List<ProductFullDTO> products, String destination) {
        this.order_id = order_id;
        this.client_username = client_username;
        this.products = products;
        this.destination = destination;
    }

    public String getClient_username() {
        return client_username;
    }

    public void setClient_username(String client_username) {
        this.client_username = client_username;
    }

    public long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(long order_id) {
        this.order_id = order_id;
    }

    public List<ProductFullDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductFullDTO> products) {
        this.products = products;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

}
