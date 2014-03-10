package braynstorm.flowcraft.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemTank extends ItemBlock {


	public ItemTank(int id) {
		super(id);

		this.setMaxStackSize(1);
		// this.setUnlocalizedName(FlowCraft.REGISTRY_ITEMTANK_NAME);
		// this.setTextureName(FlowCraft.MODID + ":" + FlowCraft.REGISTRY_ITEMTANK_NAME);
		// this.setCreativeTab(FlowCraft.creativeTab);
	}

	@Override
	@SideOnly (Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer player, List dataList, boolean bool) {

		if (itemStack.getTagCompound() != null) {
			int[] tank = itemStack.getTagCompound().getIntArray("tankDetails");
			dataList.add("FluidID: " + tank[0]);
			dataList.add("Amount: " + tank[1]);
		} else
			dataList.add("Something went wrong.");
	}
	/*
	 * 
	 * @Override public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int par7, float par9, float par10, float
	 * par11) { if (par7 != 1) return false; else if (player.canPlayerEdit(x, y, z, par7, itemStack)) {
	 * 
	 * 
	 * world.setBlock(x, y, z, FlowCraft.blockTank.blockID); world.notifyBlocksOfNeighborChange(x, y, z, FlowCraft.blockTank.blockID); --itemStack.stackSize;
	 * return true; } return false; }
	 */
}
