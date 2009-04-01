package tools.mapeditor;

import java.awt.*;
import javax.swing.*;


import java.io.*;

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
	private EditableMap map;
	private MapView mapView;
	
	/**
	 * Create the map editor window.
	 * Load a map with loadMap(String), loadMap(File), setMap(GameMap), or setMap(String)
	 * Show it with show()
	 */
	public MapEditor()
	{
		map = null;
		
		window = new JFrame("Sphereorit2: Map editor");
		window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		mapView = new MapView();
		JScrollPane scroller = new JScrollPane(mapView);
		window.getContentPane().add(scroller, BorderLayout.CENTER);
		
		window.pack();
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
			map = new EditableMap(name);
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
		if (args.length < 1)
		{
			MapEditor me = new MapEditor();
			me.show();
		}
		else
		{
			// TODO: Figure out how to show multiple windows without having them use the same painting thread
			System.out.println("Loading map files from command-line not yet possible.");
		}
	}
	
	private class MapView extends JComponent
	{
		private static final long serialVersionUID = 198753L;
		
		private int zoomLevel;
		
		protected MapView()
		{
			setZoomLevel(8);
		}
		
		public void setZoomLevel(int level)
		{
			if (level < 1)
				return;
			
			zoomLevel = level;
			Dimension d = new Dimension(zoomLevel*map.getWidth(), zoomLevel*map.getHeight());
			setSize(d);
			setPreferredSize(d);
			setMinimumSize(d);
			revalidate();
			repaint();
		}
		
		public void paintComponent(Graphics g)
		{
			
		}
	}
}
