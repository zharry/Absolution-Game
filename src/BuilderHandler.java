import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class BuilderHandler {

	private AbsolutionGame game;
	private GameHandler gameHandler;

	public BuilderHandler(AbsolutionGame game, GameHandler gameHandler) {
		this.game = game;
		this.gameHandler = gameHandler;
	}

	public void render(Graphics g) {
		// Render New GameObject Placement
		Graphics2D g2d = (Graphics2D) g;
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		BufferedImage toDraw = null;
		switch (game.selectedID) {
		case GameObject.PLAYER:
			toDraw = AbsolutionGame.sprPlayer[game.rotateDegs];
			break;
		}
		g.drawImage(toDraw, game.gridLockX, game.gridLockY, null);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
	}

	public void tick() {

	}

	public void addNewGameObject() {
		switch (game.selectedID) {
		case GameObject.PLAYER:
			gameHandler.addGameObject(new Player(game.gridLockX, game.gridLockY, game.rotateDegs, 32, 32));
		}
	}

	public void dragAddNewGameObject() {
		// TODO Change to template based object creation
		ArrayList<GameObject> inCol = gameHandler
				.checkCollisionWith(new Player(game.gridLockX, game.gridLockY, game.rotateDegs, 32, 32));
		if (inCol.size() == 0)
			addNewGameObject();
	}

	public void removeGameObject() {
		// TODO Change to point-rect collision
		ArrayList<GameObject> inCol = gameHandler
				.checkCollisionWith(new Player(game.gridLockX, game.gridLockY, game.rotateDegs, 1, 1));
		for (GameObject obj : inCol)
			gameHandler.removeGameObject(obj);
	}

}
