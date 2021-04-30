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
        this.field_77777_bU = 1;
        this.canRepair = false;
        this.func_77656_e(0);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon func_77617_a(final int par1) {
        return this.icon;
    }
    
    public boolean func_77645_m() {
        return false;
    }
    
    public void func_77624_a(final ItemStack stack, final EntityPlayer player, final List list, final boolean par4) {
        final AspectList al = this.getVisCost(stack);
        if (al != null && al.size() > 0) {
            list.add(StatCollector.func_74838_a(this.isVisCostPerTick(stack) ? "item.Focus.cost2" : "item.Focus.cost1"));
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
            list.add(EnumChatFormatting.DARK_PURPLE + FocusUpgradeType.types[id2].getLocalizedName() + ((map.get(id2) > 1) ? (" " + StatCollector.func_74838_a("enchantment.level." + map.get(id2))) : ""));
        }
    }
    
    public boolean isVisCostPerTick(final ItemStack focusstack) {
        return false;
    }
    
    public EnumRarity func_77613_e(final ItemStack focusstack) {
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
        for (int j = 0; j < nbttaglist.func_74745_c() && j < 5; ++j) {
            l[j] = nbttaglist.func_150305_b(j).func_74765_d("id");
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
        return (focusstack.field_77990_d == null) ? null : focusstack.field_77990_d.func_150295_c("upgrade", 10);
    }
    
    private void setFocusUpgradeTagList(final ItemStack focusstack, final short[] upgrades) {
        if (!focusstack.func_77942_o()) {
            focusstack.func_77982_d(new NBTTagCompound());
        }
        final NBTTagCompound nbttagcompound = focusstack.func_77978_p();
        final NBTTagList tlist = new NBTTagList();
        nbttagcompound.func_74782_a("upgrade", (NBTBase)tlist);
        for (final short id : upgrades) {
            final NBTTagCompound f = new NBTTagCompound();
            f.func_74777_a("id", id);
            tlist.func_74742_a((NBTBase)f);
        }
    }
    
    public void func_77663_a(final ItemStack stack, final World world, final Entity entity, final int p_77663_4_, final boolean p_77663_5_) {
        if (stack.field_77990_d != null && stack.field_77990_d.func_74764_b("ench")) {
            stack.field_77990_d.func_82580_o("ench");
        }
        super.func_77663_a(stack, world, entity, p_77663_4_, p_77663_5_);
    }
    
    public enum WandFocusAnimation
    {
        WAVE, 
        CHARGE;
    }
}
