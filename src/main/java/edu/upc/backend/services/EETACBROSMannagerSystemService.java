package edu.upc.backend.services;

import edu.upc.backend.EBDBManagerSystem;
import edu.upc.backend.EETACBROSMannagerSystem;
import edu.upc.backend.EETACBROSMannagerSystemImpl;
import edu.upc.backend.classes.*;
import edu.upc.backend.exceptions.IncorrectPasswordException;
import edu.upc.backend.exceptions.UserNotFoundException;
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

        UsersList userslist = this.sistema.getUsersList();
        List<Item> itemlist = this.sistema.getItemList();
        PlayerList playerlist = this.sistema.getPlayerList();

        /*
        if (userslist.size() == 0 && sistema.getClass() != EBDBManagerSystem.class) {


            User user1 = new User("agente007","Manel Colominas Ruiz","Barcelona","Castelldefels");
            userslist.addUser(user1);

            int playerId = user1.getId();
            Player player1 = new Player(playerId, 100, 100, 100, 100, 100);
            playerlist.addPlayer(player1);
            Item item1 = new Item("Calculator", 200, 200, "📱", "Solve tricky math problems with ease.");
            Item item2 = new Item("Laptop", 200, 200, "💻", "Complete reports and projects efficiently.");
            Item item3 = new Item( "Notebook", 150, 150, "📓", "Keep track of class notes and ideas.");
            Item item4 = new Item( "Pen", 100, 100, "🖊️", "Write down important formulas and reminders.");
            Item item5 = new Item( "Old Mobile", 180, 180, "☎️", "Check messages and stay connected the old-school way.");
            Item item6 = new Item( "Energy Drink", 120, 120, "🥤", "Boost your focus and stay awake during long study sessions.");
            Item item7 = new Item( "Headphones", 160, 160, "🎧", "Concentrate on work and block out distractions.");
            Item item8 = new Item("Backpack", 200, 200, "🎒", "Carry all your items and tools wherever you go.");
            Item item9 = new Item( "USB Drive", 100, 100, "💾", "Store and transport your important files easily.");
            Item item10 = new Item( "Coffee", 100, 100, "☕", "Recharge your energy and stay productive.");
            itemlist.add(item1);
            itemlist.add(item2);
            itemlist.add(item3);
            itemlist.add(item4);
            itemlist.add(item5);
            itemlist.add(item6);
            itemlist.add(item7);
            itemlist.add(item8);
            itemlist.add(item9);
            itemlist.add(item10);


        }

         */

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
        UsersList usersList = this.sistema.getUsersList();

        List<User> usuarises = usersList.getUserslist();

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
    public Response showItemList() {
        List<Item> itemList = this.sistema.getItemList();

        GenericEntity<List<Item>> entity = new GenericEntity<List<Item>>(itemList) {};
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
            this.sistema.logIn(user);
            return Response.status(Response.Status.CREATED).entity(user).build();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

        int playerId = request.getPlayerId();
        User user = sistema.getUserById(playerId);

        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        String username = user.getUsername();

        List<Item> itemList = request.getItems();

        Player player = sistema.getPlayerById(playerId);
        if (player == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        player.setItems(itemList);

        logger.info("Purchase done for user " + username);

        return Response.status(Response.Status.CREATED).entity(request).build();
    }
}
