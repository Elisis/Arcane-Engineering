package thaumcraft.api;

import net.minecraft.item.*;
import java.util.*;
import net.minecraftforge.common.util.*;
import net.minecraft.tileentity.*;
import thaumcraft.api.aspects.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.block.*;

public class ThaumcraftApiHelper
{
    private static HashMap<Integer, AspectList> allAspects;
    private static HashMap<Integer, AspectList> allCompoundAspects;
    
    public static AspectList cullTags(final AspectList temp) {
        final AspectList temp2 = new AspectList();
        for (final Aspect tag : temp.getAspects()) {
            if (tag != null) {
                temp2.add(tag, temp.getAmount(tag));
            }
        }
        while (temp2 != null && temp2.size() > 6) {
            Aspect lowest = null;
            float low = 32767.0f;
            for (final Aspect tag2 : temp2.getAspects()) {
                if (tag2 != null) {
                    float ta = (float)temp2.getAmount(tag2);
                    if (tag2.isPrimal()) {
                        ta *= 0.9f;
                    }
                    else {
                        if (!tag2.getComponents()[0].isPrimal()) {
                            ta *= 1.1f;
                            if (!tag2.getComponents()[0].getComponents()[0].isPrimal()) {
                                ta *= 1.05f;
                            }
                            if (!tag2.getComponents()[0].getComponents()[1].isPrimal()) {
                                ta *= 1.05f;
                            }
                        }
                        if (!tag2.getComponents()[1].isPrimal()) {
                            ta *= 1.1f;
                            if (!tag2.getComponents()[1].getComponents()[0].isPrimal()) {
                                ta *= 1.05f;
                            }
                            if (!tag2.getComponents()[1].getComponents()[1].isPrimal()) {
                                ta *= 1.05f;
                            }
                        }
                    }
                    if (ta < low) {
                        low = ta;
                        lowest = tag2;
                    }
                }
            }
            temp2.aspects.remove(lowest);
        }
        return temp2;
    }
    
    public static boolean areItemsEqual(final ItemStack s1, final ItemStack s2) {
        if (s1.func_77984_f() && s2.func_77984_f()) {
            return s1.func_77973_b() == s2.func_77973_b();
        }
        return s1.func_77973_b() == s2.func_77973_b() && s1.func_77960_j() == s2.func_77960_j();
    }
    
    public static boolean isResearchComplete(final String username, final String researchkey) {
        return ThaumcraftApi.internalMethods.isResearchComplete(username, researchkey);
    }
    
    public static boolean hasDiscoveredAspect(final String username, final Aspect aspect) {
        return ThaumcraftApi.internalMethods.hasDiscoveredAspect(username, aspect);
    }
    
    public static AspectList getDiscoveredAspects(final String username) {
        return ThaumcraftApi.internalMethods.getDiscoveredAspects(username);
    }
    
    public static ItemStack getStackInRowAndColumn(final Object instance, final int row, final int column) {
        return ThaumcraftApi.internalMethods.getStackInRowAndColumn(instance, row, column);
    }
    
    public static AspectList getObjectAspects(final ItemStack is) {
        return ThaumcraftApi.internalMethods.getObjectAspects(is);
    }
    
    public static AspectList getBonusObjectTags(final ItemStack is, final AspectList ot) {
        return ThaumcraftApi.internalMethods.getBonusObjectTags(is, ot);
    }
    
    public static AspectList generateTags(final Item item, final int meta) {
        return ThaumcraftApi.internalMethods.generateTags(item, meta);
    }
    
