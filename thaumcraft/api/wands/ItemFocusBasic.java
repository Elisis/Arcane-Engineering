package thaumcraft.api.wands;

import cpw.mods.fml.relauncher.*;
import net.minecraft.entity.player.*;
import java.text.*;
import thaumcraft.api.aspects.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.*;

public class ItemFocusBasic extends Item
{
    public IIcon icon;
    
    public ItemFocusBasic() {
        this.maxStackSize = 1;
        this.canRepair = false;
        this.setMaxDurability(0);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(final int par1) {
        return this.icon;
    }
    
    public boolean isDamageable() {
        return false;
    }
    
    public void addInformation(final ItemStack stack, final EntityPlayer player, final List list, final boolean par4) {
        final AspectList al = this.getVisCost(stack);
        if (al != null && al.size() > 0) {
            list.add(StatCollector.translateToLocal(this.isVisCostPerTick(stack) ? "item.Focus.cost2" : "item.Focus.cost1"));
            for (final Aspect aspect : al.getAspectsSorted()) {
                final DecimalFormat myFormatter = new DecimalFormat("#####.##");
                final String amount = myFormatter.format(al.getAmount(aspect) / 100.0f);
                list.add(" §" + aspect.getChatcolor() + aspect.getName() + "§r x " + amount);
            }
        }
        this.addFocusInformation(stack, player, list, par4);
    }
    
    public void addFocusInformation(final ItemStack focusstack, final EntityPlayer player, final List list, final boolean par4) {
        final LinkedHashMap<Short, Integer> map = new LinkedHashMap<Short, Integer>();
        for (final short id : this.getAppliedUpgrades(focusstack)) {
            if (id >= 0) {
                int amt = 1;
                if (map.containsKey(id)) {
                    amt = map.get(id) + 1;
                }
                map.put(id, amt);
            }
        }
        for (final Short id2 : map.keySet()) {
            list.add(EnumChatFormatting.DARK_PURPLE + FocusUpgradeType.types[id2].getLocalizedName() + ((map.get(id2) > 1) ? (" " + StatCollector.translateToLocal("enchantment.level." + map.get(id2))) : ""));
        }
    }
    
    public boolean isVisCostPerTick(final ItemStack focusstack) {
        return false;
    }
    
    public EnumRarity getRarity(final ItemStack focusstack) {
        return EnumRarity.rare;
    }
    
    public int getFocusColor(final ItemStack focusstack) {
        return 0;
    }
    
    public IIcon getOrnament(final ItemStack focusstack) {
        return null;
    }
    
    public IIcon getFocusDepthLayerIcon(final ItemStack focusstack) {
        return null;
    }
    
    public WandFocusAnimation getAnimation(final ItemStack focusstack) {
        return WandFocusAnimation.WAVE;
    }
    
    public String getSortingHelper(final ItemStack focusstack) {
        String out = "";
        for (final short id : this.getAppliedUpgrades(focusstack)) {
            out += id;
        }
        return out;
    }
    
    public AspectList getVisCost(final ItemStack focusstack) {
        return null;
    }
    
    public int getActivationCooldown(final ItemStack focusstack) {
        return 0;
    }
    
    public int getMaxAreaSize(final ItemStack focusstack) {
        return 1;
    }
    
    public FocusUpgradeType[] getPossibleUpgradesByRank(final ItemStack focusstack, final int rank) {
        return null;
    }
    
    public short[] getAppliedUpgrades(final ItemStack focusstack) {
        final short[] l = { -1, -1, -1, -1, -1 };
        final NBTTagList nbttaglist = this.getFocusUpgradeTagList(focusstack);
        if (nbttaglist == null) {
            return l;
        }
        for (int j = 0; j < nbttaglist.tagCount() && j < 5; ++j) {
            l[j] = nbttaglist.getCompoundTagAt(j).getShort("id");
        }
        return l;
    }
    
    public boolean applyUpgrade(final ItemStack focusstack, final FocusUpgradeType type, final int rank) {
        final short[] upgrades = this.getAppliedUpgrades(focusstack);
        if (upgrades[rank - 1] != -1 || rank < 1 || rank > 5) {
            return false;
        }
        upgrades[rank - 1] = type.id;
        this.setFocusUpgradeTagList(focusstack, upgrades);
        return true;
    }
    
    public boolean canApplyUpgrade(final ItemStack focusstack, final EntityPlayer player, final FocusUpgradeType type, final int rank) {
        return true;
    }
    
    public boolean isUpgradedWith(final ItemStack focusstack, final FocusUpgradeType focusUpgradetype) {
        return this.getUpgradeLevel(focusstack, focusUpgradetype) > 0;
    }
    
    public int getUpgradeLevel(final ItemStack focusstack, final FocusUpgradeType focusUpgradetype) {
        final short[] list = this.getAppliedUpgrades(focusstack);
        int level = 0;
        for (final short id : list) {
            if (id == focusUpgradetype.id) {
                ++level;
            }
        }
        return level;
    }
    
    public ItemStack onFocusRightClick(final ItemStack wandstack, final World world, final EntityPlayer player, final MovingObjectPosition movingobjectposition) {
        return null;
    }
    
    public void onUsingFocusTick(final ItemStack wandstack, final EntityPlayer player, final int count) {
    }
    
    public void onPlayerStoppedUsingFocus(final ItemStack wandstack, final World world, final EntityPlayer player, final int count) {
    }
    
    public boolean onFocusBlockStartBreak(final ItemStack wandstack, final int x, final int y, final int z, final EntityPlayer player) {
        return false;
    }
    
    private NBTTagList getFocusUpgradeTagList(final ItemStack focusstack) {
        return (focusstack.stackTagCompound == null) ? null : focusstack.stackTagCompound.getTagList("upgrade", 10);
    }
    
    private void setFocusUpgradeTagList(final ItemStack focusstack, final short[] upgrades) {
        if (!focusstack.hasTagCompound()) {
            focusstack.setTagCompound(new NBTTagCompound());
        }
        final NBTTagCompound nbttagcompound = focusstack.getTagCompound();
        final NBTTagList tlist = new NBTTagList();
        nbttagcompound.setTag("upgrade", (NBTBase)tlist);
        for (final short id : upgrades) {
            final NBTTagCompound f = new NBTTagCompound();
            f.setShort("id", id);
            tlist.appendTag((NBTBase)f);
        }
    }
    
    public void onUpdate(final ItemStack stack, final World world, final Entity entity, final int p_77663_4_, final boolean p_77663_5_) {
        if (stack.stackTagCompound != null && stack.stackTagCompound.hasKey("ench")) {
            stack.stackTagCompound.removeTag("ench");
        }
        super.onUpdate(stack, world, entity, p_77663_4_, p_77663_5_);
    }
    
    public enum WandFocusAnimation
    {
        WAVE, 
        CHARGE;
    }
}
