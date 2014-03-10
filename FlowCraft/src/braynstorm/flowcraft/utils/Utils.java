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

	public static void registerBlock(Block block, String uniqueName, String name) {

		GameRegistry.registerBlock(block, uniqueName);
		LanguageRegistry.addName(block, name);
	}

	/**
	 * Registers an item in {@link GameRegistry} and {@link LanguageRegistry}
	 * 
	 * @param item
	 *            The instance of the {@link Item}.
	 * @param uniqueName
	 *            The unique name of the {@link Item}.
	 */
	public static void registerItem(Item item, String uniqueName, String name) {

		GameRegistry.registerItem(item, uniqueName);
		LanguageRegistry.addName(item, name);
	}
}
