package net.haoooozhang.thinkbeforedrop;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import me.shedaniel.autoconfig.AutoConfig;

@OnlyIn(Dist.CLIENT)
public class DropManager {
    private static int lastSlot = -1;
    private static long lastDropTime = 0;
    private static long lastDroppedTime = 0;
    private static boolean dropped = false;
    private static Item lastDropItem = null;
    private static ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

    public static boolean shouldThrow(ItemStack stack, int slot) {
        long now = System.currentTimeMillis();
        if (slot != lastSlot && stack.getItem() != lastDropItem) {
            lastDropTime = 0;
            dropped = false;
        }
        if (dropped && now - lastDroppedTime <= config.time.maxSecond * 1000 && stack.getItem() == lastDropItem) {
            lastDropItem = stack.getItem();
            lastDroppedTime = now;
            return true;
        }
        if (now - lastDropTime >= config.time.minSecond * 1000 && now - lastDropTime <= config.time.maxSecond * 1000) {
            if (stack.getCount() != 1) {
                lastDroppedTime = now;
                dropped = true;
            }
            lastDropItem = stack.getItem();
            lastDropTime = 0;
            return true;
        }
        lastDropItem = null;
        lastDropTime = now;
        lastSlot = slot;
        return false;
    }

    public static boolean isEnabled() {
        return config.enabled;
    }

    public static Component showDropTips() {
        String second = Double.toString(config.time.minSecond);
        String dropKey = Minecraft.getInstance().options.keyDrop.getTranslatedKeyMessage().getString();
        return Component.translatable("tbd.drop_tips", Component.literal(second).withStyle(ChatFormatting.RED),
                Component.literal(dropKey).withStyle(ChatFormatting.RED));
    }
}
