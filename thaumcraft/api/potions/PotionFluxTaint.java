package thaumcraft.api.potions;

import net.minecraft.potion.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.entity.*;
import thaumcraft.api.entities.*;
import net.minecraft.entity.player.*;
import thaumcraft.api.damagesource.*;

public class PotionFluxTaint extends Potion
{
    public static PotionFluxTaint instance;
    private int statusIconIndex;
    static final ResourceLocation rl;
    
    public PotionFluxTaint(final int par1, final boolean par2, final int par3) {
        super(par1, par2, par3);
        this.statusIconIndex = -1;
        this.setIconIndex(0, 0);
    }
    
    public static void init() {
        PotionFluxTaint.instance.setPotionName("potion.fluxtaint");
        PotionFluxTaint.instance.setIconIndex(3, 1);
        PotionFluxTaint.instance.setEffectiveness(0.25);
    }
    
    public boolean isBadEffect() {
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public int getStatusIconIndex() {
        Minecraft.getMinecraft().renderEngine.bindTexture(PotionFluxTaint.rl);
        return super.getStatusIconIndex();
    }
    
    public void performEffect(final EntityLivingBase target, final int par2) {
        if (target instanceof ITaintedMob) {
            target.heal(1.0f);
        }
        else if (!target.isEntityUndead() && !(target instanceof EntityPlayer)) {
            target.attackEntityFrom(DamageSourceThaumcraft.taint, 1.0f);
        }
        else if (!target.isEntityUndead() && (target.getMaxHealth() > 1.0f || target instanceof EntityPlayer)) {
            target.attackEntityFrom(DamageSourceThaumcraft.taint, 1.0f);
        }
    }
    
    public boolean isReady(final int par1, final int par2) {
        final int k = 40 >> par2;
        return k <= 0 || par1 % k == 0;
    }
    
    static {
        PotionFluxTaint.instance = null;
        rl = new ResourceLocation("thaumcraft", "textures/misc/potions.png");
    }
}
