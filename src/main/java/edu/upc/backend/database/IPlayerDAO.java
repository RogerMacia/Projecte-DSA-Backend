package edu.upc.backend.database;

import edu.upc.backend.classes.Player;
import edu.upc.backend.classes.User;

import java.util.List;

public interface IPlayerDAO
{
    public int addPlayer(int userId) throws Exception;
    public Player getPlayer(int playerId) throws Exception;
    public Player getPlayerbyUserId(int userId) throws Exception;
    public void updatePlayer(Player player) throws Exception;
    public void deletePlayer(Player player) throws Exception;
    public List<Player> getPlayers() throws Exception;

}
