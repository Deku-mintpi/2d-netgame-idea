package baseSystems;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EditorFrame extends JFrame implements ActionListener {
	/**
	 * 
	 */
	
	JButton testButton = new JButton("Shift GL left");
	JPanel labelPanel = new JPanel(new GridLayout(2, 1));
	JLabel dispY = new JLabel("yShift: 0");
	JLabel dispX = new JLabel("xShift: 0");
	public GameAdapter game;
	
	public EditorFrame(GameAdapter game) {
		this.game = game;
		this.setTitle("HyperEdit version 0.0.0; 2016-06-16");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(200, 200);
		this.setLayout(new BorderLayout());
		testButton.addActionListener(this);
		this.add(testButton,BorderLayout.NORTH);
		labelPanel.add(dispY);
		labelPanel.add(dispX);
		this.add(labelPanel, BorderLayout.SOUTH);
		this.pack();
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(testButton)) {
//			game.xShift -= 10.f;
			game.editorTest();
		}
	}
	
	public void updateDisplay(double yVal, double xVal){
		dispY.setText("yShift: " + yVal);
		dispX.setText("xShift: " + xVal);
	}

}
