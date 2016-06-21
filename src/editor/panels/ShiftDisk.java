package editor.panels;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import baseSystems.EditorFrame;
import baseSystems.EditorFrame.ActionPanel;
import baseSystems.EditorFrame.EditorPanel;
import baseSystems.GameAdapter;
import editor.modes.ShiftDiskMode;

public class ShiftDisk implements EditorPanel {
	
	public static void updatePanel() {
		JLabel xLabel = new JLabel("xShift: ");
		JLabel yLabel = new JLabel("yShift: ");
		JTextField xEdit = new JTextField("0.0", 4);
		JTextField yEdit = new JTextField("0.0", 4);
		
		editor.game.setInpMode(1);
		
		panel.setListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (e.getSource().equals(xEdit)) {
					ShiftDiskMode.setShift(Double.valueOf(xEdit.getText()), Double.valueOf(yEdit.getText()));
				}
				if (e.getSource().equals(yEdit)) {
					ShiftDiskMode.setShift(Double.valueOf(xEdit.getText()), Double.valueOf(yEdit.getText()));
				}
			}
		});
		
		xEdit.addActionListener(panel.listener);
		yEdit.addActionListener(panel.listener);
		JPanel shiftPane = new JPanel();
		shiftPane.add(xLabel);
		shiftPane.add(xEdit);
		shiftPane.add(yLabel);
		shiftPane.add(yEdit);
		panel.setPanel(shiftPane);
		editor.pack();
	}
}
