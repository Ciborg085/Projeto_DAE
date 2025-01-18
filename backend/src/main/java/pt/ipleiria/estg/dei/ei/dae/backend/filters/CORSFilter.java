package pt.ipleiria.estg.dei.ei.dae.backend.filters;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class CORSFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        // Permitir origem do frontend
        responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");

        // Permitir cabeçalhos personalizados
        responseContext.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");

        // Permitir métodos HTTP
        responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PATCH, PUT, DELETE, OPTIONS, HEAD");

        // Permitir credenciais (cookies, tokens)
        responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");

        // Cache do preflight (opcional)
        responseContext.getHeaders().add("Access-Control-Max-Age", "1209600");

        // Responde imediatamente às requisições OPTIONS com status 200
        if ("OPTIONS".equalsIgnoreCase(requestContext.getMethod())) {
            responseContext.setStatus(200);
        }
    }
}
