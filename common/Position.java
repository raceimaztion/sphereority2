package common;

/**
 * An immutable instance of a 2D position
 * @author dvanhumb
 */
public class Position
{
	private float x, y;
	
	/**
	 * Create a new Position centered at (0, 0)
	 */
	public Position()
	{
		x = y = 0;
	}
	
	/**
	 * Create a new Position with the specified x and y coordinates
	 * @param x  The x-coordinate to use
	 * @param y  The y-coordinate to use
	 */
	public Position(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Copy a Position
	 * @param p  The Position to copy
	 */
	public Position(Position p)
	{
		x = p.x;
		y = p.y;
	}
	
	public float getX()
	{
		return x;
	}
	
	public float getY()
	{
		return y;
	}
	
	public Position scale(float c)
	{
		return new Position(x*c, y*c);
	}
}
