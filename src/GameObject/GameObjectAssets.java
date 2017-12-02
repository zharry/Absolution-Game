package GameObject;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Handler.Helper;

public class GameObjectAssets {

	public static final String ASSETSDIR = "Assets/";
	public static final String TILESDIR = ASSETSDIR + "Tiles/";

	// Game Assets
	public static BufferedImage sprPlayer;

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
	public static BufferedImage[] water = new BufferedImage[16];

	public static void initSprites() {
		// Load Sprites
		try {
			// Player
			sprPlayer = ImageIO.read(new File(ASSETSDIR + "Player.png"));

			// Base
			int i = 0;
			base[i++] = ImageIO.read(new File(TILESDIR + "Base.png"));
			base[i++] = ImageIO.read(new File(TILESDIR + "Base-Ext.png"));

			// Border
			i = 0;
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-L.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-L-Alt1.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-L-Alt2.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-T.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-T-Alt1.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-T-Alt2.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-R.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-R-Alt1.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-R-Alt2.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-B.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-B-Alt1.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-B-Alt2.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-LT.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-LT-Alt1.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-LT-Alt2.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-TR.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-TR-Alt1.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-TR-Alt2.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-RB.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-RB-Alt1.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-RB-Alt2.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-LB.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-LB-Alt1.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-LB-Alt2.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-LR.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-TB.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-LTR.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-TRB.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-LRB.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-LTB.png"));
			border[i++] = ImageIO.read(new File(TILESDIR + "Border-LTRB.png"));

			// Water + Border
			i = 0;
			borderWater[i++] = ImageIO.read(new File(TILESDIR + "Border-Water-L.png"));
			borderWater[i++] = ImageIO.read(new File(TILESDIR + "Border-Water-T.png"));
			borderWater[i++] = ImageIO.read(new File(TILESDIR + "Border-Water-R.png"));
			borderWater[i++] = ImageIO.read(new File(TILESDIR + "Border-Water-B.png"));
			borderWater[i++] = ImageIO.read(new File(TILESDIR + "Border-Water-LT.png"));
			borderWater[i++] = ImageIO.read(new File(TILESDIR + "Border-Water-LT-Alt1.png"));
			borderWater[i++] = ImageIO.read(new File(TILESDIR + "Border-Water-LT-Alt2.png"));
			borderWater[i++] = ImageIO.read(new File(TILESDIR + "Border-Water-TR.png"));
			borderWater[i++] = ImageIO.read(new File(TILESDIR + "Border-Water-TR-Alt1.png"));
			borderWater[i++] = ImageIO.read(new File(TILESDIR + "Border-Water-TR-Alt2.png"));
			borderWater[i++] = ImageIO.read(new File(TILESDIR + "Border-Water-RB.png"));
			borderWater[i++] = ImageIO.read(new File(TILESDIR + "Border-Water-RB-Alt1.png"));
			borderWater[i++] = ImageIO.read(new File(TILESDIR + "Border-Water-RB-Alt2.png"));
			borderWater[i++] = ImageIO.read(new File(TILESDIR + "Border-Water-LB.png"));
			borderWater[i++] = ImageIO.read(new File(TILESDIR + "Border-Water-LB-Alt1.png"));
			borderWater[i++] = ImageIO.read(new File(TILESDIR + "Border-Water-LB-Alt2.png"));
			borderWater[i++] = ImageIO.read(new File(TILESDIR + "Border-Water-LR.png"));
			borderWater[i++] = ImageIO.read(new File(TILESDIR + "Border-Water-TB.png"));
			borderWater[i++] = ImageIO.read(new File(TILESDIR + "Border-Water-LTR.png"));
			borderWater[i++] = ImageIO.read(new File(TILESDIR + "Border-Water-TRB.png"));
			borderWater[i++] = ImageIO.read(new File(TILESDIR + "Border-Water-LRB.png"));
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
			floor[i++] = ImageIO.read(new File(TILESDIR + "Floor-Stripe-Alt.png"));
			floor[i++] = ImageIO.read(new File(TILESDIR + "Floor-Stripe-Alt.png"));

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
			water[i++] = ImageIO.read(new File(TILESDIR + "Water.png"));
			water[i++] = ImageIO.read(new File(TILESDIR + "Water-Border-LT.png"));
			water[i++] = ImageIO.read(new File(TILESDIR + "Water-Border-TR.png"));
			water[i++] = ImageIO.read(new File(TILESDIR + "Water-Border-RB.png"));
			water[i++] = ImageIO.read(new File(TILESDIR + "Water-Border-LB.png"));
			water[i++] = ImageIO.read(new File(TILESDIR + "Water-Border-LR.png"));
			water[i++] = ImageIO.read(new File(TILESDIR + "Water-Border-TB.png"));
			water[i++] = ImageIO.read(new File(TILESDIR + "Water-Border-LTR.png"));
			water[i++] = ImageIO.read(new File(TILESDIR + "Water-Border-TRB.png"));
			water[i++] = ImageIO.read(new File(TILESDIR + "Water-Border-LRB.png"));
			water[i++] = ImageIO.read(new File(TILESDIR + "Water-Border-LTB.png"));
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
			img = new BufferedImage[1];
			img[0] = sprPlayer;
			maxSprites = 1;
			break;
		case GameObjectRegistry.TILE_BASE:
			img = GameObjectAssets.base;
			maxSprites = GameObjectAssets.base.length;
			break;
		case GameObjectRegistry.TILE_BORDER:
			img = GameObjectAssets.border;
			maxSprites = GameObjectAssets.border.length;
			break;
		case GameObjectRegistry.TILE_BORDERWATER:
			img = GameObjectAssets.borderWater;
			maxSprites = GameObjectAssets.borderWater.length;
			break;
		case GameObjectRegistry.TILE_DOOR:
			img = GameObjectAssets.door;
			maxSprites = GameObjectAssets.door.length;
			break;
		case GameObjectRegistry.TILE_SEWER:
			img = GameObjectAssets.sewer;
			maxSprites = GameObjectAssets.sewer.length;
			break;
		case GameObjectRegistry.TILE_WATERFALL:
			img = GameObjectAssets.waterfall;
			maxSprites = GameObjectAssets.waterfall.length;
			break;
		case GameObjectRegistry.TILE_STAIRS:
			img = GameObjectAssets.stairs;
			maxSprites = GameObjectAssets.stairs.length;
			break;
		case GameObjectRegistry.TILE_WALL:
			img = GameObjectAssets.wall;
			maxSprites = GameObjectAssets.wall.length;
			break;
		case GameObjectRegistry.TILE_WATER:
			img = GameObjectAssets.water;
			maxSprites = GameObjectAssets.water.length;
			break;
		default:
			img = GameObjectAssets.floor;
			maxSprites = GameObjectAssets.floor.length;
		}

		return img[Math.floorMod(variation, maxSprites)];
	}

}
