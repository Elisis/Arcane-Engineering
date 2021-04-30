package arcane_engineering;

import net.minecraft.block.*;
import thaumcraft.api.wands.*;
import arcane_engineering.items.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import arcane_engineering.blocks.*;
import blusunrize.immersiveengineering.common.*;
import net.minecraft.init.*;
import thaumcraft.api.*;
import net.minecraftforge.oredict.*;
import cpw.mods.fml.common.registry.*;
import net.minecraft.item.crafting.*;
import net.minecraftforge.common.util.*;

public class AEContent
{
    public static ItemArmor.ArmorMaterial MECH;
    public static Item drillHeadThaum;
    public static Block destabilizer;
    public static Item impulseBoots;
    public static Item wandUpgradeable;
    public static Item wandUpgrade;
    public static Item wandCap;
    public static WandRod WAND_ROD_UPGRADEABLE;
    public static WandCap WAND_CAP_STEEL;
    public static WandCap WAND_CAP_ELECTRUM;
    
    public static void preInitItems() {
        AEContent.drillHeadThaum = new DrillHeadThaum();
        AEContent.impulseBoots = (Item)new ItemImpulseBoots();
        AEContent.wandUpgradeable = (Item)new ItemWandUpgradeable();
        AEContent.wandUpgrade = new ItemWandUpgrade();
        AEContent.wandCap = new AEItem("wandcap", 64, new String[] { "steel", "electrum" });
        AEContent.WAND_ROD_UPGRADEABLE = new WandRodUpgradeable();
        AEContent.WAND_CAP_STEEL = new WandCap("steel", 1.0f, new ItemStack(AEContent.wandCap, 1, 0), 4);
        AEContent.WAND_CAP_ELECTRUM = new WandCap("electrum", 0.95f, new ItemStack(AEContent.wandCap, 1, 1), 5);
        AEContent.WAND_CAP_STEEL.setTexture(new ResourceLocation("arcane_engineering", "textures/models/wand_cap_steel.png"));
        AEContent.WAND_CAP_ELECTRUM.setTexture(new ResourceLocation("arcane_engineering", "textures/models/wand_cap_electrum.png"));
    }
    
    public static void preInitBlocks() {
        AEContent.destabilizer = (Block)new BlockDestabilizer();
    }
    
    public static void initCraftingRecipes() {
        addOredictRecipe(new ItemStack(AEContent.impulseBoots, 1), "IFI", "BFB", "C C", 'I', "ingotAluminium", 'F', new ItemStack((Item)IEContent.itemMaterial, 1, 4), 'B', Items.field_151133_ar, 'C', new ItemStack((Item)IEContent.itemMaterial, 1, 12));
        addOredictRecipe(new ItemStack(AEContent.impulseBoots, 1), "IFI", "BFB", "C C", 'I', "ingotAluminum", 'F', new ItemStack((Item)IEContent.itemMaterial, 1, 4), 'B', Items.field_151133_ar, 'C', new ItemStack((Item)IEContent.itemMaterial, 1, 12));
        final UpgradeableWandRecipe wr = new UpgradeableWandRecipe();
        ThaumcraftApi.getCraftingRecipes().add(wr);
    }
    
    public static ShapedOreRecipe addOredictRecipe(final ItemStack output, final Object... recipe) {
        final ShapedOreRecipe sor = new ShapedOreRecipe(output, recipe);
        GameRegistry.addRecipe((IRecipe)sor);
        return sor;
    }
    
    static {
        AEContent.MECH = EnumHelper.addArmorMaterial("MECH", 13, new int[] { 0, 0, 0, 2 }, 5);
    }
}
