package arcane_engineering;

import net.minecraft.entity.player.*;
import cpw.mods.fml.common.network.simpleimpl.*;
import cpw.mods.fml.relauncher.*;

public abstract class BidirectionalMessageHandler<T extends IMessage> implements IMessageHandler<T, IMessage>
{
    @SideOnly(Side.CLIENT)
    public abstract IMessage handleClientMessage(final EntityPlayer p0, final T p1, final MessageContext p2);
    
    public abstract IMessage handleServerMessage(final EntityPlayer p0, final T p1, final MessageContext p2);
    
    public IMessage onMessage(final T message, final MessageContext ctx) {
        if (ctx.side.isClient()) {
            return this.handleClientMessage(ArcaneEngineering.proxy.getPlayerEntity(ctx), message, ctx);
        }
        return this.handleServerMessage(ArcaneEngineering.proxy.getPlayerEntity(ctx), message, ctx);
    }
}
