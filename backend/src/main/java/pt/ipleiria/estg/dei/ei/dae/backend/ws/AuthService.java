package pt.ipleiria.estg.dei.ei.dae.backend.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import pt.ipleiria.estg.dei.ei.dae.backend.dtos.LoginDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.dtos.ProfileDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.dtos.RegisterDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.UserBean;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.User;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.backend.security.Authenticated;
import pt.ipleiria.estg.dei.ei.dae.backend.security.TokenIssuer;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthService {

    @Inject
    private TokenIssuer issuer;

    @EJB
    private UserBean userBean;

    @Context
    private SecurityContext securityContext;

    // 🔐 Rota de registro
    @POST
    @Path("/register")
    public Response register(RegisterDTO registerDTO) {
        try {
            userBean.registerClient(
                    registerDTO.getUsername(),
                    registerDTO.getPassword(),
                    registerDTO.getName(),
                    registerDTO.getEmail()
            );
            return Response.status(Response.Status.CREATED).entity("Conta criada com sucesso!").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao criar conta: " + e.getMessage()).build();
        }
    }

    // 🔐 Rota de login
    @POST
    @Path("/login")
    public Response login(@Valid LoginDTO loginDTO) {
        if (userBean.canLogin(loginDTO.getUsername(), loginDTO.getPassword())) {
            String token = issuer.issue(loginDTO.getUsername());
            return Response.ok(token).build();
        }

        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    // 🔎 Rota de perfil do usuário logado
    @GET
    @Path("/profile")
    @Produces({MediaType.APPLICATION_JSON,MediaType.TEXT_PLAIN})
    @Authenticated
    @RolesAllowed({"Administrator","Client"})
    public Response getProfile() {
        try {
            var pricipal = securityContext.getUserPrincipal();
            User user = userBean.find(pricipal.getName());
            ProfileDTO dto = ProfileDTO.from(user);

            return Response.ok().entity(dto).build();

        } catch (MyEntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("Usuário não encontrado!").build();
        }
    }
}
