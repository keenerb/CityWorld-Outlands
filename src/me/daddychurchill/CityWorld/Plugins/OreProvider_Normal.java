package me.daddychurchill.CityWorld.Plugins;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.CachedYs;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class OreProvider_Normal extends OreProvider {

	public OreProvider_Normal(WorldGenerator generator) {
		super(generator);
	}
	
	@Override
	public String getCollectionName() {
		return "Normal";
	}

	/**
	 * Populates the world with ores.
	 *
	 * original authors Nightgunner5, Markus Persson
	 * modified by simplex
	 * wildly modified by daddychurchill
	 */
	
	private static final Material[] ore_types = new Material[] {Material.WATER,
																Material.LAVA,
																Material.GRAVEL,
																Material.COAL_ORE,
																Material.IRON_ORE,
																Material.GOLD_ORE,
																Material.LAPIS_ORE,
																Material.REDSTONE_ORE,
																Material.DIAMOND_ORE,
																Material.EMERALD_ORE}; 
	
	//                                                         WATER   LAVA   GRAV   COAL   IRON   GOLD  LAPIS  REDST   DIAM   EMER  
	private static final int[] ore_iterations = new int[]    {     8,     6,    40,    30,    12,     4,     2,     4,     2,    10};
	private static final int[] ore_amountToDo = new int[]    {     1,     1,    12,     8,     8,     3,     3,    10,     3,     1};
	private static final int[] ore_maxY = new int[]          {   128,    32,   111,   128,    61,    29,    25,    16,    15,    32};
	private static final int[] ore_minY = new int[]          {    32,     2,    40,    16,    10,     8,     8,     6,     2,     2};
	private static final boolean[] ore_upper = new boolean[] {  true, false, false,  true,  true,  true,  true,  true, false, false};
	private static final boolean[] ore_liquid = new boolean[] { true,  true, false, false, false, false, false, false, false, false};
	
	@Override
	public void sprinkleOres(WorldGenerator generator, PlatLot lot, RealChunk chunk, CachedYs blockYs, Odds odds, OreLocation location) {
		
		// do it!
		for (int typeNdx = 0; typeNdx < ore_types.length; typeNdx++) {
			sprinkleOre(generator, lot, chunk, blockYs,
					odds, ore_types[typeNdx], ore_maxY[typeNdx], 
					ore_minY[typeNdx], ore_iterations[typeNdx], 
					ore_amountToDo[typeNdx], ore_upper[typeNdx], ore_liquid[typeNdx]);
		}
	}
}
