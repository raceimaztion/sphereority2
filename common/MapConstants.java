package common;

/**
 * Constants relevant to Maps, including map file naming, minimum sizes, and loading and saving constants
 * @author dvanhumb
 */
public interface MapConstants
{
	/**
	 * The character that represents a wall
	 */
	public static final char CHAR_WALL = '+';
	/**
	 * The character that represents a spawn point for team 1
	 */
	public static final char CHAR_SPAWN_A = 's';
	/**
	 * The character that represents a spawn point for team 2
	 */
	public static final char CHAR_SPAWN_B = 'S';
	/**
	 * The character that represents a flag for team 1
	 */
	public static final char CHAR_FLAG_A = 'f';
	/**
	 * The character that represents a flag for team 2
	 */
	public static final char CHAR_FLAG_B = 'F';
	/**
	 * The smallest width a map can be
	 */
	public static final int MINIMUM_WIDTH = 8;
	/**
	 * The smallest height a map can be
	 */
	public static final int MINIMUM_HEIGHT = 8;
	/**
	 * The widest a map can be
	 */
	public static final int MAXIMUM_WIDTH = 4*1024;
	/**
	 * The tallest a map can be
	 */
	public static final int MAXIMUM_HEIGHT = 4*1024;
	/**
	 * How local maps are named
	 */
	public static final String LOCAL_MAP_NAMING = "maps/%s.map";
}
