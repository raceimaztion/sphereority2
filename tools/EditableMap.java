package tools;

import java.io.*;

import common.GameMap;

public class EditableMap extends GameMap
{
	public EditableMap(String mapName) throws FileNotFoundException, IOException
	{
		super(mapName);
	}
	
	public EditableMap(String mapName, String map)
	{
		super(mapName, map);
	}
}
