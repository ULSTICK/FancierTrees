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

public class ModBlocksBoP {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MOD_ID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);

    public static final DeferredBlock<BranchBlock> FIR_BRANCH = registerBlock("bop_fir_branch",
            () -> new BranchBlock
                    (BlockBehaviour.Properties.of()
                            .mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)
                            .strength(1.0F)
                            .sound(SoundType.WOOD)
                            .ignitedByLava()));

    public static final DeferredBlock<BranchBlock> PINE_BRANCH = registerBlock("bop_pine_branch",
            () -> new BranchBlock
                    (BlockBehaviour.Properties.of()
                            .mapColor(MapColor.DIRT)
                            .strength(1.0F)
                            .sound(SoundType.WOOD)
                            .ignitedByLava()));

    public static final DeferredBlock<BranchBlock> MAPLE_BRANCH = registerBlock("bop_maple_branch",
            () -> new BranchBlock
                    (BlockBehaviour.Properties.of()
                            .mapColor(MapColor.TERRACOTTA_BROWN)
                            .strength(1.0F)
                            .sound(SoundType.CHERRY_WOOD)
                            .ignitedByLava()));

    public static final DeferredBlock<BranchBlock> REDWOOD_BRANCH = registerBlock("bop_redwood_branch",
            () -> new BranchBlock
                    (BlockBehaviour.Properties.of()
                            .mapColor(MapColor.TERRACOTTA_ORANGE)
                            .strength(1.0F)
                            .sound(SoundType.WOOD)
                            .ignitedByLava()));

    public static final DeferredBlock<BranchBlock> MAHOGANY_BRANCH = registerBlock("bop_mahogany_branch",
            () -> new BranchBlock
                    (BlockBehaviour.Properties.of()
                            .mapColor(MapColor.DIRT)
                            .strength(1.0F)
                            .sound(SoundType.WOOD)
                            .ignitedByLava()));

    public static final DeferredBlock<BranchBlock> JACARANDA_BRANCH = registerBlock("bop_jacaranda_branch",
            () -> new BranchBlock
                    (BlockBehaviour.Properties.of()
                            .mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)
                            .strength(1.0F)
                            .sound(SoundType.CHERRY_WOOD)
                            .ignitedByLava()));

    public static final DeferredBlock<BranchBlock> PALM_BRANCH = registerBlock("bop_palm_branch",
            () -> new BranchBlock
                    (BlockBehaviour.Properties.of()
                            .mapColor(MapColor.PODZOL)
                            .strength(1.0F)
                            .sound(SoundType.WOOD)
                            .ignitedByLava()));

    public static final DeferredBlock<BranchBlock> WILLOW_BRANCH = registerBlock("bop_willow_branch",
            () -> new BranchBlock
                    (BlockBehaviour.Properties.of()
                            .mapColor(MapColor.TERRACOTTA_LIGHT_GREEN)
                            .strength(1.0F)
                            .sound(SoundType.WOOD)
                            .ignitedByLava()));

    public static final DeferredBlock<BranchBlock> DEAD_BRANCH = registerBlock("bop_dead_branch",
            () -> new BranchBlock
                    (BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_GRAY)
                            .strength(1.0F)
                            .sound(SoundType.WOOD)
                            .ignitedByLava()));

    public static final DeferredBlock<BranchBlock> MAGIC_BRANCH = registerBlock("bop_magic_branch",
            () -> new BranchBlock
                    (BlockBehaviour.Properties.of()
                            .mapColor(MapColor.TERRACOTTA_LIGHT_BLUE)
                            .strength(1.0F)
                            .sound(SoundType.CHERRY_WOOD)
                            .ignitedByLava()));

    public static final DeferredBlock<BranchBlock> UMBRAN_BRANCH = registerBlock("bop_umbran_branch",
            () -> new BranchBlock
                    (BlockBehaviour.Properties.of()
                            .mapColor(MapColor.TERRACOTTA_BLUE)
                            .strength(1.0F)
                            .sound(SoundType.NETHER_WOOD)));

    public static final DeferredBlock<BranchBlock> HELLBARK_BRANCH = registerBlock("bop_hellbark_branch",
            () -> new BranchBlock
                    (BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_LIGHT_GRAY)
                            .strength(1.0F)
                            .sound(SoundType.NETHER_WOOD)));

    public static final DeferredBlock<BranchBlock> EMPYREAL_BRANCH = registerBlock("bop_empyreal_branch",
            () -> new BranchBlock
                    (BlockBehaviour.Properties.of()
                            .mapColor(MapColor.QUARTZ)
                            .strength(1.0F)
                            .sound(SoundType.NETHER_WOOD)));

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void registerBranches(IEventBus eventBus) {
        FancierTrees.LOGGER.info("Registering BoP BRANCH!");
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
    }
}
