package com.megacrit.cardcrawl.events.shrines;

import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.curses.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.events.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.unlock.*;
import com.megacrit.cardcrawl.vfx.cardManip.*;
import com.megacrit.cardcrawl.rewards.*;
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
  
  private static enum CurScreen
  {
    INTRO,  RESULT;
    
    private CurScreen() {}
  }
  
  private AbstractCard getRandoCurse() {
	switch (AbstractDungeon.miscRng.random(0, 15)) {
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
		default: {
			return new SpreadingInfection();
		}
	}
  }
  
  public ChaosEvent()
  {
    super(NAME, DIALOG_1, "images/events/blacksmith.jpg");
	
	this.curse = this.getRandoCurse();
	this.curse2 = this.getRandoCurse();
	ArrayList<AbstractRelic> rings = new ArrayList<AbstractRelic>();
	rings.add(new RingOfFury());
	rings.add(new RingOfPeace());
	if (!AbstractDungeon.player.hasRelic("Snecko Eye") && !AbstractDungeon.player.hasRelic("Snecko Heart")) {
		rings.add(new RingOfPanic());
	}
	rings.add(new RingOfHypnosis());
	rings.add(new RingOfMisfortune());
	switch(AbstractDungeon.player.chosenClass) {
		case IRONCLAD: {
			if (!AbstractDungeon.player.hasRelic("Dodecahedron")) {
				rings.add(new RingOfSearing());
			}
			break;
		}
		case THE_SILENT: {
			rings.add(new RingOfFangs());
			break;
		}
		case DEFECT: {
			rings.add(new RingOfShattering());
			break;
		}
		default: {
			rings.add(new RingOfFangs());
			if (!AbstractDungeon.player.hasRelic("Dodecahedron")) {
				rings.add(new RingOfSearing());
			}
		}
	}
	if (AbstractDungeon.id.equals("TheBeyond")) {
		if (!AbstractDungeon.player.hasRelic("Ring of Chaos")) {
			rings.add(new RingOfChaos());
		}
	} else {
		if (!AbstractDungeon.player.hasRelic("Ectoplasm")) {
			rings.add(new RingOfGreed());
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
		AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
        //UnlockTracker.markCardAsSeen("Hallucinations");
        this.imageEventText.updateBodyText(D_RESULT);
      }
      else if (buttonPressed == 2)
      {
        //AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, this.ring.makeCopy());
		if (AbstractDungeon.miscRng.randomBoolean(0.75F)) {
			AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(this.getRandoCurse(), (Settings.WIDTH * 2.0F) / 3.0F, Settings.HEIGHT / 2.0F));
		}
		AbstractDungeon.combatRewardScreen.open();
		AbstractDungeon.combatRewardScreen.rewards.clear();
		AbstractDungeon.combatRewardScreen.rewards.add(new RewardItem(this.ring.makeCopy()));
		AbstractDungeon.combatRewardScreen.rewards.add(new RewardItem(this.ring2.makeCopy()));
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
