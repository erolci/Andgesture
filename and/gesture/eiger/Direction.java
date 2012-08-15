package and.gesture.eiger;

public enum Direction 
{
	N,S,E,W,NE,SE,NW,SW,NO_DIRECTION;
	
	public static final Direction parse(String dir)
	{
		
		for(final Direction d:Direction.values())
		{
			if(d.name().equals(dir.trim()))return d;
		}
		return null;
		
		
		
	}
	
}
