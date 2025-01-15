package pt.ipleiria.estg.dei.ei.dae.backend.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.backend.dtos.RegisterDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.UserBean;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.User;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityNotFoundException;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthService {

    @EJB
    private UserBean userBean;

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
    public Response login(RegisterDTO loginDTO) {
        try {
            User user = userBean.authenticate(loginDTO.getUsername(), loginDTO.getPassword());

            // ⚠️ Simulação de token (substituir por JWT futuramente)
            String token = "Bearer " + user.getUsername() + "_token";

            return Response.ok().entity("Token: " + token).build();
        } catch (MyEntityNotFoundException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Credenciais inválidas!").build();
        }
    }

    // 🔎 Rota de perfil do usuário logado
    @GET
    @Path("/profile")
    public Response getProfile(@HeaderParam("Authorization") String authHeader) {
        if (authHeader == null || authHeader.isEmpty()) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Token não fornecido!").build();
        }

        // ⚠️ Simulação de token (substituir por JWT no futuro)
        String username = authHeader.replace("Bearer ", "").replace("_token", "");

        try {
            User user = userBean.find(username);

            // Retorna dados do perfil
            return Response.ok().entity("Perfil do usuário: " + user.getName() + " (" + user.getEmail() + ")").build();

        } catch (MyEntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("Usuário não encontrado!").build();
        }
    }
}
