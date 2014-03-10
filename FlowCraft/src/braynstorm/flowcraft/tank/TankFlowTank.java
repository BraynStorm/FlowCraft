package braynstorm.flowcraft.tank;

import net.minecraft.util.Icon;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

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
		// System.out.println("getting true texture");
		return this.fluid.getFluid().getIcon();

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
	public int getFillPrecentage() {
		if (this.hasFluid())
			return this.getFluidAmount() / this.capacity;
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
		if (this.fluid == null)
			return false;

		if (this.fluid.fluidID == 0)
			return false;

		if (this.fluid.amount == 0)
			return false;
		return true;
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {

		this.updateTankFluid();
		if (resource != null && resource.amount > 0)
			if (this.hasFluid() && resource.fluidID == this.getFluidID()) {

				int filled = Math.min(this.capacity - resource.amount, resource.amount);

				if (doFill)
					this.fluid.amount += filled;


				return filled;
			} else if (this.capacity > resource.amount) {
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

			FluidStack stack;

			if (this.capacity >= amount && this.fluid.amount >= amount) {
				stack = new FluidStack(this.getFluidID(), amount);

				if (doDrain)
					this.fluid = stack.copy();

			}

			this.updateTankFluid();
			return null;
		} else
			return null;
	}
}
