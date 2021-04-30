package arcane_engineering;

import net.minecraft.creativetab.*;
import net.minecraft.item.*;

public class AECreativeTab extends CreativeTabs
{
    public AECreativeTab(final int p_i1853_1_, final String p_i1853_2_) {
        super(p_i1853_1_, p_i1853_2_);
    }
    
    public Item func_78016_d() {
        return new ItemStack(AEContent.destabilizer).func_77973_b();
    }
}
