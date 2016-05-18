package baseComponents;

import org.la4j.vector.dense.BasicVector;

import com.badlogic.ashley.core.*;

// This stores an object's local motion.
// distance, angle, spin

public class VelocityComponent implements Component {
	public double distance, angle, spin;
}