package com.megacrit.cardcrawl.events.shrines;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.events.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.rewards.*;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.screens.*;
import com.megacrit.cardcrawl.cards.curses.*;
import com.megacrit.cardcrawl.unlock.*;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import ReplayTheSpireMod.*;
import java.util.ArrayList;

public class TrappedChest
  extends AbstractImageEvent
{
  public static final String ID = "Trapped Chest";
  private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString("Trapped Chest");
  public static final String NAME = eventStrings.NAME;
  public static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
  public static final String[] OPTIONS = eventStrings.OPTIONS;
  private static final String DIALOG_1 = DESCRIPTIONS[0];
  private static final float HP_LOSS_PERCENT = 0.25F;
  private static final float DODGE_HP_LOSS_PERCENT = 0.4F;
  private static final float A_2_HP_LOSS_PERCENT = 0.33F;
  private int hpLoss;
  private int maxHpLoss;
  private int dodgeChance;
  private boolean hasKey;
  private CurScreen screen = CurScreen.INTRO;
  
  private static enum CurScreen
  {
    INTRO,  TRAP, CURSE, RESULT;
    
    private CurScreen() {}
  }
  
  public TrappedChest()
  {
    super(NAME, DIALOG_1, "images/events/mausoleum.jpg");
    
    this.imageEventText.setDialogOption(OPTIONS[0]);
	
	this.hasKey = ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic("Cursed Key");
	if (this.hasKey){
		this.imageEventText.setDialogOption(OPTIONS[1]);
	} else {
		this.imageEventText.setDialogOption(OPTIONS[2], true);
	}
    this.imageEventText.setDialogOption(OPTIONS[3]);
    if (AbstractDungeon.ascensionLevel >= 15)
    {
      this.hpLoss = ((int)(AbstractDungeon.player.maxHealth * 0.33F));
      this.maxHpLoss = ((int)(AbstractDungeon.player.maxHealth * 0.4F));
	  this.dodgeChance = 75;
    }
    else
    {
      this.hpLoss = ((int)(AbstractDungeon.player.maxHealth * 0.25F));
      this.maxHpLoss = ((int)(AbstractDungeon.player.maxHealth * 0.4F));
	  this.dodgeChance = 50;
    }
    if (this.maxHpLoss < 1) {
      this.maxHpLoss = 1;
    }
  }
  
  private void unlockTheChest() {
	    this.imageEventText.updateDialogOption(0, OPTIONS[9]);
        this.imageEventText.clearRemainingOptions();
		if (ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic("Matryoshka")) {
			if (ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_getRelic("Matryoshka").counter > 0) {
				this.imageEventText.setDialogOption(OPTIONS[12]);
			} else {
				this.imageEventText.setDialogOption(OPTIONS[11], true);
			}
		} else {
			this.imageEventText.setDialogOption(OPTIONS[10], true);
		}
  }
  
  protected void buttonEffect(int buttonPressed)
  {
    switch (this.screen)
    {
    case INTRO: 
      switch (buttonPressed)
      {
      case 0: 
        this.screen = CurScreen.TRAP;
        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
        //logMetric("Triggered Chest Trap");
        this.imageEventText.updateDialogOption(0, OPTIONS[4], new Pain());
        this.imageEventText.updateDialogOption(1, OPTIONS[5] + this.hpLoss + OPTIONS[6]);
        this.imageEventText.updateDialogOption(2, OPTIONS[7] + this.dodgeChance + OPTIONS[8] + this.maxHpLoss + OPTIONS[6]);
        break;
      case 1: 
        this.screen = CurScreen.CURSE;
        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
        //logMetric("Used Cursed Key");
        this.unlockTheChest();
		AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(
        AbstractDungeon.returnRandomCurse(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
        break;
      case 2: 
        //logMetric("Ignored Chest");
		openMap();
        break;
	  }
      break;
	case TRAP:
		this.screen = CurScreen.CURSE;
		switch (buttonPressed)
		{
			case 0: 
				this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
				this.unlockTheChest();
				CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.MED, false);
				AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Pain(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));

				UnlockTracker.markCardAsSeen("Pain");
				//logMetric("Take Pain");
				break;
			case 1: 
				this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
				this.unlockTheChest();
				CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.MED, false);
				CardCrawlGame.sound.play("BLUNT_FAST");
				AbstractDungeon.player.damage(new DamageInfo(null, this.hpLoss));
				//logMetric("Take Damage");
				break;
			case 2: 
				int r = MathUtils.random(100);
				if (r > this.dodgeChance){
					this.imageEventText.updateBodyText(DESCRIPTIONS[4]);
					this.unlockTheChest();
				} else {
					this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
					this.unlockTheChest();
					CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.MED, false);
					CardCrawlGame.sound.play("BLUNT_FAST");
					AbstractDungeon.player.damage(new DamageInfo(null, this.maxHpLoss));
				}
				//logMetric("Dodge");
				break;
		}
		break;
	case CURSE:
		this.screen = CurScreen.RESULT;
		this.imageEventText.updateBodyText(DESCRIPTIONS[5]);
		this.imageEventText.updateDialogOption(0, OPTIONS[3]);
		this.imageEventText.clearRemainingOptions();
        AbstractDungeon.getCurrRoom().rewards.clear();
		
		int rewardSet = MathUtils.random(8);
		int goldAmt = MathUtils.random(20) + 15;
		if (AbstractDungeon.ascensionLevel >= 15){
			goldAmt -= 10;
		}
		switch (rewardSet) {
			case 0:
				AbstractDungeon.getCurrRoom().addRelicToRewards(AbstractRelic.RelicTier.RARE);
				goldAmt += MathUtils.random(10);
				break;
			case 1:
				AbstractDungeon.getCurrRoom().addRelicToRewards(AbstractRelic.RelicTier.UNCOMMON);
				AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(PotionHelper.getRandomPotion()));
				goldAmt += MathUtils.random(10) + 25;
				break;
			case 2:
				AbstractDungeon.getCurrRoom().addRelicToRewards(AbstractRelic.RelicTier.UNCOMMON);
				AbstractDungeon.getCurrRoom().rewards.add(new RewardItem());
				goldAmt += MathUtils.random(10) + 20;
				break;
			case 3:
				AbstractDungeon.getCurrRoom().addRelicToRewards(AbstractRelic.RelicTier.COMMON);
				AbstractDungeon.getCurrRoom().rewards.add(new RewardItem());
				AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(PotionHelper.getRandomPotion()));
				goldAmt += MathUtils.random(30) + 15;
				break;
			case 4:
				AbstractDungeon.getCurrRoom().addRelicToRewards(AbstractRelic.RelicTier.COMMON);
				AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(PotionHelper.getRandomPotion()));
				AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(PotionHelper.getRandomPotion()));
				goldAmt += MathUtils.random(30) + 25;
				break;
			case 5:
				AbstractDungeon.getCurrRoom().addRelicToRewards(AbstractRelic.RelicTier.COMMON);
				AbstractDungeon.getCurrRoom().rewards.add(new RewardItem());
				goldAmt += MathUtils.random(30) + 50;
				break;
			case 6:
				AbstractDungeon.getCurrRoom().rewards.add(new RewardItem());
				AbstractDungeon.getCurrRoom().rewards.add(new RewardItem());
				goldAmt += MathUtils.random(25) + 60;
				break;
			case 7:
				AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(PotionHelper.getRandomPotion()));
				AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(PotionHelper.getRandomPotion()));
				AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(PotionHelper.getRandomPotion()));
				goldAmt += MathUtils.random(50);
				break;
			default:
				goldAmt += 175;
				break;
				
		}
		if (buttonPressed == 1 && ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic("Matryoshka")) {
			AbstractRelic mat = ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_getRelic("Matryoshka");
			mat.counter--;
			mat.flash();
			//AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, mat));
			if (mat.counter == 0) {
                mat.setCounter(-2);
                mat.description = mat.DESCRIPTIONS[2];
            }
            else {
                mat.description = mat.DESCRIPTIONS[1];
            }
			rewardSet = MathUtils.random(6);
			switch (rewardSet) {
				case 0:
					AbstractDungeon.getCurrRoom().addRelicToRewards(AbstractRelic.RelicTier.UNCOMMON);
					AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(PotionHelper.getRandomPotion()));
					goldAmt += MathUtils.random(10) + 25;
					break;
				case 1:
					AbstractDungeon.getCurrRoom().addRelicToRewards(AbstractRelic.RelicTier.UNCOMMON);
					AbstractDungeon.getCurrRoom().rewards.add(new RewardItem());
					goldAmt += MathUtils.random(10) + 20;
					break;
				case 2:
					AbstractDungeon.getCurrRoom().addRelicToRewards(AbstractRelic.RelicTier.COMMON);
					AbstractDungeon.getCurrRoom().rewards.add(new RewardItem());
					AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(PotionHelper.getRandomPotion()));
					goldAmt += MathUtils.random(30) + 15;
					break;
				case 3:
					AbstractDungeon.getCurrRoom().addRelicToRewards(AbstractRelic.RelicTier.COMMON);
					AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(PotionHelper.getRandomPotion()));
					AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(PotionHelper.getRandomPotion()));
					goldAmt += MathUtils.random(30) + 25;
					break;
				case 4:
					AbstractDungeon.getCurrRoom().addRelicToRewards(AbstractRelic.RelicTier.COMMON);
					AbstractDungeon.getCurrRoom().rewards.add(new RewardItem());
					goldAmt += MathUtils.random(30) + 50;
					break;
				case 5:
					AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(PotionHelper.getRandomPotion()));
					AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(PotionHelper.getRandomPotion()));
					AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(PotionHelper.getRandomPotion()));
					goldAmt += MathUtils.random(50);
					break;
				default:
					goldAmt += 175;
					break;
					
			}
		}
		
        AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(goldAmt));
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
        AbstractDungeon.combatRewardScreen.open();
		break;
    default: 
      openMap();
    }
  }
  
  public void logMetric(String actionTaken)
  {
    AbstractEvent.logMetric("Trapped Chest", actionTaken);
  }
}

