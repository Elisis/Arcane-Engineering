package thaumcraft.api.wands;

import thaumcraft.api.aspects.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import java.util.*;

public class WandCap
{
    private String tag;
    private int craftCost;
    float baseCostModifier;
    List<Aspect> specialCostModifierAspects;
    float specialCostModifier;
    ResourceLocation texture;
    ItemStack item;
    public static LinkedHashMap<String, WandCap> caps;
    
    public WandCap(final String tag, final float discount, final ItemStack item, final int craftCost) {
        this.setTag(tag);
        this.baseCostModifier = discount;
        this.specialCostModifierAspects = null;
        this.texture = new ResourceLocation("thaumcraft", "textures/models/wand_cap_" + this.getTag() + ".png");
        this.item = item;
        this.setCraftCost(craftCost);
        WandCap.caps.put(tag, this);
    }
    
    public WandCap(final String tag, final float discount, final List<Aspect> specialAspects, final float discountSpecial, final ItemStack item, final int craftCost) {
        this.setTag(tag);
        this.baseCostModifier = discount;
        this.specialCostModifierAspects = specialAspects;
        this.specialCostModifier = discountSpecial;
        this.texture = new ResourceLocation("thaumcraft", "textures/models/wand_cap_" + this.getTag() + ".png");
        this.item = item;
        this.setCraftCost(craftCost);
        WandCap.caps.put(tag, this);
    }
    
    public float getBaseCostModifier() {
        return this.baseCostModifier;
    }
    
    public List<Aspect> getSpecialCostModifierAspects() {
        return this.specialCostModifierAspects;
    }
    
    public float getSpecialCostModifier() {
        return this.specialCostModifier;
    }
    
    public ResourceLocation getTexture() {
        return this.texture;
    }
    
    public void setTexture(final ResourceLocation texture) {
        this.texture = texture;
    }
    
    public String getTag() {
        return this.tag;
    }
    
    public void setTag(final String tag) {
        this.tag = tag;
    }
    
    public ItemStack getItem() {
        return this.item;
    }
    
    public void setItem(final ItemStack item) {
        this.item = item;
    }
    
    public int getCraftCost() {
        return this.craftCost;
    }
    
    public void setCraftCost(final int craftCost) {
        this.craftCost = craftCost;
    }
    
    public String getResearch() {
        return "CAP_" + this.getTag();
    }
    
    static {
        WandCap.caps = new LinkedHashMap<String, WandCap>();
    }
}
