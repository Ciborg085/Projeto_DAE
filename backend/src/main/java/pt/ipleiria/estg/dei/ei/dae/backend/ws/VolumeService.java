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
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

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
    public List<VolumeDTO> getAllVolumes() {
        return new ArrayList<>();
    }

    // TODO
    @Path("/")
    @POST
    public Response createVolume(VolumeCreatePostDTO volumeCreatePostDTO) throws
            MyEntityExistsException, MyEntityNotFoundException
    {
        if (volumeCreatePostDTO.getOrder_id().isEmpty() && volumeCreatePostDTO.getOrder().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        }

        if (volumeCreatePostDTO.getOrder().isPresent()) {
            /*
            - Verificar se a encomenda já existe
                - Se sim, erro
                - Se não, criar a encomenda
             */
            // create order
            OrderForVolumeDTO orderDTO = volumeCreatePostDTO.getOrder().get();
            orderBean.create(
                    orderDTO.getOrder_id(),
                    orderDTO.getClient_username(),
                    orderDTO.getDestination()
            );
            Order order = orderBean.find(orderDTO.getOrder_id());

            for (ProductFullDTO productFullDTO : orderDTO.getProducts()) {
                productBean.create(
                        productFullDTO.getId(),
                        productFullDTO.getName(),
                        productFullDTO.getBrand(),
                        productFullDTO.getPrice(),
                        productFullDTO.getCategory(),
                        productFullDTO.getMaxQuantityPerVolume(),
                        productFullDTO.getTypeOfPackage(),
                        order.getId()
                );
            };

        }
        Order order = orderBean.find(volumeCreatePostDTO.getOrder_id().get());

        // TODO criar volume

        volumeBean.create(
                volumeCreatePostDTO.getVolume_id(),
                order.getId(),
                volumeCreatePostDTO.getProduct_id(),
                volumeCreatePostDTO.getQuantity()
        );

        Volume volume = volumeBean.find(volumeCreatePostDTO.getVolume_id());

        // TODO criar e adicionar sensores
        for (SensorForVolumeDTO sensorForVolumeDTO : volumeCreatePostDTO.getSensors()) {
            sensorBean.create(
                    sensorForVolumeDTO.getId(),
                    volume.getId(),
                    sensorForVolumeDTO.getType()
            );
        }



        return Response.ok().build();
    }

}
