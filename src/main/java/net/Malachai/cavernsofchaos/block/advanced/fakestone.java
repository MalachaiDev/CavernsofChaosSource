package net.malachai.cavernsofchaos.block.advanced;

import net.malachai.cavernsofchaos.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class fakestone extends Block {
    public fakestone(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        if(pBlock instanceof fakestone){
            pLevel.playSound((Player) null, pPos, ModSounds.CRUMBLE.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
        pLevel.scheduleTick(pPos, this, 2);}
        super.neighborChanged(pState, pLevel, pPos, pBlock, pFromPos, pIsMoving);
    }



    @Override
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
        if(pEntity instanceof Player && !pEntity.isSteppingCarefully()){
        pLevel.playSound((Player) null, pPos, ModSounds.CRUMBLE.get(), SoundSource.BLOCKS, 2.0F, 1.0F);
        pLevel.scheduleTick(pPos, this, 3);}
        super.stepOn(pLevel, pPos, pState, pEntity);
    }

    @Override
    public void tick(BlockState state, ServerLevel pLevel, BlockPos pos, RandomSource pRandom) {
        pLevel.destroyBlock(pos,false);
        super.tick(state, pLevel, pos, pRandom);
    }
}
