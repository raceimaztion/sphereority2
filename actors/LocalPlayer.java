package actors;

import common.*;

/**
 * This class denotes a player controlled by the local client
 * @author dvanhumb
 */
public class LocalPlayer extends Player
{	
	public LocalPlayer(AbstractGameEngine gameEngine, String name)
	{
		super(gameEngine, name);
		setMotionControl(WeightedMotion.getInstance());
	}
}
