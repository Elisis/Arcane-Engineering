package arcane_engineering;

import net.minecraft.util.*;
import thaumcraft.api.research.*;
import blusunrize.immersiveengineering.common.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.item.crafting.*;
import thaumcraft.common.config.*;
import thaumcraft.common.items.wands.*;
import thaumcraft.api.aspects.*;
import thaumcraft.api.*;
import net.minecraft.block.*;
import net.minecraftforge.oredict.*;
import thaumcraft.api.wands.*;
import thaumcraft.api.crafting.*;
import java.util.*;

public class AEResearch
{
    public static List recipes;
    
    public static void setupResearchPages() {
        ResearchCategories.registerCategory("ARCANEENGINEERING", new ResourceLocation("arcane_engineering:textures/gui/AEresearchicon.png"), new ResourceLocation("arcane_engineering:textures/gui/AEresearchback.png"));
    }
    
    public static void registerResearch() {
        AspectList ra = new AspectList();
        ResearchPage[] pages = { new ResearchPage("ArcaneEngineering.research_page.MAGITECH.1"), new ResearchPage((IRecipe)new ShapelessOreRecipe(new ItemStack((Item)IEContent.itemTool, 1, 3), new Object[] { Items.field_151122_aG, Blocks.field_150442_at })) };
        newResearch("MAGITECH", "ARCANEENGINEERING", ra, 0, 0, 0, new ItemStack((Item)IEContent.itemTool, 1, 3)).setPages(pages).setStub().setAutoUnlock().setSpecial().registerResearchItem();
        final ItemStack wand = new ItemStack(ConfigItems.itemWandCasting, 1, 0);
        ((ItemWandCasting)wand.func_77973_b()).setCap(wand, ConfigItems.WAND_CAP_IRON);
        ((ItemWandCasting)wand.func_77973_b()).setRod(wand, ConfigItems.WAND_ROD_WOOD);
        newFakeResearch("BASICTHAUMATURGY", "THAUMATURGY", 0, 2, wand).setRound().registerResearchItem();
        newFakeResearch("THAUMIUM", "ALCHEMY", 4, 5, ItemApi.getItem("itemResource", 2)).registerResearchItem();
        newFakeResearch("VOIDMETAL", "ELDRITCH", 4, 4, ItemApi.getItem("itemResource", 16)).registerResearchItem();
        newFakeResearch("NODESTABILIZER", "THAUMATURGY", -4, 5, ItemApi.getBlock("blockStoneDevice", 9)).registerResearchItem();
        ra = new AspectList().add(Aspect.TOOL, 4).add(Aspect.METAL, 4).add(Aspect.MECHANISM, 2).add(Aspect.MINE, 6);
        pages = new ResearchPage[] { new ResearchPage("ArcaneEngineering.research_page.DRILLTHAUMIUM"), new ResearchPage(AEResearch.recipes.get(1)) };
        newResearch("DRILLTHAUMIUM", "ARCANEENGINEERING", ra, 3, 4, 2, new ItemStack(AEContent.drillHeadThaum, 1, 0)).setPages(pages).setParents("AEFAKE_THAUMIUM").registerResearchItem();
        ra = new AspectList().add(Aspect.TOOL, 4).add(Aspect.METAL, 4).add(Aspect.ELDRITCH, 6).add(Aspect.DARKNESS, 6).add(Aspect.MINE, 6);
        pages = new ResearchPage[] { new ResearchPage("ArcaneEngineering.research_page.DRILLVOID"), new ResearchPage(AEResearch.recipes.get(2)) };
        newResearch("DRILLVOID", "ARCANEENGINEERING", ra, 3, 2, 2, new ItemStack(AEContent.drillHeadThaum, 1, 1)).setPages(pages).setParents("DRILLTHAUMIUM", "AEFAKE_VOIDMETAL").registerResearchItem();
        ra = new AspectList().add(Aspect.AURA, 4).add(Aspect.ENERGY, 4).add(Aspect.HUNGER, 4);
        pages = new ResearchPage[] { new ResearchPage("ArcaneEngineering.research_page.NODEDESTABILIZER"), new ResearchPage(new IArcaneRecipe[] { AEResearch.recipes.get(0) }) };
        newResearch("NODEDESTABILIZER", "ARCANEENGINEERING", ra, -3, 2, 2, new ItemStack(Item.func_150898_a(AEContent.destabilizer))).setPages(pages).setParents("AEFAKE_NODESTABILIZER").registerResearchItem();
        ThaumcraftApi.addWarpToResearch("NODEDESTABILIZER", 4);
        ra = new AspectList().add(Aspect.TOOL, 3).add(Aspect.TREE, 6).add(Aspect.MAGIC, 3).add(Aspect.MECHANISM, 3);
        pages = new ResearchPage[] { new ResearchPage("ArcaneEngineering.research_page.ROD_upgradeable"), new ResearchPage((IRecipe)new ShapedOreRecipe(new ItemStack((Item)IEContent.itemMaterial, 4, 0), new Object[] { "w", "w", 'w', new ItemStack((Block)IEContent.blockTreatedWood, 1, 0) })) };
        newResearch("ROD_upgradeable", "ARCANEENGINEERING", ra, 0, 4, 1, new ItemStack((Item)IEContent.itemMaterial, 1, 0)).setPages(pages).setParents("AEFAKE_BASICTHAUMATURGY").registerResearchItem();
        ra = new AspectList().add(Aspect.METAL, 3).add(Aspect.ORDER, 3).add(Aspect.TOOL, 3).add(Aspect.MECHANISM, 3);
        pages = new ResearchPage[] { new ResearchPage("ArcaneEngineering.research_page.CAP_steel"), new ResearchPage(new IArcaneRecipe[] { AEResearch.recipes.get(3) }) };
        newResearch("CAP_steel", "ARCANEENGINEERING", ra, 1, 4, 1, new ItemStack(AEContent.wandCap, 1, 0)).setPages(pages).setParents("AEFAKE_BASICTHAUMATURGY").registerResearchItem();
        ra = new AspectList().add(Aspect.METAL, 3).add(Aspect.GREED, 3).add(Aspect.TOOL, 3);
        pages = new ResearchPage[] { new ResearchPage("ArcaneEngineering.research_page.CAP_electrum"), new ResearchPage(new IArcaneRecipe[] { AEResearch.recipes.get(4) }) };
        newResearch("CAP_electrum", "ARCANEENGINEERING", ra, -1, 4, 1, new ItemStack(AEContent.wandCap, 1, 1)).setPages(pages).setParents("AEFAKE_BASICTHAUMATURGY").registerResearchItem();
        ra = new AspectList().add(Aspect.AURA, 3).add(Aspect.ENERGY, 3).add(Aspect.VOID, 6).add(Aspect.MECHANISM, 3);
        pages = new ResearchPage[] { new ResearchPage("ArcaneEngineering.research_page.CAPACITOR"), new ResearchPage(new IArcaneRecipe[] { AEResearch.recipes.get(5) }) };
        newResearch("CAPACITOR", "ARCANEENGINEERING", ra, -1, 6, 2, new ItemStack(AEContent.wandUpgrade, 1, 0)).setPages(pages).setParents("ROD_upgradeable", "DISTILESSENTIA", "THAUMIUM").registerResearchItem();
        ra = new AspectList().add(Aspect.AURA, 6).add(Aspect.MAGIC, 3).add(Aspect.VOID, 3).add(Aspect.MECHANISM, 3);
        pages = new ResearchPage[] { new ResearchPage("ArcaneEngineering.research_page.CHARGER"), new ResearchPage(AEResearch.recipes.get(6)) };
        newResearch("CHARGER", "ARCANEENGINEERING", ra, 0, 6, 2, new ItemStack(AEContent.wandUpgrade, 1, 1)).setPages(pages).setParents("ROD_upgradeable", "DISTILESSENTIA", "INFUSION", "ROD_primal_staff").registerResearchItem();
        ThaumcraftApi.addWarpToResearch("CHARGER", 3);
        ra = new AspectList().add(Aspect.MAGIC, 3).add(Aspect.ENERGY, 6).add(Aspect.ELDRITCH, 3).add(Aspect.MECHANISM, 3);
        pages = new ResearchPage[] { new ResearchPage("ArcaneEngineering.research_page.ENERGIZER"), new ResearchPage(AEResearch.recipes.get(7)) };
        newResearch("ENERGIZER", "ARCANEENGINEERING", ra, 1, 6, 3, new ItemStack(AEContent.wandUpgrade, 1, 2)).setPages(pages).setParents("ROD_upgradeable", "PRIMPEARL", "INFUSION").registerResearchItem();
        ThaumcraftApi.addWarpToResearch("ENERGIZER", 3);
        ra = new AspectList();
        pages = new ResearchPage[] { new ResearchPage("ArcaneEngineering.research_page.IMPULSEBOOTS"), new ResearchPage((IRecipe)new ShapedOreRecipe(new ItemStack(AEContent.impulseBoots, 1), new Object[] { "IFI", "BFB", "C C", 'I', "ingotAluminum", 'F', new ItemStack((Item)IEContent.itemMaterial, 1, 4), 'B', Items.field_151133_ar, 'C', new ItemStack((Item)IEContent.itemMaterial, 1, 12) })) };
        newResearch("IMPULSEBOOTS", "ARCANEENGINEERING", ra, 0, -2, 1, new ItemStack(AEContent.impulseBoots)).setPages(pages).setAutoUnlock().setRound().registerResearchItem();
    }
    
