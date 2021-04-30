package thaumcraft.api;

import net.minecraft.block.*;
import net.minecraft.item.*;
import java.util.concurrent.*;
import net.minecraftforge.oredict.*;
import net.minecraft.enchantment.*;
import java.util.*;
import thaumcraft.api.crafting.*;
import net.minecraft.entity.player.*;
import thaumcraft.api.research.*;
import thaumcraft.api.aspects.*;
import net.minecraftforge.common.util.*;
import thaumcraft.api.internal.*;

public class ThaumcraftApi
{
    public static Item.ToolMaterial toolMatThaumium;
    public static Item.ToolMaterial toolMatVoid;
    public static Item.ToolMaterial toolMatElemental;
    public static ItemArmor.ArmorMaterial armorMatThaumium;
    public static ItemArmor.ArmorMaterial armorMatSpecial;
    public static ItemArmor.ArmorMaterial armorMatThaumiumFortress;
    public static ItemArmor.ArmorMaterial armorMatVoid;
    public static ItemArmor.ArmorMaterial armorMatVoidFortress;
    public static int enchantFrugal;
    public static int enchantPotency;
    public static int enchantWandFortune;
    public static int enchantHaste;
    public static int enchantRepair;
    public static ArrayList<Block> portableHoleBlackList;
    public static IInternalMethodHandler internalMethods;
    public static ArrayList<IScanEventHandler> scanEventhandlers;
    public static ArrayList<EntityTags> scanEntities;
    private static ArrayList craftingRecipes;
    private static HashMap<Object, ItemStack> smeltingBonus;
    private static HashMap<int[], Object[]> keyCache;
    public static ConcurrentHashMap<List, AspectList> objectTags;
    public static ConcurrentHashMap<List, int[]> groupedObjectTags;
    private static HashMap<Object, Integer> warpMap;
    
    public static void registerScanEventhandler(final IScanEventHandler scanEventHandler) {
        ThaumcraftApi.scanEventhandlers.add(scanEventHandler);
    }
    
    public static void registerEntityTag(final String entityName, final AspectList aspects, final EntityTagsNBT... nbt) {
        ThaumcraftApi.scanEntities.add(new EntityTags(entityName, aspects, nbt));
    }
    
    public static void addSmeltingBonus(final ItemStack in, final ItemStack out) {
        ThaumcraftApi.smeltingBonus.put(Arrays.asList(in.getItem(), in.getMetadata()), new ItemStack(out.getItem(), 0, out.getMetadata()));
    }
    
    public static void addSmeltingBonus(final String in, final ItemStack out) {
        ThaumcraftApi.smeltingBonus.put(in, new ItemStack(out.getItem(), 0, out.getMetadata()));
    }
    
    public static ItemStack getSmeltingBonus(final ItemStack in) {
        ItemStack out = ThaumcraftApi.smeltingBonus.get(Arrays.asList(in.getItem(), in.getMetadata()));
        if (out == null) {
            out = ThaumcraftApi.smeltingBonus.get(Arrays.asList(in.getItem(), 32767));
        }
        if (out == null) {
            final String od = OreDictionary.getOreName(OreDictionary.getOreID(in));
            out = ThaumcraftApi.smeltingBonus.get(od);
        }
        return out;
    }
    
    public static List getCraftingRecipes() {
        return ThaumcraftApi.craftingRecipes;
    }
    
    public static ShapedArcaneRecipe addArcaneCraftingRecipe(final String research, final ItemStack result, final AspectList aspects, final Object... recipe) {
        final ShapedArcaneRecipe r = new ShapedArcaneRecipe(research, result, aspects, recipe);
        ThaumcraftApi.craftingRecipes.add(r);
        return r;
    }
    
    public static ShapelessArcaneRecipe addShapelessArcaneCraftingRecipe(final String research, final ItemStack result, final AspectList aspects, final Object... recipe) {
        final ShapelessArcaneRecipe r = new ShapelessArcaneRecipe(research, result, aspects, recipe);
        ThaumcraftApi.craftingRecipes.add(r);
        return r;
    }
    
