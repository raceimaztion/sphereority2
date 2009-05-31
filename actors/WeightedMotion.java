package actors;

import common.Position;

/**
 * Apply a weight to the acceleration
 * @author dvanhumb
 */
public class WeightedMotion extends AbstractMotion
{
	private static WeightedMotion singleton = null;
	
	public static WeightedMotion getInstance()
	{
		if (singleton == null)
			singleton = new WeightedMotion();
		return singleton;
	}
	
	public void accelerateActor(Actor actor, Position newForce, float dTime)
	{
		Position nextVelocity = actor.getVelocity().move(newForce, dTime/actor.getWeight());
		actor.setVelocity(nextVelocity);
	}
	
	public void moveActor(Actor actor, float dTime)
	{
		Position next = actor.getPosition().move(actor.getVelocity(), dTime);
		actor.setPosition(next);
	}
}
