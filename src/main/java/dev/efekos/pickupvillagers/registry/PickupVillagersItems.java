package dev.efekos.pickupvillagers.registry;

import dev.efekos.pickupvillagers.PickupVillagers;
import dev.efekos.pickupvillagers.item.VillagerItem;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.entity.passive.VillagerEntity;
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

    public static final Item ZOMBIE_VILLAGER = register("zombie_villager",new VillagerItem(PickupVillagersBlocks.ZOMBIE_VILLAGER,new Item.Settings().maxCount(1),entity -> entity instanceof ZombieVillagerEntity));
    public static final VillagerItem VILLAGER = register("villager", new VillagerItem(PickupVillagersBlocks.VILLAGER, new Item.Settings().maxCount(1),entity -> entity instanceof VillagerEntity));
    public static final Item VILLAGERI = register("villageri", new Item(new Item.Settings()));
    public static final Item ZOMBIE_VILLAGERI = register("zombie_villageri", new Item(new Item.Settings()));

}
