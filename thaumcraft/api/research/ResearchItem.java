package thaumcraft.api.research;

import net.minecraft.item.*;
import thaumcraft.api.aspects.*;
import net.minecraft.util.*;

public class ResearchItem
{
    public final String key;
    public final String category;
    public final AspectList tags;
    public String[] parents;
    public String[] parentsHidden;
    public String[] siblings;
    public final int displayColumn;
    public final int displayRow;
    public final ItemStack icon_item;
    public final ResourceLocation icon_resource;
    private int complexity;
    private boolean isSpecial;
    private boolean isSecondary;
    private boolean isRound;
    private boolean isStub;
    private boolean isVirtual;
    private boolean isConcealed;
    private boolean isHidden;
    private boolean isLost;
    private boolean isAutoUnlock;
    private ItemStack[] itemTriggers;
    private String[] entityTriggers;
    private Aspect[] aspectTriggers;
    private ResearchPage[] pages;
    
    public ResearchItem(final String key, final String category) {
        this.parents = null;
        this.parentsHidden = null;
        this.siblings = null;
        this.pages = null;
        this.key = key;
        this.category = category;
        this.tags = new AspectList();
        this.icon_resource = null;
        this.icon_item = null;
        this.displayColumn = 0;
        this.displayRow = 0;
        this.setVirtual();
    }
    
    public ResearchItem(final String key, final String category, final AspectList tags, final int col, final int row, final int complex, final ResourceLocation icon) {
        this.parents = null;
        this.parentsHidden = null;
        this.siblings = null;
        this.pages = null;
        this.key = key;
        this.category = category;
        this.tags = tags;
        this.icon_resource = icon;
        this.icon_item = null;
        this.displayColumn = col;
        this.displayRow = row;
        this.complexity = complex;
        if (this.complexity < 1) {
            this.complexity = 1;
        }
        if (this.complexity > 3) {
            this.complexity = 3;
        }
    }
    
    public ResearchItem(final String key, final String category, final AspectList tags, final int col, final int row, final int complex, final ItemStack icon) {
        this.parents = null;
        this.parentsHidden = null;
        this.siblings = null;
        this.pages = null;
        this.key = key;
        this.category = category;
        this.tags = tags;
        this.icon_item = icon;
        this.icon_resource = null;
        this.displayColumn = col;
        this.displayRow = row;
        this.complexity = complex;
        if (this.complexity < 1) {
            this.complexity = 1;
        }
        if (this.complexity > 3) {
            this.complexity = 3;
        }
    }
    
    public ResearchItem setSpecial() {
        this.isSpecial = true;
        return this;
    }
    
    public ResearchItem setStub() {
        this.isStub = true;
        return this;
    }
    
    public ResearchItem setLost() {
        this.isLost = true;
        return this;
    }
    
    public ResearchItem setConcealed() {
        this.isConcealed = true;
        return this;
    }
    
    public ResearchItem setHidden() {
        this.isHidden = true;
        return this;
    }
    
    public ResearchItem setVirtual() {
        this.isVirtual = true;
        return this;
    }
    
    public ResearchItem setParents(final String... par) {
        this.parents = par;
        return this;
    }
    
    public ResearchItem setParentsHidden(final String... par) {
        this.parentsHidden = par;
        return this;
    }
    
    public ResearchItem setSiblings(final String... sib) {
        this.siblings = sib;
        return this;
    }
    
    public ResearchItem setPages(final ResearchPage... par) {
        this.pages = par;
        return this;
    }
    
    public ResearchPage[] getPages() {
        return this.pages;
    }
    
    public ResearchItem setItemTriggers(final ItemStack... par) {
        this.itemTriggers = par;
        return this;
    }
    
    public ResearchItem setEntityTriggers(final String... par) {
        this.entityTriggers = par;
        return this;
    }
    
    public ResearchItem setAspectTriggers(final Aspect... par) {
        this.aspectTriggers = par;
        return this;
    }
    
    public ItemStack[] getItemTriggers() {
        return this.itemTriggers;
    }
    
    public String[] getEntityTriggers() {
        return this.entityTriggers;
    }
    
    public Aspect[] getAspectTriggers() {
        return this.aspectTriggers;
    }
    
    public ResearchItem registerResearchItem() {
        ResearchCategories.addResearch(this);
        return this;
    }
    
    public String getName() {
        return StatCollector.func_74838_a("tc.research_name." + this.key);
    }
    
    public String getText() {
        return StatCollector.func_74838_a("tc.research_text." + this.key);
    }
    
    public boolean isSpecial() {
        return this.isSpecial;
    }
    
    public boolean isStub() {
        return this.isStub;
    }
    
    public boolean isLost() {
        return this.isLost;
    }
    
    public boolean isConcealed() {
        return this.isConcealed;
    }
    
    public boolean isHidden() {
        return this.isHidden;
    }
    
    public boolean isVirtual() {
        return this.isVirtual;
    }
    
    public boolean isAutoUnlock() {
        return this.isAutoUnlock;
    }
    
    public ResearchItem setAutoUnlock() {
        this.isAutoUnlock = true;
        return this;
    }
    
    public boolean isRound() {
        return this.isRound;
    }
    
    public ResearchItem setRound() {
        this.isRound = true;
        return this;
    }
    
    public boolean isSecondary() {
        return this.isSecondary;
    }
    
    public ResearchItem setSecondary() {
        this.isSecondary = true;
        return this;
    }
    
    public int getComplexity() {
        return this.complexity;
    }
    
    public ResearchItem setComplexity(final int complexity) {
        this.complexity = complexity;
        return this;
    }
    
    public Aspect getResearchPrimaryTag() {
        Aspect aspect = null;
        int highest = 0;
        if (this.tags != null) {
            for (final Aspect tag : this.tags.getAspects()) {
                if (this.tags.getAmount(tag) > highest) {
                    aspect = tag;
                    highest = this.tags.getAmount(tag);
                }
            }
        }
        return aspect;
    }
}
