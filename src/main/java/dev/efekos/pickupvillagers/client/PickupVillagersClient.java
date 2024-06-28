package dev.efekos.pickupvillagers.client;

import dev.efekos.pickupvillagers.registry.PickupVillagersBlocks;
import dev.efekos.pickupvillagers.registry.PickupVillagersComponentTypes;
import dev.efekos.pickupvillagers.registry.PickupVillagersItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.math.RotationAxis;

public class PickupVillagersClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        BlockRenderLayerMap.INSTANCE.putBlock(PickupVillagersBlocks.VILLAGER, RenderLayer.getCutout());
        BuiltinItemRendererRegistry.INSTANCE.register(PickupVillagersItems.VILLAGER, (stack, mode, matrices, vertexConsumers, light, overlay) -> {
            ItemRenderer renderer = MinecraftClient.getInstance().getItemRenderer();
            EntityRenderDispatcher dispatcher = MinecraftClient.getInstance().getEntityRenderDispatcher();

            matrices.push();
            matrices.translate(0.5f, 0.5f, 0.5f);
            matrices.scale(1f, 1f, 1f);
            matrices.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(0));
            renderer.renderItem(PickupVillagersItems.VILLAGERI.getDefaultStack(), mode, light, overlay, matrices, vertexConsumers, null, 1);
            matrices.pop();

            if(!stack.getComponents().contains(PickupVillagersComponentTypes.VILLAGER_DATA))return;
            NbtCompound nbt = stack.getComponents().get(PickupVillagersComponentTypes.VILLAGER_DATA).copyNbt();
            if (!nbt.contains("villager", NbtElement.COMPOUND_TYPE)) return;
            VillagerEntity entity = new VillagerEntity(EntityType.VILLAGER, MinecraftClient.getInstance().world);
            entity.readNbt(nbt.getCompound("villager"));

            matrices.push();
            switch (mode) {
                case FIXED -> {
                    matrices.translate(0.5f, 0.15f, 0.5f);
                    matrices.scale(0.4f, 0.4f, 0.4f);
                }
                case FIRST_PERSON_LEFT_HAND, FIRST_PERSON_RIGHT_HAND -> {
                    matrices.translate(0.5f, 0f, 0.5f);
                    matrices.scale(0.3f, 0.3f, 0.3f);
                }
                case THIRD_PERSON_LEFT_HAND, THIRD_PERSON_RIGHT_HAND -> {
                    matrices.translate(0.5f, 0.45f, 0.5f);
                    matrices.scale(0.15f, 0.15f, 0.15f);
                }
                case GUI -> {
                    matrices.translate(0.5f, 0.35f, 0.5f);
                    matrices.scale(0.25f, 0.25f, 0.25f);
                }
                default -> {
                    matrices.translate(0.5f, 0.35f, 0.5f);
                    matrices.scale(0.2f, 0.2f, 0.2f);
                }
            }
            dispatcher.render(entity, 0, 0, 0, 0, 0.5f, matrices, vertexConsumers, light);
            matrices.pop();
        });

    }

}
