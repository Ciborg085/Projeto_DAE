package pt.ipleiria.estg.dei.ei.dae.backend.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.backend.dtos.ProductDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.ProductBean;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityNotFoundException;

import java.util.List;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
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
    public List<ProductDTO> getProducts(){
            return ProductDTO.from(productBean.findAll());
    }

    @GET
    @Path("{id}")
    public Response getProductById(@PathParam("id") long id) throws MyEntityNotFoundException {
        var product = productBean.find(id);
        var productDTO = ProductDTO.from(product);

        return Response.ok(productDTO).build();
    }


    @POST
    @Path("/")
    public Response createNewProduct(ProductDTO productDTO) throws
            MyEntityExistsException,
            MyEntityNotFoundException {
        productBean.create(
                productDTO.getName(),
                productDTO.getBrand(),
                productDTO.getPrice(),
                productDTO.getMaxQuantityPerVolume(),
                productDTO.getTypeOfPackage()
        );

        var product = productBean.find(productDTO.getId());
        return Response.status(Response.Status.CREATED).entity(productDTO.from(product)).build();
    }
}
