package com.baisylia.cookscollection.tab;

import com.baisylia.cookscollection.CooksCollection;
import com.baisylia.cookscollection.block.ModBlocks;
import com.baisylia.cookscollection.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CooksCollection.MOD_ID);

    public static final Supplier<CreativeModeTab> COOKSCOLLECTION_TAB = CREATIVE_MODE_TAB.register("cookscollections_tab", () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(ModItems.LEMON.get()))
            .title(Component.translatable("creativetab.cookscollection.cookscollections_tab"))
            .displayItems((itemDisplayParameters, output) -> {
                output.accept(ModItems.LEMON);
                output.accept(ModItems.SALT);
                output.accept(ModItems.COOKING_OIL);
                output.accept(ModItems.CHOCOLATE_MUFFIN);
                output.accept(ModItems.LEMON_MUFFIN);
                output.accept(ModItems.FRIED_POTATO);
                output.accept(ModItems.FISH_AND_CHIPS);
                output.accept(ModItems.LEMONADE);
                output.accept(ModBlocks.RUSTIC_LOAF);
                output.accept(ModItems.RUSTIC_LOAF_SLICE);
                output.accept(ModBlocks.LEMON_SAPLING);
                output.accept(ModBlocks.LEMON_LOG);
                output.accept(ModBlocks.LEMON_WOOD);
                output.accept(ModBlocks.LEMON_LEAVES);
                output.accept(ModBlocks.FRUITING_LEMON_LEAVES);
                output.accept(ModBlocks.SALT_BLOCK);
                output.accept(ModBlocks.SALT_SPIKE);
                output.accept(ModBlocks.LEMON_CRATE);
                output.accept(ModBlocks.OVEN);

            }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
