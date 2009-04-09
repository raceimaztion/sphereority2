package tools.mapeditor;

import common.MapConstants;

import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import javax.swing.*;

/**
 * This displays an EditableMap to edit.
 * @author dvanhumb
 */
public class MapView extends JComponent implements MapConstants, MapAlterationListener, MouseListener, MouseMotionListener, ActionListener
{
	private static final long serialVersionUID = 98234532L;
	
	private EditableMap map;
	private int zoomLevel;
	private Rectangle selection;
	private int pinned_x = 0, pinned_y = 0;
	private JPopupMenu popupMenu;
	private JMenuItem menuCopy, menuPaste, menuSpace, menuWall, menuSpawnA, menuSpawnB, menuFlagA, menuFlagB;
	
	public MapView()
	{
		zoomLevel = 16;
		
		addMouseListener(this);
		addMouseMotionListener(this);
		setFocusable(true);
		
		// Our popup menu
		menuCopy = createMenuItem("Copy", 0, null);
		menuPaste = createMenuItem("Paste", 0, null);
		menuSpace = createMenuItem("Space", 0, null);
		menuWall = createMenuItem("Wall", 0, null);
		
		popupMenu = new JPopupMenu("Edit");
		popupMenu.add(menuCopy);
		popupMenu.add(menuPaste);
		popupMenu.addSeparator();
		popupMenu.add(menuSpace);
		popupMenu.add(menuWall);
		
		for (Component c : popupMenu.getComponents())
			c.setEnabled(false);
		setComponentPopupMenu(popupMenu);
	}
	
	private JMenuItem createMenuItem(String label, int mnemonic, Icon icon)
	{
		JMenuItem item = new JMenuItem(label, icon);
		if (mnemonic >= 0 && mnemonic < label.length())
			item.setMnemonic(label.charAt(mnemonic));
		item.addActionListener(this);
		
		return item;
	}
	
	private void updateSize()
	{
		if (map == null)
			return;
		
		Dimension d = new Dimension(zoomLevel*map.getWidth(), zoomLevel*map.getHeight());
		setSize(d);
		setPreferredSize(d);
		setMinimumSize(d);
		revalidate();
		repaint();
	}
	
	public void setMap(EditableMap map)
	{
		this.map = map;
		
		for (Component c : popupMenu.getComponents())
			c.setEnabled(map != null);
		
		updateSize();
	}
	
	public void setZoomLevel(int level)
	{
		if (level < 1)
			return;
		
		zoomLevel = level;
		
		updateSize();
	}
	
	public int getZoomLevel()
	{
		return zoomLevel;
	}
	
	public EditableMap getMap()
	{
		return map;
	}
	
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		
		// Figure out the bounds of the redraw rectangle
		Rectangle clipBounds = g2.getClipBounds();
		
		// If we don't have a map, draw the background blank and return
		if (map == null)
		{
			g2.setColor(getBackground());
			g2.fill(g2.getClip());
			return;
		}
		
		g2.setColor(Color.white);
		g2.fill(g2.getClip());
		
		int min_x, min_y, max_x, max_y;
		min_x = Math.max(clipBounds.x / zoomLevel - 1, 0);
		max_x = Math.min((clipBounds.x + clipBounds.width) / zoomLevel + 1, map.getWidth());
		min_y = Math.max(clipBounds.y / zoomLevel - 1, 0);
		max_y = Math.min((clipBounds.y + clipBounds.height) / zoomLevel + 1, map.getHeight());
		
		// Draw the map
		for (int y = min_y; y < max_y; y++)
			for (int x = min_x; x < max_x; x++)
			{
				char c = map.getSquareType(x, y);
				if (c == MapConstants.CHAR_WALL)
					g2.setColor(Color.darkGray);
				else if (c == MapConstants.CHAR_FLAG_A || c == MapConstants.CHAR_FLAG_B)
					g2.setColor(Color.orange);
				else if (c == MapConstants.CHAR_SPAWN_A || c == MapConstants.CHAR_SPAWN_B)
					g2.setColor(Color.yellow);
				else // it's a wall
					g2.setColor(Color.white);
				
				g2.fillRect(x*zoomLevel, y*zoomLevel, zoomLevel, zoomLevel);
			}
		
		// Draw a grid if we're zoomed in enough
		if (zoomLevel >= 8)
		{
			g2.setColor(Color.black);
			for (int x=min_x; x <= max_x; x++)
				g2.drawLine(x*zoomLevel, min_y*zoomLevel, x*zoomLevel, max_y*zoomLevel);
			for (int y=min_y; y <= max_y; y++)
				g2.drawLine(min_x*zoomLevel, y*zoomLevel, max_x*zoomLevel, y*zoomLevel);
		}
		
