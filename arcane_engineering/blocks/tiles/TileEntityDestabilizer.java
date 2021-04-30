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
        if (this.field_145850_b.field_72995_K) {
            return 0;
        }
        final int r = this.energyStorage.receiveEnergy(maxReceive, simulate);
        this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
        this.func_70296_d();
        return r;
    }
    
    public int getEnergyStored(final ForgeDirection from) {
        return this.energyStorage.getEnergyStored();
    }
    
    public int getMaxEnergyStored(final ForgeDirection from) {
        return this.energyStorage.getMaxEnergyStored();
    }
    
    public Packet func_145844_m() {
        final NBTTagCompound tag = new NBTTagCompound();
        this.func_145841_b(tag);
        return (Packet)new S35PacketUpdateTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e, 4, tag);
    }
    
    public void func_145841_b(final NBTTagCompound tag) {
        super.func_145841_b(tag);
        this.energyStorage.writeToNBT(tag);
        tag.func_74757_a("open", this.open);
        tag.func_74780_a("rotationSpeed", this.rotationSpeed);
    }
    
    public void func_145839_a(final NBTTagCompound nbt) {
        super.func_145839_a(nbt);
        this.energyStorage.readFromNBT(nbt);
        this.open = nbt.func_74767_n("open");
        this.rotationSpeed = nbt.func_74769_h("rotationSpeed");
    }
    
    public void onDataPacket(final NetworkManager net, final S35PacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        this.func_145839_a(pkt.func_148857_g());
    }
    
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        final AxisAlignedBB aabb = AxisAlignedBB.func_72330_a(this.field_145851_c - 0.1, (double)this.field_145848_d, this.field_145849_e - 0.1, this.field_145851_c + 1.1, this.field_145848_d + 1.1, this.field_145849_e + 1.1);
        return aabb;
    }
    
    public void func_145845_h() {
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
            if (this.energyStorage.getEnergyStored() == this.energyStorage.getMaxEnergyStored() && this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d + 1, this.field_145849_e) instanceof INode) {
                this.open = true;
                ((INode)this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d + 1, this.field_145849_e)).setNodeType(NodeType.HUNGRY);
                this.field_145850_b.func_72908_a((double)(this.field_145851_c + 0.5f), (double)(this.field_145848_d + 0.5f), (double)(this.field_145849_e + 0.5f), "thaumcraft:runicShieldCharge", 1.0f, 1.0f);
                this.energyStorage.setEnergyStored(0);
                this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d + 1, this.field_145849_e);
            }
        }
        else if (this.energyStorage.getEnergyStored() >= 100 && this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d + 1, this.field_145849_e) instanceof INode) {
            this.energyStorage.extractEnergy(100, false);
        }
        else if (this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d + 1, this.field_145849_e) instanceof INode) {
            this.open = false;
            if (Math.random() < 0.75) {
                ((INode)this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d + 1, this.field_145849_e)).setNodeType(NodeType.NORMAL);
                this.field_145850_b.func_72908_a((double)(this.field_145851_c + 0.5f), (double)(this.field_145848_d + 0.5f), (double)(this.field_145849_e + 0.5f), "thaumcraft:craftfail", 1.0f, 1.0f);
            }
            else {
                final AspectList aspects = new AspectList().add(Aspect.TAINT, 1);
                ((INode)this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d + 1, this.field_145849_e)).setAspects(aspects);
                ((INode)this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d + 1, this.field_145849_e)).setNodeType(NodeType.TAINTED);
                ((INode)this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d + 1, this.field_145849_e)).setNodeModifier(NodeModifier.FADING);
                this.field_145850_b.func_72876_a((Entity)null, this.field_145851_c + 0.5, this.field_145848_d + 1.5, this.field_145849_e + 0.5, 2.0f, true);
            }
            this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d + 1, this.field_145849_e);
        }
        this.rotationAngle += this.rotationSpeed;
        this.rotationAngle %= 360.0;
    }
}
