package dev.efekos.pickupvillagers.registry;

import dev.efekos.pickupvillagers.PickupVillagers;
import dev.efekos.pickupvillagers.item.VillagerItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class PickupVillagersItems {

    public static <T extends Item> T register(String id, T item) {
        Registry.register(Registries.ITEM, Identifier.of(PickupVillagers.MOD_ID, id), item);
        PickupVillagers.LOGGER.info("Registered item pickupvillagers:" + id);
        return item;
    }

    public static final VillagerItem VILLAGER = register("villager", new VillagerItem(PickupVillagersBlocks.VILLAGER, new Item.Settings().maxCount(1)));
    public static final Item VILLAGERI = register("villageri", new Item(new Item.Settings()));

}
