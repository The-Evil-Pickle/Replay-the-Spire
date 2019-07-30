package replayTheSpire.patches;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.mod.replay.relics.*;
import com.megacrit.cardcrawl.mod.replay.rooms.*;
import com.megacrit.cardcrawl.mod.replay.ui.campfire.*;
import com.megacrit.cardcrawl.mod.replay.vfx.*;
import com.megacrit.cardcrawl.mod.replay.vfx.campfire.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.BottledFlame;
import com.megacrit.cardcrawl.relics.BottledLightning;
import com.megacrit.cardcrawl.relics.BottledTornado;
import com.megacrit.cardcrawl.relics.DeadBranch;
import com.megacrit.cardcrawl.relics.PeacePipe;
import com.megacrit.cardcrawl.relics.Shovel;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import com.megacrit.cardcrawl.ui.campfire.DigOption;
import com.megacrit.cardcrawl.ui.campfire.RestOption;
import com.megacrit.cardcrawl.ui.campfire.SmithOption;
import com.megacrit.cardcrawl.ui.campfire.TokeOption;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import replayTheSpire.*;
import replayTheSpire.replayxover.infinitebs;

import com.evacipated.cardcrawl.modthespire.lib.*;
import java.util.*;
import java.util.function.*;

import basemod.*;
import basemod.abstracts.CustomBottleRelic;

import com.megacrit.cardcrawl.daily.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.core.*;

@SpirePatch(cls = "com.megacrit.cardcrawl.rooms.CampfireUI", method = "initializeButtons")
public class ReplayCampButtonsPatch
{
	public static ArrayList<AbstractCampfireOption> GetBonfireOptions() {
		final ArrayList<AbstractCampfireOption> optionsYo = new ArrayList<AbstractCampfireOption>();
		if ((!AbstractDungeon.player.hasRelic("Coffee Dripper") && AbstractDungeon.currMapNode == BonfirePatches.bonfireNode) || (AbstractDungeon.player.hasRelic("Coffee Dripper") && AbstractDungeon.player.hasRelic(Multitool.ID))) {
			optionsYo.add(new RestOption(true));
		}
		if ((!ModHelper.isModEnabled("Midas") && !AbstractDungeon.player.hasRelic("Fusion Hammer")  && AbstractDungeon.currMapNode == BonfirePatches.bonfireNode) || ((ModHelper.isModEnabled("Midas") || AbstractDungeon.player.hasRelic("Fusion Hammer")) && AbstractDungeon.player.hasRelic(Multitool.ID))) {
			optionsYo.add(new SmithOption(AbstractDungeon.player.masterDeck.getUpgradableCards().size() > 0));
		}
		if (!AbstractDungeon.player.hasRelic(PeacePipe.ID) ) {
			optionsYo.add(new TokeOption(true));
		}
		if (!AbstractDungeon.player.hasRelic(Shovel.ID)) {
			optionsYo.add(new DigOption());
		}
		if (!AbstractDungeon.player.hasRelic(ChameleonRing.ID)) {
			optionsYo.add(new ChameleonBrewOption());
		}
		if (!AbstractDungeon.player.hasRelic("Replay:Pickaxe")) {
			optionsYo.add(new PickMineOption());
		}
		if (!AbstractDungeon.player.hasRelic(PocketPolymer.ID)) {
			optionsYo.add(new PolymerizeTransformOption());
		}
		if (!AbstractDungeon.player.hasRelic(GremlinFood.ID)) {
			optionsYo.add(new BonfireMultitaskOption());
		}
		if (!AbstractDungeon.eventList.isEmpty()) {
			optionsYo.add(new BonfireExploreOption());
		}
		return optionsYo;
	}
	
