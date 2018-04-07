package com.megacrit.cardcrawl.potions;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PotionStrings;
import java.util.ArrayList;

public class HealthPotion
  extends AbstractPotion
{
  public static final String POTION_ID = "Health Potion";
  private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString("Health Potion");
  public static final String NAME = potionStrings.NAME;
  public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;
  
  public HealthPotion()
  {
    super(NAME, "Health Potion", AbstractPotion.PotionSize.T, AbstractPotion.PotionColor.GREEN);
    if (AbstractDungeon.ascensionLevel >= 11) {
      this.potency = 9;
    } else {
      this.potency = 15;
    }
    this.description = (DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1]);
    this.isThrown = false;
    this.tips.add(new PowerTip(this.name, this.description));
	//this.rarity = AbstractPotion.PotionRarity.SHOP;
  }
  
  public void use(AbstractCreature target)
  {
    AbstractDungeon.actionManager.addToBottom(new HealAction(target, AbstractDungeon.player, this.potency));
  }
  
  public AbstractPotion makeCopy()
  {
    return new HealthPotion();
  }
  
  
  public int getPrice()
  {
	return 40;
  }
  
}
