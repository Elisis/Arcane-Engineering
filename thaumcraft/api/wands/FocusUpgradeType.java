package thaumcraft.api.wands;

import org.apache.logging.log4j.*;
import net.minecraft.util.*;
import thaumcraft.api.aspects.*;

public class FocusUpgradeType
{
    public static FocusUpgradeType[] types;
    public short id;
    public ResourceLocation icon;
    public String name;
    public String text;
    public AspectList aspects;
    public static FocusUpgradeType potency;
    public static FocusUpgradeType frugal;
    public static FocusUpgradeType treasure;
    public static FocusUpgradeType enlarge;
    public static FocusUpgradeType alchemistsfire;
    public static FocusUpgradeType alchemistsfrost;
    public static FocusUpgradeType architect;
    public static FocusUpgradeType extend;
    public static FocusUpgradeType silktouch;
    
    public FocusUpgradeType(final int id, final ResourceLocation icon, final String name, final String text, final AspectList aspects) {
        this.id = (short)id;
        this.icon = icon;
        this.name = name;
        this.text = text;
        this.aspects = aspects;
        if (id < FocusUpgradeType.types.length && FocusUpgradeType.types[id] != null) {
            LogManager.getLogger("THAUMCRAFT").fatal("Focus Upgrade id " + id + " already occupied. Ignoring.");
            return;
        }
        if (id >= FocusUpgradeType.types.length) {
            final FocusUpgradeType[] temp = new FocusUpgradeType[id + 1];
            System.arraycopy(FocusUpgradeType.types, 0, temp, 0, FocusUpgradeType.types.length);
            FocusUpgradeType.types = temp;
        }
        FocusUpgradeType.types[id] = this;
    }
    
    public String getLocalizedName() {
        return StatCollector.func_74838_a(this.name);
    }
    
    public String getLocalizedText() {
        return StatCollector.func_74838_a(this.text);
    }
    
    @Override
    public boolean equals(final Object obj) {
        return obj instanceof FocusUpgradeType && this.id == ((FocusUpgradeType)obj).id;
    }
    
    static {
        FocusUpgradeType.types = new FocusUpgradeType[20];
        FocusUpgradeType.potency = new FocusUpgradeType(0, new ResourceLocation("thaumcraft", "textures/foci/potency.png"), "focus.upgrade.potency.name", "focus.upgrade.potency.text", new AspectList().add(Aspect.WEAPON, 1));
        FocusUpgradeType.frugal = new FocusUpgradeType(1, new ResourceLocation("thaumcraft", "textures/foci/frugal.png"), "focus.upgrade.frugal.name", "focus.upgrade.frugal.text", new AspectList().add(Aspect.HUNGER, 1));
        FocusUpgradeType.treasure = new FocusUpgradeType(2, new ResourceLocation("thaumcraft", "textures/foci/treasure.png"), "focus.upgrade.treasure.name", "focus.upgrade.treasure.text", new AspectList().add(Aspect.GREED, 1));
        FocusUpgradeType.enlarge = new FocusUpgradeType(3, new ResourceLocation("thaumcraft", "textures/foci/enlarge.png"), "focus.upgrade.enlarge.name", "focus.upgrade.enlarge.text", new AspectList().add(Aspect.TRAVEL, 1));
        FocusUpgradeType.alchemistsfire = new FocusUpgradeType(4, new ResourceLocation("thaumcraft", "textures/foci/alchemistsfire.png"), "focus.upgrade.alchemistsfire.name", "focus.upgrade.alchemistsfire.text", new AspectList().add(Aspect.ENERGY, 1).add(Aspect.SLIME, 1));
        FocusUpgradeType.alchemistsfrost = new FocusUpgradeType(5, new ResourceLocation("thaumcraft", "textures/foci/alchemistsfrost.png"), "focus.upgrade.alchemistsfrost.name", "focus.upgrade.alchemistsfrost.text", new AspectList().add(Aspect.COLD, 1).add(Aspect.TRAP, 1));
        FocusUpgradeType.architect = new FocusUpgradeType(6, new ResourceLocation("thaumcraft", "textures/foci/architect.png"), "focus.upgrade.architect.name", "focus.upgrade.architect.text", new AspectList().add(Aspect.CRAFT, 1));
        FocusUpgradeType.extend = new FocusUpgradeType(7, new ResourceLocation("thaumcraft", "textures/foci/extend.png"), "focus.upgrade.extend.name", "focus.upgrade.extend.text", new AspectList().add(Aspect.EXCHANGE, 1));
        FocusUpgradeType.silktouch = new FocusUpgradeType(8, new ResourceLocation("thaumcraft", "textures/foci/silktouch.png"), "focus.upgrade.silktouch.name", "focus.upgrade.silktouch.text", new AspectList().add(Aspect.GREED, 1));
    }
}
