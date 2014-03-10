package braynstorm.flowcraft.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.TileFluidHandler;
import braynstorm.flowcraft.tank.TankFlowTank;

public class TileEntityTank extends TileFluidHandler {

	private TankFlowTank	fluidTank	= new TankFlowTank(4000);

	// private Packet132TileEntityData packet

	public TileEntityTank() {
		super();
		// this.tank.fill(new FluidStack(FluidRegistry.LAVA, 2000), true);
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);


		int[] details = new int[2];

		if (this.fluidTank.hasFluid()) {
			FluidStack tankFluid = this.fluidTank.getFluid();

			details[0] = tankFluid != null && tankFluid.fluidID != 0 ? tankFluid.fluidID : null;
			details[1] = this.fluidTank.getFluidAmount();

			tag.setIntArray("tankDetails", details);
		}

	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);

		int[] tankDetails = tag.getIntArray("tankDetails");

		if (tankDetails != null && tankDetails.length == 2 && tankDetails[0] != 0 && tankDetails[1] != 0)
			this.fluidTank.fill(new FluidStack(tankDetails[0], tankDetails[1]), true);
		else
			System.out.println("Couldnt read tile from NBT");
	}

	@Override
	public Packet132TileEntityData getDescriptionPacket() {
		System.out.println("PacketSent");
		NBTTagCompound nbtTag = new NBTTagCompound();
		this.writeToNBT(nbtTag);
		return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, nbtTag);
	}

	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData packet) {
		System.out.println("PacketRecieved");
		this.readFromNBT(packet.data);
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
