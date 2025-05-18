package net.malachai.cavernsofchaos.entity.custom;


import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net. minecraft.world.level.Level;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;


public class flyskullEntity extends Monster implements TraceableEntity {
    public flyskullEntity(EntityType<? extends flyskullEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.moveControl = new VexMoveControl(this);
        this.xpReward = 3;

    }
        public static final float FLAP_DEGREES_PER_TICK = 45.836624F;
        public static final int TICKS_PER_FLAP = Mth.ceil(3.9269907F);
        protected static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(Vex.class, EntityDataSerializers.BYTE);
        private static final int FLAG_IS_CHARGING = 1;
        private static final double RIDING_OFFSET = 0.4D;
        @Nullable
        Mob owner;
        @Nullable
        private BlockPos boundOrigin;
        private boolean hasLimitedLife;
        private int limitedLifeTicks;




    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pDimensions) {
            return pDimensions.height - 0.28125F;
        }

        public boolean isFlapping() {
            return this.tickCount % TICKS_PER_FLAP == 0;
        }

        public void move(MoverType pType, Vec3 pPos) {
            super.move(pType, pPos);
            this.checkInsideBlocks();
        }

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public void tick() {
        super.tick();
        this.setNoGravity(true);
        if(this.level().isClientSide()) {
            setupAnimationStates();
        }
        //this.noPhysics = true;
}
    public static boolean checkCaveSpawnRules(EntityType<flyskullEntity> pEntity, ServerLevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) {
            if (pLevel.getDifficulty() != Difficulty.PEACEFUL) {
                return checkMobSpawnRules(pEntity, pLevel, pSpawnType, pPos, pRandom);
            }
        return false;
    }
        protected void registerGoals() {
            super.registerGoals();
            this.goalSelector.addGoal(0, new FloatGoal(this));
            this.goalSelector.addGoal(4, new VexChargeAttackGoal());
            this.goalSelector.addGoal(8, new VexRandomMoveGoal());
            this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
            this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
            this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, Raider.class)).setAlertOthers());
            this.targetSelector.addGoal(2, new VexCopyOwnerTargetGoal(this));
            this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true));
        }

        public static AttributeSupplier.Builder createAttributes() {
            return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 7.0D).add(Attributes.ATTACK_DAMAGE, 4.0D).add(Attributes.FOLLOW_RANGE,20).add(Attributes.FLYING_SPEED, 2);
        }







        protected void defineSynchedData() {
            super.defineSynchedData();
            this.entityData.define(DATA_FLAGS_ID, (byte)0);
        }

        /**
         * (abstract) Protected helper method to read subclass entity data from NBT.
         */
        public void readAdditionalSaveData(CompoundTag pCompound) {
            super.readAdditionalSaveData(pCompound);
            if (pCompound.contains("BoundX")) {
                this.boundOrigin = new BlockPos(pCompound.getInt("BoundX"), pCompound.getInt("BoundY"), pCompound.getInt("BoundZ"));
            }

            if (pCompound.contains("LifeTicks")) {
                this.setLimitedLife(pCompound.getInt("LifeTicks"));
            }

        }

    @Override
    protected boolean isSunBurnTick() {
        return super.isSunBurnTick();
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
            super.addAdditionalSaveData(pCompound);
            if (this.boundOrigin != null) {
                pCompound.putInt("BoundX", this.boundOrigin.getX());
                pCompound.putInt("BoundY", this.boundOrigin.getY());
                pCompound.putInt("BoundZ", this.boundOrigin.getZ());
            }

            if (this.hasLimitedLife) {
                pCompound.putInt("LifeTicks", this.limitedLifeTicks);
            }

        }

        /**
         * Returns null or the entityliving it was ignited by
         */
        @Nullable
        public Mob getOwner() {
            return this.owner;
        }

        @Nullable
        public BlockPos getBoundOrigin() {
            return this.boundOrigin;
        }

        public void setBoundOrigin(@Nullable BlockPos pBoundOrigin) {
            this.boundOrigin = pBoundOrigin;
        }

        private boolean getVexFlag(int pMask) {
            int i = this.entityData.get(DATA_FLAGS_ID);
            return (i & pMask) != 0;
        }

        private void setVexFlag(int pMask, boolean pValue) {
            int i = this.entityData.get(DATA_FLAGS_ID);
            if (pValue) {
                i |= pMask;
            } else {
                i &= ~pMask;
            }

            this.entityData.set(DATA_FLAGS_ID, (byte)(i & 255));
        }

        public boolean isCharging() {
            return this.getVexFlag(1);
        }

        public void setIsCharging(boolean pCharging) {
            this.setVexFlag(1, pCharging);
        }

        public void setOwner(Mob pOwner) {
            this.owner = pOwner;
        }

        public void setLimitedLife(int pLimitedLifeTicks) {
            this.hasLimitedLife = true;
            this.limitedLifeTicks = pLimitedLifeTicks;
        }
    private void setupAnimationStates() {
        if(this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
    }
    protected SoundEvent getAmbientSound() {
        return SoundEvents.SKELETON_AMBIENT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.SKELETON_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.SKELETON_HURT;
    }

    @Override
    protected int calculateFallDamage(float pFallDistance, float pDamageMultiplier) {
        return super.calculateFallDamage(pFallDistance, 0);
    }

    public float getLightLevelDependentMagicValue() {
        return 1.0F;
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        SpawnGroupData spawngroupdata = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        RandomSource randomsource = pLevel.getRandom();
        this.populateDefaultEquipmentSlots(randomsource, pDifficulty);
        this.populateDefaultEquipmentEnchantments(randomsource, pDifficulty);
        return spawngroupdata;
    }




    /**
     * Returns the Y Offset of this entity.
     */
    public double getMyRidingOffset() {
        return 0.4D;
    }

    class VexChargeAttackGoal extends Goal {
        public VexChargeAttackGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            LivingEntity livingentity = flyskullEntity.this.getTarget();
            if (livingentity != null && livingentity.isAlive() && !flyskullEntity.this.getMoveControl().hasWanted() && flyskullEntity.this.random.nextInt(reducedTickDelay(7)) == 0) {
                return flyskullEntity.this.distanceToSqr(livingentity) > 4.0D;
            } else {
                return false;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            return flyskullEntity.this.getMoveControl().hasWanted() && flyskullEntity.this.isCharging() && flyskullEntity.this.getTarget() != null && flyskullEntity.this.getTarget().isAlive();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            LivingEntity livingentity = flyskullEntity.this.getTarget();
            if (livingentity != null) {
                Vec3 vec3 = livingentity.getEyePosition();
                flyskullEntity.this.moveControl.setWantedPosition(vec3.x, vec3.y, vec3.z, 2.0D);
            }
            flyskullEntity.this.setIsCharging(true);
            flyskullEntity.this.playSound(SoundEvents.HOGLIN_HURT, 1.0F, 1.3F);
        }


        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void stop() {
            flyskullEntity.this.setIsCharging(false);
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            LivingEntity livingentity = flyskullEntity.this.getTarget();
            if (livingentity != null) {
                if (flyskullEntity.this.getBoundingBox().intersects(livingentity.getBoundingBox())) {
                    flyskullEntity.this.doHurtTarget(livingentity);
                    flyskullEntity.this.setIsCharging(false);
                } else {
                    double d0 = flyskullEntity.this.distanceToSqr(livingentity);
                    if (d0 < 9.0D) {
                        Vec3 vec3 = livingentity.getEyePosition();
                        flyskullEntity.this.moveControl.setWantedPosition(vec3.x, vec3.y, vec3.z, 1.0D);
                    }
                }

            }
        }
    }
    public void aiStep() {
        if(!isCharging()){
            if(Math.random() > 0.8) {
                flyskullEntity.this.level().addParticle(ParticleTypes.SMOKE, true, flyskullEntity.this.getX() + (Math.random() - 0.3), flyskullEntity.this.getY() + (Math.random()), flyskullEntity.this.getZ() + (Math.random() - 0.3), 0.0D, 0.0D, 0.0D);
            }
        }
        else{
            flyskullEntity.this.level().addParticle(ParticleTypes.SOUL_FIRE_FLAME, true, flyskullEntity.this.getX()+(Math.random()-0.5), flyskullEntity.this.getY()+(Math.random()), flyskullEntity.this.getZ()+(Math.random()-0.5), 0.0D, 0.0D, 0.0D);
        }
        if (this.isAlive()) {
            boolean flag = this.isSunBurnTick();
            if (flag) {
                ItemStack itemstack = this.getItemBySlot(EquipmentSlot.HEAD);
                if (!itemstack.isEmpty()) {
                    if (itemstack.isDamageableItem()) {
                        itemstack.setDamageValue(itemstack.getDamageValue() + this.random.nextInt(2));
                        if (itemstack.getDamageValue() >= itemstack.getMaxDamage()) {
                            this.broadcastBreakEvent(EquipmentSlot.HEAD);
                            this.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
                        }
                    }

                    flag = false;
                }

                if (flag) {
                    this.setSecondsOnFire(8);
                }
            }
        }

        super.aiStep();
    }

    class VexCopyOwnerTargetGoal extends TargetGoal {
        private final TargetingConditions copyOwnerTargeting = TargetingConditions.forNonCombat().ignoreLineOfSight().ignoreInvisibilityTesting();

        public VexCopyOwnerTargetGoal(PathfinderMob pMob) {
            super(pMob, false);
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            return flyskullEntity.this.owner != null && flyskullEntity.this.owner.getTarget() != null && this.canAttack(flyskullEntity.this.owner.getTarget(), this.copyOwnerTargeting);
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            flyskullEntity.this.setTarget(flyskullEntity.this.owner.getTarget());
            super.start();
        }
    }

    class VexMoveControl extends MoveControl {
        public VexMoveControl(flyskullEntity pVex) {
            super(pVex);
        }

        public void tick() {
            if (this.operation == Operation.MOVE_TO) {
                Vec3 vec3 = new Vec3(this.wantedX - flyskullEntity.this.getX(), this.wantedY - flyskullEntity.this.getY(), this.wantedZ - flyskullEntity.this.getZ());
                double d0 = vec3.length();
                if (d0 < flyskullEntity.this.getBoundingBox().getSize()) {
                    this.operation = Operation.WAIT;
                    flyskullEntity.this.setDeltaMovement(flyskullEntity.this.getDeltaMovement().scale(0.5D));
                } else {
                    flyskullEntity.this.setDeltaMovement(flyskullEntity.this.getDeltaMovement().add(vec3.scale(this.speedModifier * 0.05D / d0)));
                    if (flyskullEntity.this.getTarget() == null) {
                        Vec3 vec31 = flyskullEntity.this.getDeltaMovement();
                        flyskullEntity.this.setYRot(-((float) Mth.atan2(vec31.x, vec31.z)) * (180F / (float)Math.PI));
                        flyskullEntity.this.yBodyRot = flyskullEntity.this.getYRot();
                    } else {
                        double d2 = flyskullEntity.this.getTarget().getX() - flyskullEntity.this.getX();
                        double d1 = flyskullEntity.this.getTarget().getZ() - flyskullEntity.this.getZ();
                        flyskullEntity.this.setYRot(-((float)Mth.atan2(d2, d1)) * (180F / (float)Math.PI));
                        flyskullEntity.this.yBodyRot = flyskullEntity.this.getYRot();
                    }
                }

            }
        }
    }

    class VexRandomMoveGoal extends Goal {
        public VexRandomMoveGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            return !flyskullEntity.this.getMoveControl().hasWanted() && flyskullEntity.this.random.nextInt(reducedTickDelay(7)) == 0;
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            return false;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            BlockPos blockpos = flyskullEntity.this.getBoundOrigin();
            if (blockpos == null) {
                blockpos = flyskullEntity.this.blockPosition();
            }

            for(int i = 0; i < 3; ++i) {
                BlockPos blockpos1 = blockpos.offset(flyskullEntity.this.random.nextInt(15) - 7, flyskullEntity.this.random.nextInt(11) - 5, flyskullEntity.this.random.nextInt(15) - 7);
                if (flyskullEntity.this.level().isEmptyBlock(blockpos1)) {
                    flyskullEntity.this.moveControl.setWantedPosition((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.5D, (double)blockpos1.getZ() + 0.5D, 0.25D);
                    if (flyskullEntity.this.getTarget() == null) {
                        flyskullEntity.this.getLookControl().setLookAt((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.5D, (double)blockpos1.getZ() + 0.5D, 180.0F, 20.0F);
                    }
                    break;
                }
            }

        }
    }

}

