package arcane_engineering.items;

import blusunrize.immersiveengineering.api.tool.*;
import net.minecraft.client.renderer.texture.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.creativetab.*;
import net.minecraftforge.oredict.*;
import blusunrize.immersiveengineering.common.util.*;
import net.minecraft.util.*;
import blusunrize.immersiveengineering.common.*;

public class DrillHeadThaum extends AEItem implements IDrillHead
{
    DrillHeadPerm[] perms;
    
    public DrillHeadThaum() {
        super("drillhead", 1, new String[] { "thaumium", "void" });
        this.perms = new DrillHeadPerm[this.subNames.length];
        this.addPerm(0, new DrillHeadPerm("ingotThaumium", 3, 1, 3, 10.0f, 6, 3000, "arcane_engineering:drill_thaumium"));
        this.addPerm(1, new DrillHeadPerm("ingotVoid", 3, 1, 3, 12.0f, 7, 4000, "arcane_engineering:drill_void"));
    }
    
    private void addPerm(final int i, final DrillHeadPerm perm) {
        if (i < this.perms.length) {
            this.perms[i] = perm;
        }
    }
    
    private DrillHeadPerm getHeadPerm(final ItemStack stack) {
        if (stack.getMetadata() >= 0 && stack.getMetadata() < this.perms.length) {
            return this.perms[stack.getMetadata()];
        }
        return new DrillHeadPerm("", 0, 0, 0, 0.0f, 0, 0, "immersiveengineering:textures/models/drill_diesel.png");
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(final IIconRegister ir) {
        super.registerIcons(ir);
        for (final DrillHeadPerm p : this.perms) {
            p.icon = ir.registerIcon(p.texture);
        }
    }
    
    public void addInformation(final ItemStack stack, final EntityPlayer player, final List list, final boolean adv) {
        if (stack.getMetadata() < this.getSubNames().length) {
            list.add(StatCollector.translateToLocalFormatted("desc.ImmersiveEngineering.flavour.drillhead.size", new Object[] { this.getMiningSize(stack), this.getMiningDepth(stack) }));
            list.add(StatCollector.translateToLocalFormatted("desc.ImmersiveEngineering.flavour.drillhead.level", new Object[] { Utils.getHarvestLevelName(this.getMiningLevel(stack)) }));
            list.add(StatCollector.translateToLocalFormatted("desc.ImmersiveEngineering.flavour.drillhead.speed", new Object[] { Utils.formatDouble((double)this.getMiningSpeed(stack), "0.###") }));
            list.add(StatCollector.translateToLocalFormatted("desc.ImmersiveEngineering.flavour.drillhead.damage", new Object[] { Utils.formatDouble((double)this.getAttackDamage(stack), "0.###") }));
            final int maxDmg = this.getMaximumHeadDamage(stack);
            final int dmg = maxDmg - this.getHeadDamage(stack);
            final float quote = dmg / (float)maxDmg;
            final String status = "" + ((quote < 0.1) ? EnumChatFormatting.RED : ((quote < 0.3) ? EnumChatFormatting.GOLD : ((quote < 0.6) ? EnumChatFormatting.YELLOW : EnumChatFormatting.GREEN)));
            final String s = status + (this.getMaximumHeadDamage(stack) - this.getHeadDamage(stack)) + "/" + this.getMaximumHeadDamage(stack);
            list.add(StatCollector.translateToLocalFormatted("desc.ImmersiveEngineering.info.durability", new Object[] { s }));
        }
    }
    
    @Override
    public void getSubItems(final Item item, final CreativeTabs tab, final List list) {
        for (int i = 0; i < this.getSubNames().length; ++i) {
            final ItemStack s = new ItemStack((Item)this, 1, i);
            if (!OreDictionary.getOres(this.getHeadPerm(s).repairMaterial).isEmpty()) {
                list.add(s);
            }
        }
    }
    
    public boolean getIsRepairable(final ItemStack stack, final ItemStack material) {
        return Utils.compareToOreName(material, this.getHeadPerm(stack).repairMaterial);
    }
    
    public boolean beforeBlockbreak(final ItemStack drill, final ItemStack head, final EntityPlayer player) {
        return false;
    }
    
    public void afterBlockbreak(final ItemStack drill, final ItemStack head, final EntityPlayer player) {
    }
    
    public int getMiningSize(final ItemStack head) {
        return this.getHeadPerm(head).drillSize;
    }
    
    public int getMiningDepth(final ItemStack head) {
        return this.getHeadPerm(head).drillDepth;
    }
    
    public int getMiningLevel(final ItemStack head) {
        return this.getHeadPerm(head).drillLevel;
    }
    
    public float getMiningSpeed(final ItemStack head) {
        return this.getHeadPerm(head).drillSpeed;
    }
    
    public float getAttackDamage(final ItemStack head) {
        return this.getHeadPerm(head).drillAttack;
    }
    
    public int getHeadDamage(final ItemStack head) {
        return ItemNBTHelper.getInt(head, "headDamage");
    }
    
    public int getMaximumHeadDamage(final ItemStack head) {
        return this.getHeadPerm(head).maxDamage;
    }
    
    public void damageHead(final ItemStack head, final int dmg) {
        if (head.getMetadata() != 1) {
            ItemNBTHelper.setInt(head, "headDamage", ItemNBTHelper.getInt(head, "headDamage") + dmg);
        }
    }
    
    public double getDurabilityForDisplay(final ItemStack stack) {
        return ItemNBTHelper.getInt(stack, "headDamage") / (double)this.getMaximumHeadDamage(stack);
    }
    
    public boolean showDurabilityBar(final ItemStack stack) {
        return ItemNBTHelper.getInt(stack, "headDamage") > 0;
    }
    
    public IIcon getDrillTexture(final ItemStack drill, final ItemStack head) {
        final DrillHeadPerm perm = this.getHeadPerm(head);
        return (perm.icon != null) ? perm.icon : IEContent.itemDrill.icons[0];
    }
    
    static class DrillHeadPerm
    {
        final String repairMaterial;
        final int drillSize;
        final int drillDepth;
        final int drillLevel;
        final float drillSpeed;
        final float drillAttack;
        final int maxDamage;
        final String texture;
        public IIcon icon;
        
        public DrillHeadPerm(final String repairMaterial, final int drillSize, final int drillDepth, final int drillLevel, final float drillSpeed, final int drillAttack, final int maxDamage, final String texture) {
            this.repairMaterial = repairMaterial;
            this.drillSize = drillSize;
            this.drillDepth = drillDepth;
            this.drillLevel = drillLevel;
            this.drillSpeed = drillSpeed;
            this.drillAttack = (float)drillAttack;
            this.maxDamage = maxDamage;
            this.texture = texture;
        }
    }
}
