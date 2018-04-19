package com.megacrit.cardcrawl.events.shrines;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import com.megacrit.cardcrawl.cards.curses.Pain;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.core.Settings;
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
    
    GenericEventDialog.setDialogOption(OPTIONS[0]);
	
	this.hasKey = AbstractDungeon.player.hasRelic("Cursed Key");
	if (this.hasKey){
		GenericEventDialog.setDialogOption(OPTIONS[1]);
	} else {
		GenericEventDialog.setDialogOption(OPTIONS[2], true);
	}
    GenericEventDialog.setDialogOption(OPTIONS[3]);
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
  
  protected void buttonEffect(int buttonPressed)
  {
    switch (this.screen)
    {
    case INTRO: 
      switch (buttonPressed)
      {
      case 0: 
        this.screen = CurScreen.TRAP;
        GenericEventDialog.updateBodyText(DESCRIPTIONS[1]);
        //logMetric("Triggered Chest Trap");
        GenericEventDialog.updateDialogOption(0, OPTIONS[4], CardLibrary.getCopy("Pain"));
        GenericEventDialog.updateDialogOption(1, OPTIONS[5] + this.hpLoss + OPTIONS[6]);
        GenericEventDialog.updateDialogOption(2, OPTIONS[7] + this.dodgeChance + OPTIONS[8] + this.maxHpLoss + OPTIONS[6]);
        break;
      case 1: 
        this.screen = CurScreen.CURSE;
        GenericEventDialog.updateBodyText(DESCRIPTIONS[2]);
        //logMetric("Used Cursed Key");
        GenericEventDialog.updateDialogOption(0, OPTIONS[9]);
        GenericEventDialog.clearRemainingOptions();
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
				GenericEventDialog.updateBodyText(DESCRIPTIONS[3]);
				GenericEventDialog.updateDialogOption(0, OPTIONS[9]);
				GenericEventDialog.clearRemainingOptions();
				CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.MED, false);
				AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Pain(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));

				UnlockTracker.markCardAsSeen("Pain");
				//logMetric("Take Pain");
				break;
			case 1: 
				GenericEventDialog.updateBodyText(DESCRIPTIONS[3]);
				GenericEventDialog.updateDialogOption(0, OPTIONS[9]);
				GenericEventDialog.clearRemainingOptions();
				CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.MED, false);
				CardCrawlGame.sound.play("BLUNT_FAST");
				AbstractDungeon.player.damage(new DamageInfo(null, this.hpLoss));
				//logMetric("Take Damage");
				break;
			case 2: 
				int r = MathUtils.random(100);
				if (r > this.dodgeChance){
					GenericEventDialog.updateBodyText(DESCRIPTIONS[4]);
					GenericEventDialog.updateDialogOption(0, OPTIONS[9]);
					GenericEventDialog.clearRemainingOptions();
				} else {
					GenericEventDialog.updateBodyText(DESCRIPTIONS[3]);
					GenericEventDialog.updateDialogOption(0, OPTIONS[9]);
					GenericEventDialog.clearRemainingOptions();
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
		GenericEventDialog.updateBodyText(DESCRIPTIONS[5]);
		GenericEventDialog.updateDialogOption(0, OPTIONS[3]);
		GenericEventDialog.clearRemainingOptions();
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

