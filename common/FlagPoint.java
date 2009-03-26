package common;

public class FlagPoint implements ActorConstants
{
	private int x,y;
	private byte teamCode;
	
	/**
	 * Create a new flag point
	 * @param x The x-coordinate of this flag point
	 * @param y The y-coordinate of this flag point
	 * @param teamCode The team associated with this flag
	 */
	public FlagPoint(int x, int y, byte teamCode)
	{
		this.x = x;
		this.y = y;
		this.teamCode = teamCode;
	}
	
	/**
	 * @return The team's code associated with this team
	 */
	public byte getTeamCode()
	{
		return teamCode;
	}
	
	/**
	 * @return The x-coordinate of this flag point
	 */
	public int getX()
	{
		return x;
	}
	
	/**
	 * @return The y-coordinate of this flag point
	 */
	public int getY()
	{
		return y;
	}
}
