package thaumcraft.api.crafting;

import thaumcraft.api.aspects.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import thaumcraft.api.*;
import net.minecraft.enchantment.*;
import java.util.*;
import net.minecraftforge.oredict.*;

public class InfusionEnchantmentRecipe
{
    public AspectList aspects;
    public String research;
    public ItemStack[] components;
    public Enchantment enchantment;
    public int recipeXP;
    public int instability;
    
    public InfusionEnchantmentRecipe(final String research, final Enchantment input, final int inst, final AspectList aspects2, final ItemStack[] recipe) {
        this.research = research;
        this.enchantment = input;
        this.aspects = aspects2;
        this.components = recipe;
        this.instability = inst;
        this.recipeXP = Math.max(1, input.getMinEnchantability(1) / 3);
    }
    
    public boolean matches(final ArrayList<ItemStack> input, final ItemStack central, final World world, final EntityPlayer player) {
        if (this.research.length() > 0 && !ThaumcraftApiHelper.isResearchComplete(player.getCommandSenderName(), this.research)) {
            return false;
        }
        if (!this.enchantment.canApply(central) || !central.getItem().isItemTool(central)) {
            return false;
        }
        final Map map1 = EnchantmentHelper.getEnchantments(central);
        for (final int j1 : map1.keySet()) {
            final Enchantment ench = Enchantment.enchantmentsList[j1];
            if (j1 == this.enchantment.effectId && EnchantmentHelper.getEnchantmentLevel(j1, central) >= ench.getMaxLevel()) {
                return false;
            }
            if (this.enchantment.effectId != ench.effectId && (!this.enchantment.canApplyTogether(ench) || !ench.canApplyTogether(this.enchantment))) {
                return false;
            }
        }
        ItemStack i2 = null;
        final ArrayList<ItemStack> ii = new ArrayList<ItemStack>();
        for (final ItemStack is : input) {
            ii.add(is.copy());
        }
        for (final ItemStack comp : this.components) {
            boolean b = false;
            for (int a = 0; a < ii.size(); ++a) {
                i2 = ii.get(a).copy();
                if (comp.getMetadata() == 32767) {
                    i2.setMetadata(32767);
                }
                if (this.areItemStacksEqual(i2, comp, true)) {
                    ii.remove(a);
                    b = true;
                    break;
                }
            }
            if (!b) {
                return false;
            }
        }
        return ii.size() == 0;
    }
    
    protected boolean areItemStacksEqual(final ItemStack stack0, final ItemStack stack1, final boolean fuzzy) {
        if (stack0 == null && stack1 != null) {
            return false;
        }
        if (stack0 != null && stack1 == null) {
            return false;
        }
        if (stack0 == null && stack1 == null) {
            return true;
        }
        final boolean t1 = ThaumcraftApiHelper.areItemStackTagsEqualForCrafting(stack0, stack1);
        if (!t1) {
            return false;
        }
        if (fuzzy) {
            final Integer od = OreDictionary.getOreID(stack0);
            if (od != -1) {
                final ItemStack[] ores = OreDictionary.getOres(od).toArray(new ItemStack[0]);
                if (ThaumcraftApiHelper.containsMatch(false, new ItemStack[] { stack1 }, ores)) {
                    return true;
                }
            }
        }
        return stack0.getItem() == stack1.getItem() && stack0.getMetadata() == stack1.getMetadata() && stack0.stackSize <= stack0.getMaxStackSize();
    }
    
    public Enchantment getEnchantment() {
        return this.enchantment;
    }
    
    public AspectList getAspects() {
        return this.aspects;
    }
    
    public String getResearch() {
        return this.research;
    }
    
    public int calcInstability(final ItemStack recipeInput) {
        int i = 0;
        final Map map1 = EnchantmentHelper.getEnchantments(recipeInput);
        for (final int j1 : map1.keySet()) {
            i += EnchantmentHelper.getEnchantmentLevel(j1, recipeInput);
        }
        return i / 2 + this.instability;
    }
    
    public int calcXP(final ItemStack recipeInput) {
        return this.recipeXP * (1 + EnchantmentHelper.getEnchantmentLevel(this.enchantment.effectId, recipeInput));
    }
    
    public float getEssentiaMod(final ItemStack recipeInput) {
        float mod = (float)EnchantmentHelper.getEnchantmentLevel(this.enchantment.effectId, recipeInput);
        final Map map1 = EnchantmentHelper.getEnchantments(recipeInput);
        for (final int j1 : map1.keySet()) {
            if (j1 != this.enchantment.effectId) {
                mod += EnchantmentHelper.getEnchantmentLevel(j1, recipeInput) * 0.1f;
            }
        }
        return mod;
    }
}
