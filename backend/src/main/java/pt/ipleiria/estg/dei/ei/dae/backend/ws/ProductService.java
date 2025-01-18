package pt.ipleiria.estg.dei.ei.dae.backend.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import pt.ipleiria.estg.dei.ei.dae.backend.dtos.ProductFullDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.ProductBean;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.UserBean;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.User;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.backend.security.Authenticated;

import java.util.List;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
public class ProductService {
    @EJB
    private ProductBean productBean;

    @EJB
    private UserBean userBean;

    @Context
    private SecurityContext securityContext;

//    @GET
//    @Path("/")
//    public List<ProductDTO> getProducts(){
////        return ProductDTO.from(productBean.findAll());
//    }
    @GET
    @Path("/")
    @RolesAllowed({"Administrator","Client"})
    public Response getProducts() throws MyEntityNotFoundException{
        var principal = securityContext.getUserPrincipal();
        User user = userBean.find(principal.getName());
        List<Product> products = null;

        if (user.getRole().equals("Administrator")) {
            products = productBean.findAll();
        } else if (user.getRole().equals("Client")) {
            products = productBean.findAll(user.getUsername());
        }

        if (products == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Role not found in token, role: '"+user.getRole()+"'")
                    .build();
        }

        return Response.ok(ProductFullDTO.from(products))
                .build();
    }

    @GET
    @Path("{id}")
    @RolesAllowed({"Administrator","Client"})
    public Response getProductById(@PathParam("id") long id) throws MyEntityNotFoundException {
        var product = productBean.find(id);
        var productDTO = ProductFullDTO.from(product);

        return Response.ok(productDTO).build();
    }


    /*
        Add a product to a order
     */
    @POST
    @Path("/")
    @RolesAllowed({"Administrator","Client"})
    public Response createNewProduct(ProductFullDTO productFullDTO) throws
            MyEntityExistsException,
            MyEntityNotFoundException {
        productBean.create(
                productFullDTO.getId(),
                productFullDTO.getName(),
                productFullDTO.getBrand(),
                productFullDTO.getPrice(),
                productFullDTO.getQuantityOrdered(),
                productFullDTO.getCategory(),
                productFullDTO.getMaxQuantityPerVolume(),
                productFullDTO.getTypeOfPackage(),
                productFullDTO.getOrder_id()
        );

        var product = productBean.find(productFullDTO.getId());
        return Response.status(Response.Status.CREATED).entity(productFullDTO.from(product)).build();
    }
}
