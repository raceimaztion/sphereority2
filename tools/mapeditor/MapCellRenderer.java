package tools.mapeditor;

import java.awt.*;
import java.awt.geom.*;

import common.MapConstants;

public class MapCellRenderer implements MapConstants
{
	public static final Color WALL_COLOR = Color.gray;
	public static final Color SPACE_COLOR = Color.white;
	public static final Color SPAWN_COLOR = Color.yellow;
	public static final Color FLAG_COLOR = Color.orange;
	
	private static Font font;
	private static Rectangle2D bounds;
	private static int fontHeight = -1;
	
	/**
	 * Draw a map cell
	 * @param g  The graphics context to use
	 * @param x  The left side of the cell
	 * @param y  The top of the cell
	 * @param width  The width of the cell
	 * @param height  The height of the cell
	 * @param c  The cell's type
	 */
	public static void renderCell(Graphics2D g, int x, int y, int width, int height, char c)
	{
		if (c == CHAR_WALL)
		{
			g.setColor(WALL_COLOR);
			g.fillRect(x, y, width, height);
			return;
		}
		else if (c == CHAR_SPAWN_A || c == CHAR_SPAWN_B)
		{
			g.setColor(SPAWN_COLOR);
			g.fillRect(x, y, width, height);
		}
		else if (c == CHAR_FLAG_A || c == CHAR_FLAG_B)
		{
			g.setColor(FLAG_COLOR);
			g.fillRect(x, y, width, height);
		}
		else // if (c == CHAR_SPACE)
		{
			g.setColor(SPACE_COLOR);
			g.fillRect(x, y, width, height);
			return;
		}
		
		g.setColor(Color.black);
		Font oldFont = g.getFont();
		if (font == null && fontHeight != height)
		{
			font = g.getFont().deriveFont(height * 0.75f);
			bounds = g.getFontMetrics().getStringBounds("A", g);
			
			fontHeight = height;
		}
		
		g.setFont(font);
		float dx, dy;
		dx = x + (float)(width - bounds.getWidth() - bounds.getX()) * 0.5f;
		dy = y + (float)(height + bounds.getHeight() + 0.25 * bounds.getY()) * 0.5f;
		
		if (c == CHAR_FLAG_A || c == CHAR_SPAWN_A)
			g.drawString("A", dx, dy);
		else
			g.drawString("B", dx, dy);
		
		g.setFont(oldFont);
	}
}
