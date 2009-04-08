package tools.mapeditor;

import common.*;

import java.io.*;
import java.util.Vector;
import java.util.Scanner;


public class EditableMap implements MapConstants
{
	private Vector<MapAlterationListener> listeners = new Vector<MapAlterationListener>();
	private boolean dirty = false;
	private String mapName;
	private int width, height;
	private char[][] mapData;
	
	/**
	 * Load a map to edit from a file
	 * @param mapName  The name of the map to load
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public EditableMap(String mapName) throws FileNotFoundException, IOException
	{
		this.mapName = mapName;
		
		// Try loading the file itself first, then try asking for the properly-named file
		File f = new File(mapName);
		if (!f.exists())
			f = new File(String.format(LOCAL_MAP_NAMING, mapName));
		
		Scanner in = new Scanner(f);
		width = in.nextInt();
		height = in.nextInt();
		in.nextLine();
		
		mapData = new char[width][height];
		String line;
		for (int y=0; y < height; y++)
		{
			line = in.nextLine();
			for (int x=0; x < width; x++)
				mapData[x][y] = line.charAt(x);
		}
	}
	
	/**
	 * Parse a map with given data
	 * @param mapName  The name of the map
	 * @param map  The map data
	 */
	public EditableMap(String mapName, String map)
	{
		this.mapName = mapName;
		Scanner in = new Scanner(map);
		width = in.nextInt();
		height = in.nextInt();
		in.nextLine();
		
		mapData = new char[width][height];
		String line;
		for (int y=0; y < height; y++)
		{
			line = in.nextLine();
			for (int x=0; x < width; x++)
				mapData[x][y] = line.charAt(x);
		}
	}
	
	/**
	 * Duplicate a pre-existing map
	 * @param map The map to duplicate
	 */
	public EditableMap(GameMap map)
	{
		
	}
	
	/**
	 * Create a new, empty map
	 * @param mapName  The name of this new map
	 * @param width  The width of this new map
	 * @param height  The height of this new map
	 */
	public EditableMap(String mapName, int width, int height)
	{
		this.width = width;
		this.height = height;
		this.mapName = mapName;
		
		for (int i=0; i < width; i++)
		{
			mapData[i][0] = CHAR_WALL;
			mapData[0][height-1] = CHAR_WALL;
		}
		
		for (int y=1; y < height-1; y++)
		{
			mapData[0][y] = CHAR_WALL;
			mapData[width-1][y] = CHAR_WALL;
			
			for (int x=1; x < width-1; x++)
				mapData[x][y] = '.';
		}
	}
	
	public boolean isValidPosition(int x, int y)
	{
		return ((x >= 0) && (x < width)) || ((y >= 0) && (y < height));
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
			if (isWall)
				mapData[x][y] = CHAR_WALL;
			else
				mapData[x][y] = '.';
			
			for (MapAlterationListener mcl : listeners)
				mcl.mapChanged(this, x, y);
			
			dirty = true;
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
		
		dirty = true;
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
		
		// Write map data
		for (int y=0; y < height; y++)
		{
			for (int x=0; x < width; x++)
				out.write(mapData[x][y]);
			out.write('\n');
		}
		
		dirty = false;
		
		out.flush();
		out.close();
	}
	
	public boolean isDirty()
	{
		return dirty;
	}

	public int getHeight()
	{
		return height;
	}

	public String getName()
	{
		return mapName;
	}

	public int getWidth()
	{
		return width;
	}
	
	public boolean isWall(int x, int y)
	{
		if (isValidPosition(x, y))
			return mapData[x][y] == CHAR_WALL;
		else
			return true;
	}
	
	public char getSquareType(int x, int y)
	{
		if (isValidPosition(x, y))
			return mapData[x][y];
		else
			return CHAR_WALL;
	}
}
