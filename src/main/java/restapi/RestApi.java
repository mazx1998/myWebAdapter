package restapi;

import database.entities.BirthPlacesEntity;
import database.entities.PassportsEntity;
import database.entities.RequestsEntity;
import database.services.RequestService;
import database.services.impl.RequestServiceImpl;
import exceptions.NotAllFieldsAreFilledException;
import org.json.JSONObject;
import restapi.authorization.Roles;
import restapi.pojo.RequestFilterPojo;
import restapi.pojo.request.in.RequestInPojo;
import restapi.pojo.request.out.RequestOutPojo;
import restapi.utils.Validator;

import javax.annotation.security.RolesAllowed;
import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Date;
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

        List<RequestOutPojo> requestOutPojos = new LinkedList<>();
        requestsEntities.forEach(requestsEntity -> requestOutPojos.add(new RequestOutPojo(requestsEntity)));
        return Response.status(200).entity(requestOutPojos).build();
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

    @GET
    @Path("/createRequest")
    @RolesAllowed({Roles.ADMIN, Roles.USER})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createRequest(RequestInPojo request) {
        try {
            Validator.requestIsValid(request);
        } catch (NotAllFieldsAreFilledException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        PassportsEntity passportData = null;
        BirthPlacesEntity birthPlacesData = null;
        // If we dont have passport data
        if (request.getNumber() == null) {
            birthPlacesData = new BirthPlacesEntity(
                    request.getPlace_type(),
                    request.getSettlement(),
                    request.getDistrict(),
                    request.getRegion(),
                    request.getCountry()
            );
        }
        // If we have birth places data
        else if (request.getSettlement() == null){
            passportData = new PassportsEntity(
                    request.getSeries(),
                    request.getNumber(),
                    new Date(request.getIssue_date()),
                    request.getIssuer()
            );
        }
        // If all exists
        else {
            passportData = new PassportsEntity(
                    request.getSeries(),
                    request.getNumber(),
                    new Date(request.getIssue_date()),
                    request.getIssuer()
            );
            birthPlacesData = new BirthPlacesEntity(
                    request.getPlace_type(),
                    request.getSettlement(),
                    request.getDistrict(),
                    request.getRegion(),
                    request.getCountry()
            );
        }

        RequestsEntity requestsEntity = new RequestsEntity(
                request.getFirst_name(),
                request.getLast_name(),
                request.getPatronymic(),
                request.getGender(),
                new Date(request.getBirth_date()),
                new Timestamp(request.getRequest_date()),
                new Timestamp(request.getResponse_date()),
                request.getSnils(),
                birthPlacesData,
                passportData
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
        "first_name": "Максим",
        "last_name": "Зеленский",
        "patronymic": "Сергеевич",
        "gender": "Мужской",
        "birth_date": 886269600000,
        "request_date": 1585573975000,
        "response_date": 1585649577000,
        "snils": "1234567",

    	"place_type": "ОСОБЕННОЕ",
    	"settlement": "ГОРОД",
    	"district": "РАЙОН",
    	"region": "РЕГИОН",
        "country": "СТРАНА",

        "series": "65421",
        "number": "12442",
        "issue_date": 1490806800000,
        "issuer": "УФМС РОССИИ"
    }
    */
}
