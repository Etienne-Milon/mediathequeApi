package fr.em.mediathequeapi.endpoint;

import fr.em.mediathequeapi.dao.DaoFactory;
import fr.em.mediathequeapi.metier.Article;
import fr.em.mediathequeapi.security.Tokened;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.annotations.Param;

import java.util.ArrayList;

@Tag(name = "Articles")
@Path("/articles")
public class ArticleRessource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllArticles() {
        ArrayList<Article> allArticles;
        allArticles = DaoFactory.getArticleDAO().getAll();
        if (allArticles != null)
            return Response.ok(allArticles).build();
        else
            return Response.noContent().build();
    }

    //test : 5001756529522
    @GET
    @Path("{ean13}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("ean13") long ean13) {
        Article article = DaoFactory.getArticleDAO().getById(ean13);
        if (article != null)
            return Response.ok(article).build();
        else
            return Response.noContent().build();
    }

    @PUT
    @Tokened
    @Path("{ean13}/{prixachat}/{titre}/{grande_valeur}/{id_editeur}/{id_format}/{id_serie}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("ean13") long ean13, @PathParam("prixachat") float prix_achat, @PathParam("titre") String titre,
                           @PathParam("grande_valeur") Integer grande_valeur,@PathParam("id_editeur") Integer id_editeur,
                           @PathParam("id_format") Integer id_format,@PathParam("id_serie") Integer id_serie){
        if (DaoFactory.getArticleDAO().update(new Article()))
            return Response.ok().build();
        else
            return Response.status((Response.Status.CONFLICT)).build();
    }

}
