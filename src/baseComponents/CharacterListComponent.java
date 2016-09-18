package baseComponents;

import java.util.*;

import com.badlogic.ashley.core.*;

public class CharacterListComponent implements Component {
	public Map<Integer, Entity> m = Collections.synchronizedMap(new HashMap<Integer, Entity>(32));
}