package arcane_engineering.client.render;

import net.minecraftforge.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.item.*;
import org.lwjgl.opengl.*;
import arcane_engineering.blocks.tiles.*;
import arcane_engineering.*;
import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.tileentity.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.texture.*;

public class ItemRenderDestabilizer implements IItemRenderer
{
    RenderBlocks rb;
    
    public ItemRenderDestabilizer() {
        this.rb = new RenderBlocks();
    }
    
    public boolean handleRenderType(final ItemStack item, final IItemRenderer.ItemRenderType type) {
        return true;
    }
    
    public boolean shouldUseRenderHelper(final IItemRenderer.ItemRenderType type, final ItemStack item, final IItemRenderer.ItemRendererHelper helper) {
        return true;
    }
    
    public void renderItem(final IItemRenderer.ItemRenderType type, final ItemStack item, final Object... data) {
        if (type == IItemRenderer.ItemRenderType.ENTITY) {
            GL11.glTranslatef(-0.5f, -0.25f, -0.5f);
        }
        else if (type == IItemRenderer.ItemRenderType.EQUIPPED) {
            GL11.glTranslatef(0.0f, 0.0f, -0.5f);
        }
        else if (type == IItemRenderer.ItemRenderType.INVENTORY) {
            GL11.glTranslated(0.0, -0.1, 0.0);
        }
        final TileEntityDestabilizer ted = new TileEntityDestabilizer();
        ted.blockType = AEContent.destabilizer;
        ted.blockMetadata = 0;
        TileEntityRendererDispatcher.instance.renderTileEntityAt((TileEntity)ted, 0.0, 0.0, 0.0, 0.0f);
        GL11.glPushMatrix();
        GL11.glTranslatef(0.5f, 0.5f, 0.5f);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
        this.rb.useInventoryTint = true;
        this.rb.renderBlockAsItem(AEContent.destabilizer, item.getMetadata(), 1.0f);
        GL11.glPopMatrix();
        GL11.glEnable(32826);
    }
}
