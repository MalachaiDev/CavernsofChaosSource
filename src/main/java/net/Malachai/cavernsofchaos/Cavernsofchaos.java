package net.malachai.cavernsofchaos;

import com.mojang.logging.LogUtils;
import net.malachai.cavernsofchaos.block.ModBlockEntities;
import net.malachai.cavernsofchaos.block.ModBlocks;
import net.malachai.cavernsofchaos.entity.ModEntities;
import net.malachai.cavernsofchaos.entity.client.*;
import net.malachai.cavernsofchaos.item.ModCreativeModeTabs;
import net.malachai.cavernsofchaos.item.ModItems;
import net.malachai.cavernsofchaos.particle.ModParticles;
import net.malachai.cavernsofchaos.sound.ModSounds;
import net.malachai.cavernsofchaos.worldgen.biome.ModTerrablender;
import net.malachai.cavernsofchaos.worldgen.biome.surface.ModSurfaceRules;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;
import terrablender.api.SurfaceRuleManager;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Cavernsofchaos.MODID)
public class Cavernsofchaos
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "cavernsofchaos";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public Cavernsofchaos()
    {

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();


        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);

        ModBlocks.register(modEventBus);

        ModBlockEntities.register(modEventBus);

        ModParticles.register(modEventBus);

        ModEntities.register(modEventBus);

        ModSounds.register(modEventBus);

        ModTerrablender.registerBiomes();


        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);


        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, MODID, ModSurfaceRules.makeRules());
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));

    }

    // Add the example block item to the building blocks tab


    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {




        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {


            EntityRenderers.register(ModEntities.TORCHWRAITH.get(), TorchwraithRenderer::new);
            EntityRenderers.register(ModEntities.LIZAGER.get(), lizagerRenderer::new);
            EntityRenderers.register(ModEntities.FLYSKULL.get(), flyskullRenderer::new);
            EntityRenderers.register(ModEntities.BOULDER_GOLEM.get(), BoulderGolemRenderer::new);
            EntityRenderers.register(ModEntities.MIMIC.get(), MimicRenderer::new);
            EntityRenderers.register(ModEntities.CAVESLIME.get(), CaveSlimeRenderer::new);

            EntityRenderers.register(ModEntities.GLOWSTICK_PROJECTILE.get(), ThrownItemRenderer::new);
            EntityRenderers.register(ModEntities.NITROBOTTLE_PROJECTILE.get(), ThrownItemRenderer::new);
            EntityRenderers.register(ModEntities.BOOMSTICK_PROJECTILE.get(), ThrownItemRenderer::new);
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());

        }
    }
}
