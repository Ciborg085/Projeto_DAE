package pt.ipleiria.estg.dei.ei.dae.backend.dtos;

import jakarta.json.bind.annotation.JsonbProperty;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Order;

import java.util.List;
import java.util.stream.Collectors;

public class OrderWithoutProductsDTO {

    private long id;
    @JsonbProperty("client_username")
    private String clientUsername;

    @JsonbProperty("order_status")
    private String orderStatus;
    private String destination;

    public OrderWithoutProductsDTO() {
    }

    public OrderWithoutProductsDTO(long id, String clientUsername, String orderStatus, String destination) {
        this.id = id;
        this.clientUsername = clientUsername;
        this.destination = destination;
        this.orderStatus = orderStatus;
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

     public static OrderWithoutProductsDTO from(Order order) {
         return new OrderWithoutProductsDTO(
                 order.getId(),
                 order.getClient().getUsername(),
                 order.getOrder_status().getStatus(),
                 order.getDestination()
         );
     }
     public static List<OrderWithoutProductsDTO> from(List<Order> orders) {
         return orders.stream().map(OrderWithoutProductsDTO::from).collect(Collectors.toList());
     }
}
