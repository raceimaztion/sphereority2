package client.gui;

import java.awt.*;

public abstract class AbstractGameView extends CardComponent
{	
	private static final long serialVersionUID = 0x98324;
	
	public AbstractGameView(String name)
	{
		super(name);
		setBackground(Color.black);
		setOpaque(true);
	}
	
	/**
	 * The method overridden to draw the current game view
	 * @param g  The graphics context to draw on
	 */
	public abstract void renderView(Graphics2D g);
	
	public void paintChildren(Graphics g)
	{
		renderView((Graphics2D) g);
	}
}
