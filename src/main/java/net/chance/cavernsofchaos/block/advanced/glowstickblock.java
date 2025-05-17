package net.chance.cavernsofchaos.block.advanced;

import net.chance.cavernsofchaos.item.ModItems;
import net.chance.cavernsofchaos.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class glowstickblock extends FallingBlock {
    public glowstickblock(Properties pProperties) {
        super(pProperties);
    }



    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D);
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        if (isFree(pLevel.getBlockState(pPos.below())) && pPos.getY() >= pLevel.getMinBuildHeight()) {
            FallingBlockEntity fallingblockentity = FallingBlockEntity.fall(pLevel, pPos, pState);
            fallingblockentity.dropItem = false;
            this.falling(fallingblockentity);
        }
    }

    @Override
    public void onBrokenAfterFall(Level pLevel, BlockPos pPos, FallingBlockEntity pFallingBlock) {
        popResource(pLevel, pPos, new ItemStack(ModItems.GLOWSTICKITEM.get(), 1));
        super.onBrokenAfterFall(pLevel, pPos, pFallingBlock);
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
       if (isFree(pLevel.getBlockState(pPos.below())) && pPos.getY() >= pLevel.getMinBuildHeight()) {
           FallingBlockEntity fallingblockentity = FallingBlockEntity.fall(pLevel, pPos, pState);
           fallingblockentity.dropItem = false;
           this.falling(fallingblockentity);
       }else{
       var timer = (600 + (Math.random() * 120));
        pLevel.scheduleTick(pPos,this, (int) timer);
        super.onPlace(pState, pLevel, pPos, pOldState, pIsMoving);}}

    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        pLevel.destroyBlock(pPos,false);
        super.tick(pState, pLevel, pPos, pRandom);
    }
}
