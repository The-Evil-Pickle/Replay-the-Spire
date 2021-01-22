package com.megacrit.cardcrawl.mod.replay.events.thebottom;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.blue.AutoShields;
import com.megacrit.cardcrawl.cards.blue.BeamCell;
import com.megacrit.cardcrawl.cards.blue.Chaos;
import com.megacrit.cardcrawl.cards.blue.Dualcast;
import com.megacrit.cardcrawl.cards.blue.Leap;
import com.megacrit.cardcrawl.cards.blue.Reboot;
import com.megacrit.cardcrawl.cards.blue.Rebound;
import com.megacrit.cardcrawl.cards.blue.Recycle;
import com.megacrit.cardcrawl.cards.blue.Reprogram;
import com.megacrit.cardcrawl.cards.blue.Scrape;
import com.megacrit.cardcrawl.cards.blue.SelfRepair;
import com.megacrit.cardcrawl.cards.blue.SteamBarrier;
import com.megacrit.cardcrawl.cards.blue.WhiteNoise;
import com.megacrit.cardcrawl.cards.blue.Zap;
import com.megacrit.cardcrawl.cards.green.AfterImage;
import com.megacrit.cardcrawl.cards.green.Alchemize;
import com.megacrit.cardcrawl.cards.green.DeadlyPoison;
import com.megacrit.cardcrawl.cards.green.EndlessAgony;
import com.megacrit.cardcrawl.cards.green.Footwork;
import com.megacrit.cardcrawl.cards.green.InfiniteBlades;
import com.megacrit.cardcrawl.cards.green.Neutralize;
import com.megacrit.cardcrawl.cards.green.Survivor;
import com.megacrit.cardcrawl.cards.green.WellLaidPlans;
import com.megacrit.cardcrawl.cards.purple.Defend_Watcher;
import com.megacrit.cardcrawl.cards.purple.Eruption;
import com.megacrit.cardcrawl.cards.purple.ForeignInfluence;
import com.megacrit.cardcrawl.cards.purple.Strike_Purple;
import com.megacrit.cardcrawl.cards.purple.Vigilance;
import com.megacrit.cardcrawl.cards.red.Armaments;
import com.megacrit.cardcrawl.cards.red.Bash;
import com.megacrit.cardcrawl.cards.red.Brutality;
import com.megacrit.cardcrawl.cards.red.DemonForm;
import com.megacrit.cardcrawl.cards.red.Hemokinesis;
import com.megacrit.cardcrawl.cards.red.Inflame;
import com.megacrit.cardcrawl.cards.red.RecklessCharge;
import com.megacrit.cardcrawl.cards.red.SearingBlow;
import com.megacrit.cardcrawl.cards.red.SpotWeakness;
import com.megacrit.cardcrawl.cards.red.TwinStrike;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.cards.blue.*;
import com.megacrit.cardcrawl.mod.replay.cards.curses.*;
import com.megacrit.cardcrawl.mod.replay.cards.green.*;
import com.megacrit.cardcrawl.mod.replay.cards.red.*;
import com.megacrit.cardcrawl.mod.replay.cards.replayxover.ArmamentsMkIIB;
import com.megacrit.cardcrawl.mod.replay.cards.replayxover.downfall.M_BronzeAgony;
import com.megacrit.cardcrawl.mod.replay.cards.replayxover.downfall.M_BronzeBash;
import com.megacrit.cardcrawl.mod.replay.cards.replayxover.downfall.M_BronzeSurvivor;
import com.megacrit.cardcrawl.mod.replay.cards.replayxover.guardian.GuardianBash;
import com.megacrit.cardcrawl.mod.replay.cards.replayxover.guardian.GuardianCast;
import com.megacrit.cardcrawl.mod.replay.cards.replayxover.marisa.LightBash;
import com.megacrit.cardcrawl.mod.replay.events.*;
import com.megacrit.cardcrawl.mod.replay.modifiers.MistsModifier;
import com.megacrit.cardcrawl.mod.replay.relics.*;
import com.megacrit.cardcrawl.mod.replay.rooms.*;
import com.megacrit.cardcrawl.mod.replay.vfx.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.BurningBlood;
import com.megacrit.cardcrawl.relics.CrackedCore;
import com.megacrit.cardcrawl.relics.PureWater;
import com.megacrit.cardcrawl.relics.SnakeRing;
import com.megacrit.cardcrawl.screens.select.*;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.cardManip.*;

import ThMod.characters.Marisa;
import automaton.AutomatonChar;
import automaton.cards.Goto;
import automaton.cards.Replicate;
import beaked.characters.BeakedTheCultist;
import blackbeard.characters.TheBlackbeard;
import blackrusemod.cards.Exchange;
import blackrusemod.cards.KidneyShot;
import blackrusemod.characters.*;
import champ.ChampChar;
import champ.cards.Execute;
import champ.cards.Taunt;
import champ.relics.ChampionCrown;
import chronomuncher.cards.PatternShift;
import chronomuncher.cards.SecondHand;
import chronomuncher.character.*;
import constructmod.cards.AttackMode;
import constructmod.cards.DefenseMode;
import constructmod.characters.TheConstruct;
import constructmod.relics.ClockworkPhoenix;
import constructmod.relics.Cogwheel;
import gluttonmod.characters.GluttonCharacter;
import guardian.cards.CurlUp;
import guardian.cards.TwinSlam;
import guardian.characters.GuardianCharacter;
import mysticmod.character.*;

import com.megacrit.cardcrawl.helpers.*;

import replayTheSpire.*;
import runesmith.character.player.RunesmithCharacter;
import slimebound.characters.SlimeboundCharacter;
import sneckomod.TheSnecko;
import theHexaghost.TheHexaghost;
import theHexaghost.cards.Float;
import theHexaghost.relics.SpiritBrand;
import the_gatherer.character.TheGatherer;

import com.megacrit.cardcrawl.unlock.*;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.bard.cards.Inspire;
import com.evacipated.cardcrawl.mod.bard.cards.Riposte;
import com.evacipated.cardcrawl.mod.bard.characters.Bard;
import com.evacipated.cardcrawl.mod.bard.relics.PitchPipe;

import java.util.ArrayList;

