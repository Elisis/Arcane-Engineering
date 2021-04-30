package arcane_engineering.client.render;

import net.minecraft.client.renderer.tileentity.*;
import arcane_engineering.client.models.*;
import net.minecraft.util.*;
import net.minecraft.tileentity.*;
import arcane_engineering.blocks.tiles.*;
import org.lwjgl.opengl.*;

public class RenderDestabilizer extends TileEntitySpecialRenderer
{
    private final ModelDestabilizer model;
    private final ResourceLocation texture;
    private final ResourceLocation textureOpen;
    
    public RenderDestabilizer() {
        this.model = new ModelDestabilizer();
        this.texture = new ResourceLocation("arcane_engineering", "textures/models/destabilizer.png");
        this.textureOpen = new ResourceLocation("arcane_engineering", "textures/models/destabilizer_open.png");
    }
    
    public void renderTileEntityAt(final TileEntity te, final double x, final double y, final double z, final float scale) {
        if (((TileEntityDestabilizer)te).open) {
            this.bindTexture(this.textureOpen);
        }
        else {
            this.bindTexture(this.texture);
        }
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x + 0.5f, (float)y + 0.5f, (float)z + 0.5f);
        GL11.glPushMatrix();
        GL11.glRotated(((TileEntityDestabilizer)te).rotationAngle, 0.0, 1.0, 0.0);
        this.model.renderArms();
        GL11.glPopMatrix();
        this.model.renderBase();
        GL11.glPopMatrix();
    }
}
