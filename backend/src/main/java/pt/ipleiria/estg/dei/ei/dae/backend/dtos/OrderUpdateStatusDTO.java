package pt.ipleiria.estg.dei.ei.dae.backend.dtos;

public class OrderUpdateStatusDTO {
    private String status;

    public OrderUpdateStatusDTO() {
    }

    public OrderUpdateStatusDTO(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
