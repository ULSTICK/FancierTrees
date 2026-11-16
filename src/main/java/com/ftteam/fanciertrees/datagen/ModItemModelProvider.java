package com.ftteam.fanciertrees.datagen;

import com.ftteam.fanciertrees.BranchBlock;
import com.ftteam.fanciertrees.FancierTrees;
import com.ftteam.fanciertrees.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, FancierTrees.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        branchItem(ModBlocks.OAK_BRANCH, "minecraft", "block/oak_log");
        branchItem(ModBlocks.SPRUCE_BRANCH,  "minecraft", "block/spruce_log");
        branchItem(ModBlocks.BIRCH_BRANCH, "minecraft", "block/birch_log");
        branchItem(ModBlocks.JUNGLE_BRANCH, "minecraft", "block/jungle_log");
        branchItem(ModBlocks.ACACIA_BRANCH, "minecraft", "block/acacia_log");
        branchItem(ModBlocks.DARK_OAK_BRANCH, "minecraft", "block/dark_oak_log");
        branchItem(ModBlocks.MANGROVE_BRANCH, "minecraft", "block/mangrove_log");
        branchItem(ModBlocks.CHERRY_BRANCH, "minecraft", "block/cherry_log");
        branchItem(ModBlocks.CRIMSON_BRANCH, "minecraft", "block/crimson_stem");
        branchItem(ModBlocks.WARPED_BRANCH, "minecraft", "block/warped_stem");
    }

    public void branchItem(DeferredBlock<?> block, String textureNamespace, String texturePath) {
        ResourceLocation logTexture = ResourceLocation.fromNamespaceAndPath(
                textureNamespace, texturePath
        );
        ResourceLocation topTexture = ResourceLocation.fromNamespaceAndPath(
                textureNamespace, texturePath + "_top"
        );

        this.withExistingParent(block.getId().getPath(), modLoc("block/branch_item"))
                .texture("log", logTexture)
                .texture("log_top", topTexture);
    }
}
