package thaumcraft.api.research;

import net.minecraft.util.*;
import java.util.*;

public class ResearchCategoryList
{
    public int minDisplayColumn;
    public int minDisplayRow;
    public int maxDisplayColumn;
    public int maxDisplayRow;
    public ResourceLocation icon;
    public ResourceLocation background;
    public Map<String, ResearchItem> research;
    
    public ResearchCategoryList(final ResourceLocation icon, final ResourceLocation background) {
        this.research = new HashMap<String, ResearchItem>();
        this.icon = icon;
        this.background = background;
    }
}
