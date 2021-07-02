package com.teamaurora.depths_and_reefs.core.registry;

import com.minecraftabnormals.abnormals_core.core.util.registry.ItemSubRegistryHelper;
import com.teamaurora.depths_and_reefs.core.DepthsAndReefs;
import net.minecraft.block.BushBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DepthsAndReefs.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DRItems {
    public static final ItemSubRegistryHelper HELPER = DepthsAndReefs.REGISTRY_HELPER.getItemSubHelper();

    public static final RegistryObject<Item> ULVA = HELPER.createItem("ulva", ()->new BlockNamedItem(DRBlocks.ULVA_CARPET.get(), new Item.Properties().group(ItemGroup.MISC)));
    public static final RegistryObject<Item> DRIED_ULVA = HELPER.createItem("dried_ulva", ()->new Item(new Item.Properties().group(ItemGroup.FOOD).food(Foods.DRIED_ULVA)));

    public static class Foods {
        public static final Food DRIED_ULVA = (new Food.Builder()).hunger(1).saturation(0.3F).fastToEat().build(); // temporarily kelp clone
    }
}
