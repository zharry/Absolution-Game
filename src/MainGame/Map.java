package MainGame;

import java.io.Serializable;
import java.util.ArrayList;

import GameObject.CheckPoint;
import GameObject.GameObject;
import Handler.GameHandler;

public class Map implements Serializable {

	private static final long serialVersionUID = -1242754288783431337L;

	private ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
	private int difficulty;

	private CheckPoint start, end;

	public Map(int diff) {
		difficulty = diff;
	}

	public ArrayList<GameObject> gameObjects() {
		return this.gameObjects;
	}

	public void importFromHandler(GameHandler h) throws Exception {
		gameObjects = h.gameObjects();

		// Check to see if GameHandler has exactly 1 Start and End
		int foundStart = 0, foundEnd = 0;
		for (GameObject go : gameObjects) {
			if (go instanceof CheckPoint) {
				if (((CheckPoint) go).isStart())
					foundStart++;
				else
					foundEnd++;
			}
		}
		if (!(foundStart == 1 && foundEnd == 1))
			throw new Exception();
	}

	public int difficulty() {
		// 0 - Easy
		// 1 - Medium
		// 2 - Hard
		return difficulty;
	}

}
