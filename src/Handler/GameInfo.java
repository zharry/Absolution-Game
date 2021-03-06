package Handler;

import java.awt.event.MouseEvent;

public class GameInfo {

	public static int height = 640, width = 1040, gameWidth = 1040, builderWidth = 214;

	private final int targetTPS = 120;
	private boolean running, mouseDragging = false, gameOver = false;
	private int fps, fpsProc = 0, curTps, mouseX = 0, mouseY = 0, mouseButton = MouseEvent.NOBUTTON;

	public GameInfo() {
	}

	public void setFPS(int fps) {
		this.fps = fps;
	}

	public int getFPS() {
		return fps;
	}

	public void setFPSProc(int fpsProc) {
		this.fpsProc = fpsProc;
	}

	public int getFPSProc() {
		return fpsProc;
	}

	public void setCurTPS(int curTps) {
		this.curTps = curTps;
	}

	public int getCurTPS() {
		return curTps;
	}

	public int getTargetTPS() {
		return targetTPS;
	}

	public void setState(boolean running) {
		this.running = running;
	}

	public boolean getState() {
		return running;
	}

	public void setMouse(int x, int y, int mouseButton, boolean drag) {
		if (x != -1)
			this.mouseX = x;
		if (y != -1)
			this.mouseY = y;
		if (mouseButton != -1)
			this.mouseButton = mouseButton;
		this.mouseDragging = drag;
	}

	public int getMouseX() {
		return mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

	public int getMouseButton() {
		return mouseButton;
	}

	public boolean getMouseDragging() {
		return mouseDragging;
	}

	public void setGameOver() {
		gameOver = true;
	}

	public boolean isGameOver() {
		return gameOver;
	}

}
