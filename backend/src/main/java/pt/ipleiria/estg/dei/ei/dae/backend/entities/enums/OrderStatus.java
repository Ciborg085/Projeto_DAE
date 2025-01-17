package pt.ipleiria.estg.dei.ei.dae.backend.entities.enums;

// Enum para definir los estados del pedido
public enum OrderStatus {
    CREATED("created"),
    PROCESSING("processing"),
    SHIPPED("shipped"),
    DELIVERED("delivered"),
    CANCELED("canceled");

    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static OrderStatus fromString(String status) {
        for (OrderStatus orderStatus :  OrderStatus.values()) {
            if (orderStatus.status.equals(status)) {
                return orderStatus;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + status);
    }
}
