package thaumcraft.api.research;

import net.minecraft.entity.*;

public class ScanResult
{
    public byte type;
    public int id;
    public int meta;
    public Entity entity;
    public String phenomena;
    
    public ScanResult(final byte type, final int blockId, final int blockMeta, final Entity entity, final String phenomena) {
        this.type = 0;
        this.type = type;
        this.id = blockId;
        this.meta = blockMeta;
        this.entity = entity;
        this.phenomena = phenomena;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof ScanResult) {
            final ScanResult sr = (ScanResult)obj;
            if (this.type != sr.type) {
                return false;
            }
            if (this.type == 1 && (this.id != sr.id || this.meta != sr.meta)) {
                return false;
            }
            if (this.type == 2 && this.entity.func_145782_y() != sr.entity.func_145782_y()) {
                return false;
            }
            if (this.type == 3 && !this.phenomena.equals(sr.phenomena)) {
                return false;
            }
        }
        return true;
    }
}
