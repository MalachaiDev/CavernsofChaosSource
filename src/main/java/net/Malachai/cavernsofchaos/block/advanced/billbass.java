package net.malachai.cavernsofchaos.block.advanced;

import net.malachai.cavernsofchaos.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class billbass extends HorizontalDirectionalBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public float active_time = 0f;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_25;
    private static final VoxelShape[] DIRECTIONALSHAPES = new VoxelShape[]{
            // South
            Shapes.or(
                    box(2.0D, 3.0D, 0.0D, 14.0D, 12.0D, 4.0D)
            ).optimize(),
            // West
            Shapes.or(
                    box(12.0D, 3.0D, 2.0D, 16.0D, 12.0D, 14.0D)
            ).optimize(),
            // North
            Shapes.or(
                    box(2.0D, 3.0D, 12.0D, 14.0D, 12.0D, 16.0D)
            ).optimize(),
            // East
            Shapes.or(
                    box(0.0D, 3.0D, 2.0D, 4.0D, 12.0D, 14.0D)
            ).optimize()
    };
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        return DIRECTIONALSHAPES[direction.get2DDataValue()];
    }

    public billbass(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LIT, true));
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        active_time = 0f;
        super.onPlace(pState, pLevel, pPos, pOldState, pIsMoving);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite()).setValue(LIT, true).setValue(AGE,0);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, LIT, AGE);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(pState.getValue(AGE) == 0) {
            pLevel.playSound((Player) null, pPos, ModSounds.BASS.get(), SoundSource.BLOCKS, 0.8F, 1.0F);
            pLevel.scheduleTick(pPos, this, 0);
            pState.setValue(AGE, pState.getValue(AGE)+1);
            pLevel.setBlock(pPos, pState.setValue(LIT,false), 3);
        return InteractionResult.SUCCESS;
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if(pState.getValue(AGE) == 0) {
            pLevel.playSound((Player) null, pPos, ModSounds.BASS.get(), SoundSource.BLOCKS, 1.3F, 1.0F);
            pLevel.scheduleTick(pPos, this, 20);
            pState.setValue(AGE, pState.getValue(AGE)+1);
        }
        super.randomTick(pState, pLevel, pPos, pRandom);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        super.animateTick(pState, pLevel, pPos, pRandom);
    }

    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        //pLevel.playSound((Player) null,pPos,ModSounds.INSERT_COIN.get(),SoundSource.BLOCKS,1,1);
        if (pState.getValue(AGE) < 4) {
            pState.setValue(AGE, pState.getValue(AGE)+1) ;
                if (pState.getValue(LIT)) {
                    //pState.setValue(LIT, false);
                    pLevel.setBlock(pPos, pState.cycle(LIT).setValue(AGE, pState.getValue(AGE)), 3);
                } else {
                    //pState.setValue(LIT, true);
                    pLevel.setBlock(pPos, pState.cycle(LIT).setValue(AGE, pState.getValue(AGE)), 3);
                }

            //}
            pLevel.scheduleTick(pPos, this, 36);
        } else {
                //pState.setValue(LIT, true);
                pLevel.setBlock(pPos, pState.setValue(LIT,false).setValue(AGE, 0), 3);
                active_time = 0;
        }


        super.tick(pState, pLevel, pPos, pRandom);
    }
}
