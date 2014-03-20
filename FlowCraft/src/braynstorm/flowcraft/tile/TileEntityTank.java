package braynstorm.flowcraft.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.TileFluidHandler;
import braynstorm.flowcraft.tank.TankFlowTank;

public class TileEntityTank extends TileFluidHandler {

	private TankFlowTank	fluidTank	= new TankFlowTank(8000);

	// private Packet132TileEntityData packet

	public TileEntityTank() {
		super();
		this.fluidTank.setTile(this);
		// this.tank.fill(new FluidStack(FluidRegistry.LAVA, 2000), true);
	}

	// @Override
	// public FluidTankInfo[] getTankInfo(ForgeDirection from) {
	// return new FluidTankInfo[] {
	// this.fluidTank.getInfo() };
	// }

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);

		tag.setInteger("tankFluidID", this.fluidTank.getFluidID());
		tag.setInteger("tankFluidAmount", this.fluidTank.getFluidAmount());

		// FlowCraft.log("writeFromNBT", "TankFluidID: " + tag.getInteger("tankFluidID"), this.worldObj);
		// FlowCraft.log("writeFromNBT", "TankFluidAmount: " + tag.getInteger("tankFluidAmount"), this.worldObj);
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection dir) {
		return new FluidTankInfo[] {
			this.fluidTank.getInfo() };
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		this.readFromNBTInit(tag);
	}

	public void readFromNBTInit(NBTTagCompound tag) {
		if (tag == null) {
			tag = new NBTTagCompound();
			tag.setInteger("tankFluidID", 0);
			tag.setInteger("tankFluidAmount", 0);
		}
		this.fluidTank.setFluid(new FluidStack(tag.getInteger("tankFluidID"), tag.getInteger("tankFluidAmount")));
	}

	@Override
	public Packet132TileEntityData getDescriptionPacket() {
		NBTTagCompound nbtTag = new NBTTagCompound();
		this.writeToNBT(nbtTag);
		return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, nbtTag);
	}

	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData packet) {
		this.readFromNBT(packet.data);
		this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
	}


	public TankFlowTank getTank() {
		return this.fluidTank;
	}

	public void updateTank() {
		// if (this.fluidTank.getFillPrecentage() > 0D && this.fluidTank.getFluid().fluidID == FluidRegistry.LAVA.getID())
		// this.worldObj.setLightValue(EnumSkyBlock.Block, this.xCoord, this.yCoord, this.zCoord, (int) (15 * this.fluidTank.getFillPrecentage() / 100));
		this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
	}

	@Override
	public int fill(ForgeDirection dir, FluidStack resource, boolean doFill) {
		int t = this.fluidTank.fill(resource, doFill);
		this.updateTank();
		return t;
	}

	@Override
	public FluidStack drain(ForgeDirection dir, int amount, boolean doDrain) {
		FluidStack t = this.fluidTank.drain(amount, doDrain);
		this.updateTank();
		return t;
	}


	public int getFluidAmount() {
		return this.fluidTank.getFluidAmount();
	}


}
