package dev.efekos.pickupvillagers.registry;

import dev.efekos.pickupvillagers.PickupVillagers;
import dev.efekos.pickupvillagers.block.VillagerBlock;
import dev.efekos.pickupvillagers.block.VillagerPlankBlock;
import dev.efekos.pickupvillagers.block.ZombieVillagerBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class PickupVillagersBlocks {


    public static <T extends Block> T register(String id, T block) {
        Registry.register(Registries.BLOCK, Identifier.of(PickupVillagers.MOD_ID, id), block);
        PickupVillagers.LOGGER.info("Registered block pickupvillagers:" + id);

        return block;
    }

    public static final ZombieVillagerBlock ZOMBIE_VILLAGER = register("zombie_villager", new ZombieVillagerBlock(AbstractBlock.Settings.copy(Blocks.STONE).nonOpaque()));
    public static final VillagerBlock VILLAGER = register("villager", new VillagerBlock(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque()));
    public static final VillagerPlankBlock VILLAGER_PLANK = register("villager_plank", new VillagerPlankBlock(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).strength(0.5f)));
    public static final VillagerPlankBlock ZOMBIE_VILLAGER_PLANK = register("zombie_villager_plank", new VillagerPlankBlock(AbstractBlock.Settings.copy(Blocks.STONE).strength(0.5f)));


}
