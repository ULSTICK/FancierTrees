package com.ftteam.fanciertrees;

import net.neoforged.fml.ModList;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(FancierTrees.MOD_ID)
public class FancierTrees {
    public static final String MOD_ID = "fanciertrees";
    public static final Logger LOGGER = LogUtils.getLogger();

    public FancierTrees(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        ModBlocks.registerBranches(modEventBus);
        if (ModList.get().isLoaded("vanillabackport")) {
            ModBlocksBackport.registerBranches(modEventBus);
        }
        if (ModList.get().isLoaded("biomesoplenty")) {
            ModBlocksBoP.registerBranches(modEventBus);
        }
        ModItemGroup.registerBranchItemGroup(modEventBus);
    }

    private void commonSetup(FMLCommonSetupEvent event) {

    }
}
