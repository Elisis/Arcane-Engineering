package thaumcraft.api.aspects;

import org.apache.commons.lang3.text.*;
import net.minecraft.util.*;
import java.util.*;

public class Aspect
{
    String tag;
    Aspect[] components;
    int color;
    private String chatcolor;
    ResourceLocation image;
    int blend;
    public static LinkedHashMap<String, Aspect> aspects;
    public static final Aspect AIR;
    public static final Aspect EARTH;
    public static final Aspect FIRE;
    public static final Aspect WATER;
    public static final Aspect ORDER;
    public static final Aspect ENTROPY;
    public static final Aspect VOID;
    public static final Aspect LIGHT;
    public static final Aspect WEATHER;
    public static final Aspect MOTION;
    public static final Aspect COLD;
    public static final Aspect CRYSTAL;
    public static final Aspect LIFE;
    public static final Aspect POISON;
    public static final Aspect ENERGY;
    public static final Aspect EXCHANGE;
    public static final Aspect METAL;
    public static final Aspect DEATH;
    public static final Aspect FLIGHT;
    public static final Aspect DARKNESS;
    public static final Aspect SOUL;
    public static final Aspect HEAL;
    public static final Aspect TRAVEL;
    public static final Aspect ELDRITCH;
    public static final Aspect MAGIC;
    public static final Aspect AURA;
    public static final Aspect TAINT;
    public static final Aspect SLIME;
    public static final Aspect PLANT;
    public static final Aspect TREE;
    public static final Aspect BEAST;
    public static final Aspect FLESH;
    public static final Aspect UNDEAD;
    public static final Aspect MIND;
    public static final Aspect SENSES;
    public static final Aspect MAN;
    public static final Aspect CROP;
    public static final Aspect MINE;
    public static final Aspect TOOL;
    public static final Aspect HARVEST;
    public static final Aspect WEAPON;
    public static final Aspect ARMOR;
    public static final Aspect HUNGER;
    public static final Aspect GREED;
    public static final Aspect CRAFT;
    public static final Aspect CLOTH;
    public static final Aspect MECHANISM;
    public static final Aspect TRAP;
    
    public Aspect(final String tag, final int color, final Aspect[] components, final ResourceLocation image, final int blend) {
        if (Aspect.aspects.containsKey(tag)) {
            throw new IllegalArgumentException(tag + " already registered!");
        }
        this.tag = tag;
        this.components = components;
        this.color = color;
        this.image = image;
        this.blend = blend;
        Aspect.aspects.put(tag, this);
    }
    
    public Aspect(final String tag, final int color, final Aspect[] components) {
        this(tag, color, components, new ResourceLocation("thaumcraft", "textures/aspects/" + tag.toLowerCase() + ".png"), 1);
    }
    
    public Aspect(final String tag, final int color, final Aspect[] components, final int blend) {
        this(tag, color, components, new ResourceLocation("thaumcraft", "textures/aspects/" + tag.toLowerCase() + ".png"), blend);
    }
    
    public Aspect(final String tag, final int color, final String chatcolor, final int blend) {
        this(tag, color, (Aspect[])null, blend);
        this.setChatcolor(chatcolor);
    }
    
    public int getColor() {
        return this.color;
    }
    
    public String getName() {
        return WordUtils.capitalizeFully(this.tag);
    }
    
    public String getLocalizedDescription() {
        return StatCollector.translateToLocal("tc.aspect." + this.tag);
    }
    
    public String getTag() {
        return this.tag;
    }
    
    public void setTag(final String tag) {
        this.tag = tag;
    }
    
    public Aspect[] getComponents() {
        return this.components;
    }
    
    public void setComponents(final Aspect[] components) {
        this.components = components;
    }
    
    public ResourceLocation getImage() {
        return this.image;
    }
    
    public static Aspect getAspect(final String tag) {
        return Aspect.aspects.get(tag);
    }
    
    public int getBlend() {
        return this.blend;
    }
    
    public void setBlend(final int blend) {
        this.blend = blend;
    }
    
    public boolean isPrimal() {
        return this.getComponents() == null || this.getComponents().length != 2;
    }
    
    public static ArrayList<Aspect> getPrimalAspects() {
        final ArrayList<Aspect> primals = new ArrayList<Aspect>();
        final Collection<Aspect> pa = Aspect.aspects.values();
        for (final Aspect aspect : pa) {
            if (aspect.isPrimal()) {
                primals.add(aspect);
            }
        }
        return primals;
    }
    
    public static ArrayList<Aspect> getCompoundAspects() {
        final ArrayList<Aspect> compounds = new ArrayList<Aspect>();
        final Collection<Aspect> pa = Aspect.aspects.values();
        for (final Aspect aspect : pa) {
            if (!aspect.isPrimal()) {
                compounds.add(aspect);
            }
        }
        return compounds;
    }
    
    public String getChatcolor() {
        return this.chatcolor;
    }
    
    public void setChatcolor(final String chatcolor) {
        this.chatcolor = chatcolor;
    }
    
