package me.daddychurchill.CityWorld.Rooms;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Support.Direction.Facing;
import me.daddychurchill.CityWorld.Support.Direction.Stair;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class DeskInternsRoom extends DeskRoom {

	public DeskInternsRoom() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawFixture(WorldGenerator generator, RealChunk chunk, Odds odds, int floor, int x,
			int y, int z, int width, int height, int depth,
			Facing sideWithWall, Material materialWall, Material materialGlass) {
		switch (sideWithWall) {
		case NORTH:
			chunk.setTable(x, y, z, Material.WOOD_PLATE);
			chunk.setBlock(x + 1, y, z, Material.WOOD);
			chunk.setTable(x + 2, y, z, Material.WOOD_PLATE);
			chunk.setStair(x, y, z + 1, Material.WOOD_STAIRS, Stair.SOUTH);
			chunk.setStair(x + 2, y, z + 1, Material.WOOD_STAIRS, Stair.SOUTH);
			break;
		case SOUTH:
			chunk.setTable(x, y, z + 2, Material.WOOD_PLATE);
			chunk.setBlock(x + 1, y, z + 2, Material.WOOD);
			chunk.setTable(x + 2, y, z + 2, Material.WOOD_PLATE);
			chunk.setStair(x, y, z + 1, Material.WOOD_STAIRS, Stair.NORTH);
			chunk.setStair(x + 2, y, z + 1, Material.WOOD_STAIRS, Stair.NORTH);
			break;
		case WEST:
			chunk.setTable(x, y, z, Material.WOOD_PLATE);
			chunk.setBlock(x, y, z + 1, Material.WOOD);
			chunk.setTable(x, y, z + 2, Material.WOOD_PLATE);
			chunk.setStair(x + 1, y, z, Material.WOOD_STAIRS, Stair.EAST);
			chunk.setStair(x + 1, y, z + 2, Material.WOOD_STAIRS, Stair.EAST);
			break;
		case EAST:
			chunk.setTable(x + 2, y, z, Material.WOOD_PLATE);
			chunk.setBlock(x + 2, y, z + 1, Material.WOOD);
			chunk.setTable(x + 2, y, z + 2, Material.WOOD_PLATE);
			chunk.setStair(x + 1, y, z, Material.WOOD_STAIRS, Stair.WEST);
			chunk.setStair(x + 1, y, z + 2, Material.WOOD_STAIRS, Stair.WEST);
			break;
		}
	}

}
