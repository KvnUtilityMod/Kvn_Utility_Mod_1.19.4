package net.kvn.modules.client;


import net.kvn.event.events.HudRender;
import net.kvn.modules.Category;
import net.kvn.modules.Module;
import net.kvn.utils.InventoryUtil.CraftingInvUtil;
import net.kvn.utils.InventoryUtil.CurrentInvUtil;
import net.kvn.utils.InventoryUtil.PlayerInvUtil;
import net.minecraft.client.gui.screen.ingame.CraftingScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;

import static net.kvn.KvnUtilityMod.mc;

public class QuickCrafting extends Module implements HudRender {

    public QuickCrafting() {
        super("QuickCrafting", "Quickly craft items", Category.CLIENT);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onHudRender(MatrixStack matrices, float tickDelta) {
        if (!this.isActive() || mc.player == null) return;

        if (mc.currentScreen instanceof CraftingScreen) {
            Item item = CurrentInvUtil.getInvSlot(0).getItem();

            if (item != Items.AIR && CraftingInvUtil.isRoomForItem(item)) {
                CurrentInvUtil.clickSlot(0, SlotActionType.QUICK_MOVE);
            }
        }

        if (mc.currentScreen instanceof InventoryScreen) {
            Item item = CurrentInvUtil.getInvSlot(0).getItem();

            if (item != Items.AIR && PlayerInvUtil.isRoomForItem(item)) {
                CurrentInvUtil.clickSlot(0, SlotActionType.QUICK_MOVE);
            }
        }
    }
}
