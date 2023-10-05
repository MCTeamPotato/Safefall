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
        Entity directSourceEntity = pSource.getDirectEntity();
        if (directSourceEntity != null && directSourceEntity.getTags().contains("safefall")) cir.setReturnValue(false);
    }
}
