package edu.upc.backend;

import edu.upc.backend.classes.*;
import edu.upc.backend.database.*;
import edu.upc.backend.exceptions.*;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

// Eetac bros Database manager
public class EBDBManagerSystem implements EETACBROSMannagerSystem {

    private static final Logger log = Logger.getLogger(EBDBManagerSystem.class);
    private static EBDBManagerSystem _instance;

    private EBDBManagerSystem() {}

    public static EBDBManagerSystem getInstance()
    {
        if(_instance == null) _instance = new EBDBManagerSystem();
        return _instance;
    }

    @Override
    public void registerUser(User user) throws SQLException, UserOrPasswordInvalidException {
        UserDAO _users = UserDAO.getInstance();
        PlayerDAO _players = PlayerDAO.getInstance();
        GameDAO _games = GameDAO.getInstance();
        User userExists = _users.getUserByUsername(user.getUsername());

        if (userExists != null) {
            throw new UserOrPasswordInvalidException("Username is not available");
        }
        else {
            try {

                int userId = _users.registerUser(user);
                int playerId = _players.addPlayer(userId);
                int gameId = _games.add(userId,playerId);
            }
            catch (Exception e) {
                log.error("Error: " + e.getMessage() );
            }
        }
    }

    @Override
    public User logIn(User user) throws UserOrPasswordInvalidException {

        UserDAO _users = UserDAO.getInstance();
        User userExists = null;
        try {
            userExists = _users.getUserByUsername(user.getUsername());
        } catch (SQLException e) {
            log.error("Database error during login: " + e.getMessage());
            // Re-throw as a generic runtime exception or a custom LoginFailedException if needed
            throw new RuntimeException("Login failed due to a database error.", e);
        }
        String password = user.getPassword();

        if (userExists == null) {
            throw new UserOrPasswordInvalidException("Invalid username or password.");
        }
        else {
            if (password.equals(userExists.getPassword())) {
                return userExists;
            } else {
                throw new UserOrPasswordInvalidException("Invalid username or password.");
            }
        }
    }

    @Override
    public void registerPurchase(BuyRequest buyRequest) throws UserNotFoundException, InsufficientMoneyException, Exception {
        UserDAO userDAO = UserDAO.getInstance();

        User user = userDAO.getUserById(buyRequest.getUserId());
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        int totalCost = 0;
        for (Item item : buyRequest.getItems()) {
            totalCost += item.getPrice() * item.getQuantity();
        }

        if (user.getCoins() < totalCost) {
            throw new InsufficientMoneyException("Insufficient money to complete the purchase.");
        }

        user.setCoins((user.getCoins() - totalCost));
        userDAO.updateUser(user);

        for (Item item : buyRequest.getItems()) {
            UserItemDAO userItemDAO = UserItemDAO.getInstance();
            userItemDAO.addUserItem(new UserItem(user.getId(),item.getId(),item.getQuantity()));
        }


    }

    public User updateUserData(User user) throws Exception {
        UserDAO _users = UserDAO.getInstance();
        User userExists = null;

        userExists = _users.getUserById(user.getId());
        if (userExists == null){
            throw new UserNotFoundException("User not found");
        }
        try {
            userExists.setName(user.getName());
            userExists.setEmail(user.getEmail());
            userExists.setPassword(user.getPassword());
            userExists.setUsername(user.getUsername());
            _users.updateUser(userExists);
        }
        catch (SQLException e) {
            log.error("Database error during updating: " + e.getMessage());
        }
        return userExists;
    }

    public User updateUserScore(User user) throws Exception {
        UserDAO _users = UserDAO.getInstance();
        HistoryDao _points = HistoryDao.getInstance();
        User userExists = null;
        userExists = _users.getUserById(user.getId());
        if (userExists == null){
            throw new UserNotFoundException("User not found");
        }
        try {
            userExists.setCoins(user.getCoins());
            userExists.setScore(user.getScore());
            _points.save(user);
            _users.updateUser(userExists);
        }
        catch (SQLException e) {
            log.error("Database error during updating: " + e.getMessage());
        }
        return userExists;
    }

    public void deleteUserData(int id) throws Exception {
        UserDAO _users = UserDAO.getInstance();
        User userExists = _users.getUserById(id);
        if (userExists == null){
            throw new UserNotFoundException("User not found");
        }
        try {
            _users.deleteUser(id);
        }
        catch (SQLException e) {
            log.error("Database error during deleting: " + e.getMessage());
        }
    }

    @Override
    public void addPlayer(Player player) {

    }

    @Override
    public  List<User> getUsersListDatabase() {
        UserDAO _users = UserDAO.getInstance();
        List<User> list = null;
        try {
            list = _users.getUsers();
        }
        catch (Exception e) {
            log.error("Error: " + e.getMessage());
        }
        return list;
    }

