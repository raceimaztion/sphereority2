package client.gui;

import javax.swing.JComponent;

/**
 * This is a card to display in a CardPanel
 * @author dvanhumb
 */
public class CardComponent extends JComponent
{
	private static final long serialVersionUID = 0x98734651;
	
	private String name;
	
	public CardComponent(String name)
	{
		this.name = name;
	}
	
	public String getCardName()
	{
		return name;
	}
}
