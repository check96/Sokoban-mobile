package model;

public enum Objects {

	WALL("W"), PLAYER("P"), BOX("B");

	
	public String tag;
	
	private Objects(String tag) {
		this.tag = tag;
	}
	
	public static Objects getObject(String tag)
	{
			if(tag.equals("B"))
				return BOX;
			else if(tag.equals("P"))
				return PLAYER;
			else if(tag.equals("W"))
				return WALL;

			return null;
	}
	
	@Override
	public String toString() {
		return tag;
	}
}
