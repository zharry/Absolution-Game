package GameObject;

import java.awt.Graphics;

import Handler.GameObjectAssets;
import Handler.GameObjectRegistry;

public class Tile extends GameObject {

	private static final long serialVersionUID = 8584770631071877315L;
	private int type, variation;

	private int col2Length, col2Width, col2XOffset, col2YOffset;
	private boolean no2Col;

	public Tile(int x, int y, int type, int variation) {
		super(x, y, 0, 0, 0, 0);
		this.x = x;
		this.y = y;
		this.type = type;
		this.variation = variation;

		noCol = false;
		no2Col = true;
		colLength = 32;
		colWidth = 32;
		colXOffset = 0;
		colYOffset = 0;
		col2Length = 0;
		col2Width = 0;
		col2XOffset = 0;
		col2YOffset = 0;

		switch (type) {
		// Full Sprite ColBox
		case GameObjectRegistry.TILE_BASE:
		case GameObjectRegistry.TILE_DOOR:
		case GameObjectRegistry.TILE_SEWER:
		case GameObjectRegistry.TILE_WALL:
			break;

		// No ColBox
		case GameObjectRegistry.TILE_FLOOR:
		case GameObjectRegistry.TILE_STAIRS:
			noCol = true;
			break;

		case GameObjectRegistry.TILE_BORDER:
			switch (variation) {
			// Border-L, Border-L-Alt1, Border-L-Alt2
			case 0:
			case 1:
			case 2:
				colWidth = 6;
				break;
			// Border-T, Border-T-Alt1, Border-T-Alt2
			case 3:
			case 4:
			case 5:
				colLength = 6;
				break;
			// Border-R, Border-R-Alt1, Border-R-Alt2
			case 6:
			case 7:
			case 8:
				colWidth = 6;
				colXOffset = 32 - 6;
				break;
			// Border-B, Border-B-Alt1, Border-B-Alt2
			case 9:
			case 10:
			case 11:
				colLength = 6;
				colYOffset = 32 - 6;
				break;
			// Border-LT, Border-LT-Alt1
			case 12:
			case 13:
				no2Col = false;
				colWidth = 6;
				col2Width = 32 - 6;
				col2Length = 6;
				col2XOffset = 6;
				break;
			// Border-LT-Alt2
			case 14:
				colWidth = 6;
				colLength = 6;
				break;
			// Border-TR, Border-TR-Alt
			case 15:
			case 16:
				no2Col = false;
				colLength = 6;
				col2Width = 6;
				col2Length = 32 - 6;
				col2XOffset = 32 - 6;
				col2YOffset = 6;
				break;
			// Border-TR-Alt2
			case 17:
				colWidth = 6;
				colLength = 6;
				colXOffset = 32 - 6;
				break;
			// Border-RB, Border-RB-Alt1
			case 18:
			case 19:
				no2Col = false;
				colWidth = 6;
				colXOffset = 32 - 6;
				col2Width = 32 - 6;
				col2Length = 6;
				col2YOffset = 32 - 6;
				break;
			// Border-RB-Alt2
			case 20:
				colWidth = 6;
				colLength = 6;
				colXOffset = 32 - 6;
				colYOffset = 32 - 6;
				break;
			// Border-LB, Border-LB-Alt1
			case 21:
			case 22:
				no2Col = false;
				colWidth = 6;
				col2Length = 6;
				col2YOffset = 32 - 6;
				break;
			// Border-LB-Alt2
			case 23:
				colWidth = 6;
				colLength = 6;
				colYOffset = 32 - 6;
				break;
			// Border-LR
			case 24:
				no2Col = false;
				colWidth = 6;
				col2Width = 6;
				col2XOffset = 32 - 6;
				break;
			// Border-TB
			case 25:
				no2Col = false;
				colLength = 6;
				col2Length = 6;
				col2YOffset = 32 - 6;
				break;
			// Border-LTR
			case 26:
				colLength = 16;
				break;
			// Border-TRB
			case 27:
				colWidth = 16;
				colXOffset = 32 - 16;
				break;
			// Border-LRB
			case 28:
				colLength = 16;
				colYOffset = 32 - 16;
				break;
			// Border-LTB
			case 29:
				colWidth = 16;
				break;
			// Border-LTRB
			case 30:
				noCol = true;
				break;
			}
		case GameObjectRegistry.TILE_BORDERWATER:
		case GameObjectRegistry.TILE_WATERFALL:

		case GameObjectRegistry.TILE_WATER:
		}
	}

	public void incVar() {
		variation++;
	}

	public void decVar() {
		variation--;
	}

	@Override
	public boolean isCollide(GameObject other) {
		if (noCol)
			return false;
		if ((this.x < other.x + other.colLength && this.x + this.colLength > other.x
				&& this.y < other.y + other.colWidth && this.y + this.colWidth > other.y))
			return true;
		return false;
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
