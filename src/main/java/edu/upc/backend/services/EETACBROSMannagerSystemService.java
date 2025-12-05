package edu.upc.backend.services;

import edu.upc.backend.EBDBManagerSystem;
import edu.upc.backend.EETACBROSMannagerSystem;
import edu.upc.backend.classes.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.List;

@Api(value = "/eetacbros", description = "Endpoint de biblioteca Service")
@Path("/eetacbros")
public class EETACBROSMannagerSystemService {

    private static final Logger logger = Logger.getLogger(EETACBROSMannagerSystemService.class);
    private EETACBROSMannagerSystem sistema;

    public EETACBROSMannagerSystemService() {
        // Use the singleton instance
        this.sistema = EBDBManagerSystem.getInstance();

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
        List<User> connectedUsers = this.sistema.getUsersListDatabase();
        GenericEntity<List<User>> entity = new GenericEntity<List<User>>(connectedUsers) {};
        return Response.ok(entity).build();
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
        try {
            logger.info("Trying to register user:" + user.getUsername());
            this.sistema.registerUser(user);
            return Response.status(Response.Status.CREATED).entity(user).build();
        }
        catch (Exception e) {
            logger.error("Error registering user: " + e.getMessage());
            return Response.status(Response.Status.CONFLICT).entity("Username not available").build();
        }

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
        try {
            User userlogged = this.sistema.logIn(user);
            logger.info("id: "+userlogged.getId()+" name: "+userlogged.getName()+" username: " + userlogged.getUsername() + " password: "+userlogged.getPassword()+" email: "+userlogged.getEmail()+"coins: "+userlogged.getCoins());
            return Response.status(Response.Status.CREATED).entity(userlogged).build();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // TORNAR LLISTA D'USUARIS;
    @GET
    @ApiOperation(value = "Consulta la llista d'items comprats d'un usuari", notes = "Mostra tots els items comprats d'un usuari")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Llista trobada", response = Item.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "No hi ha items")
    })
    @Path("user/items/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response showUserItems(@PathParam("userId") int userId) {
        List<Item> items = this.sistema.getUserItems(userId);
        GenericEntity<List<Item>> entity = new GenericEntity<List<Item>>(items) {};
        return Response.ok(entity).build();
    }


    // SHOP ITEMS
    @GET
    @ApiOperation(value = "Consultar llista d'Items", notes = "Mostra tots els Items")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Llista trobada", response = Item.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "No hi ha Items")
    })
    @Path("shop/items")
    @Produces(MediaType.APPLICATION_JSON)
    public Response showItemList() {
        List<Item> itemList = this.sistema.getItemList();

        GenericEntity<List<Item>> entity = new GenericEntity<List<Item>>(itemList) {};
        return Response.ok(entity).build();
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
    public Response shopBuyItems(BuyRequest buyrequest) {
        try {
            sistema.registerPurchase(buyrequest);
            return Response.status(Response.Status.CREATED).entity(buyrequest).build();
        }
        catch (Exception ex) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

}
