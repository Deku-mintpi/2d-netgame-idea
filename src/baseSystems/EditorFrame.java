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
	JButton[] aaButton = {null, null, null, null};
	JPanel labelPanel = new JPanel(new GridLayout(3, 1));
	JPanel aaPanel = new JPanel(new GridLayout(1, 4));
	JLabel dispY = new JLabel("yShift: 0");
	JLabel dispX = new JLabel("xShift: 0");
	
	public GameAdapter game;
	
	public EditorFrame(GameAdapter game) {
		this.game = game;
		this.setTitle("HyperEdit version 0.0.0; 2016-06-16");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(300, 400);
		this.setLayout(new BorderLayout());
		testButton.addActionListener(this);
		this.add(testButton,BorderLayout.NORTH);
		labelPanel.add(dispY);
		labelPanel.add(dispX);
		for(int i = 0; i < 4; i++) {
			aaButton[i] = new JButton((i+1)*(i+1) + " sample/pix");
			aaButton[i].addActionListener(this);
			aaPanel.add(aaButton[i]);
		}
		labelPanel.add(aaPanel);
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
		else
		{
			for (int i = 0; i < aaButton.length; i++){
				if (e.getSource().equals(aaButton[i])) {
//					game.xShift -= 10.f;
					game.adjustSamples((i+1)*(i+1));
				}
			}
		}
		
		
	}
	
	public void updateDisplay(double yVal, double xVal){
		dispY.setText("yShift: " + yVal);
		dispX.setText("xShift: " + xVal);
	}

}
