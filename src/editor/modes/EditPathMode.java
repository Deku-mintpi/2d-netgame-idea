package editor.modes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import javax.swing.JTextField;
import javax.swing.JPanel;

import baseSystems.EditorFrame.Mode;
import editor.panels.EditPath;
import types.Complex;
import types.VecPath;

public class EditPathMode implements Mode {
	
	public static void setVertex(int path, int vert, Complex coords) {
		game.shapes.set(path, new VecPath(new Complex[]
			{new Complex(-1,0), new Complex(0, -1), coords},
			game.shapes.get(path).fillType));
	}
	
	public static InputListener getInputListener() {
		return new InputListener() {
			public boolean mouseMoved(InputEvent event, float x, float y) {
				int pathNum = Integer.valueOf(((JTextField) ((JPanel) game.editorWindow.mainContextPanel.panel.getComponents()[0]).getComponents()[1]).getText());
				game.shapes.set(pathNum, new VecPath(new Complex[] {new Complex(-1,0), new Complex(0, -1), new Complex((x - game.width / 2)/(game.height / 2), (y - game.height / 2)/(game.height / 2))},game.shapes.get(pathNum).fillType));
				EditPath.updatePanel(pathNum);
				return true;
			}
			
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				int pathNum = Integer.valueOf(((JTextField) ((JPanel) game.editorWindow.mainContextPanel.panel.getComponents()[0]).getComponents()[1]).getText());
				game.shapes.set(pathNum,new VecPath(new Complex[] {new Complex(-1,0), new Complex(0, -1), new Complex((x - game.width / 2)/(game.height / 2), (y - game.height / 2)/(game.height / 2))},Color.BLUE));
				game.shapes.add(new VecPath(new Complex[] {new Complex(-1,0), new Complex(0, -1), new Complex((x - game.width / 2)/(game.height / 2), (y - game.height / 2)/(game.height / 2))}, Color.RED));
				EditPath.updatePanel(game.shapes.size()-1);
				return true;
			}
			
			public boolean scrolled(InputEvent event, float x, float y, int amount) {
				int pathNum = Integer.valueOf(((JTextField) ((JPanel) game.editorWindow.mainContextPanel.panel.getComponents()[0]).getComponents()[1]).getText());
				System.out.println(amount);
				EditPath.updatePanel(((pathNum-amount)%game.shapes.size()+game.shapes.size())%game.shapes.size());
				return true;
			}
			
		}; 
		
	}

	@Override
	public int modeType() { return 2; }

}
