package restapi;

import database.entities.RequestsEntity;
import database.services.RequestService;
import database.services.impl.RequestServiceImpl;
import org.json.JSONObject;
import restapi.authorization.Roles;
import restapi.pojo.RequestFilterPojo;
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
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("/requests")
    @RolesAllowed({Roles.ADMIN, Roles.USER})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    /*
        Consume json example:
        {
            "first_name": "Максим",
            "last_name": null
            "patronymic": "Сергеевич",
            "page_number": 1,
            "page_size": 10
        }
    */
    public Response getRequestListWithFilter(RequestFilterPojo filter) {
        RequestService requestService = new RequestServiceImpl();
        List<RequestsEntity> requestsEntities;
        requestsEntities = requestService.getDataByFilter(filter);
        if (requestsEntities == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        List<RequestPojo> requestPojos = new LinkedList<>();
        requestsEntities.forEach(requestsEntity -> requestPojos.add(new RequestPojo(requestsEntity)));
        return Response.status(200).entity(requestPojos).build();
    }

    @GET
    @Path("/reqCount")
    @RolesAllowed({Roles.ADMIN, Roles.USER})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getRequestsCount(RequestFilterPojo filter) {
        RequestService requestService = new RequestServiceImpl();
        Integer rowsCount = requestService.getRowsCountByFilter(filter);
        if (rowsCount == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("rows_count", rowsCount);
        return Response.status(200).entity(jsonObject.toString()).build();
    }
}
