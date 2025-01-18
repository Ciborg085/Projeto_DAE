package pt.ipleiria.estg.dei.ei.dae.backend.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.backend.dtos.*;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.OrderBean;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.ProductBean;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Volume;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.IllegalArgumentException;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.backend.security.Authenticated;

import java.util.List;
import java.util.stream.Collectors;

//TODO
@Path("/loja/encomendas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
public class OrderService {

    @EJB
    private OrderBean orderBean;

    @EJB
    private ProductBean productBean;

    @GET
    @Path("/")
    public Response getAllOrdersWithProducts() {
        List<Order> orders = orderBean.findAllWithProducts();

        List<OrderWithProductsDTO> orderWithProductsDTOS = orders.stream().map(order -> {
            List<ProductSummaryDTO> productSummaryDTOS = ProductSummaryDTO.from(order.getProducts());

            return new OrderWithProductsDTO(
                    order.getId(),
                    order.getClient().getUsername(),
                    order.getOrder_status().toString(),
                    order.getDestination(),
                    productSummaryDTOS
            );
        }).collect(Collectors.toList());

        return Response.ok(orderWithProductsDTOS).build();
    }

    @GET
    @Path("/{order_id}")
    public Response getOrderById(@PathParam("order_id") long order_id)
            throws MyEntityNotFoundException {
        Order order = orderBean.findWithProducts(order_id);

        List<ProductSummaryDTO> productSummaryDTOS = ProductSummaryDTO.from(order.getProducts());
        OrderWithProductsDTO order_DTO = new OrderWithProductsDTO(
                order.getId(),
                order.getClient().getUsername(),
                order.getOrder_status().toString(),
                order.getDestination(),
                productSummaryDTOS
        );

        return Response.status(Response.Status.OK)
                .entity(order_DTO)
                .build();
    }

    // TODO
    @GET
    @Path("/{order_id}/volumes")
    public void getOrderWithVolumes(@PathParam("order_id") long order_id) {

    }
    // TODO
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

    @PATCH
    @Path("/{order_id}")
    public Response updateStatus(@PathParam("order_id") long order_id, OrderUpdateStatusDTO orderUpdateStatusDTO) throws MyEntityNotFoundException, IllegalArgumentException {
        orderBean.updateStatus(order_id,orderUpdateStatusDTO.getStatus());
        Order order =orderBean.find(order_id);
        OrderWithoutProductsDTO orderDTO =  OrderWithoutProductsDTO.from(order);
        return Response.ok(orderDTO).build();
    }
}
