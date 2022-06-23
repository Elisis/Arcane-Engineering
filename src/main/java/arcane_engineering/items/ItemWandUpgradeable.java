package arcane_engineering.items;

import thaumcraft.common.items.wands.*;
import cpw.mods.fml.common.registry.*;
import net.minecraft.creativetab.*;
import net.minecraft.item.*;
import arcane_engineering.*;
import thaumcraft.api.aspects.*;
import cpw.mods.fml.relauncher.*;
import thaumcraft.api.wands.*;
import net.minecraft.util.*;
import net.minecraft.inventory.*;
import blusunrize.immersiveengineering.common.gui.*;
import blusunrize.immersiveengineering.common.util.*;
import java.util.*;
import blusunrize.immersiveengineering.api.tool.*;
import net.minecraft.entity.player.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;

public class ItemWandUpgradeable extends ItemWandCasting implements IInternalStorageItem, IUpgradeableTool
{
    String upgradeType;
    
    public ItemWandUpgradeable() {
        this.setCreativeTab(ArcaneEngineering.tabArcaneEngineering);
        this.setUnlocalizedName("ArcaneEngineering.wand_upgradeable");
        GameRegistry.registerItem((Item)this, "wand_upgradeable");
        this.upgradeType = "WAND";
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubItems(final Item par1, final CreativeTabs par2CreativeTabs, final List par3List) {
        final ItemStack w1 = new ItemStack((Item)this, 1, 0);
        final ItemStack w2 = new ItemStack((Item)this, 1, 10);
        final ItemStack w3 = new ItemStack((Item)this, 1, 8);
        ((ItemWandCasting)w1.getItem()).setRod(w1, AEContent.WAND_ROD_UPGRADEABLE);
        ((ItemWandCasting)w2.getItem()).setCap(w2, AEContent.WAND_CAP_ELECTRUM);
        ((ItemWandCasting)w2.getItem()).setRod(w2, AEContent.WAND_ROD_UPGRADEABLE);
        ((ItemWandCasting)w3.getItem()).setCap(w3, AEContent.WAND_CAP_STEEL);
        ((ItemWandCasting)w3.getItem()).setRod(w3, AEContent.WAND_ROD_UPGRADEABLE);
        for (final Aspect aspect : Aspect.getPrimalAspects()) {
            ((ItemWandUpgradeable)w1.getItem()).addVis(w1, aspect, ((ItemWandCasting)w1.getItem()).getMaxVis(w1), true);
            ((ItemWandUpgradeable)w2.getItem()).addVis(w2, aspect, ((ItemWandCasting)w2.getItem()).getMaxVis(w2), true);
            ((ItemWandUpgradeable)w3.getItem()).addVis(w3, aspect, ((ItemWandCasting)w3.getItem()).getMaxVis(w3), true);
        }
        par3List.add(w1);
        par3List.add(w2);
        par3List.add(w3);
    }
    
    public WandRod getRod(final ItemStack stack) {
        return AEContent.WAND_ROD_UPGRADEABLE;
    }
    
    public String getItemStackDisplayName(final ItemStack is) {
        String name = StatCollector.translateToLocal("item.Wand.name");
        name = name.replace("%CAP", StatCollector.translateToLocal("item.Wand." + this.getCap(is).getTag() + ".cap"));
        String rod = this.getRod(is).getTag();
        if (rod.indexOf("_staff") >= 0) {
            rod = rod.substring(0, this.getRod(is).getTag().indexOf("_staff"));
        }
        name = name.replace("%ROD", StatCollector.translateToLocal("item.Wand." + rod + ".rod"));
        name = name.replace("%OBJ", this.isStaff(is) ? StatCollector.translateToLocal("item.Wand.staff.obj") : (this.isSceptre(is) ? StatCollector.translateToLocal("item.Wand.sceptre.obj") : StatCollector.translateToLocal("item.Wand.wand.obj")));
        return name;
    }
    
    public int getInternalSlots(final ItemStack stack) {
        if (this.getCap(stack) == AEContent.WAND_CAP_STEEL) {
            return 4;
        }
        if (this.getCap(stack) == AEContent.WAND_CAP_ELECTRUM) {
            return 3;
        }
        return 2;
    }
    
    public Slot[] getWorkbenchSlots(final Container container, final ItemStack stack, final IInventory invItem) {
        if (this.getCap(stack) == AEContent.WAND_CAP_STEEL) {
            return new Slot[] { (Slot)new IESlot.Upgrades(container, invItem, 0, 80, 32, "WAND", stack, false), (Slot)new IESlot.Upgrades(container, invItem, 1, 100, 32, "WAND", stack, false), (Slot)new IESlot.Upgrades(container, invItem, 2, 120, 32, "WAND", stack, false), (Slot)new IESlot.Upgrades(container, invItem, 3, 140, 32, "WAND", stack, false) };
        }
        if (this.getCap(stack) == AEContent.WAND_CAP_ELECTRUM) {
            return new Slot[] { (Slot)new IESlot.Upgrades(container, invItem, 0, 80, 32, "WAND", stack, false), (Slot)new IESlot.Upgrades(container, invItem, 1, 100, 32, "WAND", stack, false), (Slot)new IESlot.Upgrades(container, invItem, 2, 120, 32, "WAND", stack, false) };
        }
        return new Slot[] { (Slot)new IESlot.Upgrades(container, invItem, 0, 80, 32, "WAND", stack, false), (Slot)new IESlot.Upgrades(container, invItem, 1, 100, 32, "WAND", stack, false) };
    }
    
    public boolean canModify(final ItemStack arg0) {
        return true;
    }
    
    public boolean canTakeFromWorkbench(final ItemStack arg0) {
        return true;
    }
    
    public void clearUpgrades(final ItemStack stack) {
        ItemNBTHelper.remove(stack, "upgrades");
    }
    
    public NBTTagCompound getUpgrades(final ItemStack stack) {
        return ItemNBTHelper.getTagCompound(stack, "upgrades");
    }
    
    public NBTTagCompound getUpgradeBase(final ItemStack stack) {
        return ItemNBTHelper.getTagCompound(stack, "baseUpgrades");
    }
    
    public void recalculateUpgrades(final ItemStack stack) {
        this.clearUpgrades(stack);
        final ItemStack[] inv = this.getContainedItems(stack);
        final HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < inv.length; ++i) {
            final ItemStack u = inv[i];
            if (u != null && u.getItem() instanceof IUpgrade) {
                final IUpgrade upg = (IUpgrade)u.getItem();
                if (upg.getUpgradeTypes(u).contains(this.upgradeType) && upg.canApplyUpgrades(stack, u)) {
                    upg.applyUpgrades(stack, u, (HashMap)map);
                }
            }
        }
        final NBTTagCompound upgradeTag = (NBTTagCompound)this.getUpgradeBase(stack).copy();
        for (final String key : map.keySet()) {
            final Object o = map.get(key);
            if (o instanceof Byte) {
                upgradeTag.setByte(key, (byte)o);
            }
            else if (o instanceof byte[]) {
                upgradeTag.setByteArray(key, (byte[])o);
            }
            else if (o instanceof Boolean) {
                upgradeTag.setBoolean(key, (boolean)o);
            }
            else if (o instanceof Integer) {
                upgradeTag.setInteger(key, (int)o);
            }
            else if (o instanceof int[]) {
                upgradeTag.setIntArray(key, (int[])o);
            }
            else if (o instanceof Float) {
                upgradeTag.setFloat(key, (float)o);
            }
            else if (o instanceof Double) {
                upgradeTag.setDouble(key, (double)o);
            }
            else {
                if (!(o instanceof String)) {
                    continue;
                }
                upgradeTag.setString(key, (String)o);
            }
        }
        ItemNBTHelper.setTagCompound(stack, "upgrades", upgradeTag);
        for (final Aspect aspect : Aspect.getPrimalAspects()) {
            if (this.getVis(stack, aspect) > this.getMaxVis(stack)) {
                this.storeVis(stack, aspect, ((ItemWandUpgradeable)stack.getItem()).getMaxVis(stack));
            }
        }
    }
    
