package com.megacrit.cardcrawl.potions;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.powers.*;
import ReplayTheSpireMod.*;
import java.util.ArrayList;

public class LanguidPotion
  extends AbstractPotion
{
  public static final String POTION_ID = "Languid Potion";
  private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString("Languid Potion");
  public static final String NAME = potionStrings.NAME;
  public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;
  
  private int secondPotency = 2;
  
  public LanguidPotion()
  {
    super(NAME, "Languid Potion", PotionRarity.UNCOMMON, AbstractPotion.PotionSize.JAR, AbstractPotion.PotionColor.POISON);
	this.potency = this.getPotency();
    this.description = (DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1]);// + DESCRIPTIONS[1] + (this.secondPotency - 1) + DESCRIPTIONS[2] + this.secondPotency + DESCRIPTIONS[3]
    this.isThrown = true;
    this.tips.add(new PowerTip(this.name, this.description));
    /*this.tips.add(new PowerTip(
      TipHelper.capitalize("necrotic poison"), 
      (String)GameDictionary.keywords.get("necrotic poison")));*/
	//this.rarity = AbstractPotion.PotionRarity.RARE;
  }
  
  public void use(AbstractCreature target)
  {
    for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
      if (!m.isDeadOrEscaped()) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new LanguidPower(m, this.potency, false), this.potency));
      }
    }
	
	//AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new VulnerablePower(AbstractDungeon.player, this.secondPotency - 1, false), this.secondPotency - 1));
	//AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FrailPower(AbstractDungeon.player, this.secondPotency, false), this.secondPotency));
  }
  @Override
    public int getPotency(final int ascensionLevel) {
        return (ascensionLevel < 11) ? 5 : 4;
    }
  public AbstractPotion makeCopy()
  {
    return new LanguidPotion();
  }
  
  
}