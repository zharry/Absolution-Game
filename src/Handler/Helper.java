package Handler;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JOptionPane;

import Game.AbsolutionGame;
import GameObject.GameObject;

public class Helper {

	private int drawY, incY;
	private GameInfo gameInfo;
	private BuilderHandler builderHandler;
	private GameHandler gameHandler;
	private AbsolutionGame mainRef;

	public Helper(GameInfo gameInfo, AbsolutionGame mainRef) {
		this.gameInfo = gameInfo;
		this.mainRef = mainRef;
		reset();
	}

	public void setBuilderHandler(BuilderHandler builderHandler) {
		this.builderHandler = builderHandler;
	}

	public void setGameHandler(GameHandler gameHandler) {
		this.gameHandler = gameHandler;
	}

	public void reset() {
		drawY = 0;
		incY = 15;
	}

	public void drawDebug(Graphics g) {
		g.setColor(Color.white);
		g.drawString("General Info:", 15, drawY += incY);
		g.drawString("FPS: " + gameInfo.getFPS() + ", TPS: " + gameInfo.getCurTPS(), 15, drawY += incY);
		g.drawString("MouseX: " + gameInfo.getMouseX() + ", MouseY: " + gameInfo.getMouseY(), 15, drawY += incY);
		g.drawString("Mouse Button: " + gameInfo.getMouseButton(), 15, drawY += incY);
		g.drawString("Mouse Dragging: " + gameInfo.getMouseDragging(), 15, drawY += incY);
		g.drawString("", 15, drawY += incY);
	}

	@SuppressWarnings("static-access")
	public void drawDebugColBox(Graphics g) {
		g.setColor(Color.WHITE);
		for (GameObject go : gameHandler.gameObjects())
			if (go.hasCol())
				g.drawRect(go.getPos()[0] + go.getCol()[2] - mainRef.player.getPos()[0] + gameInfo.width / 2,
						go.getPos()[1]  + go.getCol()[3]- mainRef.player.getPos()[1] + gameInfo.height / 2,
						go.getCol()[0], go.getCol()[1]);
	}

	public void drawBuilderDebug(Graphics g) {
		g.setColor(Color.white);
		g.drawString("World Builder Info:", 15, drawY += incY);
		g.drawString("Selected ID: " + builderHandler.selectedID, 15, drawY += incY);
		g.drawString("Grid Locked: " + builderHandler.gridLock, 15, drawY += incY);
		g.drawString("Showing Grid: " + builderHandler.showGrid, 15, drawY += incY);
		if (builderHandler.gridLock)
			g.drawString("X Locked Pos: " + (gameInfo.getMouseX() / 16) * 16 + ", Y Locked Pos: "
					+ (gameInfo.getMouseY() / 16) * 16, 15, drawY += incY);
		g.drawString("Selected Variation: " + builderHandler.selectedVar, 15, drawY += incY);
		g.drawString("Map Difficulty: " + builderHandler.difficulty, 15, drawY += incY);
		g.drawString("", 15, drawY += incY);
	}

	public void drawBuilderGrid(Graphics g) {
		if (builderHandler.gridLock && builderHandler.showGrid) {
			g.setColor(new Color(0x303030));
			for (int i = 0; i < GameInfo.gameWidth; i += 16)
				g.drawLine(i, 0, i, GameInfo.height);
			for (int i = 0; i < GameInfo.height; i += 16)
				g.drawLine(0, i, GameInfo.gameWidth, i);
		}
	}

	public void drawPlayerDebug(Graphics g) {
		g.setColor(Color.white);
		g.drawString("Player Info:", 15, drawY += incY);
		g.drawString("X: " + mainRef.player.getPos()[0] + " Y: " + mainRef.player.getPos()[1], 15, drawY += incY);
		g.drawString("X Vel: " + mainRef.player.getVel()[0] + " Y Vel: " + mainRef.player.getVel()[1], 15,
				drawY += incY);
		g.drawString("Col Width: " + mainRef.player.getCol()[0] + " Col Length: " + mainRef.player.getCol()[1], 15,
				drawY += incY);
		g.drawString("Col X Offset: " + mainRef.player.getCol()[2] + "  Col Y Offset: " + mainRef.player.getCol()[3],
				15, drawY += incY);
		g.drawString("Moveback: " + mainRef.player.moveBack, 15, drawY += incY);
		g.drawString("Up: " + mainRef.player.goUp + " Down: " + mainRef.player.goDown, 15, drawY += incY);
		g.drawString("Left: " + mainRef.player.goLeft + " Right:" + mainRef.player.goRight, 15, drawY += incY);
		g.drawString("Draw Direction: " + mainRef.player.lastDir, 15, drawY += incY);
		g.drawString("Frame Timer: " + mainRef.player.frameTimer + " Animation Frame: " + mainRef.player.curFrame, 15,
				drawY += incY);
		g.drawString("", 15, drawY += incY);
	}

	public static int displayError(String msg) {
		return JOptionPane.showOptionDialog(null, msg, "Error!", JOptionPane.YES_NO_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, new Object[] { "OK" }, "OK");
	}
}
