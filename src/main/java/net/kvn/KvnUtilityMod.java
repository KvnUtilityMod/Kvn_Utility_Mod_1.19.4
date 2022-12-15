package net.kvn;

import net.fabricmc.api.ModInitializer;
import net.kvn.control.ToggleModules;
import net.kvn.event.CustomEventHandler;
import net.kvn.event.FabricEvents;
import net.kvn.gui.MainGui.MainGui;
import net.kvn.hud.InfoDisplay.InfoDisplay;
import net.kvn.modules.Module;
import net.kvn.modules.ModuleManager;
import net.kvn.settings.*;
import net.kvn.utils.file.FileReader;
import net.kvn.utils.file.FileUtil;
import net.kvn.utils.file.FileWriter;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;

public class KvnUtilityMod implements ModInitializer {

	public static final String MOD_ID = "kvnutilitymod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	//dir enabled and initialized modules
	public static final String modulesDir = FileUtil.getKvnFolder() + "Modules";
	public static final String enabledModules = modulesDir + File.separator + "enabledModules.txt";
	public static final String initializedModules = modulesDir + File.separator + "initializedModules.txt";
	public static final String visibleModules = modulesDir + File.separator + "visibleModules.txt";
	public static final String moduleKeys = modulesDir + File.separator + "moduleKeys.txt";
	public static final String settingsDir = FileUtil.getKvnFolder() + "Settings";
	public static final String booleanSettings = settingsDir + File.separator + "BooleanSettings.txt";
	public static final String integerSettings = settingsDir + File.separator + "IntegerSettings.txt";
	public static final String blockPosSettings = settingsDir + File.separator + "BlockPosSettings.txt";
	public static final String colorSettings = settingsDir + File.separator + "ColorSettings.txt";

	public static MinecraftClient mc = MinecraftClient.getInstance();
	public static CustomEventHandler customEventHandler;
	public static ModuleManager moduleManager;
	public static InfoDisplay infoDisplay;
	public FabricEvents fabricEvents;

	@Override
	public void onInitialize() {
		customEventHandler = new CustomEventHandler();
		moduleManager = new ModuleManager();
		infoDisplay = new InfoDisplay();
		fabricEvents = new FabricEvents();
		customEventHandler.addListener(new ToggleModules());
		customEventHandler.addListener(MainGui.INSTANCE);

		createDirs();
		activateModules();
		activateSettings();
	}

	private void createDirs(){
		FileUtil.createDir(FileUtil.getKvnFolderWithoutSeparator());
		FileUtil.createDir(settingsDir);
		FileUtil.createDir(modulesDir);
	}

	private void activateModules(){

		ArrayList<String> enabledModules = FileReader.readLines(KvnUtilityMod.enabledModules);
		ArrayList<String> visibleModules = FileReader.readLines(KvnUtilityMod.visibleModules);
		ArrayList<String> moduleKeys = FileReader.readLines(KvnUtilityMod.moduleKeys);
		ArrayList<String> initializedModules = FileReader.readLines(KvnUtilityMod.initializedModules);

		for (Module m : moduleManager.getModules()){

			enabledModules = m.loadIsEnabledFromFile(enabledModules, initializedModules);
			visibleModules = m.getVisible().loadIsVisibleFromFile(visibleModules);
			moduleKeys = m.getKeySetting().loadKeyFromFile(moduleKeys);
			if (!initializedModules.contains(m.getName())) initializedModules.add(m.getName());
		}

		FileWriter.writeLines(enabledModules, KvnUtilityMod.enabledModules);
		FileWriter.writeLines(visibleModules, KvnUtilityMod.visibleModules);
		FileWriter.writeLines(moduleKeys, KvnUtilityMod.moduleKeys);
		FileWriter.writeLines(initializedModules, KvnUtilityMod.initializedModules);
	}

	private void activateSettings(){
		ArrayList<String> booleanSettings = FileReader.readLines(KvnUtilityMod.booleanSettings);
		ArrayList<String> integerSettings = FileReader.readLines(KvnUtilityMod.integerSettings);
		ArrayList<String> blockPosSettings = FileReader.readLines(KvnUtilityMod.blockPosSettings);
		ArrayList<String> colorSettings = FileReader.readLines(KvnUtilityMod.colorSettings);

		for (Module module : moduleManager.getModules()){

			for (Setting setting : module.getSettings()){

				if (setting instanceof BooleanValue) booleanSettings = ((BooleanValue) setting).loadBooleanFromFile(booleanSettings);
				if (setting instanceof IntegerValue) integerSettings = ((IntegerValue) setting).loadIntegerFromFile(integerSettings);
				if (setting instanceof BlockPosValue) blockPosSettings = ((BlockPosValue) setting).loadBlockPosFromFile(blockPosSettings);
				if (setting instanceof ColorValue) colorSettings = ((ColorValue) setting).loadColorFromFile(colorSettings);
			}
		}

		FileWriter.writeLines(booleanSettings, KvnUtilityMod.booleanSettings);
		FileWriter.writeLines(integerSettings, KvnUtilityMod.integerSettings);
		FileWriter.writeLines(blockPosSettings, KvnUtilityMod.blockPosSettings);
		FileWriter.writeLines(colorSettings, KvnUtilityMod.colorSettings);
	}
}