    public static InfusionRecipe addInfusionCraftingRecipe(final String research, final Object result, final int instability, final AspectList aspects, final ItemStack input, final ItemStack[] recipe) {
        if (!(result instanceof ItemStack) && !(result instanceof Object[])) {
            return null;
        }
        final InfusionRecipe r = new InfusionRecipe(research, result, instability, aspects, input, recipe);
        ThaumcraftApi.craftingRecipes.add(r);
        return r;
    }
    
    public static InfusionEnchantmentRecipe addInfusionEnchantmentRecipe(final String research, final Enchantment enchantment, final int instability, final AspectList aspects, final ItemStack[] recipe) {
        final InfusionEnchantmentRecipe r = new InfusionEnchantmentRecipe(research, enchantment, instability, aspects, recipe);
        ThaumcraftApi.craftingRecipes.add(r);
        return r;
    }
    
    public static InfusionRecipe getInfusionRecipe(final ItemStack res) {
        for (final Object r : getCraftingRecipes()) {
            if (r instanceof InfusionRecipe && ((InfusionRecipe)r).getRecipeOutput() instanceof ItemStack && ((ItemStack)((InfusionRecipe)r).getRecipeOutput()).isItemEqual(res)) {
                return (InfusionRecipe)r;
            }
        }
        return null;
    }
    
    public static CrucibleRecipe addCrucibleRecipe(final String key, final ItemStack result, final Object catalyst, final AspectList tags) {
        final CrucibleRecipe rc = new CrucibleRecipe(key, result, catalyst, tags);
        getCraftingRecipes().add(rc);
        return rc;
    }
    
    public static CrucibleRecipe getCrucibleRecipe(final ItemStack stack) {
        for (final Object r : getCraftingRecipes()) {
            if (r instanceof CrucibleRecipe && ((CrucibleRecipe)r).getRecipeOutput().isItemEqual(stack)) {
                return (CrucibleRecipe)r;
            }
        }
        return null;
    }
    
    public static CrucibleRecipe getCrucibleRecipeFromHash(final int hash) {
        for (final Object r : getCraftingRecipes()) {
            if (r instanceof CrucibleRecipe && ((CrucibleRecipe)r).hash == hash) {
                return (CrucibleRecipe)r;
            }
        }
        return null;
    }
    
    public static Object[] getCraftingRecipeKey(final EntityPlayer player, final ItemStack stack) {
        final int[] key = { Item.getIdFromItem(stack.getItem()), stack.getMetadata() };
        if (!ThaumcraftApi.keyCache.containsKey(key)) {
            for (final ResearchCategoryList rcl : ResearchCategories.researchCategories.values()) {
                for (final ResearchItem ri : rcl.research.values()) {
                    if (ri.getPages() == null) {
                        continue;
                    }
                    for (int a = 0; a < ri.getPages().length; ++a) {
                        final ResearchPage page = ri.getPages()[a];
                        if (page.recipe != null && page.recipe instanceof CrucibleRecipe[]) {
                            final CrucibleRecipe[] array;
                            final CrucibleRecipe[] crs = array = (CrucibleRecipe[])page.recipe;
                            for (final CrucibleRecipe cr : array) {
                                if (cr.getRecipeOutput().isItemEqual(stack)) {
                                    ThaumcraftApi.keyCache.put(key, new Object[] { ri.key, a });
                                    if (ThaumcraftApiHelper.isResearchComplete(player.getCommandSenderName(), ri.key)) {
                                        return new Object[] { ri.key, a };
                                    }
                                }
                            }
                        }
                        else if (page.recipeOutput != null && stack != null && page.recipeOutput.isItemEqual(stack)) {
                            ThaumcraftApi.keyCache.put(key, new Object[] { ri.key, a });
                            if (ThaumcraftApiHelper.isResearchComplete(player.getCommandSenderName(), ri.key)) {
                                return new Object[] { ri.key, a };
                            }
                            return null;
                        }
                    }
                }
            }
            ThaumcraftApi.keyCache.put(key, null);
            return null;
        }
        if (ThaumcraftApi.keyCache.get(key) == null) {
            return null;
        }
        if (ThaumcraftApiHelper.isResearchComplete(player.getCommandSenderName(), (String)ThaumcraftApi.keyCache.get(key)[0])) {
            return ThaumcraftApi.keyCache.get(key);
        }
        return null;
    }
    
