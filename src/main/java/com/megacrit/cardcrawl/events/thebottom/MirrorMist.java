package com.megacrit.cardcrawl.events.thebottom;

import com.megacrit.cardcrawl.audio.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.red.*;
import com.megacrit.cardcrawl.cards.green.*;
import com.megacrit.cardcrawl.cards.blue.*;
import com.megacrit.cardcrawl.cards.curses.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.events.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.screens.select.*;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.vfx.cardManip.*;

//import beaked.characters.*;
import blackrusemod.characters.*;
import chronomuncher.character.*;
import fruitymod.seeker.characters.*;
import mysticmod.character.*;

import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.relics.*;
import replayTheSpire.*;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.unlock.*;
import com.badlogic.gdx.math.MathUtils;
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
	
	switch(AbstractDungeon.player.chosenClass) {
		case IRONCLAD: {
			this.has_1 = CardHelper.hasCardWithID("Bash");
			this.has_1b = CardHelper.hasCardWithID("Bash");
			this.has_2 = ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic("Burning Blood");
			this.has_2b = ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic("Burning Blood");
			this.loss_c_1 = new Bash();
			this.gain_c_1 = new Survivor();
			this.gain_c_1b = new Neutralize();
			this.loss_r_2 = new BurningBlood();
			this.gain_c_2b = new SelfRepair();
			this.gain_c_2b.upgrade();
			this.gain_r_2 = new IronCore();
			break;
		}
		case THE_SILENT: {
			this.has_1 = ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic("Ring of the Snake");
			this.has_2 = CardHelper.hasCardWithID("Survivor");
			this.has_2b = CardHelper.hasCardWithID("Neutralize");
			this.loss_r_1 = new SnakeRing();
			this.gain_r_1 = new SizzlingBlood();
			this.loss_c_2 = new Survivor();
			this.loss_c_2b = new Neutralize();
			this.gain_c_2 = new Leap();
			this.gain_c_2b = new BeamCell();
			break;
		}
		case DEFECT: {
			this.has_1 = CardHelper.hasCardWithID("Dualcast");
			this.has_1b = ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic("Cracked Core");
			this.has_2b = this.has_1b;
			this.has_2 = CardHelper.hasCardWithID("Zap");
			this.loss_c_1 = new Dualcast();
			this.gain_c_1 = new Bash();
			this.loss_r_1b = new CrackedCore();
			this.gain_r_1b = new IronCore();
			this.loss_r_2b = new CrackedCore();
			this.gain_r_2b = new SnakeRing();
			this.loss_c_2 = new Zap();
			this.gain_c_2 = new DeadlyPoison();
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
			if (ReplayTheSpireMod.foundmod_seeker && AbstractDungeon.player instanceof TheSeeker) {
				this.has_1 = ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic("Arcanosphere");
				this.has_1b = CardHelper.hasCardWithID("AstralHaze");
				this.has_2 = this.has_1b;
				this.has_3 = this.has_2;
				this.loss_r_1 = ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_getRelic("Arcanosphere");
				this.gain_r_1 = new m_ArcaneBlood();
				if (this.has_1b) {
					for (final AbstractCard c : AbstractDungeon.player.masterDeck.group) {
						if (c.cardID.equals("AstralHaze")) {
							this.loss_c_1b = c.makeCopy();
						}
					}
					this.gain_c_1b = new Hemokinesis();
				}
				this.gain_c_1b = new RecklessCharge();
				this.loss_c_2 = this.loss_c_1b;
				this.loss_c_3 = this.loss_c_1b;
				this.gain_c_2 = new EndlessAgony();
				this.gain_c_3 = new Rebound();
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
				this.has_2b = CardHelper.hasCardWithID("KidneyShot");
				this.loss_r_2 = ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_getRelic("Uniform");
				this.gain_r_2 = new m_SnakeCloak();
				this.loss_c_2b = CardLibrary.getCopy("KidneyShot");
				this.gain_c_2b = new Survivor();
				this.has_3 = CardHelper.hasCardWithID("CleanUp");
				this.loss_c_3 = CardLibrary.getCopy("CleanUp");
				this.gain_c_3 = new Reprogram();
				this.has_3b = CardHelper.hasCardWithID("KidneyShot");
				this.loss_c_3b = CardLibrary.getCopy("KidneyShot");
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
					this.has_1 = CardHelper.hasCardWithID("PatternShift");
					this.loss_c_1 = CardLibrary.getCopy("PatternShift");
					this.gain_c_1 = CardLibrary.getCopy("hubris:StunningStrike");
					this.has_1b = CardHelper.hasCardWithID("SecondHand");
					this.loss_c_1b = CardLibrary.getCopy("SecondHand");
					this.gain_c_1b = CardLibrary.getCopy("hubris:ReadiedAction");
				} else {
					this.has_1 = AbstractDungeon.player.hasRelic("Chronometer");
					this.has_1b = CardHelper.hasCardWithID("SecondHand");
					this.loss_r_1 = RelicLibrary.getRelic("Chronometer").makeCopy();
					this.gain_r_1 = new BurningBlood();
					this.loss_c_1b = CardLibrary.getCopy("SecondHand");
					this.gain_c_1b = new SpotWeakness();
				}
				this.has_2 = CardHelper.hasCardWithID("PatternShift");
				this.has_2b = CardHelper.hasCardWithID("SecondHand");
				this.loss_c_2 = CardLibrary.getCopy("PatternShift");
				this.loss_c_2b = CardLibrary.getCopy("SecondHand");
				this.gain_c_2 = new InfiniteBlades();
				this.gain_c_2b = new WellLaidPlans();
				this.has_3 = AbstractDungeon.player.hasRelic("Chronometer");
				this.has_3b = CardHelper.hasCardWithID("SecondHand");
				this.loss_r_3 = RelicLibrary.getRelic("Chronometer").makeCopy();
				this.gain_r_3 = new m_MercuryCore();
				this.loss_c_3b = CardLibrary.getCopy("SecondHand");
				this.gain_c_3b = new WhiteNoise();
				this.thirdOption = true;
				break;
			}
			/*if (ReplayTheSpireMod.foundmod_beaked && AbstractDungeon.player instanceof BeakedTheCultist) {
				int ceremonies = 0;
				for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
					if (c.cardID.equals("beaked:Ceremony")) {
						ceremonies++;
					}
				}
				if (ceremonies > 1) {
					this.has_1 = true;
					this.has_1b = true;
					this.loss_c_1 = CardLibrary.getCopy("beaked:Ceremony");
					this.loss_c_1b = CardLibrary.getCopy("beaked:Ceremony");
					this.gain_c_1 = new DemonForm();
				} else {
					this.has_1 = AbstractDungeon.player.hasRelic("beaked:MendingPlumage");
					this.has_1b = CardHelper.hasCardWithID("beaked:Ceremony");
					if (this.has_1) {
						this.loss_r_1 = AbstractDungeon.player.getRelic("beaked:MendingPlumage");
					}
					this.gain_r_1 = new BurningBlood();
					this.loss_c_1b = CardLibrary.getCopy("beaked:Ceremony");
					this.gain_c_1b = new Inflame();
				}
				break;
			}*/
			
			this.moddedguy = true;
		}
	}
	
	if (this.moddedguy) {
		this.imageEventText.setDialogOption(OPTIONS[10] + this.goldgain + OPTIONS[11] + this.searchcursechance + OPTIONS[12] + this.searchCurse.name + ".", this.searchCurse);
		this.imageEventText.setDialogOption(OPTIONS[13] + this.searchCurse2.name + ".", this.searchCurse2);
		this.thirdOption = false;
	} else {
		this.checkForUpgrades();
		String string_1 = "";
		String string_2 = "";
		String string_3 = "";
		AbstractCard prevcard_1 = null;
		AbstractCard prevcard_2 = null;
		AbstractCard prevcard_3 = null;
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
			this.imageEventText.setDialogOption(string_1, (!this.has_1 && !this.has_1b));
		} else {
			this.imageEventText.setDialogOption(string_1, prevcard_1);
		}
		if (prevcard_2 == null) {
			this.imageEventText.setDialogOption(string_2, (!this.has_2 && !this.has_2b));
		} else {
			this.imageEventText.setDialogOption(string_2, prevcard_2);
		}
		if (this.thirdOption) {
			if (prevcard_3 == null) {
				this.imageEventText.setDialogOption(string_3, (!this.has_3 && !this.has_3b));
			} else {
				this.imageEventText.setDialogOption(string_3, prevcard_3);
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
        //logMetric("Wander");
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
