package baseComponents;

import java.util.*;

import com.badlogic.ashley.core.*;

public class CharacterListComponent implements Component {
	public Map<Integer, Component> m = Collections.synchronizedMap(new HashMap<Integer, Component>(32));
}