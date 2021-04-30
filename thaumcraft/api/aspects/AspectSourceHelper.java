package thaumcraft.api.aspects;

import java.lang.reflect.*;
import net.minecraft.tileentity.*;
import net.minecraftforge.common.util.*;
import cpw.mods.fml.common.*;

public class AspectSourceHelper
{
    static Method drainEssentia;
    static Method findEssentia;
    
    public static boolean drainEssentia(final TileEntity tile, final Aspect aspect, final ForgeDirection direction, final int range) {
        try {
            if (AspectSourceHelper.drainEssentia == null) {
                final Class fake = Class.forName("thaumcraft.common.lib.events.EssentiaHandler");
                AspectSourceHelper.drainEssentia = fake.getMethod("drainEssentia", TileEntity.class, Aspect.class, ForgeDirection.class, Integer.TYPE);
            }
            return (boolean)AspectSourceHelper.drainEssentia.invoke(null, tile, aspect, direction, range);
        }
        catch (Exception ex) {
            FMLLog.warning("[Thaumcraft API] Could not invoke thaumcraft.common.lib.events.EssentiaHandler method drainEssentia", new Object[0]);
            return false;
        }
    }
    
    public static boolean findEssentia(final TileEntity tile, final Aspect aspect, final ForgeDirection direction, final int range) {
        try {
            if (AspectSourceHelper.findEssentia == null) {
                final Class fake = Class.forName("thaumcraft.common.lib.events.EssentiaHandler");
                AspectSourceHelper.findEssentia = fake.getMethod("findEssentia", TileEntity.class, Aspect.class, ForgeDirection.class, Integer.TYPE);
            }
            return (boolean)AspectSourceHelper.findEssentia.invoke(null, tile, aspect, direction, range);
        }
        catch (Exception ex) {
            FMLLog.warning("[Thaumcraft API] Could not invoke thaumcraft.common.lib.events.EssentiaHandler method findEssentia", new Object[0]);
            return false;
        }
    }
}
