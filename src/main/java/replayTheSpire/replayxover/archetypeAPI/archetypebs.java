package replayTheSpire.replayxover.archetypeAPI;

import com.evacipated.cardcrawl.modthespire.Loader;

import archetypeAPI.archetypes.abstractArchetype;
import archetypeAPI.archetypes.theDefect.ClawLowCostDefect;
import archetypeAPI.archetypes.theDefect.DarkDefect;
import archetypeAPI.archetypes.theDefect.FrostDefect;
import archetypeAPI.archetypes.theDefect.OrbSupportDefect;
import archetypeAPI.archetypes.theDefect.PowerDefect;
import archetypeAPI.archetypes.theIronclad.BasicIronclad;
import archetypeAPI.archetypes.theIronclad.BlockIronclad;
import archetypeAPI.archetypes.theIronclad.ExhaustIronclad;
import archetypeAPI.archetypes.theIronclad.SelfDamageIronclad;
import archetypeAPI.archetypes.theIronclad.StrengthIronclad;
import archetypeAPI.archetypes.theIronclad.StrikeIronclad;
import archetypeAPI.archetypes.theSilent.BasicSilent;
import archetypeAPI.archetypes.theSilent.BlockSilent;
import archetypeAPI.archetypes.theSilent.DiscardSilent;
import archetypeAPI.archetypes.theSilent.PoisonSilent;
import archetypeAPI.archetypes.theSilent.ShivSilent;

public class archetypebs {
	public static void postInit() {
		if (Loader.isModLoaded("archetypeapi")) {

			NecroSet.necroticArchetypeFiles.add("APIJsons/Replay/NecroSet.json");
            abstractArchetype.silentArchetypeSelectCards.addToTop(new NecroticSelectCard().makeCopy());
			SilentWeakSet.weakArchetypeFiles.add("APIJsons/Replay/SilentWeak.json");
            abstractArchetype.silentArchetypeSelectCards.addToTop(new SilentWeakSelectCard().makeCopy());
			ThiccSet.thiccArchetypeFiles.add("APIJsons/Replay/DefectThicc.json");
            abstractArchetype.defectArchetypeSelectCards.addToTop(new ThiccSelectCard().makeCopy());
            
            PoisonSilent.poisonSilentArchetypeFiles.add("APIJsons/Replay/SilentPoison.json");
            DiscardSilent.discardSilentArchetypeFiles.add("APIJsons/Replay/SilentDiscard.json");
            BlockSilent.blockSilentArchetypeFiles.add("APIJsons/Replay/SilentBlock.json");
            ShivSilent.shivSilentArchetypeFiles.add("APIJsons/Replay/SilentShivs.json");
            BasicSilent.basicSilentArchetypeFiles.add("APIJsons/Replay/SilentBasic.json");
            
            BlockIronclad.blockIroncladArchetypeFiles.add("APIJsons/Replay/IronBlock.json");
            ExhaustIronclad.exhaustIroncladArchetypeFiles.add("APIJsons/Replay/IronExhaust.json");
            SelfDamageIronclad.selfDamageIroncladArchetypeFiles.add("APIJsons/Replay/IronBleed.json");
            StrengthIronclad.strengthIroncladArchetypeFiles.add("APIJsons/Replay/IronStrength.json");
            StrikeIronclad.strikeIroncladArchetypeFiles.add("APIJsons/Replay/IronStrike.json");
            BasicIronclad.basicIroncladArchetypeFiles.add("APIJsons/Replay/IronBasic.json");
            
            ClawLowCostDefect.clawLowCostDefectDefectArchetypeFiles.add("APIJsons/Replay/DefectClaw.json");
            DarkDefect.darkDefectArchetypeFiles.add("APIJsons/Replay/DefectDark.json");
            FrostDefect.frostDefectArchetypeFiles.add("APIJsons/Replay/DefectFrost.json");
            OrbSupportDefect.orbSupporDefectArchetypeFiles.add("APIJsons/Replay/DefectOrb.json");
            PowerDefect.powerDefectArchetypeFiles.add("APIJsons/Replay/DefectPower.json");
		}
	}
}
