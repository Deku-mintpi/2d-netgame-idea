package baseSystems;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import editor.modes.EditPathMode;
import types.Complex;
import types.VecPath;

public class ModeActionUpdater {
	final static int MODE_PLAY = 0;
	final static int MODE_SHIFT = 1;
	final static int MODE_EDIT = 2;
	
	public static InputListener changeInpMode(int mode, GameAdapter game) {
		switch (mode) {
			case MODE_PLAY: return new InputListener() {};
			case MODE_SHIFT: return new InputListener() {};
			case MODE_EDIT: return EditPathMode.getInputListener();

		default: return new InputListener() {};
		}
	}
	
}
