package com.baisylia.cookscollection;

import com.baisylia.cookscollection.advancement.ModAdvancements;
import com.baisylia.cookscollection.block.ModBlocks;
import com.baisylia.cookscollection.block.custom.SaltBlock;
import com.baisylia.cookscollection.block.entity.ModBlockEntities;
import com.baisylia.cookscollection.block.entity.screen.ModMenus;
import com.baisylia.cookscollection.block.entity.screen.OvenScreen;
import com.baisylia.cookscollection.client.ModSounds;
import com.baisylia.cookscollection.item.ModItems;
import com.baisylia.cookscollection.recipe.ModRecipes;
import com.baisylia.cookscollection.tab.ModCreativeModeTabs;
import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

@Mod(CooksCollection.MOD_ID)
public class CooksCollection {
    public static final String MOD_ID = "cookscollection";
    private static final Logger LOGGER = LogUtils.getLogger();

    public CooksCollection(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        ModCreativeModeTabs.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenus.register(modEventBus);
        ModRecipes.register(modEventBus);
        ModSounds.register(modEventBus);
        ModAdvancements.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
        modEventBus.addListener(this::registerScreens);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

    }

    public static ResourceLocation locate(String identifier) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, identifier);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    @SubscribeEvent
    public void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            BlockPos pos = event.getPos();
            BlockState placed = event.getPlacedBlock();
            LevelAccessor level = event.getLevel();

            if (placed.is(Blocks.POINTED_DRIPSTONE) && placed.getValue(PointedDripstoneBlock.TIP_DIRECTION) == Direction.DOWN) {
                BlockState above = level.getBlockState(pos.above());
                if (above.is(ModBlocks.SALT_BLOCK.get()) && SaltBlock.isWaterAbove(level, pos.above())) {
                    ModAdvancements.GROW_SALT_SPIKE.get().trigger(player);
                }
            } else if (placed.is(ModBlocks.SALT_BLOCK.get())) {
                BlockState below = level.getBlockState(pos.below());
                if (below.is(Blocks.POINTED_DRIPSTONE) && below.getValue(PointedDripstoneBlock.TIP_DIRECTION) == Direction.DOWN) {
                    if (SaltBlock.isWaterAbove(level, pos)) {
                        ModAdvancements.GROW_SALT_SPIKE.get().trigger(player);
                    }
                }
            }
        }
    }

    public void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenus.OVEN_MENU.get(), OvenScreen::new);
    }

    @EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.LEMON_SAPLING.get(), RenderType.cutoutMipped());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.SALT_SPIKE.get(), RenderType.cutout());
        }
    }
}
