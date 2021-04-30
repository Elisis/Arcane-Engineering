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
        this.recipeXP = Math.max(1, input.func_77321_a(1) / 3);
    }
    
    public boolean matches(final ArrayList<ItemStack> input, final ItemStack central, final World world, final EntityPlayer player) {
        if (this.research.length() > 0 && !ThaumcraftApiHelper.isResearchComplete(player.func_70005_c_(), this.research)) {
            return false;
        }
        if (!this.enchantment.func_92089_a(central) || !central.func_77973_b().func_77616_k(central)) {
            return false;
        }
        final Map map1 = EnchantmentHelper.func_82781_a(central);
        for (final int j1 : map1.keySet()) {
            final Enchantment ench = Enchantment.field_77331_b[j1];
            if (j1 == this.enchantment.field_77352_x && EnchantmentHelper.func_77506_a(j1, central) >= ench.func_77325_b()) {
                return false;
            }
            if (this.enchantment.field_77352_x != ench.field_77352_x && (!this.enchantment.func_77326_a(ench) || !ench.func_77326_a(this.enchantment))) {
                return false;
            }
        }
        ItemStack i2 = null;
        final ArrayList<ItemStack> ii = new ArrayList<ItemStack>();
        for (final ItemStack is : input) {
            ii.add(is.func_77946_l());
        }
        for (final ItemStack comp : this.components) {
            boolean b = false;
            for (int a = 0; a < ii.size(); ++a) {
                i2 = ii.get(a).func_77946_l();
                if (comp.func_77960_j() == 32767) {
                    i2.func_77964_b(32767);
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
        return stack0.func_77973_b() == stack1.func_77973_b() && stack0.func_77960_j() == stack1.func_77960_j() && stack0.field_77994_a <= stack0.func_77976_d();
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
        final Map map1 = EnchantmentHelper.func_82781_a(recipeInput);
        for (final int j1 : map1.keySet()) {
            i += EnchantmentHelper.func_77506_a(j1, recipeInput);
        }
        return i / 2 + this.instability;
    }
    
    public int calcXP(final ItemStack recipeInput) {
        return this.recipeXP * (1 + EnchantmentHelper.func_77506_a(this.enchantment.field_77352_x, recipeInput));
    }
    
    public float getEssentiaMod(final ItemStack recipeInput) {
        float mod = (float)EnchantmentHelper.func_77506_a(this.enchantment.field_77352_x, recipeInput);
        final Map map1 = EnchantmentHelper.func_82781_a(recipeInput);
        for (final int j1 : map1.keySet()) {
            if (j1 != this.enchantment.field_77352_x) {
                mod += EnchantmentHelper.func_77506_a(j1, recipeInput) * 0.1f;
            }
        }
        return mod;
    }
}
