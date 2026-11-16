package com.ftteam.fanciertrees;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.ftteam.fanciertrees.FancierTrees.MOD_ID;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MOD_ID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);

    public static final DeferredBlock<BranchBlock> OAK_BRANCH = registerBlock("oak_branch",
        () -> new BranchBlock
            (BlockBehaviour.Properties.of()
                .mapColor(MapColor.PODZOL)
                .strength(1.0F)
                .sound(SoundType.WOOD)
                .ignitedByLava()));
    public static final DeferredBlock<BranchBlock> SPRUCE_BRANCH = registerBlock("spruce_branch",
        () -> new BranchBlock
            (BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_BROWN)
                .strength(1.0F)
                .sound(SoundType.WOOD)
                .ignitedByLava()));
    public static final DeferredBlock<BranchBlock> BIRCH_BRANCH = registerBlock("birch_branch",
        () -> new BranchBlock
            (BlockBehaviour.Properties.of()
                .mapColor(MapColor.QUARTZ)
                .strength(1.0F)
                .sound(SoundType.WOOD)
                .ignitedByLava()));
    public static final DeferredBlock<BranchBlock> JUNGLE_BRANCH = registerBlock("jungle_branch",
        () -> new BranchBlock
            (BlockBehaviour.Properties.of()
                .mapColor(MapColor.PODZOL)
                .strength(1.0F)
                .sound(SoundType.WOOD)
                .ignitedByLava()));
    public static final DeferredBlock<BranchBlock> ACACIA_BRANCH = registerBlock("acacia_branch",
        () -> new BranchBlock
            (BlockBehaviour.Properties.of()
                .mapColor(MapColor.STONE)
                .strength(1.0F)
                .sound(SoundType.WOOD)
                .ignitedByLava()));
    public static final DeferredBlock<BranchBlock> DARK_OAK_BRANCH = registerBlock("dark_oak_branch",
        () -> new BranchBlock
            (BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_BROWN)
                .strength(1.0F)
                .sound(SoundType.WOOD)
                .ignitedByLava()));
    public static final DeferredBlock<BranchBlock> MANGROVE_BRANCH = registerBlock("mangrove_branch",
        () -> new BranchBlock
            (BlockBehaviour.Properties.of()
                .mapColor(MapColor.PODZOL)
                .strength(1.0F)
                .sound(SoundType.WOOD)
                .ignitedByLava()));
    public static final DeferredBlock<BranchBlock> CHERRY_BRANCH = registerBlock("cherry_branch",
        () -> new BranchBlock
            (BlockBehaviour.Properties.of()
                .mapColor(MapColor.TERRACOTTA_GRAY)
                .strength(1.0F)
                .sound(SoundType.CHERRY_WOOD)
                .ignitedByLava()));
    public static final DeferredBlock<BranchBlock> CRIMSON_BRANCH = registerBlock("crimson_branch",
        () -> new BranchBlock
            (BlockBehaviour.Properties.of()
                .mapColor(MapColor.CRIMSON_STEM)
                .strength(1.0F)
                .sound(SoundType.STEM)));
    public static final DeferredBlock<BranchBlock> WARPED_BRANCH = registerBlock("warped_branch",
        () -> new BranchBlock
            (BlockBehaviour.Properties.of()
                .mapColor(MapColor.WARPED_STEM)
                .strength(1.0F)
                .sound(SoundType.STEM)));

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void registerBranches(IEventBus eventBus) {
        FancierTrees.LOGGER.info("Registering BRANCH!");
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
    }
}
