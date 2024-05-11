package dev.efekos.pickupvillagers.registry;

import dev.efekos.pickupvillagers.PickupVillagers;
import dev.efekos.pickupvillagers.block.VillagerBlock;
import dev.efekos.pickupvillagers.block.VillagerPlankBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class PickupVillagersBlocks {

    public static <T extends Block> T register(String id, T block){
        Registry.register(Registries.BLOCK,new Identifier(PickupVillagers.MOD_ID,id),block);
        PickupVillagers.LOGGER.info("Registered block pickupvillagers:"+id);

        return block;
    }

    public static final VillagerBlock VILLAGER = register("villager",new VillagerBlock(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque()));
    public static final VillagerPlankBlock VILLAGER_PLANK = register("villager_plank",new VillagerPlankBlock(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).strength(0.5f)));


}
