package fr.em.mediathequeapi.endpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.em.mediathequeapi.metier.Genre;
import fr.em.mediathequeapi.metier.Reference;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.jboss.resteasy.plugins.server.resourcefactory.POJOResourceFactory;
import org.jboss.resteasy.spi.Dispatcher;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.sql.Array;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GenreRessourceTest {

    MockHttpRequest request;
    MockHttpResponse response;
    Dispatcher dispatcher = MockDispatcherFactory.createDispatcher();
    POJOResourceFactory noDefaults = new POJOResourceFactory(GenreRessource.class);

    @Test
    void getAllGenres()  throws URISyntaxException, UnsupportedEncodingException {
        dispatcher.getRegistry().addResourceFactory(noDefaults);
        {
            request = MockHttpRequest.get("/genres");
            response = new MockHttpResponse();

            dispatcher.invoke(request, response);
            assertEquals(HttpServletResponse.SC_OK, response.getStatus());
        }
    }

    @Test
    void getById() throws URISyntaxException, UnsupportedEncodingException {
        dispatcher.getRegistry().addResourceFactory(noDefaults);
        {
            request = MockHttpRequest.get("/genres/8030387318159");
            response = new MockHttpResponse();
            dispatcher.invoke(request, response);
            assertEquals(HttpServletResponse.SC_OK, response.getStatus());
            System.out.println(response.getContentAsString());
            assertEquals("[{\"id\":1,\"nom\":\"Action\"}", response.getContentAsString().substring(0,24));
        }
    }

    @Test //  OSEF = poubelle
    void update() throws URISyntaxException {
        dispatcher.getRegistry().addResourceFactory(noDefaults);
        {
            request = MockHttpRequest.put("/genres/8030387318159/2/3");
            response = new MockHttpResponse();
            dispatcher.invoke(request, response);
            assertEquals(HttpServletResponse.SC_OK,response.getStatus());
            request = MockHttpRequest.put("/genres/8030387318159/3/2");
        }
        {
            request = MockHttpRequest.put("/genres/8030387318159/4/2");
            response = new MockHttpResponse();
            dispatcher.invoke(request, response);
            assertEquals(404,response.getStatus());
        }
        {
            request = MockHttpRequest.put("/genres/8030387318159/17/11");
            response = new MockHttpResponse();
            dispatcher.invoke(request, response);
            assertEquals(409,response.getStatus());
        }
    }

    @Test
    void insert() throws URISyntaxException, JsonProcessingException {
        dispatcher.getRegistry().addResourceFactory(noDefaults);
        {
            request = MockHttpRequest.post("/genres/8030387318159");
            request.accept(MediaType.APPLICATION_JSON);
            request.contentType(MediaType.APPLICATION_JSON);
            ObjectMapper objectMapper = new ObjectMapper();
            Genre genretest1 = new Genre(4,"Mystery");
            Genre genretest2 = new Genre(5,"Horror");
            ArrayList<Reference> genresTests = new ArrayList<>();
            genresTests.add(genretest1);
            genresTests.add(genretest2);
            byte[] genresTestsJson = objectMapper.writeValueAsBytes(genresTests);
            request.content(genresTestsJson);
            response = new MockHttpResponse();
            dispatcher.invoke(request,response);
            assertEquals(HttpServletResponse.SC_CREATED,response.getStatus());
        }
    }

    @Test
    void delete() {
        dispatcher.getRegistry().addResourceFactory(noDefaults);
        {

        }

    }
}