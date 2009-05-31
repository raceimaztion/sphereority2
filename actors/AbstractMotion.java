package actors;

import common.Position;

public abstract class AbstractMotion
{
	public abstract void moveActor(Actor actor, float dTime);
	public abstract void accelerateActor(Actor actor, Position newForce, float dTime);
}
