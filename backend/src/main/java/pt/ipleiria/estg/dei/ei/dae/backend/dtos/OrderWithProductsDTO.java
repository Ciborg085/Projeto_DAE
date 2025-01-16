package pt.ipleiria.estg.dei.ei.dae.backend.dtos;

import jakarta.json.bind.annotation.JsonbProperty;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Order;

import java.util.List;
import java.util.stream.Collectors;

// TODO
/*
    Possivel que isto nem seja preciso...
 */
public class OrderWithProductsDTO {

    private long id;
    /*
    isto n funciona for some reason :|

    Erro:
        Explicit usage of Jackson annotation in a Jakarta RESTful Web Services deployment; the system will disable Jakarta JSON Binding processing for the current deployment.
        Consider setting the 'resteasy.preferJacksonOverJsonB' property to 'false' to restore Jakarta JSON Binding.

     Estranho porque eu meti este setting que estão a pedir :|
     */
    @JsonbProperty("client_username")
    private String clientUsername;

    @JsonbProperty("order_status")
    private String orderStatus;
    private String destination;
    private List<ProductSummaryDTO> products;

    public OrderWithProductsDTO() {
    }

    public OrderWithProductsDTO(long id, String clientUsername,String orderStatus, String destination, List<ProductSummaryDTO> products) {
        this.id = id;
        this.clientUsername = clientUsername;
        this.destination = destination;
        this.orderStatus = orderStatus;
        this.products = products;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getClientUsername() {
        return clientUsername;
    }

    public void setClientUsername(String clientUsername) {
        this.clientUsername = clientUsername;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public List<ProductSummaryDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductSummaryDTO> products) {
        this.products = products;
    }

   // public static OrderWithProductsDTO from(Order order) {
   //     return new OrderWithProductsDTO(
   //             order.getId(),
   //             order.getClient().getUsername(),
   //             order.getDestination(),
   //             ProductSummaryDTO.from(order.getProducts())
   //     );
   // }
   // public static List<OrderWithProductsDTO> from(List<Order> orders) {
   //     return orders.stream().map(OrderWithProductsDTO::from).collect(Collectors.toList());
   // }
}
