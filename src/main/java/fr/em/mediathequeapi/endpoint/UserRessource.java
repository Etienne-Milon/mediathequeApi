package fr.em.mediathequeapi.endpoint;

import fr.em.mediathequeapi.security.MyToken;
import fr.em.mediathequeapi.security.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import static fr.em.mediathequeapi.security.Tokened.TOKEN;

@Path("/user")
@Tag(name = "user")
public class UserRessource {

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(User user){
        if (user.getLogin().equals("admin") && user.getPassword().equals("string")){
            return Response.ok().header(TOKEN,"Bearer " + new MyToken(user)).build();
        }
        else
            return Response.status(Response.Status.UNAUTHORIZED).build();
    }


}
