package common;

import java.io.*;
import java.util.Scanner;
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
	
	private Vector<SpawnPoint> spawnPointsA, spawnPointsB;
	private Vector<FlagPoint> flagPointsA, flagPointsB;
	private int width, height;
	private boolean[][] walls;
	private String mapName;
	
	/**
	 * Create an instance of a map from a string that somebody passed to us 
	 * @param mapName The name of this map
	 * @param map  The contents of this map (i.e., the map layout)
	 */
	public GameMap(String mapName, String map)
	{
		this.mapName = mapName;
		parseData(new Scanner(map));
	}
	
	/**
	 * Load an instance of a map from a file.
	 * @param mapName  The map's name.
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public GameMap(String mapName) throws IOException, FileNotFoundException
	{
		this.mapName = mapName;
	}
	
	private void parseData(Scanner mapData)
	{
		width = mapData.nextInt();
		height = mapData.nextInt();
		spawnPointsA = new Vector<SpawnPoint>();
		spawnPointsB = new Vector<SpawnPoint>();
		flagPointsA = new Vector<FlagPoint>();
		flagPointsB = new Vector<FlagPoint>();
		
		mapData.nextLine();
		
		String line;
		for (int y=0; y < height; y++)
		{
			line = mapData.nextLine();
			for (int x=0; x < width; x++)
			{
				if (line.charAt(x) == '+')
					walls[x][y] = true;
				else if (line.charAt(x) == 'f')
					flagPointsA.add(new FlagPoint(x, y, ActorConstants.TEAM_A_CODE));
				else if (line.charAt(x) == 'F')
					flagPointsB.add(new FlagPoint(x, y, ActorConstants.TEAM_B_CODE));
				else if (line.charAt(x) == 's')
					spawnPointsA.add(new SpawnPoint(x, y));
				else if (line.charAt(x) == 'S')
					spawnPointsB.add(new SpawnPoint(x, y));
				// Anything else we ignore
			}
		}
		
		// If we have no spawn points, add some in
		if ((spawnPointsA.size() == 0) && (spawnPointsB.size() == 0))
		{
			spawnPointsA = spawnPointsB;
			for (int y=0; y < height; y++)
				for (int x=0; x < width; x++)
					if (!walls[x][y])
						spawnPointsA.add(new SpawnPoint(x, y));
		}
		else if (spawnPointsA.size() == 0)
			spawnPointsA = spawnPointsB;
		else if (spawnPointsB.size() == 0)
			spawnPointsB = spawnPointsA;
	}
	
	/**
	 * Ask for a random spawn point
	 * @return A random spawn point
	 */
	public SpawnPoint getRandomSpawnPoint(byte teamCode)
	{
		if (teamCode != ActorConstants.TEAM_A_CODE && teamCode != ActorConstants.TEAM_B_CODE)
		{
			if (RANDOM.nextBoolean())
				teamCode = ActorConstants.TEAM_A_CODE;
			else
				teamCode = ActorConstants.TEAM_B_CODE;
		}
		if (teamCode == ActorConstants.TEAM_A_CODE)
			return spawnPointsA.get(RANDOM.nextInt(spawnPointsA.size()));
		else
			return spawnPointsB.get(RANDOM.nextInt(spawnPointsB.size()));
	}
	
	/**
	 * @return The name of this map
	 */
	public String getName()
	{
		return mapName;
	}
	
	/**
	 * Find out if a particular location is a wall
	 * @param x The x-coordinate we're interested in
	 * @param y The y-coordinate we're interested in
	 * @return Whether there's a wall there
	 */
	public boolean isWall(int x, int y)
	{
		return walls[x][y];
	}
	
	/**
	 * @return The width of this map
	 */
	public int getWidth()
	{
		return width;
	}
	
	/**
	 * @return The height of this map
	 */
	public int getHeight()
	{
		return height;
	}
}
