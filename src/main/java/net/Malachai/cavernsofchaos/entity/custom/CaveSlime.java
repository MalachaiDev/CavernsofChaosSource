package net.malachai.cavernsofchaos.entity.custom;

import com.google.common.annotations.VisibleForTesting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class CaveSlime extends Monster implements Enemy {
    private static final EntityDataAccessor<Integer> ID_SIZE = SynchedEntityData.defineId(CaveSlime.class, EntityDataSerializers.INT);
    public static final int MIN_SIZE = 1;
    public static final int MAX_SIZE = 127;
    public float targetSquish;
    public float squish;
    public float oSquish;
    private boolean wasOnGround;

    public CaveSlime(EntityType<? extends CaveSlime> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.fixupDimensions();
        this.moveControl = new CaveSlime.CaveSlimeMoveControl(this);
    }
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.FOLLOW_RANGE, 16.0D).add(Attributes.ATTACK_SPEED, 1).add(Attributes.MOVEMENT_SPEED, (double)0.5F).add(Attributes.MAX_HEALTH, 20.0D).add(Attributes.ATTACK_DAMAGE, 5.0D).add(Attributes.KNOCKBACK_RESISTANCE, 10);
    }
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new CaveSlime.CaveSlimeFloatGoal(this));
        this.goalSelector.addGoal(2, new CaveSlime.CaveSlimeAttackGoal(this));
        this.goalSelector.addGoal(3, new CaveSlime.CaveSlimeRandomDirectionGoal(this));
        this.goalSelector.addGoal(5, new CaveSlime.CaveSlimeKeepOnJumpingGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, (p_289461_) -> {
            return Math.abs(p_289461_.getY() - this.getY()) <= 4.0D;
        }));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
    }
    public static boolean checkCaveSlimeSpawnRules(EntityType<CaveSlime> pSlime, ServerLevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) {
        if(pPos.getY() < pLevel.getSeaLevel()) {
            if (pLevel.getDifficulty() != Difficulty.PEACEFUL) {
                return checkMobSpawnRules(pSlime, pLevel, pSpawnType, pPos, pRandom);
            }
        }
        return false;
    }
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ID_SIZE, 1);
    }

    @VisibleForTesting
    public void setSize(int pSize, boolean pResetHealth) {
        int i = Mth.clamp(pSize, 1, 127);
        this.entityData.set(ID_SIZE, i);
        this.reapplyPosition();
        this.refreshDimensions();
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue((double)(i * i));
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue((double)(0.2F + 0.1F * (float)i));
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue((double)i);
        if (pResetHealth) {
            this.setHealth(this.getMaxHealth());
        }

        this.xpReward = i;
    }

    /**
     * Returns the size of the slime.
     */
    public int getSize() {
        return this.entityData.get(ID_SIZE);
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("Size", this.getSize() - 1);
        pCompound.putBoolean("wasOnGround", this.wasOnGround);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditionalSaveData(CompoundTag pCompound) {
        this.setSize(pCompound.getInt("Size") + 1, false);
        super.readAdditionalSaveData(pCompound);
        this.wasOnGround = pCompound.getBoolean("wasOnGround");
    }

    public boolean isTiny() {
        return this.getSize() <= 1;
    }

    protected ParticleOptions getParticleType() {
        return ParticleTypes.SMOKE;
    }

    protected boolean shouldDespawnInPeaceful() {
        return this.getSize() > 0;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {
        this.squish += (this.targetSquish - this.squish) * 0.5F;
        this.oSquish = this.squish;
        super.tick();
        if (this.onGround() && !this.wasOnGround) {
            int i = this.getSize();

            // Forge: Don't spawn particles if it's handled by the implementation itself
            if (!spawnCustomParticles())
                for(int j = 0; j < i * 8; ++j) {
                    float f = this.random.nextFloat() * ((float)Math.PI * 2F);
                    float f1 = this.random.nextFloat() * 0.5F + 0.5F;
                    float f2 = Mth.sin(f) * (float)i * 0.5F * f1;
                    float f3 = Mth.cos(f) * (float)i * 0.5F * f1;
                    this.level().addParticle(this.getParticleType(), this.getX() + (double)f2, this.getY(), this.getZ() + (double)f3, 0.0D, -0.1D, 0.0D);
                }

            this.playSound(this.getSquishSound(), this.getSoundVolume(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) / 0.8F);
            this.targetSquish = -0.5F;
        } else if (!this.onGround() && this.wasOnGround) {
            this.targetSquish = 1.0F;
        }

        this.wasOnGround = this.onGround();
        this.decreaseSquish();
    }

    protected void decreaseSquish() {
        this.targetSquish *= 0.6F;
    }

    /**
     * Gets the amount of time the slime needs to wait between jumps.
     */
    protected int getJumpDelay() {
        return this.random.nextInt(20) + 10;
    }

    public void refreshDimensions() {
        double d0 = this.getX();
        double d1 = this.getY();
        double d2 = this.getZ();
        super.refreshDimensions();
        this.setPos(d0, d1, d2);
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (ID_SIZE.equals(pKey)) {
            this.refreshDimensions();
            this.setYRot(this.yHeadRot);
            this.yBodyRot = this.yHeadRot;
            if (this.isInWater() && this.random.nextInt(20) == 0) {
                this.doWaterSplashEffect();
            }
        }

        super.onSyncedDataUpdated(pKey);
    }

    public EntityType<? extends CaveSlime> getType() {
        return (EntityType<? extends CaveSlime>)super.getType();
    }

    public void remove(Entity.RemovalReason pReason) {
        int i = this.getSize();
        if (!this.level().isClientSide && i > 1 && this.isDeadOrDying()) {
            Component component = this.getCustomName();
            boolean flag = this.isNoAi();
            float f = (float)i / 4.0F;
            int j = i / 2;
            int k = 2 + this.random.nextInt(3);

            for(int l = 0; l < k; ++l) {
                float f1 = ((float)(l % 2) - 0.5F) * f;
                float f2 = ((float)(l / 2) - 0.5F) * f;
                CaveSlime Caveslime = this.getType().create(this.level());
                if (Caveslime != null) {
                    if (this.isPersistenceRequired()) {
                        Caveslime.setPersistenceRequired();
                    }

                    Caveslime.setCustomName(component);
                    Caveslime.setNoAi(flag);
                    Caveslime.setInvulnerable(this.isInvulnerable());
                    Caveslime.setSize(j, true);
                    Caveslime.moveTo(this.getX() + (double)f1, this.getY() + 0.5D, this.getZ() + (double)f2, this.random.nextFloat() * 360.0F, 0.0F);
                    this.level().addFreshEntity(Caveslime);
                }
            }
        }

        super.remove(pReason);
    }

    /**
     * Applies a velocity to the entities, to push them away from each other.
     */
    public void push(Entity pEntity) {
        super.push(pEntity);
        if (pEntity instanceof IronGolem && this.isDealsDamage()) {
            this.dealDamage((LivingEntity)pEntity);
        }

    }

    /**
     * Called by a player entity when they collide with an entity
     */
    public void playerTouch(Player pEntity) {
        if (this.isDealsDamage()) {
            this.dealDamage(pEntity);
        }

    }

    protected void dealDamage(LivingEntity pLivingEntity) {
        if (this.isAlive()) {
            int i = this.getSize();
            if (this.distanceToSqr(pLivingEntity) < 0.6D * (double)i * 0.6D * (double)i && this.hasLineOfSight(pLivingEntity) && pLivingEntity.hurt(this.damageSources().mobAttack(this), this.getAttackDamage())) {
                this.playSound(SoundEvents.SLIME_ATTACK, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                this.doEnchantDamageEffects(this, pLivingEntity);
            }
        }

    }

    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize) {
        return 0.625F * pSize.height;
    }

    /**
     * Indicates weather the slime is able to damage the player (based upon the slime's size)
     */
    protected boolean isDealsDamage() {
        return !this.isTiny() && this.isEffectiveAi();
    }

    protected float getAttackDamage() {
        return (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return this.isTiny() ? SoundEvents.SLIME_HURT_SMALL : SoundEvents.SLIME_HURT;
    }

    protected SoundEvent getDeathSound() {
        return this.isTiny() ? SoundEvents.SLIME_DEATH_SMALL : SoundEvents.SLIME_DEATH;
    }

    protected SoundEvent getSquishSound() {
        return this.isTiny() ? SoundEvents.SLIME_SQUISH_SMALL : SoundEvents.SLIME_SQUISH;
    }


    /**
     * Returns the volume for the sounds this mob makes.
     */
    protected float getSoundVolume() {
        return 0.4F * (float)this.getSize();
    }

    /**
     * The speed it takes to move the entity's head rotation through the faceEntity method.
     */
    public int getMaxHeadXRot() {
        return 0;
    }

    /**
     * Returns {@code true} if the slime makes a sound when it jumps (based upon the slime's size)
     */
    protected boolean doPlayJumpSound() {
        return this.getSize() > 0;
    }

    /**
     * Causes this entity to do an upwards motion (jumping).
     */
    protected void jumpFromGround() {
        Vec3 vec3 = this.getDeltaMovement();
        this.setDeltaMovement(vec3.x, (double)this.getJumpPower(), vec3.z);
        this.hasImpulse = true;
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        RandomSource randomsource = pLevel.getRandom();
        int i = randomsource.nextInt(3);
        if (i < 2 && randomsource.nextFloat() < 0.5F * pDifficulty.getSpecialMultiplier()) {
            ++i;
        }

        int j = 1 << i;
        this.setSize(j, true);
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    float getSoundPitch() {
        float f = this.isTiny() ? 1.4F : 0.8F;
        return ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) * f;
    }

    protected SoundEvent getJumpSound() {
        return this.isTiny() ? SoundEvents.SLIME_JUMP_SMALL : SoundEvents.SLIME_JUMP;
    }

    public EntityDimensions getDimensions(Pose pPose) {
        return super.getDimensions(pPose).scale(0.255F * (float)this.getSize());
    }

    /**
     * Called when the slime spawns particles on landing, see onUpdate.
     * Return true to prevent the spawning of the default particles.
     */
    protected boolean spawnCustomParticles() { return false; }

    static class CaveSlimeAttackGoal extends Goal {
        private final CaveSlime Caveslime;
        private int growTiredTimer;

        public CaveSlimeAttackGoal(CaveSlime pSlime) {
            this.Caveslime = pSlime;
            this.setFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            LivingEntity livingentity = this.Caveslime.getTarget();
            if (livingentity == null) {
                return false;
            } else {
                return !this.Caveslime.canAttack(livingentity) ? false : this.Caveslime.getMoveControl() instanceof CaveSlime.CaveSlimeMoveControl;
            }
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            this.growTiredTimer = reducedTickDelay(300);
            super.start();
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            LivingEntity livingentity = this.Caveslime.getTarget();
            if (livingentity == null) {
                return false;
            } else if (!this.Caveslime.canAttack(livingentity)) {
                return false;
            } else {
                return --this.growTiredTimer > 0;
            }
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            LivingEntity livingentity = this.Caveslime.getTarget();
            if (livingentity != null) {
                this.Caveslime.lookAt(livingentity, 10.0F, 10.0F);
            }

            MoveControl movecontrol = this.Caveslime.getMoveControl();
            if (movecontrol instanceof CaveSlime.CaveSlimeMoveControl Caveslime$Caveslimemovecontrol) {
                Caveslime$Caveslimemovecontrol.setDirection(this.Caveslime.getYRot(), this.Caveslime.isDealsDamage());
            }

        }
    }

    static class CaveSlimeFloatGoal extends Goal {
        private final CaveSlime Caveslime;

        public CaveSlimeFloatGoal(CaveSlime pSlime) {
            this.Caveslime = pSlime;
            this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
            pSlime.getNavigation().setCanFloat(true);
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            return (this.Caveslime.isInWater() || this.Caveslime.isInLava()) && this.Caveslime.getMoveControl() instanceof CaveSlime.CaveSlimeMoveControl;
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            if (this.Caveslime.getRandom().nextFloat() < 0.8F) {
                this.Caveslime.getJumpControl().jump();
            }

            MoveControl movecontrol = this.Caveslime.getMoveControl();
            if (movecontrol instanceof CaveSlime.CaveSlimeMoveControl Caveslime$Caveslimemovecontrol) {
                Caveslime$Caveslimemovecontrol.setWantedMovement(1.2D);
            }

        }
    }

    static class CaveSlimeKeepOnJumpingGoal extends Goal {
        private final CaveSlime Caveslime;

        public CaveSlimeKeepOnJumpingGoal(CaveSlime pSlime) {
            this.Caveslime = pSlime;
            this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            return !this.Caveslime.isPassenger();
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            MoveControl movecontrol = this.Caveslime.getMoveControl();
            if (movecontrol instanceof CaveSlime.CaveSlimeMoveControl Caveslime$Caveslimemovecontrol) {
                Caveslime$Caveslimemovecontrol.setWantedMovement(1.0D);
            }

        }
    }

    static class CaveSlimeMoveControl extends MoveControl {
        private float yRot;
        private int jumpDelay;
        private final CaveSlime Caveslime;
        private boolean isAggressive;

        public CaveSlimeMoveControl(CaveSlime pSlime) {
            super(pSlime);
            this.Caveslime = pSlime;
            this.yRot = 180.0F * pSlime.getYRot() / (float)Math.PI;
        }

        public void setDirection(float pYRot, boolean pAggressive) {
            this.yRot = pYRot;
            this.isAggressive = pAggressive;
        }

        public void setWantedMovement(double pSpeed) {
            this.speedModifier = pSpeed;
            this.operation = MoveControl.Operation.MOVE_TO;
        }

        public void tick() {
            this.mob.setYRot(this.rotlerp(this.mob.getYRot(), this.yRot, 90.0F));
            this.mob.yHeadRot = this.mob.getYRot();
            this.mob.yBodyRot = this.mob.getYRot();
            if (this.operation != MoveControl.Operation.MOVE_TO) {
                this.mob.setZza(0.0F);
            } else {
                this.operation = MoveControl.Operation.WAIT;
                if (this.mob.onGround()) {
                    this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                    if (this.jumpDelay-- <= 0) {
                        this.jumpDelay = this.Caveslime.getJumpDelay();
                        if (this.isAggressive) {
                            this.jumpDelay /= 3;
                        }

                        this.Caveslime.getJumpControl().jump();
                        if (this.Caveslime.doPlayJumpSound()) {
                            this.Caveslime.playSound(this.Caveslime.getJumpSound(), this.Caveslime.getSoundVolume(), this.Caveslime.getSoundPitch());
                        }
                    } else {
                        this.Caveslime.xxa = 0.0F;
                        this.Caveslime.zza = 0.0F;
                        this.mob.setSpeed(0.0F);
                    }
                } else {
                    this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                }

            }
        }
    }

    static class CaveSlimeRandomDirectionGoal extends Goal {
        private final CaveSlime Caveslime;
        private float chosenDegrees;
        private int nextRandomizeTime;

        public CaveSlimeRandomDirectionGoal(CaveSlime pSlime) {
            this.Caveslime = pSlime;
            this.setFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            return this.Caveslime.getTarget() == null && (this.Caveslime.onGround() || this.Caveslime.isInWater() || this.Caveslime.isInLava() || this.Caveslime.hasEffect(MobEffects.LEVITATION)) && this.Caveslime.getMoveControl() instanceof CaveSlime.CaveSlimeMoveControl;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            if (--this.nextRandomizeTime <= 0) {
                this.nextRandomizeTime = this.adjustedTickDelay(40 + this.Caveslime.getRandom().nextInt(60));
                this.chosenDegrees = (float)this.Caveslime.getRandom().nextInt(360);
            }

            MoveControl movecontrol = this.Caveslime.getMoveControl();
            if (movecontrol instanceof CaveSlime.CaveSlimeMoveControl Caveslime$Caveslimemovecontrol) {
                Caveslime$Caveslimemovecontrol.setDirection(this.chosenDegrees, false);
            }

        }
    }
}
