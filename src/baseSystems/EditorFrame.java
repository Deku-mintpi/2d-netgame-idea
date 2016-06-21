package baseSystems;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import editor.ModePanel;
import editor.panels.ShiftDisk;

public class EditorFrame extends JFrame implements ActionListener {
	/**
	 * 
	 */
	
	public static GameAdapter game;
	int editMode = 0; //unset
	
	ModePanel modeMenu = new ModePanel(this);
	public static ActionPanel mainContextPanel;
	
	public EditorFrame(GameAdapter game) {
		this.game = game;
		EditorFrame.mainContextPanel = new ActionPanel();
		this.setTitle("HyperEdit version 0.0.0; 2016-06-16");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(300, 400);
		this.setLayout(new BorderLayout());
		this.add(modeMenu, BorderLayout.NORTH);
		this.add(mainContextPanel.panel, BorderLayout.SOUTH);
		this.pack();
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(mainContextPanel)) {
			game.shiftGL(-25, 0);
		}
	}
	
	public void updateDisplay(double yVal, double xVal){
//		dispY.setText("yShift: " + yVal);
//		dispX.setText("xShift: " + xVal);
	}
	
	public interface Mode {
		final GameAdapter game = EditorFrame.game;
		
		public int modeType();
	}
	
	public interface EditorPanel {
		ActionPanel panel = EditorFrame.mainContextPanel;
		final EditorFrame editor = game.editorWindow;
	}
	
	public class ActionPanel {
		public JPanel panel;
		public ActionListener listener;
		
		public ActionPanel() {
			this.panel = new JPanel();
			listener = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {}
			};
		}
		
		public ActionPanel(JPanel panel) {
			this.panel = panel;
			listener = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {}
			};
		}
		
		public void setPanel(JPanel panel) {
			this.panel.removeAll();
			this.panel.add(panel);
		}
		
		public void setListener(ActionListener listener) {
			this.listener = listener;
		}
	}
	
}
