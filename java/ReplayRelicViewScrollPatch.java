package ReplayTheSpireMod.patches;

import java.util.*;
import ReplayTheSpireMod.*;
import com.megacrit.cardcrawl.screens.mainMenu.*;
import basemod.*;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.*;

@SpirePatch(cls = "com.megacrit.cardcrawl.screens.mainMenu.RelicViewScreen", method = "open")
public class ReplayRelicViewScrollPatch {
	
	public static void Postfix(RelicViewScreen __Instance) {
		ReflectionHacks.setPrivate((Object)__Instance, (Class)RelicViewScreen.class, "scrollUpperBound", (Object)(2800.0f * Settings.scale));
	}
	
}