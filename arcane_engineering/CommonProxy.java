package arcane_engineering;

import cpw.mods.fml.common.network.simpleimpl.*;
import net.minecraft.entity.player.*;

public class CommonProxy
{
    public void registerRenders() {
    }
    
    public int addArmor(final String armor) {
        return 0;
    }
    
    public EntityPlayer getPlayerEntity(final MessageContext ctx) {
        return (EntityPlayer)ctx.getServerHandler().field_147369_b;
    }
}
