classDiagram

%% ============================
%% NOTA GLOBAL DEL SISTEMA
%% ============================
note top of Entity
FUNCIONAMENT DEL SISTEMA:
1. Registre → INSERT BD
2. Login → SELECT BD + User object + add a UsersList
3. Comprar items → assigna items al User + UPDATE BD
4. Iniciar partida → crea Game i Player (items del User)
5. Acabar partida → suma score Player al User + UPDATE BD
end note


%% ============================
%% ENTITY
%% ============================
class Entity{
    <<abstract>>
    - X : float
    - Y : float
    - hp : int
}


%% ============================
%% PLAYER
%% ============================
class Player{
    + id : int
    - speed : double
    - items : Item[]
    - score : int
}

Player ..|> Entity

note right of Player
Els items provenen del User.
El score és temporal de la partida.
end note


%% ============================
%% ENEMY
%% ============================
class Enemy{
    <<abstract>>
}

Enemy ..|> Entity

note right of Enemy
Classe base per tots els enemics.
end note


%% ============================
%% GAME
%% ============================
class Game{
    - id : String
    - settings : Settings
    - enemies : Enemy[]
    - player : Player
}

Game --> Player : "1 Player"
Game --* Enemy : "0..*"

note right of Game
Quan l'usuari inicia una partida:
- Es crea un Game
- Es crea un Player basat en el User
end note


%% ============================
%% ITEM
%% ============================
class Item{
    <<abstract>>
    - id : int
    - durability : int
}

Player --* Item : "0..*"

note right of Item
Items guardats al User,
i transferits al Player.
end note


%% ============================
%% USER
%% ============================
class User{
    - nom : string
    - username : string
    - id : int
    - password : string
    - email : string
    - items : Item[]
    - score : int
}

User --> Player : "crea Player al iniciar partida"

note right of User
LOGIN:
- SELECT BD
- Crear objecte User
- Afegir a UsersList

Després partida:
- Suma score Player al User
end note


%% ============================
%% USERSLIST
%% ============================
class UsersList{
    - userslist : List<User>
    + getUserByUsername(username : String) User
    + getUserById(userId : int) User
    + getUserslist() List<User>
    + size() int
}

UsersList o-- User : "0..*"

note right of UsersList
Conté tots els usuaris actius
(loguejats a memòria).
end note


%% ============================
%% GAMELIST
%% ============================
class GameList{
    - gamelist : List<Game>
    + create(playerId : int) Game
    + find(playerId : int) Game
    + remove(playerId : int) void
    + size() int
}

GameList o-- Game : "0..*"

note right of GameList
Gestiona totes les partides actives.
end note
