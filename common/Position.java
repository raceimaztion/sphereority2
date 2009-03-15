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
	
    /**
     * Get the x-coordinate of this Position
     * @return
     */
	public float getX()
	{
		return x;
	}
	
    /**
     * Get the y-coordinate of this Position
     * @return
     */
	public float getY()
	{
		return y;
	}
	
    /**
     * Scale this Position by a specified amount
     * @param s  The scaling factor
     * @return  A scaled copy of this Position
     */
	public Position scale(float s)
	{
		return new Position(x*s, y*s);
	}
    
    /**
     * Move this Position by another Position
     * @param p  The Position to move by
     * @return  A moved copy of this Position
     */
    public Position add(Position p)
    {
        return new Position(x+p.x, y+p.y);
    }
    
    /**
     * Move this Position by a fraction of another Position
     * @param p  The Position to move by
     * @param s  The amount of the Position to move by
     * @return  A moved copy of this Position
     */
    public Position move(Position p, float s)
    {
        return new Position(x + s*p.x, y + s*p.y);
    }
    
    /**
     * @return  The distance from this Position to (0, 0)
     */
    public float getLength()
    {
        return (float)Math.sqrt(x*x + y*y);
    }
    
    /**
     * Points this Position in the direction of another Position
     * @param p  The Position to point at.
     * @return  A copy of this Position moved by the given amount
     */
    public Position pointAt(Position p)
    {
        float scale = getLength()/p.getLength();
        
        return new Position(p.x*scale, p.y*scale);
    }
}
