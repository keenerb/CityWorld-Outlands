package me.daddychurchill.CityWorld.Plugins;

import net.minecraft.server.Material;
import me.daddychurchill.CityWorld.WorldGenerator;

public class OreProvider_SnowDunes extends OreProvider_Normal {

	public OreProvider_SnowDunes(WorldGenerator generator) {
		super(generator);

		fluidId = iceId;
		fluidFluidId = iceId;
		surfaceId = snowBlockId;
		subsurfaceId = stoneId;
	}

	@Override
	public String getCollectionName() {
		return "SnowDunes";
	}

}
