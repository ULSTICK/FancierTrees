package com.ftteam.fanciertrees;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModItemGroup {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FancierTrees.MOD_ID);

    public static final Supplier<CreativeModeTab> BRANCH_ITEM_GROUP = CREATIVE_MODE_TABS.register("branch",
        () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(ModBlocks.OAK_BRANCH.get()))
            .title(Component.translatable("itemGroup." + FancierTrees.MOD_ID))
            .displayItems((params, entries) -> {
                entries.accept(ModBlocks.OAK_BRANCH.get());
                entries.accept(ModBlocks.SPRUCE_BRANCH.get());
                entries.accept(ModBlocks.BIRCH_BRANCH.get());
                entries.accept(ModBlocks.JUNGLE_BRANCH.get());
                entries.accept(ModBlocks.ACACIA_BRANCH.get());
                entries.accept(ModBlocks.DARK_OAK_BRANCH.get());
                entries.accept(ModBlocks.MANGROVE_BRANCH.get());
                entries.accept(ModBlocks.CHERRY_BRANCH.get());
                entries.accept(ModBlocks.CRIMSON_BRANCH.get());
                entries.accept(ModBlocks.WARPED_BRANCH.get());

                if (ModList.get().isLoaded("biomesoplenty")) {
                    entries.accept(ModBlocksBoP.DEAD_BRANCH.get());
                    entries.accept(ModBlocksBoP.FIR_BRANCH.get());
                    entries.accept(ModBlocksBoP.JACARANDA_BRANCH.get());
                    entries.accept(ModBlocksBoP.MAHOGANY_BRANCH.get());
                    entries.accept(ModBlocksBoP.MAPLE_BRANCH.get());
                    entries.accept(ModBlocksBoP.PALM_BRANCH.get());
                    entries.accept(ModBlocksBoP.PINE_BRANCH.get());
                    entries.accept(ModBlocksBoP.REDWOOD_BRANCH.get());
                    entries.accept(ModBlocksBoP.WILLOW_BRANCH.get());
                    entries.accept(ModBlocksBoP.MAGIC_BRANCH.get());
                    entries.accept(ModBlocksBoP.UMBRAN_BRANCH.get());
                    entries.accept(ModBlocksBoP.EMPYREAL_BRANCH.get());
                    entries.accept(ModBlocksBoP.HELLBARK_BRANCH.get());
                }
            })
            .build());

    public static void registerBranchItemGroup(IEventBus modEventBus) {
        CREATIVE_MODE_TABS.register(modEventBus);
    }
}
