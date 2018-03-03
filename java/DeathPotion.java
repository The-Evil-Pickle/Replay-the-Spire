package com.megacrit.cardcrawl.potions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PotionStrings;
import java.util.ArrayList;

public class DeathPotion
  extends AbstractPotion
{
  public static final String POTION_ID = "Death Potion";
  private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString("Death Potion");
  public static final String NAME = potionStrings.NAME;
  public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;
  
  private int secondPotency = 15;
  
  public DeathPotion()
  {
    super(NAME, "Death Potion", AbstractPotion.PotionSize.L, AbstractPotion.PotionColor.STRENGTH);
    if (AbstractDungeon.ascensionLevel >= 11) {
      this.potency = 30;
	  this.secondPotency = 18;
    } else {
      this.potency = 35;
	  this.secondPotency = 15;
    }
    this.description = (DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1] + this.secondPotency + DESCRIPTIONS[2]);
    this.isThrown = true;
    this.targetRequired = true;
    this.tips.add(new PowerTip(this.name, this.description));
  }
  
  public void use(AbstractCreature target)
  {
    DamageInfo info = new DamageInfo(AbstractDungeon.player, this.potency, DamageInfo.DamageType.THORNS);
    info.applyEnemyPowersOnly(target);
	AbstractDungeon.actionManager.addToBottom(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, this.secondPotency));
    AbstractDungeon.actionManager.addToBottom(new DamageAction(target, info, AbstractGameAction.AttackEffect.FIRE));
  }
  
  public AbstractPotion makeCopy()
  {
    return new DeathPotion();
  }
}
