package tools.mapeditor;

import common.*;

import java.io.*;
import java.util.Vector;


public class EditableMap extends GameMap
{
	private Vector<MapAlterationListener> listeners;
	
	/**
	 * Load a map to edit from a file
	 * @param mapName  The name of the map to load
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public EditableMap(String mapName) throws FileNotFoundException, IOException
	{
		super(mapName);
		listeners = new Vector<MapAlterationListener>();
	}
	
	/**
	 * Parse a map with given data
	 * @param mapName  The name of the map
	 * @param map  The map data
	 */
	public EditableMap(String mapName, String map)
	{
		super(mapName, map);
		listeners = new Vector<MapAlterationListener>();
	}
	
	/**
	 * Duplicate a pre-existing map
	 * @param map The map to duplicate
	 */
	public EditableMap(GameMap map)
	{
		super(map);
	}
	
	/**
	 * Create a new, empty map
	 * @param mapName  The name of this new map
	 * @param width  The width of this new map
	 * @param height  The height of this new map
	 */
	public EditableMap(String mapName, int width, int height)
	{
		super(mapName, createEmpty(width, height));
	}
	
	private static String createEmpty(int width, int height)
	{
		String map = String.format("%s %s\n", width, height);
		
		String line = "";
		for (int i=0; i < width; i++)
			line += CHAR_WALL;
		line += '\n';
		
		line += CHAR_WALL;
		for (int y=2; y < height; y++)
		{
			for (int i=2; i < width; i++)
				line += ' ';
			line += CHAR_WALL;
			line += '\n';
		}
		
		for (int i=0; i < width; i++)
			line += CHAR_WALL;
		line += '\n';
		
		return map;
	}
	
	/**
	 * Change a wall at a particular location
	 * @param x  The x-coordinate of the wall to change
	 * @param y  The y-coordinate of the wall to change
	 * @param isWall  Whether the location should be a wall or not
	 */
	public void setWall(int x, int y, boolean isWall)
	{
		if (isValidPosition(x, y))
		{
			super.walls[x][y] = isWall;
			
			for (MapAlterationListener mcl : listeners)
				mcl.mapChanged(this, x, y);
		}
	}
	
	/**
	 * Adds a map alteration listener
	 * @param listener  The listener to add
	 */
	public void addMapChangeListener(MapAlterationListener listener)
	{
		listeners.add(listener);
	}
	
	/**
	 * Removes a map alteration listener
	 * @param listener  The listener to remove
	 */
	public void removeMapChangeListener(MapAlterationListener listener)
	{
		listeners.remove(listener);
	}
	
	/**
	 * Set the map's name
	 * @param name
	 */
	public void setName(String name)
	{
		mapName = name;
	}
	
	/**
	 * Save map to our internal list of maps
	 */
	public void save() throws IOException
	{
		FileWriter out = new FileWriter(String.format("maps/%s.map", getName()));
		final int width = getWidth(), height = getHeight();
		
		// Write header
		out.write(String.format("%d %d\n", width, height));
		
		// Write out map contents
		char[][] data = new char[width][height];
		for (int y=0; y < width; y++)
			for (int x=0; x < height; x++)
			{
				if (walls[x][y])
					data[x][y] = '+';
				else
					data[x][y] = '.';
			}
		
		if (spawnPointsA != null)
		{
			for (SpawnPoint p : spawnPointsA)
				data[p.getX()][p.getY()] = 's';
			
			if (spawnPointsB != null && spawnPointsB != spawnPointsA)
			{
				for (SpawnPoint p : spawnPointsB)
					data[p.getX()][p.getY()] = 'S';
			}
		}
		else if (spawnPointsB != null)
		{
			for (SpawnPoint p : spawnPointsB)
				data[p.getX()][p.getY()] = 'S';
		}
		
		if (flagPointsA != null)
		{
			for (FlagPoint p : flagPointsA)
				data[p.getX()][p.getY()] = 'f';
			
			if (flagPointsB != null && flagPointsB != flagPointsA)
			{
				for (FlagPoint p : flagPointsB)
					data[p.getX()][p.getY()] = 'F';
			}
		}
		else if (flagPointsB != null)
		{
			for (FlagPoint p : flagPointsB)
				data[p.getX()][p.getY()] = 'F';
		}
		
		for (int y=0; y < height; y++)
		{
			for (int x=0; x < width; x++)
				out.write(data[x][y]);
			out.write('\n');
		}
		
		out.flush();
		out.close();
	}
}
