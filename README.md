classDiagram
direction BT
class BuyRequest {
  + BuyRequest() 
  + BuyRequest(int) 
  - List~Item~ items
  - int userId
   int totalItems
   List~Item~ items
   int userId
}
class DAOTest {
  + DAOTest() 
  + ListPlayersTest() void
  + getItemList() void
  + PlayerDeleteTest() void
  + testFaq() void
  + updateUserTest() void
  + TopPlayersTest() void
  + PlayerQueryTest() void
  + getUserByUsernameTest() void
  + getAllItemTest() void
  + testHistory() void
  + PlayerFields() void
  + Init() void
  + getUsersTest() void
  + addUserTest() void
  + testCore() void
  + PlayerByUserId() void
  + buyTest() void
  + Finalize() void
  + PlayerUpdateTest() void
  + getItemByIdTest() void
  + getUserInventory() void
  + testQuery() void
  + testFaqs() void
  + getIdByUsername() void
  + PlayerTest() void
  + getUserTest() void
  + testGame() void
}
class DBUtils {
  + DBUtils() 
  + String DB_HOST
  + String DB_PORT
  + String DB_USER
  + retrieveUserID() int
   String db
   String DB_PORT
   String dbPasswd
   Connection connection
   String DB_USER
   String DB_HOST
}
class EBDBManagerSystem {
  - EBDBManagerSystem() 
  + registerUser(User) void
  + logIn(User) User
  + getGameByUserId(int) Game
  + getRanking(int) RankingResponse
  + getUserByUsername(String) User
  + updateUserData(User) User
  + registerPurchase(BuyRequest) void
  + getPlayerByUserId(int) Player
  + getUserById(int) User
  + clear() void
  + addPlayer(Player) void
  + getPlayerById(int) Player
  + getUserItems(int) List~Item~
  + deleteUserData(int) void
  + updateUserScore(User) User
   List~User~ usersListDatabase
   List~Item~ itemList
   EBDBManagerSystem instance
   List~Faq~ allFaqs
   PlayerList playerList
}
class EETACBROSMannagerSystem {
<<Interface>>
  + clear() void
  + getRanking(int) RankingResponse
  + deleteUserData(int) void
  + getGameByUserId(int) Game
  + updateUserData(User) User
  + updateUserScore(User) User
  + registerPurchase(BuyRequest) void
  + getPlayerByUserId(int) Player
  + getUserByUsername(String) User
  + logIn(User) User
  + registerUser(User) void
  + getUserById(int) User
  + getPlayerById(int) Player
  + addPlayer(Player) void
  + getUserItems(int) List~Item~
   List~User~ usersListDatabase
   List~Item~ itemList
   List~Faq~ allFaqs
   PlayerList playerList
}
class EETACBROSMannagerSystemService {
  + EETACBROSMannagerSystemService() 
  + getPlayerByUserId(int) Response
  + getRanking(int) Response
  + showUserItems(int) Response
  + updateUserData(User) Response
  + getGameByUserId(int) Response
  + showItemList() Response
  + registerUser(User) Response
  + logIn(User) Response
  + shopBuyItems(BuyRequest) Response
  + showUsersList() Response
  + deleteUser(int) Response
  + updateUserScore(User) Response
   Response faqs
}
class EmojiManager {
  + EmojiManager() 
  + getEmoji(int) String
}
class Enemy {
  + Enemy(int, int, int) 
  + Enemy() 
}
class Entity {
  + Entity() 
  + Entity(int, int, int) 
  ~ float Y
  ~ int hp
  ~ float X
   float Y
   float X
   int hp
}
class ErrorResponse {
  + ErrorResponse(String, String) 
  - String code
  - String message
   String code
   String message
}
class ExampleTest {
  + ExampleTest() 
  + setUp() void
  + tearDown() void
  + testIDs() void
}
class Faq {
  + Faq() 
  + Faq(int, String, String) 
  - String answer
  - int id
  - String question
  + toString() String
   String question
   int id
   String answer
}
class FaqDAO {
  + FaqDAO() 
  + delete(Faq) void
  + add(Faq) void
  + get(int) Faq
  + update(Faq) void
   List~Faq~ all
   FaqDAO instance
}
class Game {
  + Game() 
  + Game(int, int) 
  - int id
  - int level
   int id
   int level
}
class GameDAO {
  + GameDAO() 
  + getByUserId(int) Game
  + update(Game) void
  + delete(Game) void
  + add(int, int) int
   List~Game~ all
   GameDAO instance
}
class GameNotFoundException {
  + GameNotFoundException(String) 
}
class History {
  + History(int, int) 
  + History() 
  ~ int userId
  ~ int score
  + toString() String
   int score
   int userId
}
class HistoryDao {
  + HistoryDao() 
  + save(User) void
  + getAllPointsByUser(int) List~Integer~
   HistoryDao instance
}
class IFaqDAO {
<<Interface>>
  + delete(Faq) void
  + get(int) Faq
  + update(Faq) void
  + add(Faq) void
   List~Faq~ all
}
class IGameDAO {
<<Interface>>
  + add(int, int) int
  + update(Game) void
  + delete(Game) void
  + getByUserId(int) Game
   List~Game~ all
}
class IHistoryDao {
<<Interface>>
  + getAllPointsByUser(int) List~Integer~
  + save(User) void
}
class IItemDAO {
<<Interface>>
   List~Item~ itemlist
}
class IPlayerDAO {
<<Interface>>
  + updatePlayer(Player) void
  + deletePlayer(Player) void
  + addPlayer(int) int
  + getPlayer(int) Player
  + getPlayerbyUserId(int) Player
   List~Player~ players
}
class IUserDAO {
<<Interface>>
  + deleteUser(int) void
  + getTopUsers(int) List~User~
  + getUserById(int) User
  + updateUser(User) void
   List~User~ users
}
class IUserItemDAO {
<<Interface>>
  + getUserItems(int) List~UserItem~
  + addUserItem(UserItem) void
  + updateUserItem(UserItem) void
}
class InsufficientMoneyException {
  + InsufficientMoneyException(String) 
}
class Item {
  + Item() 
  + Item(String, int, int, String, String, int) 
  ~ int durability
  ~ String name
  ~ String emoji
  ~ int id
  ~ int quantity
  ~ int price
  ~ String description
  + toString() String
   int quantity
   String name
   String description
   int durability
   int id
   int price
   String emoji
}
class ItemDAO {
  - ItemDAO() 
  + getItemById(int) Item
   ItemDAO instance
   List~Item~ itemlist
}
class Main {
  + Main() 
  + main(String[]) void
  + startServer() HttpServer
}
class MyExceptionMapper {
  + MyExceptionMapper() 
  + toResponse(Exception) Response
}
class ObjectHelper {
  + ObjectHelper() 
  + getFields(Object) String[]
  + getter(Object, String) Object
  + setter(Object, String, Object) void
  + getFields(Class) String[]
}
class Pair~F, S~ {
  + Pair(F, S) 
  - F first
  - S second
   F first
   S second
}
class Player {
  + Player() 
  + Player(int, int, Integer, float, float, int) 
  ~ int score
  ~ float Y
  ~ int speed
  ~ float X
  ~ int id
  ~ int hp
  + toString() String
   int score
   float Y
   float X
   int id
   Integer speed
   int hp
}
class PlayerDAO {
  - PlayerDAO() 
  + getPlayer(int) Player
  + getPlayerbyUserId(int) Player
  + updatePlayer(Player) void
  + addPlayer(int) int
  + deletePlayer(Player) void
   PlayerDAO instance
   List~Player~ players
}
class PlayerList {
  + PlayerList() 
  - List~Player~ playerList
  + add(Player) void
  + addPlayer(Player) void
  + size() int
  + getPlayerByPlayerId(int) Player
   List~Player~ playerList
}
class PlayerNotFoundException {
  + PlayerNotFoundException(String) 
}
class QueryHelper {
  + QueryHelper() 
  + createQuerySelectAll(Class) String
  + createQueryDELETE(Object) String
  + createUPDATEMASTERFUNCTION(Class, HashMap~String, Object~, HashMap~String, Object~) String
  + createQuerySELECT(Object) String
  + createQyerySELECTSOME(Class) String
  + createQueryINSERTOneToOne(Object, Object) String
  + createQuerySELECT(Class) String
  + createQuerySELECTSOME(Class, HashMap) String
  + createSELECTLast(Class) String
  + createQueryUPDATE(Object) String
  + createQueryINSERTOneToMany(Object, Integer, Object) String
  + createQuerySELECTID(Object) String
  + createSELECTMASTERFUNCTION(Class, HashMap~String, Object~) String
  + createQueryMasterFunction(String, Class, HashMap~String, Object~) String
  + createQueryINSERT(Object) String
  - buildWhereClause(HashMap~String, Object~) String
}
class RandomUtils {
  + RandomUtils() 
   String id
}
class RankingEntry {
  + RankingEntry(int, String, int) 
  + RankingEntry() 
  - int position
  - String username
  - int score
   int score
   int position
   String username
}
class RankingResponse {
  + RankingResponse() 
  + RankingResponse(List~RankingEntry~, RankingEntry) 
  - List~RankingEntry~ podium
  - RankingEntry userEntry
   List~RankingEntry~ podium
   RankingEntry userEntry
}
class ResourceManager {
  - ResourceManager() 
  + get(String) String
   ResourceManager instance
   String vars
}
class Session {
<<Interface>>
  + findId(String, HashMap) int
  + findAll(Class, HashMap) List~Object~
  + findId(Object) int
  + findAll(Class) List~Object~
  + close() void
  + save(Object) void
  + get(Class, int) Object
  + query(String, Class, HashMap) List~Object~
  + update(Object) void
  + delete(Object) void
  + findLast(Class) Object
}
class SessionBuilder {
  + SessionBuilder() 
  + build() Session
   String port
   String password
   String host
   String database
   String user
}
class SessionImpl {
  + SessionImpl(Connection) 
  + findId(Object) int
  + findAll(Class, HashMap) List~Object~
  + query(String, Class, HashMap) List~Object~
  + save(Object) void
  + findLast(Class) Object
  + findAll(Class) List~Object~
  + get(Class, int) Object
  + update(Class, HashMap~String, Object~, HashMap~String, Object~) void
  + update(Object) void
  + findId(String, HashMap) int
  + delete(Object) void
  + close() void
}
class SessionLogged {
  + SessionLogged(Session) 
  + query(String, Class, HashMap) List~Object~
  + findAll(Class, HashMap) List~Object~
  + delete(Object) void
  + findId(String, HashMap) int
  + update(Object) void
  + findAll(Class) List~Object~
  + findId(Object) int
  + save(Object) void
  + findLast(Class) Object
  + close() void
  + get(Class, int) Object
}
class User {
  + User(String, String, String, String) 
  + User() 
  - int score
  - int id
  - String name
  - int coins
  - String email
  - String username
  - String password
  + toString() String
   String name
   String password
   int coins
   int id
   String email
   int score
   String username
}
class UserDAO {
  - UserDAO() 
  + getTopUsers(int) List~User~
  + registerUser(User) int
  + getUserById(int) User
  + getUserByUsername(String) User
  + deleteUser(int) void
  + updateUser(User) void
   UserDAO instance
   List~User~ users
}
class UserItem {
  + UserItem() 
  + UserItem(int, int, int) 
  - int userId
  - int itemId
  - int quantity
  + toString() String
   int quantity
   int itemId
   int userId
}
class UserItemDAO {
  - UserItemDAO() 
  + getUserItems(int) List~UserItem~
  + addUserItem(UserItem) void
  + updateUserItem(UserItem) void
   UserItemDAO instance
}
class UserNotFoundException {
  + UserNotFoundException(String) 
}
class UserOrPasswordInvalidException {
  + UserOrPasswordInvalidException(String) 
}
class Utils {
  + Utils() 
  + computeOrder(String, String[]) String[]
  + valueParser(StringBuffer, Object) void
  + CapitalizeFirst(String) String
  + computeOrder(String, Set~String~) String[]
  + formatUrl(ResourceManager) String
  + getKeyByValue(Map~Integer, E~, E) Integer
  + castList(List~Object~) List~T~
}

