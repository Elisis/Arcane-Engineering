package thaumcraft.api.wands;

import net.minecraft.item.*;
import net.minecraft.util.*;

public class StaffRod extends WandRod
{
    boolean runes;
    
    public StaffRod(final String tag, final int capacity, final ItemStack item, final int craftCost) {
        super(tag + "_staff", capacity, item, craftCost);
        this.runes = false;
        this.texture = new ResourceLocation("thaumcraft", "textures/models/wand_rod_" + tag + ".png");
    }
    
    public StaffRod(final String tag, final int capacity, final ItemStack item, final int craftCost, final IWandRodOnUpdate onUpdate, final ResourceLocation texture) {
        super(tag + "_staff", capacity, item, craftCost, onUpdate, texture);
        this.runes = false;
    }
    
    public StaffRod(final String tag, final int capacity, final ItemStack item, final int craftCost, final IWandRodOnUpdate onUpdate) {
        super(tag + "_staff", capacity, item, craftCost, onUpdate);
        this.runes = false;
        this.texture = new ResourceLocation("thaumcraft", "textures/models/wand_rod_" + tag + ".png");
    }
    
    public StaffRod(final String tag, final int capacity, final ItemStack item, final int craftCost, final ResourceLocation texture) {
        super(tag + "_staff", capacity, item, craftCost, texture);
        this.runes = false;
    }
    
    public boolean hasRunes() {
        return this.runes;
    }
    
    public void setRunes(final boolean hasRunes) {
        this.runes = hasRunes;
    }
}
