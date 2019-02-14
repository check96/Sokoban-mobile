package sokoban;

import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;

import model.FinalBox;
import model.Map;
import model.Move;
import model.Player;
import model.Stack;

public class GameConfig {
	
	public static int MOVE = 0;
	
	public static int LEVEL = 1;
	public static HashMap<Integer, Map> LEVELS = new HashMap<Integer,Map>();
	public static Stack<Move> MOVES = new Stack<Move>();
	public static HashMap<Integer, List<FinalBox>> FINAL_POSITIONS = new HashMap<Integer, List<FinalBox>>();
	
	public static boolean LEFT = false;
	public static boolean RIGHT = false;
	public static boolean UP = false;
	public static boolean DOWN = false;
	
	public static int CELL = 128;
	public static World WORLD = null;
	public static HashMap<Integer,Player> PLAYERS = new HashMap<Integer,Player>();

	public static Texture playerTexture;
	public static Texture box;
	public static Texture finalBox;
	
	public static Map getMap(){
		return LEVELS.get(LEVEL);
	}

	public static Player getPlayer(){
		return PLAYERS.get(LEVEL);
	}

	public static void undo(){

		if(MOVES.size() <= 1)
			return;
		
		MOVES.pop();
		
		Move move = MOVES.get();
		LEVELS.put(LEVEL,new Map(move.getMap()));
		PLAYERS.put(LEVEL,new Player(move.getPlayer()));
	}

	public static void restart(){

		if(MOVES.size() < 1)
			return;

		while(MOVES.size() > 1)
			MOVES.pop();

		Move move = MOVES.get();
		LEVELS.put(LEVEL,new Map(move.getMap()));
		PLAYERS.put(LEVEL,new Player(move.getPlayer()));
	}
}
