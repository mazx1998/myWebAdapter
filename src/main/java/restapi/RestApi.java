package restapi;

import database.entities.RequestsEntity;
import database.entities.UsersEntity;
import database.services.RequestService;
import database.services.UsersService;
import exceptions.DataBaseException;
import exceptions.NotAllFieldsAreFilledException;
import restapi.pojo.RequestFilterPojo;
import restapi.utils.XMLParserUtil;
import org.json.JSONObject;
import restapi.authorization.Roles;
import restapi.pojo.RequestResponsePojo;
import restapi.utils.ValidatorUtil;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;
import java.util.Comparator;
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
    @Inject
    RequestService requestService;
    @Inject
    UsersService usersService;

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
    public Response getRequestListWithFilter(@QueryParam("first_name") String firstName,
                                             @QueryParam("family_name") String familyName,
                                             @QueryParam("patronymic") String patronymic,
                                             @QueryParam("page_number") Integer pageNumber,
                                             @QueryParam("page_size") Integer pageSize) {
        RequestFilterPojo filter = new RequestFilterPojo();
        filter.setFirst_name(firstName);
        filter.setFamily_name(familyName);
        filter.setPatronymic(patronymic);
        filter.setPage_number(pageNumber);
        filter.setPage_size(pageSize);

        List<RequestsEntity> requestsEntities;

        requestsEntities = requestService.getDataByFilter(filter);
        if (requestsEntities == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        List<RequestResponsePojo> requestsPojo = new LinkedList<>();
        requestsEntities.forEach(requestsEntity -> {
            RequestResponsePojo pojo = new RequestResponsePojo();
            pojo.setRequest_date(requestsEntity.getRequestDate().getTime());
            pojo.setId(requestsEntity.getId());
            pojo.setAuthor(requestsEntity.getUserId().getLogin());
            if (requestsEntity.getResponseDate() != null) {
                pojo.setResponse_date(requestsEntity.getResponseDate().getTime());
            }
            requestsPojo.add(pojo);
        });

        return Response.status(200).entity(requestsPojo).build();
    }

    @GET
    @Path("/request")
    @RolesAllowed({Roles.ADMIN, Roles.USER})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRequestById(@QueryParam("id") Integer id) {
        RequestsEntity requestsEntity = requestService.getById(id);

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

        return Response.status(200).entity(pojo).build();
    }

    @GET
    @Path("/reqCount")
    @RolesAllowed({Roles.ADMIN, Roles.USER})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRequestsCount(@QueryParam("first_name") String firstName,
                                     @QueryParam("family_name") String familyName,
                                     @QueryParam("patronymic") String patronymic,
                                     @QueryParam("page_number") Integer pageNumber,
                                     @QueryParam("page_size") Integer pageSize) {
        RequestFilterPojo filter = new RequestFilterPojo();
        filter.setFirst_name(firstName);
        filter.setFamily_name(familyName);
        filter.setPatronymic(patronymic);
        filter.setPage_number(pageNumber);
        filter.setPage_size(pageSize);

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
