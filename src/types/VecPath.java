package types;

import java.util.List;

import org.la4j.matrix.dense.Basic2DMatrix;

import com.badlogic.gdx.graphics.Color;

public class VecPath {
	public List<Basic2DMatrix> vertices;
	public Color fillType;
	
	public VecPath(List<Basic2DMatrix> vertices, Color fillType) {
		this.vertices = vertices;
		this.fillType = fillType;
	}
}