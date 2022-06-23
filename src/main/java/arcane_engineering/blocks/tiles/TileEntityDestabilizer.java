package arcane_engineering.blocks.tiles;

import net.minecraft.tileentity.*;
import cofh.api.energy.*;
import net.minecraftforge.common.util.*;
import net.minecraft.nbt.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import cpw.mods.fml.relauncher.*;
import thaumcraft.api.aspects.*;
import thaumcraft.api.nodes.*;
import net.minecraft.entity.*;

public class TileEntityDestabilizer extends TileEntity implements IEnergyReceiver
{
    public EnergyStorage energyStorage;
    public double rotationAngle;
    public double rotationSpeed;
    public boolean open;
    
    public TileEntityDestabilizer() {
        this.energyStorage = new EnergyStorage(100000);
        this.rotationAngle = 0.0;
        this.rotationSpeed = 0.0;
        this.open = false;
    }
    
    public boolean canConnectEnergy(final ForgeDirection from) {
        return from != null && from == ForgeDirection.DOWN;
    }
    
    public int receiveEnergy(final ForgeDirection from, final int maxReceive, final boolean simulate) {
        if (this.worldObj.isRemote) {
            return 0;
        }
        final int r = this.energyStorage.receiveEnergy(maxReceive, simulate);
        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        this.markDirty();
        return r;
    }
    
    public int getEnergyStored(final ForgeDirection from) {
        return this.energyStorage.getEnergyStored();
    }
    
    public int getMaxEnergyStored(final ForgeDirection from) {
        return this.energyStorage.getMaxEnergyStored();
    }
    
    public Packet getDescriptionPacket() {
        final NBTTagCompound tag = new NBTTagCompound();
        this.writeToNBT(tag);
        return (Packet)new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 4, tag);
    }
    
    public void writeToNBT(final NBTTagCompound tag) {
        super.writeToNBT(tag);
        this.energyStorage.writeToNBT(tag);
        tag.setBoolean("open", this.open);
        tag.setDouble("rotationSpeed", this.rotationSpeed);
    }
    
    public void readFromNBT(final NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.energyStorage.readFromNBT(nbt);
        this.open = nbt.getBoolean("open");
        this.rotationSpeed = nbt.getDouble("rotationSpeed");
    }
    
    public void onDataPacket(final NetworkManager net, final S35PacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        //this.readFromNBT(pkt.getNbtCompound());
        this.readFromNBT(pkt.func_148857_g());
    }
    
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        final AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(this.xCoord - 0.1, (double)this.yCoord, this.zCoord - 0.1, this.xCoord + 1.1, this.yCoord + 1.1, this.zCoord + 1.1);
        return aabb;
    }
    
    public void updateEntity() {
        if (!this.open) {
            if (this.energyStorage.getEnergyStored() >= 100 && !this.open) {
                this.rotationSpeed = Math.pow(this.energyStorage.getEnergyStored() / (double)this.energyStorage.getMaxEnergyStored(), 2.5) * 24.0;
            }
            else if (this.rotationSpeed > 1.5 && !this.open) {
                this.rotationSpeed -= 0.1;
            }
            else if (this.rotationAngle % 90.0 <= 1.5 && !this.open) {
                this.rotationSpeed = 0.0;
                this.rotationAngle = 0.0;
            }
            if (this.energyStorage.getEnergyStored() == this.energyStorage.getMaxEnergyStored() && this.worldObj.getTileEntity(this.xCoord, this.yCoord + 1, this.zCoord) instanceof INode) {
                this.open = true;
                ((INode)this.worldObj.getTileEntity(this.xCoord, this.yCoord + 1, this.zCoord)).setNodeType(NodeType.HUNGRY);
                this.worldObj.playSoundEffect((double)(this.xCoord + 0.5f), (double)(this.yCoord + 0.5f), (double)(this.zCoord + 0.5f), "thaumcraft:runicShieldCharge", 1.0f, 1.0f);
                this.energyStorage.setEnergyStored(0);
                this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord + 1, this.zCoord);
            }
        }
        else if (this.energyStorage.getEnergyStored() >= 100 && this.worldObj.getTileEntity(this.xCoord, this.yCoord + 1, this.zCoord) instanceof INode) {
            this.energyStorage.extractEnergy(100, false);
        }
        else if (this.worldObj.getTileEntity(this.xCoord, this.yCoord + 1, this.zCoord) instanceof INode) {
            this.open = false;
            if (Math.random() < 0.75) {
                ((INode)this.worldObj.getTileEntity(this.xCoord, this.yCoord + 1, this.zCoord)).setNodeType(NodeType.NORMAL);
                this.worldObj.playSoundEffect((double)(this.xCoord + 0.5f), (double)(this.yCoord + 0.5f), (double)(this.zCoord + 0.5f), "thaumcraft:craftfail", 1.0f, 1.0f);
            }
            else {
                final AspectList aspects = new AspectList().add(Aspect.TAINT, 1);
                ((INode)this.worldObj.getTileEntity(this.xCoord, this.yCoord + 1, this.zCoord)).setAspects(aspects);
                ((INode)this.worldObj.getTileEntity(this.xCoord, this.yCoord + 1, this.zCoord)).setNodeType(NodeType.TAINTED);
                ((INode)this.worldObj.getTileEntity(this.xCoord, this.yCoord + 1, this.zCoord)).setNodeModifier(NodeModifier.FADING);
                this.worldObj.createExplosion((Entity)null, this.xCoord + 0.5, this.yCoord + 1.5, this.zCoord + 0.5, 2.0f, true);
            }
            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord + 1, this.zCoord);
        }
        this.rotationAngle += this.rotationSpeed;
        this.rotationAngle %= 360.0;
    }
}
