package restapi;

import database.entities.RequestsEntity;
import database.services.RequestService;
import database.services.impl.RequestServiceImpl;

import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Date;

/**
 * @author Максим Зеленский
 * @since 07.03.2020
 */
@Path("authorization")
public class RestApi {
    @Context
    ServletContext servletContext;

    @GET
    @Path("/index.html")
    public InputStream getRequestPage() {
        try {
            String base = servletContext.getRealPath("/");
            File htmlPage = new File(base + "/index.html");
            return new FileInputStream(htmlPage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GET
    @Path("/responses.html")
    public InputStream getResponsesPage() {
        try {
            String base = servletContext.getRealPath("/");
            File htmlPage = new File(base + "/responses.html");
            return new FileInputStream(htmlPage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


    @POST
    @Path("createRequest")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void createRequest(@FormParam("firstName") String firstName,
                                 @FormParam("lastName") String lastName,
                                @FormParam("patronymic") String patronymic,
                                @FormParam("gender") String gender,
                                @FormParam("birthDate") Date birthDate) {

        if (gender.equals("Мужской")) {
            gender = "M";
        } else {
            gender = "F";
        }
        RequestsEntity requestsEntity
                = new RequestsEntity(
                        firstName,
                        lastName,
                        patronymic,
                        gender,
                        birthDate,
                        new Date(System.currentTimeMillis())
                );

        RequestService requestService = new RequestServiceImpl();
        requestService.create(requestsEntity);
    }

}
