package baseComponents;

import java.util.List;

import com.badlogic.ashley.core.*;
import com.badlogic.gdx.utils.Pool.Poolable;

public class PositionComponent implements Component, Poolable {
	public double x, y;

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		x = 0.0;
		y = 0.0;
	}
}
