package arcane_engineering.client.models;

import net.minecraft.client.model.*;
import net.minecraft.entity.*;

public class ModelDestabilizer extends ModelBase
{
    public ModelRenderer Base;
    public ModelRenderer ArmRest;
    public ModelRenderer Crystal;
    public ModelRenderer ArmA;
    public ModelRenderer ArmB;
    public ModelRenderer ArmC;
    public ModelRenderer ArmD;
    public ModelRenderer Arm2;
    public ModelRenderer Arm2_1;
    public ModelRenderer Arm2_2;
    public ModelRenderer Arm2_3;
    
    public ModelDestabilizer() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        (this.ArmA = new ModelRenderer((ModelBase)this, 0, 0)).setRotationPoint(0.0f, -5.0f, 4.0f);
        this.ArmA.addBox(-0.5f, 0.0f, -0.5f, 1, 8, 1, 0.0f);
        this.setRotateAngle(this.ArmA, 1.0471976f, 0.0f, 0.0f);
        (this.Arm2 = new ModelRenderer((ModelBase)this, 0, 0)).setRotationPoint(0.0f, 8.5f, 0.5f);
        this.Arm2.addBox(-0.5f, 0.0f, -0.5f, 1, 8, 1, 0.0f);
        this.setRotateAngle(this.Arm2, -1.5707964f, 0.0f, 0.0f);
        (this.Base = new ModelRenderer((ModelBase)this, 0, 42)).setRotationPoint(0.0f, -2.0f, 0.0f);
        this.Base.addBox(-8.0f, -6.0f, -8.0f, 16, 6, 16, 0.0f);
        (this.ArmB = new ModelRenderer((ModelBase)this, 0, 0)).setRotationPoint(0.0f, -5.0f, -5.0f);
        this.ArmB.addBox(-0.5f, 0.0f, -1.0f, 1, 8, 1, 0.0f);
        this.setRotateAngle(this.ArmB, 1.0471976f, 3.1415927f, 0.0f);
        (this.ArmRest = new ModelRenderer((ModelBase)this, 12, 28)).setRotationPoint(0.0f, 6.0f, 0.0f);
        this.ArmRest.addBox(-5.0f, -6.0f, -5.0f, 10, 3, 10, 0.0f);
        (this.Arm2_3 = new ModelRenderer((ModelBase)this, 0, 0)).setRotationPoint(0.0f, 8.5f, 0.5f);
        this.Arm2_3.addBox(-0.5f, 0.0f, -0.5f, 1, 8, 1, 0.0f);
        this.setRotateAngle(this.Arm2_3, -1.5707964f, 0.0f, 0.0f);
        (this.Arm2_2 = new ModelRenderer((ModelBase)this, 0, 0)).setRotationPoint(0.0f, 8.5f, 0.5f);
        this.Arm2_2.addBox(-0.5f, 0.0f, -0.5f, 1, 8, 1, 0.0f);
        this.setRotateAngle(this.Arm2_2, -1.5707964f, 0.0f, 0.0f);
        (this.ArmD = new ModelRenderer((ModelBase)this, 0, 0)).setRotationPoint(-4.0f, -5.0f, 0.0f);
        this.ArmD.addBox(-0.5f, 0.0f, -0.5f, 1, 8, 1, 0.0f);
        this.setRotateAngle(this.ArmD, 1.0471976f, -1.5707964f, 0.0f);
        (this.Crystal = new ModelRenderer((ModelBase)this, 40, 0)).setRotationPoint(0.0f, 3.0f, 0.0f);
        this.Crystal.addBox(-3.0f, -6.0f, -3.0f, 6, 7, 6, 0.0f);
        (this.ArmC = new ModelRenderer((ModelBase)this, 0, 0)).setRotationPoint(4.0f, -5.0f, 0.0f);
        this.ArmC.addBox(-0.5f, 0.0f, -0.5f, 1, 8, 1, 0.0f);
        this.setRotateAngle(this.ArmC, 1.0471976f, 1.5707964f, 0.0f);
        (this.Arm2_1 = new ModelRenderer((ModelBase)this, 0, 0)).setRotationPoint(0.0f, 8.5f, 0.0f);
        this.Arm2_1.addBox(-0.5f, 0.0f, -0.5f, 1, 8, 1, 0.0f);
        this.setRotateAngle(this.Arm2_1, -1.5758578f, 0.0f, 0.0f);
        this.ArmA.addChild(this.Arm2);
        this.Base.addChild(this.ArmRest);
        this.ArmD.addChild(this.Arm2_3);
        this.ArmC.addChild(this.Arm2_2);
        this.ArmRest.addChild(this.Crystal);
        this.ArmB.addChild(this.Arm2_1);
        this.ArmA.offsetY = 0.25f;
        this.ArmB.offsetY = 0.25f;
        this.ArmC.offsetY = 0.25f;
        this.ArmD.offsetY = 0.25f;
    }
    
    public void renderBase() {
        super.render((Entity)null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        this.Base.render(0.0625f);
    }
    
    public void renderArms() {
        this.ArmA.render(0.0625f);
        this.ArmB.render(0.0625f);
        this.ArmC.render(0.0625f);
        this.ArmD.render(0.0625f);
    }
    
    public void setRotateAngle(final ModelRenderer modelRenderer, final float x, final float y, final float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
