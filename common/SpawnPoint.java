package common;

/**
 * Indicates where a player can spawn from
 * @author dvanhumb
 */
public class SpawnPoint
{
	private int x, y;
	
	public SpawnPoint(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
}
