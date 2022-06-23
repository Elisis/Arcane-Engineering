package arcane_engineering;

import io.netty.buffer.*;
import cpw.mods.fml.common.network.simpleimpl.*;
import net.minecraft.entity.player.*;

public class FallDamageMessage implements IMessage
{
    public void fromBytes(final ByteBuf buf) {
    }
    
    public void toBytes(final ByteBuf buf) {
    }
    
    public static class Handler implements IMessageHandler<FallDamageMessage, IMessage>
    {
        public IMessage onMessage(final FallDamageMessage message, final MessageContext ctx) {
            final EntityPlayer player = (EntityPlayer)ctx.getServerHandler().playerEntity;
            player.fallDistance = 0.0f;
            return null;
        }
    }
}
