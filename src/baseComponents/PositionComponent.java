package baseComponents;

import org.la4j.*;
import org.la4j.vector.dense.BasicVector;

import com.badlogic.ashley.core.*;

// x,y,rot
public class PositionComponent implements Component {
	public BasicVector coords;
}