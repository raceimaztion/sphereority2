package common;

/**
 * Contains a list of constants useful for actors, players, projectiles, etc.
 * @author dvanhumb
 */
public interface ActorConstants
{
    /**
     * The code for the first team
     */
    public static final byte TEAM_A_CODE = 0x11;
    
    /**
     * The code for the second team
     */
    public static final byte TEAM_B_CODE = 0x12;
    
    /**
     * A useful default for teamless actors
     * Do NOT use this to tell if an actor is teamless!
     * Instead, check it against the codes for teams A & B
     */
    public static final byte TEAMLESS_CODE = 0x00;
    
    /**
     * The default size for Actors, in world units
     */
    public static final Position DEFAULT_ACTOR_SIZE = new Position(1, 1);
}
