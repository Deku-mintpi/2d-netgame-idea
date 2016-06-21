package editor.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import baseSystems.EditorFrame.EditorPanel;
import editor.modes.EditPathMode;
import editor.modes.ShiftDiskMode;
import types.Complex;

public class EditPath implements EditorPanel {
	
	public static void updatePanel(int pathNum) {
		JLabel shapeLabel = new JLabel("Path#: ");
		JLabel xLabel = new JLabel("x: ");
		JLabel yLabel = new JLabel("y: ");
		
		JTextField shapeEdit = new JTextField("" + pathNum, 2);
		JTextField xEdit = new JTextField("" + editor.game.shapes.get(pathNum).vertices[2].re, 5);
		JTextField yEdit = new JTextField("" + editor.game.shapes.get(pathNum).vertices[2].im, 5);
		
		editor.game.setInpMode(2);
		
		panel.setListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (e.getSource().equals(shapeEdit)) {
					xEdit.setText("" + editor.game.shapes.get(Integer.valueOf(shapeEdit.getText())).vertices[2].re);
					yEdit.setText("" + editor.game.shapes.get(Integer.valueOf(shapeEdit.getText())).vertices[2].im);
				}
				if (e.getSource().equals(xEdit)) {
					EditPathMode.setVertex(Integer.valueOf(shapeEdit.getText()), 2, new Complex(Double.valueOf(xEdit.getText()), Double.valueOf(yEdit.getText())));
				}
				if (e.getSource().equals(yEdit)) {
					EditPathMode.setVertex(Integer.valueOf(shapeEdit.getText()), 2, new Complex(Double.valueOf(xEdit.getText()), Double.valueOf(yEdit.getText())));
				}
			}
		});
		
		shapeEdit.addActionListener(panel.listener);
		xEdit.addActionListener(panel.listener);
		yEdit.addActionListener(panel.listener);
		JPanel shiftPane = new JPanel();
		shiftPane.add(shapeLabel);
		shiftPane.add(shapeEdit);
		shiftPane.add(xLabel);
		shiftPane.add(xEdit);
		shiftPane.add(yLabel);
		shiftPane.add(yEdit);
		panel.setPanel(shiftPane);
		editor.pack();
	}

}
