package pt.ipleiria.estg.dei.ei.dae.backend.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import pt.ipleiria.estg.dei.ei.dae.backend.dtos.OrderDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.OrderBean;

import java.util.ArrayList;
import java.util.List;

//TODO
@Path("/loja/encomendas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderService {

    @EJB
    private OrderBean orderBean;

    @GET
    @Path("/")
    public List<OrderDTO> getOrders() {
        //TODO
        return new ArrayList<>();
    }
}
