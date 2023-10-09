package net.kvn.modules.client;

import net.kvn.event.events.MouseClick;
import net.kvn.modules.Category;
import net.kvn.modules.Module;
import net.kvn.settings.*;
import net.kvn.utils.input.TextUtil;
import net.kvn.utils.world.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import static net.kvn.KvnUtilityMod.mc;

public class TestModule extends Module implements MouseClick {

    public net.kvn.settings.BooleanValue testBoolean = addSetting(new BooleanValue("TestBoolean", "testBoolean", this, true));
    public BooleanValue testBoolean2 = addSetting(new BooleanValue("TestBoolean2", "testBoolean2", this, testBoolean, true));
    public BooleanValue testBoolean3 = addSetting(new BooleanValue("TestBoolean3", "testBoolean3", this, testBoolean2, true));
    public ModeValue testMode = addSetting(new ModeValue("TestMode", "testMode", this, testBoolean, new String[]{"mode1", "mode2", "mode3"} ,0));
    public BooleanValue testBoolean4 = addSetting(new BooleanValue("TestBoolean4", "testBoolean4", this, true));
    public ColorValue testColor = addSetting(new ColorValue("TestColor", "testColor", this, testMode));
    public IntegerValue testInteger = addSetting(new IntegerValue("TestInteger", "testInteger", this, testMode, 0, 0, 10));
    public SettingPlaceHolder testPlaceHolder = addSetting(new SettingPlaceHolder("TestPlaceHolder", "testPlaceHolder", this));
    public SettingPlaceHolder testPlaceHolder2 = addSetting(new SettingPlaceHolder("TestPlaceHolder2", "testPlaceHolder2", this, testPlaceHolder));
    public BlockTypeValue testBlockType = addSetting(new BlockTypeValue("TestBlockType", "testBlockType", this, testMode, "stone"));

    public TestModule() {
        super("TestModule", "testModule", Category.CLIENT);
        testColor.setModeIndex(1);
        testInteger.setModeIndex(1);
        testBlockType.setModeIndex(0);
    }

    @Override
    public void onEnable() {
        super.onEnable();

        /*
        System.out.println(WorldUtil.getServerWorld().getRegistryKey().getValue());

        World world = mc.world;
        BlockPos blockPos = new BlockPos(0, 170, 0);
        //get the server world
        World serverWorld = mc.getServer().getOverworld();
        serverWorld.setBlockState(blockPos, Blocks.DIAMOND_BLOCK.getDefaultState());

        String s = "";
        String[] strings = new String[]{"stone", "dirt", "grass_block", "stonebricks", "onee", "twoo", "threee", "tone", "ttwo", "tthree"};
        System.out.println(s);
        for (String string : strings) {
            System.out.println(string + " score: " + TextUtil.getSimilarity(s, string));
        }

        Block block = Blocks.STONE;

        BlockState blockState = block.getDefaultState();
        //world.setBlockState(blockPos, blockState);

         */
        /*
        Iterator<Block> var0 = Registry.BLOCK.iterator();
        var0.forEachRemaining(block1 -> {
            System.out.println(block1.getTranslationKey());
        });

         */

        //block.minecraft.deepslate_iron_ore


        //world.setBlockState()

    }

    @Override
    public void onMouseClick(int button, int x, int y) {
        /*
        System.out.println("Mouse clicked!");
        print the button, pressed, x and y
        System.out.println("Button: " + button + " Pressed: " + pressed + " X: " + x + " Y: " + y);
        */
    }
}
