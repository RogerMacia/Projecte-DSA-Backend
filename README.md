# Backend per el projecte de DSA

## Esquema

```mermaid
classDiagram

## %% ============================================================
## %%                    FUNCIONAMENT DEL SISTEMA
## %% ============================================================
## %% Quan es registra un User:
## %%    → Es fa un INSERT a la base de dades.
## %%
## %% Quan un User fa login:
%%    → Es fa un SELECT a la base de dades.
%%    → Es crea un objecte User a memòria.
%%    → S’afegeix a UsersList (llista d’usuaris loguejats).
%%
%% Quan un User compra items a la tenda:
%%    → Els nous items s’assignen al seu objecte User.
%%    → També es fa UPDATE a la base de dades.
%%
%% Quan l’usuari crea/entra en una partida:
%%    → Es crea un objecte Game.
%%    → Es crea un objecte Player basat en el User.
%%    → Els items del Player = els items del User.
%%
%% Quan acaba una partida:
%%    → Els punts de Player s’afegeixen al score del User.
%%    → UPDATE a la base de dades.
%% ============================================================



%% ========================
%%       ENTITY
%% ========================
class Entity{
    <<abstract>>
    - X : float
    - Y : float
    - hp : int
}


%% ========================
%%         PLAYER
%% ========================
class Player{
    + id : int
    - speed : double
    - items : Item[]   % Els items provenen del User
    - score : int      % Score temporal de la partida
}

Player ..|> Entity : 0..* 



%% ========================
%%         ENEMY
%% ========================
class Enemy{
    <<abstract>>
    %% Enemics dins la partida
}

Enemy ..|> Entity



%% ========================
%%          GAME
%% ========================
class Game{
    - id : String
    - settings : Settings
    - enemies : Enemy[]
    - player : Player
}

%% Un Game sempre té un Player
Game --> Player : 1..1 Crea 1 Player quan comença partida

%% Els enemics pertanyen al Game
Game --* Enemy : 0..*



%% ========================
%%          ITEM
%% ========================
class Item{
    <<abstract>>
    - id : int
    - durability : int
}

%% Els items del Player venen del User
Player --* Item : 0..*



%% ========================
%%           USER
%% ========================
class User{
    - nom : string
    - username : string
    - id : int
    - password : string
    - email : string
    - items : Item[]   % Items del User guardats a la BD
    - score : int      % Score acumulat de totes les partides
}

%% Un User genera un Player quan comença una partida
User --> Player : 1..1   Crea 1 Player quan comença partida

%% ============================================================
%%                    USERSLIST
%% ============================================================
class UsersList{
    -List<User> userslist : User[]
    + User getUserByUsername(String username)
    + User getUserById(int userId)
    + List<User> getUserslist()
    + size()
}

%% UsersList gestiona els usuaris actius
UsersList o-- User : conté 0..*

%% ============================================================
%%                    GAMELIST
%% ============================================================
class GameList{
    -List<User> userslist : User[]
    + create(int playerId)
    + Game find(int playerId)
    + remove(int playerId)
    + size()
}

%% UsersList gestiona els usuaris actius
GameList o-- Game : conté 0..* 

```
