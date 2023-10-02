package com.teampotato.safefall.mixin;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class MixinPlayer {
    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    private void onHurt(@NotNull DamageSource pSource, float pAmount, CallbackInfoReturnable<Boolean> cir) {
        Entity sourceEntity = pSource.getEntity();
        Entity directSourceEntity = pSource.getDirectEntity();
        if ((sourceEntity != null && sourceEntity.getTags().contains("safefall")) || (directSourceEntity != null && directSourceEntity.getTags().contains("safefall"))) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}
