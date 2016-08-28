package baseSystems;

import org.la4j.matrix.dense.Basic2DMatrix;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.*;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Engine engine = new Engine();
		boolean isConsole = false;
		boolean isServer = false;
		for (String string : args) {
			switch (string) {
			case "-d": case "-dedicated": isConsole = true;
			case "-s": case "-server": isServer = true; break;
			default: break;
			}
		}
		if (!isConsole) startGUI();
	}
	
	static void startGUI() {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Hyperbolic Test";
		config.height = 600;
		config.width = 1200;
		LwjglApplication mainWindow = new LwjglApplication(new GameAdapter(), config);
	}
}
