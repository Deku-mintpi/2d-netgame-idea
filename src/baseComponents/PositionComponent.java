package baseComponents;

import com.badlogic.ashley.core.*;

import types.Complex;

// x,y,rot
public class PositionComponent implements Component {
	public Complex coords;
	public double heading;
}