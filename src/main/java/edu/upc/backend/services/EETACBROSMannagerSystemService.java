package edu.upc.backend.services;

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

import java.util.List;

@Api(value = "/eetacbros", description = "Endpoint de biblioteca Service")
@Path("/eetacbros")
public class EETACBROSMannagerSystemService {

    private static final Logger log = Logger.getLogger(EETACBROSMannagerSystemService.class);
    private EETACBROSMannagerSystemImpl sistema;

    public EETACBROSMannagerSystemService() {
        // Use the singleton instance
        this.sistema = EETACBROSMannagerSystemImpl.getInstance();

        UsersList userslist = this.sistema.getUsersList();

        if (userslist.size() == 0) {

            User user1 = new User("agente007","Manel Colominas Ruiz","Barcelona","Castelldefels");
            userslist.addUser(user1);

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
        UsersList usersList = this.sistema.getUsersList();

        List<User> usuarises = usersList.getUserslist();

        GenericEntity<List<User>> entity = new GenericEntity<List<User>>(usuarises) {};
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
    public Response addUser(User user) {
        UsersList usersList = this.sistema.getUsersList();
        User userExists = usersList.getUserByUsername(user.getUsername());
        log.info(user.getUsername());

        if (userExists != null) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("Username not available")
                    .build();
        } else {

            this.sistema.addUser(user);
            return Response.status(Response.Status.CREATED)
                    .entity(user)
                    .build();
        }

    }
    // LOGIN
    @POST
    @Path("user/loginCredentials")
    @ApiOperation(value = "Login d'usuari", notes = "Comprova username i password")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Login correcte", response = User.class),
            @ApiResponse(code = 400, message = "Falten camps"),
            @ApiResponse(code = 401, message = "Credencials incorrectes")
    })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginCredentials credentials) {

        if (credentials == null ||
                credentials.getUsername() == null ||
                credentials.getPassword() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Falten camps username o password")
                    .build();
        }

        // Fem servir directament la UsersList existent
        UsersList usersList = this.sistema.getUsersList();
        User user = usersList.getUserByUsername(credentials.getUsername());

        if (user == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Usuari o contrasenya incorrectes")
                    .build();
        }

        if (!user.getPassword().equals(credentials.getPassword())) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Usuari o contrasenya incorrectes")
                    .build();
        }

        // Login correcte → retornem l'usuari
        return Response.ok(user).build();
    }
    // LOG IN
    @POST
    @Path("user/loginUser")
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
            sistema.logIn(username, password);
        }
        catch (UserNotFoundException e) {
            return Response.status(404)
                    .entity("User not found")
                    .build();
        }
        catch (IncorrectPasswordException e) {
            return Response.status(400)
                    .entity("Incorrect password")
                    .build();
        }

        return Response.status(201)
                .entity(user)
                .build();
    }
}