public class MirrorMist
  extends AbstractImageEvent
{
  public static final String ID = "Mirror Mist";
  private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString("Mirror Mist");
  public static final String NAME = eventStrings.NAME;
  public static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
  public static final String[] OPTIONS = eventStrings.OPTIONS;
  private static final String DIALOG_1 = DESCRIPTIONS[0];
  private static final String RESULT_DIALOG_A = DESCRIPTIONS[1];
  private static final String RESULT_DIALOG_B = DESCRIPTIONS[2];
  private CurScreen screen = CurScreen.INTRO;
  
  private AbstractCard loss_c_1;
  private AbstractCard loss_c_1b;
  private AbstractCard loss_c_2;
  private AbstractCard loss_c_2b;
  private AbstractCard loss_c_3;
  private AbstractCard loss_c_3b;
  private AbstractCard gain_c_1;
  private AbstractCard gain_c_1b;
  private AbstractCard gain_c_2;
  private AbstractCard gain_c_2b;
  private AbstractCard gain_c_3;
  private AbstractCard gain_c_3b;
  private AbstractRelic loss_r_1;
  private AbstractRelic loss_r_1b;
  private AbstractRelic loss_r_2;
  private AbstractRelic loss_r_2b;
  private AbstractRelic loss_r_3;
  private AbstractRelic loss_r_3b;
  private AbstractRelic gain_r_1;
  private AbstractRelic gain_r_1b;
  private AbstractRelic gain_r_2;
  private AbstractRelic gain_r_2b;
  private AbstractRelic gain_r_3;
  private AbstractRelic gain_r_3b;
  private boolean has_1;
  private boolean has_1b;
  private boolean has_2;
  private boolean has_2b;
  private boolean has_3;
  private boolean has_3b;
  private boolean moddedguy;
  private boolean thirdOption;
  
  private int goldgain;
  private int searchcursechance;
  private int searchcursechance2;
  private int searchrelicchance2;
  private AbstractCard searchCurse;
  private AbstractCard searchCurse2;
  
  private static enum CurScreen
  {
    INTRO,  RESULT;
    
    private CurScreen() {}
  }
  
  private static boolean hasUpgradedCard(final String targetID) {
	  boolean r = false;
        for (final AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.cardID.equals(targetID)) {
                if (c.upgraded) {
					r = true;
				} else {
					return false;
				}
            }
        }
        return r;
    }
  
  private void checkForUpgrades() {
	  if (this.has_1) {
		  if (this.loss_c_1 != null && MirrorMist.hasUpgradedCard(this.loss_c_1.cardID)) {
			  this.loss_c_1.upgrade();
			  if (this.gain_c_1 != null) {
				  this.gain_c_1.upgrade();
			  }
			  if (this.loss_c_1b == null && this.loss_r_1b == null && this.gain_c_1b != null) {
				  this.gain_c_1b.upgrade();
			  }
		  }
	  }
	  if (this.has_1b) {
		  if (this.loss_c_1b != null && MirrorMist.hasUpgradedCard(this.loss_c_1b.cardID)) {
			   this.loss_c_1b.upgrade();
			  if (this.gain_c_1b != null) {
				  this.gain_c_1b.upgrade();
			  }
		  }
	  }
	  if (this.has_2) {
		  if (this.loss_c_2 != null && MirrorMist.hasUpgradedCard(this.loss_c_2.cardID)) {
			   this.loss_c_2.upgrade();
			  if (this.gain_c_2 != null) {
				  this.gain_c_2.upgrade();
			  }
			  if (this.loss_c_2b == null && this.loss_r_2b == null && this.gain_c_2b != null) {
				  this.gain_c_2b.upgrade();
			  }
		  }
	  }
	  if (this.has_2b) {
		  if (this.loss_c_2b != null && MirrorMist.hasUpgradedCard(this.loss_c_2b.cardID)) {
			   this.loss_c_2b.upgrade();
			  if (this.gain_c_2b != null) {
				  this.gain_c_2b.upgrade();
			  }
		  }
	  }
	  if (this.has_3) {
		  if (this.loss_c_3 != null && MirrorMist.hasUpgradedCard(this.loss_c_3.cardID)) {
			   this.loss_c_3.upgrade();
			  if (this.gain_c_3 != null) {
				  this.gain_c_3.upgrade();
			  }
			  if (this.loss_c_3b == null && this.loss_r_3b == null && this.gain_c_3b != null) {
				  this.gain_c_3b.upgrade();
			  }
		  }
	  }
	  if (this.has_3b) {
		  if (this.loss_c_3b != null && MirrorMist.hasUpgradedCard(this.loss_c_3b.cardID)) {
			   this.loss_c_3b.upgrade();
			  if (this.gain_c_3b != null) {
				  this.gain_c_3b.upgrade();
			  }
		  }
	  }
  }
  
  public MirrorMist()
  {
    super(NAME, DIALOG_1, "images/events/livingWall.jpg");
    
	this.goldgain = 80;
	this.searchcursechance = 50;
	this.searchcursechance2 = 75;
	this.searchrelicchance2 = 75;
	this.searchCurse = new Delirium();
	this.searchCurse2 = new Amnesia();
	
	this.has_1 = false;
	this.has_1b = false;
	this.has_2 = false;
	this.has_2b = false;
	this.has_3 = false;
	this.has_3b = false;
	this.moddedguy = false;
	this.thirdOption = false;
	MistsModifier.hasGottenEvent = true;
	
	switch(AbstractDungeon.player.chosenClass) {
		case IRONCLAD: {
			this.has_1 = CardHelper.hasCardWithID("Bash");
			this.has_1b = CardHelper.hasCardWithID("Bash");
			this.has_2 = ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic("Burning Blood");
			this.has_2b = ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic("Burning Blood");
			this.has_3 = AbstractDungeon.player.hasRelic(BurningBlood.ID);
			this.has_3b = CardHelper.hasCardWithID("Bash");
			this.loss_c_1 = new Bash();
			this.gain_c_1 = new Survivor();
			this.gain_c_1b = new Neutralize();
			this.loss_r_2 = new BurningBlood();
			this.gain_c_2b = new SelfRepair();
			this.gain_c_2b.upgrade();
			this.gain_r_2 = new IronCore();
			this.loss_r_3 = new BurningBlood();
			this.gain_r_3 = new M_SmolderingBlood();
			this.loss_c_3b = new Bash();
			this.gain_c_3b = new Eruption();
			this.thirdOption = true;
			break;
		}
		case THE_SILENT: {
			this.has_1 = ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic("Ring of the Snake");
			this.has_2 = CardHelper.hasCardWithID("Survivor");
			this.has_2b = CardHelper.hasCardWithID("Neutralize");
			this.has_3 = AbstractDungeon.player.hasRelic("Ring Of The Snake");
			this.has_3b = CardHelper.hasCardWithID("Survivor");
			this.loss_r_1 = new SnakeRing();
			this.gain_r_1 = new SizzlingBlood();
			this.loss_c_2 = new Survivor();
			this.loss_c_2b = new Neutralize();
			this.gain_c_2 = new Leap();
			this.gain_c_2b = new BeamCell();
			this.loss_r_3 = new SnakeRing();
			this.gain_r_3 = new M_StaffOfTheSnake();
			this.loss_c_3b = new Survivor();
			this.gain_c_3b = new Survivalism();
			this.thirdOption = true;
			break;
		}
		case DEFECT: {
			this.has_1 = CardHelper.hasCardWithID("Dualcast");
			this.has_1b = ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic("Cracked Core");
			this.has_2b = this.has_1b;
			this.has_2 = CardHelper.hasCardWithID("Zap");
			this.has_3 = this.has_1;
			this.has_3b = this.has_2;
			this.loss_c_1 = new Dualcast();
			this.gain_c_1 = new Bash();
			this.loss_r_1b = new CrackedCore();
			this.gain_r_1b = new IronCore();
			this.loss_r_2b = new CrackedCore();
			this.gain_r_2b = new SnakeRing();
			this.loss_c_2 = new Zap();
			this.gain_c_2 = new DeadlyPoison();
			this.loss_c_3 = new Dualcast();
			this.loss_c_3b = new Zap();
			this.gain_c_3 = new Vigilance();
			this.gain_c_3b = new Eruption();
			this.thirdOption = true;
			break;
		}
		case WATCHER: {
			this.has_1 = AbstractDungeon.player.hasRelic(PureWater.ID);
			this.has_1b = CardHelper.hasCardWithID(Vigilance.ID);
			this.has_2 = this.has_1;
			this.has_3 = CardHelper.hasCardWithID(Strike_Purple.ID);
			this.has_3b = CardHelper.hasCardWithID(Defend_Watcher.ID);
			this.loss_r_1 = new PureWater();
			this.gain_r_1 = new M_SmolderingBlood();
			this.loss_c_1b = new Vigilance();
			this.gain_c_1b = new Bash();
			this.loss_r_2 = new PureWater();
			this.gain_r_2 = new M_StaffOfTheSnake();
			this.loss_c_3 = new Strike_Purple();
			this.gain_c_3 = new ForeignInfluence();
			this.loss_c_3b = new Defend_Watcher();
			this.gain_c_3b = new SteamBarrier();
			this.thirdOption = true;
			break;
		}
		default: {
			if (ReplayTheSpireMod.foundmod_science && (ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic("FuelTank") || CardHelper.hasCardWithID("HideBehindJunk") || CardHelper.hasCardWithID("PoweredStrike") || CardHelper.hasCardWithID("Defend_Bronze") || CardHelper.hasCardWithID("Strike_Bronze"))) {
				//MAD SCIENTIST
				this.has_1 = ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic("FuelTank");
				this.has_1b = this.has_1 && CardHelper.hasCardWithID("PoweredStrike");
				if (this.has_1) {
					this.loss_r_1 = ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_getRelic("FuelTank");
					if (ReplayTheSpireMod.foundmod_science) {
						this.gain_r_1 = new ChemicalBlood();
					} else {
						this.gain_r_1 = new SizzlingBlood();
					}
					if (this.has_1b) {
						for (final AbstractCard c : AbstractDungeon.player.masterDeck.group) {
							if (c.cardID.equals("PoweredStrike")) {
								this.loss_c_1b = c.makeCopy();
							}
						}
						this.gain_c_1b = new Hemokinesis();
					}
				}
				this.has_2 = CardHelper.hasCardWithID("HideBehindJunk");
				if (this.has_2) {
					for (final AbstractCard c : AbstractDungeon.player.masterDeck.group) {
						if (c.cardID.equals("HideBehindJunk")) {
							this.loss_c_2 = c.makeCopy();
						}
					}
					this.gain_c_2 = new Survivor();
				}
				this.has_3 = CardHelper.hasCardWithID("PoweredStrike");
				if (this.has_3) {
					for (final AbstractCard c : AbstractDungeon.player.masterDeck.group) {
						if (c.cardID.equals("PoweredStrike")) {
							this.loss_c_3 = c.makeCopy();
						}
					}
					this.gain_c_3 = new Scrape();
				}
				this.thirdOption = true;
				break;
			}
			if (ReplayTheSpireMod.foundmod_servant && AbstractDungeon.player instanceof TheServant) {
				this.has_1 = ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic("Uniform");
				this.has_1b = this.has_1;
				this.loss_r_1 = ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_getRelic("Uniform");
				this.gain_r_1 = new m_ScarletBlood();
				this.gain_c_1b = new Inflame();
				this.has_2 = ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic("Uniform");
				this.has_2b = CardHelper.hasCardWithID(KidneyShot.ID);
				this.loss_r_2 = ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_getRelic("Uniform");
				this.gain_r_2 = new m_SnakeCloak();
				this.loss_c_2b = CardLibrary.getCopy(KidneyShot.ID);
				this.gain_c_2b = new Survivor();
				this.has_3 = CardHelper.hasCardWithID(Exchange.ID);
				this.loss_c_3 = CardLibrary.getCopy(Exchange.ID);
				this.gain_c_3 = new Reprogram();
				this.has_3b = CardHelper.hasCardWithID(KidneyShot.ID);
				this.loss_c_3b = CardLibrary.getCopy(KidneyShot.ID);
				this.gain_c_3b = new Reboot();
				this.thirdOption = true;
				break;
			}
			if (ReplayTheSpireMod.foundmod_mystic && AbstractDungeon.player instanceof MysticCharacter) {
				this.has_1 = CardHelper.hasCardWithID("mysticmod:ShockingGrasp");
				this.has_1b = CardHelper.hasCardWithID("mysticmod:ArcaneDodge");
				this.loss_c_1 = CardLibrary.getCopy("mysticmod:ShockingGrasp");
				this.gain_c_1 = new Bash();
				this.loss_c_1b = CardLibrary.getCopy("mysticmod:ArcaneDodge");
				this.gain_c_1b = new Leap();
				this.has_2 = AbstractDungeon.player.hasRelic("mysticmod:SpellBook");
				this.loss_r_2 = RelicLibrary.getRelic("mysticmod:SpellBook").makeCopy();
				this.gain_r_2 = new m_BookOfShivs();
				break;
			}
			if (ReplayTheSpireMod.foundmod_deciple && AbstractDungeon.player instanceof Chronomuncher) {
				if (ReplayTheSpireMod.foundmod_hubris) {
					this.has_1 = CardHelper.hasCardWithID(PatternShift.ID);
					this.loss_c_1 = CardLibrary.getCopy(PatternShift.ID);
					this.gain_c_1 = CardLibrary.getCopy("hubris:StunningStrike");
					//this.has_1b = CardHelper.hasCardWithID(SecondHand.ID);
					//this.loss_c_1b = CardLibrary.getCopy(SecondHand.ID);
					//this.gain_c_1b = CardLibrary.getCopy("hubris:ReadiedAction");
				} else {
					this.has_1 = AbstractDungeon.player.hasRelic("Chronometer");
					this.has_1b = CardHelper.hasCardWithID(SecondHand.ID);
					this.loss_r_1 = RelicLibrary.getRelic("Chronometer").makeCopy();
					this.gain_r_1 = new BurningBlood();
					this.loss_c_1b = CardLibrary.getCopy(SecondHand.ID);
					this.gain_c_1b = new SpotWeakness();
				}
				this.has_2 = CardHelper.hasCardWithID(PatternShift.ID);
				this.has_2b = CardHelper.hasCardWithID(SecondHand.ID);
				this.loss_c_2 = CardLibrary.getCopy(PatternShift.ID);
				this.loss_c_2b = CardLibrary.getCopy(SecondHand.ID);
				this.gain_c_2 = new InfiniteBlades();
				this.gain_c_2b = new WellLaidPlans();
				this.has_3 = AbstractDungeon.player.hasRelic("Chronometer");
				this.has_3b = CardHelper.hasCardWithID(SecondHand.ID);
				this.loss_r_3 = RelicLibrary.getRelic("Chronometer").makeCopy();
				this.gain_r_3 = new m_MercuryCore();
				this.loss_c_3b = CardLibrary.getCopy(SecondHand.ID);
				this.gain_c_3b = new WhiteNoise();
				this.thirdOption = true;
				break;
			}
			if (ReplayTheSpireMod.foundmod_gatherer && AbstractDungeon.player instanceof TheGatherer) {
				this.has_1 = AbstractDungeon.player.hasRelic("Gatherer:AlchemyBag");
				this.has_1b = CardHelper.hasCardWithID("Gatherer:SpareBottle");
				this.loss_r_1 = RelicLibrary.getRelic("Gatherer:AlchemyBag").makeCopy();
				this.gain_c_1 = new SearingBlow();
				this.loss_c_1b = CardLibrary.getCopy("Gatherer:SpareBottle");
				this.gain_c_1b = new Armaments();
				this.has_2 = CardHelper.hasCardWithID("Gatherer:FlowerWhip");
				this.loss_c_2 = CardLibrary.getCopy("Gatherer:FlowerWhip");
				this.gain_c_2 = new Neutralize();
				this.has_2b = CardHelper.hasCardWithID("Gatherer:Centralize");
				this.loss_c_2b = CardLibrary.getCopy("Gatherer:Centralize");
				this.gain_c_2b = new Alchemize();
				break;
			}
			if (ReplayTheSpireMod.foundmod_blackbeard && AbstractDungeon.player instanceof TheBlackbeard) {
				this.has_1 = AbstractDungeon.player.hasRelic("blackbeard:LoadTheCannons");
				this.has_1b = CardHelper.hasCardWithID("blackbeard:Cutlass");
				this.loss_r_1 = RelicLibrary.getRelic("blackbeard:LoadTheCannons").makeCopy();
				this.gain_r_1 = new M_SeaBlood();
				this.loss_c_1b = CardLibrary.getCopy("blackbeard:Cutlass");
				this.gain_c_1b = new Bash();
				this.has_2 = AbstractDungeon.player.hasRelic("blackbeard:LoadTheCannons");
				this.has_2b = CardHelper.hasCardWithID("blackbeard:Cutlass");
				this.loss_r_2 = RelicLibrary.getRelic("blackbeard:LoadTheCannons").makeCopy();
				this.gain_r_2 = new M_SerpentRing();
				this.loss_c_2b = CardLibrary.getCopy("blackbeard:Cutlass");
				this.gain_c_2b = new Neutralize();
				break;
			}
			if (ReplayTheSpireMod.foundmod_beaked && AbstractDungeon.player instanceof BeakedTheCultist) {
				int ceremonies = 0;
				for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
					if (c.cardID.equals("beaked:Ceremony")) {
						ceremonies++;
					}
				}
				if (ceremonies > 1 && !AbstractDungeon.player.hasRelic("beaked:MendingPlumage")) {
					this.has_1 = true;
					this.has_1b = true;
					this.loss_c_1 = CardLibrary.getCopy("beaked:Ceremony");
					this.loss_c_1b = CardLibrary.getCopy("beaked:Ceremony");
					this.gain_c_1 = new DemonForm();
				} else if (CardHelper.hasCardWithID("beaked:CrazyRituals")) {
					this.has_1b = true;
					this.has_1 = AbstractDungeon.player.hasRelic("beaked:MendingPlumage");
					this.loss_c_1b = CardLibrary.getCopy("beaked:CrazyRituals");
					this.gain_c_1b = CardLibrary.getCopy("beaked:Ceremony");
					this.loss_r_1 = AbstractDungeon.player.getRelic("beaked:MendingPlumage");
					this.gain_r_1 = new M_ByrdBlood();
					this.has_3 = CardHelper.hasCardWithID("beaked:CrazyRituals");
					this.loss_c_3 = CardLibrary.getCopy("beaked:CrazyRituals");
				} else {
					this.has_1 = AbstractDungeon.player.hasRelic("beaked:MendingPlumage");
					this.has_1b = CardHelper.hasCardWithID("beaked:Ceremony");
					if (this.has_1) {
						this.loss_r_1 = AbstractDungeon.player.getRelic("beaked:MendingPlumage");
					}
					this.gain_r_1 = new M_ByrdBlood();
					this.loss_c_1b = CardLibrary.getCopy("beaked:Ceremony");
					this.gain_c_1b = new Inflame();
					this.has_3 = CardHelper.hasCardWithID("beaked:Ceremony");
					this.loss_c_3 = CardLibrary.getCopy("beaked:Ceremony");
				}
				this.has_2 = AbstractDungeon.player.hasRelic("beaked:MendingPlumage");
				this.has_2b = AbstractDungeon.player.hasRelic("beaked:MendingPlumage");
				this.loss_r_2 = RelicLibrary.getRelic("beaked:MendingPlumage").makeCopy();
				this.gain_r_2 = new SnakeRing();
				this.gain_c_2b = new AfterImage();
				this.gain_c_2b.upgrade();
				this.has_3b = this.has_3;
				this.gain_c_3 = new IC_ScorchingBeam();
				this.gain_c_3b = new SelfRepair();
				this.thirdOption = true;
				break;
			}
			if (ReplayTheSpireMod.foundmod_marisa && AbstractDungeon.player instanceof Marisa) {
				this.has_1 = CardHelper.hasCardWithID("UpSweep");
				this.loss_c_1 = CardLibrary.getCopy("UpSweep");
				this.gain_c_1 = new LightBash();
				this.has_2 = AbstractDungeon.player.hasRelic("MiniHakkero");
				this.has_2b = CardHelper.hasCardWithID("MasterSpark");
				this.loss_r_2 = RelicLibrary.getRelic("MiniHakkero").makeCopy();
				this.gain_r_2 = new M_TsuchinokoRing();
				this.loss_c_2b = CardLibrary.getCopy("MasterSpark");
				this.gain_c_2b = new Neutralize();
				this.has_3 = AbstractDungeon.player.hasRelic("MiniHakkero");
				this.loss_r_3 = RelicLibrary.getRelic("MiniHakkero").makeCopy();
				this.gain_r_3 = new M_SpellCore();
				this.thirdOption = true;
				break;
			}
			if (ReplayTheSpireMod.foundmod_construct && AbstractDungeon.player instanceof TheConstruct) {
				this.has_1 = AbstractDungeon.player.hasRelic(ClockworkPhoenix.ID);
				this.has_1b = CardHelper.hasCardWithID(AttackMode.ID);
				this.loss_r_1 = RelicLibrary.getRelic(ClockworkPhoenix.ID).makeCopy();
				this.gain_r_1 = new BurningBlood();
				this.loss_c_1b = CardLibrary.getCopy(AttackMode.ID);
				if (this.has_1) {
					this.gain_c_1b = CardLibrary.getCopy("ReplayTheSpireMod:Armaments MK-II");
				} else {
					this.gain_c_1b = new Inflame();
				}
				this.has_2 = CardHelper.hasCardWithID(DefenseMode.ID);
				this.loss_c_2 = CardLibrary.getCopy(DefenseMode.ID);
				this.gain_c_2 = new Footwork();
				if (AbstractDungeon.player.hasRelic(Cogwheel.ID) || !CardHelper.hasCardWithID(DefenseMode.ID) || !CardHelper.hasCardWithID(AttackMode.ID) || !ReplayTheSpireMod.foundmod_beaked) {
					this.has_3 = AbstractDungeon.player.hasRelic(Cogwheel.ID);
					this.loss_r_3 = RelicLibrary.getRelic(Cogwheel.ID).makeCopy();
					this.gain_r_3 = new AncientBracer();
				} else {
					this.has_3 = CardHelper.hasCardWithID(AttackMode.ID);
					this.has_3b = CardHelper.hasCardWithID(DefenseMode.ID);
					this.loss_c_3 = CardLibrary.getCopy(AttackMode.ID);
					this.loss_c_3b = CardLibrary.getCopy(DefenseMode.ID);
					this.gain_c_3 = new SystemScan();
					this.gain_c_3b = CardLibrary.getCopy("beaked:MimicMachine");
				}
				this.thirdOption = true;
				break;
			}
			if (ReplayTheSpireMod.foundmod_glutton && AbstractDungeon.player instanceof GluttonCharacter) {
				this.has_1 = AbstractDungeon.player.hasRelic("Glutton:EternalHunger");
				this.has_1b = this.has_1;
				this.loss_r_1 = RelicLibrary.getRelic("Glutton:EternalHunger").makeCopy();
				this.gain_r_1 = new M_IronSupplements();
				this.gain_c_1b = new Brutality();
				this.has_2 = CardHelper.hasCardWithID("Glutton:Strike");
				this.has_2b = CardHelper.hasCardWithID("Glutton:Flail");
				this.loss_c_2 = CardLibrary.getCopy("Glutton:Strike");
				this.loss_c_2b = CardLibrary.getCopy("Glutton:Flail");
				this.gain_c_2 = new Survivor();
				this.gain_c_2b = new Neutralize();
				this.has_3 = CardHelper.hasCardWithID("Glutton:Defend");
				this.has_3b = CardHelper.hasCardWithID("Glutton:Flail");
				this.loss_c_3 = CardLibrary.getCopy("Glutton:Defend");
				this.loss_c_3b = CardLibrary.getCopy("Glutton:Flail");
				this.gain_c_3 = new AutoShields();
				this.gain_c_3b = new Recycle();
				this.thirdOption = true;
				break;
			}
			if (ReplayTheSpireMod.foundmod_runesmith && AbstractDungeon.player instanceof RunesmithCharacter) {
				this.has_1 = AbstractDungeon.player.hasRelic("Runesmith:BrokenRuby");
				this.loss_r_1 = RelicLibrary.getRelic("Runesmith:BrokenRuby").makeCopy();
				this.gain_r_1 = new M_RuneBlood();
				this.has_1b = CardHelper.hasCardWithID("Runesmith:CraftFirestone");
				this.loss_c_1b = CardLibrary.getCopy("Runesmith:CraftFirestone");
				this.gain_c_1b = new ArmamentsMkIIB();
				this.has_2 = CardHelper.hasCardWithID("Runesmith:CraftFirestone");
				this.loss_c_2 = CardLibrary.getCopy("Runesmith:CraftFirestone");
				this.gain_c_2 = new Neutralize();
				this.has_2b = CardHelper.hasCardWithID("Runesmith:Fortify");
				this.loss_c_2b = CardLibrary.getCopy("Runesmith:Fortify");
				this.gain_c_2b = new Survivor();
				break;
			}
			if (ReplayTheSpireMod.foundmod_downfall) {
				if (AbstractDungeon.player instanceof SlimeboundCharacter) {
					this.has_1 = AbstractDungeon.player.hasRelic("Slimebound:AbsorbEndCombat");
					this.loss_r_1 = RelicLibrary.getRelic("Slimebound:AbsorbEndCombat").makeCopy();
					this.gain_r_1 = new M_BurningSludge();
					this.has_2 = AbstractDungeon.player.hasRelic("Slimebound:AbsorbEndCombat");
					this.loss_r_2 = RelicLibrary.getRelic("Slimebound:AbsorbEndCombat").makeCopy();
					this.gain_r_2 = new M_SlimeRing();
					/*this.has_2b = CardHelper.hasCardWithID("Slimebound:CorrosiveSpit");
					this.loss_c_2b = CardLibrary.getCopy("Slimebound:CorrosiveSpit");
					this.gain_c_2b = new DeadlyPoison();*/
					this.has_3 = CardHelper.hasCardWithID("Slimebound:Split");
					this.loss_c_3 = CardLibrary.getCopy("Slimebound:Split");
					this.gain_c_3 = new Chaos();
					this.thirdOption = true;
					break;
				}
				if (AbstractDungeon.player instanceof GuardianCharacter) {
					this.has_1 = AbstractDungeon.player.hasRelic("Guardian:ModeShifter");
					this.loss_r_1 = RelicLibrary.getRelic("Guardian:ModeShifter").makeCopy();
					this.gain_r_1 = new M_GuardianBlood();
					this.has_1b = CardHelper.hasCardWithID(CurlUp.ID);
					this.loss_c_1b = CardLibrary.getCopy(CurlUp.ID);
					this.gain_c_1b = CardLibrary.getCopy(GuardianBash.ID);
					this.has_2 = CardHelper.hasCardWithID(TwinSlam.ID);
					this.has_2b = this.has_2;
					this.loss_c_2 = CardLibrary.getCopy(TwinSlam.ID);
					this.gain_c_2 = new Survivor();
					this.gain_c_2b = new Neutralize();
					this.has_3 = AbstractDungeon.player.hasRelic("Guardian:ModeShifter");
					this.loss_r_3 = RelicLibrary.getRelic("Guardian:ModeShifter").makeCopy();
					this.gain_r_3 = new CrackedCore();
					this.has_3b = this.has_3;
					this.gain_c_3b = CardLibrary.getCopy(GuardianCast.ID);
					this.thirdOption = true;
					break;
				}
			}
			if (ReplayTheSpireMod.foundmod_bard && AbstractDungeon.player instanceof Bard) {
				this.has_1 = AbstractDungeon.player.hasRelic(PitchPipe.ID);
				this.loss_r_1 = RelicLibrary.getRelic(PitchPipe.ID).makeCopy();
				this.gain_r_1 = new M_CorellonBlood();
				this.has_1b = CardHelper.hasCardWithID(Inspire.ID);
				this.loss_c_1b = CardLibrary.getCopy(Inspire.ID);
				this.gain_c_1b = CardLibrary.getCopy(Bash.ID);
				this.has_2 = AbstractDungeon.player.hasRelic(PitchPipe.ID);
				this.loss_r_2 = RelicLibrary.getRelic(PitchPipe.ID).makeCopy();
				this.gain_r_2 = new M_ChordRing();
				this.has_2b = CardHelper.hasCardWithID(Riposte.ID);
				this.loss_c_2b = CardLibrary.getCopy(Riposte.ID);
				this.gain_c_2b = CardLibrary.getCopy(Neutralize.ID);
				this.has_3 = AbstractDungeon.player.hasRelic(PitchPipe.ID);
				this.loss_r_3 = RelicLibrary.getRelic(PitchPipe.ID).makeCopy();
				this.gain_r_3 = new M_MusicBoxCore();
				this.has_3b = CardHelper.hasCardWithID(Inspire.ID);
				this.loss_c_3b = CardLibrary.getCopy(Inspire.ID);
				this.gain_c_3b = CardLibrary.getCopy(Zap.ID);
				this.thirdOption = true;
				break;
			}
			if (ReplayTheSpireMod.foundmod_downfall && AbstractDungeon.player instanceof TheHexaghost) {
				this.has_1 = AbstractDungeon.player.hasRelic(SpiritBrand.ID);
				this.loss_r_1 = RelicLibrary.getRelic(SpiritBrand.ID).makeCopy();
				this.gain_r_1 = new M_DevilBlood();
				this.has_2 = AbstractDungeon.player.hasRelic(SpiritBrand.ID);
				this.loss_r_2 = RelicLibrary.getRelic(SpiritBrand.ID).makeCopy();
				this.gain_r_2 = new M_Hexaring();
				this.has_2b = CardHelper.hasCardWithID(Float.ID);
				this.loss_c_2b = CardLibrary.getCopy(Float.ID);
				this.gain_c_2b = CardLibrary.getCopy(Survivor.ID);
				break;
			}
			if (ReplayTheSpireMod.foundmod_downfall && AbstractDungeon.player instanceof ChampChar) {
				this.has_1 = AbstractDungeon.player.hasRelic(ChampionCrown.ID);
				this.loss_r_1 = RelicLibrary.getRelic(ChampionCrown.ID).makeCopy();
				this.gain_r_1 = new M_IronCrown();
				this.has_2 = CardHelper.hasCardWithID(Taunt.ID);
				this.loss_c_2 = CardLibrary.getCopy(Taunt.ID);
				this.gain_c_2 = CardLibrary.getCopy(Survivor.ID);
				this.has_2b = CardHelper.hasCardWithID(Execute.ID);
				this.loss_c_2b = CardLibrary.getCopy(Execute.ID);
				this.gain_c_2b = CardLibrary.getCopy(Neutralize.ID);
				this.has_3 = CardHelper.hasCardWithID(Taunt.ID);
				this.loss_c_3 = CardLibrary.getCopy(Taunt.ID);
				this.gain_c_3 = CardLibrary.getCopy(Vigilance.ID);
				this.has_3b = CardHelper.hasCardWithID(Execute.ID);
				this.loss_c_3b = CardLibrary.getCopy(Execute.ID);
				this.gain_c_3b = CardLibrary.getCopy(Eruption.ID);
				this.thirdOption = true;
				break;
			}
			if (ReplayTheSpireMod.foundmod_downfall && AbstractDungeon.player instanceof AutomatonChar) {
				this.has_1 = AbstractDungeon.player.hasRelic(automaton.relics.BronzeCore.ID);
				this.loss_r_1 = RelicLibrary.getRelic(automaton.relics.BronzeCore.ID).makeCopy();
				this.gain_r_1 = new M_BronzeBlood();
				this.has_1b = CardHelper.hasCardWithID(Goto.ID);
				this.loss_c_1b = CardLibrary.getCopy(Goto.ID);
				this.gain_c_1b = CardLibrary.getCopy(M_BronzeBash.ID);
				this.has_2 = CardHelper.hasCardWithID(Replicate.ID);
				this.loss_c_2 = CardLibrary.getCopy(Replicate.ID);
				this.gain_c_2 = CardLibrary.getCopy(M_BronzeAgony.ID);
				this.has_2b = CardHelper.hasCardWithID(Goto.ID);
				this.loss_c_2b = CardLibrary.getCopy(Goto.ID);
				this.gain_c_2b = CardLibrary.getCopy(M_BronzeSurvivor.ID);
				break;
			}
			
			this.moddedguy = true;
		}
	}
	
	if (this.moddedguy) {
		this.imageEventText.setDialogOption(OPTIONS[10] + this.goldgain + OPTIONS[11] + this.searchcursechance + OPTIONS[12] + this.searchCurse.name + ".", this.searchCurse);
		this.imageEventText.setDialogOption(OPTIONS[13] + this.searchCurse2.name + ".", this.searchCurse2);
		this.thirdOption = false;
		if (ReplayTheSpireMod.foundmod_snecko && AbstractDungeon.player instanceof TheSnecko) {
			this.searchrelicchance2 = 100;
			this.imageEventText.setDialogOption(OPTIONS[14] + this.searchrelicchance2 + OPTIONS[15] + this.searchcursechance2 + OPTIONS[12] + "(Random)" + ".");
			this.thirdOption = true;
		}
		else if (!(AbstractDungeon.player.hasRelic("hubris:DisguiseKit") && AbstractDungeon.player.hasRelic("PrismaticShard"))) {
			this.imageEventText.setDialogOption(OPTIONS[14] + this.searchrelicchance2 + OPTIONS[15] + this.searchcursechance2 + OPTIONS[12] + "(Random)" + ".");
			this.thirdOption = true;
		}
	} else {
		this.checkForUpgrades();
		String string_1 = "";
		String string_2 = "";
		String string_3 = "";
		AbstractCard prevcard_1 = null;
		AbstractCard prevcard_2 = null;
		AbstractCard prevcard_3 = null;
		AbstractRelic prevrelic_1 = null;
		AbstractRelic prevrelic_2 = null;
		AbstractRelic prevrelic_3 = null;
		if (this.has_1 || this.has_1b) {
			string_1 = OPTIONS[2];
			String obstring = OPTIONS[8];
			if (this.has_1) {
				if (this.loss_c_1 != null) {
					string_1 += this.loss_c_1.name;
				} else if (this.loss_r_1 != null) {
					string_1 += this.loss_r_1.name;
				}
				if (this.gain_c_1 != null) {
					obstring += FontHelper.colorString(this.gain_c_1.name, "g");
					prevcard_1 = this.gain_c_1;
				} else if (this.gain_r_1 != null) {
					obstring += FontHelper.colorString(this.gain_r_1.name, "g");
					prevrelic_1 = this.gain_r_1;
				}
			}
			if (this.has_1b) {
				if (this.has_1) {
					if (this.loss_c_1b != null || this.loss_r_1b != null) {
						string_1 += " and ";
					}
					obstring += " #gand ";
				}
				if (this.loss_c_1b != null) {
					string_1 += this.loss_c_1b.name;
				} else if (this.loss_r_1b != null) {
					string_1 += this.loss_r_1b.name;
				}
				if (this.gain_c_1b != null) {
					obstring += FontHelper.colorString(this.gain_c_1b.name, "g");
					if (prevcard_1 == null) {
						prevcard_1 = this.gain_c_1b;
					} else {
						prevcard_1 = null;
					}
				} else if (this.gain_r_1b != null) {
					obstring += FontHelper.colorString(this.gain_r_1b.name, "g");
				}
			}
			string_1 += obstring + ".";
		} else {
			string_1 = OPTIONS[0];
			if (this.loss_c_1 != null) {
				string_1 += this.loss_c_1.name;
			} else if (this.loss_r_1 != null) {
				string_1 += this.loss_r_1.name;
			}
			if (this.loss_c_1b != null) {
				string_1 += " or " + this.loss_c_1b.name;
			} else if (this.loss_r_1b != null) {
				string_1 += " or " + this.loss_r_1b.name;
			}
			string_1 += ".";
		}
		if (this.has_2 || this.has_2b) {
			string_2 = OPTIONS[2];
			String obstring = OPTIONS[8];
			if (this.has_2) {
				if (this.loss_c_2 != null) {
					string_2 += this.loss_c_2.name;
				} else if (this.loss_r_2 != null) {
					string_2 += this.loss_r_2.name;
				}
				if (this.gain_c_2 != null) {
					obstring += FontHelper.colorString(this.gain_c_2.name, "g");
					prevcard_2 = this.gain_c_2;
				} else if (this.gain_r_2 != null) {
					obstring += FontHelper.colorString(this.gain_r_2.name, "g");
					prevrelic_2 = this.gain_r_2;
				}
			}
			if (this.has_2b) {
				if (this.has_2) {
					if (this.loss_c_2b != null || this.loss_r_2b != null) {
						string_2 += " and ";
					}
					obstring += " #gand ";
				}
				if (this.loss_c_2b != null) {
					string_2 += this.loss_c_2b.name;
				} else if (this.loss_r_2b != null) {
					string_2 += this.loss_r_2b.name;
				}
				if (this.gain_c_2b != null) {
					obstring += FontHelper.colorString(this.gain_c_2b.name, "g");
					if (prevcard_2 == null) {
						prevcard_2 = this.gain_c_2b;
					} else {
						prevcard_2 = null;
					}
				} else if (this.gain_r_2b != null) {
					obstring += FontHelper.colorString(this.gain_r_2b.name, "g");
				}
			}
			string_2 += obstring + ".";
		} else {
			string_2 = OPTIONS[0];
			if (this.loss_c_2 != null) {
				string_2 += this.loss_c_2.name;
			} else if (this.loss_r_2 != null) {
				string_2 += this.loss_r_2.name;
			}
			if (this.loss_c_2b != null) {
				string_2 += " or " + this.loss_c_2b.name;
			} else if (this.loss_r_2b != null) {
				string_2 += " or " + this.loss_r_2b.name;
			}
			string_2 += ".";
		}
		
		if (this.has_3 || this.has_3b) {
			string_3 = OPTIONS[2];
			String obstring = OPTIONS[8];
			if (this.has_3) {
				if (this.loss_c_3 != null) {
					string_3 += this.loss_c_3.name;
				} else if (this.loss_r_3 != null) {
					string_3 += this.loss_r_3.name;
				}
				if (this.gain_c_3 != null) {
					obstring += FontHelper.colorString(this.gain_c_3.name, "g");
					prevcard_3 = this.gain_c_3;
				} else if (this.gain_r_3 != null) {
					obstring += FontHelper.colorString(this.gain_r_3.name, "g");
					prevrelic_3 = this.gain_r_3;
				}
			}
			if (this.has_3b) {
				if (this.has_3) {
					if (this.loss_c_3b != null || this.loss_r_3b != null) {
						string_3 += " and ";
					}
					obstring += " #gand ";
				}
				if (this.loss_c_3b != null) {
					string_3 += this.loss_c_3b.name;
				} else if (this.loss_r_3b != null) {
					string_3 += this.loss_r_3b.name;
				}
				if (this.gain_c_3b != null) {
					obstring += FontHelper.colorString(this.gain_c_3b.name, "g");
					if (prevcard_3 == null) {
						prevcard_3 = this.gain_c_3b;
					} else {
						prevcard_3 = null;
					}
				} else if (this.gain_r_3b != null) {
					obstring += FontHelper.colorString(this.gain_r_3b.name, "g");
				}
			}
			string_3 += obstring + ".";
		} else {
			string_3 = OPTIONS[0];
			if (this.loss_c_3 != null) {
				string_3 += this.loss_c_3.name;
			} else if (this.loss_r_3 != null) {
				string_3 += this.loss_r_3.name;
			}
			if (this.loss_c_3b != null) {
				string_3 += " or " + this.loss_c_3b.name;
			} else if (this.loss_r_3b != null) {
				string_3 += " or " + this.loss_r_3b.name;
			}
			string_3 += ".";
		}
		if (prevcard_1 == null) {
			if (prevrelic_1 == null)
				this.imageEventText.setDialogOption(string_1, (!this.has_1 && !this.has_1b));
			else
				this.imageEventText.setDialogOption(string_1, prevrelic_1);
		} else {
			if (prevrelic_1 == null)
				this.imageEventText.setDialogOption(string_1, prevcard_1);
			else
				this.imageEventText.setDialogOption(string_1, prevcard_1, prevrelic_1);
		}
		if (prevcard_2 == null) {
			if (prevrelic_2 == null)
				this.imageEventText.setDialogOption(string_2, (!this.has_2 && !this.has_2b));
			else
				this.imageEventText.setDialogOption(string_2, prevrelic_2);
		} else {
			if (prevrelic_2 == null)
				this.imageEventText.setDialogOption(string_2, prevcard_2);
			else
				this.imageEventText.setDialogOption(string_2, prevcard_2, prevrelic_2);
		}
		if (this.thirdOption) {
			if (prevcard_3 == null) {
				if (prevrelic_3 == null)
					this.imageEventText.setDialogOption(string_3, (!this.has_3 && !this.has_3b));
				else
					this.imageEventText.setDialogOption(string_3, prevrelic_3);
			} else {
				if (prevrelic_3 == null)
					this.imageEventText.setDialogOption(string_3, prevcard_3);
				else
					this.imageEventText.setDialogOption(string_3, prevcard_3, prevrelic_3);
			}
		}
	}
	
    this.imageEventText.setDialogOption(OPTIONS[5]);
	
  }

  protected void buttonEffect(int buttonPressed)
  {
    switch (this.screen)
    {
    case INTRO: 
      switch (buttonPressed)
      {
      case 0: 
		if (this.moddedguy) {
			AbstractDungeon.effectList.add(new RainingGoldEffect(this.goldgain));
            AbstractDungeon.player.gainGold(this.goldgain);
			if (AbstractDungeon.miscRng.randomBoolean(((float)this.searchcursechance) / 100.0F)) {
				//GenericEventDialog.updateBodyText(AccursedBlacksmith.RUMMAGE_RESULT + AccursedBlacksmith.CURSE_RESULT2);
				AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(this.searchCurse.makeCopy(), Settings.WIDTH / 2, Settings.HEIGHT / 2));
				UnlockTracker.markCardAsSeen(this.searchCurse.cardID);
			}
		} else {
		if (this.has_1) {
			if (this.loss_c_1 != null) {
				AbstractDungeon.effectList.add(new PurgeCardEffect(this.loss_c_1.makeCopy()));
				AbstractDungeon.player.masterDeck.removeCard(this.loss_c_1.cardID);
				CardCrawlGame.sound.play("CARD_EXHAUST");
			} else if (this.loss_r_1 != null) {
				ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_loseRelic(this.loss_r_1.relicId);
			}
			if (this.gain_c_1 != null) {
				AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(this.gain_c_1, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
				UnlockTracker.markCardAsSeen(this.gain_c_1.cardID);
			}
			if (this.gain_r_1 != null) {
				AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, this.gain_r_1);
			}
		}
		if (this.has_1b) {
			if (this.loss_c_1b != null) {
				AbstractDungeon.effectList.add(new PurgeCardEffect(this.loss_c_1b.makeCopy()));
				AbstractDungeon.player.masterDeck.removeCard(this.loss_c_1b.cardID);
				CardCrawlGame.sound.play("CARD_EXHAUST");
			} else if (this.loss_r_1b != null) {
				ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_loseRelic(this.loss_r_1b.relicId);
			}
			if (this.gain_c_1b != null) {
				AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(this.gain_c_1b, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
				UnlockTracker.markCardAsSeen(this.gain_c_1b.cardID);
			}
			if (this.gain_r_1b != null) {
				AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, this.gain_r_1b);
			}
		}
        //logMetric("Bash to Survivor+Neutralize");
		this.imageEventText.updateBodyText(RESULT_DIALOG_A);
		}
        break;
      case 1: 
        //logMetric("Ring to Blood");
		this.imageEventText.updateBodyText(RESULT_DIALOG_A);
		if (this.moddedguy) {
			AbstractDungeon.player.heal(AbstractDungeon.player.maxHealth);
			AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(this.searchCurse2.makeCopy(), Settings.WIDTH / 2, Settings.HEIGHT / 2));
			UnlockTracker.markCardAsSeen(this.searchCurse2.cardID);
		} else {
		if (this.has_2) {
			if (this.loss_c_2 != null) {
				AbstractDungeon.effectList.add(new PurgeCardEffect(this.loss_c_2.makeCopy()));
				AbstractDungeon.player.masterDeck.removeCard(this.loss_c_2.cardID);
				CardCrawlGame.sound.play("CARD_EXHAUST");
			} else if (this.loss_r_2 != null) {
				ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_loseRelic(this.loss_r_2.relicId);
			}
			if (this.gain_c_2 != null) {
				AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(this.gain_c_2, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
				UnlockTracker.markCardAsSeen(this.gain_c_2.cardID);
			}
			if (this.gain_r_2 != null) {
				AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, this.gain_r_2);
			}
		}
		if (this.has_2b) {
			if (this.loss_c_2b != null) {
				AbstractDungeon.effectList.add(new PurgeCardEffect(this.loss_c_2b.makeCopy()));
				AbstractDungeon.player.masterDeck.removeCard(this.loss_c_2b.cardID);
				CardCrawlGame.sound.play("CARD_EXHAUST");
			} else if (this.loss_r_2b != null) {
				ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_loseRelic(this.loss_r_2b.relicId);
			}
			if (this.gain_c_2b != null) {
				AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(this.gain_c_2b, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
				UnlockTracker.markCardAsSeen(this.gain_c_2b.cardID);
			}
			if (this.gain_r_2b != null) {
				AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, this.gain_r_2b);
			}
		}
		}
        break;
      case 2: 
		if (this.thirdOption) {
			this.imageEventText.updateBodyText(RESULT_DIALOG_A);
			if (this.moddedguy) {
				if (AbstractDungeon.miscRng.randomBoolean(((float)this.searchcursechance2) / 100.0F)) {
					AbstractCard curse = AbstractDungeon.returnRandomCurse().makeCopy();
					AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(curse, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
					UnlockTracker.markCardAsSeen(curse.cardID);
				}
				if (AbstractDungeon.miscRng.randomBoolean(((float)this.searchrelicchance2) / 100.0F)) {
					if (ReplayTheSpireMod.foundmod_snecko && AbstractDungeon.player instanceof TheSnecko) {
						AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, new M_SnakeBloodCore());
					} else if (ReplayTheSpireMod.foundmod_hubris && !AbstractDungeon.player.hasRelic("hubris:DisguiseKit") && (AbstractDungeon.player.hasRelic("PrismaticShard") || AbstractDungeon.miscRng.randomBoolean(0.70f))) {
						AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, RelicLibrary.getRelic("hubris:DisguiseKit"));
						AbstractDungeon.uncommonRelicPool.remove("hubris:DisguiseKit");
					} else {
						AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, RelicLibrary.getRelic("PrismaticShard"));
						AbstractDungeon.shopRelicPool.remove("PrismaticShard");
					}
				}
				break;
			}
			if (this.has_3) {
				if (this.loss_c_3 != null) {
					AbstractDungeon.effectList.add(new PurgeCardEffect(this.loss_c_3.makeCopy()));
					AbstractDungeon.player.masterDeck.removeCard(this.loss_c_3.cardID);
					CardCrawlGame.sound.play("CARD_EXHAUST");
				} else if (this.loss_r_3 != null) {
					ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_loseRelic(this.loss_r_3.relicId);
				}
				if (this.gain_c_3 != null) {
					AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(this.gain_c_3, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
					UnlockTracker.markCardAsSeen(this.gain_c_3.cardID);
				}
				if (this.gain_r_3 != null) {
					AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, this.gain_r_3);
				}
			}
			if (this.has_3b) {
				if (this.loss_c_3b != null) {
					AbstractDungeon.effectList.add(new PurgeCardEffect(this.loss_c_3b.makeCopy()));
					AbstractDungeon.player.masterDeck.removeCard(this.loss_c_3b.cardID);
					CardCrawlGame.sound.play("CARD_EXHAUST");
				} else if (this.loss_r_3b != null) {
					ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_loseRelic(this.loss_r_3b.relicId);
				}
				if (this.gain_c_3b != null) {
					AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(this.gain_c_3b, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
					UnlockTracker.markCardAsSeen(this.gain_c_3b.cardID);
				}
				if (this.gain_r_3b != null) {
					AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, this.gain_r_3b);
				}
			}
			break;
		}
      default: 
        this.imageEventText.updateBodyText(RESULT_DIALOG_B);
		float displayCount = 0.0F;
		
		ArrayList<AbstractCard> cards = new ArrayList();
		for (AbstractCard c : CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck).group) {
			cards.add(c);
		}
		
		if (cards.size() > 0) {
			AbstractCard card = (AbstractCard)cards.remove(MathUtils.random(cards.size() - 1));
			card.untip();
			card.unhover();
			AbstractDungeon.player.masterDeck.removeCard(card);
			AbstractDungeon.transformCard(card, true, AbstractDungeon.miscRng);
			if ((AbstractDungeon.screen != AbstractDungeon.CurrentScreen.TRANSFORM) && (AbstractDungeon.transformedCard != null))
			{
			  AbstractDungeon.effectsQueue.add(new ShowCardAndObtainEffect(
				AbstractDungeon.getTransformedCard(), Settings.WIDTH / 3.0F + displayCount, Settings.HEIGHT / 2.0F, false));
			  
			  displayCount += Settings.WIDTH / 6.0F;
			}
		} else {
			AbstractDungeon.effectsQueue.add(new ShowCardAndObtainEffect(new MirrorMistCurse(), Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0F, false));
		}
		AbstractDungeon.getCurrRoom().rewardPopOutTimer = 0.25F;
		
      }
      this.imageEventText.clearAllDialogs();
      this.imageEventText.setDialogOption(OPTIONS[6]);
      this.screen = CurScreen.RESULT;
      break;
    default: 
      openMap();
    }
  }
  
  public void logMetric(String actionTaken)
  {
    AbstractEvent.logMetric("Mirror Mist", actionTaken);
  }
}
