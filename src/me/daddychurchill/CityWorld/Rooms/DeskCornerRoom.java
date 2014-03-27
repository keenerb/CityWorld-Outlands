package me.daddychurchill.CityWorld.Rooms;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Support.Direction.Facing;
import me.daddychurchill.CityWorld.Support.Direction.Stair;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class DeskCornerRoom extends DeskRoom {

	public DeskCornerRoom() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawFixture(WorldGenerator generator, RealChunk chunk, Odds odds, int floor, int x,
			int y, int z, int width, int height, int depth,
			Facing sideWithWall, Material materialWall, Material materialGlass) {
		switch (sideWithWall) {
		case NORTH:
			chunk.setTable(x, x + 3, y, z, z + 1, Material.WOOD_PLATE);
			chunk.setBlocks(x, x + 1, y, z + 1, z + 3, Material.WOOD);
			chunk.setBlocks(x, x + 1, y + 1, z + 1, z + 3, Material.WOOD_PLATE);
			chunk.setStair(x + 1, y, z + 1, Material.WOOD_STAIRS, Stair.SOUTH);
			break;
		case SOUTH:
			chunk.setTable(x, x + 3, y, z + 2, z + 3, Material.WOOD_PLATE);
			chunk.setBlocks(x + 2, x + 3, y, z, z + 2, Material.WOOD);
			chunk.setBlocks(x + 2, x + 3, y + 1, z, z + 2, Material.WOOD_PLATE);
			chunk.setStair(x + 1, y, z + 1, Material.WOOD_STAIRS, Stair.NORTH);
			break;
		case WEST:
			chunk.setTable(x, x + 1, y, z, z + 3, Material.WOOD_PLATE);
			chunk.setBlocks(x + 1, x + 3, y, z + 2, z + 3, Material.WOOD);
			chunk.setBlocks(x + 1, x + 3, y + 1, z + 2, z + 3, Material.WOOD_PLATE);
			chunk.setStair(x + 1, y, z + 1, Material.WOOD_STAIRS, Stair.EAST);
			break;
		case EAST:
			chunk.setTable(x + 2, x + 3, y, z, z + 3, Material.WOOD_PLATE);
			chunk.setBlocks(x, x + 2, y, z, z + 1, Material.WOOD);
			chunk.setBlocks(x, x + 2, y + 1, z, z + 1, Material.WOOD_PLATE);
			chunk.setStair(x + 1, y, z + 1, Material.WOOD_STAIRS, Stair.WEST);
			break;
		}
	}

}
