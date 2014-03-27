package me.daddychurchill.CityWorld.Rooms;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Support.Direction.Facing;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class DebugRoom extends PlatRoom {

	public DebugRoom() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawFixture(WorldGenerator generator, RealChunk chunk, Odds odds, int floor, int x,
			int y, int z, int width, int height, int depth,
			Facing sideWithWall, Material materialWall, Material materialGlass) {
		chunk.setBlocks(x, x + width, y, y + 1, z, z + depth, materialWall);
		switch (sideWithWall) {
		case NORTH:
			chunk.setBlocks(x, x + width, y + 1, y + height, z, z + 1, Material.AIR);
			break;
		case SOUTH:
			chunk.setBlocks(x, x + width, y + 1, y + height, z + depth - 1, z + depth, Material.AIR);
			break;
		case WEST:
			chunk.setBlocks(x, x + 1, y + 1, y + height, z, z + depth, Material.AIR);
			break;
		case EAST:
			chunk.setBlocks(x + width - 1, x + width, y + 1, y + height, z, z + depth, Material.AIR);
			break;
		}
	}

}
