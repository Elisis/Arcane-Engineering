package arcane_engineering;

import net.minecraft.creativetab.*;
import cpw.mods.fml.common.network.simpleimpl.*;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.network.*;
import cpw.mods.fml.relauncher.*;
import arcane_engineering.blocks.tiles.*;
import cpw.mods.fml.common.registry.*;
import cpw.mods.fml.common.event.*;

@Mod(modid = "arcane_engineering", name = "Arcane Engineering", version = "0.2.7", dependencies = "required-after:Forge;required-after:ImmersiveEngineering;after:Thaumcraft")
public class ArcaneEngineering
{
    public static CreativeTabs tabArcaneEngineering;
    public static SimpleNetworkWrapper network;
    @Mod.Instance
    public static ArcaneEngineering instance;
    @SidedProxy(serverSide = "arcane_engineering.CommonProxy", clientSide = "arcane_engineering.client.ClientProxy")
    public static CommonProxy proxy;
    
    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        AEContent.preInitItems();
        AEContent.preInitBlocks();
        ArcaneEngineering.proxy.registerRenders();
        (ArcaneEngineering.network = NetworkRegistry.INSTANCE.newSimpleChannel("MessageChannel")).registerMessage((Class)FallDamageMessage.Handler.class, (Class)FallDamageMessage.class, 0, Side.SERVER);
        ArcaneEngineering.network.registerMessage((Class)BootParticleMessage.Handler.class, (Class)BootParticleMessage.class, 1, Side.SERVER);
        ArcaneEngineering.network.registerMessage((Class)BootParticleMessage.Handler.class, (Class)BootParticleMessage.class, 2, Side.CLIENT);
    }
    
    @Mod.EventHandler
    public void Init(final FMLInitializationEvent event) {
        GameRegistry.registerTileEntity((Class)TileEntityDestabilizer.class, "destabliizer");
        AEContent.initCraftingRecipes();
    }
    
    @Mod.EventHandler
    public void postInit(final FMLPostInitializationEvent event) {
        AEResearch.setupResearchPages();
        AEResearch.registerRecipes();
        AEResearch.registerResearch();
    }
    
    static {
        ArcaneEngineering.tabArcaneEngineering = new AECreativeTab(CreativeTabs.getNextID(), "arcaneengineering");
        ArcaneEngineering.instance = new ArcaneEngineering();
    }
}
