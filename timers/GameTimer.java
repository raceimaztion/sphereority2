package timers;

/**
 * Framework class for timers used in game.
 * @author dvanhumb
 */
public abstract class GameTimer
{
	/**
	 * The current value of this timer
	 */
	protected float timerValue;
	
	/**
	 * Create a game timer at time 0
	 */
	public GameTimer()
	{
		timerValue = 0.0f;
		TimerSystem.getTimerSystem().addTimer(this);
	}
	
	/**
	 * Create a game timer with the specified time value
	 * @param time
	 */
	public GameTimer(float time)
	{
		timerValue = time;
		TimerSystem.getTimerSystem().addTimer(this);
	}
	
	/**
	 * @return  The current time on this timer
	 */
	public float getTime()
	{
		return timerValue;
	}
	
	/**
	 * @return  True if this timer is currently running
	 */
	public abstract boolean isRunning();
	
	/**
	 * Called by the timer system to keep this timer updated.
	 * Only called when this timer is actually running
	 * @param dTime
	 */
	public abstract void updateTimer(float dTime);
}
