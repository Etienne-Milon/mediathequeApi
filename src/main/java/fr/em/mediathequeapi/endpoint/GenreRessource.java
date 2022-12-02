package fr.em.mediathequeapi.endpoint;

import fr.em.mediathequeapi.dao.DaoFactory;

import fr.em.mediathequeapi.metier.Genre;
import fr.em.mediathequeapi.metier.Reference;
import fr.em.mediathequeapi.security.Tokened;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.ArrayList;

@Tag(name ="Genres")
@Path("/genres")
public class GenreRessource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllGenres() {
        ArrayList<Reference> allGenres = DaoFactory.getGenreDAO().getAll();
        if (allGenres != null)
            return Response.ok(allGenres).build();
        else
            return Response.noContent().build();
    }

    //test : 8030387318159
    @GET
    @Path("{ean13}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("ean13") long ean13) {
        ArrayList<Reference> genres = DaoFactory.getGenreDAO().getById(ean13);
        if (genres != null)
            return Response.ok(genres).build();
        else
            return Response.noContent().build();
    }

    /* UPDATE un genre existant dans un article OSEF = poubelle */
    @Tokened
    @PUT
    @Path("{ean13}/{idOldGenre}/{idNewGenre}")
    @ApiResponse(responseCode = "404", description = "Non trouvé : le genre à mettre à jour n'existe pas pour cet article")
    @ApiResponse(responseCode = "409", description = "Conflit : le genre mis à jour existe déja")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("ean13") long ean13, @PathParam("idOldGenre") Integer oldGenre, @PathParam("idNewGenre") Integer newGenre) {

        if (oldGenre == null || newGenre == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        ArrayList<Reference> genres = DaoFactory.getGenreDAO().getById(ean13);
        if(!Outils.testPresence(oldGenre,genres)){
            return Response.status(404).build();
        }
        if (Outils.testPresence(newGenre, genres)) {
            return Response.status(409).build();
        }
        if (DaoFactory.getGenreDAO().updateGenreInArticle(ean13, oldGenre, newGenre))
            return Response.ok(newGenre).build();
        else
            return Response.status(Response.Status.CONFLICT).build();
    }


    /* INSERT une ArrayList de Genres existants dans un article   */
    @Tokened
    @POST
    @Path("{ean13}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insert(@PathParam("ean13") long ean13, ArrayList<Reference> genres) throws SQLException {
//        ArrayList<Reference> genresArticle = DaoFactory.getGenreDAO().getById(ean13);
//        if (Outils.testPresence(genres, genresArt)) {
//            return Response.status(409).build();
//        }
        if (DaoFactory.getGenreDAO().insertGenresInArticle(ean13, genres))
            return Response.ok(genres).status(Response.Status.CREATED).build();
        else
            return Response.status(Response.Status.CONFLICT).build();
    }

    /* DELETE une ArrayList de Genres existants dans un article */
    @Tokened
    @DELETE
    @Path("{ean13}/{idGenre}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiResponse(responseCode = "204", description = "Genre supprimé")
    @ApiResponse(responseCode = "404", description = "Non trouvé")
    public Response delete(@PathParam("ean13") long ean13, ArrayList<Reference> genres) {
        if (genres == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (DaoFactory.getGenreDAO().deleteGenresInArticle(ean13, genres)) {
            return Response.status(204).build();
        } else {
            return Response.status(404).build();
        }
    }
}



