package me.daddychurchill.CityWorld.Context;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Clipboard.PasteProvider.SchematicFamily;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.PlatLot.LotStyle;
import me.daddychurchill.CityWorld.Plats.Rural.FarmLot;
import me.daddychurchill.CityWorld.Plats.Rural.HouseLot;
import me.daddychurchill.CityWorld.Plugins.ShapeProvider;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class FarmContext extends RuralContext {

	protected final static double oddsOfFarmHouse = DataContext.oddsSomewhatUnlikely;
	
	public FarmContext(WorldGenerator generator) {
		super(generator);
		
	}
	
	@Override
	protected void initialize() {
		super.initialize();

		oddsOfIsolatedLots = oddsVeryLikely;
		
		schematicFamily = SchematicFamily.FARM;
	}
	
	@Override
	public void populateMap(WorldGenerator generator, PlatMap platmap) {
		
		// now add our stuff
		Odds platmapOdds = platmap.getOddsGenerator();
		ShapeProvider shapeProvider = generator.shapeProvider;
		boolean housePlaced = false;
		int lastX = 0, lastZ = 0;
		
		// where do we begin?
		int originX = platmap.originX;
		int originZ = platmap.originZ;
		
		// clean up the platmap of singletons and odd road structures
		for (int x = 0; x < PlatMap.Width; x++) {
			for (int z = 0; z < PlatMap.Width; z++) {
				PlatLot current = platmap.getLot(x, z);
				
				// something here?
				if (current == null) {
					
					// but there aren't neighbors
					if (!platmap.isEmptyLot(x - 1, z) && !platmap.isEmptyLot(x + 1, z) &&
						!platmap.isEmptyLot(x, z - 1) && !platmap.isEmptyLot(x, z + 1))
						platmap.recycleLot(x, z);
				}
				
				// look for singleton nature and roundabouts
				else {
					
					// if a single natural thing is here but surrounded by four "things"
					if (current.style == LotStyle.NATURE &&
						platmap.isEmptyLot(x - 1, z) && platmap.isEmptyLot(x + 1, z) &&
						platmap.isEmptyLot(x, z - 1) && platmap.isEmptyLot(x, z + 1))
						platmap.emptyLot(x, z);
					
					// get rid of roundabouts
					else if (current.style == LotStyle.ROUNDABOUT) {
						platmap.paveLot(x, z, false);
						platmap.emptyLot(x - 1, z - 1);
						platmap.emptyLot(x - 1, z + 1);
						platmap.emptyLot(x + 1, z - 1);
						platmap.emptyLot(x + 1, z + 1);
					}
				}
			}
		}
		
		// let the user add their stuff first
		mapsSchematics.populate(generator, platmap);
		
		// backfill with farms and a single house
		for (int x = 0; x < PlatMap.Width; x++) {
			for (int z = 0; z < PlatMap.Width; z++) {
				PlatLot current = platmap.getLot(x, z);
				if (current == null) {
					
					//TODO Barns and Wells
					
					// farm house here?
					if (!housePlaced && platmapOdds.playOdds(oddsOfFarmHouse) && generator.settings.includeHouses) {
						housePlaced = platmap.setLot(x, z, getHouseLot(generator, platmap, platmapOdds, originX + lastX, originZ + lastZ)); 
					
					// place the farm
					} else {
						
						// place the farm
						current = getFarmLot(generator, platmap, platmapOdds, originX + x, originZ + z);
						
						// see if the previous chunk is the same type
						PlatLot previous = null;
						if (x > 0 && current.isConnectable(platmap.getLot(x - 1, z))) {
							previous = platmap.getLot(x - 1, z);
						} else if (z > 0 && current.isConnectable(platmap.getLot(x, z - 1))) {
							previous = platmap.getLot(x, z - 1);
						}
						
						// if there was a similar previous one then copy it... maybe
						if (previous != null && !shapeProvider.isIsolatedLotAt(originX + x, originZ + z, oddsOfIsolatedLots)) {
							current.makeConnected(previous);
						}

						// remember what we did
						platmap.setLot(x, z, current);

						// remember the last place
						lastX = x;
						lastZ = z;
					}
				}
			}
		}
		
		// did we miss out placing the farm house?
		if (!housePlaced && platmap.isEmptyLot(lastX, lastZ) && generator.settings.includeHouses) {
			platmap.setLot(lastX, lastZ, getHouseLot(generator, platmap, platmapOdds, originX + lastX, originZ + lastZ)); 
		}
	}
	
	@Override
	protected PlatLot getBackfillLot(WorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ) {
		return getFarmLot(generator, platmap, odds, chunkX, chunkZ);
	}
	
	protected PlatLot getFarmLot(WorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ) {
		return new FarmLot(platmap, chunkX, chunkZ);
	}
	
	protected PlatLot getHouseLot(WorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ) {
		return new HouseLot(platmap, chunkX, chunkZ);
	}
}
