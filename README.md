# Backend per el projecte de DSA

## To-Do

[x] Web Registre. (2,5 hores per Wenjie)

[] Web Login.

[] Android App Registre.

[] Android App Login.

## Esquema

```mermaid
classDiagram


class Entity{
<<interface>>
}

class Player{
+ userName : string
+ hp : int
- speed : double
- position : Vector2
}

class Enemy{
+ hp : int
- position : Vector2
}

Player ..|> Entity
Enemy ..|> Entity


class Game{
- player : Player
- id : int
- score : int
- settings : Settings
- items : Item[]
- level : Level
}

class Level{
- enemies : Enemy
- _tileMap : int[][]
- timer : Timer

}

class Item{
<<abstract>>
- id : int
- durability : int
}

Game --> Player : 1..n
Level --* Enemy
Game --* Item : 1..n
Game --> Level
```