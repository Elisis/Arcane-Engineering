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
            HashMap<WorldCoordinates, WeakReference<TileVisNode>> sourcelist = VisNetHandler.sources.get(this.worldObj.provider.dimensionId);
            if (sourcelist == null) {
                sourcelist = new HashMap<WorldCoordinates, WeakReference<TileVisNode>>();
            }
            sourcelist.remove(this.getLocation());
            VisNetHandler.sources.put(this.worldObj.provider.dimensionId, sourcelist);
        }
        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
    }
    
    public void invalidate() {
        this.removeThisNode();
        super.invalidate();
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
    
    public void updateEntity() {
        if (!this.worldObj.isRemote && (this.nodeCounter++ % 40 == 0 || this.nodeRefresh)) {
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
                VisNetHandler.addSource(this.getWorld(), this);
                this.nodeRegged = true;
            }
            else if (!this.isSource() && !VisNetHandler.isNodeValid(this.getParent())) {
                this.setParent(VisNetHandler.addNode(this.getWorld(), this));
                this.nodeRefresh = true;
            }
            if (this.nodeRefresh) {
                this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
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
