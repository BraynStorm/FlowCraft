package braynstorm.flowcraft;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import braynstorm.flowcraft.block.BlockFluidBlaze;
import braynstorm.flowcraft.block.BlockFluidFurnace;
import braynstorm.flowcraft.block.BlockTank;
import braynstorm.flowcraft.fluid.FluidBlaze;
import braynstorm.flowcraft.item.ItemCapsule;
import braynstorm.flowcraft.item.ItemFlowch;
import braynstorm.flowcraft.item.ItemTank;
import braynstorm.flowcraft.materials.MaterialBlaze;
import braynstorm.flowcraft.materials.MaterialFlowCraftMachine;
import braynstorm.flowcraft.recipe.RecipeManager;
import braynstorm.flowcraft.render.RendererCapsule;
import braynstorm.flowcraft.render.RendererTank;
import braynstorm.flowcraft.tile.TileEntityFluidFurnace;
import braynstorm.flowcraft.tile.TileEntityTank;
import braynstorm.flowcraft.utils.Utils;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod (modid = FlowCraft.MODID, name = FlowCraft.MODNAME, version = FlowCraft.VERSION)
@NetworkMod (clientSideRequired = true, serverSideRequired = true)
public class FlowCraft {

	public static final String		MODID							= "flowcraft";
	public static final String		MODNAME							= "FlowCraft";
	public static final String		VERSION							= "0.0.012";

	// BLOCKS
	public static final String		REGISTRY_BLOCKTANK_NAME			= "blockTank";
	public static final String		REGISTRY_BLOCKTANK_KEY			= MODID + ":" + REGISTRY_BLOCKTANK_NAME;
	public static final String		REGISTRY_BLOCKFLUIDFURANCE_NAME	= "blockFluidFurnace";
	public static final String		REGISTRY_BLOCKFLUIDFURANCE_KEY	= MODID + ":" + REGISTRY_BLOCKFLUIDFURANCE_NAME;

	// FLUID BLOCKS
	public static final String		REGISTRY_BLOCKFLUIDBLAZE_NAME	= "blockFluidBlaze";
	public static final String		REGISTRY_BLOCKFLUIDBLAZE_KEY	= MODID + ":" + REGISTRY_BLOCKFLUIDBLAZE_NAME;

	// FLUIDS
	public static final String		REGISTRY_FLUIDBLAZE_NAME		= "fluidBlaze";
	public static final String		REGISTRY_FLUIDBLAZE_KEY			= MODID + ":" + REGISTRY_FLUIDBLAZE_NAME;


	// ITEMS
	public static final String		REGISTRY_ITEMFLOWCH_NAME		= "itemFlowch";
	public static final String		REGISTRY_ITEMFLOWCH_KEY			= MODID + ":" + REGISTRY_ITEMFLOWCH_NAME;
	public static final String		REGISTRY_ITEMTANK_NAME			= "itemTank";
	public static final String		REGISTRY_ITEMTANK_KEY			= MODID + ":" + REGISTRY_ITEMTANK_NAME;
	public static final String		REGISTRY_ITEMCAPSULE_NAME		= "itemCapsule";
	public static final String		REGISTRY_ITEMCAPSULE_KEY		= MODID + ":" + REGISTRY_ITEMCAPSULE_NAME;


	public static CreativeTabs		creativeTab;

	// IDs
	public static final int			REGISTRY_BLOCKTANK_ID			= 3800;
	public static final int			REGISTRY_BLOCKFLUIDFURNACE_ID	= 3801;
	public static final int			REGISTRY_BLOCKFLUIDBLAZE_ID		= 3900;
	public static final int			REGISTRY_ITEMFLOWCH_ID			= 4200;
	public static final int			REGISTRY_ITEMTANK_ID			= REGISTRY_BLOCKTANK_ID - 256;
	public static final int			REGISTRY_ITEMCAPSULE_ID			= 4900;


	// MATERIALS
	public static Material			materialBlaze;
	public static Material			materialMachine;


