classDiagram

%% ============================================================
%%                  NOTA GENERAL DEL SISTEMA
%% ============================================================
note "FUNCIONAMENT DEL SISTEMA:
1. Quan es registra un User → INSERT a la BD.
2. Quan fa login → SELECT, es crea un objecte User i s’afegeix a UsersList.
3. Quan compra items → s’afegeixen al seu User + UPDATE BD.
4. Quan entra a una partida → es crea Game i Player, els items del Player = User.
5. Quan acaba la partida → score del Player s’acumula al User + UPDATE BD."
as SystemNote
SystemNote .. Entity


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
    - items : Item[]
    - score : int
}

note right of Player
Els items del Player venen del User.
El score només és temporal (partida).
end note

Player ..|> Entity



%% ========================
%%         ENEMY
%% ========================
class Enemy{
    <<abstract>>
}

note right of Enemy
Classe base per tots els enemics del joc.
end note

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

note right of Game
Quan l’usuari crea o entra a una partida:
→ Es crea un objecte Game.
→ Es crea un Player basat en el User.
end note

Game --> Player : "1 Player"
Game --* Enemy : "0..*"


%% ========================
%%          ITEM
%% ========================
class Item{
    <<abstract>>
    - id : int
    - durability : int
}

note right of Item
Els items s’assignen al User
i després passen al Player.
end note

Player --* Item : "0..*"



%% ========================
%%           USER
%% ========================
class User{
    - nom : string
    - username : string
    - id : int
    - password : string
    - email : string
    - items : Item[]
    - score : int
}

note right of User
Quan fa login:
→ SELECT a la BD
→ Es crea User a memòria
→ S’afegeix a UsersList

Quan acaba partida:
→ Suma score del Player al User.
end note

User --> Player : "Crea 1 Player en iniciar partida"



%% ============================================================
%%                    USERSLIST
%% ============================================================
class UsersList{
    - userslist : List<User>
    + getUserByUsername(username : String) User
    + getUserById(userId : int) User
    + getUserslist() List<User>
    + size() int
}

note right of UsersList
Llista d'usuaris actius a memòria,
és a dir, loguejats actualment.
end note

UsersList o-- User : "0..*"



%% ============================================================
%%                    GAMELIST
%% ============================================================
class GameList{
    - gamelist : List<Game>
    + create(playerId : int) Game
    + find(playerId : int) Game
    + remove(playerId : int) void
    + size() int
}

note right of GameList
Gestiona totes les partides actives.
end note

GameList o-- Game : "0..*"
