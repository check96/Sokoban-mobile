package model;

public class Move {
    private Map map;
    private Player player;

    public Move(Map map, Player player){

    	this.map = new Map(map);
    	this.player = new Player(player);
    }

	public Player getPlayer() {
        return player;
    }

    public Map getMap() {
        return map;
    }

    @Override
    public String toString() {
        return ""+map+"\n";
    }
}
