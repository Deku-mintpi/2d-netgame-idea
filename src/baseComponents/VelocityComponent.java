package baseComponents;

import org.la4j.vector.dense.BasicVector;

import com.badlogic.ashley.core.*;

// This stores an object's local motion.
// x, y, rot

public class VelocityComponent implements Component {
	BasicVector motion;
}