    public List<Item> getItemList() {
        ItemDAO _itemsInstance = ItemDAO.getInstance();
        List<Item> items = null;
        try {
            items = _itemsInstance.getItemlist();
        }
        catch (Exception e)
        {
            log.error("Error: " + e.getMessage() );
        }
        return  items;
    }

    @Override
    public PlayerList getPlayerList() {
        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        UserDAO _users = UserDAO.getInstance();
        User res = null;
        try{
            res = _users.getUserByUsername(username);
        }
        catch (Exception e)
        {
            log.error("Error: " + e.getMessage() );
        }
        return  res;
    }

    @Override
    public Player getPlayerById(int id) {
        return null;
    }

    @Override
    public User getUserById(int userId) {
        UserDAO _users = UserDAO.getInstance();
        User res = null;
        try{
            res = _users.getUserById(userId);
        }
        catch (Exception e)
        {
            log.error("Error: " + e.getMessage() );
        }
        return  res;
    }

    public List<Item> getUserItems(int userId){
        UserItemDAO _userItemInstance = UserItemDAO.getInstance();
        List<Item> items = null;
        HashMap<String,Object> paramsSearch = new HashMap<>();
        //paramsSearch.put("userId",userId);
        try {
            List<UserItem> userItems = _userItemInstance.getUserItems(userId);
            items = new ArrayList<>();
            for (UserItem userItem : userItems) {
                ItemDAO _itemsInstance = ItemDAO.getInstance();
                Item item = _itemsInstance.getItemById(userItem.getItemId());
                item.setQuantity(userItem.getQuantity());
                items.add(item);
            }
        }
        catch (Exception e)
        {
            log.error("Error: " + e.getMessage() );
        }
        return  items;
    }

    public RankingResponse getRanking(int userId) throws UserNotFoundException {
        int podiumSize = 4;
        List<User> allUsers = getUsersListDatabase();

        if (allUsers == null || allUsers.isEmpty()) {
            return new RankingResponse(new ArrayList<>(), null);
        }

        allUsers.sort(Comparator.comparingInt(User::getScore).reversed());

        List<RankingEntry> podium = new ArrayList<>();
        for (int i = 0; i < Math.min(podiumSize, allUsers.size()); i++) {
            User user = allUsers.get(i);
            podium.add(new RankingEntry(i + 1, user.getUsername(), user.getScore()));
        }

        RankingEntry userEntry = null;
        boolean userInPodium = false;
        for (int i = 0; i < allUsers.size(); i++) {
            if (allUsers.get(i).getId() == userId) {
                User user = allUsers.get(i);
                userEntry = new RankingEntry(i + 1, user.getUsername(), user.getScore());
                if (i < podiumSize) {
                    userInPodium = true;
                }
                break;
            }
        }

        if (userEntry == null) {
            throw new UserNotFoundException("User with ID " + userId + " not found in ranking.");
        }

        if (userInPodium) {
            return new RankingResponse(podium, null);
        } else {
            return new RankingResponse(podium, userEntry);
        }
    }



    @Override
    public void clear() {

    }

    @Override
    public Player getPlayerByUserId(int userId) throws Exception {
        PlayerDAO players = PlayerDAO.getInstance();
        Player player = null;
        try {
            player = players.getPlayerbyUserId(userId);
        }
        catch (Exception e) {
            log.error("Error: " + e.getMessage());
        }
        return player;
    }

    @Override
    public Game getGameByUserId(int userId) throws SQLException {
        GameDAO games = GameDAO.getInstance();
        return games.getByUserId(userId);
    }

    @Override
    public List<Faq> getAllFaqs() throws SQLException {
        FaqDAO faqs = FaqDAO.getInstance();
        return faqs.getAll();
    }

    @Override
    public List<Integer> getPointsHistory(int userId) throws Exception
    {
        // Actually esto es una funcion de orden superior.
        HistoryDao _points = HistoryDao.getInstance();
        return _points.getAllPointsByUser(userId);
    }

    @Override
    public void updateGame(int id,Game game) throws Exception
    {
        GameDAO games= GameDAO.getInstance();
        // Extra steps para garantizar que no haya nadie haciendo spoofing de peticiones.
        Game g = games.getByUserId(id);
        g.setLevel(game.getLevel());
        games.update(g);
    }

    @Override
    public void updatePlayer(int id,Player player) throws Exception
    {
        PlayerDAO players = PlayerDAO.getInstance();
        Player p = players.getPlayerbyUserId(id);
        //players.updatePlayer(player);
        p.setHp(player.getHp());
        p.setSpeed(player.getSpeed());
        p.setX(player.getX());
        p.setY(player.getY());
        players.updatePlayer(p);
    }

    @Override
    public void updateUser(User user) throws Exception
    {
        UserDAO users = UserDAO.getInstance();
        User before = users.getUserById(user.getId());
        users.updateUser(user);
    }


}