package model;

public class Player {

	private int x;
	private int y;
	private Direction direction;
	
	public Player(int x, int y) {
		this.x = x;
		this.y = y;
		this.direction = Direction.DOWN;
	}

	public Player(Player player) {
		this.x = player.x;
		this.y = player.y;
		this.direction = player.direction;
	}

	public String toString() {
		return "P";
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void move(Direction dir) {
		switch (dir) {
			case RIGHT:
				x++;
				break;
			case LEFT:
				x--;
				break;
			case UP:
				y++;
				break;
			case DOWN:
				y--;
				break;
			default:
				break;
		}
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}; 
}
