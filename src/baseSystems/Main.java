package baseSystems;

import org.la4j.matrix.dense.Basic2DMatrix;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.*;

import types.Complex;
import world.LevelNode;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Engine engine = new Engine();
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Hyperbolic Test";
		config.height = 800;
		config.width = 1800;
		LwjglApplication mainWindow = new LwjglApplication(new GameAdapter(), config);
		
		
	}
	
	public void testSetup() {
		new LevelNode<Complex, LevelNode<Basic2DMatrix, LevelNode>>(new Complex(0, 0), null);
	}

}