BuyRequest "1" *--> "items *" Item 
DAOTest  ..>  DAOTest 
DAOTest  ..>  Faq 
DAOTest "1" *--> "_faqs 1" FaqDAO 
DAOTest  ..>  Game 
DAOTest "1" *--> "_games 1" GameDAO 
DAOTest "1" *--> "_scores 1" HistoryDao 
DAOTest  ..>  Item 
DAOTest "1" *--> "_items 1" ItemDAO 
DAOTest  ..>  ObjectHelper 
DAOTest  ..>  Player : «create»
DAOTest "1" *--> "_players 1" PlayerDAO 
DAOTest  ..>  QueryHelper 
DAOTest  ..>  User : «create»
DAOTest "1" *--> "_users 1" UserDAO 
DAOTest  ..>  UserItem : «create»
DAOTest "1" *--> "_inventory 1" UserItemDAO 
EBDBManagerSystem  ..>  BuyRequest 
EBDBManagerSystem "1" *--> "_instance 1" EBDBManagerSystem 
EBDBManagerSystem  ..>  EETACBROSMannagerSystem 
EBDBManagerSystem  ..>  Faq 
EBDBManagerSystem  ..>  Game 
EBDBManagerSystem  ..>  Item 
EBDBManagerSystem  ..>  Player 
EBDBManagerSystem  ..>  PlayerList 
EBDBManagerSystem  ..>  RankingResponse 
EBDBManagerSystem  ..>  User 
EETACBROSMannagerSystem  ..>  BuyRequest 
EETACBROSMannagerSystem  ..>  Faq 
EETACBROSMannagerSystem  ..>  Game 
EETACBROSMannagerSystem  ..>  Item 
EETACBROSMannagerSystem  ..>  Player 
EETACBROSMannagerSystem  ..>  PlayerList 
EETACBROSMannagerSystem  ..>  RankingResponse 
EETACBROSMannagerSystem  ..>  User 
EETACBROSMannagerSystemService  ..>  BuyRequest 
EETACBROSMannagerSystemService "1" *--> "sistema 1" EETACBROSMannagerSystem 
EETACBROSMannagerSystemService  ..>  User 
Enemy  -->  Entity 
ExampleTest  ..>  DBUtils 
ExampleTest "1" *--> "manager 1" EBDBManagerSystem 
ExampleTest  ..>  ExampleTest 
FaqDAO  ..>  Faq 
FaqDAO "1" *--> "_instance 1" FaqDAO 
FaqDAO  ..>  IFaqDAO 
GameDAO  ..>  Game 
GameDAO "1" *--> "_instance 1" GameDAO 
GameDAO  ..>  IGameDAO 
HistoryDao "1" *--> "_instance 1" HistoryDao 
HistoryDao  ..>  IHistoryDao 
HistoryDao  ..>  User 
IFaqDAO  ..>  Faq 
IGameDAO  ..>  Game 
IHistoryDao  ..>  User 
IItemDAO  ..>  Item 
IPlayerDAO  ..>  Player 
IUserDAO  ..>  User 
IUserItemDAO  ..>  UserItem 
ItemDAO  ..>  IItemDAO 
ItemDAO  ..>  Item 
ItemDAO "1" *--> "_instance 1" ItemDAO 
PlayerDAO  ..>  IPlayerDAO 
PlayerDAO  ..>  Player 
PlayerDAO "1" *--> "_instance 1" PlayerDAO 
PlayerList "1" *--> "playerList *" Player 
RankingResponse "1" *--> "podium *" RankingEntry 
ResourceManager "1" *--> "_instance 1" ResourceManager 
SessionBuilder  ..>  Session 
SessionBuilder  ..>  SessionBuilder 
SessionImpl  ..>  Session 
SessionLogged  ..>  Session 
SessionLogged "1" *--> "_session 1" Session 
UserDAO  ..>  IUserDAO 
UserDAO  ..>  User 
UserDAO "1" *--> "_instance 1" UserDAO 
UserItemDAO  ..>  IUserItemDAO 
UserItemDAO  ..>  UserItem 
UserItemDAO "1" *--> "_instance 1" UserItemDAO 
Utils  ..>  ResourceManager 
