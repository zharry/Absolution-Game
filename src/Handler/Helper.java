package Handler;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JOptionPane;

import Game.AbsolutionGame;
import GameObject.GameObject;

public class Helper {

	public static final int incY = 15;
	public int drawY;
	
	private GameInfo gameInfo;
	private GameHandler gameHandler;
	private AbsolutionGame mainRef;

	public int getCurrentY() {
		return drawY;
	}

	public void setDrawY(int y) {
		drawY = y;
	}

	public Helper(GameInfo gameInfo, AbsolutionGame mainRef) {
		this.gameInfo = gameInfo;
		this.mainRef = mainRef;
		reset();
	}

	public void setGameHandler(GameHandler gameHandler) {
		this.gameHandler = gameHandler;
	}

	public void reset() {
		drawY = 0;
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
				g.drawRect(go.getPos()[0] + go.getCol()[2] - mainRef.player().getPos()[0] + gameInfo.width / 2,
						go.getPos()[1] + go.getCol()[3] - mainRef.player().getPos()[1] + gameInfo.height / 2,
						go.getCol()[0], go.getCol()[1]);
	}

	public void drawPlayerDebug(Graphics g) {
		g.setColor(Color.white);
		g.drawString("Player Info:", 15, drawY += incY);
		g.drawString("X: " + mainRef.player().getPos()[0] + " Y: " + mainRef.player().getPos()[1], 15, drawY += incY);
		g.drawString("X Vel: " + mainRef.player().getVel()[0] + " Y Vel: " + mainRef.player().getVel()[1], 15,
				drawY += incY);
		g.drawString("Col Width: " + mainRef.player().getCol()[0] + " Col Length: " + mainRef.player().getCol()[1], 15,
				drawY += incY);
		g.drawString(
				"Col X Offset: " + mainRef.player().getCol()[2] + "  Col Y Offset: " + mainRef.player().getCol()[3], 15,
				drawY += incY);
		g.drawString("In collision: " + mainRef.player().getMoveBack(), 15, drawY += incY);
		g.drawString("Draw Direction: " + mainRef.player().getDrawDebug()[0], 15, drawY += incY);
		g.drawString("Frame Timer: " + mainRef.player().getDrawDebug()[1] + " Animation Frame: "
				+ mainRef.player().getDrawDebug()[2], 15, drawY += incY);
		g.drawString("", 15, drawY += incY);
	}

	public static int displayError(String msg) {
		return JOptionPane.showOptionDialog(null, msg, "Error!", JOptionPane.YES_NO_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, new Object[] { "OK" }, "OK");
	}
}