    public static void registerRecipes() {
        AspectList a = new AspectList().add(Aspect.ENTROPY, 50).add(Aspect.AIR, 25);
        registerArcaneRecipe("NODEDESTABILIZER", new ItemStack(Item.func_150898_a(AEContent.destabilizer)), a, " c ", "isi", "bmb", 'c', ItemApi.getItem("itemResource", 15), 'i', "ingotSteel", 's', ItemApi.getBlock("blockStoneDevice", 9), 'b', ItemApi.getBlock("blockCosmeticSolid", 6), 'm', new ItemStack((Item)IEContent.itemMaterial, 1, 11));
        a = new AspectList().add(Aspect.EARTH, 20).add(Aspect.ORDER, 20);
        registerArcaneRecipe("DRILLTHAUMIUM", new ItemStack(AEContent.drillHeadThaum, 1, 0), a, "ii ", "bbi", "ii ", 'i', ItemApi.getItem("itemResource", 2), 'b', ItemApi.getBlock("blockCosmeticSolid", 4));
        a = new AspectList().add(Aspect.DARKNESS, 16).add(Aspect.ELDRITCH, 32).add(Aspect.MINE, 8).add(Aspect.METAL, 16);
        registerInfusionRecipe("DRILLVOID", new ItemStack(AEContent.drillHeadThaum, 1, 1), 8, a, new ItemStack((Item)IEContent.itemDrillhead, 1, 0), new ItemStack[] { ItemApi.getItem("itemResource", 16), ItemApi.getItem("itemResource", 16), ItemApi.getItem("itemResource", 17), ItemApi.getItem("itemResource", 17) });
        a = new AspectList().add(Aspect.ORDER, WandCap.caps.get("steel").getCraftCost()).add(Aspect.FIRE, WandCap.caps.get("steel").getCraftCost()).add(Aspect.AIR, WandCap.caps.get("steel").getCraftCost());
        registerArcaneRecipe("CAP_steel", new ItemStack(AEContent.wandCap, 1, 0), a, "nnn", "n n", 'n', "nuggetSteel");
        a = new AspectList().add(Aspect.ORDER, WandCap.caps.get("electrum").getCraftCost()).add(Aspect.FIRE, WandCap.caps.get("electrum").getCraftCost()).add(Aspect.AIR, WandCap.caps.get("electrum").getCraftCost());
        registerArcaneRecipe("CAP_electrum", new ItemStack(AEContent.wandCap, 1, 1), a, "nnn", "n n", 'n', "nuggetElectrum");
        a = new AspectList().add(Aspect.ORDER, 24).add(Aspect.AIR, 16).add(Aspect.ENTROPY, 16).add(Aspect.FIRE, 8);
        registerArcaneRecipe("CAPACITOR", new ItemStack(AEContent.wandUpgrade, 1, 0), a, " i ", "gwg", "ifi", 'i', ItemApi.getItem("itemResource", 2), 'g', Blocks.field_150359_w, 'w', ItemApi.getItem("itemWispEssence", 0), 'f', ItemApi.getItem("itemResource", 8));
        a = new AspectList().add(Aspect.AURA, 24).add(Aspect.CRYSTAL, 24).add(Aspect.GREED, 8).add(Aspect.MAGIC, 32);
        registerInfusionRecipe("CHARGER", new ItemStack(AEContent.wandUpgrade, 1, 1), 8, a, ItemApi.getBlock("blockMetalDevice", 14), new ItemStack[] { new ItemStack(Items.field_151043_k), new ItemStack(Items.field_151043_k), ItemApi.getItem("itemResource", 8), ItemApi.getItem("itemWispEssence", 0) });
        a = new AspectList().add(Aspect.MAGIC, 24).add(Aspect.ENERGY, 32).add(Aspect.ELDRITCH, 16).add(Aspect.MECHANISM, 16);
        registerInfusionRecipe("ENERGIZER", new ItemStack(AEContent.wandUpgrade, 1, 2), 8, a, ItemApi.getItem("itemResource", 15), new ItemStack[] { new ItemStack((Item)IEContent.itemMaterial, 1, 12), new ItemStack((Item)IEContent.itemMaterial, 1, 12) });
    }
    
