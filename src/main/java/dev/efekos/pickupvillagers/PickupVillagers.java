package dev.efekos.pickupvillagers;

import dev.efekos.pickupvillagers.registry.PickupVillagersBlocks;
import dev.efekos.pickupvillagers.registry.PickupVillagersItems;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PickupVillagers implements ModInitializer {

    public static final String MOD_ID = "pickupvillagers";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        PickupVillagersBlocks.VILLAGER.getName();
        PickupVillagersItems.VILLAGER.getName();
    }
}
