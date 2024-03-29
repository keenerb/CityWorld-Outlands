package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Rooms.PlatRoom;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealChunk;
import me.daddychurchill.CityWorld.Support.Direction.Facing;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

public abstract class RoomProvider extends Provider {

	protected List<PlatRoom> roomTypes;
	
	public RoomProvider() {
		super();
		roomTypes = new ArrayList<PlatRoom>();
	}
	
	private PlatRoom getRandomRoomGenerator(Odds odds) {
		int index = odds.getRandomInt(roomTypes.size());
		return roomTypes.get(index);
	}

	public void drawFixtures(WorldGenerator generator, RealChunk chunk, 
			Odds odds, int floor, int x, int y, int z, int width,
			int height, int depth, Facing sideWithWall, Material materialWall, Material materialGlass) {
		
		PlatRoom roomGen = getRandomRoomGenerator(odds);
		if (roomGen != null)
			roomGen.drawFixture(generator, chunk, odds, floor, x, y, z, width, height, depth, sideWithWall, materialWall, materialGlass);
	}
}
