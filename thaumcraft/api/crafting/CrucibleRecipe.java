package thaumcraft.api.crafting;

import net.minecraft.item.*;
import net.minecraftforge.oredict.*;
import thaumcraft.api.aspects.*;
import thaumcraft.api.*;
import java.util.*;

public class CrucibleRecipe
{
    private ItemStack recipeOutput;
    public Object catalyst;
    public AspectList aspects;
    public String key;
    public int hash;
    
    public CrucibleRecipe(final String researchKey, final ItemStack result, final Object cat, final AspectList tags) {
        this.recipeOutput = result;
        this.aspects = tags;
        this.key = researchKey;
        this.catalyst = cat;
        if (cat instanceof String) {
            this.catalyst = OreDictionary.getOres((String)cat);
        }
        String hc = researchKey + result.toString();
        for (final Aspect tag : tags.getAspects()) {
            hc = hc + tag.getTag() + tags.getAmount(tag);
        }
        if (cat instanceof ItemStack) {
            hc += ((ItemStack)cat).toString();
        }
        else if (cat instanceof ArrayList && ((ArrayList)this.catalyst).size() > 0) {
            for (final ItemStack is : (ArrayList)this.catalyst) {
                hc += is.toString();
            }
        }
        this.hash = hc.hashCode();
    }
    
    public boolean matches(final AspectList itags, final ItemStack cat) {
        if (this.catalyst instanceof ItemStack && !ThaumcraftApiHelper.itemMatches((ItemStack)this.catalyst, cat, false)) {
            return false;
        }
        if (this.catalyst instanceof ArrayList && ((ArrayList)this.catalyst).size() > 0) {
            final ItemStack[] ores = ((ArrayList)this.catalyst).toArray(new ItemStack[0]);
            if (!ThaumcraftApiHelper.containsMatch(false, new ItemStack[] { cat }, ores)) {
                return false;
            }
        }
        if (itags == null) {
            return false;
        }
        for (final Aspect tag : this.aspects.getAspects()) {
            if (itags.getAmount(tag) < this.aspects.getAmount(tag)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean catalystMatches(final ItemStack cat) {
        if (this.catalyst instanceof ItemStack && ThaumcraftApiHelper.itemMatches((ItemStack)this.catalyst, cat, false)) {
            return true;
        }
        if (this.catalyst instanceof ArrayList && ((ArrayList)this.catalyst).size() > 0) {
            final ItemStack[] ores = ((ArrayList)this.catalyst).toArray(new ItemStack[0]);
            if (ThaumcraftApiHelper.containsMatch(false, new ItemStack[] { cat }, ores)) {
                return true;
            }
        }
        return false;
    }
    
    public AspectList removeMatching(AspectList itags) {
        final AspectList temptags = new AspectList();
        temptags.aspects.putAll((Map<?, ?>)itags.aspects);
        for (final Aspect tag : this.aspects.getAspects()) {
            temptags.remove(tag, this.aspects.getAmount(tag));
        }
        itags = temptags;
        return itags;
    }
    
    public ItemStack getRecipeOutput() {
        return this.recipeOutput;
    }
}
