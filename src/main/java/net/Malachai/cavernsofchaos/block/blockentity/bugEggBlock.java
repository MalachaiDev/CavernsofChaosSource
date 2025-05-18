package net.malachai.cavernsofchaos.block.blockentity;

import net.malachai.cavernsofchaos.block.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;


public class bugEggBlock extends BaseEntityBlock {
    public bugEggBlock(BlockBehaviour.Properties pProperties) {
        super(pProperties);
    }
    public static final VoxelShape SHAPE = Block.box(1, 0, 1, 15, 15, 15);
    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }
    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }


    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState blockState) {
        return new bugEggBlockEntity(pos, blockState);
    }
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, ModBlockEntities.BUG_NEST.get(), pLevel.isClientSide ? bugEggBlockEntity::clientTick : bugEggBlockEntity::serverTick);
    }
    public void onRemove(BlockState state, Level level, BlockPos blockPos, BlockState newState, boolean force) {
        if (state.hasBlockEntity() && (!(newState.getBlock() instanceof bugEggBlock) || !newState.hasBlockEntity())) {
            level.removeBlockEntity(blockPos);
        }
    }
}
