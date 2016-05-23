package baseSystems;

import org.la4j.matrix.dense.Basic2DMatrix;

import com.badlogic.ashley.core.Engine;

import types.Complex;
import world.LevelNode;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Engine engine = new Engine();
		
	}
	
	public void testSetup() {
		new LevelNode<Complex, LevelNode<Basic2DMatrix, LevelNode>>(new Complex(0, 0), null);
	}

}
