package Handler;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GameObjectAssets {

	public static final String ASSETSDIR = "Assets/";
	public static final String TILESDIR = ASSETSDIR + "Tiles/";
	public static final String PLAYERDIR = ASSETSDIR + "Player/";
	public static final String UIDIR = ASSETSDIR + "UI/";

	// Game Assets
	public static BufferedImage[][] sprPlayer = new BufferedImage[4][4];

	// UI
	public static BufferedImage skillBar, skillDisabled;
	public static BufferedImage[] settings = new BufferedImage[2];
	public static BufferedImage pointStart, pointEnd, vertLine, horLine;

	// World
	public static BufferedImage[] base = new BufferedImage[2];
	public static BufferedImage[] border = new BufferedImage[31];
	public static BufferedImage[] borderWater = new BufferedImage[22];
	public static BufferedImage[] door = new BufferedImage[3];
	public static BufferedImage[] sewer = new BufferedImage[3];
	public static BufferedImage[] waterfall = new BufferedImage[2];
	public static BufferedImage[] floor = new BufferedImage[5];
	public static BufferedImage[] stairs = new BufferedImage[4];
	public static BufferedImage[] wall = new BufferedImage[3];
	public static BufferedImage[] water = new BufferedImage[12];

	public static void initSprites() {
		// Load Sprites
		try {
			// Player
			int i = 0;
			sprPlayer[0][i++] = ImageIO.read(new File(PLAYERDIR + "B1.png"));
			sprPlayer[0][i++] = ImageIO.read(new File(PLAYERDIR + "B2.png"));
			sprPlayer[0][i++] = ImageIO.read(new File(PLAYERDIR + "B3.png"));
			sprPlayer[0][i++] = ImageIO.read(new File(PLAYERDIR + "B4.png"));
			i = 0;
			sprPlayer[1][i++] = ImageIO.read(new File(PLAYERDIR + "R1.png"));
			sprPlayer[1][i++] = ImageIO.read(new File(PLAYERDIR + "R2.png"));
			sprPlayer[1][i++] = ImageIO.read(new File(PLAYERDIR + "R3.png"));
			sprPlayer[1][i++] = ImageIO.read(new File(PLAYERDIR + "R4.png"));
			i = 0;
			sprPlayer[2][i++] = ImageIO.read(new File(PLAYERDIR + "F1.png"));
			sprPlayer[2][i++] = ImageIO.read(new File(PLAYERDIR + "F2.png"));
			sprPlayer[2][i++] = ImageIO.read(new File(PLAYERDIR + "F3.png"));
			sprPlayer[2][i++] = ImageIO.read(new File(PLAYERDIR + "F4.png"));
			i = 0;
			sprPlayer[3][i++] = ImageIO.read(new File(PLAYERDIR + "L1.png"));
			sprPlayer[3][i++] = ImageIO.read(new File(PLAYERDIR + "L2.png"));
			sprPlayer[3][i++] = ImageIO.read(new File(PLAYERDIR + "L3.png"));
			sprPlayer[3][i++] = ImageIO.read(new File(PLAYERDIR + "L4.png"));

			// Skillbar
			skillBar = ImageIO.read(new File(UIDIR + "Skillbar-Filled.png"));
			skillDisabled = ImageIO.read(new File(UIDIR + "Disabled-Skill.png"));

			// Settings
			i = 0;
			settings[i++] = ImageIO.read(new File(UIDIR + "Settings.png"));
			settings[i++] = ImageIO.read(new File(UIDIR + "Settings-Clicked.png"));

			// Checkpoints
			pointStart = ImageIO.read(new File(UIDIR + "Point_Start.png"));
			pointEnd = ImageIO.read(new File(UIDIR + "Point_End.png"));

			// Line Cols
			vertLine = ImageIO.read(new File(UIDIR + "VertLine.png"));
			horLine = ImageIO.read(new File(UIDIR + "HorLine.png"));

			// Base
			i = 0;
			base[i++] = ImageIO.read(new File(TILESDIR + "Base.png"));
			base[i++] = ImageIO.read(new File(TILESDIR + "Base-Ext.png"));

			// Border
			i = 0;
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-L.png")); // 0
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-L-Alt1.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-L-Alt2.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-T.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-T-Alt1.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-T-Alt2.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-R.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-R-Alt1.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-R-Alt2.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-B.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-B-Alt1.png")); // 10
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-B-Alt2.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-LT.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-LT-Alt1.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-LT-Alt2.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-TR.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-TR-Alt1.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-TR-Alt2.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-RB.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-RB-Alt1.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-RB-Alt2.png")); // 20
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-LB.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-LB-Alt1.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-LB-Alt2.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-LR.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-TB.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-LTR.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-TRB.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-LRB.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-LTB.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-LTRB.png")); // 30

			// Water + Border
			i = 0;
			borderWater[i++] = ImageIO.read(new File(TILESDIR + "Border-Water-L.png")); // 0
			borderWater[i++] = ImageIO.read(new File(TILESDIR + "Border-Water-T.png"));
			borderWater[i++] = ImageIO.read(new File(TILESDIR + "Border-Water-R.png"));
			borderWater[i++] = ImageIO.read(new File(TILESDIR + "Border-Water-B.png"));
			borderWater[i++] = ImageIO.read(new File(TILESDIR + "Border-Water-LT.png"));
			borderWater[i++] = ImageIO.read(new File(TILESDIR + "Border-Water-LT-Alt1.png"));
			borderWater[i++] = ImageIO.read(new File(TILESDIR + "Border-Water-LT-Alt2.png"));
			borderWater[i++] = ImageIO.read(new File(TILESDIR + "Border-Water-TR.png"));
			borderWater[i++] = ImageIO.read(new File(TILESDIR + "Border-Water-TR-Alt1.png"));
			borderWater[i++] = ImageIO.read(new File(TILESDIR + "Border-Water-TR-Alt2.png"));
			borderWater[i++] = ImageIO.read(new File(TILESDIR + "Border-Water-RB.png")); // 10
			borderWater[i++] = ImageIO.read(new File(TILESDIR + "Border-Water-RB-Alt1.png"));
			borderWater[i++] = ImageIO.read(new File(TILESDIR + "Border-Water-RB-Alt2.png"));
			borderWater[i++] = ImageIO.read(new File(TILESDIR + "Border-Water-LB.png"));
			borderWater[i++] = ImageIO.read(new File(TILESDIR + "Border-Water-LB-Alt1.png"));
			borderWater[i++] = ImageIO.read(new File(TILESDIR + "Border-Water-LB-Alt2.png"));
			borderWater[i++] = ImageIO.read(new File(TILESDIR + "Border-Water-LR.png"));
			borderWater[i++] = ImageIO.read(new File(TILESDIR + "Border-Water-TB.png"));
			borderWater[i++] = ImageIO.read(new File(TILESDIR + "Border-Water-LTR.png"));
			borderWater[i++] = ImageIO.read(new File(TILESDIR + "Border-Water-TRB.png"));
			borderWater[i++] = ImageIO.read(new File(TILESDIR + "Border-Water-LRB.png")); // 20
			borderWater[i++] = ImageIO.read(new File(TILESDIR + "Border-Water-LTB.png"));

			// Door
			i = 0;
			door[i++] = ImageIO.read(new File(TILESDIR + "Door.png"));
			door[i++] = ImageIO.read(new File(TILESDIR + "Door-Alt1.png"));
			door[i++] = ImageIO.read(new File(TILESDIR + "Door-Alt2.png"));

			// Sewer
			i = 0;
			sewer[i++] = ImageIO.read(new File(TILESDIR + "Sewer.png"));
			sewer[i++] = ImageIO.read(new File(TILESDIR + "Sewer-Alt1.png"));
			sewer[i++] = ImageIO.read(new File(TILESDIR + "Sewer-Alt2.png"));

			// Waterfall
			i = 0;
			waterfall[i++] = ImageIO.read(new File(TILESDIR + "Waterfall.png"));
			waterfall[i++] = ImageIO.read(new File(TILESDIR + "Waterfall-Alt.png"));

			// Floor
			i = 0;
			floor[i++] = ImageIO.read(new File(TILESDIR + "Floor.png"));
			floor[i++] = ImageIO.read(new File(TILESDIR + "Floor-Alt.png"));
			floor[i++] = ImageIO.read(new File(TILESDIR + "Floor-Stripe.png"));
			floor[i++] = ImageIO.read(new File(TILESDIR + "Floor-Stripe-Alt1.png"));
			floor[i++] = ImageIO.read(new File(TILESDIR + "Floor-Stripe-Alt2.png"));

			// Stairs
			i = 0;
			stairs[i++] = ImageIO.read(new File(TILESDIR + "Stairs.png"));
			stairs[i++] = ImageIO.read(new File(TILESDIR + "Stairs-Alt1.png"));
			stairs[i++] = ImageIO.read(new File(TILESDIR + "Stairs-Alt2.png"));
			stairs[i++] = ImageIO.read(new File(TILESDIR + "Stairs-Alt3.png"));

			// Wall
			i = 0;
			wall[i++] = ImageIO.read(new File(TILESDIR + "Wall.png"));
			wall[i++] = ImageIO.read(new File(TILESDIR + "Wall-Alt1.png"));
			wall[i++] = ImageIO.read(new File(TILESDIR + "Wall-Alt2.png"));

			// Water
			i = 0;
			water[i++] = ImageIO.read(new File(TILESDIR + "Water.png")); // 0
			water[i++] = ImageIO.read(new File(TILESDIR + "Water-Border-LT.png"));
			water[i++] = ImageIO.read(new File(TILESDIR + "Water-Border-TR.png"));
			water[i++] = ImageIO.read(new File(TILESDIR + "Water-Border-RB.png"));
			water[i++] = ImageIO.read(new File(TILESDIR + "Water-Border-LB.png"));
			water[i++] = ImageIO.read(new File(TILESDIR + "Water-Border-LR.png"));
			water[i++] = ImageIO.read(new File(TILESDIR + "Water-Border-TB.png"));
			water[i++] = ImageIO.read(new File(TILESDIR + "Water-Border-LTR.png"));
			water[i++] = ImageIO.read(new File(TILESDIR + "Water-Border-TRB.png"));
			water[i++] = ImageIO.read(new File(TILESDIR + "Water-Border-LRB.png"));
			water[i++] = ImageIO.read(new File(TILESDIR + "Water-Border-LTB.png")); // 10
			water[i++] = ImageIO.read(new File(TILESDIR + "Water-Border-LTRB.png"));

		} catch (IOException e) {
			e.printStackTrace();
			Helper.displayError("Failed to load Game Assets, please re-download the game and try again!");
			System.exit(-1);
		}

	}

	public static BufferedImage idToAsset(int id, int variation) {
		BufferedImage[] img;
		int maxSprites;

		switch (id) {
		case GameObjectRegistry.PLAYER:
			img = sprPlayer[0];
			maxSprites = 1;
			break;
		case GameObjectRegistry.TILE_BASE:
			img = base;
			maxSprites = base.length;
			break;
		case GameObjectRegistry.TILE_BORDER:
			img = border;
			maxSprites = border.length;
			break;
		case GameObjectRegistry.TILE_BORDERWATER:
			img = borderWater;
			maxSprites = borderWater.length;
			break;
		case GameObjectRegistry.TILE_DOOR:
			img = door;
			maxSprites = door.length;
			break;
		case GameObjectRegistry.TILE_SEWER:
			img = sewer;
			maxSprites = sewer.length;
			break;
		case GameObjectRegistry.TILE_WATERFALL:
			img = waterfall;
			maxSprites = waterfall.length;
			break;
		case GameObjectRegistry.TILE_STAIRS:
			img = stairs;
			maxSprites = stairs.length;
			break;
		case GameObjectRegistry.TILE_WALL:
			img = wall;
			maxSprites = wall.length;
			break;
		case GameObjectRegistry.TILE_WATER:
			img = water;
			maxSprites = water.length;
			break;
		case GameObjectRegistry.POINT_START:
			return GameObjectAssets.pointStart;
		case GameObjectRegistry.POINT_END:
			return GameObjectAssets.pointEnd;
		case GameObjectRegistry.COL_HOR:
			return GameObjectAssets.horLine;
		case GameObjectRegistry.COL_VERT:
			return GameObjectAssets.vertLine;
		default:
			img = floor;
			maxSprites = floor.length;
		}
		return img[Math.floorMod(variation, maxSprites)];
	}

}