    public static boolean exists(final Item item, final int meta) {
        AspectList tmp = ThaumcraftApi.objectTags.get(Arrays.asList(item, meta));
        if (tmp == null) {
            tmp = ThaumcraftApi.objectTags.get(Arrays.asList(item, 32767));
            if (meta == 32767 && tmp == null) {
                int index = 0;
                do {
                    tmp = ThaumcraftApi.objectTags.get(Arrays.asList(item, index));
                } while (++index < 16 && tmp == null);
            }
            if (tmp == null) {
                return false;
            }
        }
        return true;
    }
    
    public static void registerObjectTag(final ItemStack item, AspectList aspects) {
        if (aspects == null) {
            aspects = new AspectList();
        }
        try {
            ThaumcraftApi.objectTags.put(Arrays.asList(item.getItem(), item.getMetadata()), aspects);
        }
        catch (Exception ex) {}
    }
    
    public static void registerObjectTag(final ItemStack item, final int[] meta, AspectList aspects) {
        if (aspects == null) {
            aspects = new AspectList();
        }
        try {
            ThaumcraftApi.objectTags.put(Arrays.asList(item.getItem(), meta[0]), aspects);
            for (final int m : meta) {
                ThaumcraftApi.groupedObjectTags.put(Arrays.asList(item.getItem(), m), meta);
            }
        }
        catch (Exception ex) {}
    }
    
    public static void registerObjectTag(final String oreDict, AspectList aspects) {
        if (aspects == null) {
            aspects = new AspectList();
        }
        final ArrayList<ItemStack> ores = (ArrayList<ItemStack>)OreDictionary.getOres(oreDict);
        if (ores != null && ores.size() > 0) {
            for (final ItemStack ore : ores) {
                try {
                    ThaumcraftApi.objectTags.put(Arrays.asList(ore.getItem(), ore.getMetadata()), aspects);
                }
                catch (Exception ex) {}
            }
        }
    }
    
    public static void registerComplexObjectTag(final ItemStack item, final AspectList aspects) {
        if (!exists(item.getItem(), item.getMetadata())) {
            final AspectList tmp = ThaumcraftApiHelper.generateTags(item.getItem(), item.getMetadata());
            if (tmp != null && tmp.size() > 0) {
                for (final Aspect tag : tmp.getAspects()) {
                    aspects.add(tag, tmp.getAmount(tag));
                }
            }
            registerObjectTag(item, aspects);
        }
        else {
            final AspectList tmp = ThaumcraftApiHelper.getObjectAspects(item);
            for (final Aspect tag : aspects.getAspects()) {
                tmp.merge(tag, tmp.getAmount(tag));
            }
            registerObjectTag(item, tmp);
        }
    }
    
    public static void addWarpToItem(final ItemStack craftresult, final int amount) {
        ThaumcraftApi.warpMap.put(Arrays.asList(craftresult.getItem(), craftresult.getMetadata()), amount);
    }
    
    public static void addWarpToResearch(final String research, final int amount) {
        ThaumcraftApi.warpMap.put(research, amount);
    }
    
