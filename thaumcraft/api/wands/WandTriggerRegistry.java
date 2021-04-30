package thaumcraft.api.wands;

import net.minecraft.block.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;

public class WandTriggerRegistry
{
    private static HashMap<String, HashMap<List, List>> triggers;
    private static final String DEFAULT = "default";
    
    public static void registerWandBlockTrigger(final IWandTriggerManager manager, final int event, final Block block, final int meta, final String modid) {
        if (!WandTriggerRegistry.triggers.containsKey(modid)) {
            WandTriggerRegistry.triggers.put(modid, new HashMap<List, List>());
        }
        final HashMap<List, List> temp = WandTriggerRegistry.triggers.get(modid);
        temp.put(Arrays.asList(block, meta), Arrays.asList(manager, event));
        WandTriggerRegistry.triggers.put(modid, temp);
    }
    
    public static void registerWandBlockTrigger(final IWandTriggerManager manager, final int event, final Block block, final int meta) {
        registerWandBlockTrigger(manager, event, block, meta, "default");
    }
    
    public static boolean hasTrigger(final Block block, final int meta) {
        for (final String modid : WandTriggerRegistry.triggers.keySet()) {
            final HashMap<List, List> temp = WandTriggerRegistry.triggers.get(modid);
            if (temp.containsKey(Arrays.asList(block, meta)) || temp.containsKey(Arrays.asList(block, -1))) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean hasTrigger(final Block block, final int meta, final String modid) {
        if (!WandTriggerRegistry.triggers.containsKey(modid)) {
            return false;
        }
        final HashMap<List, List> temp = WandTriggerRegistry.triggers.get(modid);
        return temp.containsKey(Arrays.asList(block, meta)) || temp.containsKey(Arrays.asList(block, -1));
    }
    
    public static boolean performTrigger(final World world, final ItemStack wand, final EntityPlayer player, final int x, final int y, final int z, final int side, final Block block, final int meta) {
        for (final String modid : WandTriggerRegistry.triggers.keySet()) {
            final HashMap<List, List> temp = WandTriggerRegistry.triggers.get(modid);
            List l = temp.get(Arrays.asList(block, meta));
            if (l == null) {
                l = temp.get(Arrays.asList(block, -1));
            }
            if (l == null) {
                continue;
            }
            final IWandTriggerManager manager = l.get(0);
            final int event = l.get(1);
            final boolean result = manager.performTrigger(world, wand, player, x, y, z, side, event);
            if (result) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean performTrigger(final World world, final ItemStack wand, final EntityPlayer player, final int x, final int y, final int z, final int side, final Block block, final int meta, final String modid) {
        if (!WandTriggerRegistry.triggers.containsKey(modid)) {
            return false;
        }
        final HashMap<List, List> temp = WandTriggerRegistry.triggers.get(modid);
        List l = temp.get(Arrays.asList(block, meta));
        if (l == null) {
            l = temp.get(Arrays.asList(block, -1));
        }
        if (l == null) {
            return false;
        }
        final IWandTriggerManager manager = l.get(0);
        final int event = l.get(1);
        return manager.performTrigger(world, wand, player, x, y, z, side, event);
    }
    
    static {
        WandTriggerRegistry.triggers = new HashMap<String, HashMap<List, List>>();
    }
}
