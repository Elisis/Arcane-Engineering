package thaumcraft.api.research;

import java.util.*;
import net.minecraft.util.*;
import org.apache.logging.log4j.*;
import cpw.mods.fml.common.*;

public class ResearchCategories
{
    public static LinkedHashMap<String, ResearchCategoryList> researchCategories;
    
    public static ResearchCategoryList getResearchList(final String key) {
        return ResearchCategories.researchCategories.get(key);
    }
    
    public static String getCategoryName(final String key) {
        return StatCollector.translateToLocal("tc.research_category." + key);
    }
    
    public static ResearchItem getResearch(final String key) {
        final Collection rc = ResearchCategories.researchCategories.values();
        for (final Object cat : rc) {
            final Collection rl = ((ResearchCategoryList)cat).research.values();
            for (final Object ri : rl) {
                if (((ResearchItem)ri).key.equals(key)) {
                    return (ResearchItem)ri;
                }
            }
        }
        return null;
    }
    
    public static void registerCategory(final String key, final ResourceLocation icon, final ResourceLocation background) {
        if (getResearchList(key) == null) {
            final ResearchCategoryList rl = new ResearchCategoryList(icon, background);
            ResearchCategories.researchCategories.put(key, rl);
        }
    }
    
    public static void addResearch(final ResearchItem ri) {
        final ResearchCategoryList rl = getResearchList(ri.category);
        if (rl != null && !rl.research.containsKey(ri.key)) {
            if (!ri.isVirtual()) {
                for (final ResearchItem rr : rl.research.values()) {
                    if (rr.displayColumn == ri.displayColumn && rr.displayRow == ri.displayRow) {
                        FMLLog.log(Level.FATAL, "[Thaumcraft] Research [" + ri.getName() + "] not added as it overlaps with existing research [" + rr.getName() + "]", new Object[0]);
                        return;
                    }
                }
            }
            rl.research.put(ri.key, ri);
            if (ri.displayColumn < rl.minDisplayColumn) {
                rl.minDisplayColumn = ri.displayColumn;
            }
            if (ri.displayRow < rl.minDisplayRow) {
                rl.minDisplayRow = ri.displayRow;
            }
            if (ri.displayColumn > rl.maxDisplayColumn) {
                rl.maxDisplayColumn = ri.displayColumn;
            }
            if (ri.displayRow > rl.maxDisplayRow) {
                rl.maxDisplayRow = ri.displayRow;
            }
        }
    }
    
    static {
        ResearchCategories.researchCategories = new LinkedHashMap<String, ResearchCategoryList>();
    }
}
