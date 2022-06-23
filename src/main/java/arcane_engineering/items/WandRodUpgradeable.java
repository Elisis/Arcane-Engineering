package arcane_engineering.items;

import thaumcraft.api.wands.*;
import blusunrize.immersiveengineering.common.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

public class WandRodUpgradeable extends WandRod
{
    public WandRodUpgradeable() {
        super("upgradeable", 30, new ItemStack((Item)IEContent.itemMaterial, 1, 0), 2);
        WandRod.rods.remove("upgradeable");
        this.texture = new ResourceLocation("arcane_engineering", "textures/models/wand_rod_" + this.getTag() + ".png");
    }
}
