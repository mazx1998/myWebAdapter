package restapi.authorization;

import database.entities.UsersEntity;
import database.services.UsersService;
import database.services.impl.UsersServiceImpl;
import exceptions.DataBaseException;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Base64;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 * This filter verify the access permissions for a user
 * based on username and passowrd provided in request
 * */
@RequestScoped
@Provider
public class AuthenticationFilter implements javax.ws.rs.container.ContainerRequestFilter
{

    @Context
    private ResourceInfo resourceInfo;

    private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String AUTHENTICATION_SCHEME = "Basic";

    @Inject
    UsersService usersService;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        Method method = resourceInfo.getResourceMethod();
        //Access allowed for all
        if( ! method.isAnnotationPresent(PermitAll.class)) {
            //Access denied for all
            if(method.isAnnotationPresent(DenyAll.class)) {
                requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
                return;
            }

            //Get request headers
            final MultivaluedMap<String, String> headers = requestContext.getHeaders();

            //Fetch authorization header
            final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);

            //If no authorization information present; block access
            if(authorization == null || authorization.isEmpty()) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                return;
            }

            //Get encoded username and password
            final String encodedUserPassword = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");

            //Decode username and password
            String usernameAndPassword = new String(Base64.getDecoder().decode(encodedUserPassword.getBytes()));

            //Split username and password tokens
            final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
            final String username = tokenizer.nextToken();
            final String password = tokenizer.nextToken();

            //Verify user access
            if(method.isAnnotationPresent(RolesAllowed.class)) {
                RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
                Set<String> rolesSet = new HashSet<>(Arrays.asList(rolesAnnotation.value()));

                //Is user valid?
                if( !isUserAllowed(username, password, rolesSet)) {
                    requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                }
            }
        }
    }

    private boolean isUserAllowed(final String login, final String password, final Set<String> rolesSet)
    {
        boolean isAllowed = false;

        UsersEntity foundUser;
        try {
            foundUser = usersService.findByLogin(login);
        } catch (DataBaseException e) {
            e.printStackTrace();
            return isAllowed;
        }

        String md5Hex = DigestUtils.md5Hex(password);

        //md5 for password 'password' : 5f4dcc3b5aa765d61d8327deb882cf99
        if (md5Hex.equals(foundUser.getPassword())) {
            // Verify user role
            if (rolesSet.contains(foundUser.getRole())) {
                isAllowed = true;
            }
        }

        return isAllowed;
    }
}
