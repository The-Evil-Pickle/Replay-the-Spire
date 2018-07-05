package replayTheSpire.patches;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.rooms.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import replayTheSpire.*;
import com.evacipated.cardcrawl.modthespire.lib.*;
import java.util.*;
import com.megacrit.cardcrawl.vfx.campfire.*;
import basemod.*;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.daily.*;
import com.megacrit.cardcrawl.ui.campfire.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.core.*;

@SpirePatch(cls = "com.megacrit.cardcrawl.rooms.CampfireUI", method = "initializeButtons")
public class ReplayCampButtonsPatch
{
    public static void Postfix(final Object meObj) {
		if (AbstractDungeon.player.hasRelic("Chameleon Ring")) {
			final CampfireUI campfire = (CampfireUI)meObj;
			try {
				final ArrayList<AbstractCampfireOption> campfireButtons = (ArrayList<AbstractCampfireOption>)ReflectionHacks.getPrivate((Object)campfire, (Class)CampfireUI.class, "buttons");
				final ChameleonBrewOption button = new ChameleonBrewOption();
				campfireButtons.add(button);
				float x = 950.f;
				float y = 990.0f - (270.0f * (float)((campfireButtons.size() + 1) / 2));
				if (campfireButtons.size() % 2 == 0) {
					x = 1110.0f;
					campfireButtons.get(campfireButtons.size() - 2).setPosition(800.0f * Settings.scale, y * Settings.scale);
				}
				campfireButtons.get(campfireButtons.size() - 1).setPosition(x * Settings.scale, y * Settings.scale);
			}
			catch (SecurityException | IllegalArgumentException ex2) {
				//final RuntimeException ex;
				//final RuntimeException e = ex;
				//e.printStackTrace();
			}
		}
		if (AbstractDungeon.player.hasRelic("Dead Branch")) {
			final CampfireUI campfire = (CampfireUI)meObj;
			try {
				final ArrayList<AbstractCampfireOption> campfireButtons = (ArrayList<AbstractCampfireOption>)ReflectionHacks.getPrivate((Object)campfire, (Class)CampfireUI.class, "buttons");
				final BranchBurnOption button = new BranchBurnOption();
				campfireButtons.add(button);
				float x = 950.f;
				float y = 990.0f - (270.0f * (float)((campfireButtons.size() + 1) / 2));
				if (campfireButtons.size() % 2 == 0) {
					x = 1110.0f;
					campfireButtons.get(campfireButtons.size() - 2).setPosition(800.0f * Settings.scale, y * Settings.scale);
				}
				campfireButtons.get(campfireButtons.size() - 1).setPosition(x * Settings.scale, y * Settings.scale);
			}
			catch (SecurityException | IllegalArgumentException ex2) {
				//final RuntimeException ex;
				//final RuntimeException e = ex;
				//e.printStackTrace();
			}
		}
    }
}