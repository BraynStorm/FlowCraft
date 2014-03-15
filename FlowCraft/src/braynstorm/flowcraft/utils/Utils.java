package braynstorm.flowcraft.utils;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

/**
 * General Utilities
 */
public final class Utils {
	/**
	 * Gets the {@link TileEntity} at the specified coordinates, casted to the specified type.
	 * 
	 * @param access
	 *            A {@link IBlockAccess} implementation. Usually the world.
	 * @param entityType
	 *            The type the {@link TileEntity} should be casted to.
	 * @param coords
	 *            The coordinates of the {@link TileEntity}.
	 * @return The casted {@link TileEntity} or <code>null</code> if no {@link TileEntity} was found or the casting failed.
	 */
	public static final <T extends TileEntity> T getTileEntityAt(IBlockAccess access, Class <T> entityType, int... coords) {
		if (access != null && entityType != null && coords != null && coords.length == 3) {
			TileEntity entity = access.getBlockTileEntity(coords[0], coords[1], coords[2]);

			if (entity != null && entity.getClass() == entityType)
				return (T) entity;
		}

		return null;
	}

	/**
	 * Registers a block in {@link GameRegistry} and {@link LanguageRegistry}
	 * 
	 * @param block
	 *            The instance of the {@link Block}'s .
	 * @param uniqueName
	 *            The unique name of the {@link Block}.
	 */

	public static void registerBlock(Block block, String uniqueName) {

		GameRegistry.registerBlock(block, uniqueName);
		// LanguageRegistry.addName(block, name);
	}

	/**
	 * Registers an item in {@link GameRegistry} and {@link LanguageRegistry}
	 * 
	 * @param item
	 *            The instance of the {@link Item}.
	 * @param uniqueName
	 *            The unique name of the {@link Item}.
	 */
	public static void registerItem(Item item, String uniqueName) {

		GameRegistry.registerItem(item, uniqueName);
		// LanguageRegistry.addName(item, name);
	}


	public static int[] getXYZForSide(int x, int y, int z, int side) {
		int nx = x, ny = y, nz = z;

		switch (side) {
			case 0:
				ny--;
				break;
			case 1:
				ny++;
				break;
			case 2:
				nz--;
				break;
			case 3:
				nz++;
				break;
			case 4:
				nx--;
				break;
			case 5:
				nx++;
				break;
		}

		return new int[] {
				nx, ny, nz };

	}

	public static int getXForSide(int x, int side) {
		if (side == 4)
			return --x;
		if (side == 5)
			return ++x;
		return x;
	}

	public static int getZForSide(int z, int side) {
		if (side == 2)
			return --z;
		if (side == 3)
			return ++z;
		return z;
	}

	public static int getYForSide(int y, int side) {
		if (side == 0)
			return --y;
		if (side == 1)
			return ++y;
		return y;
	}


}
