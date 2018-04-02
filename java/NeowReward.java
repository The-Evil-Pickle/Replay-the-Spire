package com.megacrit.cardcrawl.gashapon;

import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.OverlayMenu;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.PotionPlaceholder;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;
import com.megacrit.cardcrawl.relics.NeowsLament;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.screens.saveAndContinue.SaveAndContinue;
import com.megacrit.cardcrawl.screens.saveAndContinue.SaveFile;
import com.megacrit.cardcrawl.screens.saveAndContinue.SaveFile.SaveType;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import com.megacrit.cardcrawl.screens.select.BossRelicSelectScreen;
import com.megacrit.cardcrawl.ui.buttons.CancelButton;
import com.megacrit.cardcrawl.vfx.GameSavedEffect;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NeowReward
{
  private static final Logger logger = LogManager.getLogger(NeowReward.class.getName());
  private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString("Neow Reward");
  public static final String[] NAMES = characterStrings.NAMES;
  public static final String[] TEXT = characterStrings.TEXT;
  public static final String[] UNIQUE_REWARDS = characterStrings.UNIQUE_REWARDS;
  public String optionLabel = "";
  public NeowRewardType type;
  public NeowRewardDrawback drawback = NeowRewardDrawback.NONE;
  private boolean activated = false;
  private int hp_bonus = 0;
  private boolean cursed = false;
  private static final int GOLD_BONUS = 100;
  private static final int LARGE_GOLD_BONUS = 250;
  
  public static enum NeowRewardType
  {
    THREE_CARDS,  ONE_RANDOM_RARE_CARD,  REMOVE_CARD,  UPGRADE_CARD,  TRANSFORM_CARD,  THREE_SMALL_POTIONS,  RANDOM_COMMON_RELIC,  TEN_PERCENT_HP_BONUS,  HUNDRED_GOLD,  THREE_ENEMY_KILL,  REMOVE_TWO,  TRANSFORM_TWO_CARDS,  ONE_RARE_RELIC,  THREE_RARE_CARDS,  THREE_HUGE_POTIONS,  TWO_FIFTY_GOLD,  TWENTY_PERCENT_HP_BONUS,  BOSS_RELIC, COLORLESS_CARD;
    
    private NeowRewardType() {}
  }
  
  public static enum NeowRewardDrawback
  {
    NONE,  TEN_PERCENT_HP_LOSS,  NO_GOLD,  CURSE,  LOSE_STARTER_RELIC,  FIFTY_PERCENT_DAMAGE;
    
    private NeowRewardDrawback() {}
  }
  
  public NeowReward(int category)
  {
    this.hp_bonus = ((int)(AbstractDungeon.player.maxHealth * 0.1F));
    switch (category)
    {
    case 0: 
      rollCardReward();
      break;
    case 1: 
      rollNonCardReward();
      break;
    case 2: 
      rollChaosDrawback();
      rollChaosReward();
      break;
    case 3: 
      rollUniqueReward();
    }
  }
  
  private void rollCardReward()
  {
    switch (NeowEvent.rng.random(0, 5))
    {
    case 0: 
      this.type = NeowRewardType.THREE_CARDS;
      this.optionLabel += TEXT[0];
      break;
    case 1: 
      this.type = NeowRewardType.ONE_RANDOM_RARE_CARD;
      this.optionLabel += TEXT[1];
      break;
    case 2: 
      this.type = NeowRewardType.REMOVE_CARD;
      this.optionLabel += TEXT[2];
      break;
    case 3: 
      this.type = NeowRewardType.UPGRADE_CARD;
      this.optionLabel += TEXT[3];
      break;
	case 4:
	  this.type = NeowRewardType.COLORLESS_CARD;
	  this.optionLabel += "[ #gObtain #ga #grandom #gcolorless #gCard ]";
	  break;
    default: 
      this.type = NeowRewardType.TRANSFORM_CARD;
      this.optionLabel += TEXT[4];
    }
  }
  
  private void rollNonCardReward()
  {
    switch (NeowEvent.rng.random(1, 5))
    {
    case 1: 
      this.type = NeowRewardType.THREE_SMALL_POTIONS;
      this.optionLabel += TEXT[5];
      break;
    case 2: 
      this.type = NeowRewardType.RANDOM_COMMON_RELIC;
      this.optionLabel += TEXT[6];
      break;
    case 3: 
      this.type = NeowRewardType.TEN_PERCENT_HP_BONUS;
      this.optionLabel = (this.optionLabel + TEXT[7] + this.hp_bonus + " ]");
      break;
    case 4: 
      this.type = NeowRewardType.THREE_ENEMY_KILL;
      this.optionLabel += TEXT[28];
      break;
    default: 
      this.type = NeowRewardType.HUNDRED_GOLD;
      this.optionLabel = (this.optionLabel + TEXT[8] + 100 + TEXT[9]);
    }
  }
  
  private void rollChaosReward()
  {
    ArrayList<NeowRewardType> possibleRewards = new ArrayList();
    possibleRewards.add(NeowRewardType.REMOVE_TWO);
    possibleRewards.add(NeowRewardType.ONE_RARE_RELIC);
    possibleRewards.add(NeowRewardType.THREE_RARE_CARDS);
    possibleRewards.add(NeowRewardType.TWO_FIFTY_GOLD);
    possibleRewards.add(NeowRewardType.TRANSFORM_TWO_CARDS);
    possibleRewards.add(NeowRewardType.TWENTY_PERCENT_HP_BONUS);
    switch (this.drawback)
    {
    case TEN_PERCENT_HP_LOSS: 
      possibleRewards.remove(NeowRewardType.TWENTY_PERCENT_HP_BONUS);
      break;
    case NO_GOLD: 
      possibleRewards.remove(NeowRewardType.TWO_FIFTY_GOLD);
      break;
    case CURSE: 
      possibleRewards.remove(NeowRewardType.REMOVE_TWO);
      break;
    }
    NeowRewardType reward = (NeowRewardType)possibleRewards.get(NeowEvent.rng.random(0, possibleRewards.size() - 1));
    switch (reward)
    {
    case REMOVE_TWO: 
      this.type = NeowRewardType.REMOVE_TWO;
      this.optionLabel += TEXT[10];
      break;
    case ONE_RARE_RELIC: 
      this.type = NeowRewardType.ONE_RARE_RELIC;
      this.optionLabel += TEXT[11];
      break;
    case THREE_RARE_CARDS: 
      this.type = NeowRewardType.THREE_RARE_CARDS;
      this.optionLabel += TEXT[12];
      break;
    case TWO_FIFTY_GOLD: 
      this.type = NeowRewardType.TWO_FIFTY_GOLD;
      this.optionLabel = (this.optionLabel + TEXT[13] + 250 + TEXT[14]);
      break;
    case TRANSFORM_TWO_CARDS: 
      this.type = NeowRewardType.TRANSFORM_TWO_CARDS;
      this.optionLabel += TEXT[15];
      break;
    case TWENTY_PERCENT_HP_BONUS: 
      this.type = NeowRewardType.TWENTY_PERCENT_HP_BONUS;
      this.optionLabel = (this.optionLabel + TEXT[16] + this.hp_bonus * 2 + " ]");
      break;
    default: 
      this.type = NeowRewardType.ONE_RARE_RELIC;
      this.optionLabel += TEXT[11];
    }
  }
  
  private void rollChaosDrawback()
  {
    switch (NeowEvent.rng.random(0, 3))
    {
    case 0: 
      this.drawback = NeowRewardDrawback.TEN_PERCENT_HP_LOSS;
      this.optionLabel = (this.optionLabel + TEXT[17] + this.hp_bonus + TEXT[18]);
      break;
    case 1: 
      this.drawback = NeowRewardDrawback.NO_GOLD;
      this.optionLabel += TEXT[19];
      break;
    case 2: 
      this.drawback = NeowRewardDrawback.CURSE;
      this.optionLabel += TEXT[20];
      break;
    case 3: 
      this.drawback = NeowRewardDrawback.FIFTY_PERCENT_DAMAGE;
      this.optionLabel += TEXT[21];
      break;
    }
  }
  
  private void rollUniqueReward()
  {
    switch (NeowEvent.rng.random(0, 2))
    {
    }
    this.type = NeowRewardType.BOSS_RELIC;
    this.optionLabel += UNIQUE_REWARDS[0];
  }
  
  public void update()
  {
    if (this.activated)
    {
      if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())
      {
        switch (this.type)
        {
        case UPGRADE_CARD: 
          AbstractCard c = (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0);
          c.upgrade();
          AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy()));
          AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
          
          break;
        case REMOVE_CARD: 
          CardCrawlGame.sound.play("CARD_EXHAUST");
          AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(
          
            (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0), Settings.WIDTH / 2, Settings.HEIGHT / 2));
          
          AbstractDungeon.player.masterDeck.removeCard(
            (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0));
          break;
        case REMOVE_TWO: 
          CardCrawlGame.sound.play("CARD_EXHAUST");
          AbstractCard c1 = (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0);
          AbstractCard c2 = (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(1);
          AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c1, Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH / 2.0F - 30.0F * Settings.scale, Settings.HEIGHT / 2));
          
          AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c2, Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH / 2.0F + 30.0F * Settings.scale, Settings.HEIGHT / 2.0F));
          
          AbstractDungeon.player.masterDeck.removeCard(c1);
          AbstractDungeon.player.masterDeck.removeCard(c2);
          break;
        case TRANSFORM_CARD: 
          AbstractDungeon.transformCard((AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0));
          AbstractDungeon.player.masterDeck.removeCard(
            (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0));
          AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(
          
            AbstractDungeon.getTransformedCard(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
          
          break;
        case TRANSFORM_TWO_CARDS: 
          AbstractCard t1 = (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0);
          AbstractCard t2 = (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(1);
          AbstractDungeon.player.masterDeck.removeCard(t1);
          AbstractDungeon.player.masterDeck.removeCard(t2);
          
          AbstractDungeon.transformCard(t1);
          AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(
          
            AbstractDungeon.getTransformedCard(), Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH / 2.0F - 30.0F * Settings.scale, Settings.HEIGHT / 2.0F));
          
          AbstractDungeon.transformCard(t2);
          AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(
          
            AbstractDungeon.getTransformedCard(), Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH / 2.0F + 30.0F * Settings.scale, Settings.HEIGHT / 2.0F));
          
          break;
        }
        AbstractDungeon.gridSelectScreen.selectedCards.clear();
        AbstractDungeon.overlayMenu.cancelButton.hide();
        if (!Settings.isDailyRun)
        {
          SaveFile saveFile = new SaveFile(SaveFile.SaveType.POST_NEOW);
          SaveAndContinue.save(saveFile);
          AbstractDungeon.effectList.add(new GameSavedEffect());
        }
        this.activated = false;
      }
      if (this.cursed)
      {
        this.cursed = (!this.cursed);
        AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(
        
          AbstractDungeon.getCardWithoutRng(AbstractCard.CardRarity.CURSE), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
      }
    }
  }
  
  public void activate()
  {
    this.activated = true;
    switch (this.type)
    {
    case THREE_RARE_CARDS: 
      AbstractDungeon.cardRewardScreen.open(getRewardCards(true), null, TEXT[22]);
      break;
    case HUNDRED_GOLD: 
      CardCrawlGame.sound.play("GOLD_JINGLE");
      AbstractDungeon.player.gainGold(100);
      break;
    case ONE_RANDOM_RARE_CARD: 
      AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(
      
        AbstractDungeon.getCard(AbstractCard.CardRarity.RARE, NeowEvent.rng), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
      
      break;
    case RANDOM_COMMON_RELIC: 
      AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, 
      
        AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.COMMON));
      break;
    case ONE_RARE_RELIC: 
      AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, 
      
        AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.RARE));
      break;
    case BOSS_RELIC: 
      AbstractDungeon.player.loseRelic(((AbstractRelic)AbstractDungeon.player.relics.get(0)).relicId);
      AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, 
      
        AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.BOSS));
      break;
    case THREE_ENEMY_KILL: 
      AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, new NeowsLament());
      
      break;
    case REMOVE_CARD: 
      AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck, 1, TEXT[23], false, false, false, true);
      
      break;
    case REMOVE_TWO: 
      AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck, 2, TEXT[24], false, false, false, false);
      
      break;
    case TEN_PERCENT_HP_BONUS: 
      AbstractDungeon.player.increaseMaxHp(this.hp_bonus, true);
      break;
    case THREE_CARDS: 
      AbstractDungeon.cardRewardScreen.open(
        getRewardCards(false), null, 
        
        CardCrawlGame.languagePack.getUIString("CardRewardScreen").TEXT[1]);
      break;
    case THREE_HUGE_POTIONS: 
      CardCrawlGame.sound.play("POTION_1");
      for (int i = 0; i < 3; i++) {
        getPotion(PotionHelper.getRandomPotion(NeowEvent.rng));
      }
      break;
    case THREE_SMALL_POTIONS: 
      CardCrawlGame.sound.play("POTION_1");
      for (int i = 0; i < 3; i++) {
        getPotion(PotionHelper.getRandomPotion(NeowEvent.rng));
      }
      break;
    case TRANSFORM_CARD: 
      AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck, 1, TEXT[25], false, true, false, false);
      
      break;
    case TRANSFORM_TWO_CARDS: 
      AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck, 2, TEXT[26], false, false, false, false);
      
      break;
    case TWENTY_PERCENT_HP_BONUS: 
      AbstractDungeon.player.increaseMaxHp(this.hp_bonus * 2, true);
      break;
    case TWO_FIFTY_GOLD: 
      CardCrawlGame.sound.play("GOLD_JINGLE");
      AbstractDungeon.player.gainGold(250);
      break;
    case UPGRADE_CARD: 
      AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck
        .getUpgradableCards(), 1, TEXT[27], true, false, false, false);
      
      break;
	case COLORLESS_CARD:
		AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(
      
        AbstractDungeon.returnTrulyRandomColorlessCard(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
		
		break;
    }
    switch (this.drawback)
    {
    case CURSE: 
      this.cursed = true;
      break;
    case NONE: 
      break;
    case NO_GOLD: 
      AbstractDungeon.player.loseGold(AbstractDungeon.player.gold);
      break;
    case TEN_PERCENT_HP_LOSS: 
      AbstractDungeon.player.decreaseMaxHealth(this.hp_bonus);
      break;
    case FIFTY_PERCENT_DAMAGE: 
      AbstractDungeon.player.damage(new DamageInfo(null, AbstractDungeon.player.maxHealth / 2, DamageInfo.DamageType.HP_LOSS));
      
      break;
    }
  }
  
  private void getPotion(AbstractPotion p)
  {
    for (int i = 0; i < 3; i++) {
      if ((AbstractDungeon.player.potions[i] instanceof PotionPlaceholder))
      {
        p.moveInstantly(AbstractDungeon.player.potions[i].currentX, AbstractDungeon.player.potions[i].currentY);
        p.isObtained = true;
        p.isDone = true;
        p.isAnimating = false;
        p.flash();
        AbstractDungeon.player.potions[i] = p;
        break;
      }
    }
  }
  
  public ArrayList<AbstractCard> getRewardCards(boolean rareOnly)
  {
    ArrayList<AbstractCard> retVal = new ArrayList();
    
    int numCards = 3;
    AbstractCard.CardRarity rarity;
    for (int i = 0; i < numCards; i++)
    {
      rarity = rollRarity();
      if (rareOnly) {
        rarity = AbstractCard.CardRarity.RARE;
      }
      AbstractCard card = null;
      switch (rarity)
      {
      case RARE: 
        card = getCard(rarity);
        break;
      case UNCOMMON: 
        card = getCard(rarity);
        break;
      case COMMON: 
        card = getCard(rarity);
        break;
      default: 
        logger.info("WTF?");
      }
      int dupeCount = 0;
      while (retVal.contains(card)) {
        if (dupeCount < 4) {
          card = getCard(rarity);
        } else {
          switch (rarity)
          {
          case RARE: 
            card = getCard(AbstractCard.CardRarity.UNCOMMON);
            break;
          case UNCOMMON: 
            card = getCard(AbstractCard.CardRarity.COMMON);
            break;
          case COMMON: 
            card = getCard(AbstractCard.CardRarity.UNCOMMON);
            break;
          default: 
            card = getCard(AbstractCard.CardRarity.COMMON);
          }
        }
      }
      retVal.add(card);
    }
    ArrayList<AbstractCard> retVal2 = new ArrayList();
    for (AbstractCard c : retVal) {
      retVal2.add(c.makeCopy());
    }
    return retVal2;
  }
  
  public AbstractCard.CardRarity rollRarity()
  {
    if (NeowEvent.rng.randomBoolean(0.33F)) {
      return AbstractCard.CardRarity.UNCOMMON;
    }
    return AbstractCard.CardRarity.COMMON;
  }
  
  public AbstractCard getCard(AbstractCard.CardRarity rarity)
  {
    switch (rarity)
    {
    case RARE: 
      return AbstractDungeon.rareCardPool.getRandomCard(NeowEvent.rng);
    case UNCOMMON: 
      return AbstractDungeon.uncommonCardPool.getRandomCard(NeowEvent.rng);
    case COMMON: 
      return AbstractDungeon.commonCardPool.getRandomCard(NeowEvent.rng);
    }
    logger.info("Error in getCard in Neow Reward");
    return null;
  }
}
