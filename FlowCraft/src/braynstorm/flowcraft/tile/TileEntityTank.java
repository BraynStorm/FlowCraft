package braynstorm.flowcraft.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.TileFluidHandler;
import braynstorm.flowcraft.FlowCraft;
import braynstorm.flowcraft.tank.TankFlowTank;

public class TileEntityTank extends TileFluidHandler {

	private TankFlowTank	fluidTank	= new TankFlowTank(4000);

	// private Packet132TileEntityData packet

	public TileEntityTank() {
		super();
		this.fluidTank.setTile(this);
		// this.tank.fill(new FluidStack(FluidRegistry.LAVA, 2000), true);
	}

	@Override
	public void updateEntity() {

	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);

		tag.setInteger("tankFluidID", this.fluidTank.getFluidID());
		tag.setInteger("tankFluidAmount", this.fluidTank.getFluidAmount());

		FlowCraft.log("readFromNBT", "TankFluidID: " + tag.getInteger("tankFluidID"), this.worldObj);
		FlowCraft.log("readFromNBT", "TankFluidAmount: " + tag.getInteger("tankFluidAmount"), this.worldObj);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);

		this.fluidTank.fill(new FluidStack(tag.getInteger("tankFluidID"), tag.getInteger("tankFluidAmount")), true);

		FlowCraft.log("readFromNBT", "TankFluidID: " + tag.getInteger("tankFluidID"), this.worldObj);
		FlowCraft.log("readFromNBT", "TankFluidAmount: " + tag.getInteger("tankFluidAmount"), this.worldObj);
	}

	@Override
	public Packet132TileEntityData getDescriptionPacket() {
		FlowCraft.log("getDescriptionPacket", "", this.worldObj);
		NBTTagCompound nbtTag = new NBTTagCompound();
		this.writeToNBT(nbtTag);
		return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, nbtTag);
	}

	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData packet) {
		FlowCraft.log("onDataPacket", "", this.worldObj);
		this.readFromNBT(packet.data);
		this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
	}


	public TankFlowTank getTank() {
		return this.fluidTank;
	}

	@Override
	public int fill(ForgeDirection dir, FluidStack resource, boolean doFill) {
		return this.fluidTank.fill(resource, doFill);
	}

	@Override
	public FluidStack drain(ForgeDirection dir, int amount, boolean doDrain) {
		return this.fluidTank.drain(amount, doDrain);
	}


}
