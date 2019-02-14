package model;

public class Map {
	private Objects[][] map;

	public Map(Objects[][] map) {
		this.map = new Objects[map.length][map[0].length];
    	for (int i = 0; i < map.length; i++) 
    		for (int j = 0; j < map.length; j++)
    			this.map[i][j] = map[i][j];
	}
	
	public Map(Map map) {
		this(map.map);
	}

	public Objects[][] getMap() {
		return map;
	}

	@Override
	public String toString() {
		String map = "";

		int x = this.map.length-1;
		for (int i = 0; i < this.map.length; i++) {
			for (int j = 0; j < this.map[i].length; j++) {
				if(this.map[j][x-i] == null)
					map += "-";
				else
					map += this.map[j][x-i];
			}
			map += "\n";
		}
		
		return map;
	}
}
