package me.daddychurchill.CityWorld.Plats.Rural;

import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.block.BlockFace;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.ConnectedLot;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plugins.CoverProvider.FoliageSets;
import me.daddychurchill.CityWorld.Plugins.CoverProvider.CoverageType;
import me.daddychurchill.CityWorld.Support.BlackMagic;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealChunk;
import me.daddychurchill.CityWorld.Support.SupportChunk;
import me.daddychurchill.CityWorld.Support.SurroundingLots;

public class FarmLot extends ConnectedLot {

	//TODO Apple farm?
	//TODO Cocoa farm?
	//TODO PPPwPPPPPPwPPP based wheat/flower/grass/mushroom/netherwart/dead/none/fallow
	//TODO SPSwSPSSPSwSPS based pumpkin/melon/dead/none/fallow
	//TODO wPPwPPwwPPwPPw based cane/dead/none/fallow
	
	public enum CropType {FALLOW, TRELLIS, VINES, 
		GRASS, FERN, DEAD_GRASS, CACTUS, REED, DANDELION, DEAD_BUSH, 
		POPPY, BLUE_ORCHID, ALLIUM, AZURE_BLUET, OXEYE_DAISY,
		RED_TULIP, ORANGE_TULIP, WHITE_TULIP, PINK_TULIP,
		SUNFLOWER, LILAC, TALL_GRASS, TALL_FERN, ROSE_BUSH, PEONY,
		EMERALD_GREEN, 
		OAK_SAPLING, SPRUCE_SAPLING, BIRCH_SAPLING, 
		JUNGLE_SAPLING, ACACIA_SAPLING, DARK_OAK_SAPLING,
		WHEAT, CARROTS, POTATO, MELON, PUMPKIN, 
		BROWN_MUSHROOM, RED_MUSHROOM, NETHERWART,
		SHORT_FLOWERS, TALL_FLOWERS, ALL_FLOWERS,
		SHORT_PLANTS, TALL_PLANTS, ALL_PLANTS,
		EDIBLE_PLANTS, NETHER_PLANTS, DECAY_PLANTS};
	protected CropType cropType;

	private boolean directionNorthSouth;
	private double oddsOfCrop = DataContext.oddsExtremelyLikely;

	public FarmLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		
		style = LotStyle.STRUCTURE;
		cropType = CropType.FALLOW;
		directionNorthSouth = chunkOdds.flipCoin();
		
		// crop type please
		if (platmap.generator.worldEnvironment == Environment.NETHER)
			if (platmap.generator.settings.includeDecayedNature)
				cropType = setDecayedNetherCrop();
			else
				cropType = setNetherCrop();
		else
			if (platmap.generator.settings.includeDecayedNature)
				cropType = setDecayedNormalCrop();
			else
				cropType = setNormalCrop();

