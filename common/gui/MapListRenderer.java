package common.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class MapListRenderer extends Box implements ListCellRenderer
{
	private static final long serialVersionUID = 2390875L;
	
	private JLabel label1, label2;
	private Border focus, nofocus;
	
	public MapListRenderer()
	{
		super(BoxLayout.X_AXIS);
		
		label1 = new JLabel("First");
		label2 = new JLabel("Second");
		
		add(label1);
		add(createHorizontalGlue());
		add(label2);
		setOpaque(true);
		
		focus = UIManager.getBorder("List.focusCellHighlightBorder");
		nofocus = new EmptyBorder(1,1,1,1);
	}
	
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
	{
		String s = value.toString();
		
		if (s.contains("\t"))
		{
			label1.setText(s.substring(0, s.indexOf("\t")));
			label2.setText(s.substring(s.indexOf("\t")));
		}
		else
		{
			label1.setText(s);
			label2.setText("");
		}
		
		if (isSelected)
		{
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		}
		else
		{
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}
		
		if (cellHasFocus)
			setBorder(focus);
		else
			setBorder(nofocus);
		
		return this;
	}
}
