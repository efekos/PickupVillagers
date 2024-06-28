package dev.efekos.pickupvillagers.block;

import dev.efekos.pickupvillagers.registry.PickupVillagersBlocks;
import dev.efekos.pickupvillagers.registry.PickupVillagersComponentTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class VillagerBlock extends Block {

    public static VoxelShape makeShape() {
        return VoxelShapes.union(
                VoxelShapes.cuboid(0.125, 0, 0.125, 0.875, 0.0625, 0.875),
                VoxelShapes.cuboid(0.1875, 0.0625, 0.1875, 0.8125, 0.6875, 0.8125)
        );
    }

    public VillagerBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return makeShape();
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (!world.isClient()) {

            NbtCompound nbt = itemStack.getComponents().get(PickupVillagersComponentTypes.VILLAGER_DATA).copyNbt();
            if (nbt.contains("villager", NbtElement.COMPOUND_TYPE)) {
                world.breakBlock(pos, false);
                if (world.getDifficulty() == Difficulty.NORMAL || world.getDifficulty() == Difficulty.HARD)
                    world.setBlockState(pos, PickupVillagersBlocks.VILLAGER_PLANK.getDefaultState());

                VillagerEntity entity = new VillagerEntity(EntityType.VILLAGER, world);
                entity.readNbt(nbt.getCompound("villager"));
                entity.setPos(pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5);
                entity.setUuid(UUID.randomUUID());
                entity.setHeadYaw(0);
                entity.setPitch(0f);
                entity.setYaw(0);
                entity.setBodyYaw(0);
                world.spawnEntity(entity);
                world.playSound(entity, pos, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.BLOCKS, 1f, 1f);
            }

        }
    }
}
