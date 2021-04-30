package thaumcraft.api;

import net.minecraft.tileentity.*;
import net.minecraft.nbt.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;

public class TileThaumcraft extends TileEntity
{
    public void readFromNBT(final NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        this.readCustomNBT(nbttagcompound);
    }
    
    public void readCustomNBT(final NBTTagCompound nbttagcompound) {
    }
    
    public void writeToNBT(final NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        this.writeCustomNBT(nbttagcompound);
    }
    
    public void writeCustomNBT(final NBTTagCompound nbttagcompound) {
    }
    
    public Packet getDescriptionPacket() {
        final NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeCustomNBT(nbttagcompound);
        return (Packet)new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, -999, nbttagcompound);
    }
    
    public void onDataPacket(final NetworkManager net, final S35PacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        this.readCustomNBT(pkt.getNbtCompound());
    }
}
