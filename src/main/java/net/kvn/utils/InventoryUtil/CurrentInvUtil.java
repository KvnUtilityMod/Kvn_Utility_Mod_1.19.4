package net.kvn.utils.InventoryUtil;

import net.minecraft.client.gui.screen.ingame.CraftingScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;

import static net.kvn.KvnUtilityMod.mc;

public class CurrentInvUtil {

    public static ItemStack getInvSlot(int slot) {
        return mc.player.currentScreenHandler.getSlot(slot).getStack();
    }

    public static void clickSlot(int slot, SlotActionType actionType) {
        try {
            mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, slot, 0, actionType, mc.player);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
