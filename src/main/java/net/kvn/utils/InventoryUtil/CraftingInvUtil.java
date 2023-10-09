package net.kvn.utils.InventoryUtil;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import static net.kvn.KvnUtilityMod.mc;

public class CraftingInvUtil {

    // is inv full?
    public static boolean isInvFull() {
        return !isItemInPlayerInv(Items.AIR);
    }

    public static boolean isRoomForItem(Item item) {
        for (int i = 10; i < 46; i++) {
            ItemStack stack = CurrentInvUtil.getInvSlot(i);

            if (stack.getItem() == Items.AIR) {
                return true;
            }
            if (stack.getItem() == item && stack.getCount() < stack.getMaxCount()) {
                return true;
            }
        }
        return false;
    }

    public static boolean isItemInPlayerInv(Item item) {
        for (int i = 10; i < 46; i++) {
            if (CurrentInvUtil.getInvSlot(i).getItem() == item) {
                return true;
            }
        }
        return false;
    }
}
