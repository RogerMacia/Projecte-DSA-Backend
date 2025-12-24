package edu.upc.backend.services;

import edu.upc.backend.EBDBManagerSystem;
import edu.upc.backend.EETACBROSMannagerSystem;
import edu.upc.backend.classes.*;
import edu.upc.backend.exceptions.*;
import io.swagger.annotations.*;
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
    private final EETACBROSMannagerSystem sistema;

    public EETACBROSMannagerSystemService() {
        this.sistema = EBDBManagerSystem.getInstance();
    }

    @GET
    @ApiOperation(value = "Get user ranking", notes = "Returns the top 3 users and the current user's rank.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ranking retrieved successfully", response = RankingResponse.class),
            @ApiResponse(code = 404, message = "User not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @Path("/ranking/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRanking(@PathParam("userId") int userId) {
        try {
            RankingResponse rankingResponse = sistema.getRanking(userId);
            return Response.ok(rankingResponse).build();
        } catch (UserNotFoundException e) {
            logger.error("Error getting ranking: " + e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorResponse("USER_NOT_FOUND", e.getMessage())).build();
        } catch (Exception e) {
            logger.error("Unexpected error while fetching ranking: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorResponse("UNEXPECTED_ERROR", "An unexpected error occurred.")).build();
        }
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
            @ApiResponse(code = 200, message = "New user registered", response = User.class),
            @ApiResponse(code = 409, message = "Username is not available"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerUser(User user) {
        try {
            logger.info("Registering new user:" + user.getUsername());
            this.sistema.registerUser(user);
            return Response.status(Response.Status.CREATED).entity(user).build();
        }
        catch (UsernameAlreadyExistsException e) {
            logger.error("Error registering user: " + e.getMessage());
            ErrorResponse errorResponse = new ErrorResponse("USERNAME_IS_NOT_AVAILABLE", e.getMessage());
            return Response.status(Response.Status.CONFLICT).entity(errorResponse).build();
        }
        catch (Exception e) {
            logger.error("Error registering user: " + e.getMessage());
            ErrorResponse errorResponse = new ErrorResponse("GENERIC_REGISTRATION_ERROR", "An unexpected error occurred during registration.");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
        }

    }

    // LOG IN
    @POST
    @Path("user/login")
    @ApiOperation(value = "User log in", notes = "User log in")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class),
            @ApiResponse(code = 404, message = "Invalid username or password"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response logIn(User user) {
        try {
            User userlogged = this.sistema.logIn(user);
            logger.info("User logged in: " + userlogged.getUsername());
            return Response.status(Response.Status.OK).entity(userlogged).build();
        }
        catch (UserOrPasswordInvalidException e) {
            logger.error("Error logging user "+user.getUsername()+" : " + e.getMessage());
            ErrorResponse errorResponse = new ErrorResponse("USER_OR_PASSWORD_INVALID", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(errorResponse).build();
        }
        catch (Exception e) {
            logger.error("Error logging in: " + e.getMessage());
            ErrorResponse errorResponse = new ErrorResponse("GENERIC_LOGIN_ERROR", "An unexpected error occurred during login.");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
        }
    }

    // TORNAR LLISTA D'USUARIS;
    @GET
    @ApiOperation(value = "Consulta la llista d'items comprats d'un usuari", notes = "Mostra tots els items comprats d'un usuari")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Items found.", response = Item.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "No items found.")
    })
    @Path("user/items/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response showUserItems(@PathParam("userId") int userId) {
        List<Item> items = this.sistema.getUserItems(userId);
        GenericEntity<List<Item>> entity = new GenericEntity<List<Item>>(items) {};
        return Response.ok(entity).build();
    }

    @PUT
    @Path("user/update")
    @ApiOperation(value = "Update user data", notes = "Updates the personal information of an existing user.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User updated successfully", response = User.class),
            @ApiResponse(code = 404, message = "User not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUserData(User user) {
        try {
            User updatedUser = this.sistema.updateUserData(user);
            return Response.status(Response.Status.OK).entity(updatedUser).build();
        } catch (UserNotFoundException e) {
            logger.error("Error updating in: " + e.getMessage());
            ErrorResponse errorResponse = new ErrorResponse("USER_NOT_FOUND", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(errorResponse).build();
        } catch (Exception e) {
            logger.error("Error updating in: " + e.getMessage());
            ErrorResponse errorResponse = new ErrorResponse("GENERIC_UPDATING_ERROR", "An unexpected error occurred during updating.");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
        }
    }

    // DELET USER
    @DELETE
    @Path("user/delete/{id}")
    @ApiOperation(value = "Delete a user", notes = "Deletes an existing user by ID.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User deleted successfully"),
            @ApiResponse(code = 404, message = "User not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("id") int id) {
        try {
            logger.info("Deleting user with id: " + id);
            this.sistema.deleteUserData(id);
            return Response.status(Response.Status.OK).build();
        }
        catch (UserNotFoundException e) {
            logger.error("Error deleting in: " + e.getMessage());
            ErrorResponse errorResponse = new ErrorResponse("USER_NOT_FOUND", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(errorResponse).build();
        }
        catch (Exception e) {
            logger.error("Error deleting in: " + e.getMessage());
            ErrorResponse errorResponse = new ErrorResponse("GENERIC_DELETING_ERROR", "An unexpected error occurred during deleting.");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
        }
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
            @ApiResponse(code = 201, message = "Successful", response = BuyRequest.class),
            @ApiResponse(code = 404, message = "User not found"),
            @ApiResponse(code = 400, message = "Insufficient money"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response shopBuyItems(BuyRequest buyrequest) {
        try {
            logger.info("User " + buyrequest.getUserId() + " bought items.");
            sistema.registerPurchase(buyrequest);
            return Response.status(Response.Status.CREATED).entity(buyrequest).build();
        }
        catch (UserNotFoundException e) {
            logger.error("Error buying items: " + e.getMessage());
            ErrorResponse errorResponse = new ErrorResponse("USER_NOT_FOUND", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(errorResponse).build();
        }
        catch (InsufficientMoneyException e) {
            logger.error("Error buying items: " + e.getMessage());
            ErrorResponse errorResponse = new ErrorResponse("INSUFFICIENT_MONEY", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
        }
        catch (Exception ex) {
            logger.error("Error buying items: " + ex.getMessage());
            ErrorResponse errorResponse = new ErrorResponse("GENERIC_BUY_ERROR", "An unexpected error occurred during purchase.");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
        }
    }

    @GET
    @ApiOperation(value = "Get Player by id", notes = "Hola k ase")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Player.class),
            @ApiResponse(code = 404, message = "Player not found")
    })
    @Path("/player/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlayerByUserId(@PathParam("id") int id) {
        try {
            Player res = sistema.getPlayerByUserId(id);
            return Response.status(Response.Status.OK).entity(res).build();
        } catch (PlayerNotFoundException e) {
            logger.error("Error getting player: " + e.getMessage());
            ErrorResponse errorResponse = new ErrorResponse("PLAYER_NOT_FOUND", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(errorResponse).build();
        } catch (Exception e) {
            logger.error("Error getting player: " + e.getMessage());
            ErrorResponse errorResponse = new ErrorResponse("GENERIC_GET_PLAYER_ERROR", "An unexpected error occurred while getting player.");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
        }
    }

    @GET
    @ApiOperation(value = "Get game by id", notes = "Hola k ase")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Game.class),
            @ApiResponse(code = 404, message = "Game not found")
    })
    @Path("/game/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGameByUserId(@PathParam("id") int id) {
        try {
            Game res = sistema.getGameByUserId(id);
            return Response.status(Response.Status.OK).entity(res).build();
        } catch (GameNotFoundException e) {
            logger.error("Error getting game: " + e.getMessage());
            ErrorResponse errorResponse = new ErrorResponse("GAME_NOT_FOUND", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(errorResponse).build();
        } catch (Exception e) {
            logger.error("Error getting game: " + e.getMessage());
            ErrorResponse errorResponse = new ErrorResponse("GENERIC_GET_GAME_ERROR", "An unexpected error occurred while getting game.");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
        }
    }

    @GET
    @ApiOperation(value = "Get all available faqs", notes = "Hola k ase")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Faq.class, responseContainer = "List"),
    })
    @Path("/faq")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getExamples() throws SQLException {
        List<Faq> buffer = sistema.getAllFaqs();
        Faq[] res = buffer.toArray(new Faq[]{});
        GenericEntity<Faq[]> entity = new GenericEntity<Faq[]>(res) {};
        return Response.status(201).entity(entity).build();
    }

}
