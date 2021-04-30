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
        this.setUnlocalizedName("ArcaneEngineering.impulse_boots");
        this.setCreativeTab(ArcaneEngineering.tabArcaneEngineering);
        GameRegistry.registerItem((Item)this, "impulse_boots");
    }
    
    public String getArmorTexture(final ItemStack stack, final Entity entity, final int slot, final String type) {
        return "arcane_engineering:textures/models/rocketboots.png";
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister ir) {
        this.icon = ir.registerIcon("arcane_engineering:impulse_boots");
    }
    
    public IIcon getIconFromDamage(final int meta) {
        return this.icon;
    }
    
    @SideOnly(Side.CLIENT)
    public void onArmorTick(final World world, final EntityPlayer player, final ItemStack itemStack) {
        if (GameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindJump) && this.getFluid(itemStack) != null && this.getFluid(itemStack).amount > 0 && !player.isInWater()) {
            if (GameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindSneak)) {
                if (player.motionY > -0.25 && player.motionY < 0.2) {
                    player.motionY = 0.0;
                }
                else if (player.motionY < 0.0) {
                    player.motionY *= 0.7;
                }
            }
            else if (player.motionY < -0.9) {
                player.motionY *= 0.6;
            }
            else {
                player.motionY += 0.1;
            }
            if (player.motionY > -0.6) {
                ArcaneEngineering.network.sendToServer((IMessage)new FallDamageMessage());
            }
            final World worldObj = player.worldObj;
            final Random rand = new Random();
            if (worldObj.isRemote) {
                final double motionX = rand.nextGaussian() * 0.02;
                final double motionY = rand.nextGaussian() * -0.2 - 0.3;
                final double motionZ = rand.nextGaussian() * 0.02;
            }
            ArcaneEngineering.network.sendToServer((IMessage)new BootParticleMessage(player.posX, player.posY, player.posZ));
            this.drain(itemStack, 1, rand.nextBoolean());
        }
    }
    
    public void addInformation(final ItemStack stack, final EntityPlayer player, final List list, final boolean adv) {
        final FluidStack fs = this.getFluid(stack);
        if (fs != null) {
            list.add(StatCollector.translateToLocal("desc.ImmersiveEngineering.flavour.drill.fuel") + " " + fs.amount + "/" + this.getCapacity(stack) + "mB");
        }
        else {
            list.add(StatCollector.translateToLocal("desc.ImmersiveEngineering.flavour.drill.fuel") + " 0/" + this.getCapacity(stack) + "mB");
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
