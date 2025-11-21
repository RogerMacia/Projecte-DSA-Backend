package edu.upc.backend.classes;
import edu.upc.backend.services.EETACBROSMannagerSystemService;
import org.apache.log4j.Logger;

import java.util.*;

// Llista de partides, un per usuari.
public class GameList {
    List<Game> _games;
    int nextID;
    //private static final Logger logger = Logger.getLogger(GameList.class);

    public GameList()
    {
        _games = new LinkedList<>();
        nextID = 0;
    }

    public void create(int playerId)
    {
        if(find(playerId) != null)
        {
            //logger.warn(String.format("Player with id %d has already a game.",playerId));
            return;
        }

        Game game = new Game(nextID,playerId,0);
        _games.add(game);
        nextID++;
    }

    public Game find(int playerId)
    {

        for(int i = 0; i < _games.size(); i++) if(_games.get(0).getPlayerId() == playerId) return _games.get(0);
        return null;
    }

    public void remove(int playerId)
    {
        int i = 0;
        while(_games.get(i).getPlayerId() != playerId) i++;

        _games.remove(i);
    }

    public Game[] toArray()
    {
        return _games.toArray(new Game[_games.size()]);
    }



}
