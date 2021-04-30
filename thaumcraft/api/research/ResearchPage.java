package thaumcraft.api.research;

import thaumcraft.api.aspects.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.item.crafting.*;
import thaumcraft.api.crafting.*;
import net.minecraft.util.*;

public class ResearchPage
{
    public PageType type;
    public String text;
    public String research;
    public ResourceLocation image;
    public AspectList aspects;
    public Object recipe;
    public ItemStack recipeOutput;
    
    public ResearchPage(final String text) {
        this.type = PageType.TEXT;
        this.text = null;
        this.research = null;
        this.image = null;
        this.aspects = null;
        this.recipe = null;
        this.recipeOutput = null;
        this.type = PageType.TEXT;
        this.text = text;
    }
    
    public ResearchPage(final String research, final String text) {
        this.type = PageType.TEXT;
        this.text = null;
        this.research = null;
        this.image = null;
        this.aspects = null;
        this.recipe = null;
        this.recipeOutput = null;
        this.type = PageType.TEXT_CONCEALED;
        this.research = research;
        this.text = text;
    }
    
    public ResearchPage(final IRecipe recipe) {
        this.type = PageType.TEXT;
        this.text = null;
        this.research = null;
        this.image = null;
        this.aspects = null;
        this.recipe = null;
        this.recipeOutput = null;
        this.type = PageType.NORMAL_CRAFTING;
        this.recipe = recipe;
        this.recipeOutput = recipe.func_77571_b();
    }
    
    public ResearchPage(final IRecipe[] recipe) {
        this.type = PageType.TEXT;
        this.text = null;
        this.research = null;
        this.image = null;
        this.aspects = null;
        this.recipe = null;
        this.recipeOutput = null;
        this.type = PageType.NORMAL_CRAFTING;
        this.recipe = recipe;
    }
    
    public ResearchPage(final IArcaneRecipe[] recipe) {
        this.type = PageType.TEXT;
        this.text = null;
        this.research = null;
        this.image = null;
        this.aspects = null;
        this.recipe = null;
        this.recipeOutput = null;
        this.type = PageType.ARCANE_CRAFTING;
        this.recipe = recipe;
    }
    
    public ResearchPage(final CrucibleRecipe[] recipe) {
        this.type = PageType.TEXT;
        this.text = null;
        this.research = null;
        this.image = null;
        this.aspects = null;
        this.recipe = null;
        this.recipeOutput = null;
        this.type = PageType.CRUCIBLE_CRAFTING;
        this.recipe = recipe;
    }
    
    public ResearchPage(final InfusionRecipe[] recipe) {
        this.type = PageType.TEXT;
        this.text = null;
        this.research = null;
        this.image = null;
        this.aspects = null;
        this.recipe = null;
        this.recipeOutput = null;
        this.type = PageType.INFUSION_CRAFTING;
        this.recipe = recipe;
    }
    
    public ResearchPage(final List recipe) {
        this.type = PageType.TEXT;
        this.text = null;
        this.research = null;
        this.image = null;
        this.aspects = null;
        this.recipe = null;
        this.recipeOutput = null;
        this.type = PageType.COMPOUND_CRAFTING;
        this.recipe = recipe;
    }
    
    public ResearchPage(final IArcaneRecipe recipe) {
        this.type = PageType.TEXT;
        this.text = null;
        this.research = null;
        this.image = null;
        this.aspects = null;
        this.recipe = null;
        this.recipeOutput = null;
        this.type = PageType.ARCANE_CRAFTING;
        this.recipe = recipe;
        this.recipeOutput = recipe.getRecipeOutput();
    }
    
    public ResearchPage(final CrucibleRecipe recipe) {
        this.type = PageType.TEXT;
        this.text = null;
        this.research = null;
        this.image = null;
        this.aspects = null;
        this.recipe = null;
        this.recipeOutput = null;
        this.type = PageType.CRUCIBLE_CRAFTING;
        this.recipe = recipe;
        this.recipeOutput = recipe.getRecipeOutput();
    }
    
    public ResearchPage(final ItemStack input) {
        this.type = PageType.TEXT;
        this.text = null;
        this.research = null;
        this.image = null;
        this.aspects = null;
        this.recipe = null;
        this.recipeOutput = null;
        this.type = PageType.SMELTING;
        this.recipe = input;
        this.recipeOutput = FurnaceRecipes.func_77602_a().func_151395_a(input);
    }
    
    public ResearchPage(final InfusionRecipe recipe) {
        this.type = PageType.TEXT;
        this.text = null;
        this.research = null;
        this.image = null;
        this.aspects = null;
        this.recipe = null;
        this.recipeOutput = null;
        this.type = PageType.INFUSION_CRAFTING;
        this.recipe = recipe;
        if (recipe.getRecipeOutput() instanceof ItemStack) {
            this.recipeOutput = (ItemStack)recipe.getRecipeOutput();
        }
        else {
            this.recipeOutput = recipe.getRecipeInput();
        }
    }
    
    public ResearchPage(final InfusionEnchantmentRecipe recipe) {
        this.type = PageType.TEXT;
        this.text = null;
        this.research = null;
        this.image = null;
        this.aspects = null;
        this.recipe = null;
        this.recipeOutput = null;
        this.type = PageType.INFUSION_ENCHANTMENT;
        this.recipe = recipe;
    }
    
    public ResearchPage(final ResourceLocation image, final String caption) {
        this.type = PageType.TEXT;
        this.text = null;
        this.research = null;
        this.image = null;
        this.aspects = null;
        this.recipe = null;
        this.recipeOutput = null;
        this.type = PageType.IMAGE;
        this.image = image;
        this.text = caption;
    }
    
    public ResearchPage(final AspectList as) {
        this.type = PageType.TEXT;
        this.text = null;
        this.research = null;
        this.image = null;
        this.aspects = null;
        this.recipe = null;
        this.recipeOutput = null;
        this.type = PageType.ASPECTS;
        this.aspects = as;
    }
    
    public String getTranslatedText() {
        String ret = "";
        if (this.text != null) {
            ret = StatCollector.func_74838_a(this.text);
            if (ret.isEmpty()) {
                ret = this.text;
            }
        }
        return ret;
    }
    
    public enum PageType
    {
        TEXT, 
        TEXT_CONCEALED, 
        IMAGE, 
        CRUCIBLE_CRAFTING, 
        ARCANE_CRAFTING, 
        ASPECTS, 
        NORMAL_CRAFTING, 
        INFUSION_CRAFTING, 
        COMPOUND_CRAFTING, 
        INFUSION_ENCHANTMENT, 
        SMELTING;
    }
}
