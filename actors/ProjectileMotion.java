package actors;

import common.*;

/**
 * Simply moves the Actor in a straight line until the Actor dies
 * @author dvanhumb
 */
public class ProjectileMotion extends AbstractMotion
{
	private static ProjectileMotion singleton = null;
	
	public static ProjectileMotion getInstance()
	{
		if (singleton == null)
			singleton = new ProjectileMotion();
		return singleton;
	}
	
	public void moveActor(Actor actor, float dTime)
	{
		Position next = actor.getPosition().move(actor.getVelocity(), dTime);
		actor.setPosition(next);
	}
	
	public void accelerateActor(Actor actor, Position newForce, float dTime)
	{
		// Do nothing because we just fly in one direction
	}
}
