package me.daddychurchill.CityWorld.Support;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;

@SuppressWarnings("deprecation")
public abstract class BlackMagic {

	public static final int maxSnowLevel = 7;
	public static final int maxCauldronLevel = 3;
	
	public static final Material getMaterial(int id) {
		return Material.getMaterial(id);
	}
	
	public static final byte getMaterialId(Material material) {
		return (byte) material.getId();
	}
	
	public static final byte getMaterialId(Block block) {
		return (byte) block.getTypeId();
	}

	public static final byte getMaterialData(Block block) {
		return (byte) block.getData();
	}

	public static final void setBlockType(Block block, int typeId) {
		block.setTypeId(typeId);
	}
	
	public static final boolean setBlockType(Block block, int typeId, int rawdata) {
		BlockState state = block.getState();
		state.setTypeId(typeId);
		if (rawdata != 0)
			state.setRawData((byte) (rawdata & 0xff));
		return state.update(true);
	}
	
	public static final boolean setBlockType(Block block, Material material) {
		return setBlockType(block, material, 0);
	}
	
	public static final boolean setBlockType(Block block, Material material, int rawdata) {
		return setBlockType(block, material, rawdata, true, true);
	}
	
	public static final boolean setBlockType(Block block, Material material, int rawdata, boolean update, boolean physics) {
		BlockState state = block.getState();
		state.setType(material);
		if (rawdata != 0)
			state.setRawData((byte) (rawdata & 0xff));
		return state.update(update, physics);
	}
	
	public static final boolean setBlock(SupportChunk chunk, int x, int y, int z, Material material, int data) {
		return setBlockType(chunk.getActualBlock(x, y, z), material, data);
	}
	
	public static final void setBlocks(SupportChunk chunk, int x, int y1, int y2, int z, Material material, int data) {
		for (int y = y1; y < y2; y++) {
			setBlockType(chunk.getActualBlock(x, y, z), material, data);
		}
	}
	
	public static final void setBlocks(SupportChunk chunk, int x, int y1, int y2, int z, int typeId, int data) {
		for (int y = y1; y < y2; y++) {
			setBlockType(chunk.getActualBlock(x, y, z), typeId, data);
		}
	}
	
	public static final void setBlocks(SupportChunk chunk, int x1, int x2, int y, int z1, int z2, Material material, int data) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				setBlockType(chunk.getActualBlock(x, y, z), material, data);
			}
		}
	}
	
	public static final void setBlocks(SupportChunk chunk, int x1, int x2, int y, int z1, int z2, int typeId, int data) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				setBlockType(chunk.getActualBlock(x, y, z), typeId, data);
			}
		}
	}
	
	public static final void setBlocks(SupportChunk chunk, int x1, int x2, int y1, int y2, int z1, int z2, Material material, int data) {
		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {
				for (int z = z1; z < z2; z++) {
					setBlockType(chunk.getActualBlock(x, y, z), material, data);
				}
			}
		}
	}
	
	public static final void setBlocks(SupportChunk chunk, int x1, int x2, int y1, int y2, int z1, int z2, int typeId, int data) {
		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {
				for (int z = z1; z < z2; z++) {
					setBlockType(chunk.getActualBlock(x, y, z), typeId, data);
				}
			}
		}
	}

	public static void setBlocks(SupportChunk chunk, int x, int y1, int y2, int z, Material primary, Material secondary, MaterialFactory maker) {
//		setBlocks(x, y1, y2, z, BlackMagic.getMaterialId(primary), BlackMagic.getMaterialId(secondary), maker);
	}

	public static void setBlocks(SupportChunk chunk, int x1, int x2, int y1, int y2, int z1, int z2, Material primary, Material secondary, MaterialFactory maker) {
//		setBlocks(x1, x2, y1, y2, z1, z2, BlackMagic.getMaterialId(primary), BlackMagic.getMaterialId(secondary), maker);
	}

	
	
//	public static final boolean setBlockType(Block block, Material material, boolean doPhysics) {
////		return block.setTypeId(getMaterialId(material), doPhysics);
//		if (block == null) {
//			CityWorld.log.info("setBlockType(Block block, Material material, boolean doPhysics): block is null");
//			return false;
//		} else
//			return block.setTypeIdAndData(getMaterialId(material), noData, doPhysics);
//	}
//
//	public static final boolean setBlockType(Block block, byte typeId, boolean doPhysics) {
////		return block.setTypeId(typeId, doPhysics);
//		if (block == null) {
//			CityWorld.log.info("setBlockType(Block block, byte typeId, boolean doPhysics): block is null");
//			return false;
//		} else
//			return block.setTypeIdAndData(typeId & 0xFF, noData, doPhysics);
//	}
//
//	public static final boolean setBlockType(Block block, Material material, byte data, boolean doPhysics) {
////		CityWorld.log.info("BlackMagic...");
//		if (block == null) {
//			CityWorld.log.info("setBlockType(Block block, Material material, byte data, boolean doPhysics): block is null");
//			return false;
//		} else
//			return block.setTypeIdAndData(getMaterialId(material), data, doPhysics);
//	}
//
//	public static final boolean setBlockType(Block block, byte typeId, byte data, boolean doPhysics) {
//		if (block == null) {
//			CityWorld.log.info("setBlockType(Block block, byte typeId, byte data, boolean doPhysics): block is null");
//			return false;
//		} else
//			return block.setTypeIdAndData(typeId & 0xFF, data, doPhysics);
//	}
//	
//	
//	public static final byte noData = (byte) 0;
//		
	
}
