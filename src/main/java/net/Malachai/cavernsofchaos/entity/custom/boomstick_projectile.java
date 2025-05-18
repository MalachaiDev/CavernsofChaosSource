package net.malachai.cavernsofchaos.entity.custom;

import net.malachai.cavernsofchaos.entity.ModEntities;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class boomstick_projectile extends ThrowableItemProjectile {
    public boomstick_projectile(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public boomstick_projectile(Level pLevel) {
        super(ModEntities.BOOMSTICK_PROJECTILE.get(), pLevel);
    }

    public boomstick_projectile(Level pLevel, LivingEntity livingEntity) {
        super(ModEntities.BOOMSTICK_PROJECTILE.get(), livingEntity, pLevel);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.IRON_NUGGET;
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        if(!this.level().isClientSide()) {
            boomstick_projectile.this.level().playSound(null,blockPosition(), SoundEvents.STONE_BREAK, SoundSource.NEUTRAL, 0.2F, 0.5F / (level().getRandom().nextFloat() * 0.4F + 0.8F));
            this.discard();
        }
        super.onHitBlock(pResult);
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        if(pResult.getEntity() instanceof boomstick_projectile){
            super.onHitEntity(pResult);
        }
        if(!this.level().isClientSide()) {
            level().playSound(null,blockPosition(),SoundEvents.PLAYER_HURT, SoundSource.NEUTRAL, 0.5F, 0.4F / (level().getRandom().nextFloat() * 0.4F + 0.8F));
            pResult.getEntity().hurt(level().damageSources().genericKill(),20);
            if(Math.random()>0.5){this.discard();}
        }
        super.onHitEntity(pResult);
    }
}