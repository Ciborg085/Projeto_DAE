package pt.ipleiria.estg.dei.ei.dae.backend.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import pt.ipleiria.estg.dei.ei.dae.backend.dtos.*;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.OrderBean;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.ProductBean;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.UserBean;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.VolumeBean;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.*;
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

    @EJB
    private VolumeBean volumeBean;

    @EJB
    private UserBean userBean;

    @Context
    private SecurityContext securityContext;

    @GET
    @Path("/")
    public Response getAllOrdersWithProducts() throws MyEntityNotFoundException {
        var principal = securityContext.getUserPrincipal();
        User user = userBean.find(principal.getName());
        List<Order> orders = null;
        if (user.getRole().equals("Administrator")) {
            orders = orderBean.findAllWithProducts();

        } else if (user.getRole().equals("Client")) {
            Client client = (Client) user;
            orders = orderBean.findAllWithProductsWhereUsername(user.getUsername());

        }
        if (orders == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Role not found in token, role: '"+user.getRole()+"'")
                    .build();
        }
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

    @GET
    @Path("/{order_id}/volumes")
    public Response getOrderVolumes(@PathParam("order_id") long order_id) throws MyEntityNotFoundException, IllegalArgumentException {
        List<Volume> volumes = orderBean.findVolumesFromOrder(order_id);
        Order order = orderBean.find(order_id);
        if (volumes == null ||  volumes.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        OrderVolumesGetDTO orderVolumesGetDTO = new OrderVolumesGetDTO();

        orderVolumesGetDTO.setOrder_id(order_id);
        orderVolumesGetDTO.setClient_name(order.getClient().getName());

        List<VolumeGetDTO> volumeGetDTOS = volumes.stream().map(volume -> {
            return new VolumeGetDTO(
                    volume.getId(),
                    volume.getOrder().getId(),
                    SensorDTO.from(volume.getSensors()),
                    ProductSummaryDTO.from(volume.getProduct()),
                    volume.getQuantity(),
                    volume.getVolume_status()
            );
        }).collect(Collectors.toList());

        orderVolumesGetDTO.setVolumes(volumeGetDTOS);

        return Response.ok(orderVolumesGetDTO).build();
    }

    // TODO - not really needed, getOrderById already gives products
    @GET
    @Path("/{order_id}/products")
    public void getOrderProducts(@PathParam("order_id") long order_id) {
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
