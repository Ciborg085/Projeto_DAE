package pt.ipleiria.estg.dei.ei.dae.backend.dtos;

import pt.ipleiria.estg.dei.ei.dae.backend.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.Sensor;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class SensorForVolumeDTO implements Serializable {
    private long id;
    private String type;

    public SensorForVolumeDTO() {
    }

    public SensorForVolumeDTO(long id, String type) {
        this.id = id;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

   // public static ProductSummaryDTO from(Product product) {
   //     return new ProductSummaryDTO(
   //             product.getId(),
   //             product.getName(),
   //             product.getBrand(),
   //             product.getCategory(),
   //             product.getQuantityOrdered()
   //     );
   // }
   // public static List<ProductSummaryDTO> from(List<Product> products) {
   //     return products.stream().map(ProductSummaryDTO::from).collect(Collectors.toList());
   // }

    // public static SensorForVolumeDTO from(Sensor sensor) {
    //    return new SensorForVolumeDTO(
    //            sensor.getId(),
    //            sensor.getClass()
    //    );
    //}


}
