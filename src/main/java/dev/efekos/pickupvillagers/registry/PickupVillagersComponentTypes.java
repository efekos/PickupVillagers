package dev.efekos.pickupvillagers.registry;

import net.minecraft.component.ComponentType;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.function.UnaryOperator;

public class PickupVillagersComponentTypes {

    private static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, id, (builderOperator.apply(ComponentType.builder())).build());
    }

    public static final ComponentType<NbtComponent> VILLAGER_DATA = register("villager_data",nbtComponentBuilder ->
        nbtComponentBuilder.codec(NbtComponent.CODEC_WITH_ID).packetCodec(NbtComponent.PACKET_CODEC)
    );

    public static void run(){

    }

}
