package hypmath;

import org.la4j.matrix.dense.*;
import org.la4j.vector.dense.*;
import org.la4j.*;

public class HyperCoord {
	public BasicVector coords;
	public Matrix rotMatrix;
	
//	public double direction;
	
	public HyperCoord(double X, double Y) {
		coords.set(0, X); coords.set(1, Y); coords.set(2, Math.sqrt(X*X + Y*Y + 1));
//		direction = 0;
	}
	
//	public InternalCoord(double X, double Y, double B) {
//		this.X = X;
//		this.Y = Y;
//		Z = Math.sqrt(X*X + Y*Y + 1);
//		direction = B;
//	}
	
	public HyperCoord(double X, double Y, /*double B,*/ double Z) {
		coords.set(0, X); coords.set(1, Y); coords.set(2, Z);
//		direction = B;
	}
	
	public double x() { return coords.get(0); }
	public double y() { return coords.get(1); }
	public double z() { return coords.get(2); }
	
	//hyperbolic equivalent of dot product, essentially...
	public double hyperDot(HyperCoord v) {
		return (this.x()*v.x() + this.y()*v.y() - this.z()*v.z());
	}
	
	//normal dot product
	public double stdDot(HyperCoord v) {
		return (this.x()*v.x() + this.y()*v.y() + this.z()*v.z());
	}
	
	public double hyperDist(HyperCoord v) {
		return Math.sqrt((this.x()-v.x())*(this.x()-v.x())+(this.y()-v.y())*(this.y()-v.y())-(this.z()-v.z())*(this.z()-v.z())); // hopefully this is right...
	}
	
	public Vector rotate(double rX, double rY, double rZ) {
		Matrix newMatrix;
		// rotation around Z axis
		newMatrix = Basic2DMatrix.from2DArray(new double[][] {{Math.cos(rZ), -Math.sin(rZ), 0}, {Math.sin(rZ), Math.cos(rZ), 0}, {0, 0, 1}});
		// multiply by rotation around Y axis
		newMatrix = newMatrix.multiply(Basic2DMatrix.from2DArray(new double[][] {{Math.cosh(rY), 0, Math.sinh(rY)}, {0, 1, 0}, {-Math.sinh(rY), 0, Math.cosh(rY)}}));
		// multiply by rotation around X axis
		newMatrix = newMatrix.multiply(Basic2DMatrix.from2DArray(new double[][] {{1, 0, 0}, {0, Math.cosh(rX), -Math.sinh(rX)}, {0, Math.sinh(rX), Math.cosh(rX)}}));
		
		rotMatrix = newMatrix.multiply(rotMatrix);
		//finally, rotate the coordinates
		return newMatrix.multiply(coords);
	}
}
