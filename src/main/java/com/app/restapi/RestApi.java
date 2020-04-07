package com.app.restapi;

import com.app.database.entities.RequestsEntity;
import com.app.database.entities.UsersEntity;
import com.app.database.services.RequestService;
import com.app.database.services.UsersService;
import com.app.database.services.impl.RequestServiceImpl;
import com.app.database.services.impl.UsersServiceImpl;
import com.app.exceptions.DataBaseException;
import com.app.exceptions.NotAllFieldsAreFilledException;
import com.app.restapi.pojo.RequestFilterPojo;
import com.app.restapi.utils.XMLParserUtil;
import org.json.JSONObject;
import com.app.restapi.authorization.Roles;
import com.app.restapi.pojo.RequestResponsePojo;
import com.app.restapi.utils.ValidatorUtil;

import javax.annotation.security.RolesAllowed;
import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;
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
    public Response login()
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
            "last_name": null,
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

        List<RequestResponsePojo> requestsPojo = new LinkedList<>();
        requestsEntities.forEach(requestsEntity -> {
            RequestResponsePojo pojo;
            pojo = XMLParserUtil.getPojoFromRequestXML(requestsEntity.getRequestXml());
            pojo.setRequest_date(requestsEntity.getRequestDate().getTime());
            pojo.setId(requestsEntity.getId());
            pojo.setAuthor(requestsEntity.getUserId().getLogin());
            if (requestsEntity.getResponseXml() != null) {
                pojo.setSnils(XMLParserUtil.getSnilsFromResponseXML(requestsEntity.getResponseXml()));
            }
            if (requestsEntity.getResponseDate() != null) {
                pojo.setResponse_date(requestsEntity.getResponseDate().getTime());
            }
            requestsPojo.add(pojo);
        });

        return Response.status(200).entity(requestsPojo).build();
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

    @POST
    @Path("/createRequest")
    @RolesAllowed({Roles.ADMIN, Roles.USER})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createRequest(RequestResponsePojo request) {
        try {
            ValidatorUtil.requestIsValid(request);
        } catch (NotAllFieldsAreFilledException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        String requestXML;
        try {
            requestXML = XMLParserUtil.getRequestXML(request);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        UsersService usersService = new UsersServiceImpl();
        UsersEntity usersEntity;
        try {
            usersEntity = usersService.findByLogin(request.getAuthor());
        } catch (DataBaseException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        RequestsEntity requestsEntity = new RequestsEntity(
                requestXML,
                null,
                new Timestamp(request.getRequest_date()),
                null,
                usersEntity
        );

        try {
            RequestService requestService = new RequestServiceImpl();
            requestService.create(requestsEntity);
            //TODO send request to SMEV
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.status(200).build();
    }

    /*
    Consume json example:
    {
        "author": "user",

        "first_name": "МАКСИМ",
        "family_name": "ЗЕЛЕНСКИЙ",
        "patronymic": "СЕРГЕЕВИЧ",          // can be removed
        "gender": "МУЖСКОЙ",
        "birth_date": 886269600000,
        "request_date": 1585573975000,
        "response_date": 1585649577000,    // can be removed
        "snils": "1234567",                // can be removed

    	"place_type": "ОСОБОЕ",
    	"settlement": "МИРНЫЙ",
    	"district": "РАЙОН",               // can be removed
    	"region": "РЕГИОН",                // can be removed
        "country": "РФ",                   // can be removed

        "passport_series": "0005",
        "passport_number": "777777",
        "passport_issue_date": 1490806800000,
        "passport_issuer": "ОВД"
    }
    * You can use birth place data or passport data or both
    */
}
