package timers;

/**
 * A timer that counts down from a particular value whenever it has time left on it
 * @author dvanhumb
 */
public class CountdownTimer extends GameTimer
{
	/**
	 * Create a new count-down timer that has not been started yet
	 */
	public CountdownTimer()
	{
		super();
	}
	
	/**
	 * Create a new count-down timer that has time on it and is started automatically
	 * @param time  The initial amount of time left
	 */
	public CountdownTimer(float time)
	{
		super(time);
	}
	
	/**
	 * Set the amount of time left on this timer and start it if it's greater than zero.
	 * @param time
	 */
	public void setTime(float time)
	{
		timerValue = time;
	}
	
	/**
	 * Returns true if there's still time left on this timer.
	 */
	public boolean isRunning()
	{
		return (timerValue > 0);
	}

	public void updateTimer(float dTime)
	{
		timerValue -= dTime;
	}
}
