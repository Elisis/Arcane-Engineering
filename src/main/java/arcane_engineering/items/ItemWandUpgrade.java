package arcane_engineering.items;

import java.util.HashMap;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import blusunrize.immersiveengineering.api.tool.IUpgrade;
import net.minecraft.item.ItemStack;

public class ItemWandUpgrade extends AEItem implements IUpgrade
{
    public ItemWandUpgrade() {
        super("wandupgrade", 1, new String[] { "capacitor", "charger", "energizer" });
    }
    
    public void applyUpgrades(final ItemStack target, final ItemStack upgrade, final HashMap<String, Object> modifications) {
        Integer mod = 0;
        if (upgrade.getItemDamage() == 0) {
            if (modifications.get("capacitors") != null) {
                mod = (Integer) modifications.get("capacitors");
            }
            modifications.put("capacitors", mod + 1);
        }
        else if (upgrade.getItemDamage() == 1) {
            if (modifications.get("chargers") != null) {
                mod = (Integer) modifications.get("chargers");
            }
            modifications.put("chargers", mod + 1);
        }
        else if (upgrade.getItemDamage() == 2) {
            if (modifications.get("energizers") != null) {
                mod = (Integer) modifications.get("energizers");
            }
            modifications.put("energizers", mod + 1);
        }
    }
    
    public boolean canApplyUpgrades(final ItemStack arg0, final ItemStack arg1) {
        return true;
    }
    
    public Set<String> getUpgradeTypes(final ItemStack arg0) {
        return (Set<String>) ImmutableSet.of("WAND");
    }
}
