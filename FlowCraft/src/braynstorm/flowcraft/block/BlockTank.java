package braynstorm.flowcraft.block;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;
import braynstorm.flowcraft.FlowCraft;
import braynstorm.flowcraft.tile.TileEntityTank;
import braynstorm.flowcraft.utils.Utils;
import buildcraft.api.tools.IToolWrench;
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
		TileEntityTank tank = new TileEntityTank();
		// System.out.println("CREATE TILE ENTTY");
		// tank.readFromNBT(tag);

		return tank;
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
		// TODO update with a packet
		world.setBlockMetadataWithNotify(x, y, z, b0, 2);
		((TileEntityTank) world.getBlockTileEntity(x, y, z)).readFromNBTInit(itemStack.getTagCompound());;

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
			this.nbt = new NBTTagCompound();
		} else {
			NBTTagCompound tag = stack.getTagCompound();
			int id = tag.getInteger("tankFluidID");
			int amount = tag.getInteger("tankFluidAmount");

			if (id == 0)
				Minecraft.getMinecraft().thePlayer.addChatMessage("Tank is empty :(");
			else
				Minecraft.getMinecraft().thePlayer.addChatMessage("Tank has " + amount + "mB" + " of " + FluidRegistry.getFluid(id).getLocalizedName());
		}
	}


	/*
	 * @Override public void onPostBlockPlaced(World world, int x, int y, int z, int meta) { TileEntityTank tile = Utils.getTileEntityAt(world,
	 * TileEntityTank.class, x, y, z); tile.set
	 * 
	 * }
	 */

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		if (!world.isRemote) {
			ItemStack equippedItemStack = player.getCurrentEquippedItem();

			if (equippedItemStack != null)
				if (equippedItemStack.getItem() instanceof IToolWrench) { // react to Buildcraft Api ToolWrench
					this.handleToolWrenchClick(world, x, y, z, player, equippedItemStack);
					return true;
				} else {
					TileEntityTank tileTank = (TileEntityTank) world.getBlockTileEntity(x, y, z);
					if (tileTank != null)
						if (equippedItemStack.getItem().itemID == Item.bucketLava.itemID) { // Lava Bucket
							FluidStack fluidstack = new FluidStack(FluidRegistry.LAVA, 1000);
							if (tileTank.fill(null, fluidstack, false) == 1000) {
								tileTank.fill(null, fluidstack, true);

								if (!player.capabilities.isCreativeMode)
									player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(Item.bucketEmpty, 1));
								// FlowCraft.log("onBlcokActivated", "tankFilledEvent", world);
							}
						} else if (equippedItemStack.getItem().itemID == Item.bucketEmpty.itemID) { // Empty Bucket

							FluidStack drained = tileTank.drain(ForgeDirection.DOWN, 1000, false);

							if (drained != null && drained.amount >= 1000) {

								tileTank.drain(ForgeDirection.DOWN, 1000, true);
								player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(Item.bucketLava, 1));
							}
						} else if (equippedItemStack.getItem() instanceof IFluidContainerItem) { // IFluidContainer

							NBTTagCompound tag = equippedItemStack.getTagCompound();

							if (tag != null && tag.hasKey("Fluid")) {
								tag = tag.getCompoundTag("Fluid");
								// Just drain the container as much as the tank can fit;
								((IFluidContainerItem) equippedItemStack.getItem()).drain(	equippedItemStack,
																							tileTank.fill(	ForgeDirection.UP,
																											FluidStack.loadFluidStackFromNBT(tag),
																											true),
																							true);
							} else {
								NBTTagCompound fluidTag = new NBTTagCompound();
								IFluidContainerItem item = ((IFluidContainerItem) equippedItemStack.getItem());
								if (item.getCapacity(equippedItemStack) <= tileTank.getFluidAmount())
									item.fill(equippedItemStack, tileTank.drain(ForgeDirection.DOWN, item.getCapacity(equippedItemStack), true), true);

							}
						}

					return false;
				}
			return false;
		}
		return true;
	}


	@Override
	public void breakBlock(World world, int x, int y, int z, int meta, int fortune) {
		super.breakBlock(world, x, y, z, meta, fortune);
		world.removeBlockTileEntity(x, y, z);
		this.nbt = new NBTTagCompound();
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

	public Icon[] getIcons() {
		return new Icon[] {
				this.icons[0], this.icons[1], this.icons[2] };
	}
}
