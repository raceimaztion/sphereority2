package tools.mapeditor;

import common.GameMap;

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
	 * @param listener The listener to add
	 */
	public void addMapChangeListener(MapAlterationListener listener)
	{
		listeners.add(listener);
	}
	
	/**
	 * 
	 * @param listener
	 */
	public void removeMapChangeListener(MapAlterationListener listener)
	{
		listeners.remove(listener);
	}
}
