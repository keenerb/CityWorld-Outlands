package me.daddychurchill.CityWorld.Plugins;
import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.Odds;

import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class LootProvider_Decayed extends LootProvider {
	
	@Override
	public void setLoot(CityWorldGenerator generator, Odds odds, String worldPrefix, LootLocation lootLocation, Block block) {
		Chest chest = (Chest) block.getState();
		Inventory inv = chest.getInventory();
		inv.clear();
		ItemStack[] items = getLoot(generator, odds, lootLocation, block);
		if (items != null)
			inv.addItem(items);
		chest.update(true);
	}
	
	@Override
	public void saveLoots() {
		// we don't need to do anything
	}
	
	private ItemStack[] getLoot(CityWorldGenerator generator, Odds odds, LootLocation lootLocation, Block block) {
		
		// which mix?
		switch (lootLocation) {
		case EMPTY:
			return null;
		case BUNKER:
			return pickFromTreasuresDecayed(generator.materialProvider.itemsRandomMaterials_BunkerChests, odds, 1, 1);
		case MINE:
			return pickFromTreasuresDecayed(generator.materialProvider.itemsRandomMaterials_MineChests, odds, 2, 1);
		case SEWER:
			return pickFromTreasuresDecayed(generator.materialProvider.itemsRandomMaterials_SewerChests, odds, 3, 1);
		case BUILDING:
			return pickFromTreasuresDecayed(generator.materialProvider.itemsRandomMaterials_BuildingChests, odds, 4, 1);
		case WAREHOUSE:
			return pickFromTreasuresDecayed(generator.materialProvider.itemsRandomMaterials_WarehouseChests, odds, 5, 1);
		case FOOD:
			return pickFromTreasuresDecayed(generator.materialProvider.itemsRandomMaterials_FoodChests, odds, 1, 6);
		case STORAGESHED:
			return pickFromTreasuresDecayed(generator.materialProvider.itemsRandomMaterials_StorageShedChests, odds, 1, 7);
		case FARMWORKS:
			return pickFromTreasuresDecayed(generator.materialProvider.itemsRandomMaterials_FarmChests, odds, 1, 8);
		case FARMWORKSOUTPUT:
			return pickFromTreasuresDecayed(generator.materialProvider.itemsRandomMaterials_FarmOutputChests, odds, 1, 9);
		case WOODWORKS:
			return pickFromTreasuresDecayed(generator.materialProvider.itemsRandomMaterials_LumberChests, odds, 1, 10);
		case WOODWORKSOUTPUT:
			return pickFromTreasuresDecayed(generator.materialProvider.itemsRandomMaterials_LumberOutputChests, odds, 1, 11);
		case STONEWORKS:
			return pickFromTreasuresDecayed(generator.materialProvider.itemsRandomMaterials_QuaryChests, odds, 1, 12);
		case STONEWORKSOUTPUT:
			return pickFromTreasuresDecayed(generator.materialProvider.itemsRandomMaterials_QuaryOutputChests, odds, 1, 13);
		case RANDOM:
			return null;
		}
		
		throw new IllegalArgumentException(); 
	}
	
}
