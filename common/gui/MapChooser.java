package common.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class MapChooser implements ActionListener
{
	private static MapChooser singleton = null;
	
	private JDialog dialog;
	private JList list;
	private DefaultListModel listModel;
	private JButton buttonOkay, buttonCancel;
	
	private String selection = null;
	
	private MapChooser()
	{
		dialog = new JDialog();
		dialog.setTitle("Sphereority 2 - Choose a map");
		dialog.setModal(true);
		dialog.getContentPane().setLayout(new BorderLayout());
		
		listModel = new DefaultListModel();
		list = new JList(listModel);
		
		JScrollPane scroller = new JScrollPane(list);
		scroller.setPreferredSize(new Dimension(80, 60));
		dialog.getContentPane().add(scroller, BorderLayout.CENTER);
		
		buttonOkay = new JButton("Okay");
		buttonOkay.setMnemonic(0);
		buttonOkay.addActionListener(this);
		buttonOkay.setDefaultCapable(true);
		buttonCancel = new JButton("Cancel");
		buttonCancel.setMnemonic(0);
		buttonCancel.addActionListener(this);
		
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panel.add(buttonOkay);
		panel.add(buttonCancel);
		
		dialog.getContentPane().add(panel, BorderLayout.SOUTH);
		
		dialog.pack();
		updateMapList();
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(buttonOkay))
		{
			try
			{
				selection = (String)list.getSelectedValue();
				dialog.setVisible(false);
			}
			catch (Throwable er)
			{
				er.printStackTrace();
			}
		}
		else if (e.getSource().equals(buttonCancel))
		{
			selection = null;
			dialog.setVisible(false);
		}
	}
	
	private void updateMapList()
	{
		Object selected = list.getSelectedValue();
		listModel.clear();
		
		File directory = new File("maps");
		File[] fileList = directory.listFiles();
		for (File f : fileList)
		{
			if (!f.canRead())
				continue;
			if (f.getName().endsWith(".map"))
				listModel.addElement(f.getName().substring(0, f.getName().lastIndexOf('.')));
		}
		
		if (listModel.contains(selected))
			list.setSelectedValue(selected, true);
	}
	
	private void show()
	{
		list.grabFocus();
		dialog.setVisible(true);
	}
	
	public static String chooseMap()
	{
		if (singleton == null)
			singleton = new MapChooser();
		
		singleton.show();
		
		return singleton.selection;
	}
}
