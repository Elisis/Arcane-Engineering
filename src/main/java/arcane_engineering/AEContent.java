package arcane_engineering;

import arcane_engineering.blocks.BlockDestabilizer;
import arcane_engineering.items.AEItem;
import arcane_engineering.items.DrillHeadThaum;
import arcane_engineering.items.ItemImpulseBoots;
import arcane_engineering.items.ItemWandUpgrade;
import arcane_engineering.items.ItemWandUpgradeable;
import arcane_engineering.items.WandRodUpgradeable;
import blusunrize.immersiveengineering.common.*;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.ShapedOreRecipe;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.wands.WandCap;
import thaumcraft.api.wands.WandRod;

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
    
    @SuppressWarnings("unchecked")
	public static void initCraftingRecipes() {
        addOredictRecipe(new ItemStack(AEContent.impulseBoots, 1), "IFI", "BFB", "C C", 'I', "ingotAluminium", 'F', new ItemStack((Item)IEContent.itemMaterial, 1, 4), 'B', Items.bucket, 'C', new ItemStack((Item)IEContent.itemMaterial, 1, 12));
        addOredictRecipe(new ItemStack(AEContent.impulseBoots, 1), "IFI", "BFB", "C C", 'I', "ingotAluminum", 'F', new ItemStack((Item)IEContent.itemMaterial, 1, 4), 'B', Items.bucket, 'C', new ItemStack((Item)IEContent.itemMaterial, 1, 12));
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
