package pt.ipleiria.estg.dei.ei.dae.backend.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.backend.dtos.OrderDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.dtos.OrderForVolumeDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.dtos.ProductFullDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.OrderBean;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.ProductBean;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

//TODO
@Path("/loja/encomendas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderService {

    @EJB
    private OrderBean orderBean;

    @EJB
    private ProductBean productBean;

    @GET
    @Path("/")
    public List<OrderDTO> getOrders() {
        //TODO
        return new ArrayList<>();
    }

    @GET
    @Path("{order_id}")
    public Response getOrder(@PathParam("order_id") long order_id)
            throws MyEntityNotFoundException {
        Order order = orderBean.find(order_id);
        return Response.status(Response.Status.OK)
                .build();
    }

    @GET
    @Path("/{order_id}/volumes")
    public void getOrderWithVolumes(@PathParam("order_id") long order_id) {

    }
    @GET
    @Path("/{order_id}/products")
    public void getOrderWithProducts(@PathParam("order_id") long order_id) {

    }



    @POST
    @Path("/")
    public Response createOrder(OrderForVolumeDTO orderDTO)
            throws MyEntityNotFoundException, MyEntityExistsException {
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
                    productFullDTO.getQuantityOrdered(),
                    productFullDTO.getCategory(),
                    productFullDTO.getMaxQuantityPerVolume(),
                    productFullDTO.getTypeOfPackage(),
                    order.getId()
            );
        };

        return Response.status(Response.Status.OK)
                .build();
    }
}
