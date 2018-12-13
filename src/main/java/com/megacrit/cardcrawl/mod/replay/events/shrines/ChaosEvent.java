package com.megacrit.cardcrawl.mod.replay.events.shrines;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Doubt;
import com.megacrit.cardcrawl.cards.curses.Normality;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.cards.curses.*;
import com.megacrit.cardcrawl.mod.replay.events.*;
import com.megacrit.cardcrawl.mod.replay.relics.*;
import com.megacrit.cardcrawl.mod.replay.rooms.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.unlock.*;
import com.megacrit.cardcrawl.vfx.cardManip.*;

import basemod.BaseMod;
import basemod.helpers.RelicType;

import com.megacrit.cardcrawl.rewards.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.badlogic.gdx.math.MathUtils;
import java.util.ArrayList;

public class ChaosEvent
  extends AbstractImageEvent
{
  public static final String ID = "Chaos Ring Event";
  private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString("Chaos Ring Event");
  public static final String NAME = eventStrings.NAME;
  public static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
  public static final String[] OPTIONS = eventStrings.OPTIONS;
  private static final String DIALOG_1 = DESCRIPTIONS[0];
  private static final String D_RESULT = DESCRIPTIONS[1];
  private CurScreen screen = CurScreen.INTRO;
  private AbstractRelic ring;
  private AbstractRelic ring2;
  private AbstractCard curse;
  private AbstractCard curse2;

  public static class RingListEntry {
	  public AbstractRelic ring;
	  public AbstractPlayer.PlayerClass classRestriction;
	  public boolean moddedAlowed;
	  public String[] relicConflictions;
	  public RingListEntry(AbstractRelic ring) {
		  this(ring, null, true, new String[0]);
	  }
	  public RingListEntry(AbstractRelic ring, AbstractPlayer.PlayerClass classRestriction, boolean moddedAlowed) {
		  this(ring, classRestriction, moddedAlowed, new String[0]);
	  }
	  public RingListEntry(AbstractRelic ring, String[] relicConflictions) {
		  this(ring, null, true, relicConflictions);
	  }
	  public RingListEntry(AbstractRelic ring, AbstractPlayer.PlayerClass classRestriction, boolean moddedAlowed, String[] relicConflictions) {
		  this.ring = ring;
		  this.classRestriction = classRestriction;
		  this.moddedAlowed = moddedAlowed;
		  this.relicConflictions = relicConflictions;
	  }
	  public boolean isValid() {
		  if (this.ring == null) {
			  return false;
		  }
		  if (AbstractDungeon.player.hasRelic(this.ring.relicId)) {
			  return false;
		  }
		  if (this.classRestriction != null) {
			  if (AbstractDungeon.player.chosenClass != this.classRestriction) {
				  if (AbstractDungeon.player.chosenClass != AbstractPlayer.PlayerClass.IRONCLAD && AbstractDungeon.player.chosenClass != AbstractPlayer.PlayerClass.THE_SILENT && AbstractDungeon.player.chosenClass != AbstractPlayer.PlayerClass.DEFECT) {
					  if (!this.moddedAlowed) {
						  return false;
					  }
				  } else {
					  return false;
				  }
			  }
		  }
		  if (this.relicConflictions.length > 0) {
			  for (String s : this.relicConflictions) {
				  if (AbstractDungeon.player.hasRelic(s)) {
					  return false;
				  }
			  }
		  }
		  return true;
	  }
  }
  private static ArrayList<RingListEntry> ringList = new ArrayList<RingListEntry>();
  public static void addRing(AbstractRelic r) {
	  ChaosEvent.ringList.add(new RingListEntry(r));
	  BaseMod.addRelic(r, RelicType.SHARED);
  }
  public static void addRing(RingListEntry r) {
	  ChaosEvent.ringList.add(r);
	  BaseMod.addRelic(r.ring, RelicType.SHARED);
  }
  
  private static enum CurScreen
  {
    INTRO,  RESULT;
    
    private CurScreen() {}
  }
  
  private AbstractCard getRandoCurse(int rchance) {
	switch (AbstractDungeon.miscRng.random(0, 15 + rchance)) {
		case 0: case 1: {
			return new Delirium();
		}
		case 2: case 3: {
			return new Voices();
		}
		case 4: case 5: {
			return new LoomingEvil();
		}
		case 6: case 7: {
			return new Hallucinations();
		}
		case 8: case 9: {
			return new Amnesia();
		}
		case 10: {
			return new Doubt();
		}
		case 11: {
			return new Normality();
		}
		case 12: {
			return new Sickly();
		}
		case 13: {
			return new Languid();
		}
		case 14: {
			return new CommonCold();
		}
		case 15: {
			return new SpreadingInfection();
		}
		default: {
			return AbstractDungeon.returnRandomCurse().makeCopy();
		}
	}
  }
  
  public static ArrayList<AbstractRelic> getRingPool() {
	  ArrayList<AbstractRelic> rings = new ArrayList<AbstractRelic>();
		for (RingListEntry e : ringList) {
			if (e.isValid()) {
				rings.add(e.ring.makeCopy());
			}
		}
		return rings;
  }
  
  public ChaosEvent()
  {
    super(NAME, DIALOG_1, "images/events/blacksmith.jpg");
	
	this.curse = this.getRandoCurse(0);
	this.curse2 = this.getRandoCurse(0);
	while (this.curse.cardID.equals(this.curse2.cardID)) {
		this.curse2 = this.getRandoCurse(2);
	}
	ArrayList<AbstractRelic> rings = new ArrayList<AbstractRelic>();
	for (RingListEntry e : ringList) {
		if (e.isValid()) {
			rings.add(e.ring.makeCopy());
		}
	}
	if (AbstractDungeon.id.equals("TheBeyond") && AbstractDungeon.ascensionLevel < 20 && !Settings.isEndless) {
		if (!AbstractDungeon.player.hasRelic("Ring of Chaos")) {
			rings.add(new RingOfChaos());
		}
	}
	
	int rando = AbstractDungeon.miscRng.random(0, rings.size() - 1);
	this.ring = rings.remove(rando);
	rando = AbstractDungeon.miscRng.random(0, rings.size() - 1);
	this.ring2 = rings.remove(rando);
	
	this.imageEventText.setDialogOption(OPTIONS[0] + FontHelper.colorString(this.curse.name, "r") + ".", this.curse);
    this.imageEventText.setDialogOption(OPTIONS[1] + FontHelper.colorString(this.curse2.name, "r") + ".", this.curse2);
    this.imageEventText.setDialogOption(OPTIONS[2]);
    this.imageEventText.setDialogOption(OPTIONS[3]);
  }
  
  protected void buttonEffect(int buttonPressed)
  {
    switch (this.screen)
    {
    case INTRO: 
      if (buttonPressed == 0)
      {
        //AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, this.ring.makeCopy());
		if (AbstractDungeon.miscRng.randomBoolean(0.5F)) {
			AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(this.curse.makeCopy(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
		}
		AbstractDungeon.combatRewardScreen.open();
		AbstractDungeon.combatRewardScreen.rewards.clear();
		AbstractDungeon.combatRewardScreen.rewards.add(new RewardItem(this.ring.makeCopy()));
		AbstractDungeon.combatRewardScreen.positionRewards();
		AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
        //UnlockTracker.markCardAsSeen("Hallucinations");
        this.imageEventText.updateBodyText(D_RESULT);
      }
      else if (buttonPressed == 1)
      {
        //AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, this.ring.makeCopy());
		if (AbstractDungeon.miscRng.randomBoolean(0.5F)) {
			AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(this.curse2.makeCopy(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
		}
		AbstractDungeon.combatRewardScreen.open();
		AbstractDungeon.combatRewardScreen.rewards.clear();
		AbstractDungeon.combatRewardScreen.rewards.add(new RewardItem(this.ring2.makeCopy()));
		AbstractDungeon.combatRewardScreen.positionRewards();
		AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
        //UnlockTracker.markCardAsSeen("Hallucinations");
        this.imageEventText.updateBodyText(D_RESULT);
      }
      else if (buttonPressed == 2)
      {
        //AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, this.ring.makeCopy());
		if (AbstractDungeon.miscRng.randomBoolean(0.75F)) {
			AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(this.getRandoCurse(4), (Settings.WIDTH * 2.0F) / 3.0F, Settings.HEIGHT / 2.0F));
		}
		AbstractDungeon.combatRewardScreen.open();
		AbstractDungeon.combatRewardScreen.rewards.clear();
		AbstractDungeon.combatRewardScreen.rewards.add(new RewardItem(this.ring.makeCopy()));
		AbstractDungeon.combatRewardScreen.rewards.add(new RewardItem(this.ring2.makeCopy()));
		AbstractDungeon.combatRewardScreen.positionRewards();
		AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
        //UnlockTracker.markCardAsSeen("Hallucinations");
        this.imageEventText.updateBodyText(D_RESULT);
      }
      else
      {
        openMap();
        this.imageEventText.clearAllDialogs();
        this.imageEventText.setDialogOption(OPTIONS[3]);
      }
      this.imageEventText.clearAllDialogs();
      this.imageEventText.setDialogOption(OPTIONS[3]);
      this.screen = CurScreen.RESULT;
      break;
    default: 
      openMap();
    }
  }
  
}
