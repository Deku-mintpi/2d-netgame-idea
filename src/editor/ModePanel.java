package editor;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import baseSystems.EditorFrame;
import editor.panels.EditPath;
import editor.panels.ShiftDisk;

public class ModePanel extends JPanel implements ActionListener {
	
	EditorFrame editor;
	JButton shift = new JButton("ShiftGL");
	JButton editPath = new JButton("Edit Paths");
	GridLayout layout = new GridLayout(1, 2);
	
	public ModePanel(EditorFrame editor) {
		this.editor = editor;
		this.setLayout(layout);
		shift.addActionListener(this);
		editPath.addActionListener(this);
		this.add(shift);
		this.add(editPath);
		this.setPreferredSize(new Dimension(400,25));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(shift)) {
			ShiftDisk.updatePanel();
		}
		if (e.getSource().equals(editPath)) {
			EditPath.updatePanel(editor.game.shapes.size()-1);
		}
		
	}

}