    private static void registerInfusionRecipe(final String research, final Object result, final int difficulty, final AspectList infusionAspects, final ItemStack centralIngredient, final ItemStack[] otherIngredients) {
        final InfusionRecipe infusionRecipe = ThaumcraftApi.addInfusionCraftingRecipe(research, result, difficulty, infusionAspects, centralIngredient, otherIngredients);
        AEResearch.recipes.add(infusionRecipe);
    }
    
    private static void registerArcaneRecipe(final String research, final ItemStack result, final AspectList craftingAspects, final Object... recipe) {
        final ShapedArcaneRecipe arcaneRecipe = ThaumcraftApi.addArcaneCraftingRecipe(research, result, craftingAspects, recipe);
        AEResearch.recipes.add(arcaneRecipe);
    }
    
    public static AEResearchItem newResearch(final String key, final String category, final AspectList tags, final int col, final int row, final int complex, final Object icon) {
        AEResearchItem researchItem = null;
        if (icon instanceof ItemStack) {
            researchItem = new AEResearchItem(key, category, tags, col, row, complex, (ItemStack)icon);
        }
        else if (icon instanceof ResourceLocation) {
            researchItem = new AEResearchItem(key, category, tags, col, row, complex, (ResourceLocation)icon);
        }
        return researchItem;
    }
    
    public static AEFakeResearchItem newFakeResearch(final String key, final String category, final int col, final int row, final Object icon) {
        AEFakeResearchItem researchItem = null;
        if (icon instanceof ItemStack) {
            researchItem = new AEFakeResearchItem("AEFAKE_" + key, "ARCANEENGINEERING", key, category, col, row, (ItemStack)icon);
        }
        else if (icon instanceof ResourceLocation) {
            researchItem = new AEFakeResearchItem("AEFAKE_" + key, "ARCANEENGINEERING", key, category, col, row, (ResourceLocation)icon);
        }
        return researchItem;
    }
    
    static {
        AEResearch.recipes = new ArrayList();
    }
}