    public static void Postfix(final Object meObj) {

		if (AbstractDungeon.player.hasRelic(ChameleonRing.ID)) {
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
		if (AbstractDungeon.player.hasRelic("Replay:Pickaxe")) {
			final CampfireUI campfire = (CampfireUI)meObj;
			try {
				final ArrayList<AbstractCampfireOption> campfireButtons = (ArrayList<AbstractCampfireOption>)ReflectionHacks.getPrivate((Object)campfire, (Class)CampfireUI.class, "buttons");
				final PickMineOption button = new PickMineOption();
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
		if (AbstractDungeon.player.hasRelic(PocketPolymer.ID)) {
			final CampfireUI campfire = (CampfireUI)meObj;
			try {
				final ArrayList<AbstractCampfireOption> campfireButtons = (ArrayList<AbstractCampfireOption>)ReflectionHacks.getPrivate((Object)campfire, (Class)CampfireUI.class, "buttons");
				final PolymerizeTransformOption button = new PolymerizeTransformOption(true);
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
		if (AbstractDungeon.currMapNode == BonfirePatches.bonfireNode || AbstractDungeon.player.hasRelic(Multitool.ID)) {
			final CampfireUI campfire = (CampfireUI)meObj;
			try {
				final ArrayList<AbstractCampfireOption> campfireButtons = (ArrayList<AbstractCampfireOption>)ReflectionHacks.getPrivate((Object)campfire, (Class)CampfireUI.class, "buttons");
				
				
				final ArrayList<AbstractCampfireOption> optionsYo = GetBonfireOptions();
				Collections.shuffle(optionsYo);
				boolean foundrest = false;
				boolean foundsmith = false;
				optionsYo.add(new BonfireMultitaskOption());
				optionsYo.add(new SmithOption(AbstractDungeon.player.masterDeck.getUpgradableCards().size() > 0));
				optionsYo.add(new RestOption(true));
				if (AbstractDungeon.currMapNode == BonfirePatches.bonfireNode) {
			        if (ReplayTheSpireMod.foundmod_infinite) {
			        	infinitebs.TriggerBonfireQuest();
			        }
					for (AbstractCampfireOption o : campfireButtons) {
						if (o instanceof RestOption) {
							optionsYo.get(0).setPosition(o.hb.cX, o.hb.cY);
							campfireButtons.set(campfireButtons.indexOf(o), optionsYo.get(0));
							foundrest = true;
						}
						if (o instanceof SmithOption) {
							optionsYo.get(1).setPosition(o.hb.cX, o.hb.cY);
							campfireButtons.set(campfireButtons.indexOf(o), optionsYo.get(1));
							foundsmith = true;
						}
					}
				}
				
				if (!foundrest) {
					campfireButtons.add(optionsYo.get(0));
					float x = 950.f;
					float y = 990.0f - (270.0f * (float)((campfireButtons.size() + 1) / 2));
					if (campfireButtons.size() % 2 == 0) {
						x = 1110.0f;
						campfireButtons.get(campfireButtons.size() - 2).setPosition(800.0f * Settings.scale, y * Settings.scale);
					}
					campfireButtons.get(campfireButtons.size() - 1).setPosition(x * Settings.scale, y * Settings.scale);
				}
				if (!foundsmith) {
					campfireButtons.add(optionsYo.get(1));
					float x = 950.f;
					float y = 990.0f - (270.0f * (float)((campfireButtons.size() + 1) / 2));
					if (campfireButtons.size() % 2 == 0) {
						x = 1110.0f;
						campfireButtons.get(campfireButtons.size() - 2).setPosition(800.0f * Settings.scale, y * Settings.scale);
					}
					campfireButtons.get(campfireButtons.size() - 1).setPosition(x * Settings.scale, y * Settings.scale);
				}
				if (AbstractDungeon.currMapNode == BonfirePatches.bonfireNode) {
					campfireButtons.add(optionsYo.get(2));
					float x = 950.f;
					float y = 990.0f - (270.0f * (float)((campfireButtons.size() + 1) / 2));
					if (campfireButtons.size() % 2 == 0) {
						x = 1110.0f;
						campfireButtons.get(campfireButtons.size() - 2).setPosition(800.0f * Settings.scale, y * Settings.scale);
					}
					campfireButtons.get(campfireButtons.size() - 1).setPosition(x * Settings.scale, y * Settings.scale);
				}
			}
			catch (SecurityException | IllegalArgumentException ex2) {
				//final RuntimeException ex;
				//final RuntimeException e = ex;
				//e.printStackTrace();
			}
		}
		if (AbstractDungeon.player.hasRelic(DeadBranch.ID)) {
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
		for (AbstractRelic r : AbstractDungeon.player.relics) {
			if (r instanceof BottledFlame || r instanceof BottledLightning || r instanceof BottledTornado || r instanceof CustomBottleRelic) {
				boolean isEmpty = true;
				if (r instanceof CustomBottleRelic) {
					CustomBottleRelic cbr = (CustomBottleRelic)r;
					for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
						if (cbr.isOnCard().test(c)) {
							isEmpty = false;
							continue;
						}
					}
				} else if (r instanceof BottledFlame) {
					for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
						if (c.inBottleFlame) {
							isEmpty = false;
							continue;
						}
					}
				} else if (r instanceof BottledLightning) {
					for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
						if (c.inBottleLightning) {
							isEmpty = false;
							continue;
						}
					}
				} else if (r instanceof BottledTornado) {
					for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
						if (c.inBottleTornado) {
							isEmpty = false;
							continue;
						}
					}
				} else if (r instanceof Baseball) {
					for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
						if (c == ((Baseball)r).card) {
							isEmpty = false;
							continue;
						}
					}
				}
				if (isEmpty || r instanceof Baseball) {
					final CampfireUI campfire = (CampfireUI)meObj;
					try {
						final ArrayList<AbstractCampfireOption> campfireButtons = (ArrayList<AbstractCampfireOption>)ReflectionHacks.getPrivate((Object)campfire, (Class)CampfireUI.class, "buttons");
						final ReBottleOption button = new ReBottleOption(r, isEmpty);
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
    }
}