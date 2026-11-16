package com.ftteam.fanciertrees;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;


public class BranchBlock extends Block implements SimpleWaterloggedBlock {

    public static final MapCodec<BranchBlock> CODEC =
        simpleCodec(BranchBlock::new);

    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty DOWN = BlockStateProperties.DOWN;
    public static final BooleanProperty WATERLOGGED =
        BlockStateProperties.WATERLOGGED;

    private static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION =
        Map.of(
            Direction.NORTH, NORTH,
            Direction.EAST, EAST,
            Direction.SOUTH, SOUTH,
            Direction.WEST, WEST,
            Direction.UP, UP,
            Direction.DOWN, DOWN
        );

    private static final VoxelShape CORE =
        Block.box(4.0D, 4.0D, 4.0D, 12.0D, 12.0D, 12.0D);

    private static final VoxelShape NORTH_SHAPE =
        Block.box(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 8.0D);

    private static final VoxelShape EAST_SHAPE =
        Block.box(8.0D, 4.0D, 4.0D, 16.0D, 12.0D, 12.0D);

    private static final VoxelShape SOUTH_SHAPE =
        Block.box(4.0D, 4.0D, 8.0D, 12.0D, 12.0D, 16.0D);

    private static final VoxelShape WEST_SHAPE =
        Block.box(0.0D, 4.0D, 4.0D, 8.0D, 12.0D, 12.0D);

    private static final VoxelShape UP_SHAPE =
        Block.box(4.0D, 8.0D, 4.0D, 12.0D, 16.0D, 12.0D);

    private static final VoxelShape DOWN_SHAPE =
        Block.box(4.0D, 0.0D, 4.0D, 12.0D, 8.0D, 12.0D);

    public BranchBlock(BlockBehaviour.Properties properties) {
        super(properties);

        this.registerDefaultState(
            this.stateDefinition.any()
                .setValue(NORTH, false)
                .setValue(EAST, false)
                .setValue(SOUTH, false)
                .setValue(WEST, false)
                .setValue(UP, false)
                .setValue(DOWN, false)
                .setValue(WATERLOGGED, false)
        );
    }

    @Override
    public MapCodec<BranchBlock> codec() {
        return CODEC;
    }

    private boolean connectsTo(
        BlockState state,
        BlockGetter level,
        BlockPos pos,
        Direction direction
    ) {
        Block block = state.getBlock();

        return block instanceof BranchBlock
            || state.is(BlockTags.LOGS);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockGetter level = context.getLevel();
        BlockPos pos = context.getClickedPos();

        FluidState fluidState = level.getFluidState(pos);

        BlockState state = this.defaultBlockState();

        for (Direction direction : Direction.values()) {
            BlockPos neighborPos = pos.relative(direction);
            BlockState neighborState = level.getBlockState(neighborPos);

            state = state.setValue(
                PROPERTY_BY_DIRECTION.get(direction),
                connectsTo(
                    neighborState,
                    level,
                    neighborPos,
                    direction
                )
            );
        }

        return state.setValue(
            WATERLOGGED,
            fluidState.getType() == Fluids.WATER
        );
    }

