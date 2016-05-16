package baseComponents;

import java.util.List;

import com.badlogic.ashley.core.*;

public class SpawnerComponent implements Component {
	List<Entity> spawnables;
	long spawnCountdown; //in deciSeconds
}
