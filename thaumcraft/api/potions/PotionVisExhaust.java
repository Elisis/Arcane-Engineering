package thaumcraft.api.potions;

import net.minecraft.potion.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.entity.*;

public class PotionVisExhaust extends Potion
{
    public static PotionVisExhaust instance;
    private int statusIconIndex;
    static final ResourceLocation rl;
    
    public PotionVisExhaust(final int par1, final boolean par2, final int par3) {
        super(par1, par2, par3);
        this.statusIconIndex = -1;
        this.setIconIndex(0, 0);
    }
    
    public static void init() {
        PotionVisExhaust.instance.setPotionName("potion.visexhaust");
        PotionVisExhaust.instance.setIconIndex(5, 1);
        PotionVisExhaust.instance.setEffectiveness(0.25);
    }
    
    public boolean isBadEffect() {
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public int getStatusIconIndex() {
        Minecraft.getMinecraft().renderEngine.bindTexture(PotionVisExhaust.rl);
        return super.getStatusIconIndex();
    }
    
    public void performEffect(final EntityLivingBase target, final int par2) {
    }
    
    static {
        PotionVisExhaust.instance = null;
        rl = new ResourceLocation("thaumcraft", "textures/misc/potions.png");
    }
}
