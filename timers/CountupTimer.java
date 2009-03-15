package timers;

public class CountupTimer extends GameTimer
{
	/**
	 * Whether this timer is currently running
	 */
	private boolean running;
	
	/**
	 * Create a new count-up timer that has not yet started and has no time on it
	 */
	public CountupTimer()
	{
		super();
		running = false;
	}
	
	/**
	 * Create a new count-up timer that might be initially started or not. 
	 * @param run  True if this timer should be initially started
	 */
	public CountupTimer(boolean run)
	{
		super();
		running = run;
	}
	
	/**
	 * Create a new count-up timer that might be initially started, with the specified amount of starting time
	 * @param time  The initial amount of time left on this timer
	 * @param run  Whether this timer should be initially started or not
	 */
	public CountupTimer(float time, boolean run)
	{
		super(time);
		running = run;
	}
	
	/**
	 * Starts this timer running without changing the amount of time left on it.
	 */
	public void startTimer()
	{
		running = true;
	}
	
	/**
	 * Stops this timer without changing the amount of time left on it.
	 */
	public void stopTimer()
	{
		running = false;
	}
	
	/**
	 * Choose whether to start or stop this timer or not
	 * @param run
	 */
	public void setRunning(boolean run)
	{
		running = run;
	}
	
	public boolean isRunning()
	{
		return running;
	}

	public void updateTimer(float dTime)
	{
		timerValue += dTime;
	}
}
