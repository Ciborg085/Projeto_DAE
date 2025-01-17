package pt.ipleiria.estg.dei.ei.dae.backend.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.backend.dtos.*;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.OrderBean;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.ProductBean;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.SensorBean;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.VolumeBean;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Volume;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.IllegalArgumentException;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// TODO
@Path("loja/volumes")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class VolumeService {

    @EJB
    private VolumeBean volumeBean;

    @EJB
    private OrderBean orderBean;

    @EJB
    private ProductBean productBean;

    @EJB
    private SensorBean sensorBean;

    // TODO
    @Path("/")
    @GET
    public Response getAllVolumes() {
        List<Volume> volumes = volumeBean.findAllComplete();

        List<VolumeGetDTO> volumeGetDTOS = volumes.stream().map( volume -> {
            List<SensorDTO> sensorDTOS = SensorDTO.from(volume.getSensors());

            return new VolumeGetDTO(
                    volume.getId(),
                    volume.getOrder().getId(),
                    sensorDTOS,
                    ProductSummaryDTO.from(volume.getProduct()),
                    volume.getQuantity(),
                    volume.getVolume_status()
            );
        }).collect(Collectors.toList());

        return Response.ok(volumeGetDTOS).build();
    }

    @Path("/{volume_id}")
    @GET
    public Response getVolumeById(@PathParam("volume_id") long volume_id) throws MyEntityNotFoundException {
        Volume volume = volumeBean.findComplete(volume_id);

        VolumeGetDTO volumeGetDTO = new VolumeGetDTO(
                volume.getId(),
                volume.getOrder().getId(),
                SensorDTO.from(volume.getSensors()),
                ProductSummaryDTO.from(volume.getProduct()),
                volume.getQuantity(),
                volume.getVolume_status()
        );

        return Response.ok(volumeGetDTO).build();
    }

    @Path("/")
    @POST
    public Response createVolume(VolumeCreatePostDTO volumeCreatePostDTO) throws
            MyEntityExistsException, MyEntityNotFoundException, IllegalArgumentException
    {
        if (volumeCreatePostDTO.getOrder_id() == 0 && volumeCreatePostDTO.getOrder() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Order id or order object are required.")
                    .build();
        }

        if (volumeCreatePostDTO.getOrder_id() != 0 && volumeCreatePostDTO.getOrder() != null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Only order_id or order are required.")
                    .build();
        }

        Order order = null;
        if (volumeCreatePostDTO.getOrder() != null) {
            /*
            - Verificar se a encomenda já existe
                - Se sim, erro
                - Se não, criar a encomenda
             */
            // create order

            OrderForVolumeDTO orderDTO = volumeCreatePostDTO.getOrder();

            if (orderBean.exists(orderDTO.getOrder_id())) {
                throw new MyEntityExistsException("Order "+orderDTO.getOrder_id()+" already exists.");
            }

            orderBean.create(
                    orderDTO.getOrder_id(),
                    orderDTO.getClient_username(),
                    orderDTO.getDestination()
            );
            order = orderBean.find(orderDTO.getOrder_id());

            for (ProductFullDTO productFullDTO : orderDTO.getProducts()) {
                productBean.create(
                        productFullDTO.getId(),
                        productFullDTO.getName(),
                        productFullDTO.getBrand(),
                        productFullDTO.getPrice(),
                        productFullDTO.getQuantityOrdered(),
                        productFullDTO.getCategory(),
                        productFullDTO.getMaxQuantityPerVolume(),
                        productFullDTO.getTypeOfPackage(),
                        order.getId()
                );
            };

        }
        if (volumeCreatePostDTO.getOrder_id() != 0) {
            order = orderBean.find(volumeCreatePostDTO.getOrder_id());
        }

        if (order == null) {
            throw new RuntimeException("order is null");
        }

        System.out.println("Volume created: ID=" + volumeCreatePostDTO.getVolume_id()
                + ", Order_id=" + order.getId()
                + ", Product_id=" + volumeCreatePostDTO.getProduct_id()
                + ", Quantity=" + volumeCreatePostDTO.getQuantity());

        volumeBean.create(
                volumeCreatePostDTO.getVolume_id(),
                order.getId(),
                volumeCreatePostDTO.getProduct_id(),
                volumeCreatePostDTO.getQuantity()
        );

        Volume volume = volumeBean.find(volumeCreatePostDTO.getVolume_id());

        for (SensorForVolumeDTO sensorForVolumeDTO : volumeCreatePostDTO.getSensors()) {
            try {
                sensorBean.create(
                        sensorForVolumeDTO.getId(),
                        volume.getId(),
                        sensorForVolumeDTO.getType()
                );
            } catch (IllegalArgumentException ie) {
                throw new IllegalArgumentException("Sensor " +sensorForVolumeDTO.getId() +" has unknown type");
            }
        }

        return Response.ok("Volume "+volume.getId()+" created.").build();
    }

    @Path("/{volume_id}")
    @PATCH
    public Response updateVolume(@PathParam("volume_id") long id, VolumeUpdateStatusDTO volumeUpdateStatusDTO) throws MyEntityNotFoundException, IllegalArgumentException {
        volumeBean.updateStatus(id, volumeUpdateStatusDTO.getStatus());

        Volume volume = volumeBean.findComplete(id);

        VolumeGetDTO volumeGetDTO = new VolumeGetDTO(
                volume.getId(),
                volume.getOrder().getId(),
                SensorDTO.from(volume.getSensors()),
                ProductSummaryDTO.from(volume.getProduct()),
                volume.getQuantity(),
                volume.getVolume_status()
        );


        return  Response.ok(volumeGetDTO).build();
    }

}
