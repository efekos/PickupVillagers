package dev.efekos.pickupvillagers.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class VillagerPlankBlock extends Block {

    public static VoxelShape makeShape() {
        return VoxelShapes.cuboid(0.125, 0, 0.125, 0.875, 0.0625, 0.875);

    }

    public VillagerPlankBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return makeShape();
    }

}
