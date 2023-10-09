package net.kvn.modules.Structures;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.kvn.event.events.RenderBox;
import net.kvn.event.events.SettingUpdate;
import net.kvn.modules.Category;
import net.kvn.modules.Module;
import net.kvn.settings.*;
import net.kvn.utils.PlayerUtil;
import net.kvn.utils.render.RenderBoxUtil;
import net.kvn.utils.world.Structure.KvnStructure;
import net.kvn.utils.world.Structure.LoadStrategies.BottomToTop;
import net.kvn.utils.world.Structure.LoadStrategy;
import net.kvn.utils.world.Structure.StructureLoader;
import net.kvn.utils.world.WorldUtil;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;

import static net.kvn.KvnUtilityMod.moduleManager;

public class Load extends Module implements RenderBox, SettingUpdate {

    public BooleanValue showOutline = addSetting(new BooleanValue("show outline", "Show box", this, false));
    public BooleanValue clearLoadArea = addSetting(new BooleanValue("clear load area", "clear load area", this, false));
    public ColorValue color = addSetting(new ColorValue("color", "color", this));
    public BlockPosValue loadPos = addSetting(new BlockPosValue("load position", "loadPos", this, null));
    public IntegerValue time = addSetting(new IntegerValue("loadTime (millis)", "time", this, 5000, 1, 100000));
    public ModeValue loadMode = addSetting(new ModeValue("load mode", "loadMode", this, new String[]{"BottomToTop"}, 0));

    public StructureLoader structureLoader = new StructureLoader();

    public Load() {
        super("Load", "Load", Category.STRUCTURES);
    }

    @Override
    public void renderBox(WorldRenderContext context, MatrixStack matrices) {
        if (showOutline.isTrue()) {
            KvnStructure structure = moduleManager.save.structure;
            if (!structure.isStructureSaved()) return;
            BlockPos pos2 = new BlockPos(loadPos.getPos().getX() + structure.getxDiff(), loadPos.getPos().getY() + structure.getyDiff(), loadPos.getPos().getZ() + structure.getzDiff());
            RenderBoxUtil.DrawBoxOutline(matrices, loadPos.getPos(), pos2, 0.12, color.getColorObj());
        }

        if (this.isActive()) {
            if(structureLoader.isLoading()) {
                structureLoader.loadNextBlocks();
            } else {
                PlayerUtil.sendChatMsg("Finished loading");
                this.updateActive(false);
            }
        }
    }

    @Override
    public void onEnable() {
        KvnStructure structure = moduleManager.save.structure;
        if (!structure.isStructureSaved()) {
            PlayerUtil.sendChatMsg("No structure saved");
            return;
        }

        structureLoader.startLoading(time.getValue(), structure, getLoadStrategy(), loadPos.getPos());
        super.onEnable();
        PlayerUtil.sendChatMsg("Loading structure with " + structureLoader.getBlocksToLoad().size() + " blocks");
    }

    private LoadStrategy getLoadStrategy() {
        switch (loadMode.getModeName()) {
            case "BottomToTop":
                return new BottomToTop();
            default:
                return null;
        }
    }

    @Override
    public void onSettingUpdate(Setting setting) {
        if (setting == clearLoadArea) {
            if (clearLoadArea.isTrue()) {
                for (int x = loadPos.getX(); x < loadPos.getX() + moduleManager.save.structure.getxDiff(); x++) {
                    for (int y = loadPos.getY(); y < loadPos.getY() + moduleManager.save.structure.getyDiff(); y++) {
                        for (int z = loadPos.getZ(); z < loadPos.getZ() + moduleManager.save.structure.getzDiff(); z++) {
                            WorldUtil.setBlockIfNeeded(new BlockPos(x, y, z), Blocks.AIR.getDefaultState());
                        }
                    }
                }
                clearLoadArea.updateValue(false);
            }
        }
    }
}
