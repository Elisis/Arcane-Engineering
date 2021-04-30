package arcane_engineering;

import thaumcraft.api.crafting.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import thaumcraft.api.*;
import arcane_engineering.items.*;
import java.util.*;
import thaumcraft.api.wands.*;
import thaumcraft.api.aspects.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;

public class UpgradeableWandRecipe implements IArcaneRecipe
{
    @Override
    public ItemStack getCraftingResult(final IInventory inv) {
        ItemStack out = null;
        String bc = null;
        int cc = 0;
        int cr = 0;
        final ItemStack cap1 = ThaumcraftApiHelper.getStackInRowAndColumn(inv, 0, 2);
        final ItemStack cap2 = ThaumcraftApiHelper.getStackInRowAndColumn(inv, 2, 0);
        final ItemStack rod = ThaumcraftApiHelper.getStackInRowAndColumn(inv, 1, 1);
        if (ThaumcraftApiHelper.getStackInRowAndColumn(inv, 0, 0) != null || ThaumcraftApiHelper.getStackInRowAndColumn(inv, 0, 1) != null || ThaumcraftApiHelper.getStackInRowAndColumn(inv, 1, 0) != null || ThaumcraftApiHelper.getStackInRowAndColumn(inv, 1, 2) != null || ThaumcraftApiHelper.getStackInRowAndColumn(inv, 2, 1) != null || ThaumcraftApiHelper.getStackInRowAndColumn(inv, 2, 2) != null) {
            return null;
        }
        if (cap1 != null && cap2 != null && rod != null && this.checkItemEquals(cap1, cap2)) {
            for (final WandCap wc : WandCap.caps.values()) {
                if (!this.checkItemEquals(cap1, wc.getItem())) {
                    continue;
                }
                bc = wc.getTag();
                cc = wc.getCraftCost();
                break;
            }
            final WandRod wr = AEContent.WAND_ROD_UPGRADEABLE;
            if (!this.checkItemEquals(rod, wr.getItem())) {
                return null;
            }
            cr = wr.getCraftCost();
            if (bc != null) {
                final int cost = cc * cr;
                out = new ItemStack(AEContent.wandUpgradeable, 1, cost);
                ((ItemWandUpgradeable)out.getItem()).setCap(out, (WandCap)WandCap.caps.get(bc));
                ((ItemWandUpgradeable)out.getItem()).setRod(out, AEContent.WAND_ROD_UPGRADEABLE);
            }
        }
        return out;
    }
    
    @Override
    public AspectList getAspects(final IInventory inv) {
        final AspectList al = new AspectList();
        int cc = -1;
        int cr = -1;
        final ItemStack cap1 = ThaumcraftApiHelper.getStackInRowAndColumn(inv, 0, 2);
        final ItemStack cap2 = ThaumcraftApiHelper.getStackInRowAndColumn(inv, 2, 0);
        final ItemStack rod = ThaumcraftApiHelper.getStackInRowAndColumn(inv, 1, 1);
        if (ThaumcraftApiHelper.getStackInRowAndColumn(inv, 0, 0) != null || ThaumcraftApiHelper.getStackInRowAndColumn(inv, 0, 1) != null || ThaumcraftApiHelper.getStackInRowAndColumn(inv, 1, 0) != null || ThaumcraftApiHelper.getStackInRowAndColumn(inv, 1, 2) != null || ThaumcraftApiHelper.getStackInRowAndColumn(inv, 2, 1) != null || ThaumcraftApiHelper.getStackInRowAndColumn(inv, 2, 2) != null) {
            return al;
        }
        if (cap1 != null && cap2 != null && rod != null && this.checkItemEquals(cap1, cap2)) {
            for (final WandCap wc : WandCap.caps.values()) {
                if (!this.checkItemEquals(cap1, wc.getItem())) {
                    continue;
                }
                cc = wc.getCraftCost();
                break;
            }
            final WandRod wr = AEContent.WAND_ROD_UPGRADEABLE;
            if (!this.checkItemEquals(rod, wr.getItem())) {
                return null;
            }
            cr = wr.getCraftCost();
            if (cc >= 0 && cr >= 0) {
                final int cost = cc * cr;
                for (final Aspect as : Aspect.getPrimalAspects()) {
                    al.add(as, cost);
                }
            }
        }
        return al;
    }
    
    @Override
    public ItemStack getRecipeOutput() {
        return null;
    }
    
    @Override
    public boolean matches(final IInventory inv, final World world, final EntityPlayer player) {
        final ItemStack cap1 = ThaumcraftApiHelper.getStackInRowAndColumn(inv, 0, 2);
        final ItemStack cap2 = ThaumcraftApiHelper.getStackInRowAndColumn(inv, 2, 0);
        final ItemStack rod = ThaumcraftApiHelper.getStackInRowAndColumn(inv, 1, 1);
        return ThaumcraftApiHelper.getStackInRowAndColumn(inv, 0, 0) == null && ThaumcraftApiHelper.getStackInRowAndColumn(inv, 0, 1) == null && ThaumcraftApiHelper.getStackInRowAndColumn(inv, 1, 0) == null && ThaumcraftApiHelper.getStackInRowAndColumn(inv, 1, 2) == null && ThaumcraftApiHelper.getStackInRowAndColumn(inv, 2, 1) == null && ThaumcraftApiHelper.getStackInRowAndColumn(inv, 2, 2) == null && this.checkMatch(cap1, cap2, rod, player);
    }
    
    private boolean checkMatch(final ItemStack cap1, final ItemStack cap2, final ItemStack rod, final EntityPlayer player) {
        boolean bc = false;
        boolean br = false;
        if (cap1 != null && cap2 != null && rod != null && this.checkItemEquals(cap1, cap2)) {
            for (final WandCap wc : WandCap.caps.values()) {
                if (this.checkItemEquals(cap1, wc.getItem())) {
                    if (!ThaumcraftApiHelper.isResearchComplete(player.getCommandSenderName(), wc.getResearch())) {
                        continue;
                    }
                    bc = true;
                    break;
                }
            }
            final WandRod wr = AEContent.WAND_ROD_UPGRADEABLE;
            if (this.checkItemEquals(rod, wr.getItem()) && ThaumcraftApiHelper.isResearchComplete(player.getCommandSenderName(), wr.getResearch())) {
                br = true;
            }
        }
        return br && bc;
    }
    
    private boolean checkItemEquals(final ItemStack target, final ItemStack input) {
        return (input != null || target == null) && (input == null || target != null) && target.getItem() == input.getItem() && (!target.hasTagCompound() || ItemStack.areItemStackTagsEqual(target, input)) && (target.getMetadata() == 32767 || target.getMetadata() == input.getMetadata());
    }
    
    @Override
    public int getRecipeSize() {
        return 9;
    }
    
    @Override
    public AspectList getAspects() {
        return null;
    }
    
    @Override
    public String getResearch() {
        return "";
    }
}
