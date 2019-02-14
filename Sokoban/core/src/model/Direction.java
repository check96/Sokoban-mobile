package model;

public enum Direction {
	DOWN(0), LEFT(1), UP(2), RIGHT(3);
	
	private int tag;
	
	private Direction(int tag) {
		this.tag = tag;
	}
	
	public int getTag() {
		return tag;
	}
}
