package thaumcraft.api;

import net.minecraft.tileentity.*;
import net.minecraft.nbt.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;

public class TileThaumcraft extends TileEntity
{
    public void func_145839_a(final NBTTagCompound nbttagcompound) {
        super.func_145839_a(nbttagcompound);
        this.readCustomNBT(nbttagcompound);
    }
    
    public void readCustomNBT(final NBTTagCompound nbttagcompound) {
    }
    
    public void func_145841_b(final NBTTagCompound nbttagcompound) {
        super.func_145841_b(nbttagcompound);
        this.writeCustomNBT(nbttagcompound);
    }
    
    public void writeCustomNBT(final NBTTagCompound nbttagcompound) {
    }
    
    public Packet func_145844_m() {
        final NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeCustomNBT(nbttagcompound);
        return (Packet)new S35PacketUpdateTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e, -999, nbttagcompound);
    }
    
    public void onDataPacket(final NetworkManager net, final S35PacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        this.readCustomNBT(pkt.func_148857_g());
    }
}
