package com.teamaurora.depths_and_reefs.core.mixin;

import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.spawner.WorldEntitySpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(WorldEntitySpawner.class)
public class WorldEntitySpawnerMixin {
    @Inject(method = "canCreatureTypeSpawnAtLocation", at = @At("HEAD"), cancellable = true)
    private static void canCreatureTypeSpawnAtLocation(EntitySpawnPlacementRegistry.PlacementType placeType, IWorldReader worldIn, BlockPos pos, @Nullable EntityType<?> entityTypeIn, CallbackInfoReturnable<Boolean> ci) {
        if (entityTypeIn == EntityType.TURTLE && placeType == EntitySpawnPlacementRegistry.PlacementType.ON_GROUND && WorldEntitySpawner.canCreatureTypeSpawnAtLocation(EntitySpawnPlacementRegistry.PlacementType.IN_WATER, worldIn, pos, entityTypeIn)) {
            ci.setReturnValue(true);
            ci.cancel();
        }
    }
}
