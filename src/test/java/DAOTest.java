import edu.upc.backend.classes.*;
import edu.upc.backend.database.*;
import edu.upc.backend.database.util.ObjectHelper;
import edu.upc.backend.database.util.QueryHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;

public class DAOTest {

    Logger log = Logger.getLogger(DAOTest.class);
    UserDAO _users;
    ItemDAO _items;
    PlayerDAO _players;
    UserItemDAO _inventory;
    FaqDAO _faqs;
    GameDAO _games;

    @Before
    public void Init()
    {
        _users = UserDAO.getInstance();
        _items = ItemDAO.getInstance();
        _players = PlayerDAO.getInstance();
        _inventory = UserItemDAO.getInstance();
        _faqs = FaqDAO.getInstance();
        _games = GameDAO.getInstance();
    }
    @After
    public void Finalize()
    {
        _users = null;
    }

    @Test
    public void addUserTest() throws Exception {
        User u = new User("Daniel","123456Ab","Dani","daniel@gmail.com");
        int id = _users.registerUser(u);
    }




    @Test
    public void getUsersTest() throws Exception {
        List<User> userList = _users.getUsers();

        for(User user : userList)
        {
            log.info(user.toString());
        }
    }

    @Test
    public void getUserTest() throws Exception {
        User user = _users.getUserById(3);

        log.info(user.toString());

    }
    @Test
    public void updateUserTest() throws Exception {
        int id = 3;
        User user = new User("Carlos","udsuhaiha","Carlosxd","carlos@gmail.com");
        user.setId(id);
        _users.updateUser(user);

        User user2 = _users.getUserById(3);
        log.info(user2.toString());

    }

    @Test
    public void getIdByUsername()
    {

    }

/*    @Test
    public void deleteUserTest() throws Exception {
        int id = 4;
        _users.deleteUser(4);


    }*/

    @Test
    public void getUserByUsernameTest() throws SQLException {
        String username = "wenjie2";
        User res = _users.getUserByUsername(username);
        log.info(res.toString());
    }

    @Test
    public void getItemList() throws Exception {
        List<Item> res = _items.getItemlist();

        for(Item i : res) log.info(i.getName());
    }

    @Test
    public void PlayerFields()
    {
        String[] f = ObjectHelper.getFields(Player.class);
        for(String name : f) log.info(name);
    }

    @Test
    public void PlayerTest() throws Exception {
        _players.addPlayer(1);

    }

    @Test
    public void PlayerQueryTest() throws  Exception
    {
        log.info(QueryHelper.createQuerySELECT(Player.class));
    }

    @Test
    public void PlayerByUserId() throws Exception {
        Player player = (Player) _players.getPlayerbyUserId(1);
        log.info(player.toString());
    }

    @Test
    public void PlayerUpdateTest() throws Exception {
        Player player = new Player(5,50,2,3,7,42);
        _players.updatePlayer(player);
    }

    @Test
    public void PlayerDeleteTest() throws Exception
    {
        Player player = new Player(6,0,0,0,0,0);
        _players.deletePlayer(player);
    }

    @Test
    public void ListPlayersTest() throws Exception {
        List<Object> res = Collections.singletonList(_players.getPlayers());

        for(Object o : res) log.info(o.toString());
    }

    @Test
    public void getItemByIdTest() throws Exception
    {
        Item res = _items.getItemById(4);
        log.info(res.toString());
    }

    @Test
    public void getAllItemTest() throws Exception
    {
        List<Item> res = _items.getItemlist();
        for(Item i : res) log.info(i.toString());
    }

    @Test
    public void buyTest() throws Exception
    {
        UserItem test = new UserItem(2,5,7);
        _inventory.addUserItem(test);
    }

    @Test
    public void getUserInventory() throws Exception
    {
        List<UserItem> res = _inventory.getUserItems(1);
        for(UserItem ui : res) log.info(res.toString());
    }

    @Test
    public void testFaq() throws SQLException {
        Faq faq = _faqs.get(4);
        log.info(faq.toString());
    }

    @Test
    public void testFaqs() throws SQLException {
        List<Faq> faqs = _faqs.getAll();
        for(Faq faq : faqs) log.info(faq.toString());
    }

    @Test
    public void testQuery()
    {
        String query = QueryHelper.createSELECTLast(User.class);
        log.info(query);
    }
    @Test
    public void testCore() throws Exception
    {
        User user = new User("Maria1","Maria Perez","maria@gmail.com","123456Ab");
        int userId = _users.registerUser(user);
        int playerId = _players.addPlayer(userId);
        int gameId = _games.add(userId,playerId);

    }

    @Test
    public void testGame() throws Exception
    {
        int playerId = ((Game)_games.getbyUserId(6)).getId();
        log.info(playerId);
    }
}
