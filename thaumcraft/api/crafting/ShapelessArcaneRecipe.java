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

public class ShapelessArcaneRecipe implements IArcaneRecipe
{
    private ItemStack output;
    private ArrayList input;
    public AspectList aspects;
    public String research;
    
    public ShapelessArcaneRecipe(final String research, final Block result, final AspectList aspects, final Object... recipe) {
        this(research, new ItemStack(result), aspects, recipe);
    }
    
    public ShapelessArcaneRecipe(final String research, final Item result, final AspectList aspects, final Object... recipe) {
        this(research, new ItemStack(result), aspects, recipe);
    }
    
    public ShapelessArcaneRecipe(final String research, final ItemStack result, final AspectList aspects, final Object... recipe) {
        this.output = null;
        this.input = new ArrayList();
        this.aspects = null;
        this.output = result.copy();
        this.research = research;
        this.aspects = aspects;
        for (final Object in : recipe) {
            if (in instanceof ItemStack) {
                this.input.add(((ItemStack)in).copy());
            }
            else if (in instanceof Item) {
                this.input.add(new ItemStack((Item)in));
            }
            else if (in instanceof Block) {
                this.input.add(new ItemStack((Block)in));
            }
            else {
                if (!(in instanceof String)) {
                    String ret = "Invalid shapeless ore recipe: ";
                    for (final Object tmp : recipe) {
                        ret = ret + tmp + ", ";
                    }
                    ret += this.output;
                    throw new RuntimeException(ret);
                }
                this.input.add(OreDictionary.getOres((String)in));
            }
        }
    }
    
    @Override
    public int getRecipeSize() {
        return this.input.size();
    }
    
    @Override
    public ItemStack getRecipeOutput() {
        return this.output;
    }
    
    @Override
    public ItemStack getCraftingResult(final IInventory var1) {
        return this.output.copy();
    }
    
    @Override
    public boolean matches(final IInventory var1, final World world, final EntityPlayer player) {
        if (this.research.length() > 0 && !ThaumcraftApiHelper.isResearchComplete(player.getCommandSenderName(), this.research)) {
            return false;
        }
        final ArrayList required = new ArrayList(this.input);
        for (int x = 0; x < 9; ++x) {
            final ItemStack slot = var1.getStackInSlot(x);
            if (slot != null) {
                boolean inRecipe = false;
                final Iterator req = required.iterator();
                while (req.hasNext()) {
                    boolean match = false;
                    final Object next = req.next();
                    if (next instanceof ItemStack) {
                        match = this.checkItemEquals((ItemStack)next, slot);
                    }
                    else if (next instanceof ArrayList) {
                        for (final ItemStack item : (ArrayList)next) {
                            match = (match || this.checkItemEquals(item, slot));
                        }
                    }
                    if (match) {
                        inRecipe = true;
                        required.remove(next);
                        break;
                    }
                }
                if (!inRecipe) {
                    return false;
                }
            }
        }
        return required.isEmpty();
    }
    
    private boolean checkItemEquals(final ItemStack target, final ItemStack input) {
        return (input != null || target == null) && (input == null || target != null) && target.getItem() == input.getItem() && (!target.hasTagCompound() || ThaumcraftApiHelper.areItemStackTagsEqualForCrafting(input, target)) && (target.getMetadata() == 32767 || target.getMetadata() == input.getMetadata());
    }
    
    public ArrayList getInput() {
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
