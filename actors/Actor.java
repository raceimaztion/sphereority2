package actors;

import common.*;

/**
 * Represents any object that can be displayed on the screen.
 * This includes players, projectiles, etc.
 * @author dvanhumb
 */
public abstract class Actor
{
	/**
	 * The current position of this Actor
	 */
	private Position position;
	/**
	 * The current velocity of this Actor
	 */
	private Position velocity;
	/**
	 * This Actor's size
	 */
	private Position size;
	/**
	 * Indicates whether this actor is done and needs to be removed from the system
	 */
	private boolean needsRemoving;
	
	public Actor()
	{
		position = new Position();
		velocity = new Position();
		size = new Position(1, 1);
		needsRemoving = false;
	}
	
	/**
	 * @return The current position of this Actor
	 */
	public Position getPosition()
	{
		return position;
	}
	
	/**
	 * @return The current speed of this Actor
	 */
	public Position getVelocity()
	{
		return velocity;
	}
	
	/**
	 * @return The size of this Actor
	 */
	public Position getSize()
	{
		return size;
	}
	
	/**
	 * Returns true if we're finished with this Actor and it needs to
	 * be removed from the system.
	 * @return Returns true if we need to remove this Actor from the system.
	 */
	public boolean getNeedsRemoving()
	{
		return needsRemoving;
	}
}
