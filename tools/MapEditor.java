package tools;

import java.io.*;

import javax.swing.*;

/*
 * ' ', '.'		Empty spaces
 * '+'			Walls
 * 's', 'S'		Spawn points
 * 'f', 'F'		Flags
 * Notes:
 *   Spawn points:
 *     First team spawns on 's' and vice versa, unless there's
 *     only one case in use, in which case anybody can spawn on any spawn point
 */

/**
 * This is an interactive, graphical map editor.
 * Maybe this could be integrated into the game itself?
 * @author dvanhumb
 */
public class MapEditor
{
	private JFrame window;
	
	/**
	 * Create the map editor window.
	 * Load a map with loadMap(String), loadMap(File), setMap(GameMap), or setMap(String)
	 * Show it with show()
	 */
	public MapEditor()
	{
		window = new JFrame("Sphereorit2: Map editor");
		window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}
	
	public void show()
	{
		window.setVisible(true);
	}
	
	public void hide()
	{
		window.setVisible(false);
	}
	
	public void setVisible(boolean visible)
	{
		window.setVisible(visible);
	}
	
	public boolean isVisible()
	{
		return window.isVisible();
	}
	
	/**
	 * Load a map based on the map name
	 * @param name  The name of the map to load
	 */
	public void loadMap(String name)
	{
		try
		{
			BufferedReader in = new BufferedReader(new FileReader(String.format("maps/%s.map", name)));
			String line = in.readLine(), map = "";
			while (line != null)
			{
				map += line;
				line = in.readLine();
			}
		}
		catch (FileNotFoundException er)
		{
			er.printStackTrace();
		}
		catch (IOException er)
		{
			er.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		// TODO: Launch map editing from the command-line
	}
}