    public static int getWarp(final Object in) {
        if (in == null) {
            return 0;
        }
        if (in instanceof ItemStack && ThaumcraftApi.warpMap.containsKey(Arrays.asList(((ItemStack)in).getItem(), ((ItemStack)in).getMetadata()))) {
            return ThaumcraftApi.warpMap.get(Arrays.asList(((ItemStack)in).getItem(), ((ItemStack)in).getMetadata()));
        }
        if (in instanceof String && ThaumcraftApi.warpMap.containsKey(in)) {
            return ThaumcraftApi.warpMap.get(in);
        }
        return 0;
    }
    
    public static void addLootBagItem(final ItemStack item, final int weight, final int... bagTypes) {
        if (bagTypes == null || bagTypes.length == 0) {
            WeightedRandomLoot.lootBagCommon.add(new WeightedRandomLoot(item, weight));
        }
        else {
            for (final int rarity : bagTypes) {
                switch (rarity) {
                    case 0: {
                        WeightedRandomLoot.lootBagCommon.add(new WeightedRandomLoot(item, weight));
                        break;
                    }
                    case 1: {
                        WeightedRandomLoot.lootBagUncommon.add(new WeightedRandomLoot(item, weight));
                        break;
                    }
                    case 2: {
                        WeightedRandomLoot.lootBagRare.add(new WeightedRandomLoot(item, weight));
                        break;
                    }
                }
            }
        }
    }
    
    static {
        ThaumcraftApi.toolMatThaumium = EnumHelper.addToolMaterial("THAUMIUM", 3, 400, 7.0f, 2.0f, 22);
        ThaumcraftApi.toolMatVoid = EnumHelper.addToolMaterial("VOID", 4, 150, 8.0f, 3.0f, 10);
        ThaumcraftApi.toolMatElemental = EnumHelper.addToolMaterial("THAUMIUM_ELEMENTAL", 3, 1500, 10.0f, 3.0f, 18);
        ThaumcraftApi.armorMatThaumium = EnumHelper.addArmorMaterial("THAUMIUM", 25, new int[] { 2, 6, 5, 2 }, 25);
        ThaumcraftApi.armorMatSpecial = EnumHelper.addArmorMaterial("SPECIAL", 25, new int[] { 1, 3, 2, 1 }, 25);
        ThaumcraftApi.armorMatThaumiumFortress = EnumHelper.addArmorMaterial("FORTRESS", 40, new int[] { 3, 7, 6, 3 }, 25);
        ThaumcraftApi.armorMatVoid = EnumHelper.addArmorMaterial("VOID", 10, new int[] { 3, 7, 6, 3 }, 10);
        ThaumcraftApi.armorMatVoidFortress = EnumHelper.addArmorMaterial("VOIDFORTRESS", 18, new int[] { 4, 8, 7, 4 }, 10);
        ThaumcraftApi.portableHoleBlackList = new ArrayList<Block>();
        ThaumcraftApi.internalMethods = new DummyInternalMethodHandler();
        ThaumcraftApi.scanEventhandlers = new ArrayList<IScanEventHandler>();
        ThaumcraftApi.scanEntities = new ArrayList<EntityTags>();
        ThaumcraftApi.craftingRecipes = new ArrayList();
        ThaumcraftApi.smeltingBonus = new HashMap<Object, ItemStack>();
        ThaumcraftApi.keyCache = new HashMap<int[], Object[]>();
        ThaumcraftApi.objectTags = new ConcurrentHashMap<List, AspectList>();
        ThaumcraftApi.groupedObjectTags = new ConcurrentHashMap<List, int[]>();
        ThaumcraftApi.warpMap = new HashMap<Object, Integer>();
    }
    
    public static class EntityTagsNBT
    {
        public String name;
        public Object value;
        
        public EntityTagsNBT(final String name, final Object value) {
            this.name = name;
            this.value = value;
        }
    }
    
    public static class EntityTags
    {
        public String entityName;
        public EntityTagsNBT[] nbts;
        public AspectList aspects;
        
        public EntityTags(final String entityName, final AspectList aspects, final EntityTagsNBT... nbts) {
            this.entityName = entityName;
            this.nbts = nbts;
            this.aspects = aspects;
        }
    }
}
