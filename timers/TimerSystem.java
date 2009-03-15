package timers;

import actors.Animatible;

import java.util.Vector;

/**
 * This class manages timers and calling all objects that need to be updated every timer tick or are timers of their own
 * @author dvanhumb
 */
public class TimerSystem
{
	private static TimerSystem singleton = null;
	
	/**
	 * A list of all the animatible Actors
	 */
	private Vector<Animatible> animatibles;
	
	/**
	 * A list of all game timers used by Actors, etc.
	 */
	private Vector<GameTimer> gameTimers;
	
	/**
	 * The last timer tick
	 */
	private long lastTime;
	
	/**
	 * The current game time, in seconds
	 */
	private float curTime;
	
	private TimerSystem()
	{
		animatibles = new Vector<Animatible>();
		gameTimers = new Vector<GameTimer>();
		
		lastTime = System.currentTimeMillis();
		curTime = 0.0f;
	}
	
	public static TimerSystem getTimerSystem()
	{
		if (singleton == null)
			singleton = new TimerSystem();
		return singleton;
	}
	
	/**
	 * Sets the current game time, in seconds
	 * @param time
	 */
	public void setCurrentTime(float time)
	{
		curTime = time;
	}
	
	/**
	 * @return  The curent game time, in seconds
	 */
	public float getCurrentTime()
	{
		return curTime;
	}
	
	/**
	 * Updates all clocks and timers. Solely used in the game loop to update all objects that use the clock.
	 */
	public void triggerTimers()
	{
		long curTime = System.currentTimeMillis();
		float dTime = (curTime - lastTime)*0.001f;
		this.curTime += dTime;
		
		for (GameTimer t : gameTimers)
			if (t.isRunning())
				t.updateTimer(dTime);
		for (Animatible a : animatibles)
			a.animate(this.curTime, dTime);
		
		lastTime = curTime;
	}
	
	/**
	 * Adds an Animatible to the list of timers
	 * @param a
	 */
	public void addTimer(Animatible a)
	{
		animatibles.add(a);
	}
	
	/**
	 * Removes an Animatible from the list of timers
	 * @param a
	 */
	public void removeTimer(Animatible a)
	{
		animatibles.remove(a);
	}
	
	/**
	 * Adds a GameTimer to the list of timers
	 * @param t
	 */
	public void addTimer(GameTimer t)
	{
		gameTimers.add(t);
	}
	
	/**
	 * Rmoves a GameTimer from the list of timers
	 * @param t
	 */
	public void removeTimer(GameTimer t)
	{
		gameTimers.remove(t);
	}
}
