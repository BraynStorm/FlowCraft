package braynstorm.flowcraft.tank;

import net.minecraft.util.Icon;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import braynstorm.flowcraft.tile.TileEntityTank;

public class TankFlowTank extends FluidTank {

	// private FluidStack fluid;


	public TankFlowTank(FluidStack stack, int capacity) {
		super(stack, capacity);
		this.updateTankFluid();
	}

	public TankFlowTank(Fluid fluid, int amount, int capacity) {
		super(fluid, amount, capacity);
		this.updateTankFluid();
	}


	public TankFlowTank(int capacity) {
		super(capacity);
		this.updateTankFluid();
	}

	public Icon getFluidTexture() {


		if (!this.hasFluid())
			return null;
		// System.out.println(this.hasFluid());
		return this.fluid.getFluid().getIcon();

	}

	public void setTile(TileEntityTank tile) {
		this.tile = tile;
	}


	public int getFluidID() {
		if (this.hasFluid())
			return this.fluid.fluidID;
		return 0;
	}


	@Override
	public int getFluidAmount() {
		if (this.hasFluid())
			return this.fluid.amount;
		return 0;
	}

	/**
	 * @return Returns {@link int} (0-100).
	 */
	public double getFillPrecentage() {
		if (this.hasFluid())
			return (int) (((double) this.getFluidAmount() / this.capacity) * 100);
		return 0;
	}

	/**
	 * Updates the {@link FluidStack} of the {@link TankFlowTank}.
	 */
	public void updateTankFluid() {
		if (!this.hasFluid())
			this.fluid = null;
		return;
	}

	@Override
	public FluidTankInfo getInfo() {
		return new FluidTankInfo(this.fluid, this.capacity);
	}

	/**
	 * Checks if the tank has any fluid.
	 * 
	 * @return True if it has:
	 * 
	 *         <pre>
	 * A valid {@link FluidStack}.
	 * A valid {@link Fluid} id.
	 * Any amount of {@link Fluid} that is > 1
	 * </pre>
	 */
	public boolean hasFluid() {
		if (this.fluid != null && this.fluid.amount != 0 && this.fluid.fluidID != 0)
			return true;
		return false;
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {

		this.updateTankFluid();


		if (resource != null && resource.amount > 0)
			if (this.hasFluid() && resource.isFluidEqual(this.getFluid()) && this.capacity >= resource.amount) {
				// System.out.println(resource.fluidID + ",,,,," + this.getFluidID());
				int filled = 0;
				if (resource.amount + this.fluid.amount <= this.capacity)
					filled = resource.amount;


				if (doFill)
					this.fluid.amount += filled;
				// FlowCraft.log("FILL", resource.amount + "mB" + this.capacity, this.tile.worldObj);

				return filled;
			} else if (this.capacity >= resource.amount && !this.hasFluid()) {
				// System.out.println("asdasdsdaads" + resource.fluidID + "  " + this.getFluidID());
				if (doFill)
					this.fluid = resource.copy();
				return resource.amount;
			}
		this.updateTankFluid();
		return 0;

	}

	@Override
	public FluidStack drain(int amount, boolean doDrain) {
		if (this.hasFluid()) {

			FluidStack stack = null;

			if (this.capacity >= amount && this.fluid.amount >= amount) {
				stack = new FluidStack(this.getFluidID(), amount);

				if (doDrain)
					this.fluid.amount -= stack.amount;

			}

			this.updateTankFluid();
			return stack;
		} else
			return null;
	}
}
