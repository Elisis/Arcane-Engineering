package thaumcraft.api.visnet;

import java.lang.ref.*;
import thaumcraft.api.*;
import net.minecraft.tileentity.*;
import thaumcraft.api.aspects.*;
import java.util.*;

public abstract class TileVisNode extends TileThaumcraft
{
    WeakReference<TileVisNode> parent;
    ArrayList<WeakReference<TileVisNode>> children;
    protected int nodeCounter;
    private boolean nodeRegged;
    public boolean nodeRefresh;
    
    public TileVisNode() {
        this.parent = null;
        this.children = new ArrayList<WeakReference<TileVisNode>>();
        this.nodeCounter = 0;
        this.nodeRegged = false;
        this.nodeRefresh = false;
    }
    
    public WorldCoordinates getLocation() {
        return new WorldCoordinates(this);
    }
    
    public abstract int getRange();
    
    public abstract boolean isSource();
    
    public int consumeVis(final Aspect aspect, final int vis) {
        if (VisNetHandler.isNodeValid(this.getParent())) {
            final int out = this.getParent().get().consumeVis(aspect, vis);
            if (out > 0) {
                this.triggerConsumeEffect(aspect);
            }
            return out;
        }
        return 0;
    }
    
    public void removeThisNode() {
        for (final WeakReference<TileVisNode> n : this.getChildren()) {
            if (n != null && n.get() != null) {
                n.get().removeThisNode();
            }
        }
        this.children = new ArrayList<WeakReference<TileVisNode>>();
        if (VisNetHandler.isNodeValid(this.getParent())) {
            this.getParent().get().nodeRefresh = true;
        }
        this.setParent(null);
        this.parentChanged();
        if (this.isSource()) {
            HashMap<WorldCoordinates, WeakReference<TileVisNode>> sourcelist = VisNetHandler.sources.get(this.field_145850_b.field_73011_w.field_76574_g);
            if (sourcelist == null) {
                sourcelist = new HashMap<WorldCoordinates, WeakReference<TileVisNode>>();
            }
            sourcelist.remove(this.getLocation());
            VisNetHandler.sources.put(this.field_145850_b.field_73011_w.field_76574_g, sourcelist);
        }
        this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
    }
    
    public void func_145843_s() {
        this.removeThisNode();
        super.func_145843_s();
    }
    
    public void triggerConsumeEffect(final Aspect aspect) {
    }
    
    public WeakReference<TileVisNode> getParent() {
        return this.parent;
    }
    
    public WeakReference<TileVisNode> getRootSource() {
        return VisNetHandler.isNodeValid(this.getParent()) ? this.getParent().get().getRootSource() : (this.isSource() ? new WeakReference<TileVisNode>(this) : null);
    }
    
    public void setParent(final WeakReference<TileVisNode> parent) {
        this.parent = parent;
    }
    
    public ArrayList<WeakReference<TileVisNode>> getChildren() {
        return this.children;
    }
    
    public boolean canUpdate() {
        return true;
    }
    
    public void func_145845_h() {
        if (!this.field_145850_b.field_72995_K && (this.nodeCounter++ % 40 == 0 || this.nodeRefresh)) {
            if (!this.nodeRefresh && this.children.size() > 0) {
                for (final WeakReference<TileVisNode> n : this.children) {
                    if (n == null || n.get() == null || !VisNetHandler.canNodeBeSeen(this, n.get())) {
                        this.nodeRefresh = true;
                        break;
                    }
                }
            }
            if (this.nodeRefresh) {
                for (final WeakReference<TileVisNode> n : this.children) {
                    if (n.get() != null) {
                        n.get().nodeRefresh = true;
                    }
                }
                this.children.clear();
                this.parent = null;
            }
            if (this.isSource() && !this.nodeRegged) {
                VisNetHandler.addSource(this.func_145831_w(), this);
                this.nodeRegged = true;
            }
            else if (!this.isSource() && !VisNetHandler.isNodeValid(this.getParent())) {
                this.setParent(VisNetHandler.addNode(this.func_145831_w(), this));
                this.nodeRefresh = true;
            }
            if (this.nodeRefresh) {
                this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
                this.parentChanged();
            }
            this.nodeRefresh = false;
        }
    }
    
    public void parentChanged() {
    }
    
    public byte getAttunement() {
        return -1;
    }
}
