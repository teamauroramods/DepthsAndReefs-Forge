package com.teamaurora.depths_and_reefs.core.registry;

import com.minecraftabnormals.abnormals_core.core.util.registry.BlockSubRegistryHelper;
import com.teamaurora.depths_and_reefs.common.block.SeaAlgaeBlock;
import com.teamaurora.depths_and_reefs.core.DepthsAndReefs;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DepthsAndReefs.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DRBlocks {
    public static final BlockSubRegistryHelper HELPER = DepthsAndReefs.REGISTRY_HELPER.getBlockSubHelper();

    // conglomerate
    public static final RegistryObject<Block> CONGLOMERATE = HELPER.createBlock("conglomerate", ()->new Block(Properties.CONGLOMERATE), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> TIDE_CONGLOMERATE = HELPER.createBlock("tide_conglomerate", ()->new Block(Properties.CONGLOMERATE), ItemGroup.BUILDING_BLOCKS);

    // shale
    public static final RegistryObject<Block> SHALE = HELPER.createBlock("shale", ()->new Block(Properties.SHALE), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> TIDE_SHALE = HELPER.createBlock("tide_shale", ()->new Block(Properties.SHALE), ItemGroup.BUILDING_BLOCKS);

    // algae & seagrass
    public static final RegistryObject<Block> SEAGRASS_PATCH = HELPER.createBlock("seagrass_patch", ()->new Block(AbstractBlock.Properties.from(Blocks.SAND)), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> SEA_ALGAE_SAND = HELPER.createBlock("sea_algae_sand", ()->new Block(AbstractBlock.Properties.from(Blocks.SAND)), ItemGroup.BUILDING_BLOCKS);
    public static final RegistryObject<Block> SEA_ALGAE = HELPER.createBlock("sea_algae", ()->new SeaAlgaeBlock(Properties.SEA_ALGAE), ItemGroup.DECORATIONS);

    public static class Properties {
        public static final AbstractBlock.Properties CONGLOMERATE = AbstractBlock.Properties.from(Blocks.STONE);
        public static final AbstractBlock.Properties SHALE = AbstractBlock.Properties.from(Blocks.STONE);

        public static final AbstractBlock.Properties SEA_ALGAE = AbstractBlock.Properties.create(Material.OCEAN_PLANT, MaterialColor.LIME).zeroHardnessAndResistance().sound(SoundType.PLANT).notSolid().doesNotBlockMovement();
    }
}
