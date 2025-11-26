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
    private GameList _games;

    final static Logger logger = Logger.getLogger(EETACBROSMannagerSystemImpl.class);

    private EETACBROSMannagerSystemImpl() {
        this.usersList = new UsersList();
        this.itemList = new ArrayList();
        this.playerList = new PlayerList();
        this._games = new GameList();
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

    @Override
    public void addUser(User user) {
        logger.info("Inici addUser(" + user + ")");
        if (user != null) {
            this.usersList.addUser(user);
            //region nou player
            User l_user = usersList.getUserByUsername(user.getUsername());
            int playerId = l_user.getId();
            Player player = new Player(playerId,0, 100, 100, 100, 0);
            addPlayer(player);
            //endregion nou player

            //region nova partida
            createGame(playerId);
            //endregion nova partida
            logger.info("Fi addUser() -> User afegit: " + user);
        } else {
            logger.warn("Intent d’afegir user nul");
        }
    }

    @Override
    public void addPlayer(Player player) {
        logger.info("Inici addPlayer(" + player + ")");
        if (player != null) {
            this.playerList.add(player);
            logger.info("Fi addPlayer() -> Lector afegit: " + player);
        } else {
            logger.warn("Intent d’afegir player nul");
        }
    }

    @Override
    public UsersList getUsersList() {
        logger.info("Inici getUsersList()");
        logger.info("Fi getUsersList() -> Retorna: " + usersList);
        return this.usersList;
    }

    @Override
    public List<Item> getItemList() {
        return this.itemList;
    }

    @Override
    public PlayerList getPlayerList() {
        return this.playerList;
    }

    @Override
    public User getUserByUsername(String username) {
        logger.info("Inici getUserByUsername(username=" + username + ")");
        User user = usersList.getUserByUsername(username);
        logger.info("Fi getUserByUsername() -> Retorna: " + user);
        return user;
    }

    @Override
    public Player getPlayerById(int id) {
        logger.info("Inici getPlayerById(" + id + ")");
        Player player = playerList.getPlayerByPlayerId(id);
        logger.info("Fi getPlayerById() -> Retorna: " + player);
        return player;
    }

    @Override
    public User getUserById(int userId) {
        logger.info("Inici getUserById(" + userId + ")");
        User user = usersList.getUserById(userId);
        logger.info("Fi getUserById() -> Retorna: " + user);
        return user;
    }

    @Override
    public Item getItemById(Integer id) {
        for (Item p : itemList) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    @Override
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

    //region games
    public void createGame(int playerId)
    {
        _games.create(playerId);
        logger.info(String.format("A new game for player with %d has been created", playerId));
    }

    public Game findGame(int playerId)
    {
        Game res = _games.find(playerId);
        if(res == null) logger.warn(String.format("Player with id %d has already a game.",playerId));
        return res;
    }

    public void removeGame(int playerId)
    {
        _games.remove(playerId);
    }

    public void updateGame(Game game)
    {
        Game l_game = _games.find(game.getPlayerId());
        if(l_game == null)
        {
            logger.warn("Game not found.");
            return;
        }
        l_game.setScore(game.getScore());
    }
    //endregion games

    @Override
    public void clear() {
        logger.info("Inici clear()");
        this.usersList = new UsersList();
        logger.info("Fi clear() -> Llista de usuaris buidada");
    }
}
