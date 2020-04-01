package restapi;

import database.entities.RequestsEntity;
import database.services.RequestService;
import database.services.impl.RequestServiceImpl;
import restapi.authorization.Roles;
import restapi.pojo.RequestPojo;

import javax.annotation.security.RolesAllowed;
import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Максим Зеленский
 * @since 07.03.2020
 */
@Path("rest")
public class RestApi {

    @Context
    ServletContext servletContext;

    @GET
    @Path("/login")
    @RolesAllowed({Roles.ADMIN, Roles.USER})
    public Response getLogInStatus()
    {
        /*JSONObject responseJsonObj = new JSONObject();
        responseJsonObj.put("authorization_status", "success");*/
        return Response.status(Response.Status.OK)/*.entity(responseJsonObj.toString())*/.build();
    }

    @GET
    @Path("/requests")
    @RolesAllowed({Roles.ADMIN, Roles.USER})
    @Produces(MediaType.APPLICATION_JSON)
    //@Consumes(MediaType.APPLICATION_JSON)
    public Response getRequestsList(
            @QueryParam("page_number") int pageNumber,
            @QueryParam("page_size") int pageSize)
    {
        RequestService requestService = new RequestServiceImpl();

        List<RequestsEntity> requestsEntities;
        requestsEntities = requestService.getAll(pageNumber, pageSize);

        if (requestsEntities == null || requestsEntities.size() == 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        List<RequestPojo> requestPojos = new LinkedList<>();
        requestsEntities.forEach(requestsEntity -> requestPojos.add(new RequestPojo(requestsEntity)));

        return Response.status(200).entity(requestPojos).build();
    }

    @GET
    @Path("/search")
    @RolesAllowed({Roles.ADMIN, Roles.USER})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSearchList(
            @QueryParam("first_name") String firstName,
            @QueryParam("last_name") String lastName,
            @QueryParam("patronymic") String patronymic
    ) {
        RequestService requestService = new RequestServiceImpl();

        List<RequestsEntity> requestsEntities;
        requestsEntities = requestService.getByFullName(firstName, lastName, patronymic);

        if (requestsEntities == null || requestsEntities.size() == 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        List<RequestPojo> requestPojos = new LinkedList<>();
        requestsEntities.forEach(requestsEntity -> requestPojos.add(new RequestPojo(requestsEntity)));
        return Response.status(200).entity(requestPojos).build();
    }

}