    public static boolean containsMatch(final boolean strict, final ItemStack[] inputs, final ItemStack... targets) {
        for (final ItemStack input : inputs) {
            for (final ItemStack target : targets) {
                if (itemMatches(target, input, strict)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static boolean areItemStackTagsEqualForCrafting(final ItemStack slotItem, final ItemStack recipeItem) {
        if (recipeItem == null || slotItem == null) {
            return false;
        }
        if (recipeItem.field_77990_d != null && slotItem.field_77990_d == null) {
            return false;
        }
        if (recipeItem.field_77990_d == null) {
            return true;
        }
        for (final String s : recipeItem.field_77990_d.func_150296_c()) {
            if (!slotItem.field_77990_d.func_74764_b(s)) {
                return false;
            }
            if (!slotItem.field_77990_d.func_74781_a(s).toString().equals(recipeItem.field_77990_d.func_74781_a(s).toString())) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean itemMatches(final ItemStack target, final ItemStack input, final boolean strict) {
        return (input != null || target == null) && (input == null || target != null) && target.func_77973_b() == input.func_77973_b() && ((target.func_77960_j() == 32767 && !strict) || target.func_77960_j() == input.func_77960_j());
    }
    
    public static TileEntity getConnectableTile(final World world, final int x, final int y, final int z, final ForgeDirection face) {
        final TileEntity te = world.func_147438_o(x + face.offsetX, y + face.offsetY, z + face.offsetZ);
        if (te instanceof IEssentiaTransport && ((IEssentiaTransport)te).isConnectable(face.getOpposite())) {
            return te;
        }
        return null;
    }
    
    public static TileEntity getConnectableTile(final IBlockAccess world, final int x, final int y, final int z, final ForgeDirection face) {
        final TileEntity te = world.func_147438_o(x + face.offsetX, y + face.offsetY, z + face.offsetZ);
        if (te instanceof IEssentiaTransport && ((IEssentiaTransport)te).isConnectable(face.getOpposite())) {
            return te;
        }
        return null;
    }
    
    public static AspectList getAllAspects(final int amount) {
        if (ThaumcraftApiHelper.allAspects.get(amount) == null) {
            final AspectList al = new AspectList();
            for (final Aspect aspect : Aspect.aspects.values()) {
                al.add(aspect, amount);
            }
            ThaumcraftApiHelper.allAspects.put(amount, al);
        }
        return ThaumcraftApiHelper.allAspects.get(amount);
    }
    
    public static AspectList getAllCompoundAspects(final int amount) {
        if (ThaumcraftApiHelper.allCompoundAspects.get(amount) == null) {
            final AspectList al = new AspectList();
            for (final Aspect aspect : Aspect.getCompoundAspects()) {
                al.add(aspect, amount);
            }
            ThaumcraftApiHelper.allCompoundAspects.put(amount, al);
        }
        return ThaumcraftApiHelper.allCompoundAspects.get(amount);
    }
    
    public static boolean consumeVisFromWand(final ItemStack wand, final EntityPlayer player, final AspectList cost, final boolean doit, final boolean crafting) {
        return ThaumcraftApi.internalMethods.consumeVisFromWand(wand, player, cost, doit, crafting);
    }
    
    public static boolean consumeVisFromWandCrafting(final ItemStack wand, final EntityPlayer player, final AspectList cost, final boolean doit) {
        return ThaumcraftApi.internalMethods.consumeVisFromWandCrafting(wand, player, cost, doit);
    }
    
    public static boolean consumeVisFromInventory(final EntityPlayer player, final AspectList cost) {
        return ThaumcraftApi.internalMethods.consumeVisFromInventory(player, cost);
    }
    
    public static void addWarpToPlayer(final EntityPlayer player, final int amount, final boolean temporary) {
        ThaumcraftApi.internalMethods.addWarpToPlayer(player, amount, temporary);
    }
    
    public static void addStickyWarpToPlayer(final EntityPlayer player, final int amount) {
        ThaumcraftApi.internalMethods.addStickyWarpToPlayer(player, amount);
    }
    
    public static MovingObjectPosition rayTraceIgnoringSource(final World world, final Vec3 v1, final Vec3 v2, final boolean bool1, final boolean bool2, final boolean bool3) {
        if (Double.isNaN(v1.field_72450_a) || Double.isNaN(v1.field_72448_b) || Double.isNaN(v1.field_72449_c)) {
            return null;
        }
        if (!Double.isNaN(v2.field_72450_a) && !Double.isNaN(v2.field_72448_b) && !Double.isNaN(v2.field_72449_c)) {
            final int i = MathHelper.func_76128_c(v2.field_72450_a);
            final int j = MathHelper.func_76128_c(v2.field_72448_b);
            final int k = MathHelper.func_76128_c(v2.field_72449_c);
            int l = MathHelper.func_76128_c(v1.field_72450_a);
            int i2 = MathHelper.func_76128_c(v1.field_72448_b);
            int j2 = MathHelper.func_76128_c(v1.field_72449_c);
            final Block block = world.func_147439_a(l, i2, j2);
            int k2 = world.func_72805_g(l, i2, j2);
            MovingObjectPosition movingobjectposition2 = null;
            k2 = 200;
            while (k2-- >= 0) {
                if (Double.isNaN(v1.field_72450_a) || Double.isNaN(v1.field_72448_b) || Double.isNaN(v1.field_72449_c)) {
                    return null;
                }
                if (l == i && i2 == j && j2 == k) {
                    continue;
                }
                boolean flag6 = true;
                boolean flag7 = true;
                boolean flag8 = true;
                double d0 = 999.0;
                double d2 = 999.0;
                double d3 = 999.0;
                if (i > l) {
                    d0 = l + 1.0;
                }
                else if (i < l) {
                    d0 = l + 0.0;
                }
                else {
                    flag6 = false;
                }
                if (j > i2) {
                    d2 = i2 + 1.0;
                }
                else if (j < i2) {
                    d2 = i2 + 0.0;
                }
                else {
                    flag7 = false;
                }
                if (k > j2) {
                    d3 = j2 + 1.0;
                }
                else if (k < j2) {
                    d3 = j2 + 0.0;
                }
                else {
                    flag8 = false;
                }
                double d4 = 999.0;
                double d5 = 999.0;
                double d6 = 999.0;
                final double d7 = v2.field_72450_a - v1.field_72450_a;
                final double d8 = v2.field_72448_b - v1.field_72448_b;
                final double d9 = v2.field_72449_c - v1.field_72449_c;
                if (flag6) {
                    d4 = (d0 - v1.field_72450_a) / d7;
                }
                if (flag7) {
                    d5 = (d2 - v1.field_72448_b) / d8;
                }
                if (flag8) {
                    d6 = (d3 - v1.field_72449_c) / d9;
                }
                final boolean flag9 = false;
                byte b0;
                if (d4 < d5 && d4 < d6) {
                    if (i > l) {
                        b0 = 4;
                    }
                    else {
                        b0 = 5;
                    }
                    v1.field_72450_a = d0;
                    v1.field_72448_b += d8 * d4;
                    v1.field_72449_c += d9 * d4;
                }
                else if (d5 < d6) {
                    if (j > i2) {
                        b0 = 0;
                    }
                    else {
                        b0 = 1;
                    }
                    v1.field_72450_a += d7 * d5;
                    v1.field_72448_b = d2;
                    v1.field_72449_c += d9 * d5;
                }
                else {
                    if (k > j2) {
                        b0 = 2;
                    }
                    else {
                        b0 = 3;
                    }
                    v1.field_72450_a += d7 * d6;
                    v1.field_72448_b += d8 * d6;
                    v1.field_72449_c = d3;
                }
                final Vec3 func_72443_a;
                final Vec3 vec32 = func_72443_a = Vec3.func_72443_a(v1.field_72450_a, v1.field_72448_b, v1.field_72449_c);
                final double field_72450_a = MathHelper.func_76128_c(v1.field_72450_a);
                func_72443_a.field_72450_a = field_72450_a;
                l = (int)field_72450_a;
                if (b0 == 5) {
                    --l;
                    final Vec3 vec33 = vec32;
                    ++vec33.field_72450_a;
                }
                final Vec3 vec34 = vec32;
                final double field_72448_b = MathHelper.func_76128_c(v1.field_72448_b);
                vec34.field_72448_b = field_72448_b;
                i2 = (int)field_72448_b;
                if (b0 == 1) {
                    --i2;
                    final Vec3 vec35 = vec32;
                    ++vec35.field_72448_b;
                }
                final Vec3 vec36 = vec32;
                final double field_72449_c = MathHelper.func_76128_c(v1.field_72449_c);
                vec36.field_72449_c = field_72449_c;
                j2 = (int)field_72449_c;
                if (b0 == 3) {
                    --j2;
                    final Vec3 vec37 = vec32;
                    ++vec37.field_72449_c;
                }
                final Block block2 = world.func_147439_a(l, i2, j2);
                final int l2 = world.func_72805_g(l, i2, j2);
                if (bool2 && block2.func_149668_a(world, l, i2, j2) == null) {
                    continue;
                }
                if (block2.func_149678_a(l2, bool1)) {
                    final MovingObjectPosition movingobjectposition3 = block2.func_149731_a(world, l, i2, j2, v1, v2);
                    if (movingobjectposition3 != null) {
                        return movingobjectposition3;
                    }
                    continue;
                }
                else {
                    movingobjectposition2 = new MovingObjectPosition(l, i2, j2, (int)b0, v1, false);
                }
            }
            return bool3 ? movingobjectposition2 : null;
        }
        return null;
    }
    
    static {
        ThaumcraftApiHelper.allAspects = new HashMap<Integer, AspectList>();
        ThaumcraftApiHelper.allCompoundAspects = new HashMap<Integer, AspectList>();
    }
}
