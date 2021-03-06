package tools.mapeditor;

import common.*;

import java.io.*;
import java.security.InvalidParameterException;
import java.util.Vector;
import java.util.Scanner;

/**
 * A map that can be edited. A variation (not a sub-class) of common.GameMap that is optimized for editing.
 * An EditableMap can be converted to a GameMap by invoking the .toGameMap() method. 
 * @author dvanhumb
 */
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
		width = map.getWidth();
		height = map.getHeight();
		mapName = map.getName();
		
		String data = map.toString();
		Scanner in = new Scanner(data);
		String line = in.nextLine(); // skip the header
		for (int y=0; y < height; y++)
		{
			line = in.nextLine();
			for (int x=0; x < width; x++)
				mapData[x][y] = line.charAt(x);
		}
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
		mapData = new char[width][height];
		
		// Top and bottom walls
		for (int i=0; i < width; i++)
		{
			mapData[i][0] = CHAR_WALL;
			mapData[i][height-1] = CHAR_WALL;
		}
		
		for (int y=1; y < height-1; y++)
		{
			// Left and right walls
			mapData[0][y] = CHAR_WALL;
			mapData[width-1][y] = CHAR_WALL;
			
			// Center spaces
			for (int x=1; x < width-1; x++)
				mapData[x][y] = CHAR_SPACE;
		}
	}
	
	/**
	 * Check whether a particular location is inside this map
	 * @param x  The x-coordinate we're interested in
	 * @param y  The y-coordinate we're interested in
	 * @return  Whether the location is inside this map
	 */
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
		if (!isValidPosition(x, y))
			return;
		
		if (isWall)
			mapData[x][y] = CHAR_WALL;
		else
			mapData[x][y] = '.';
		
		for (MapAlterationListener mcl : listeners)
			mcl.mapChanged(this, x, y);
		
		dirty = true;
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
		
		out.write(toString());
		dirty = false;
		
		out.flush();
		out.close();
	}
	
	/**
	 * Converts this map to a string
	 * @return  This map as a string
	 */
	public String toString()
	{
		String result = String.format("%d %d\n", width, height);
		
		for (int y=0; y < height; y++)
		{
			for (int x=0; x < width; x++)
				result += mapData[x][y];
			result += "\n";
		}
		
		if (dirty)
			result += "d";
		else
			result += "c";
		
		return result;
	}
	
	/**
	 * Take a snapshot of this map's current status
	 * @return A snapshot of this map
	 */
	public String getSnapshot()
	{
		return toString();
	}
	
	/**
	 * Restore a snapshot
	 * @param s  The snapshot to restore
	 */
	public void restoreSnapshot(String s)
	{
		Scanner in = new Scanner(s);
		int w = in.nextInt(), h = in.nextInt();
		in.nextLine();
		
		if ((w != width) || (h != height))
			throw new InvalidParameterException("Tried to restore a wrong-sized snapshot.");
		
		String line;
		for (int y=0; y < height; y++)
		{
			line = in.nextLine();
			for (int x=0; x < width; x++)
				mapData[x][y] = line.charAt(x);
		}
		
		dirty = in.next(".") == "d";
	}
	
	/**
	 * Find out if this map has been changed since it was last saved
	 * @return  True if this is an unsaved map
	 */
	public boolean isDirty()
	{
		return dirty;
	}
	
	/**
	 * Get the height of this map
	 * @return  This map's height
	 */
	public int getHeight()
	{
		return height;
	}
	
	/**
	 * Get the name of this map
	 * @return  This map's name
	 */
	public String getName()
	{
		return mapName;
	}
	
	/**
	 * Find the width of this map
	 * @return  This map's width
	 */
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
	
	/**
	 * Find out what a square's type is
	 * @param x  The x-coordinate of the square of interest
	 * @param y  The y-coordinate of the square of interest
	 * @return  The square's type
	 */
	public char getSquareType(int x, int y)
	{
		if (isValidPosition(x, y))
			return mapData[x][y];
		else
			return CHAR_WALL;
	}
	
	/**
	 * Set a square to a particular type
	 * @param x  The x-coordinate of the square to change
	 * @param y  The y-coordinate of the square to change
	 * @param c  The type to change it to
	 */
	public void setSquareType(int x, int y, char c)
	{
		if (!isValidPosition(x, y))
			return;
		
		mapData[x][y] = c;
		for (MapAlterationListener l : listeners)
			l.mapChanged(this, x, y);
		
		dirty = true;
	}
	
	/**
	 * Converts this EditableMap to a GameMap
	 * @return  A copy of this map as a GameMap
	 */
	public GameMap toGameMap()
	{
		return new GameMap(mapName, toString());
	}
}
