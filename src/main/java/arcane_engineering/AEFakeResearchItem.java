package arcane_engineering;

import net.minecraft.util.*;
import thaumcraft.api.aspects.*;
import net.minecraft.item.*;
import thaumcraft.api.research.*;

public class AEFakeResearchItem extends ResearchItem
{
    public ResearchItem original;
    
    public AEFakeResearchItem(final String key, final String category, final String original, final String originalCategory, final int displayX, final int displayY, final ResourceLocation icon) {
        super(key, category, new AspectList(), displayX, displayY, 1, icon);
        this.original = ResearchCategories.researchCategories.get(originalCategory).research.get(original);
        this.setupOriginal();
    }
    
    public AEFakeResearchItem(final String key, final String category, final String original, final String originalCategory, final int displayX, final int displayY, final ItemStack icon) {
        super(key, category, new AspectList(), displayX, displayY, 1, icon);
        this.original = ResearchCategories.researchCategories.get(originalCategory).research.get(original);
        this.setupOriginal();
    }
    
    private void setupOriginal() {
        if (this.original.siblings == null) {
            this.original.setSiblings(this.key);
        }
        else {
            final String[] newSiblings = new String[this.original.siblings.length + 1];
            System.arraycopy(this.original.siblings, 0, newSiblings, 0, this.original.siblings.length);
            newSiblings[this.original.siblings.length] = this.key;
            this.original.setSiblings(newSiblings);
        }
    }
    
    @Override
    public String getName() {
        return this.original.getName();
    }
    
    @Override
    public String getText() {
        return this.original.getText();
    }
    
    @Override
    public boolean isStub() {
        return true;
    }
    
    @Override
    public boolean isHidden() {
        return true;
    }
    
    @Override
    public int getComplexity() {
        return 1;
    }
    
    @Override
    public boolean isConcealed() {
        return false;
    }
    
    @Override
    public ResearchPage[] getPages() {
        return this.original.getPages();
    }
}