	public static BlockTank			blockTank;
	public static BlockFluidBlaze	blockFluidBlaze;
	public static BlockFluidFurnace	blockFluidFurnace;

	public static FluidBlaze		fluidBlaze;


	public static Item				itemFlowch;
	public static Item				itemTank;
	public static Item				itemCapsule;


	public static RendererTank		rendererTank					= new RendererTank();
	public static RendererCapsule	rendererCapsule					= new RendererCapsule();


	@Instance (MODID)
	public static FlowCraft			instance;

	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {

		creativeTab = new CreativeTabs("FlowCraft") {

			@Override
			@SideOnly (Side.CLIENT)
			public String getTranslatedTabLabel() {
				return this.getTabLabel();
			}

			@Override
			@SideOnly (Side.CLIENT)
			public int getTabIconItemIndex() {
				return FlowCraft.itemFlowch.itemID;
			}
		};


		// stack1.setTagCompound(taggy1);


		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTank.class, rendererTank);

		MinecraftForgeClient.registerItemRenderer(REGISTRY_ITEMCAPSULE_ID, rendererCapsule);

		materialBlaze = new MaterialBlaze();
		materialMachine = new MaterialFlowCraftMachine();


		blockTank = new BlockTank(REGISTRY_BLOCKTANK_ID);
		blockFluidBlaze = new BlockFluidBlaze(REGISTRY_BLOCKFLUIDBLAZE_ID);
		blockFluidFurnace = new BlockFluidFurnace(REGISTRY_BLOCKFLUIDFURNACE_ID);
		Utils.registerBlock(blockTank, REGISTRY_BLOCKTANK_NAME);
		Utils.registerBlock(blockFluidFurnace, REGISTRY_BLOCKFLUIDFURANCE_NAME);
		Utils.registerBlock(blockFluidBlaze, REGISTRY_BLOCKFLUIDBLAZE_NAME);

		itemFlowch = new ItemFlowch(REGISTRY_ITEMFLOWCH_ID);
		itemTank = new ItemTank(REGISTRY_ITEMTANK_ID);
		itemCapsule = new ItemCapsule(REGISTRY_ITEMCAPSULE_ID, 1000);
		Utils.registerItem(itemFlowch, REGISTRY_ITEMFLOWCH_NAME);
		Utils.registerItem(itemTank, REGISTRY_ITEMTANK_NAME);
		Utils.registerItem(itemCapsule, REGISTRY_ITEMCAPSULE_NAME);


		NBTTagCompound taggy1 = new NBTTagCompound();
		taggy1.setInteger("fluidID", 2);
		taggy1.setInteger("fluidAmount", 1000);


		ItemStack stack1 = new ItemStack(itemCapsule);

		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack("lava", 1000), stack1, new ItemStack(itemCapsule));

		GameRegistry.registerTileEntity(TileEntityTank.class, MODID + ".tileTank");
		GameRegistry.registerTileEntity(TileEntityFluidFurnace.class, MODID + ".tileFluidFurnace");
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
		fluidBlaze = new FluidBlaze(REGISTRY_BLOCKFLUIDBLAZE_NAME);
		FluidRegistry.registerFluid(fluidBlaze);

		NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());

		// Registry
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		// Buckets and stuff
		RecipeManager.registerRecipe(new ItemStack(itemFlowch), new ItemStack(itemTank));
		RecipeManager.registerRecipe(new ItemStack(Item.ingotIron), new ItemStack(Item.ingotGold), new ItemStack(itemCapsule));

	}

	/**
	 * Logs something to the console.
	 * 
	 * @param method
	 *            The method calling
	 * @param string
	 *            The message
	 */
	public static void log(String method, String string, World world) {
		String worldType = "CLIENT";
		if (world != null && world.isRemote)
			worldType = "SERVER";

		System.out.println("[" + method + "][" + worldType + "] --> " + string);
	}

	public static void log(String method, String string) {
		log(method, string, null);
	}
}
