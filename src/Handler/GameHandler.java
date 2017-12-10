package Handler;

import java.awt.Graphics;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Random;

import Game.AbsolutionGame;
import Game.Map;
import GameObject.CheckPoint;
import GameObject.GameObject;
import GameObject.Player;

public class GameHandler {

	Random r = new Random();
	AbsolutionGame mainRef;

	private int[] spawn;

	private ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
	private ArrayList<String> easyMaps = new ArrayList<String>();
	private ArrayList<String> medMaps = new ArrayList<String>();
	private ArrayList<String> hardMaps = new ArrayList<String>();

	private CheckPoint lastEnd;

	int stage, displayStage;

	public GameHandler(AbsolutionGame main) {
		this.mainRef = main;
		stage = 0;
	}

	public ArrayList<GameObject> gameObjects() {
		return this.gameObjects;
	}

	public void replaceMap(Map m) {
		gameObjects = m.gameObjects();
	}

	public void addMap(Map m) {
		String mapName = m.getName();
		switch (m.difficulty()) {
		case 0:
			easyMaps.add(mapName);
			break;
		case 1:
			medMaps.add(mapName);
			break;
		default:
			hardMaps.add(mapName);
		}
	}

	public void loadNextMap() {
		stage++;
		if (stage == 41)
			stage = 21;

		// For Stage 1-5
		int easy = 100, med = 0;
		if (stage >= 6 && stage <= 10) {
			easy = 75;
			med = 25;
		} else if (stage >= 11 && stage <= 15) {
			easy = 0;
			med = 50;
		} else if (stage >= 16 && stage <= 20) {
			easy = 0;
			med = 25;
		} else if (stage >= 21 && stage <= 25) {
			easy = 75;
			med = 25;
		} else if (stage >= 26 && stage <= 30) {
			easy = 25;
			med = 60;
		} else if (stage >= 31 && stage <= 35) {
			easy = 0;
			med = 25;
		} else if (stage >= 36 && stage <= 40) {
			easy = 0;
			med = 10;
		}

		String mapName = "";
		int easyGen = r.nextInt(100), medGen = r.nextInt(100);
		System.out.println("Easy Gen: (" + easy + ")" + easyGen + " | Med Gen: (" + med + ")" + medGen);
		if (easyGen < easy) {
			mapName = easyMaps.get(r.nextInt(easyMaps.size()));
			System.out.println("Easy Map: " + mapName);
		} else if (medGen < med) {
			mapName = medMaps.get(r.nextInt(medMaps.size()));
			System.out.println("Med Map: " + mapName);
		} else {
			mapName = hardMaps.get(r.nextInt(hardMaps.size()));
			System.out.println("Hard Map: " + mapName);
		}

		// Re-fetch Map File
		FileInputStream fileIn;
		Map map = null;
		try {
			fileIn = new FileInputStream("Maps" + File.separator + mapName);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			map = (Map) in.readObject();
			in.close();
			fileIn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Load map into GameObjects
		if (stage == 1) {
			gameObjects = map.gameObjects();
			lastEnd = map.getPoints().get(1);
			for (GameObject go : map.gameObjects())
				if (go instanceof CheckPoint)
					if (((CheckPoint) go).isStart())
						spawn = go.getPos();
		} else {
			CheckPoint start = map.getPoints().get(0), end = map.getPoints().get(1);
			for (GameObject go : map.gameObjects()) {
				if (!(go instanceof CheckPoint))
					go.setPos(new int[] { go.getPos()[0] - start.getPos()[0] + lastEnd.getPos()[0],
							go.getPos()[1] - start.getPos()[1] + lastEnd.getPos()[1] });
				gameObjects.add(go);
			}
			lastEnd.setPos(new int[] { end.getPos()[0] - start.getPos()[0] + lastEnd.getPos()[0],
					end.getPos()[1] - start.getPos()[1] + lastEnd.getPos()[1] });
		}
	}

	public int[] getSpawn() {
		return spawn;
	}

	public void render(Graphics g) {
		for (GameObject object : gameObjects)
			if (!(object instanceof Player))
				object.render(g);
		mainRef.player.render(g);
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
