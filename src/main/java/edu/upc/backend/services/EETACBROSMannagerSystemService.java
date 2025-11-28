package edu.upc.backend.services;

import edu.upc.backend.EETACBROSMannagerSystem;
import edu.upc.backend.EETACBROSMannagerSystemImpl;
import edu.upc.backend.classes.*;
import edu.upc.backend.exceptions.IncorrectPasswordException;
import edu.upc.backend.exceptions.UserNotFoundException;
import io.swagger.annotations.*;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.HashMap;
import java.util.List;

@Api(value = "/eetacbros", description = "Endpoint de biblioteca Service")
@Path("/eetacbros")
public class EETACBROSMannagerSystemService {

    private static final Logger logger = Logger.getLogger(EETACBROSMannagerSystemService.class);
    private EETACBROSMannagerSystem instance;

    public EETACBROSMannagerSystemService() {

        this.instance = EETACBROSMannagerSystemImpl.getInstance();

    }

    // TORNAR LLISTA D'USUARIS;
    @GET
    @ApiOperation(value = "Consultar llista d'usuaris", notes = "Mostra tots els usuaris")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Llista trobada", response = User.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "No hi ha lectors")
    })
    @Path("users")
    @Produces(MediaType.APPLICATION_JSON)
    public Response showUsersList() {
        UserList connectedUsers = this.instance.getConnectedUsers();

        List<User> usuarises = connectedUsers.getUserslist();

        GenericEntity<List<User>> entity = new GenericEntity<List<User>>(usuarises) {};
        return Response.ok(entity).build();
    }

    @GET
    @ApiOperation(value = "Consultar llista d'Items", notes = "Mostra tots els Items")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Llista trobada", response = Item.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "No hi ha Items")
    })
    @Path("shop/items")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItemList() {
        List<Item> itemList = this.instance.findAll(Item.class, new HashMap<>());

        GenericEntity<List<Item>> entity = new GenericEntity<List<Item>>(itemList) {};
        return Response.status(Response.Status.OK).entity(entity).build();
    }

    // REGISTRE
    @POST
    @Path("user/register")
    @ApiOperation(value = "Registrar un nou usuari", notes = "Registrar un nou usuari")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Usuari nou registrat", response = User.class),
            @ApiResponse(code = 409, message = "Nom d'usuari no disponible")
    })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerUser(User user) {
        logger.info(user.getUsername());
        logger.info(user.toString());
        
        if (user.getUsername() != null && user.getPassword() != null) {
            User userRegistered = this.instance.registerUser(user);
            return Response.status(Response.Status.CREATED).entity(userRegistered).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("Username or password cannot be null").build();
    }

    // LOG IN
    @POST
    @Path("user/login")
    @ApiOperation(value = "User log in", notes = "User log in")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response logIn(User user) {
        if (user.getUsername() == null || user.getPassword() == null) {
            return Response.status(400).entity("Invalid parameters").build();
        }

        String username = user.getUsername();
        String password = user.getPassword();

        try {
            instance.logIn(username, password);
        }
        catch (UserNotFoundException e) {
            return Response.status(404).entity("User not found").build();
        }
        catch (IncorrectPasswordException e) {
            return Response.status(400).entity("Incorrect password").build();
        }

        UserList connectedUsers = this.instance.getConnectedUsers();
        User loogedUser = connectedUsers.getUserByUsername(user.getUsername());

        return Response.status(201).entity(loogedUser).build();
    }

    // BUY ITEMS SHOP
    @POST
    @Path("shop/buy")
    @ApiOperation(value = "Item buy", notes = "Item buy")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Item.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Couldn't buy"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response shopBuyItems(BuyRequest request) {

        int userId = request.getUserId();
        User user = (User) instance.get(User.class, userId);
        String username = user.getUsername();

        instance.managePurchase(request);

        logger.info("Purchase done for user " + username);

        return Response.status(Response.Status.CREATED).entity(request).build();
    }

}
