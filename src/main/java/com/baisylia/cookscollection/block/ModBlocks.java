package com.baisylia.cookscollection.block;

import com.baisylia.cookscollection.CooksCollection;
import com.baisylia.cookscollection.block.custom.*;
import com.baisylia.cookscollection.item.ModItems;
import com.baisylia.cookscollection.world.tree.ModTreeGrowers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(CooksCollection.MOD_ID);

    public static final DeferredBlock<Block> LEMON_CRATE = registerBlock("lemon_crate",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(vectorwing.farmersdelight.common.registry.ModBlocks.CARROT_CRATE.get())));

    public static final DeferredBlock<Block> LEMON_SAPLING = registerBlock("lemon_sapling",
            () -> new SaplingBlock(ModTreeGrowers.LEMON, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)));

    public static final DeferredBlock<Block> LEMON_LOG = registerBlock("lemon_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)));

    public static final DeferredBlock<Block> LEMON_WOOD = registerBlock("lemon_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)));

    public static final DeferredBlock<Block> LEMON_LEAVES = registerBlock("lemon_leaves",
            () -> new LeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                    return 60;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                    return 30;
                }
            });

    public static final DeferredBlock<Block> FRUITING_LEMON_LEAVES = registerBlock("fruiting_lemon_leaves",
            () -> new FruitingLeaves(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES), ModItems.LEMON) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                    return 60;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                    return 30;
                }
            });

    public static final DeferredBlock<Block> RUSTIC_LOAF = registerBlock("rustic_loaf",
            () -> new RusticLoafBlock(BlockBehaviour.Properties.ofFullCopy(vectorwing.farmersdelight.common.registry.ModBlocks.APPLE_PIE.get()).noOcclusion(),
                    ModItems.RUSTIC_LOAF_SLICE));

    public static final DeferredBlock<Block> SALT_BLOCK = registerBlock("salt_block",
            () -> new SaltBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DRIPSTONE_BLOCK).randomTicks()));

    public static final DeferredBlock<Block> SALT_SPIKE = registerBlock("salt_spike",
            () -> new SaltSpikeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.POINTED_DRIPSTONE)
                    .offsetType(BlockBehaviour.OffsetType.NONE)
                    .noOcclusion()
                    .sound(SoundType.POINTED_DRIPSTONE)
                    .strength(1.5F, 3.0F)
                    .pushReaction(PushReaction.DESTROY)));

    public static final DeferredBlock<Block> OVEN = registerBlock("oven",
            () -> new OvenBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS)));


    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
