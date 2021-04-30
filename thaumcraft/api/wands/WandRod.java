package thaumcraft.api.wands;

import net.minecraft.util.*;
import net.minecraft.item.*;
import java.util.*;

public class WandRod
{
    private String tag;
    private int craftCost;
    int capacity;
    protected ResourceLocation texture;
    ItemStack item;
    IWandRodOnUpdate onUpdate;
    boolean glow;
    public static LinkedHashMap<String, WandRod> rods;
    
    public WandRod(final String tag, final int capacity, final ItemStack item, final int craftCost, final ResourceLocation texture) {
        this.setTag(tag);
        this.capacity = capacity;
        this.texture = texture;
        this.item = item;
        this.setCraftCost(craftCost);
        WandRod.rods.put(tag, this);
    }
    
    public WandRod(final String tag, final int capacity, final ItemStack item, final int craftCost, final IWandRodOnUpdate onUpdate, final ResourceLocation texture) {
        this.setTag(tag);
        this.capacity = capacity;
        this.texture = texture;
        this.item = item;
        this.setCraftCost(craftCost);
        WandRod.rods.put(tag, this);
        this.onUpdate = onUpdate;
    }
    
    public WandRod(final String tag, final int capacity, final ItemStack item, final int craftCost) {
        this.setTag(tag);
        this.capacity = capacity;
        this.texture = new ResourceLocation("thaumcraft", "textures/models/wand_rod_" + this.getTag() + ".png");
        this.item = item;
        this.setCraftCost(craftCost);
        WandRod.rods.put(tag, this);
    }
    
    public WandRod(final String tag, final int capacity, final ItemStack item, final int craftCost, final IWandRodOnUpdate onUpdate) {
        this.setTag(tag);
        this.capacity = capacity;
        this.texture = new ResourceLocation("thaumcraft", "textures/models/wand_rod_" + this.getTag() + ".png");
        this.item = item;
        this.setCraftCost(craftCost);
        WandRod.rods.put(tag, this);
        this.onUpdate = onUpdate;
    }
    
    public String getTag() {
        return this.tag;
    }
    
    public void setTag(final String tag) {
        this.tag = tag;
    }
    
    public int getCapacity() {
        return this.capacity;
    }
    
    public void setCapacity(final int capacity) {
        this.capacity = capacity;
    }
    
    public ResourceLocation getTexture() {
        return this.texture;
    }
    
    public void setTexture(final ResourceLocation texture) {
        this.texture = texture;
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
    
    public IWandRodOnUpdate getOnUpdate() {
        return this.onUpdate;
    }
    
    public void setOnUpdate(final IWandRodOnUpdate onUpdate) {
        this.onUpdate = onUpdate;
    }
    
    public boolean isGlowing() {
        return this.glow;
    }
    
    public void setGlowing(final boolean hasGlow) {
        this.glow = hasGlow;
    }
    
    public String getResearch() {
        return "ROD_" + this.getTag();
    }
    
    static {
        WandRod.rods = new LinkedHashMap<String, WandRod>();
    }
}
