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
        this.func_77637_a(ArcaneEngineering.tabArcaneEngineering);
        this.func_77655_b("ArcaneEngineering.wand_upgradeable");
        GameRegistry.registerItem((Item)this, "wand_upgradeable");
        this.upgradeType = "WAND";
    }
    
    @SideOnly(Side.CLIENT)
    public void func_150895_a(final Item par1, final CreativeTabs par2CreativeTabs, final List par3List) {
        final ItemStack w1 = new ItemStack((Item)this, 1, 0);
        final ItemStack w2 = new ItemStack((Item)this, 1, 10);
        final ItemStack w3 = new ItemStack((Item)this, 1, 8);
        ((ItemWandCasting)w1.func_77973_b()).setRod(w1, AEContent.WAND_ROD_UPGRADEABLE);
        ((ItemWandCasting)w2.func_77973_b()).setCap(w2, AEContent.WAND_CAP_ELECTRUM);
        ((ItemWandCasting)w2.func_77973_b()).setRod(w2, AEContent.WAND_ROD_UPGRADEABLE);
        ((ItemWandCasting)w3.func_77973_b()).setCap(w3, AEContent.WAND_CAP_STEEL);
        ((ItemWandCasting)w3.func_77973_b()).setRod(w3, AEContent.WAND_ROD_UPGRADEABLE);
        for (final Aspect aspect : Aspect.getPrimalAspects()) {
            ((ItemWandUpgradeable)w1.func_77973_b()).addVis(w1, aspect, ((ItemWandCasting)w1.func_77973_b()).getMaxVis(w1), true);
            ((ItemWandUpgradeable)w2.func_77973_b()).addVis(w2, aspect, ((ItemWandCasting)w2.func_77973_b()).getMaxVis(w2), true);
            ((ItemWandUpgradeable)w3.func_77973_b()).addVis(w3, aspect, ((ItemWandCasting)w3.func_77973_b()).getMaxVis(w3), true);
        }
        par3List.add(w1);
        par3List.add(w2);
        par3List.add(w3);
    }
    
    public WandRod getRod(final ItemStack stack) {
        return AEContent.WAND_ROD_UPGRADEABLE;
    }
    
    public String func_77653_i(final ItemStack is) {
        String name = StatCollector.func_74838_a("item.Wand.name");
        name = name.replace("%CAP", StatCollector.func_74838_a("item.Wand." + this.getCap(is).getTag() + ".cap"));
        String rod = this.getRod(is).getTag();
        if (rod.indexOf("_staff") >= 0) {
            rod = rod.substring(0, this.getRod(is).getTag().indexOf("_staff"));
        }
        name = name.replace("%ROD", StatCollector.func_74838_a("item.Wand." + rod + ".rod"));
        name = name.replace("%OBJ", this.isStaff(is) ? StatCollector.func_74838_a("item.Wand.staff.obj") : (this.isSceptre(is) ? StatCollector.func_74838_a("item.Wand.sceptre.obj") : StatCollector.func_74838_a("item.Wand.wand.obj")));
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
            if (u != null && u.func_77973_b() instanceof IUpgrade) {
                final IUpgrade upg = (IUpgrade)u.func_77973_b();
                if (upg.getUpgradeTypes(u).contains(this.upgradeType) && upg.canApplyUpgrades(stack, u)) {
                    upg.applyUpgrades(stack, u, (HashMap)map);
                }
            }
        }
        final NBTTagCompound upgradeTag = (NBTTagCompound)this.getUpgradeBase(stack).func_74737_b();
        for (final String key : map.keySet()) {
            final Object o = map.get(key);
            if (o instanceof Byte) {
                upgradeTag.func_74774_a(key, (byte)o);
            }
            else if (o instanceof byte[]) {
                upgradeTag.func_74773_a(key, (byte[])o);
            }
            else if (o instanceof Boolean) {
                upgradeTag.func_74757_a(key, (boolean)o);
            }
            else if (o instanceof Integer) {
                upgradeTag.func_74768_a(key, (int)o);
            }
            else if (o instanceof int[]) {
                upgradeTag.func_74783_a(key, (int[])o);
            }
            else if (o instanceof Float) {
                upgradeTag.func_74776_a(key, (float)o);
            }
            else if (o instanceof Double) {
                upgradeTag.func_74780_a(key, (double)o);
            }
            else {
                if (!(o instanceof String)) {
                    continue;
                }
                upgradeTag.func_74778_a(key, (String)o);
            }
        }
        ItemNBTHelper.setTagCompound(stack, "upgrades", upgradeTag);
        for (final Aspect aspect : Aspect.getPrimalAspects()) {
            if (this.getVis(stack, aspect) > this.getMaxVis(stack)) {
                this.storeVis(stack, aspect, ((ItemWandUpgradeable)stack.func_77973_b()).getMaxVis(stack));
            }
        }
    }
    
    public void removeFromWorkbench(final EntityPlayer arg0, final ItemStack stack) {
    }
    
    public ItemStack[] getContainedItems(final ItemStack stack) {
        final ItemStack[] stackList = new ItemStack[this.getInternalSlots(stack)];
        if (stack.func_77942_o()) {
            final NBTTagList inv = stack.func_77978_p().func_150295_c("Inv", 10);
            for (int i = 0; i < inv.func_74745_c(); ++i) {
                final NBTTagCompound tag = inv.func_150305_b(i);
                final int slot = tag.func_74771_c("Slot") & 0xFF;
                if (slot >= 0 && slot < stackList.length) {
                    stackList[slot] = ItemStack.func_77949_a(tag);
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
                tag.func_74774_a("Slot", (byte)i);
                stackList[i].func_77955_b(tag);
                inv.func_74742_a((NBTBase)tag);
            }
        }
        if (!stack.func_77942_o()) {
            stack.func_77982_d(new NBTTagCompound());
        }
        stack.func_77978_p().func_74782_a("Inv", (NBTBase)inv);
    }
    
    public int getMaxVis(final ItemStack stack) {
        return (int)(this.getRod(stack).getCapacity() * 100 * Math.pow(1.5, this.getUpgrades(stack).func_74762_e("capacitors")));
    }
    
    public void func_77663_a(final ItemStack is, final World w, final Entity e, final int slot, final boolean currentItem) {
        super.func_77663_a(is, w, e, slot, currentItem);
        if (this.getUpgrades(is).func_74762_e("chargers") > 0 && e.field_70173_aa % (80 / this.getUpgrades(is).func_74762_e("chargers")) == 0) {
            for (final Aspect aspect : Aspect.getPrimalAspects()) {
                if (this.getVis(is, aspect) < this.getMaxVis(is)) {
                    this.addVis(is, aspect, 1, true);
                    break;
                }
            }
        }
    }
    
    public int getFocusPotency(final ItemStack itemstack) {
        return super.getFocusPotency(itemstack) + this.getUpgrades(itemstack).func_74762_e("energizers");
    }
    
    public float getConsumptionModifier(final ItemStack is, final EntityPlayer player, final Aspect aspect, final boolean crafting) {
        return super.getConsumptionModifier(is, player, aspect, crafting) + 0.1f * this.getUpgrades(is).func_74762_e("energizers");
    }
}
