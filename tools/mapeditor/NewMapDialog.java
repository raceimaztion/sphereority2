package tools.mapeditor;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class NewMapDialog implements common.MapConstants, ActionListener
{
	private static NewMapDialog singleton = null;
	
	private JDialog dialog;
	private JButton buttonOkay, buttonCancel;
	private JTextField mapName;
	private JSpinner spinWidth, spinHeight;
	private SpinnerNumberModel widthModel, heightModel;
	private boolean success = false;
	
	private NewMapDialog()
	{
		dialog = new JDialog();
		dialog.setTitle("Sphereority 2 - Map Editor - New map");
		dialog.setModal(true);
		dialog.getContentPane().setLayout(new BorderLayout());
		
		mapName = new JTextField();
		widthModel = new SpinnerNumberModel(MINIMUM_WIDTH, MINIMUM_WIDTH, MAXIMUM_WIDTH, 1);
		spinWidth = new JSpinner(widthModel);
		heightModel = new SpinnerNumberModel(MINIMUM_HEIGHT, MINIMUM_HEIGHT, MAXIMUM_HEIGHT, 1);
		spinHeight = new JSpinner(heightModel);
		
		buttonOkay = new JButton("Okay");
		buttonOkay.setMnemonic('O');
		buttonOkay.setDefaultCapable(true);
		buttonOkay.addActionListener(this);
		buttonCancel = new JButton("Cancel");
		buttonCancel.setMnemonic('C');
		buttonCancel.setDefaultCapable(false);
		buttonCancel.addActionListener(this);
		
		JPanel panel = new JPanel(new GridLayout(0, 2));
		panel.add(new JLabel("Map name:"));
		panel.add(mapName);
		panel.add(new JLabel("Map width:"));
		panel.add(spinWidth);
		panel.add(new JLabel("Map height:"));
		panel.add(spinHeight);
		dialog.getContentPane().add(panel, BorderLayout.CENTER);
		
		panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panel.add(buttonOkay);
		panel.add(buttonCancel);
		dialog.getContentPane().add(panel, BorderLayout.SOUTH);
		
		dialog.pack();
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(buttonOkay))
		{
			success = true;
			dialog.setVisible(false);
		}
		else if (e.getSource().equals(buttonCancel))
		{
			success = false;
			dialog.setVisible(false);
		}
	}
	
	private void show(Component parent)
	{
		dialog.setLocationRelativeTo(parent);
		dialog.setVisible(true);
	}
	
	public static EditableMap createMap(Component parent)
	{
		if (singleton == null)
			singleton = new NewMapDialog();
		
		singleton.show(parent);
		
		if (singleton.success)
			return new EditableMap(singleton.mapName.getText(), singleton.widthModel.getNumber().intValue(), singleton.heightModel.getNumber().intValue());
		else
			return null;
	}
}
