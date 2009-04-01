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
		
	}
	
	public void setMap(EditableMap map)
	{
		this.map = map;
		
	}
	
	public void setZoomLevel(int level)
	{
		if (level < 1)
			return;
		
		zoomLevel = level;
		
		if (map == null)
			return;
		
		Dimension d = new Dimension(zoomLevel*map.getWidth(), zoomLevel*map.getHeight());
		setSize(d);
		setPreferredSize(d);
		setMinimumSize(d);
		revalidate();
		repaint();
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
		int min_x, min_y, max_x, max_y;
		min_x = Math.max(clipBounds.x / zoomLevel - 1, 0);
		max_x = Math.min((clipBounds.x + clipBounds.width) / zoomLevel + 1, map.getWidth() - 1);
		min_y = Math.max(clipBounds.y / zoomLevel - 1, 0);
		max_y = Math.min((clipBounds.y + clipBounds.height) / zoomLevel + 1, map.getHeight() - 1);
		
		// Draw the map
		for (int y = min_y; y <= max_y; y++)
			for (int x = min_x; x <= max_x; x++)
			{
				
			}
		
		// Draw a grid if we're zoomed in enough
		if (zoomLevel >= 8)
		{
			
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
