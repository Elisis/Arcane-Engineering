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
        ted.field_145854_h = AEContent.destabilizer;
        ted.field_145847_g = 0;
        TileEntityRendererDispatcher.field_147556_a.func_147549_a((TileEntity)ted, 0.0, 0.0, 0.0, 0.0f);
        GL11.glPushMatrix();
        GL11.glTranslatef(0.5f, 0.5f, 0.5f);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        Minecraft.func_71410_x().field_71446_o.func_110577_a(TextureMap.field_110575_b);
        this.rb.field_147844_c = true;
        this.rb.func_147800_a(AEContent.destabilizer, item.func_77960_j(), 1.0f);
        GL11.glPopMatrix();
        GL11.glEnable(32826);
    }
}
