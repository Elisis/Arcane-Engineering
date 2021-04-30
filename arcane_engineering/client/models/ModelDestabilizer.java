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
        this.field_78090_t = 64;
        this.field_78089_u = 64;
        (this.ArmA = new ModelRenderer((ModelBase)this, 0, 0)).func_78793_a(0.0f, -5.0f, 4.0f);
        this.ArmA.func_78790_a(-0.5f, 0.0f, -0.5f, 1, 8, 1, 0.0f);
        this.setRotateAngle(this.ArmA, 1.0471976f, 0.0f, 0.0f);
        (this.Arm2 = new ModelRenderer((ModelBase)this, 0, 0)).func_78793_a(0.0f, 8.5f, 0.5f);
        this.Arm2.func_78790_a(-0.5f, 0.0f, -0.5f, 1, 8, 1, 0.0f);
        this.setRotateAngle(this.Arm2, -1.5707964f, 0.0f, 0.0f);
        (this.Base = new ModelRenderer((ModelBase)this, 0, 42)).func_78793_a(0.0f, -2.0f, 0.0f);
        this.Base.func_78790_a(-8.0f, -6.0f, -8.0f, 16, 6, 16, 0.0f);
        (this.ArmB = new ModelRenderer((ModelBase)this, 0, 0)).func_78793_a(0.0f, -5.0f, -5.0f);
        this.ArmB.func_78790_a(-0.5f, 0.0f, -1.0f, 1, 8, 1, 0.0f);
        this.setRotateAngle(this.ArmB, 1.0471976f, 3.1415927f, 0.0f);
        (this.ArmRest = new ModelRenderer((ModelBase)this, 12, 28)).func_78793_a(0.0f, 6.0f, 0.0f);
        this.ArmRest.func_78790_a(-5.0f, -6.0f, -5.0f, 10, 3, 10, 0.0f);
        (this.Arm2_3 = new ModelRenderer((ModelBase)this, 0, 0)).func_78793_a(0.0f, 8.5f, 0.5f);
        this.Arm2_3.func_78790_a(-0.5f, 0.0f, -0.5f, 1, 8, 1, 0.0f);
        this.setRotateAngle(this.Arm2_3, -1.5707964f, 0.0f, 0.0f);
        (this.Arm2_2 = new ModelRenderer((ModelBase)this, 0, 0)).func_78793_a(0.0f, 8.5f, 0.5f);
        this.Arm2_2.func_78790_a(-0.5f, 0.0f, -0.5f, 1, 8, 1, 0.0f);
        this.setRotateAngle(this.Arm2_2, -1.5707964f, 0.0f, 0.0f);
        (this.ArmD = new ModelRenderer((ModelBase)this, 0, 0)).func_78793_a(-4.0f, -5.0f, 0.0f);
        this.ArmD.func_78790_a(-0.5f, 0.0f, -0.5f, 1, 8, 1, 0.0f);
        this.setRotateAngle(this.ArmD, 1.0471976f, -1.5707964f, 0.0f);
        (this.Crystal = new ModelRenderer((ModelBase)this, 40, 0)).func_78793_a(0.0f, 3.0f, 0.0f);
        this.Crystal.func_78790_a(-3.0f, -6.0f, -3.0f, 6, 7, 6, 0.0f);
        (this.ArmC = new ModelRenderer((ModelBase)this, 0, 0)).func_78793_a(4.0f, -5.0f, 0.0f);
        this.ArmC.func_78790_a(-0.5f, 0.0f, -0.5f, 1, 8, 1, 0.0f);
        this.setRotateAngle(this.ArmC, 1.0471976f, 1.5707964f, 0.0f);
        (this.Arm2_1 = new ModelRenderer((ModelBase)this, 0, 0)).func_78793_a(0.0f, 8.5f, 0.0f);
        this.Arm2_1.func_78790_a(-0.5f, 0.0f, -0.5f, 1, 8, 1, 0.0f);
        this.setRotateAngle(this.Arm2_1, -1.5758578f, 0.0f, 0.0f);
        this.ArmA.func_78792_a(this.Arm2);
        this.Base.func_78792_a(this.ArmRest);
        this.ArmD.func_78792_a(this.Arm2_3);
        this.ArmC.func_78792_a(this.Arm2_2);
        this.ArmRest.func_78792_a(this.Crystal);
        this.ArmB.func_78792_a(this.Arm2_1);
        this.ArmA.field_82908_p = 0.25f;
        this.ArmB.field_82908_p = 0.25f;
        this.ArmC.field_82908_p = 0.25f;
        this.ArmD.field_82908_p = 0.25f;
    }
    
    public void renderBase() {
        super.func_78088_a((Entity)null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        this.Base.func_78785_a(0.0625f);
    }
    
    public void renderArms() {
        this.ArmA.func_78785_a(0.0625f);
        this.ArmB.func_78785_a(0.0625f);
        this.ArmC.func_78785_a(0.0625f);
        this.ArmD.func_78785_a(0.0625f);
    }
    
    public void setRotateAngle(final ModelRenderer modelRenderer, final float x, final float y, final float z) {
        modelRenderer.field_78795_f = x;
        modelRenderer.field_78796_g = y;
        modelRenderer.field_78808_h = z;
    }
}
