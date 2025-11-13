package edu.upc.backend.services;

import edu.upc.backend.EETACBROSMannagerSystemImpl;
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

            User user1 = new User("agemte007","Manel Colominas Ruiz","Barcelona","Castelldefels");
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
}