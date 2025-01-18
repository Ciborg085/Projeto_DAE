package pt.ipleiria.estg.dei.ei.dae.backend.ws;


import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.backend.dtos.SensorDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.dtos.SensorPatchDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.SensorBean;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.sensors.Sensor;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.backend.security.Authenticated;

import java.util.List;

// TODO - Criar DTOS especificos para cada path
@Path("/sensors")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Authenticated
public class SensorService {

    @EJB
    private SensorBean sensorBean;

    //TODO
    /*

     */
    @GET
    @Path("/")
    @RolesAllowed({"Administrator","Client"})
    public List<SensorDTO> getSensors() throws MyEntityNotFoundException {
        return SensorDTO.from(sensorBean.findAll());
    }

    @PATCH
    @Path("/{sensor_id}")
    @RolesAllowed("Administrator")
    public Response updateValues(@PathParam("sensor_id") long sensor_id, SensorPatchDTO sensorDTO)
            throws MyEntityNotFoundException {
        sensorBean.updateValues(sensor_id,sensorDTO.getProperties());
        Sensor sensor = sensorBean.find(sensor_id);

        return Response.status(Response.Status.ACCEPTED)
                .entity(SensorPatchDTO.from(sensor))
                .build();
    }

}