    static {
        Aspect.aspects = new LinkedHashMap<String, Aspect>();
        AIR = new Aspect("aer", 16777086, "e", 1);
        EARTH = new Aspect("terra", 5685248, "2", 1);
        FIRE = new Aspect("ignis", 16734721, "c", 1);
        WATER = new Aspect("aqua", 3986684, "3", 1);
        ORDER = new Aspect("ordo", 14013676, "7", 1);
        ENTROPY = new Aspect("perditio", 4210752, "8", 771);
        VOID = new Aspect("vacuos", 8947848, new Aspect[] { Aspect.AIR, Aspect.ENTROPY }, 771);
        LIGHT = new Aspect("lux", 16774755, new Aspect[] { Aspect.AIR, Aspect.FIRE });
        WEATHER = new Aspect("tempestas", 16777215, new Aspect[] { Aspect.AIR, Aspect.WATER });
        MOTION = new Aspect("motus", 13487348, new Aspect[] { Aspect.AIR, Aspect.ORDER });
        COLD = new Aspect("gelum", 14811135, new Aspect[] { Aspect.FIRE, Aspect.ENTROPY });
        CRYSTAL = new Aspect("vitreus", 8454143, new Aspect[] { Aspect.EARTH, Aspect.ORDER });
        LIFE = new Aspect("victus", 14548997, new Aspect[] { Aspect.WATER, Aspect.EARTH });
        POISON = new Aspect("venenum", 9039872, new Aspect[] { Aspect.WATER, Aspect.ENTROPY });
        ENERGY = new Aspect("potentia", 12648447, new Aspect[] { Aspect.ORDER, Aspect.FIRE });
        EXCHANGE = new Aspect("permutatio", 5735255, new Aspect[] { Aspect.ENTROPY, Aspect.ORDER });
        METAL = new Aspect("metallum", 11908557, new Aspect[] { Aspect.EARTH, Aspect.CRYSTAL });
        DEATH = new Aspect("mortuus", 8943496, new Aspect[] { Aspect.LIFE, Aspect.ENTROPY });
        FLIGHT = new Aspect("volatus", 15198167, new Aspect[] { Aspect.AIR, Aspect.MOTION });
        DARKNESS = new Aspect("tenebrae", 2236962, new Aspect[] { Aspect.VOID, Aspect.LIGHT });
        SOUL = new Aspect("spiritus", 15461371, new Aspect[] { Aspect.LIFE, Aspect.DEATH });
        HEAL = new Aspect("sano", 16723764, new Aspect[] { Aspect.LIFE, Aspect.ORDER });
        TRAVEL = new Aspect("iter", 14702683, new Aspect[] { Aspect.MOTION, Aspect.EARTH });
        ELDRITCH = new Aspect("alienis", 8409216, new Aspect[] { Aspect.VOID, Aspect.DARKNESS });
        MAGIC = new Aspect("praecantatio", 9896128, new Aspect[] { Aspect.VOID, Aspect.ENERGY });
        AURA = new Aspect("auram", 16761087, new Aspect[] { Aspect.MAGIC, Aspect.AIR });
        TAINT = new Aspect("vitium", 8388736, new Aspect[] { Aspect.MAGIC, Aspect.ENTROPY });
        SLIME = new Aspect("limus", 129024, new Aspect[] { Aspect.LIFE, Aspect.WATER });
        PLANT = new Aspect("herba", 109568, new Aspect[] { Aspect.LIFE, Aspect.EARTH });
        TREE = new Aspect("arbor", 8873265, new Aspect[] { Aspect.AIR, Aspect.PLANT });
        BEAST = new Aspect("bestia", 10445833, new Aspect[] { Aspect.MOTION, Aspect.LIFE });
        FLESH = new Aspect("corpus", 15615885, new Aspect[] { Aspect.DEATH, Aspect.BEAST });
        UNDEAD = new Aspect("exanimis", 3817472, new Aspect[] { Aspect.MOTION, Aspect.DEATH });
        MIND = new Aspect("cognitio", 16761523, new Aspect[] { Aspect.FIRE, Aspect.SOUL });
        SENSES = new Aspect("sensus", 1038847, new Aspect[] { Aspect.AIR, Aspect.SOUL });
        MAN = new Aspect("humanus", 16766912, new Aspect[] { Aspect.BEAST, Aspect.MIND });
        CROP = new Aspect("messis", 14791537, new Aspect[] { Aspect.PLANT, Aspect.MAN });
        MINE = new Aspect("perfodio", 14471896, new Aspect[] { Aspect.MAN, Aspect.EARTH });
        TOOL = new Aspect("instrumentum", 4210926, new Aspect[] { Aspect.MAN, Aspect.ORDER });
        HARVEST = new Aspect("meto", 15641986, new Aspect[] { Aspect.CROP, Aspect.TOOL });
        WEAPON = new Aspect("telum", 12603472, new Aspect[] { Aspect.TOOL, Aspect.FIRE });
        ARMOR = new Aspect("tutamen", 49344, new Aspect[] { Aspect.TOOL, Aspect.EARTH });
        HUNGER = new Aspect("fames", 10093317, new Aspect[] { Aspect.LIFE, Aspect.VOID });
        GREED = new Aspect("lucrum", 15121988, new Aspect[] { Aspect.MAN, Aspect.HUNGER });
        CRAFT = new Aspect("fabrico", 8428928, new Aspect[] { Aspect.MAN, Aspect.TOOL });
        CLOTH = new Aspect("pannus", 15395522, new Aspect[] { Aspect.TOOL, Aspect.BEAST });
        MECHANISM = new Aspect("machina", 8421536, new Aspect[] { Aspect.MOTION, Aspect.TOOL });
        TRAP = new Aspect("vinculum", 10125440, new Aspect[] { Aspect.MOTION, Aspect.ENTROPY });
    }
}
