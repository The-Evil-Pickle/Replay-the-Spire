package com.megacrit.cardcrawl.rewards;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.daily.DailyMods;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.InputHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.TipTracker;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.TutorialStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.PotionPlaceholder;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.RingOfChaos;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.TreasureRoom;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.ui.FtueTip;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.RewardGlowEffect;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RewardItem
{
  private static final Logger logger = LogManager.getLogger(RewardItem.class.getName());
  private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("RewardItem");
  public static final String[] TEXT = uiStrings.TEXT;
  private static final TutorialStrings tutorialStrings = CardCrawlGame.languagePack.getTutorialString("Potion Tip");
  public static final String[] MSG = tutorialStrings.TEXT;
  public static final String[] LABEL = tutorialStrings.LABEL;
  public RewardType type;
  public int goldAmt = 0;
  public int bonusGold = 0;
  public String text;
  public AbstractRelic relic;
  public AbstractPotion potion;
  private ArrayList<AbstractCard> cards;
  private ArrayList<AbstractGameEffect> effects = new ArrayList<AbstractGameEffect>();
  private boolean isBoss;
  public Hitbox hb = new Hitbox(460.0F * Settings.scale, 90.0F * Settings.scale);
  public float y;
  public float flashTimer = 0.0F;
  public boolean isDone = false;
  private static final float FLASH_DUR = 0.5F;
  private static final int ITEM_W = 464;
  private static final int ITEM_H = 98;
  public static final float REWARD_ITEM_X = 786.0F * Settings.scale;
  private static final float REWARD_TEXT_X = 833.0F * Settings.scale;
  
  public static enum RewardType
  {
    CARD,  GOLD,  RELIC,  POTION,  STOLEN_GOLD;
    
    private RewardType() {}
  }
  
  public RewardItem(int gold)
  {
    this.type = RewardType.GOLD;
    this.goldAmt = gold;
    applyGoldBonus(false);
  }
  
  public RewardItem(int gold, boolean theft)
  {
    this.type = RewardType.STOLEN_GOLD;
    this.goldAmt = gold;
    applyGoldBonus(theft);
  }
  
  private void applyGoldBonus(boolean theft)
  {
    int tmp = this.goldAmt;
    this.bonusGold = 0;
    if (theft)
    {
      this.text = (this.goldAmt + TEXT[0]);
    }
    else
    {
      if (!(AbstractDungeon.getCurrRoom() instanceof TreasureRoom))
      {
        if (AbstractDungeon.player.hasRelic("Golden Idol")) {
          this.bonusGold += MathUtils.round(tmp * 0.25F);
        }
        if (((Boolean)DailyMods.negativeMods.get("Midas")).booleanValue()) {
          this.bonusGold += MathUtils.round(tmp * 2.0F);
        }
      }
      if (this.bonusGold == 0) {
        this.text = (this.goldAmt + TEXT[1]);
      } else {
        this.text = (this.goldAmt + TEXT[1] + " (" + this.bonusGold + ")");
      }
    }
  }
  
  public RewardItem(AbstractRelic relic)
  {
    this.type = RewardType.RELIC;
    this.relic = relic;
    relic.hitbox = new Hitbox(80.0F * Settings.scale, 80.0F * Settings.scale);
    relic.hitbox.move(-1000.0F, -1000.0F);
    this.text = relic.name;
  }
  
  public RewardItem(AbstractPotion potion)
  {
    this.type = RewardType.POTION;
    this.potion = potion;
    this.text = potion.name;
  }
  
  public RewardItem()
  {
    this.type = RewardType.CARD;
    this.isBoss = (AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss);
    this.cards = AbstractDungeon.getRewardCards();
	if (AbstractDungeon.player.hasRelic("Ring of Chaos") && !this.isBoss) {
		int numToChange = AbstractDungeon.miscRng.random(2);
		for (int i = 0; i < this.cards.size() - 2; i++){
			numToChange += AbstractDungeon.miscRng.random(2);
		}
		for (int i = 0; i < this.cards.size() && numToChange > 0; i++){
			if (((RingOfChaos)AbstractDungeon.player.getRelic("Ring of Chaos")).chaosUpgradeCard(this.cards.get(i))) {
				numToChange -= 1;
			}
		}
	}
    this.text = TEXT[2];
  }
  
  public void incrementGold(int gold)
  {
    if (this.type == RewardType.GOLD)
    {
      this.goldAmt += gold;
      applyGoldBonus(false);
    }
    else if (this.type == RewardType.STOLEN_GOLD)
    {
      this.goldAmt += gold;
      applyGoldBonus(true);
    }
    else
    {
      logger.info("ERROR: Using increment() wrong for RewardItem");
    }
  }
  
  public void move(float y)
  {
    this.y = y;
    this.hb.move(Settings.WIDTH / 2.0F, y);
    if (this.type == RewardType.POTION)
    {
      this.potion.moveInstantly(REWARD_ITEM_X, y);
    }
    else if (this.type == RewardType.RELIC)
    {
      this.relic.currentX = REWARD_ITEM_X;
      this.relic.currentY = y;
      this.relic.targetX = REWARD_ITEM_X;
      this.relic.targetY = y;
    }
  }
  
  public void flash()
  {
    this.flashTimer = 0.5F;
  }
  
  public void update()
  {
    if (this.flashTimer > 0.0F)
    {
      this.flashTimer -= Gdx.graphics.getDeltaTime();
      if (this.flashTimer < 0.0F) {
        this.flashTimer = 0.0F;
      }
    }
    this.hb.update();
    if (this.effects.size() == 0) {
      this.effects.add(new RewardGlowEffect(this.hb.cX, this.hb.cY));
    }
    for (Iterator<AbstractGameEffect> i = this.effects.iterator(); i.hasNext();)
    {
      AbstractGameEffect e = (AbstractGameEffect)i.next();
      e.update();
      if (e.isDone) {
        i.remove();
      }
    }
    if (this.hb.hovered) {
      switch (this.type)
      {
      case POTION: 
        if (!AbstractDungeon.topPanel.potionCombine) {
          TipHelper.renderGenericTip(InputHelper.mX + 50.0F * Settings.scale, InputHelper.mY, this.potion.name, this.potion.description);
        }
        break;
      case RELIC: 
        break;
      }
    }
    if (this.hb.justHovered) {
      CardCrawlGame.sound.play("UI_HOVER");
    }
    if ((this.hb.hovered) && (InputHelper.justClickedLeft) && (!this.isDone))
    {
      CardCrawlGame.sound.playA("UI_CLICK_1", 0.1F);
      this.hb.clickStarted = true;
    }
    if (this.hb.clicked)
    {
      this.hb.clicked = false;
      this.isDone = true;
    }
  }
  
  public boolean claimReward()
  {
    switch (this.type)
    {
    case GOLD: 
      CardCrawlGame.sound.play("GOLD_GAIN");
      if (this.bonusGold == 0) {
        AbstractDungeon.player.gainGold(this.goldAmt);
      } else {
        AbstractDungeon.player.gainGold(this.goldAmt + this.bonusGold);
      }
      return true;
    case STOLEN_GOLD: 
      CardCrawlGame.sound.play("GOLD_GAIN");
      if (this.bonusGold == 0) {
        AbstractDungeon.player.gainGold(this.goldAmt);
      } else {
        AbstractDungeon.player.gainGold(this.goldAmt + this.bonusGold);
      }
      return true;
    case POTION: 
      if (AbstractDungeon.player.hasRelic("Sozu"))
      {
        AbstractDungeon.player.getRelic("Sozu").flash();
        return true;
      }
      for (int i = 0; i < 3; i++) {
        if ((AbstractDungeon.player.potions[i] instanceof PotionPlaceholder))
        {
          CardCrawlGame.sound.play("POTION_1");
          this.potion.moveInstantly(AbstractDungeon.player.potions[i].currentX, AbstractDungeon.player.potions[i].currentY);
          
          this.potion.isObtained = true;
          this.potion.isDone = true;
          this.potion.isAnimating = false;
          this.potion.flash();
          AbstractDungeon.player.potions[i] = this.potion;
          if (!((Boolean)TipTracker.tips.get("POTION_TIP")).booleanValue())
          {
            AbstractDungeon.ftue = new FtueTip(LABEL[0], MSG[0], Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, this.potion);
            
            TipTracker.neverShowAgain("POTION_TIP");
          }
          return true;
        }
      }
      return false;
    case RELIC: 
      if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.GRID) {
        return false;
      }
      this.relic.instantObtain(false);
      return true;
    case CARD: 
      if (AbstractDungeon.player.hasRelic("Question Card")) {
        AbstractDungeon.player.getRelic("Question Card").flash();
      }
      if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.COMBAT_REWARD)
      {
        AbstractDungeon.cardRewardScreen.open(this.cards, this, TEXT[4]);
        AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.COMBAT_REWARD;
      }
      return false;
    }
    logger.info("ERROR: Claim Reward() failed");
    return false;
  }
  
  public void render(SpriteBatch sb)
  {
    if (this.hb.hovered) {
      sb.setColor(new Color(0.4F, 0.6F, 0.6F, 1.0F));
    } else {
      sb.setColor(new Color(0.5F, 0.6F, 0.6F, 0.8F));
    }
    if (this.hb.clickStarted) {
      sb.draw(ImageMaster.REWARD_SCREEN_ITEM, Settings.WIDTH / 2.0F - 232.0F, this.y - 49.0F, 232.0F, 49.0F, 464.0F, 98.0F, Settings.scale * 0.98F, Settings.scale * 0.98F, 0.0F, 0, 0, 464, 98, false, false);
    } else {
      sb.draw(ImageMaster.REWARD_SCREEN_ITEM, Settings.WIDTH / 2.0F - 232.0F, this.y - 49.0F, 232.0F, 49.0F, 464.0F, 98.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 464, 98, false, false);
    }
    if (this.flashTimer != 0.0F)
    {
      sb.setColor(0.6F, 1.0F, 1.0F, this.flashTimer * 1.5F);
      sb.setBlendFunction(770, 1);
      sb.draw(ImageMaster.REWARD_SCREEN_ITEM, Settings.WIDTH / 2.0F - 232.0F, this.y - 49.0F, 232.0F, 49.0F, 464.0F, 98.0F, Settings.scale * 1.03F, Settings.scale * 1.15F, 0.0F, 0, 0, 464, 98, false, false);
      
      sb.setBlendFunction(770, 771);
    }
    sb.setColor(Color.WHITE);
    switch (this.type)
    {
    case CARD: 
      if (this.isBoss) {
        sb.draw(ImageMaster.REWARD_CARD_BOSS, REWARD_ITEM_X - 32.0F, this.y - 32.0F - 2.0F * Settings.scale, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
      } else {
        sb.draw(ImageMaster.REWARD_CARD_NORMAL, REWARD_ITEM_X - 32.0F, this.y - 32.0F - 2.0F * Settings.scale, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
      }
      break;
    case GOLD: 
      sb.draw(ImageMaster.UI_GOLD, REWARD_ITEM_X - 32.0F, this.y - 32.0F - 2.0F * Settings.scale, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
      
      break;
    case STOLEN_GOLD: 
      sb.draw(ImageMaster.UI_GOLD, REWARD_ITEM_X - 32.0F, this.y - 32.0F - 2.0F * Settings.scale, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
      
      break;
    case RELIC: 
      this.relic.renderOutline(sb);
      this.relic.render(sb);
      if (this.hb.hovered) {
        this.relic.renderTip(sb);
      }
      break;
    case POTION: 
      this.potion.renderLightOutline(sb);
      this.potion.render(sb);
      break;
    }
    if (this.hb.hovered)
    {
      Color color = Settings.GOLD_COLOR;
      FontHelper.renderSmartText(sb, FontHelper.rewardTipFont, this.text, REWARD_TEXT_X, this.y + 5.0F * Settings.scale, 1000.0F * Settings.scale, 0.0F, color);
    }
    else
    {
      Color color = Settings.CREAM_COLOR;
      FontHelper.renderSmartText(sb, FontHelper.rewardTipFont, this.text, REWARD_TEXT_X, this.y + 5.0F * Settings.scale, 1000.0F * Settings.scale, 0.0F, color);
    }
    if (!this.hb.hovered) {
      for (AbstractGameEffect e : this.effects) {
        e.render(sb);
      }
    }
    this.hb.render(sb);
  }
}
