package braynstorm.flowcraft.block;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import braynstorm.flowcraft.FlowCraft;
import braynstorm.flowcraft.tile.TileEntityTank;
import braynstorm.flowcraft.utils.Utils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTank extends WrenchableBlock {


	@SideOnly (Side.CLIENT)
	private Icon[]			icons;
	private NBTTagCompound	nbt	= new NBTTagCompound();

	public BlockTank(int id) {
		super(id, Material.glass);

		this.setUnlocalizedName(FlowCraft.REGISTRY_BLOCKTANK_NAME);
		this.setCreativeTab(FlowCraft.creativeTab);
		this.setHardness(2.5f);
		this.setResistance(1000f);
		this.setStepSound(soundGlassFootstep);

	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityTank();
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack) {
		byte b0 = 0;
		int l = MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		if (l == 0)
			b0 = 2;

		if (l == 1)
			b0 = 5;

		if (l == 2)
			b0 = 3;

		if (l == 3)
			b0 = 4;
		world.setBlockMetadataWithNotify(x, y, z, b0, 2);
	}

	// public void destroy()

	@Override
	protected void handleToolWrenchClick(World world, int x, int y, int z, EntityPlayer player, ItemStack equippedItemStack) {

		TileEntityTank tile = Utils.getTileEntityAt(world, TileEntityTank.class, x, y, z);

		ItemStack stack = new ItemStack(FlowCraft.itemTank);
		stack.setTagCompound(this.nbt);

		if (tile != null)
			tile.writeToNBT(this.nbt);


		if (player.isSneaking()) {
			this.dropBlockAsItem_do(world, x, y, z, stack);
			this.removeBlockByPlayer(world, player, x, y, z);
		} else {
			int[] tank = this.nbt.getIntArray("tankDetails");
			System.out.println("Tank at: X: " + x + ", Y: " + y + ", Z: " + z);
			System.out.println("has " + FluidRegistry.getFluidName(tank[0]) + "  " + tank[1] + "mB");


		}
	}

	@Override
	public int idDropped(int metadata, Random rand, int fortune) {
		return FlowCraft.itemTank.itemID;
	}

	@Override
	protected void dropBlockAsItem_do(World world, int x, int y, int z, ItemStack itemStack) {
		if (!world.isRemote && world.getGameRules().getGameRuleBooleanValue("doTileDrops")) {
			itemStack.setTagCompound(this.nbt);
			float f = 0.7F;
			double d0 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
			double d1 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
			double d2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
			EntityItem entityitem = new EntityItem(world, x + d0, y + d1, z + d2, itemStack);
			entityitem.delayBeforeCanPickup = 10;
			world.spawnEntityInWorld(entityitem);
		}
	}


	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l) {
		return true;
	}


	@Override
	@SideOnly (Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		if (side == 0)
			return this.icons[2];
		if (side == 1)
			return this.icons[1];
		return this.icons[0];
	}

	@Override
	@SideOnly (Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		this.icons = new Icon[] {
				iconRegister.registerIcon(FlowCraft.MODID + ":" + FlowCraft.REGISTRY_BLOCKTANK_NAME),
				iconRegister.registerIcon(FlowCraft.MODID + ":" + FlowCraft.REGISTRY_BLOCKTANK_NAME + "Top"),
				iconRegister.registerIcon(FlowCraft.MODID + ":" + FlowCraft.REGISTRY_BLOCKTANK_NAME + "Bottom") };
	}
}
