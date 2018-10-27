package com.megacrit.cardcrawl.mod.replay.potions;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.mod.replay.powers.DoomedPower;
import com.megacrit.cardcrawl.potions.AbstractPotion;
//import com.megacrit.cardcrawl.potions.PotionRarity;

import java.util.ArrayList;
import java.util.TreeMap;

public class DoomPotion
  extends AbstractPotion
{
  public static final String POTION_ID = "Doom Potion";
  private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString("Doom Potion");
  public static final String NAME = potionStrings.NAME;
  public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;
  
  public DoomPotion()
  {
    super(NAME, "Doom Potion", PotionRarity.RARE, AbstractPotion.PotionSize.SPHERE, AbstractPotion.PotionColor.GREEN);
    this.potency = this.getPotency();
    this.description = (DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1]);
    this.isThrown = true;
    this.targetRequired = true;
    this.tips.add(new PowerTip(this.name, this.description));
	//this.rarity = AbstractPotion.PotionRarity.ULTRA;
  }
  
  public void use(AbstractCreature target)
  {
    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, AbstractDungeon.player, new DoomedPower(target, this.potency), this.potency));
  }
  
  public AbstractPotion makeCopy()
  {
    return new DoomPotion();
  }
  
	@Override
	public int getPotency(final int ascensionLevel) {
		return 10;//(ascensionLevel < 11) ? 10 : 13;
	}
	
}