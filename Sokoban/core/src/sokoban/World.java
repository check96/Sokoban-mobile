package sokoban;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import model.Direction;
import model.FinalBox;
import model.Map;
import model.Move;
import model.Objects;
import model.Player;

public class World {

	public World() {
		
		for (int level = 1; level < 10; level++) {
			
			FileHandle levelMap = Gdx.files.internal("levels/level"+level+".txt");
			String[] lines = levelMap.readString().split("\n");
			
			int dimX = lines[1].length();
			int dimY = lines.length;
			
			GameConfig.FINAL_POSITIONS.put(level, new ArrayList<FinalBox>());
			
			Objects[][] map = new Objects[dimX][dimY];
			for (int i = 0; i < lines.length; i++)
				for (int j = 0; j < lines[i].length(); j++) {
					
					if (lines[i].charAt(j) == 'F')
						GameConfig.FINAL_POSITIONS.get(level).add(new FinalBox(j,i));
					else {
						map[j][i] = Objects.getObject("" + lines[i].charAt(j));
						if (map[j][i] == Objects.PLAYER)
							GameConfig.PLAYERS.put(level, new Player(j,i));
					}
				}

			GameConfig.LEVELS.put(level, new Map(map));
		}
	}

	public void update(Direction dir) {

		Objects[][] map = GameConfig.LEVELS.get(GameConfig.LEVEL).getMap();
		Player player = GameConfig.getPlayer();
		
		int x = player.getX();
		int y = player.getY();
		boolean moved = false;
		
		switch (dir) {
			case LEFT:
				if (map[x - 1][y] == null) {
					
					player.move(dir);
					map[x - 1][y] = Objects.PLAYER;

					moved = true;
				} else if (map[x - 1][y] == Objects.BOX) {
					if (map[x - 2][y] == null) {

						map[x - 2][y] = Objects.BOX;
						map[x - 1][y] = Objects.PLAYER;
						player.move(dir);

						moved = true;
					}
				}
				break;
			case RIGHT:
				if (map[x + 1][y] == null) {
					
					player.move(dir);
					map[x + 1][y] = Objects.PLAYER;

					moved = true;
				} else if (map[x + 1][y] == Objects.BOX) {
					if (map[x + 2][y] == null) {
						
						player.move(dir);
						map[x + 2][y] = Objects.BOX;
						map[x + 1][y] = Objects.PLAYER;

						moved = true;
					}
				}
				break;
			case UP:
				if (map[x][y + 1] == null) {
					
					player.move(dir);
					map[x][y + 1] = Objects.PLAYER;

					moved = true;
				} else if (map[x][y + 1] == Objects.BOX) {
					if (map[x][y + 2] == null) {
						
						map[x][y + 2] = Objects.BOX;
						map[x][y + 1] = Objects.PLAYER;
						player.move(dir);

						moved = true;
					}
				}
				break;
			case DOWN:
				if (map[x][y - 1] == null) {

					player.move(dir);
					map[x][y - 1] = Objects.PLAYER;

					moved = true;
				} else if (map[x][y - 1] == Objects.BOX) {
					if (map[x][y - 2] == null) {
						
						map[x][y - 2] = Objects.BOX;
						map[x][y - 1] = Objects.PLAYER;
						player.move(dir);
						
						moved = true;
					}
				}
				break;
			default:
				break;
		}
		
		if(moved) {
			map[x][y] = null;
			GameConfig.MOVES.push(new Move(GameConfig.getMap(),GameConfig.getPlayer()));
			GameConfig.MOVE++;
		}
	}

	public boolean gameOver() {
		
		Objects[][] map = GameConfig.LEVELS.get(GameConfig.LEVEL).getMap();
		
		for (FinalBox box : GameConfig.FINAL_POSITIONS.get(GameConfig.LEVEL))
			if(map[box.getX()][box.getY()] != Objects.BOX)
				return false;
		
		return true;
	}
}
