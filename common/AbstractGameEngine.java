package common;

import actors.Animatible;
import java.util.Vector;

/**
 * Keeps track of Actors, Timers, etc.
 * @author dvanhumb
 */
public abstract class AbstractGameEngine
{
	/**
	 * The list of Animatibles
	 */
	protected Vector<Animatible> listAnimatibles;
	
	/**
	 * Add an Animatible to the GameEngine
	 * @param a  The Animatible to track
	 */
	public void addAnimatible(Animatible a)
	{
		listAnimatibles.add(a);
	}
	
	/**
	 * Remove an Animatible from the GameEngine
	 * @param a  The Animatible to stop tracking
	 */
	public void removeAnimatible(Animatible a)
	{
		listAnimatibles.remove(a);
	}
}
