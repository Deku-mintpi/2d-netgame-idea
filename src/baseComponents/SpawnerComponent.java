package baseComponents;

import java.util.List;

import com.badlogic.ashley.core.*;

public class SpawnerComponent implements Component {
	public List<List<Component>> spawnables;
	public long spawnCountdown; //in deciSeconds
	public boolean isConstant;
}
