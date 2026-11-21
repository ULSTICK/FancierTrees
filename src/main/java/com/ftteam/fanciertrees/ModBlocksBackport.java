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

public class ModBlocksBackport {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MOD_ID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);

    public static final DeferredBlock<BranchBlock> PALE_OAK_BRANCH = registerBlock("pale_oak_branch",
            () -> new BranchBlock
                    (BlockBehaviour.Properties.of()
                            .mapColor(MapColor.STONE)
                            .strength(1.0F)
                            .sound(SoundType.WOOD)
                            .ignitedByLava()));

    public static final DeferredBlock<BranchBlock> POPLAR_BRANCH = registerBlock("poplar_branch",
            () -> new BranchBlock
                    (BlockBehaviour.Properties.of()
                            .mapColor(MapColor.PODZOL)
                            .strength(1.0F)
                            .sound(SoundType.WOOD)
                            .ignitedByLava()));

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void registerBranches(IEventBus eventBus) {
        FancierTrees.LOGGER.info("Registering Vanilla Backport BRANCH!");
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
    }
}
