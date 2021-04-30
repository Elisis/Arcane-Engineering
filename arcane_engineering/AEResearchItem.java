package arcane_engineering;

import thaumcraft.api.research.*;
import thaumcraft.api.aspects.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

public class AEResearchItem extends ResearchItem
{
    public AEResearchItem(final String key, final String category, final AspectList tags, final int col, final int row, final int complex, final ResourceLocation icon) {
        super(key, category, tags, col, row, complex, icon);
    }
    
    public AEResearchItem(final String key, final String category, final AspectList tags, final int col, final int row, final int complex, final ItemStack icon) {
        super(key, category, tags, col, row, complex, icon);
    }
    
    @Override
    public String getName() {
        return StatCollector.func_74838_a("ArcaneEngineering.research_name." + this.key);
    }
    
    @Override
    public String getText() {
        return StatCollector.func_74838_a("ArcaneEngineering.research_text." + this.key);
    }
}
