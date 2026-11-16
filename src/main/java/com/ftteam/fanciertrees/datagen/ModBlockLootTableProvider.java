package com.ftteam.fanciertrees.datagen;

import com.ftteam.fanciertrees.ModBlocks;
import com.ftteam.fanciertrees.ModBlocksBoP;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.neoforged.fml.ModList;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {

    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        addBranch(ModBlocks.OAK_BRANCH.get(), Items.OAK_LOG);
        addBranch(ModBlocks.SPRUCE_BRANCH.get(), Items.SPRUCE_LOG);
        addBranch(ModBlocks.BIRCH_BRANCH.get(), Items.BIRCH_LOG);
        addBranch(ModBlocks.JUNGLE_BRANCH.get(), Items.JUNGLE_LOG);
        addBranch(ModBlocks.ACACIA_BRANCH.get(), Items.ACACIA_LOG);
        addBranch(ModBlocks.DARK_OAK_BRANCH.get(), Items.DARK_OAK_LOG);
        addBranch(ModBlocks.MANGROVE_BRANCH.get(), Items.MANGROVE_LOG);
        addBranch(ModBlocks.CHERRY_BRANCH.get(), Items.CHERRY_LOG);
        addBranch(ModBlocks.CRIMSON_BRANCH.get(), Items.CRIMSON_STEM);
        addBranch(ModBlocks.WARPED_BRANCH.get(), Items.WARPED_STEM);
    }

    private void addBranch(Block pBlock, Item log) {
        add(pBlock, block -> branchDrops(pBlock, log));
    }

    protected LootTable.Builder branchDrops(Block pBlock, Item log) {
        HolderLookup.RegistryLookup<Enchantment> registryLookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(pBlock,
                    this.applyExplosionDecay(pBlock, LootItem.lootTableItem(log).when(LootItemRandomChanceCondition.randomChance(0.25F))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
