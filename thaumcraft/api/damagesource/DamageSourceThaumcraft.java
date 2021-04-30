package thaumcraft.api.damagesource;

import net.minecraft.util.*;
import net.minecraft.entity.*;

public class DamageSourceThaumcraft extends DamageSource
{
    public static DamageSource taint;
    public static DamageSource tentacle;
    public static DamageSource swarm;
    public static DamageSource dissolve;
    private boolean isUnblockable;
    private boolean isDamageAllowedInCreativeMode;
    private float hungerDamage;
    private boolean fireDamage;
    private boolean projectile;
    private boolean difficultyScaled;
    private boolean magicDamage;
    private boolean explosion;
    
    protected DamageSourceThaumcraft(final String par1Str) {
        super(par1Str);
        this.isUnblockable = false;
        this.isDamageAllowedInCreativeMode = false;
        this.hungerDamage = 0.3f;
        this.magicDamage = false;
        this.explosion = false;
    }
    
    public static DamageSource causeSwarmDamage(final EntityLivingBase par0EntityLiving) {
        return (DamageSource)new EntityDamageSource("swarm", (Entity)par0EntityLiving);
    }
    
    public static DamageSource causeTentacleDamage(final EntityLivingBase par0EntityLiving) {
        return (DamageSource)new EntityDamageSource("tentacle", (Entity)par0EntityLiving);
    }
    
    static {
        DamageSourceThaumcraft.taint = new DamageSourceThaumcraft("taint").func_76348_h().func_82726_p();
        DamageSourceThaumcraft.tentacle = new DamageSourceThaumcraft("tentacle");
        DamageSourceThaumcraft.swarm = new DamageSourceThaumcraft("swarm");
        DamageSourceThaumcraft.dissolve = new DamageSourceThaumcraft("dissolve").func_76348_h();
    }
}
