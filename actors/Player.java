package actors;

import common.*;

public abstract class Player extends Actor
{
	/**
	 * The name of this Player
	 */
	private String name;
	
	public Player(AbstractGameEngine gameEngine, String name)
	{
		super(gameEngine);
		
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}
}
