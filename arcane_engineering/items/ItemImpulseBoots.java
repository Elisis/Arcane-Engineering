package arcane_engineering.items;

import cpw.mods.fml.common.registry.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.texture.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.*;
import net.minecraft.client.settings.*;
import cpw.mods.fml.common.network.simpleimpl.*;
import arcane_engineering.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraftforge.fluids.*;
import blusunrize.immersiveengineering.common.util.*;
import blusunrize.immersiveengineering.common.*;

public class ItemImpulseBoots extends ItemArmor implements IFluidContainerItem
{
    IIcon icon;
    
    public ItemImpulseBoots() {
        super(AEContent.MECH, 0, 3);
        this.func_77655_b("ArcaneEngineering.impulse_boots");
        this.func_77637_a(ArcaneEngineering.tabArcaneEngineering);
        GameRegistry.registerItem((Item)this, "impulse_boots");
    }
    
    public String getArmorTexture(final ItemStack stack, final Entity entity, final int slot, final String type) {
        return "arcane_engineering:textures/models/rocketboots.png";
    }
    
    @SideOnly(Side.CLIENT)
    public void func_94581_a(final IIconRegister ir) {
        this.icon = ir.func_94245_a("arcane_engineering:impulse_boots");
    }
    
    public IIcon func_77617_a(final int meta) {
        return this.icon;
    }
    
    @SideOnly(Side.CLIENT)
    public void onArmorTick(final World world, final EntityPlayer player, final ItemStack itemStack) {
        if (GameSettings.func_100015_a(Minecraft.func_71410_x().field_71474_y.field_74314_A) && this.getFluid(itemStack) != null && this.getFluid(itemStack).amount > 0 && !player.func_70090_H()) {
            if (GameSettings.func_100015_a(Minecraft.func_71410_x().field_71474_y.field_74311_E)) {
                if (player.field_70181_x > -0.25 && player.field_70181_x < 0.2) {
                    player.field_70181_x = 0.0;
                }
                else if (player.field_70181_x < 0.0) {
                    player.field_70181_x *= 0.7;
                }
            }
            else if (player.field_70181_x < -0.9) {
                player.field_70181_x *= 0.6;
            }
            else {
                player.field_70181_x += 0.1;
            }
            if (player.field_70181_x > -0.6) {
                ArcaneEngineering.network.sendToServer((IMessage)new FallDamageMessage());
            }
            final World worldObj = player.field_70170_p;
            final Random rand = new Random();
            if (worldObj.field_72995_K) {
                final double motionX = rand.nextGaussian() * 0.02;
                final double motionY = rand.nextGaussian() * -0.2 - 0.3;
                final double motionZ = rand.nextGaussian() * 0.02;
            }
            ArcaneEngineering.network.sendToServer((IMessage)new BootParticleMessage(player.field_70165_t, player.field_70163_u, player.field_70161_v));
            this.drain(itemStack, 1, rand.nextBoolean());
        }
    }
    
    public void func_77624_a(final ItemStack stack, final EntityPlayer player, final List list, final boolean adv) {
        final FluidStack fs = this.getFluid(stack);
        if (fs != null) {
            list.add(StatCollector.func_74838_a("desc.ImmersiveEngineering.flavour.drill.fuel") + " " + fs.amount + "/" + this.getCapacity(stack) + "mB");
        }
        else {
            list.add(StatCollector.func_74838_a("desc.ImmersiveEngineering.flavour.drill.fuel") + " 0/" + this.getCapacity(stack) + "mB");
        }
    }
    
    public FluidStack getFluid(final ItemStack container) {
        return ItemNBTHelper.getFluidStack(container, "fuel");
    }
    
    public int getCapacity(final ItemStack container) {
        return 20000;
    }
    
    public int fill(final ItemStack container, final FluidStack resource, final boolean doFill) {
        if (resource != null && IEContent.fluidBiodiesel.equals(resource.getFluid())) {
            FluidStack fs = this.getFluid(container);
            final int space = (fs == null) ? this.getCapacity(container) : (this.getCapacity(container) - fs.amount);
            final int accepted = Math.min(space, resource.amount);
            if (fs == null) {
                fs = new FluidStack(IEContent.fluidBiodiesel, accepted);
            }
            else {
                final FluidStack fluidStack = fs;
                fluidStack.amount += accepted;
            }
            if (doFill) {
                ItemNBTHelper.setFluidStack(container, "fuel", fs);
            }
            return accepted;
        }
        return 0;
    }
    
    public FluidStack drain(final ItemStack container, final int maxDrain, final boolean doDrain) {
        final FluidStack fs = this.getFluid(container);
        if (fs == null) {
            return null;
        }
        final int drained = Math.min(maxDrain, fs.amount);
        final FluidStack stack = new FluidStack(fs, drained);
        if (doDrain) {
            final FluidStack fluidStack = fs;
            fluidStack.amount -= drained;
            if (fs.amount <= 0) {
                ItemNBTHelper.remove(container, "fuel");
            }
            else {
                ItemNBTHelper.setFluidStack(container, "fuel", fs);
            }
        }
        return stack;
    }
}
