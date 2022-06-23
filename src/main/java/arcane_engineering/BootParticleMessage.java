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
            final World worldObj = player.worldObj;
            final Random rand = new Random();
            final double motionX = rand.nextGaussian() * 0.02;
            final double motionY = rand.nextGaussian() * -0.2 - 0.3;
            final double motionZ = rand.nextGaussian() * 0.02;
            worldObj.spawnParticle("largesmoke", m.x + rand.nextFloat() * player.width - player.width / 2.0, m.y - player.height, m.z + rand.nextFloat() * player.width - player.width / 2.0, motionX, motionY, motionZ);
            return null;
        }
        
        @Override
        public IMessage handleServerMessage(final EntityPlayer player, final BootParticleMessage m, final MessageContext ctx) {
            ArcaneEngineering.network.sendToAllAround((IMessage)m, new NetworkRegistry.TargetPoint(player.worldObj.provider.dimensionId, player.posX, player.posY, player.posZ, 100.0));
            return null;
        }
    }
}