		// decayed world?
		if (platmap.generator.settings.includeDecayedNature)
			oddsOfCrop = DataContext.oddsSomewhatUnlikely;
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new FarmLot(platmap, chunkX, chunkZ);
	}

	@Override
	public boolean makeConnected(PlatLot relative) {
		boolean result = super.makeConnected(relative);
		
		// other bits
		if (result && relative instanceof FarmLot) {
			FarmLot relativeFarm = (FarmLot) relative;
			
			directionNorthSouth = relativeFarm.directionNorthSouth;
			cropType = relativeFarm.cropType;
		}
		return result;
	}

	protected Material waterMaterial = Material.STATIONARY_WATER;
	protected final static Material cropNone = Material.DIRT;
	
	private final static Material isolationNormalMaterial = Material.LOG;
	private final static Material isolationNetherMaterial = Material.NETHER_BRICK;
	
	private final static Material soilMaterial = Material.SOIL;
	private final static Material sandMaterial = Material.SAND;
	private final static Material mycelMaterial = Material.MYCEL;
	private final static Material dirtMaterial = Material.DIRT;
	private final static Material soulMaterial = Material.SOUL_SAND;
	private final static Material trellisMaterial = Material.WOOD;
	private final static Material poleMaterial = Material.FENCE;

	@Override
	public int getBottomY(WorldGenerator generator) {
		return generator.streetLevel;
	}
	
	@Override
	public int getTopY(WorldGenerator generator) {
		return generator.streetLevel + DataContext.FloorHeight * 3 + 1;
	}

	@Override
	protected void generateActualChunk(WorldGenerator generator, PlatMap platmap, ByteChunk chunk, BiomeGrid biomes, DataContext context, int platX, int platZ) {
		// look around
		SurroundingLots farms = new SurroundingLots(platmap, platX, platZ);
		
		// what type of ground do we have
		chunk.setLayer(generator.streetLevel - 1, 2, generator.oreProvider.surfaceMaterial);
		
		// in-between bits bits
		Material dividerMaterial = isolationNormalMaterial;
		if (generator.worldEnvironment == Environment.NETHER) {
			dividerMaterial = isolationNetherMaterial;
		}
		
		// draw the isolation blocks
		if (!farms.toNorth()) {
			chunk.setBlocks(1, 15, generator.streetLevel, 0, 1, dividerMaterial);
			if (farms.toWest())
				chunk.setBlock(0, generator.streetLevel, 0, dividerMaterial);
			if (farms.toEast())
				chunk.setBlock(15, generator.streetLevel, 0, dividerMaterial);
		}
		if (!farms.toSouth()) {
			chunk.setBlocks(1, 15, generator.streetLevel, 15, 16, dividerMaterial);
			if (farms.toWest())
				chunk.setBlock(0, generator.streetLevel, 15, dividerMaterial);
			if (farms.toEast())
				chunk.setBlock(15, generator.streetLevel, 15, dividerMaterial);
		}
		if (!farms.toWest()) {
			chunk.setBlocks(0, 1, generator.streetLevel, 1, 15, dividerMaterial);
			if (farms.toNorth())
				chunk.setBlock(0, generator.streetLevel, 0, dividerMaterial);
			if (farms.toSouth())
				chunk.setBlock(0, generator.streetLevel, 15, dividerMaterial);
		}
		if (!farms.toEast()) {
			chunk.setBlocks(15, 16, generator.streetLevel, 1, 15, dividerMaterial);
			if (farms.toNorth())
				chunk.setBlock(15, generator.streetLevel, 0, dividerMaterial);
			if (farms.toSouth())
				chunk.setBlock(15, generator.streetLevel, 15, dividerMaterial);
		}
	}
	
	@Override
	protected void generateActualBlocks(WorldGenerator generator, PlatMap platmap, RealChunk chunk, DataContext context, int platX, int platZ) {
		int croplevel = generator.streetLevel + 1;
		
		boolean fallowField = false;
		Material fallowMaterial = getAirMaterial(generator, croplevel - 1);
		
		switch (cropType) {
		case TRELLIS:
		case VINES:
			buildVineyard(chunk, croplevel);
			break;
		case GRASS:
		case FERN:
		case DEAD_GRASS:
		case DANDELION:
		case POPPY:
		case BLUE_ORCHID:
		case ALLIUM:
		case AZURE_BLUET:
		case OXEYE_DAISY:
		case RED_TULIP:
		case ORANGE_TULIP:
		case WHITE_TULIP:
		case PINK_TULIP:
		case SUNFLOWER:
		case LILAC:
		case TALL_GRASS:
		case TALL_FERN:
		case ROSE_BUSH:
		case PEONY:
		case EMERALD_GREEN:
		case OAK_SAPLING:
		case SPRUCE_SAPLING:
		case BIRCH_SAPLING:
		case ACACIA_SAPLING:
		case JUNGLE_SAPLING:
		case SHORT_FLOWERS:
		case TALL_FLOWERS:
		case ALL_FLOWERS:
		case SHORT_PLANTS:
		case TALL_PLANTS:
		case ALL_PLANTS:
		case DECAY_PLANTS:
			if (generator.settings.includeAbovegroundFluids)
				plowField(chunk, croplevel, dirtMaterial, 0, waterMaterial, 2);
			else 
				fallowField = true;
			break;
		case DARK_OAK_SAPLING:
			if (generator.settings.includeAbovegroundFluids)
				plowField(chunk, croplevel, dirtMaterial, 0, waterMaterial, 3);
			else 
				fallowField = true;
			break;
		case CACTUS:
			plowField(chunk, croplevel, sandMaterial, 0, sandMaterial, 2);
			break;
		case REED:
			if (generator.settings.includeAbovegroundFluids)
				plowField(chunk, croplevel, sandMaterial, 0, waterMaterial, 2);
			else 
				fallowField = true;
			break;
		case DEAD_BUSH:
			plowField(chunk, croplevel, dirtMaterial, 0, fallowMaterial, 2);
			break;
		case WHEAT:
		case CARROTS:
		case POTATO:
			if (generator.settings.includeAbovegroundFluids)
				plowField(chunk, croplevel, soilMaterial, 8, waterMaterial, 2);
			else 
				fallowField = true;
			break;
		case MELON:
		case PUMPKIN:
		case EDIBLE_PLANTS:
			if (generator.settings.includeAbovegroundFluids)
				plowField(chunk, croplevel, soilMaterial, 8, waterMaterial, 3);
			else 
				fallowField = true;
			break;
		case BROWN_MUSHROOM:
		case RED_MUSHROOM:
			plowField(chunk, croplevel, mycelMaterial, 0, fallowMaterial, 2);
			break;
		case NETHERWART:
			plowField(chunk, croplevel, soulMaterial, 0, fallowMaterial, 2);
			break;
		case NETHER_PLANTS:
			plowField(chunk, croplevel, soulMaterial, 0, fallowMaterial, 2);
			break;
		case FALLOW:
			fallowField = true;
			break;
		}
		
		if (fallowField)
			plowField(chunk, croplevel, dirtMaterial, 0, Material.AIR, 2);
		else {
		
			switch (cropType) {
			case TRELLIS:
				break;
			case VINES:
				plantVineyard(chunk, croplevel, Material.VINE);
				break;
			case GRASS:
				plantField(generator, chunk, croplevel, CoverageType.GRASS, 1, 2);
				break;
			case FERN:
				plantField(generator, chunk, croplevel, CoverageType.FERN, 1, 2);
				break;
			case DEAD_GRASS:
				plantField(generator, chunk, croplevel, CoverageType.DEAD_GRASS, 1, 2);
				break;
			case CACTUS:
				plantField(generator, chunk, croplevel, CoverageType.CACTUS, 2, 2);
				break;
			case REED:
				plantField(generator, chunk, croplevel, CoverageType.REED, 1, 2);
				break;
			case DANDELION:
				plantField(generator, chunk, croplevel, CoverageType.DANDELION, 1, 2);
				break;
			case DEAD_BUSH:
				plantField(generator, chunk, croplevel, CoverageType.DEAD_BUSH, 1, 2);
				break;
			case POPPY:
				plantField(generator, chunk, croplevel, CoverageType.POPPY, 1, 2);
				break;
			case BLUE_ORCHID:
				plantField(generator, chunk, croplevel, CoverageType.BLUE_ORCHID, 1, 2);
				break;
			case ALLIUM:
				plantField(generator, chunk, croplevel, CoverageType.ALLIUM, 1, 2);
				break;
			case AZURE_BLUET:
				plantField(generator, chunk, croplevel, CoverageType.AZURE_BLUET, 1, 2);
				break;
			case OXEYE_DAISY:
				plantField(generator, chunk, croplevel, CoverageType.OXEYE_DAISY, 1, 2);
				break;
			case RED_TULIP:
				plantField(generator, chunk, croplevel, CoverageType.RED_TULIP, 1, 2);
				break;
			case ORANGE_TULIP:
				plantField(generator, chunk, croplevel, CoverageType.ORANGE_TULIP, 1, 2);
				break;
			case WHITE_TULIP:
				plantField(generator, chunk, croplevel, CoverageType.WHITE_TULIP, 1, 2);
				break;
			case PINK_TULIP:
				plantField(generator, chunk, croplevel, CoverageType.PINK_TULIP, 1, 2);
				break;
			case SUNFLOWER:
				plantField(generator, chunk, croplevel, CoverageType.SUNFLOWER, 1, 2);
				break;
			case LILAC:
				plantField(generator, chunk, croplevel, CoverageType.LILAC, 1, 2);
				break;
			case TALL_GRASS:
				plantField(generator, chunk, croplevel, CoverageType.TALL_FERN, 1, 2);
				break;
			case TALL_FERN:
				plantField(generator, chunk, croplevel, CoverageType.TALL_FERN, 1, 2);
				break;
			case ROSE_BUSH:
				plantField(generator, chunk, croplevel, CoverageType.ROSE_BUSH, 1, 2);
				break;
			case PEONY:
				plantField(generator, chunk, croplevel, CoverageType.PEONY, 1, 2);
				break;
			case EMERALD_GREEN:
				plantField(generator, chunk, croplevel, CoverageType.EMERALD_GREEN, 2, 2);
				break;
			case OAK_SAPLING:
				plantField(generator, chunk, croplevel, CoverageType.OAK_SAPLING, 7, 2);
				break;
			case SPRUCE_SAPLING:
				plantField(generator, chunk, croplevel, CoverageType.SPRUCE_SAPLING, 7, 2);
				break;
			case BIRCH_SAPLING:
				plantField(generator, chunk, croplevel, CoverageType.BIRCH_SAPLING, 7, 2);
				break;
			case JUNGLE_SAPLING:
				plantField(generator, chunk, croplevel, CoverageType.JUNGLE_SAPLING, 7, 2);
				break;
			case ACACIA_SAPLING:
				plantField(generator, chunk, croplevel, CoverageType.ACACIA_SAPLING, 7, 2);
				break;
			case DARK_OAK_SAPLING:
				plantField(generator, chunk, croplevel, CoverageType.DARK_OAK_SAPLING, 7, 3);
				break;
			case WHEAT:
				plantField(generator, chunk, croplevel, CoverageType.WHEAT, 1, 2);
				break;
			case CARROTS:
				plantField(generator, chunk, croplevel, CoverageType.CARROTS, 1, 2);
				break;
			case POTATO:
				plantField(generator, chunk, croplevel, CoverageType.POTATO, 1, 2);
				break;
			case MELON:
				plantField(generator, chunk, croplevel, CoverageType.MELON, 1, 3);
				break;
			case PUMPKIN:
				plantField(generator, chunk, croplevel, CoverageType.PUMPKIN, 1, 3);
				break;
			case BROWN_MUSHROOM:
				plantField(generator, chunk, croplevel, CoverageType.BROWN_MUSHROOM, 1, 2);
				break;
			case RED_MUSHROOM:
				plantField(generator, chunk, croplevel, CoverageType.RED_MUSHROOM, 1, 2);
				break;
			case NETHERWART:
				plantField(generator, chunk, croplevel, CoverageType.NETHERWART, 1, 2);
				break;
			case SHORT_FLOWERS:
				plantField(generator, chunk, croplevel, FoliageSets.SHORT_FLOWERS, 1, 2);
				break;
			case TALL_FLOWERS:
				plantField(generator, chunk, croplevel, FoliageSets.TALL_FLOWERS, 1, 2);
				break;
			case ALL_FLOWERS:
				plantField(generator, chunk, croplevel, FoliageSets.ALL_FLOWERS, 1, 2);
				break;
			case SHORT_PLANTS:
				plantField(generator, chunk, croplevel, FoliageSets.SHORT_PLANTS, 1, 2);
				break;
			case TALL_PLANTS:
				plantField(generator, chunk, croplevel, FoliageSets.TALL_PLANTS, 2, 2);
				break;
			case ALL_PLANTS:
				plantField(generator, chunk, croplevel, FoliageSets.ALL_PLANTS, 2, 2);
				break;
			case EDIBLE_PLANTS:
				plantField(generator, chunk, croplevel, FoliageSets.EDIBLE_PLANTS, 1, 3);
				break;
			case NETHER_PLANTS:
				plantField(generator, chunk, croplevel, FoliageSets.NETHER_PLANTS, 1, 2);
				break;
			case DECAY_PLANTS:
				plantField(generator, chunk, croplevel, FoliageSets.DECAY_PLANTS, 1, 2);
				break;
			case FALLOW:
				break;
			}
		}
	}

	private void plowField(SupportChunk chunk, int croplevel, 
			Material matRidge, int datRidge, Material matFurrow, 
			int stepCol) {
		
		// do the deed
		if (directionNorthSouth) {
			for (int x = 1; x < 15; x++) {
				if (x % stepCol == 0)
					chunk.setBlocks(x, x + 1, croplevel - 1, croplevel, 1, 15, matFurrow);
				else {
					if (matRidge == Material.SOIL)
						BlackMagic.setBlocks(chunk, x, x + 1, croplevel - 1, croplevel, 1, 15, Material.SOIL, datRidge);
					else
						chunk.setBlocks(x, x + 1, croplevel - 1, croplevel, 1, 15, matRidge);
				}
			}
		} else {
			for (int z = 1; z < 15; z += stepCol) {
				if (z % stepCol == 0)
					chunk.setBlocks(1, 15, croplevel - 1, croplevel, z, z + 1, matFurrow);
				else {
					if (matRidge == Material.SOIL)
						BlackMagic.setBlocks(chunk, 1, 15, croplevel - 1, croplevel, z, z + 1, Material.SOIL, datRidge);
					else
						chunk.setBlocks(1, 15, croplevel - 1, croplevel, z, z + 1, matRidge);
				}
			}
		}
	}
	
	private void plantField(WorldGenerator generator, SupportChunk chunk, int croplevel, 
			CoverageType coverageType, int stepRow, int stepCol) {
		
		// do the deed
		if (directionNorthSouth) {
			for (int x = 1; x < 15; x += stepCol) {
				for (int z = 1; z < 15; z += stepRow)
					if (chunkOdds.playOdds(oddsOfCrop))
						generator.coverProvider.setCoverage(chunk, x, croplevel, z, coverageType);
			}
		} else {
			for (int z = 1; z < 15; z += stepCol) {
				for (int x = 1; x < 15; x += stepRow)
					if (chunkOdds.playOdds(oddsOfCrop))
						generator.coverProvider.setCoverage(chunk, x, croplevel, z, coverageType);
			}
		}
	}
	
	private void plantField(WorldGenerator generator, SupportChunk chunk, int croplevel, 
			FoliageSets coverageSet, int stepRow, int stepCol) {
		
		// do the deed
		if (directionNorthSouth) {
			for (int x = 1; x < 15; x += stepCol) {
				for (int z = 1; z < 15; z += stepRow)
					if (chunkOdds.playOdds(oddsOfCrop))
						generator.coverProvider.setCoverage(chunk, x, croplevel, z, coverageSet);
			}
		} else {
			for (int z = 1; z < 15; z += stepCol) {
				for (int x = 1; x < 15; x += stepRow)
					if (chunkOdds.playOdds(oddsOfCrop))
						generator.coverProvider.setCoverage(chunk, x, croplevel, z, coverageSet);
			}
		}
	}
	
	private static int stepCol = 3;
	private void buildVineyard(SupportChunk chunk, int cropLevel) {
		if (directionNorthSouth) {
			for (int x = 1; x < 15; x += stepCol) {
				chunk.setBlocks(x, cropLevel, cropLevel + 4, 1, poleMaterial);
				chunk.setBlocks(x, cropLevel, cropLevel + 4, 14, poleMaterial);
				chunk.setBlocks(x, x + 1, cropLevel + 3, cropLevel + 4, 2, 14, trellisMaterial);
			}
		} else {
			for (int z = 1; z < 15; z += stepCol) {
				chunk.setBlocks(1, cropLevel, cropLevel + 4, z, poleMaterial);
				chunk.setBlocks(14, cropLevel, cropLevel + 4, z, poleMaterial);
				chunk.setBlocks(2, 14, cropLevel + 3, cropLevel + 4, z, z + 1, trellisMaterial);
			}
		}
	}
	
	private void plantVineyard(SupportChunk chunk, int cropLevel, Material matCrop) {
		if (directionNorthSouth) {
			for (int x = 1; x < 15; x += stepCol) {
				if (chunkOdds.playOdds(oddsOfCrop)) {
					for (int z = 2; z < 14; z++) {
						chunk.setBlocksTypeAndDirection(x - 1, x, cropLevel + 1 + chunkOdds.getRandomInt(3), cropLevel + 4, z, z + 1, matCrop, BlockFace.EAST);
						chunk.setBlocksTypeAndDirection(x + 1, x + 2, cropLevel + 1 + chunkOdds.getRandomInt(3), cropLevel + 4, z, z + 1, matCrop, BlockFace.WEST);
					}
				}
			}
		} else {
			for (int z = 1; z < 15; z += stepCol) {
				if (chunkOdds.playOdds(oddsOfCrop)) {
					for (int x = 2; x < 14; x++) {
						chunk.setBlocksTypeAndDirection(x, x + 1, cropLevel + 1 + chunkOdds.getRandomInt(3), cropLevel + 4, z - 1, z, matCrop, BlockFace.SOUTH);
						chunk.setBlocksTypeAndDirection(x, x + 1, cropLevel + 1 + chunkOdds.getRandomInt(3), cropLevel + 4, z + 1, z + 2, matCrop, BlockFace.NORTH);
					}
				}
			}
		}
	}
	
	private final static CropType[] normalCrops = {
		CropType.FALLOW, 
		CropType.TRELLIS, 
		CropType.VINES, 
		CropType.GRASS, 
		CropType.FERN, 
		CropType.DEAD_GRASS, 
		CropType.CACTUS, 
		CropType.REED, 
		CropType.DANDELION, 
		CropType.DEAD_BUSH, 
		CropType.POPPY, 
		CropType.BLUE_ORCHID, 
		CropType.ALLIUM, 
		CropType.AZURE_BLUET, 
		CropType.OXEYE_DAISY,
		CropType.RED_TULIP, 
		CropType.ORANGE_TULIP, 
		CropType.WHITE_TULIP, 
		CropType.PINK_TULIP,
		CropType.SUNFLOWER, 
		CropType.LILAC, 
		CropType.TALL_GRASS, 
		CropType.TALL_FERN, 
		CropType.ROSE_BUSH, 
		CropType.PEONY,
		CropType.WHEAT, 
		CropType.CARROTS, 
		CropType.POTATO, 
		CropType.MELON, 
		CropType.PUMPKIN, 
		CropType.SHORT_FLOWERS, 
		CropType.TALL_FLOWERS, 
		CropType.ALL_FLOWERS,
		CropType.SHORT_PLANTS,
		CropType.TALL_PLANTS,
		CropType.ALL_PLANTS,
		CropType.EDIBLE_PLANTS};
	
	protected CropType setNormalCrop() {
//		return CropType.SHORT_FLOWERS;
		return pickACrop(normalCrops);
	}

	private final static CropType[] decayCrops = {
		CropType.FALLOW,
		CropType.TRELLIS,
		CropType.DEAD_BUSH,
		CropType.BROWN_MUSHROOM,
		CropType.RED_MUSHROOM,
		CropType.DECAY_PLANTS};
	
	protected CropType setDecayedNormalCrop() {
		return pickACrop(decayCrops);
	}

	private final static CropType[] netherCrops = {
		CropType.FALLOW,
		CropType.TRELLIS,
		CropType.DEAD_BUSH,
		CropType.BROWN_MUSHROOM,
		CropType.RED_MUSHROOM,
		CropType.NETHERWART,
		CropType.NETHER_PLANTS};
	
	protected CropType setNetherCrop() {
		return pickACrop(netherCrops);
	}

	protected CropType setDecayedNetherCrop() {
		if (chunkOdds.flipCoin())
			return CropType.FALLOW;
		else
			return setNetherCrop();
	}
	
	protected CropType pickACrop(CropType ... types) {
		return types[chunkOdds.getRandomInt(types.length)];
	}
}
