package net.kvn.event.events;

import net.kvn.settings.Setting;

public interface SettingUpdate {
    void onSettingUpdate(Setting setting);
}
