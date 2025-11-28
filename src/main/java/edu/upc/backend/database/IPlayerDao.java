package edu.upc.backend.database;

import edu.upc.backend.classes.Player;

import java.util.List;

public interface IPlayerDAO
{
    public int addPlayer(Player player) throws Exception;
    public Player getPlayer(int userID) throws Exception;
    public void updatePlayer(Player player) throws Exception;
    public void deletePlayer(int userID) throws Exception;
    public List<Player> getPlayers() throws Exception;

}
