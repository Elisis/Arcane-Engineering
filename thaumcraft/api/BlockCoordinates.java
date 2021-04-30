package thaumcraft.api;

import net.minecraft.tileentity.*;
import net.minecraft.nbt.*;

public class BlockCoordinates implements Comparable
{
    public int x;
    public int y;
    public int z;
    
    public BlockCoordinates() {
    }
    
    public BlockCoordinates(final int par1, final int par2, final int par3) {
        this.x = par1;
        this.y = par2;
        this.z = par3;
    }
    
    public BlockCoordinates(final TileEntity tile) {
        this.x = tile.field_145851_c;
        this.y = tile.field_145848_d;
        this.z = tile.field_145849_e;
    }
    
    public BlockCoordinates(final BlockCoordinates par1ChunkCoordinates) {
        this.x = par1ChunkCoordinates.x;
        this.y = par1ChunkCoordinates.y;
        this.z = par1ChunkCoordinates.z;
    }
    
    @Override
    public boolean equals(final Object par1Obj) {
        if (!(par1Obj instanceof BlockCoordinates)) {
            return false;
        }
        final BlockCoordinates coordinates = (BlockCoordinates)par1Obj;
        return this.x == coordinates.x && this.y == coordinates.y && this.z == coordinates.z;
    }
    
    @Override
    public int hashCode() {
        return this.x + this.y << 8 + this.z << 16;
    }
    
    public int compareWorldCoordinate(final BlockCoordinates par1) {
        return (this.y == par1.y) ? ((this.z == par1.z) ? (this.x - par1.x) : (this.z - par1.z)) : (this.y - par1.y);
    }
    
    public void set(final int par1, final int par2, final int par3, final int d) {
        this.x = par1;
        this.y = par2;
        this.z = par3;
    }
    
    public float getDistanceSquared(final int par1, final int par2, final int par3) {
        final float f = (float)(this.x - par1);
        final float f2 = (float)(this.y - par2);
        final float f3 = (float)(this.z - par3);
        return f * f + f2 * f2 + f3 * f3;
    }
    
    public float getDistanceSquaredToWorldCoordinates(final BlockCoordinates par1ChunkCoordinates) {
        return this.getDistanceSquared(par1ChunkCoordinates.x, par1ChunkCoordinates.y, par1ChunkCoordinates.z);
    }
    
    @Override
    public int compareTo(final Object par1Obj) {
        return this.compareWorldCoordinate((BlockCoordinates)par1Obj);
    }
    
    public void readNBT(final NBTTagCompound nbt) {
        this.x = nbt.func_74762_e("b_x");
        this.y = nbt.func_74762_e("b_y");
        this.z = nbt.func_74762_e("b_z");
    }
    
    public void writeNBT(final NBTTagCompound nbt) {
        nbt.func_74768_a("b_x", this.x);
        nbt.func_74768_a("b_y", this.y);
        nbt.func_74768_a("b_z", this.z);
    }
}
