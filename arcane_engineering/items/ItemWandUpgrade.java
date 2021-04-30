package arcane_engineering.items;

import blusunrize.immersiveengineering.api.tool.*;
import net.minecraft.item.*;
import java.util.*;
import com.google.common.collect.*;

public class ItemWandUpgrade extends AEItem implements IUpgrade
{
    public ItemWandUpgrade() {
        super("wandupgrade", 1, new String[] { "capacitor", "charger", "energizer" });
    }
    
    public void applyUpgrades(final ItemStack target, final ItemStack upgrade, final HashMap<String, Object> modifications) {
        Integer mod = 0;
        if (upgrade.getMetadata() == 0) {
            if (modifications.get("capacitors") != null) {
                mod = modifications.get("capacitors");
            }
            modifications.put("capacitors", mod + 1);
        }
        else if (upgrade.getMetadata() == 1) {
            if (modifications.get("chargers") != null) {
                mod = modifications.get("chargers");
            }
            modifications.put("chargers", mod + 1);
        }
        else if (upgrade.getMetadata() == 2) {
            if (modifications.get("energizers") != null) {
                mod = modifications.get("energizers");
            }
            modifications.put("energizers", mod + 1);
        }
    }
    
    public boolean canApplyUpgrades(final ItemStack arg0, final ItemStack arg1) {
        return true;
    }
    
    public Set<String> getUpgradeTypes(final ItemStack arg0) {
        return (Set<String>)ImmutableSet.of((Object)"WAND");
    }
}
