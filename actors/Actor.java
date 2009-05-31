package actors;

import common.*;

/**
 * Represents any object that can be displayed on the screen.
 * This includes players, projectiles, etc.
 * @author dvanhumb
 */
public abstract class Actor implements ActorConstants
{
	/**
	 * A link to the containing GameEngine
	 */
	private AbstractGameEngine gameEngine;
	/**
	 * The current position of this Actor
	 */
	private Position position;
	/**
	 * The current velocity of this Actor
	 */
	private Position velocity;
	/**
	 * This Actor's size
	 */
	private Position size;
	/**
	 * Indicates whether this actor is done and needs to be removed from the system
	 */
	private boolean needsRemoving;
    /**
     * The team to which this Actor belongs
     */
    private byte team;
    /**
     * This controls how this Actor moves
     * If this is null, the Actor will not move
     */
    private AbstractMotion motionControl;
    /**
     * How heavy this Actor is
     */
    private float weight;
	
    
    public Actor(AbstractGameEngine engine)
    {
    	this();
    	gameEngine = engine;
    	
    	if (this instanceof Animatible)
    		gameEngine.addAnimatible((Animatible)this);
    }
    
	public Actor()
	{
		position = new Position();
		velocity = new Position();
		size = new Position(1, 1);
        team = TEAMLESS_CODE;
        
        setup();
	}
    
    public Actor(Position position)
    {
        this();
        this.position = position;
        
        setup();
    }
    
    private void setup()
    {
    	needsRemoving = false;
    }
    
    /**
     * Called by the game engine to dispose of this Actor
     */
    public void dispose()
    {
    	// If we're Animatible, remove us from the timer system
    	if ((this instanceof Animatible) && (gameEngine != null))
    		gameEngine.removeAnimatible((Animatible)this);
    		
    	// If we're Intelligent, remove us from the GameEngine's intelligence list
    }
    
    public Actor(Position position, Position size)
    {
        this();
        this.position = position;
        this.size = size;
    }
	
	/**
	 * @return The current position of this Actor
	 */
	public Position getPosition()
	{
		return position;
	}
	
	/**
	 * @return The current speed of this Actor
	 */
	public Position getVelocity()
	{
		return velocity;
	}
	
	/**
	 * @return The size of this Actor
	 */
	public Position getSize()
	{
		return size;
	}
	
	/**
	 * Returns true if we're finished with this Actor and it needs to
	 * be removed from the system.
	 * @return Returns true if we need to remove this Actor from the system.
	 */
	public boolean needsRemoving()
	{
		return needsRemoving;
	}
    
    /**
     * The code for the team this Actor is on
     * @return The raw team code
     */
    public byte getTeam()
    {
        return team;
    }
    
    /**
     * Returns true if this Actor is on team A
     * @return
     */
    public boolean isTeamA()
    {
        return team == TEAM_A_CODE;
    }
    
    /**
     * Returns true if this Actor is on team B
     * @return
     */
    public boolean isTeamB()
    {
        return team == TEAM_B_CODE;
    }
    
    /**
     * Returns true if this Actor has no team
     * @return
     */
    public boolean isTeamless()
    {
        return !isTeamA() && !isTeamB();
    }
    
    /**
     * Move the Actor with the current AbstractMotion
     * @param dTime  The amount of time that elapsed since the last timer tick
     */
    public void move(float dTime)
    {
    	if (motionControl != null)
    		motionControl.moveActor(this, dTime);
    }
    
    /**
     * Accelerate by a particular amount
     * @param p  The direction to accelerate in
     * @param dTime  The amount of the acceleration to apply
     */
    public void accelerate(Position p, float dTime)
    {
    	if (motionControl != null)
    		motionControl.accelerateActor(this, p, dTime);
    }
    
    /**
     * Find the GameEngine containing this Actor
     * @return  The GameEngine this Actor is in
     */
    public AbstractGameEngine getGameEngine()
    {
    	return gameEngine;
    }
    
    /**
     * The object that's currently controlling this Actor's motion
     * @return The AbstractMotion this Actor is using
     */
	public AbstractMotion getMotionControl()
	{
		return motionControl;
	}
	
	/**
	 * @param motionControl  The AbstractMotion this Actor should use
	 */
	public void setMotionControl(AbstractMotion motionControl)
	{
		this.motionControl = motionControl;
	}
	
	/**
	 * @return  This Actor's weight
	 */
	public float getWeight()
	{
		return weight;
	}
	
	/**
	 * @param weight  This Actor's new weight
	 */
	public void setWeight(float weight)
	{
		this.weight = weight;
	}
	
	/**
	 * @param position  The new position this Actor should have
	 */
	protected void setPosition(Position position)
	{
		this.position = position;
	}
	
	/**
	 * @param velocity  The new speed this Actor should have
	 */
	protected void setVelocity(Position velocity)
	{
		this.velocity = velocity;
	}
	
	/**
	 * @param team  The team this Actor should be on
	 */
	public void setTeam(byte team)
	{
		this.team = team;
	}
}
