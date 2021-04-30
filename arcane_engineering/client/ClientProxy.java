package arcane_engineering.client;

import arcane_engineering.blocks.tiles.*;
import net.minecraft.client.renderer.tileentity.*;
import arcane_engineering.*;
import net.minecraft.item.*;
import arcane_engineering.client.render.*;
import net.minecraftforge.client.*;
import thaumcraft.client.renderers.item.*;
import cpw.mods.fml.client.registry.*;
import cpw.mods.fml.common.network.simpleimpl.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.*;

public class ClientProxy extends CommonProxy
{
    @Override
    public void registerRenders() {
        ClientRegistry.bindTileEntitySpecialRenderer((Class)TileEntityDestabilizer.class, (TileEntitySpecialRenderer)new RenderDestabilizer());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(AEContent.destabilizer), (IItemRenderer)new ItemRenderDestabilizer());
        MinecraftForgeClient.registerItemRenderer(AEContent.wandUpgradeable, (IItemRenderer)new ItemWandRenderer());
    }
    
    @Override
    public int addArmor(final String armor) {
        return RenderingRegistry.addNewArmourRendererPrefix(armor);
    }
    
    @Override
    public EntityPlayer getPlayerEntity(final MessageContext ctx) {
        return (EntityPlayer)(ctx.side.isClient() ? Minecraft.getMinecraft().thePlayer : super.getPlayerEntity(ctx));
    }
}
