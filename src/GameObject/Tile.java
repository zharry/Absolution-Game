package GameObject;

import java.awt.Graphics;

import Handler.GameObjectAssets;
import Handler.GameObjectRegistry;

public class Tile extends GameObject {

	private static final long serialVersionUID = 8584770631071877315L;
	private int type, variation;

	public Tile(int x, int y, int type, int variation) {
		super(x, y, 0, 0, 0, 0);
		this.x = x;
		this.y = y;
		this.type = type;
		this.variation = variation;
		if (type == GameObjectRegistry.TILE_WALL) {
			colWidth = 32;
			colLength = 32;
		}
	}

	public void incVar() {
		variation++;
	}

	public void decVar() {
		variation--;
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(GameObjectAssets.idToAsset(type, variation), this.x, this.y, null);
	}

	@Override
	public void tick() {
	}

	@Override
	public void onCollide() {
	}

}
