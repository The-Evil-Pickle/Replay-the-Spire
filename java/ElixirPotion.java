package com.megacrit.cardcrawl.potions;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import java.util.ArrayList;
import java.util.TreeMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ElixirPotion
  extends AbstractPotion
{
  private static final Logger logger = LogManager.getLogger(ElixirPotion.class.getName());
  public static final String POTION_ID = "Elixir";
  private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString("Elixir");
  public static final String NAME = potionStrings.NAME;
  public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;
  
  public ElixirPotion()
  {
    super(NAME, "Elixir", PotionRarity.COMMON, AbstractPotion.PotionSize.T, AbstractPotion.PotionColor.ELIXIR);
    this.description = DESCRIPTIONS[0];
	this.potency = this.getPotency();
    if (this.potency < 2) {
      this.description = DESCRIPTIONS[1];
    }
    this.isThrown = false;
    this.tips.add(new PowerTip(this.name, this.description));
    this.tips.add(new PowerTip(
    
      TipHelper.capitalize(GameDictionary.EXHAUST.NAMES[0]), 
      (String)GameDictionary.keywords.get(GameDictionary.EXHAUST.NAMES[0])));
    this.tips.add(new PowerTip(
    
      TipHelper.capitalize(GameDictionary.STATUS.NAMES[0]), 
      (String)GameDictionary.keywords.get(GameDictionary.STATUS.NAMES[0])));
    this.tips.add(new PowerTip(
    
      TipHelper.capitalize(GameDictionary.CURSE.NAMES[0]), 
      (String)GameDictionary.keywords.get(GameDictionary.CURSE.NAMES[0])));
	//this.rarity = AbstractPotion.PotionRarity.SHOP;
  }
  
    @Override
    public int getPotency(final int ascensionLevel) {
        return (ascensionLevel < 11) ? 2 : 1;
    }
  public void use(AbstractCreature target)
  {
	if (this.potency <= 1) {
		ArrayList<AbstractCard> handCopy = new ArrayList();
		for (AbstractCard c : AbstractDungeon.player.hand.group) {
		  if ((c.type == AbstractCard.CardType.STATUS) || (c.type == AbstractCard.CardType.CURSE)) {
			handCopy.add(c);
		  }
		}
		if (handCopy.isEmpty()) {
		  AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, DESCRIPTIONS[3], true));
		}
		if (!handCopy.isEmpty())
		{
		  for (AbstractCard c : handCopy) {
			AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
		  }
		  handCopy.clear();
		  logger.info("REMOVING ALL STATUSES FROM HAND...");
		}
	} else {
		ArrayList<AbstractCard> handCopy = new ArrayList();
		for (AbstractCard c : AbstractDungeon.player.hand.group) {
		  if ((c.type == AbstractCard.CardType.STATUS) || (c.type == AbstractCard.CardType.CURSE)) {
			handCopy.add(c);
		  }
		}
		ArrayList<AbstractCard> deckCopy = new ArrayList();
		for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
		  if ((c.type == AbstractCard.CardType.STATUS) || (c.type == AbstractCard.CardType.CURSE)) {
			deckCopy.add(c);
		  }
		}
		ArrayList<AbstractCard> discardCopy = new ArrayList();
		for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
		  if ((c.type == AbstractCard.CardType.STATUS) || (c.type == AbstractCard.CardType.CURSE)) {
			discardCopy.add(c);
		  }
		}
		if (handCopy.isEmpty() && deckCopy.isEmpty() && discardCopy.isEmpty()) {
		  AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, DESCRIPTIONS[3], true));
		}
		else
		{
		  for (AbstractCard c : handCopy) {
			AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
		  }
		  handCopy.clear();
		  for (AbstractCard c : deckCopy) {
			AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.drawPile));
		  }
		  deckCopy.clear();
		  for (AbstractCard c : discardCopy) {
			AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.discardPile));
		  }
		  discardCopy.clear();
		}
	}
  }
  
  public AbstractPotion makeCopy()
  {
    return new ElixirPotion();
  }
  
  
  public int getPrice()
  {
	return 35;
  }
}