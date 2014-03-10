package braynstorm.flowcraft.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.TileFluidHandler;

public class TileEntityTank extends TileFluidHandler {

	private FluidTank	tank	= new FluidTank(4000);

	// private Packet132TileEntityData packet

	public TileEntityTank() {
		super();
		this.tank.fill(new FluidStack(FluidRegistry.LAVA, 2000), true);
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);

		FluidStack tankFluid = this.tank.getFluid();

		int[] details = new int[2];
		details[0] = tankFluid != null && tankFluid.fluidID != 0 ? tankFluid.fluidID : null;
		details[1] = this.tank.getFluidAmount();

		tag.setIntArray("tankDetails", details);

	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);

		int[] tankDetails = tag.getIntArray("tankDetails");

		if (tankDetails != null)
			this.tank.setFluid(new FluidStack(tankDetails[0], tankDetails[1]));
		else
			System.out.println("Couldnt read tile from NBT");
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
	}

}
