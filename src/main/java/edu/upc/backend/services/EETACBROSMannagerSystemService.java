package edu.upc.backend.services;

import edu.upc.backend.EETACBROSMannagerSystemImpl;
import edu.upc.backend.classes.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;

@Api(value = "/eetacbros", description = "Endpoint de biblioteca Service")
@Path("/eetacbros")
public class EETACBROSMannagerSystemService {

    private EETACBROSMannagerSystemImpl sistema;

    public EETACBROSMannagerSystemService() {
        // Use the singleton instance
        this.sistema = EETACBROSMannagerSystemImpl.getInstance();

        UsersList userslist = this.sistema.getUsersList();

        if (userslist.size() == 0) {

            User user1 = new User("agemte007","Manel","Colominas", "Ruiz", "11/02/2003","Barcelona","Castelldefels");
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
    @Path("user/register/{username}/{nom}/{cognom1}/{cognom2}/{email}/{password}/{datanaixement}")
    @ApiOperation(value = "Registrar un nou usuari", notes = "Registrar un nou usuari")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Usuari nou registrat", response = User.class),
            @ApiResponse(code = 404, message = "Error al registrar l'usuari")
    })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(@PathParam("username") String username,@PathParam("nom") String nom, @PathParam("cognom1") String cognom1, @QueryParam("cognom2") String cognom2, @QueryParam("email") String email,@QueryParam("password") String password,@QueryParam("datanaixement") String datanaixement) {
        UsersList usersList = this.sistema.getUsersList();
        User userExists = usersList.getUserByUsername(username);

        if (userExists != null) {
            return Response.ok("Username not available").build();
        }
        else{
            User user = new User(username,nom,cognom1,cognom2,email, password,datanaixement);
            this.sistema.addUser(user);
            return Response.ok(user).build();
        }
    }

}