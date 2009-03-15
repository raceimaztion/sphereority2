package actors;

/**
 * This interface indicates an Actor that can move
 * @author dvanhumb
 */
public interface Animatible
{
	/**
	 * Asks an Actor to perform any logic it needs for motion 
	 * @param curTime  The amount of time since the game started, in seconds
	 * @param dTime  The amount of time that has passed since the last timer tick, in seconds
	 * @return  Return true if it has moved and needs to be repainted, false otherwise
	 */
	public void animate(float curTime, float dTime);
	
	/**
	 * Returns true if this Actor has moved or otherwise needs the screen to repaint
	 */
	public boolean needsUpdate();
}
