package dev.efekos.pickupvillagers.item;

import dev.efekos.pickupvillagers.registry.PickupVillagersComponentTypes;
import net.minecraft.block.Block;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;

import java.util.ArrayList;
import java.util.List;

public class VillagerItem extends BlockItem {

    public VillagerItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (!user.isSneaking()) return super.useOnEntity(stack, user, entity, hand);
        if (hand != Hand.MAIN_HAND) return super.useOnEntity(stack, user, entity, hand);
        if (!(entity instanceof VillagerEntity)) return super.useOnEntity(stack, user, entity, hand);
        if (stack.getComponents().contains(PickupVillagersComponentTypes.VILLAGER_DATA))
            return super.useOnEntity(stack, user, entity, hand);
        boolean isClient = user.getWorld().isClient;

        if (!isClient) {
            NbtCompound stackCompound = stack.get(PickupVillagersComponentTypes.VILLAGER_DATA).copyNbt();
            NbtCompound entityCompound = new NbtCompound();
            entity.writeNbt(entityCompound);
            stackCompound.put("villager", entityCompound);
            NbtComponent.set(PickupVillagersComponentTypes.VILLAGER_DATA,stack,stackCompound);
            user.setStackInHand(hand, stack);
            entity.getWorld().playSound(entity, entity.getBlockPos(), SoundEvents.ITEM_ARMOR_EQUIP_LEATHER.value(), SoundCategory.BLOCKS, 1f, 1f);
            entity.discard();
        }
        return ActionResult.success(isClient);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);

        if(!stack.getComponents().contains(PickupVillagersComponentTypes.VILLAGER_DATA))return;
        NbtCompound nbt = stack.getComponents().get(PickupVillagersComponentTypes.VILLAGER_DATA).copyNbt();
        if (!nbt.contains("villager", NbtElement.COMPOUND_TYPE)) {
            tooltip.add(Text.translatable("block.pickupvillagers.villager.none").formatted(Formatting.GRAY));
            return;
        }
        NbtCompound villager = nbt.getCompound("villager");
        NbtCompound VillagerData = villager.getCompound("VillagerData");
        NbtList Recipes = null;
        if (villager.contains("Offers", NbtElement.COMPOUND_TYPE))
            Recipes = villager.getCompound("Offers").getList("Recipes", NbtElement.COMPOUND_TYPE);
        String profession = VillagerData.getString("profession");

        tooltip.add(
                Text.translatable("entity.minecraft.villager." + (profession.contains(":") ? profession.split(":")[1] : profession)).formatted(Formatting.GRAY)
                        .append(Text.literal(" - ").formatted(Formatting.GRAY))
                        .append(Text.translatable("merchant.level." + VillagerData.getInt("level")).formatted(Formatting.GRAY))
        );
        if (Recipes != null) {
            tooltip.add(Text.translatable("block.pickupvillagers.tooltip.trade", Recipes.size()).formatted(Formatting.GRAY));

            ArrayList<Text> list = new ArrayList<>();
            for (NbtElement recipe : Recipes) {
                NbtCompound compound = (NbtCompound) recipe;
                String s = compound.getCompound("sell").getString("id");
                if (s.matches("^(minecraft:)?enchanted_book$")) {
                    for (NbtElement element : compound.getCompound("sell").getCompound("tag").getList("StoredEnchantments", NbtElement.COMPOUND_TYPE)) {
                        NbtCompound enchCompound = (NbtCompound) element;
                        String[] split = enchCompound.getString("id").split(":");
                        list.add(Text.translatable("enchantment." + split[0] + "." + split[1]).formatted(Formatting.DARK_PURPLE));
                    }
                }
            }

            if (!list.isEmpty()) {
                Text text1 = list.stream().reduce((text, text2) -> text.copy().append(Text.literal(", ")).append(text2)).get();
                tooltip.add(text1);
            }

        }
    }
}