    public void removeFromWorkbench(final EntityPlayer arg0, final ItemStack stack) {
    }
    
    public ItemStack[] getContainedItems(final ItemStack stack) {
        final ItemStack[] stackList = new ItemStack[this.getInternalSlots(stack)];
        if (stack.hasTagCompound()) {
            final NBTTagList inv = stack.getTagCompound().getTagList("Inv", 10);
            for (int i = 0; i < inv.tagCount(); ++i) {
                final NBTTagCompound tag = inv.getCompoundTagAt(i);
                final int slot = tag.getByte("Slot") & 0xFF;
                if (slot >= 0 && slot < stackList.length) {
                    stackList[slot] = ItemStack.loadItemStackFromNBT(tag);
                }
            }
        }
        return stackList;
    }
    
    public void setContainedItems(final ItemStack stack, final ItemStack[] stackList) {
        final NBTTagList inv = new NBTTagList();
        for (int i = 0; i < stackList.length; ++i) {
            if (stackList[i] != null) {
                final NBTTagCompound tag = new NBTTagCompound();
                tag.setByte("Slot", (byte)i);
                stackList[i].writeToNBT(tag);
                inv.appendTag((NBTBase)tag);
            }
        }
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        stack.getTagCompound().setTag("Inv", (NBTBase)inv);
    }
    
    public int getMaxVis(final ItemStack stack) {
        return (int)(this.getRod(stack).getCapacity() * 100 * Math.pow(1.5, this.getUpgrades(stack).getInteger("capacitors")));
    }
    
    public void onUpdate(final ItemStack is, final World w, final Entity e, final int slot, final boolean currentItem) {
        super.onUpdate(is, w, e, slot, currentItem);
        if (this.getUpgrades(is).getInteger("chargers") > 0 && e.ticksExisted % (80 / this.getUpgrades(is).getInteger("chargers")) == 0) {
            for (final Aspect aspect : Aspect.getPrimalAspects()) {
                if (this.getVis(is, aspect) < this.getMaxVis(is)) {
                    this.addVis(is, aspect, 1, true);
                    break;
                }
            }
        }
    }
    
    public int getFocusPotency(final ItemStack itemstack) {
        return super.getFocusPotency(itemstack) + this.getUpgrades(itemstack).getInteger("energizers");
    }
    
    public float getConsumptionModifier(final ItemStack is, final EntityPlayer player, final Aspect aspect, final boolean crafting) {
        return super.getConsumptionModifier(is, player, aspect, crafting) + 0.1f * this.getUpgrades(is).getInteger("energizers");
    }

	@Override
	public void finishUpgradeRecalculation(ItemStack arg0) {
		// TODO Auto-generated method stub
		
	}
}
