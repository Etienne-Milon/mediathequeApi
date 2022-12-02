package fr.em.mediathequeapi.endpoint;


import fr.em.mediathequeapi.dao.DaoFactory;
import fr.em.mediathequeapi.metier.Compte;
import fr.em.mediathequeapi.security.Tokened;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

@Tag(name = "Comptes")
@Path("/comptes")
public class CompteRessource {

    @Tokened
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllComptes() {
        ArrayList<Compte> allComptes = DaoFactory.getCompteDAO().getAll();
        if (allComptes != null)
            return Response.ok(allComptes).build();
        else
            return Response.noContent().build();
    }

    @Tokened
    @GET
    @Path("{num_adherent}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("num_adherent") Integer num_adherent) {
        Compte compte = DaoFactory.getCompteDAO().getById(num_adherent);
        if (compte != null) {
            return Response.ok(compte).build();
        }
        else
            return Response.noContent().build();
    }

    /* INSERT un nouveau compte*/
    @Tokened
    @POST
    public Response insert(Compte compte) throws SQLException {
        if (DaoFactory.getCompteDAO().insert(compte))
            return Response.ok(compte).status(Response.Status.CREATED).build();
        else
            return Response.status(Response.Status.CONFLICT).build();
    }



}
