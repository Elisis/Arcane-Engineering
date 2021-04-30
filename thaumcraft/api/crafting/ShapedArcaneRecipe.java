package thaumcraft.api.crafting;

import thaumcraft.api.aspects.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraftforge.oredict.*;
import net.minecraft.inventory.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import thaumcraft.api.*;
import java.util.*;

public class ShapedArcaneRecipe implements IArcaneRecipe
{
    private static final int MAX_CRAFT_GRID_WIDTH = 3;
    private static final int MAX_CRAFT_GRID_HEIGHT = 3;
    public ItemStack output;
    public Object[] input;
    public AspectList aspects;
    public String research;
    public int width;
    public int height;
    private boolean mirrored;
    
    public ShapedArcaneRecipe(final String research, final Block result, final AspectList aspects, final Object... recipe) {
        this(research, new ItemStack(result), aspects, recipe);
    }
    
    public ShapedArcaneRecipe(final String research, final Item result, final AspectList aspects, final Object... recipe) {
        this(research, new ItemStack(result), aspects, recipe);
    }
    
    public ShapedArcaneRecipe(final String research, final ItemStack result, final AspectList aspects, Object... recipe) {
        this.output = null;
        this.input = null;
        this.aspects = null;
        this.width = 0;
        this.height = 0;
        this.mirrored = true;
        this.output = result.func_77946_l();
        this.research = research;
        this.aspects = aspects;
        String shape = "";
        int idx = 0;
        if (recipe[idx] instanceof Boolean) {
            this.mirrored = (boolean)recipe[idx];
            if (recipe[idx + 1] instanceof Object[]) {
                recipe = (Object[])recipe[idx + 1];
            }
            else {
                idx = 1;
            }
        }
        if (recipe[idx] instanceof String[]) {
            final String[] array;
            final String[] parts = array = (String[])recipe[idx++];
            for (final String s : array) {
                this.width = s.length();
                shape += s;
            }
            this.height = parts.length;
        }
        else {
            while (recipe[idx] instanceof String) {
                final String s2 = (String)recipe[idx++];
                shape += s2;
                this.width = s2.length();
                ++this.height;
            }
        }
        if (this.width * this.height != shape.length()) {
            String ret = "Invalid shaped ore recipe: ";
            for (final Object tmp : recipe) {
                ret = ret + tmp + ", ";
            }
            ret += this.output;
            throw new RuntimeException(ret);
        }
        final HashMap<Character, Object> itemMap = new HashMap<Character, Object>();
        while (idx < recipe.length) {
            final Character chr = (Character)recipe[idx];
            final Object in = recipe[idx + 1];
            if (in instanceof ItemStack) {
                itemMap.put(chr, ((ItemStack)in).func_77946_l());
            }
            else if (in instanceof Item) {
                itemMap.put(chr, new ItemStack((Item)in));
            }
            else if (in instanceof Block) {
                itemMap.put(chr, new ItemStack((Block)in, 1, 32767));
            }
            else {
                if (!(in instanceof String)) {
                    String ret2 = "Invalid shaped ore recipe: ";
                    for (final Object tmp2 : recipe) {
                        ret2 = ret2 + tmp2 + ", ";
                    }
                    ret2 += this.output;
                    throw new RuntimeException(ret2);
                }
                itemMap.put(chr, OreDictionary.getOres((String)in));
            }
            idx += 2;
        }
        this.input = new Object[this.width * this.height];
        int x = 0;
        for (final char chr2 : shape.toCharArray()) {
            this.input[x++] = itemMap.get(chr2);
        }
    }
    
    @Override
    public ItemStack getCraftingResult(final IInventory var1) {
        return this.output.func_77946_l();
    }
    
    @Override
    public int getRecipeSize() {
        return this.input.length;
    }
    
    @Override
    public ItemStack getRecipeOutput() {
        return this.output;
    }
    
    @Override
    public boolean matches(final IInventory inv, final World world, final EntityPlayer player) {
        if (this.research.length() > 0 && !ThaumcraftApiHelper.isResearchComplete(player.func_70005_c_(), this.research)) {
            return false;
        }
        for (int x = 0; x <= 3 - this.width; ++x) {
            for (int y = 0; y <= 3 - this.height; ++y) {
                if (this.checkMatch(inv, x, y, false)) {
                    return true;
                }
                if (this.mirrored && this.checkMatch(inv, x, y, true)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean checkMatch(final IInventory inv, final int startX, final int startY, final boolean mirror) {
        for (int x = 0; x < 3; ++x) {
            for (int y = 0; y < 3; ++y) {
                final int subX = x - startX;
                final int subY = y - startY;
                Object target = null;
                if (subX >= 0 && subY >= 0 && subX < this.width && subY < this.height) {
                    if (mirror) {
                        target = this.input[this.width - subX - 1 + subY * this.width];
                    }
                    else {
                        target = this.input[subX + subY * this.width];
                    }
                }
                final ItemStack slot = ThaumcraftApiHelper.getStackInRowAndColumn(inv, x, y);
                if (target instanceof ItemStack) {
                    if (!this.checkItemEquals((ItemStack)target, slot)) {
                        return false;
                    }
                }
                else if (target instanceof ArrayList) {
                    boolean matched = false;
                    for (final ItemStack item : (ArrayList)target) {
                        matched = (matched || this.checkItemEquals(item, slot));
                    }
                    if (!matched) {
                        return false;
                    }
                }
                else if (target == null && slot != null) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private boolean checkItemEquals(final ItemStack target, final ItemStack input) {
        return (input != null || target == null) && (input == null || target != null) && target.func_77973_b() == input.func_77973_b() && (!target.func_77942_o() || ThaumcraftApiHelper.areItemStackTagsEqualForCrafting(input, target)) && (target.func_77960_j() == 32767 || target.func_77960_j() == input.func_77960_j());
    }
    
    public ShapedArcaneRecipe setMirrored(final boolean mirror) {
        this.mirrored = mirror;
        return this;
    }
    
    public Object[] getInput() {
        return this.input;
    }
    
    @Override
    public AspectList getAspects() {
        return this.aspects;
    }
    
    @Override
    public AspectList getAspects(final IInventory inv) {
        return this.aspects;
    }
    
    @Override
    public String getResearch() {
        return this.research;
    }
}
