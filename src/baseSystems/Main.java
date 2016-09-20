package baseSystems;

import org.la4j.matrix.dense.Basic2DMatrix;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.*;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.*;

public class Main {
	public static PooledEngine engine;
	public static boolean isDebug = false;
	public static boolean isServer = false;
	public static boolean isMasterServer = false;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		engine = new PooledEngine();
		Settings settings = new Settings();
		settings.maxWidth = 1024;
		settings.maxHeight = 1024;
		TexturePacker.process(settings, "src/art", "src/images", "textures");
		boolean isConsole = false;
		for (String string : args) {
			switch (string) {
			case "-master": isMasterServer = true;
			case "-d": case "-dedicated": isConsole = true;
			case "-s": case "-server": isServer = true; break;
			case "-debug": case "-console": isDebug = true; System.out.println("Debug!");
			default: break;
			}
		}
		if (!isConsole) startGUI();
	}
	
	static void startGUI() {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Platformer";
		config.height = 600;
		config.width = 1200;
		LwjglApplication mainWindow = new LwjglApplication(new GameAdapter(), config);
	}
	
	public static void printdebug() {
		System.out.println("Hello from render?");
	}
}
