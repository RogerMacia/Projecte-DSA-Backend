package edu.upc.backend;

import edu.upc.backend.classes.*;
import edu.upc.backend.exceptions.*;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

// Per a que en faci les traces li he passat al chat i m'ho ha fet autotamaticament ell

public class EETACBROSMannagerSystemImpl implements EETACBROSMannagerSystem {
    private static EETACBROSMannagerSystemImpl instance;

    private UsersList usersList;
    private PlayerList playerList;
    private List<Item> itemList;

    final static Logger logger = Logger.getLogger(EETACBROSMannagerSystemImpl.class);

    private EETACBROSMannagerSystemImpl() {
        this.usersList = new UsersList();
        this.itemList = new ArrayList();
        this.playerList = new PlayerList();
        logger.info("Constructor EETACbROSManagerSystemImpl inicialitzat");
    }

    public static EETACBROSMannagerSystemImpl getInstance() {
        logger.info("Inici getInstance()");
        if (instance == null) {
            instance = new EETACBROSMannagerSystemImpl();
            logger.warn("Instancia creada");
        }
        logger.info("Fi getInstance() -> " + instance);
        return instance;
    }

    public void addUser(User user) {
        logger.info("Inici addLector(" + user + ")");
        if (user != null) {
            this.usersList.addUser(user);
            logger.info("Fi addLector() -> Lector afegit: " + user);
        } else {
            logger.warn("Intent d’afegir lector nul");
        }
    }

    public void addPlayer(Player player) {
        logger.info("Inici addPlayer(" + player + ")");
        if (player != null) {
            this.playerList.add(player);
            logger.info("Fi addLector() -> Lector afegit: " + player);
        } else {
            logger.warn("Intent d’afegir lector nul");
        }
    }

    public UsersList getUsersList() {
        logger.info("Inici getLlistaLectors()");
        logger.info("Fi getLlistaLectors() -> Retorna: " + usersList);
        return this.usersList;
    }

    public List<Item> getItemList() {
        return this.itemList;
    }

    public PlayerList getPlayerList() {
        return this.playerList;
    }

    public User getUserByUsername(String username) {
        logger.info("Inici getLector(username=" + username + ")");
        User user = usersList.getUserByUsername(username);
        logger.info("Fi getLector() -> Retorna: " + user);
        return user;
    }

    public Player getPlayerById(int id) {
        logger.info("Inici getPlayerById(" + id + ")");
        Player player = playerList.getPlayerByPlayerId(id);
        logger.info("Fi getPlayerById() -> Retorna: " + player);
        return player;
    }

    public User getUserById(int userId) {
        logger.info("Inici getUserById(" + userId + ")");
        User user = usersList.getUserById(userId);
        logger.info("Fi getUserById() -> Retorna: " + user);
        return user;
    }

    public Item getItemById(Integer id) {
        for (Item p : itemList) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public void logIn (String username, String password) {
        User u = getUserByUsername(username);
        if (u == null) {
            logger.error("User " + username + " not found");
            throw new UserNotFoundException();
        }
        else {
            logger.info("User found");
            if (password.equals(u.getPassword())) {
                logger.info("User with correct credentials");
            }
            else {
                logger.error("Incorrect password");
                throw new IncorrectPasswordException();
            }
        }
    }

    public void clear() {
        logger.info("Inici clear()");
        this.usersList = new UsersList();
        logger.info("Fi clear() -> Llista de lectors buidada");
    }
}
