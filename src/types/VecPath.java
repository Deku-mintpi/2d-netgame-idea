package types;

import java.util.List;

import org.la4j.matrix.dense.Basic2DMatrix;

import com.badlogic.gdx.graphics.Color;

public class VecPath{
	public List<Basic2DMatrix> vertices;
	public Color fillType;
	
	public VecPath(List<Basic2DMatrix> vertices, Color fillType) {
		this.vertices = vertices;
		this.fillType = fillType;
	}
	
	public Complex[] toCoordinates(Complex pos) {
		Complex[] outCoords = new Complex[vertices.size()];
		Complex origin = new Complex(0, 0);
		for(int i = 0; i < vertices.size(); i++) {
			Complex v = origin.mobius(vertices.get(i));
			outCoords[i] = (pos.times(v).times(2.0).add(pos.absSq())).times(v).add(pos.times(v.absSq()-1));
			outCoords[i] = outCoords[i].divide(pos.times(v).times(2).add(v.absSq()*pos.absSq()+1));
		}
		return outCoords;
	}
}