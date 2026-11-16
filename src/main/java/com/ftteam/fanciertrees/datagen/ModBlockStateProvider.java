package com.ftteam.fanciertrees.datagen;

import com.ftteam.fanciertrees.BranchBlock;
import com.ftteam.fanciertrees.FancierTrees;
import com.ftteam.fanciertrees.ModBlocks;
import com.ftteam.fanciertrees.ModBlocksBoP;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, FancierTrees.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        branchBlock(ModBlocks.OAK_BRANCH.get(), "oak_branch", "minecraft", "block/oak_log");
        branchBlock(ModBlocks.SPRUCE_BRANCH.get(), "spruce_branch", "minecraft", "block/spruce_log");
        branchBlock(ModBlocks.BIRCH_BRANCH.get(), "birch_branch", "minecraft", "block/birch_log");
        branchBlock(ModBlocks.JUNGLE_BRANCH.get(), "jungle_branch", "minecraft", "block/jungle_log");
        branchBlock(ModBlocks.ACACIA_BRANCH.get(), "acacia_branch", "minecraft", "block/acacia_log");
        branchBlock(ModBlocks.DARK_OAK_BRANCH.get(), "dark_oak_branch", "minecraft", "block/dark_oak_log");
        branchBlock(ModBlocks.MANGROVE_BRANCH.get(), "mangrove_branch", "minecraft", "block/mangrove_log");
        branchBlock(ModBlocks.CHERRY_BRANCH.get(), "cherry_branch", "minecraft", "block/cherry_log");
        branchBlock(ModBlocks.CRIMSON_BRANCH.get(), "crimson_branch", "minecraft", "block/crimson_stem");
        branchBlock(ModBlocks.WARPED_BRANCH.get(), "warped_branch", "minecraft", "block/warped_stem");

        //branchBlock(ModBlocksBoP.FIR_BRANCH.get(), "fir_branch", "biomesoplenty", "block/fir_log");
        //branchBlock(ModBlocksBoP.PINE_BRANCH.get(), "pine_branch", "biomesoplenty", "block/pine_log");
        //branchBlock(ModBlocksBoP.MAPLE_BRANCH.get(), "maple_branch", "biomesoplenty", "block/maple_log");
        //branchBlock(ModBlocksBoP.REDWOOD_BRANCH.get(), "redwood_branch", "biomesoplenty", "block/redwood_log");
        //branchBlock(ModBlocksBoP.MAHOGANY_BRANCH.get(), "mahogany_branch", "biomesoplenty", "block/mahogany_log");
        //branchBlock(ModBlocksBoP.JACARANDA_BRANCH.get(), "jacaranda_branch", "biomesoplenty", "block/jacaranda_log");
        //branchBlock(ModBlocksBoP.PALM_BRANCH.get(), "palm_branch", "biomesoplenty", "block/palm_log");
        //branchBlock(ModBlocksBoP.WILLOW_BRANCH.get(), "willow_branch", "biomesoplenty", "block/willow_log");
        //branchBlock(ModBlocksBoP.DEAD_BRANCH.get(), "dead_branch", "biomesoplenty", "block/dead_log");
        //branchBlock(ModBlocksBoP.MAGIC_BRANCH.get(), "magic_branch", "biomesoplenty", "block/magic_log");
        //branchBlock(ModBlocksBoP.UMBRAN_BRANCH.get(), "umbran_branch", "biomesoplenty", "block/umbran_log");
        //branchBlock(ModBlocksBoP.HELLBARK_BRANCH.get(), "hellbark_branch", "biomesoplenty", "block/hellbark_log");
        //branchBlock(ModBlocksBoP.EMPYREAL_BRANCH.get(), "empyreal_branch", "biomesoplenty", "block/empyreal_log");
    }

    public void sixWayBlock(BranchBlock block, ModelFile base, ModelFile side) {
        MultiPartBlockStateBuilder builder = getMultipartBuilder(block);

        builder.part()
                .modelFile(base)
                .addModel()
                .useOr()
                .nestedGroup()
                .useOr()
                    .condition(BranchBlock.UP, true)
                    .condition(BranchBlock.DOWN, true)
                .end()
                .nestedGroup()
                    .condition(BranchBlock.UP, false)
                    .condition(BranchBlock.DOWN, false)
                    .condition(BranchBlock.EAST, false)
                    .condition(BranchBlock.WEST, false)
                    .condition(BranchBlock.NORTH, false)
                    .condition(BranchBlock.SOUTH, false)
                .end()
                .end();

        builder.part()
                .modelFile(base)
                .rotationX(90)
                .addModel()
                .nestedGroup()
                    .useOr()
                    .condition(BranchBlock.NORTH, true)
                    .condition(BranchBlock.SOUTH, true)
                .end()
                .nestedGroup()
                    .condition(BranchBlock.UP, false)
                    .condition(BranchBlock.DOWN, false)
                .end()
                .end();

        builder.part()
                .modelFile(base)
                .rotationX(90)
                .rotationY(90)
                .addModel()
                .nestedGroup()
                    .useOr()
                    .condition(BranchBlock.EAST, true)
                    .condition(BranchBlock.WEST, true)
                .end()
                .nestedGroup()
                    .condition(BranchBlock.UP, false)
                    .condition(BranchBlock.DOWN, false)
                    .condition(BranchBlock.NORTH, false)
                    .condition(BranchBlock.SOUTH, false)
                .end()
                .end();

        this.sixWayMultipart(builder, side);
    }

    public void sixWayMultipart(MultiPartBlockStateBuilder builder, ModelFile side) {
        PipeBlock.PROPERTY_BY_DIRECTION.forEach((dir, property) -> {
            int x = 0;
            int y = 0;

            switch (dir) {
                case NORTH -> x = 90;
                case SOUTH -> x = 270;
                case EAST  -> {
                    x = 90;
                    y = 90;
                }
                case WEST  -> {
                    x = 90;
                    y = 270;
                }
                case UP    -> x = 0;
                case DOWN  -> x = 180;
            }

            builder.part()
                    .modelFile(side)
                    .rotationX(x)
                    .rotationY(y)
                    .addModel()
                    .condition(property, true);
        });
    }

    public void branchBlock(BranchBlock block, String name, String textureNamespace, String texturePath) {
        ResourceLocation logTexture = ResourceLocation.fromNamespaceAndPath(
                textureNamespace, texturePath
        );
        ResourceLocation topTexture = ResourceLocation.fromNamespaceAndPath(
                textureNamespace, texturePath + "_top"
        );

        this.sixWayBlock(
                block,
                this.models().getBuilder(name + "_base")
                        .parent(new ModelFile.UncheckedModelFile("fanciertrees:block/branch_base"))
                        .texture("log", logTexture),
                this.models().getBuilder(name + "_side")
                        .parent(new ModelFile.UncheckedModelFile("fanciertrees:block/branch_side"))
                        .texture("log", logTexture)
                        .texture("log_top", topTexture)
                );
    }

    public void blockWithItem(DeferredBlock<?> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }
}
