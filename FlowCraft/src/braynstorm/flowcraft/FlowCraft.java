package braynstorm.flowcraft;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import braynstorm.flowcraft.block.BlockTank;
import braynstorm.flowcraft.item.ItemFlowch;
import braynstorm.flowcraft.item.ItemTank;
import braynstorm.flowcraft.render.RendererTank;
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
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod (modid = FlowCraft.MODID, name = FlowCraft.MODNAME, version = FlowCraft.VERSION)
@NetworkMod (clientSideRequired = true, serverSideRequired = true)
public class FlowCraft {

	public static final String	MODID						= "flowcraft";
	public static final String	MODNAME						= "FlowCraft";
	public static final String	VERSION						= "Pre-Alpha 0.0";
	public static final String	REGISTRY_BLOCKTANK_NAME		= "blockTank";
	public static final String	REGISTRY_BLOCKTANK_KEY		= MODID + ":" + REGISTRY_BLOCKTANK_NAME;
	public static final String	REGISTRY_ITEMFLOWCH_NAME	= "itemFlowch";
	public static final String	REGISTRY_ITEMFLOWCH_KEY		= MODID + ":" + REGISTRY_ITEMFLOWCH_NAME;
	public static final String	REGISTRY_ITEMTANK_NAME		= "itemTank";
	public static final String	REGISTRY_ITEMTANK_KEY		= MODID + ":" + REGISTRY_ITEMTANK_NAME;

	public static CreativeTabs	creativeTab;
	public static final int		REGISTRY_BLOCKTANK_ID		= 3800;
	public static final int		REGISTRY_ITEMFLOWCH_ID		= 4200;
	public static final int		REGISTRY_ITEMTANK_ID		= REGISTRY_BLOCKTANK_ID - 256;


	public static BlockTank		blockTank;

	public static Item			itemFlowch;
	public static Item			itemTank;


	@Instance (MODID)
	public static FlowCraft		instance;

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

		// TODO render fluid!
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTank.class, new RendererTank());


		blockTank = new BlockTank(REGISTRY_BLOCKTANK_ID);
		Utils.registerBlock(blockTank, REGISTRY_BLOCKTANK_NAME, "Tank");

		itemFlowch = new ItemFlowch(REGISTRY_ITEMFLOWCH_ID);
		itemTank = new ItemTank(REGISTRY_ITEMTANK_ID);
		Utils.registerItem(itemFlowch, REGISTRY_ITEMFLOWCH_NAME, "Flowch");
		Utils.registerItem(itemTank, REGISTRY_ITEMTANK_NAME, "Tank");


		GameRegistry.registerTileEntity(TileEntityTank.class, "tileTankkkk");
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		// Buckets and stuff


	}

	/**
	 * Logs something to the console.
	 * 
	 * @param class The class of the log
	 * @param string
	 *            the message
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
