package dev.notkili.pixelmon.bossconfigurator;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.battles.attacks.Attack;
import com.pixelmonmod.pixelmon.battles.attacks.AttackBase;
import dev.notkili.pixelmon.bossconfigurator.Config.Loader;
import dev.notkili.pixelmon.bossconfigurator.Events.BossSpawnEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;

@Mod(
        modid = Main.MOD_ID,
        name = Main.MOD_NAME,
        version = Main.VERSION,
        serverSideOnly = true,
        acceptableRemoteVersions = "*",
        dependencies = "required-after:pixelmon@[1.12.2-8.3.6,);"
)
public class Main {

    public static final String MOD_ID = "bossconfigurator";
    public static final String MOD_NAME = "BossConfigurator";
    public static final String VERSION = "1.1-Release";

    private Loader loader;

    @Mod.Instance(MOD_ID)
    public static Main INSTANCE;

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        loader = new Loader(event.getModConfigurationDirectory().getAbsolutePath());
        if (loader.init()) {
            Pixelmon.EVENT_BUS.register(new BossSpawnEvent(loader.getData(), loader.getConfig()));
        } else {
            LogManager.getLogger().warn("A fatal error occured while reading the config, the mod wont enable");
        }
    }
}
