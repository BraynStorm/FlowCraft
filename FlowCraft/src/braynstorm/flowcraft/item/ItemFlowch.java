package braynstorm.flowcraft.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import braynstorm.flowcraft.FlowCraft;
import buildcraft.api.tools.IToolWrench;

public class ItemFlowch extends Item implements IToolWrench {

	public ItemFlowch(int id) {

		super(id);


		this.setMaxStackSize(1);
		this.setUnlocalizedName(FlowCraft.REGISTRY_ITEMFLOWCH_NAME);
		this.setTextureName(FlowCraft.REGISTRY_ITEMFLOWCH_KEY);

		this.setCreativeTab(FlowCraft.creativeTab);
	}

	@Override
	public boolean canWrench(EntityPlayer player, int x, int y, int z) {
		return true;
	}

	@Override
	public void wrenchUsed(EntityPlayer player, int x, int y, int z) {
		System.out.println("wrench used!");
	}


	@Override
	public boolean shouldPassSneakingClickToBlock(World world, int x, int y, int z) {
		return true;
	}

}
