package baseComponents;

import org.la4j.matrix.dense.Basic2DMatrix;

import com.badlogic.ashley.core.*;

// This stores an object's local motion.
// distance, angle, spin

public class VelocityComponent implements Component {
	public double distance, angle, spin;
	public Basic2DMatrix currentTransform;
}