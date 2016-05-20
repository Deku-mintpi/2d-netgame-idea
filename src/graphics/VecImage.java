package graphics;

import java.util.List;

import org.la4j.matrix.dense.Basic2DMatrix;

import com.badlogic.gdx.graphics.Color;

import types.Complex;

public class VecImage {
	protected List<Path> paths;
	
	class Path {
		protected List<Basic2DMatrix> vertices;
		protected Color fillType;
	}
}