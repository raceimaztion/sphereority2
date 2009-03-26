package actors;

import common.*;

public abstract class WeightedActor extends Actor implements Animatible
{
    /**
     * The weight of this Actor. The heavier it is, the slower it is to react to motion
     */
    private float weight;
    
    public WeightedActor()
    {
        weight = DEFAULT_WEIGHT;
    }
    
    public WeightedActor(float weight)
    {
        this.weight = weight;
    }
    
    public float getWeight()
    {
        return weight;
    }
    
    /**
     * Accelerate by a given amount, taking into consideration this Actor's weight.
     */
    public void accelerate(Position direction, float dTime)
    {
    	// The heavier we are, the slower we move
    	super.accelerate(direction, dTime/weight);
    }
}
