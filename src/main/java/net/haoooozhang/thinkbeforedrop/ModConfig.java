package net.haoooozhang.thinkbeforedrop;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@Config(name = ThinkBeforeDrop.MODID)
public class ModConfig implements ConfigData {
    public boolean enabled = true;
    @ConfigEntry.Gui.CollapsibleObject
    public Time time = new Time();

    @Config(name = "time")
    public static class Time implements ConfigData {
        public double minSecond = 1.0;
        public double maxSecond = 5.0;
    }
}
