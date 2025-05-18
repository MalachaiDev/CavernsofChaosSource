package net.malachai.cavernsofchaos.entity.goals;

import net.malachai.cavernsofchaos.entity.custom.TorchwraithEntity;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class SnuffTorchGoal extends MoveToBlockGoal {
    private final TorchwraithEntity torchwraith;

    public SnuffTorchGoal(TorchwraithEntity entity, int range) {
        super(entity, 1.0F, range, range);
        this.torchwraith = entity;
    }
    protected int nextStartTick(PathfinderMob mob) {
        return reducedTickDelay(60 + torchwraith.getRandom().nextInt(100));
    }
    @Override
    public boolean canUse() {
        return super.canUse() && !isTargetBlocked(blockPos.getCenter());
    }

    public boolean isTargetBlocked(Vec3 target) {
        Vec3 Vector3d = new Vec3(mob.getX(), mob.getEyeY(), mob.getZ());
        return mob.level().clip(new ClipContext(Vector3d, target, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, mob)).getType() != HitResult.Type.MISS;
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse();
    }

    public double acceptedDistance() {
        return 2;
    }

    @Override
    public void tick() {
        super.tick();
        BlockPos target = getMoveToTarget();
        if (target != null) {
            torchwraith.lookAt(EntityAnchorArgument.Anchor.EYES, Vec3.atCenterOf(target));
            if (this.isReachedTarget() && torchwraith.level().getBlockState(target).is(Blocks.TORCH)||this.isReachedTarget() &&torchwraith.level().getBlockState(target).is(Blocks.WALL_TORCH)) {
                torchwraith.level().destroyBlock(target, true);
            }
        }
    }


    protected BlockPos getMoveToTarget() {
        return this.blockPos;
    }

    @Override
    protected boolean isValidTarget(LevelReader worldIn, BlockPos pos) {
        return pos != null && worldIn.getBlockState(pos).is(Blocks.TORCH)|| worldIn.getBlockState(pos).is(Blocks.WALL_TORCH);
    }
}
