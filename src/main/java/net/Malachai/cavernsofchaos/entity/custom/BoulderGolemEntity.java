package net.chance.cavernsofchaos.entity.custom;

import com.google.common.base.MoreObjects;
import net.chance.cavernsofchaos.entity.goals.SnuffTorchGoal;
import net.chance.cavernsofchaos.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class BoulderGolemEntity extends Monster {
    public BoulderGolemEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    private int timeleft = 260;
    private boolean hastarget = false;
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;



    private void setupAnimationStates() {
        if(this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        float f;
        if(this.getPose() == Pose.STANDING) {
            f = Math.min(pPartialTick * 6F, 1f);
        } else {
            f = 0f;
        }

        this.walkAnimation.update(f, 0.2f);
    }


    @Override
    protected void jumpFromGround() {
        this.hurt(damageSources().magic(),100);
        super.jumpFromGround();
    }


    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this,1.2D, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, false));

    }
    public void tick() {
      //  if(level().canSeeSky(blockPosition())){}
        int x = blockPosition().getX();
        int y = blockPosition().getY();
        int z = blockPosition().getZ();
            LivingEntity livingentity = BoulderGolemEntity.this.getTarget();
             super.tick();
             if(timeleft == 0){this.hurt(damageSources().magic(),100);}
             if(hastarget){timeleft--;}
             else {
                 if (livingentity == null) {
                     BoulderGolemEntity.this.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 10, 20));
                     this.setNoGravity(true);
                     removeEffectParticles();
                 } else {if (livingentity.getY() < this.getY()
                         && livingentity.getZ() < this.getZ()+3
                         && livingentity.getZ() > this.getZ()-3
                         && livingentity.getX() < this.getX()+3
                         && livingentity.getX() > this.getX()-3
                     ) {
                        BoulderGolemEntity.this.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 100, -30));
                        this.setNoGravity(false);
                        hastarget = true;
                        removeEffectParticles();
                    }
                 }
             }
        if(this.level().isClientSide()) {
            setupAnimationStates();
        }


    }

    public static boolean checkCaveSpawnRules(EntityType<BoulderGolemEntity> pEntity, ServerLevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) {
        return pPos.getY() < pLevel.getSeaLevel() && pLevel.getDifficulty() != Difficulty.PEACEFUL && isDarkEnoughToSpawn(pLevel, pPos, pRandom) && checkMobSpawnRules(pEntity, pLevel, pSpawnType, pPos, pRandom);
    }


        @Override
    protected int calculateFallDamage(float pFallDistance, float pDamageMultiplier) {
     return super.calculateFallDamage(pFallDistance, 0);
    }


    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.FOLLOW_RANGE, 35.0D).add(Attributes.ATTACK_SPEED, 1).add(Attributes.MOVEMENT_SPEED, (double)0.5F).add(Attributes.MAX_HEALTH, 20.0D).add(Attributes.ATTACK_DAMAGE, 17.0D).add(Attributes.KNOCKBACK_RESISTANCE, 10);
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        SpawnGroupData spawngroupdata = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        if(pLevel.canSeeSky(blockPosition())){
            return spawngroupdata;
        }
        return null;
    }
    protected SoundEvent getAmbientSound() {
        return SoundEvents.IRON_GOLEM_STEP;
    }

    protected SoundEvent getDeathSound() {
        return ModSounds.CRUMBLE.get();
    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.IRON_GOLEM_HURT;
    }
}
