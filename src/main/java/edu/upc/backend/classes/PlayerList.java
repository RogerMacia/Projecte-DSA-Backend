package edu.upc.backend.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlayerList {
    private List<Player> playerList;

    // 🔧 Constructor
    public PlayerList() {
        this.playerList = new ArrayList<>();
    }

    // ➕ Afegir un nou client
    public void addPlayer(Player player) {
        this.playerList.add(player);
    }

    // 🔍 Buscar un client per ID
    public Player getPlayerByPlayerId(int playerId) {
        for (Player p : playerList) {
            if (p.getId() == playerId) {
                return p;
            }
        }
        return null; // si no el troba
    }

    public List<Player> getPlayerList() {
        return this.playerList;
    }

    public int size() {
        return this.playerList.size();
    }

    public void add(Player player) {
        this.playerList.add(player);
    }
}

