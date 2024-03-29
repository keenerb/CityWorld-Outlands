package me.daddychurchill.CityWorld.Rooms;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealChunk;
import me.daddychurchill.CityWorld.Support.Direction.Facing;

public class LoungeTableRoom extends LoungeRoom {

	public LoungeTableRoom() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void drawFixture(WorldGenerator generator, RealChunk chunk, Odds odds, int floor, int x,
			int y, int z, int width, int height, int depth,
			Facing sideWithWall, Material materialWall, Material materialGlass) {
		int offset;
		switch (sideWithWall) {
		case NORTH:
		case SOUTH:
			offset = odds.getRandomInt(width);
			chunk.setTable(x + offset, x + 1 + offset, y, z, z + depth, Material.WOOD_PLATE);
			break;
		case WEST:
		case EAST:
			offset = odds.getRandomInt(depth);
			chunk.setTable(x, x + width, y, z + offset, z + 1 + offset, Material.WOOD_PLATE);
			break;
		}
	}

}