		// Do we have a selection?
		if (selection != null)
		{
			// Make sure the selection is inside the current map
			
			// Draw the selection
			g2.setColor(Color.green);
			g2.drawRect(selection.x*zoomLevel,
					selection.y*zoomLevel,
					selection.width*zoomLevel,
					selection.height*zoomLevel);
		}
	}

	public void mapChanged(EditableMap map, int x, int y)
	{
//		if (map == this.map)
			repaintCell(x, y);
	}
	
	public void repaintCell(int x, int y)
	{
		repaint(zoomLevel*x, zoomLevel*y, zoomLevel+1, zoomLevel+1);
	}
	
	public void repaintCells(Rectangle rect)
	{
		if (rect != null)
			repaint(rect.x*zoomLevel, rect.y*zoomLevel, rect.width*zoomLevel + 1, rect.height*zoomLevel + 1);
	}
	
	public void repaintCells(int x, int y, int width, int height)
	{
		repaint(zoomLevel*x, zoomLevel*y, width*zoomLevel+1, height*zoomLevel+1);
	}
	
	public void mouseClicked(MouseEvent e)
	{
		// If it's not the primary mouse button, don't do anything
		if ((e.getButton() != MouseEvent.BUTTON1) || (map == null))
			return;
		
		int x = e.getX(), y = e.getY();
		x /= zoomLevel;
		y /= zoomLevel;
		x = Math.max(0, Math.min(map.getWidth()-1, x));
		y = Math.max(0, Math.min(map.getHeight()-1, y));
		
		if ((map == null) || (x < 0) || (x >= map.getWidth()) || (y < 0) || (y >= map.getHeight()))
			return;
		
		Rectangle rect = null;
		if (selection != null)
			rect = new Rectangle(selection);
		selection = new Rectangle(x, y, 1, 1);
		
		repaintCells(rect);
		repaintCells(selection);
	}

	public void mouseEntered(MouseEvent e) { }

	public void mouseExited(MouseEvent e) { }

	public void mousePressed(MouseEvent e)
	{
		// If it's not the primary mouse button, don't do anything
		if ((e.getButton() != MouseEvent.BUTTON1) || (map == null))
			return;
		
		int x = e.getX(), y = e.getY();
		x /= zoomLevel;
		y /= zoomLevel;
		x = Math.max(0, Math.min(map.getWidth()-1, x));
		y = Math.max(0, Math.min(map.getHeight()-1, y));
		pinned_x = x;
		pinned_y = y;
		
		Rectangle rect = null;
		if (selection != null)
			rect = new Rectangle(selection);
		selection = new Rectangle(x, y, 1, 1);
		
		repaintCells(rect);
		repaintCells(selection);
	}

	public void mouseReleased(MouseEvent e) { }

	public void mouseDragged(MouseEvent e)
	{
		// If it's not the primary mouse button, don't do anything
		if (map == null)
			return;
		
		int x = e.getX(), y = e.getY();
		x /= zoomLevel;
		y /= zoomLevel;
		x = Math.max(0, Math.min(map.getWidth()-1, x));
		y = Math.max(0, Math.min(map.getHeight()-1, y));
		
		int width = x - pinned_x, height = y - pinned_y;
		Rectangle rect = new Rectangle(selection);
		
		if (width < 0)
			selection.x = pinned_x + width;
		else
			selection.x = pinned_x;
		if (height < 0)
			selection.y = pinned_y + height;
		else
			selection.y = pinned_y;
		
		selection.width = 1 + Math.abs(width);
		selection.height = 1 + Math.abs(height);
		
		if (!rect.equals(selection))
		{
			repaintCells(rect);
			repaintCells(selection);
		}
	}

	public void mouseMoved(MouseEvent e) { }
	
	/**
	 * Get what's currently selected
	 * @return  The current selection
	 */
	public String copySelection()
	{
		if (selection == null)
			return null;
		
		String result = String.format("%d %d\n", selection.width, selection.height);
		for (int y=0; y < selection.height; y++)
		{
			for (int x=0; x < selection.width; x++)
				result += map.getSquareType(x + selection.x, y + selection.y);
			result += "\n";
		}
		
		return result;
	}
	
	/**
	 * Paste in a selection
	 * @param selection  The selection to paste in
	 */
	public void pasteSelection(String selection)
	{
		Scanner in = new Scanner(selection);
		int width = in.nextInt(), height = in.nextInt();
		in.nextLine();
		
		String line;
		for (int y=0; y < height; y++)
		{
			line = in.nextLine();
			for (int x=0; x < width; x++)
			{
				map.setSquareType(x+this.selection.x, y+this.selection.y, line.charAt(x));
			}
		}
		
		repaintCells(this.selection.x, this.selection.y, width, height);
	}
	
	public void fillSelectionWith(char c)
	{
		if (selection == null)
			return;
		
		for (int y=0; y < selection.height; y++)
			for (int x=0; x < selection.width; x++)
				map.setSquareType(x + selection.x, y + selection.y, c);
		
		repaintCells(selection);
	}

	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		
		if (source.equals(menuCopy))
		{
			// TODO: Move copy code here from MapEditor
		}
		else if (source.equals(menuPaste))
		{
			// TODO: Move paste code here from MapEditor
		}
		else if (source.equals(menuSpace))
		{
			fillSelectionWith(CHAR_SPACE);
		}
		else if (source.equals(menuWall))
		{
			fillSelectionWith(CHAR_WALL);
		}
	}
}
