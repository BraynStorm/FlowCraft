package braynstorm.flowcraft.render;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import braynstorm.flowcraft.block.BlockTank;
import braynstorm.flowcraft.model.TessellationManager;
import braynstorm.flowcraft.tank.TankFlowTank;
import braynstorm.flowcraft.tile.TileEntityTank;

public class RendererTank extends TileEntitySpecialRenderer {

	public RendererTank() {
		super();
	}

	@Override
	public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float f) {

		if (entity == null)
			return;

		TileEntityTank tankEntity = (TileEntityTank) entity;

		Tessellator tsl = Tessellator.instance;
		TessellationManager.setBaseCoords(x, y, z);

		BlockTank tankBlock = (BlockTank) tankEntity.getBlockType();

		Icon[] icons = tankBlock.getIcons();


		TankFlowTank tank = tankEntity.getTank();

		int brightness = tankBlock.getMixedBrightnessForBlock(tankEntity.worldObj, tankEntity.xCoord, tankEntity.yCoord, tankEntity.zCoord);
		// FlowCraft.log("renderTileEntityAt", "" + tank.getFluidAmount(), entity.getWorldObj());
		// System.out.println();
		tsl.setBrightness(brightness);
		this.bindTexture(TextureMap.locationBlocksTexture);
		tsl.startDrawingQuads();


		// if (tankEntity.getTank() == null || !tankEntity.getTank().hasFluid())


		if (tank.hasFluid()) {
			double fluidHeight = (tank.getFillPrecentage() * 16.0D / 100.0D) - 0.01D;
			double verticalTextureOffset = 16.0 / 100 * (100 - tank.getFillPrecentage());

			Icon fluidTexture = tank.getFluidTexture();
			this.renderPosXFace(tankEntity, fluidTexture, fluidHeight);
			this.renderNegXFace(tankEntity, fluidTexture, fluidHeight);
			this.renderPosYFace(tankEntity, fluidTexture, fluidHeight);
			this.renderNegYFace(tankEntity, fluidTexture, fluidHeight);
			this.renderPosZFace(tankEntity, fluidTexture, fluidHeight);
			this.renderNegZFace(tankEntity, fluidTexture, fluidHeight);
		}

		this.renderSolid(tankEntity, icons);

		tsl.draw();

	}

	private void renderSolid(TileEntityTank tankEntity, Icon[] icons) {
		// TessellationManager.renderPositiveXFace(0, 0, 0, 16, 16, icons[0]);
		// TessellationManager.renderNegativeXFace(0, 0, 0, 16, 16, icons[0]);
		// TessellationManager.renderPositiveYFace(0, 0, 0, 16, 16, icons[1]);
		// TessellationManager.renderNegativeYFace(0, 0, 0, 16, 16, icons[2]);
		// TessellationManager.renderPositiveZFace(0, 0, 0, 16, 16, icons[0]);
		// TessellationManager.renderNegativeZFace(0, 0, 0, 16, 16, icons[0]);
	}

	private void renderPosXFace(TileEntityTank tankEntity, Icon fluidTexture, double fluidHeight) {
		TessellationManager.renderNegativeXFace(0.01D, 0, 0, fluidHeight, 16, 0, 0, 16, fluidHeight, fluidTexture, TessellationManager.pixel);
	}

	private void renderNegXFace(TileEntityTank tankEntity, Icon fluidTexture, double fluidHeight) {
		TessellationManager.renderPositiveXFace(15.99D, 0, 0, fluidHeight, 16, 0, 0, 16, fluidHeight, fluidTexture, TessellationManager.pixel);

	}

	private void renderPosYFace(TileEntityTank tankEntity, Icon fluidTexture, double fluidHeight) {
		TessellationManager.renderPositiveYFace(0, fluidHeight, 0, 16, 16, fluidTexture);
	}

	private void renderNegYFace(TileEntityTank tankEntity, Icon fluidTexture, double fluidHeight) {
		TessellationManager.renderNegativeYFace(0, 0.01D, 0, 16, 16, fluidTexture);
	}

	private void renderPosZFace(TileEntityTank tankEntity, Icon fluidTexture, double fluidHeight) {
		TessellationManager.renderNegativeZFace(0, 0, 0.01D, 16, fluidHeight, 0, 0, 16, fluidHeight, fluidTexture, TessellationManager.pixel);
	}

	private void renderNegZFace(TileEntityTank tankEntity, Icon fluidTexture, double fluidHeight) {
		TessellationManager.renderPositiveZFace(0, 0, 15.99D, 16, fluidHeight, 0, 0, 16, fluidHeight, fluidTexture, TessellationManager.pixel);
	}

	/*
	 * private static void render(TileEntityTank tankEntity, Icon icons, Icon fluidTexture, double fluidHeight, double verticalTextureOffset, int face) { if
	 * (face == 0) TessellationManager.renderNegativeXFace(0.01D, 1, 1, 14, 14, fluidTexture); if (face == 1) TessellationManager.renderNegativeXFace(0.01D, 1,
	 * 1, 14, 14, fluidTexture); }
	 */

}
