package com.teampotato.safefall.mixin;

import io.redspace.ironsspellbooks.entity.spells.comet.Comet;
import io.redspace.ironsspellbooks.spells.ender.StarfallSpell;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = StarfallSpell.class, remap = false)
public abstract class MixinStarfallSpell {
    @Shadow protected abstract float getDamage(int spellLevel, LivingEntity caster);

    /**
     * @author Kasualix
     * @reason safefall
     */
    @Overwrite
    public void shootComet(Level world, int spellLevel, LivingEntity entity, @NotNull Vec3 spawn) {
        Comet fireball = new Comet(world, entity);
        fireball.setPos(spawn.add(-1.0, 0.0, 0.0));
        fireball.shoot(new Vec3(0.15000000596046448, -0.8500000238418579, 0.0), 0.075F);
        fireball.setDamage(this.getDamage(spellLevel, entity));
        fireball.setExplosionRadius(2.0F);
        fireball.addTag("safefall");
        world.addFreshEntity(fireball);
        world.playSound(null, spawn.x, spawn.y, spawn.z, SoundEvents.FIREWORK_ROCKET_LAUNCH, SoundSource.PLAYERS, 3.0F, 0.7F + world.random.nextFloat() * 0.3F);
    }
}
