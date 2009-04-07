package tools.mapeditor;

import java.awt.*;
import java.awt.event.*;

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
public class MapEditor implements ActionListener
{
	private JFrame window;
	private EditableMap map;
	private MapView mapView;
	// Menu items:
	private JMenuItem menuNew, menuOpen, menuSave, menuSaveAs, menuQuit;
	
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
		
		// Editing part of the window
		mapView = new MapView();
		JScrollPane scroller = new JScrollPane(mapView);
		window.getContentPane().add(scroller, BorderLayout.CENTER);
		
		// Menus:
		JMenuBar menuBar = new JMenuBar();
		window.setJMenuBar(menuBar);
		
		// Map menu:
		JMenu mapMenu = new JMenu("Map");
		mapMenu.setMnemonic(0);
		menuBar.add(mapMenu);
		
		menuNew = createMenuItem("New", 0);
		menuOpen = createMenuItem("Open...", 0);
		menuSave = createMenuItem("Save", 0);
		menuSaveAs = createMenuItem("Save as...", 5);
		menuQuit = createMenuItem("Quit", 0);
		
		mapMenu.add(menuNew);
		mapMenu.addSeparator();
		mapMenu.add(menuOpen);
		mapMenu.add(menuSave);
		mapMenu.add(menuSaveAs);
		mapMenu.addSeparator();
		mapMenu.add(menuQuit);
		
		window.pack();
	}
	
	private JMenuItem createMenuItem(String label, int mnemonic)
	{
		JMenuItem item = new JMenuItem(label, mnemonic);
		item.addActionListener(this);
		
		return item;
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
			mapView.setMap(map);
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
	
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		
		if (source.equals(menuNew))
		{
			// TODO: Show new map dialog
		}
		else if (source.equals(menuOpen))
		{
			// TODO: Show open map dialog
		}
		else if (source.equals(menuSave))
		{
			if (map != null)
			{
				try
				{
					map.save();
				}
				catch (IOException er)
				{
					JOptionPane.showMessageDialog(window, String.format("Error saving map named %s.\nError was:\n%s", map.getName(), er.toString()), "Sphereority 2 Map Editor", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		else if (source.equals(menuSaveAs))
		{
			
		}
		else if (source.equals(menuQuit))
		{
			
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
}
