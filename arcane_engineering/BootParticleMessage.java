package arcane_engineering;

import io.netty.buffer.*;
import net.minecraft.entity.player.*;
import cpw.mods.fml.common.network.simpleimpl.*;
import java.util.*;
import net.minecraft.world.*;
import cpw.mods.fml.common.network.*;

public class BootParticleMessage implements IMessage
{
    private double x;
    private double y;
    private double z;
    
    public BootParticleMessage(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public BootParticleMessage() {
    }
    
    public void fromBytes(final ByteBuf buf) {
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.z = buf.readDouble();
    }
    
    public void toBytes(final ByteBuf buf) {
        buf.writeDouble(this.x);
        buf.writeDouble(this.y);
        buf.writeDouble(this.z);
    }
    
    public static class Handler extends BidirectionalMessageHandler<BootParticleMessage> implements IMessageHandler<BootParticleMessage, IMessage>
    {
        @Override
        public IMessage handleClientMessage(final EntityPlayer player, final BootParticleMessage m, final MessageContext ctx) {
            final World worldObj = player.field_70170_p;
            final Random rand = new Random();
            final double motionX = rand.nextGaussian() * 0.02;
            final double motionY = rand.nextGaussian() * -0.2 - 0.3;
            final double motionZ = rand.nextGaussian() * 0.02;
            worldObj.func_72869_a("largesmoke", m.x + rand.nextFloat() * player.field_70130_N - player.field_70130_N / 2.0, m.y - player.field_70131_O, m.z + rand.nextFloat() * player.field_70130_N - player.field_70130_N / 2.0, motionX, motionY, motionZ);
            return null;
        }
        
        @Override
        public IMessage handleServerMessage(final EntityPlayer player, final BootParticleMessage m, final MessageContext ctx) {
            ArcaneEngineering.network.sendToAllAround((IMessage)m, new NetworkRegistry.TargetPoint(player.field_70170_p.field_73011_w.field_76574_g, player.field_70165_t, player.field_70163_u, player.field_70161_v, 100.0));
            return null;
        }
    }
}
