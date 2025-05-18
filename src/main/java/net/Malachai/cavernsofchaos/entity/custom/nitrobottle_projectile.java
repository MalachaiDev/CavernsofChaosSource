package net.malachai.cavernsofchaos.entity.custom;

import net.malachai.cavernsofchaos.entity.ModEntities;
import net.malachai.cavernsofchaos.item.ModItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class nitrobottle_projectile extends ThrowableItemProjectile {
    public nitrobottle_projectile(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public nitrobottle_projectile(Level pLevel) {
        super(ModEntities.NITROBOTTLE_PROJECTILE.get(), pLevel);
    }

    public nitrobottle_projectile(Level pLevel, LivingEntity livingEntity) {
        super(ModEntities.NITROBOTTLE_PROJECTILE.get(), livingEntity, pLevel);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.NITROBOTTLE.get();
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        if(!this.level().isClientSide()) {
            level().playSound(null,blockPosition(),SoundEvents.GLASS_BREAK, SoundSource.NEUTRAL, 0.5F, 0.4F / (level().getRandom().nextFloat() * 0.4F + 0.8F));
            Vec3 vec3 = blockPosition().getCenter();
            level().explode((Entity)null, level().damageSources().explosion(null), null, vec3, 3.0F, false, Level.ExplosionInteraction.NONE);
            this.discard();
        }
        super.onHitBlock(pResult);
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        if(!this.level().isClientSide()) {
            level().playSound(null,blockPosition(),SoundEvents.GLASS_BREAK, SoundSource.NEUTRAL, 0.5F, 0.4F / (level().getRandom().nextFloat() * 0.4F + 0.8F));
            Vec3 vec3 = blockPosition().getCenter();
            level().explode((Entity)null, level().damageSources().explosion(null), null, vec3, 3.0F, false, Level.ExplosionInteraction.NONE);
            this.discard();
        }
        super.onHitEntity(pResult);
    }
}