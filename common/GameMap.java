package common;

import java.io.*;
import java.util.Random;
import java.util.Vector;

/**
 * A game map.
 * Map format is as follows:
 *   '+'		Walls
 *   ' ', '.'	Empty spaces
 *   'f', 'F'	A flag
 *   's', 'S'	Spawn points. If only one "casedness" is used, any player can spawn at any point, else team 1 can spawn on 's' and vice versa
 * @author dvanhumb
 */
public class GameMap
{
	private static final Random RANDOM = new Random();
	
	private Vector<SpawnPoint> spawnPoints;
	
	/**
	 * Create an instance of a map from a string that somebody passed to us 
	 * @param mapName The name of this map
	 * @param map  The contents of this map (i.e., the map layout)
	 */
	public GameMap(String mapName, String map)
	{
		
	}
	
	/**
	 * Load an instance of a map from a file.
	 * @param mapName  The map's name.
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public GameMap(String mapName) throws IOException, FileNotFoundException
	{
		
	}
	
	/**
	 * Ask for a random spawn point
	 * @return A random spawn point
	 */
	public SpawnPoint getRandomSpawnPoint()
	{
		return spawnPoints.get(RANDOM.nextInt(spawnPoints.size()));
	}
}
