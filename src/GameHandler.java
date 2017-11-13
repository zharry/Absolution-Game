import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;

public class GameHandler implements Serializable {

	private static final long serialVersionUID = -1961788191210149136L;
	
	public ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();

	public GameHandler(AbsolutionGame game) {
	}

	public void render(Graphics g) {
		for (GameObject object : gameObjects)
			object.render(g);
	}

	public void tick() {

	}
	
	public ArrayList<GameObject> checkCollisionWith(GameObject obj) {
		ArrayList<GameObject> inCollision = new ArrayList<GameObject>();
		for (GameObject go : gameObjects)
			if (obj.isCollide(go))
				inCollision.add(go);
		return inCollision;
	}

	public void addGameObject(GameObject obj) {
		gameObjects.add(obj);
	}

	public void removeGameObject(GameObject obj) {
		gameObjects.remove(obj);
	}

}
