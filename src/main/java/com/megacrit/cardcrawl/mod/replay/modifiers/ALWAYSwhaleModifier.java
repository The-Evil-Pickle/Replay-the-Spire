package com.megacrit.cardcrawl.mod.replay.modifiers;

import java.util.ArrayList;
import java.util.List;

import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.daily.mods.AbstractDailyMod;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.RunModStrings;
import com.megacrit.cardcrawl.map.MapGenerator;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.mod.replay.relics.GrabBag;
import com.megacrit.cardcrawl.mod.replay.relics.Kintsugi;
import com.megacrit.cardcrawl.mod.replay.rooms.FakeNeowRoom;
import com.megacrit.cardcrawl.neow.NeowEvent;
import com.megacrit.cardcrawl.neow.NeowRoom;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Astrolabe;
import com.megacrit.cardcrawl.relics.CallingBell;
import com.megacrit.cardcrawl.relics.NeowsLament;
import com.megacrit.cardcrawl.relics.Orrery;
import com.megacrit.cardcrawl.relics.PandorasBox;
import com.megacrit.cardcrawl.relics.TinyHouse;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.EventRoom;

import javassist.CtBehavior;

//this was made by Reina, all credit to her
public class ALWAYSwhaleModifier extends AbstractDailyMod
{
    public static final String ID = "replay:ALWAYS";
    private static final RunModStrings modStrings = CardCrawlGame.languagePack.getRunModString(ID);
    public static final String NAME = modStrings.NAME;
    public static final String DESC = modStrings.DESCRIPTION;

    public ALWAYSwhaleModifier() {
        super(ID, NAME, DESC, null, true);
        this.img = ImageMaster.loadImage("images/relics/cursedBlood.png");
    }
    
	@SpirePatch(clz = NeowEvent.class, method = "blessing")
	public static class WhyIsThisDoneLikeThis {
	    @SpireInsertPatch(rloc = 3)
	    public static void FUCK(NeowEvent __instance) {
	    	if (CardCrawlGame.trial != null && CardCrawlGame.trial.dailyModIDs().contains(ALWAYSwhaleModifier.ID)) {
	    		NeowEvent.rng = new Random(Settings.seed + AbstractDungeon.floorNum);
	    	}
	    }
	}

	@SpirePatch(clz=AbstractDungeon.class, method="generateMap")
	public static class ALWAYS {
	    @SpireInsertPatch(locator = Locator.class)
	    public static void ALWAYSNeowRoom() {
	    	if (CardCrawlGame.trial != null && CardCrawlGame.trial.dailyModIDs().contains(ALWAYSwhaleModifier.ID)) {
		        for (List<MapRoomNode> rows : AbstractDungeon.map) {
		            for (MapRoomNode node : rows) {
		                if (node.room instanceof EventRoom && AbstractDungeon.mapRng.randomBoolean()) {
		                    node.setRoom(new FakeNeowRoom());
		                }
		            }
		        }
	    	}
	    }
	
	    private static class Locator extends SpireInsertLocator {
	        @Override
	        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
	            Matcher finalMatcher = new Matcher.MethodCallMatcher(MapGenerator.class, "toString");
	            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
	        }
	    }


	}
	
	@SpirePatch(clz=NeowEvent.class, method="shouldSkipNeowDialog")
	public static class DontSkipPls {
		public static boolean Postfix(boolean _result, NeowEvent _instance) {
			if (CardCrawlGame.trial != null && CardCrawlGame.trial.dailyModIDs().contains(ALWAYSwhaleModifier.ID) && AbstractDungeon.floorNum > 1) {
				return false;
			}
			return _result;
		}
	}
	

	@SpirePatch(
	        clz= AbstractDungeon.class,
	        method="returnRandomRelicKey"
	)
	public static class banishTheseRelics {
	    private static List<String> relicBlacklist = new ArrayList<>();
	
	    static {
	        relicBlacklist.add(CallingBell.ID);
	        relicBlacklist.add(Orrery.ID);
	        relicBlacklist.add(TinyHouse.ID);
	        relicBlacklist.add(PandorasBox.ID);
	        relicBlacklist.add(Astrolabe.ID);
	        relicBlacklist.add(Kintsugi.ID);
	    }
	
	    public static String Postfix(String __result, AbstractRelic.RelicTier tier) {
	        if (AbstractDungeon.currMapNode != null) {
	            AbstractRoom room = AbstractDungeon.getCurrRoom();
	            if (room != null) {
	                if (room instanceof FakeNeowRoom) {
	                    if (relicBlacklist.contains(__result)) {
	                        return AbstractDungeon.returnRandomRelicKey(tier);
	                    }
	                }
	            }
	        }
	
	        return __result;
	    }
	}
	
	@SpirePatch(
	        clz = NeowsLament.class,
	        method = "atBattleStart"
	)
	public static class stackTheLaments {
	    private static ArrayList<AbstractRelic> laments = new ArrayList<>();
	    private static AbstractRelic fuckukio = null;
	    public static SpireReturn Prefix(NeowsLament __instance) {
	    	if (CardCrawlGame.trial != null && CardCrawlGame.trial.dailyModIDs().contains(ALWAYSwhaleModifier.ID)) {
		        for (AbstractRelic r : AbstractDungeon.player.relics) {
		            if (r.relicId.equals(NeowsLament.ID)) {
		                laments.add(r);
		            }
		        }
		        for (AbstractRelic r : laments) {
		            if (fuckukio == null) {
		                fuckukio = r;
		            }
		            if (fuckukio.counter < 0 && r.counter > 0) {
		                fuckukio = r;
		            }
		            if (r.counter < fuckukio.counter && r.counter > 0) {
		                fuckukio = r;
		            }
		        }
		        if (__instance == fuckukio) {
		            return SpireReturn.Continue();
		        } else {
		            return SpireReturn.Return(null);
		        }
		    }
	    	return SpireReturn.Continue();
	    }
	}
}
