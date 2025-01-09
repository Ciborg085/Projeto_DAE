package pt.ipleiria.estg.dei.ei.dae.backend.dtos;

import java.util.List;

// TODO
/*
    Possivel que isto nem seja preciso...
 */
public class OrderDTO {
    private String client_id;
    private String destination;
    private String payment_method;
    private List<ProductSummaryDTO> products;

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public List<ProductSummaryDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductSummaryDTO> products) {
        this.products = products;
    }
}