    @Override
    protected BlockState updateShape(
        BlockState state,
        Direction direction,
        BlockState neighborState,
        LevelAccessor level,
        BlockPos currentPos,
        BlockPos neighborPos
    ) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(
                currentPos,
                Fluids.WATER,
                Fluids.WATER.getTickDelay(level)
            );
        }

        BooleanProperty property = PROPERTY_BY_DIRECTION.get(direction);

        if (connectsTo(neighborState, level, neighborPos, direction)) {
            return state.setValue(property, true);
        }

        return state;
    }

    @Override
    protected VoxelShape getShape(
        BlockState state,
        BlockGetter level,
        BlockPos pos,
        CollisionContext context
    ) {
        VoxelShape shape = CORE;

        if (state.getValue(NORTH)) {
            shape = Shapes.or(shape, NORTH_SHAPE);
        }

        if (state.getValue(EAST)) {
            shape = Shapes.or(shape, EAST_SHAPE);
        }

        if (state.getValue(SOUTH)) {
            shape = Shapes.or(shape, SOUTH_SHAPE);
        }

        if (state.getValue(WEST)) {
            shape = Shapes.or(shape, WEST_SHAPE);
        }

        if (state.getValue(UP)) {
            shape = Shapes.or(shape, UP_SHAPE);
        }

        if (state.getValue(DOWN)) {
            shape = Shapes.or(shape, DOWN_SHAPE);
        }

        return shape;
    }

    private Direction getNearestBranchDirection(Vec3 pos) {
        double x = Math.abs(pos.x - 0.5);
        double y = Math.abs(pos.y - 0.5);
        double z = Math.abs(pos.z - 0.5);

        if (x > y && x > z) {
            return pos.x > 0.5 ? Direction.EAST : Direction.WEST;
        }

        if (y > x && y > z) {
            return pos.y > 0.5 ? Direction.UP : Direction.DOWN;
        }

        return pos.z > 0.5 ? Direction.SOUTH : Direction.NORTH;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!stack.is(ItemTags.AXES)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        BlockPos localPos = pos;
        Vec3 hitPos = hitResult.getLocation().subtract(Vec3.atLowerCornerOf(localPos));

        Direction direction = getNearestBranchDirection(hitPos);
        BooleanProperty property = PROPERTY_BY_DIRECTION.get(direction);

        if (!state.getValue(property)) {
            return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
        }

        if (!level.isClientSide) {
            level.setBlock(pos, state.setValue(property, false), 3);
        }

        return ItemInteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    protected VoxelShape getCollisionShape(
        BlockState state,
        BlockGetter level,
        BlockPos pos,
        CollisionContext context
    ) {
        return this.getShape(state, level, pos, context);
    }

    @Override
    protected VoxelShape getVisualShape(
        BlockState state,
        BlockGetter level,
        BlockPos pos,
        CollisionContext context
    ) {
        return this.getShape(state, level, pos, context);
    }

    @Override
    protected boolean isPathfindable(
        BlockState state,
        PathComputationType type
    ) {
        return false;
    }

    protected BlockState rotate(BlockState state, Rotation rot) {
        switch (rot) {
            case CLOCKWISE_180 -> {
                return (BlockState)((BlockState)((BlockState)((BlockState)state.setValue(NORTH, (Boolean)state.getValue(SOUTH))).setValue(EAST, (Boolean)state.getValue(WEST))).setValue(SOUTH, (Boolean)state.getValue(NORTH))).setValue(WEST, (Boolean)state.getValue(EAST));
            }
            case COUNTERCLOCKWISE_90 -> {
                return (BlockState)((BlockState)((BlockState)((BlockState)state.setValue(NORTH, (Boolean)state.getValue(EAST))).setValue(EAST, (Boolean)state.getValue(SOUTH))).setValue(SOUTH, (Boolean)state.getValue(WEST))).setValue(WEST, (Boolean)state.getValue(NORTH));
            }
            case CLOCKWISE_90 -> {
                return (BlockState)((BlockState)((BlockState)((BlockState)state.setValue(NORTH, (Boolean)state.getValue(WEST))).setValue(EAST, (Boolean)state.getValue(NORTH))).setValue(SOUTH, (Boolean)state.getValue(EAST))).setValue(WEST, (Boolean)state.getValue(SOUTH));
            }
            default -> {
                return state;
            }
        }
    }

    protected BlockState mirror(BlockState state, Mirror mirror) {
        switch (mirror) {
            case LEFT_RIGHT -> {
                return (BlockState)((BlockState)state.setValue(NORTH, (Boolean)state.getValue(SOUTH))).setValue(SOUTH, (Boolean)state.getValue(NORTH));
            }
            case FRONT_BACK -> {
                return (BlockState)((BlockState)state.setValue(EAST, (Boolean)state.getValue(WEST))).setValue(WEST, (Boolean)state.getValue(EAST));
            }
            default -> {
                return super.mirror(state, mirror);
            }
        }
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED)
            ? Fluids.WATER.getSource(false)
            : super.getFluidState(state);
    }

    @Override
    protected void createBlockStateDefinition(
        StateDefinition.Builder<Block, BlockState> builder
    ) {
        builder.add(
            NORTH,
            EAST,
            SOUTH,
            WEST,
            UP,
            DOWN,
            WATERLOGGED
        );
    }
}

