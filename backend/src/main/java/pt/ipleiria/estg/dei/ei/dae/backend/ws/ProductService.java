package pt.ipleiria.estg.dei.ei.dae.backend.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.backend.dtos.ProductFullDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.ProductBean;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityNotFoundException;

import java.util.List;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductService {
    @EJB
    private ProductBean productBean;

//    @GET
//    @Path("/")
//    public List<ProductDTO> getProducts(){
////        return ProductDTO.from(productBean.findAll());
//    }
    @GET
    @Path("/")
    public List<ProductFullDTO> getProducts(){
            return ProductFullDTO.from(productBean.findAll());
    }

    @GET
    @Path("{id}")
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
