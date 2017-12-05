package Handler;

import java.awt.Graphics;
import java.util.ArrayList;

import Game.Map;
import GameObject.GameObject;

public class GameHandler {

	private ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
	private ArrayList<Map> maps = new ArrayList<Map>();

	public GameHandler() {
	}

	public ArrayList<GameObject> gameObjects() {
		return this.gameObjects;
	}

	public void importFromMap(Map m, boolean replace) {
		if (replace)
			gameObjects = m.gameObjects();
		else
			gameObjects.addAll(m.gameObjects());
	}
	
	public void render(Graphics g) {
		for (GameObject object : gameObjects)
			object.render(g);
	}

	public void tick() {
		for (GameObject go : gameObjects)
			go.tick();
	}

	public ArrayList<GameObject> checkCollisionWith(GameObject obj) {
		ArrayList<GameObject> inCollision = new ArrayList<GameObject>();
		for (GameObject go : gameObjects)
			if (obj.isCollide(go))
				inCollision.add(go);
		return inCollision;
	}

	public ArrayList<GameObject> checkSprOverlap(GameObject obj) {
		ArrayList<GameObject> inCollision = new ArrayList<GameObject>();
		for (GameObject go : gameObjects)
			if (obj.isSprCollide(go))
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
