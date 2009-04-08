package tools.mapeditor;

import java.awt.*;
import javax.swing.*;

public class MapView extends JComponent implements MapAlterationListener
{
	private static final long serialVersionUID = 98234532L;
	
	private EditableMap map;
	private int zoomLevel;
	
	public MapView()
	{
		zoomLevel = 16;
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
		for (int y = min_y; y <= max_y; y++)
			for (int x = min_x; x <= max_x; x++)
			{
				// TODO: Draw each cell in the grid
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
	}

	public void mapChanged(EditableMap map, int x, int y)
	{
		if (map == this.map)
		{
			repaint(zoomLevel*x, zoomLevel*y, zoomLevel, zoomLevel);
		}
	}
}
