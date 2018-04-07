package com.megacrit.cardcrawl.potions;

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
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import java.util.ArrayList;
import java.util.TreeMap;

public class IronSkinPotion
  extends AbstractPotion
{
  public static final String POTION_ID = "Ironskin Potion";
  private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString("Ironskin Potion");
  public static final String NAME = potionStrings.NAME;
  public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;
  
  public IronSkinPotion()
  {
    super(NAME, "Ironskin Potion", AbstractPotion.PotionSize.L, AbstractPotion.PotionColor.BLUE);
    if (AbstractDungeon.ascensionLevel >= 11) {
      this.potency = 2;
    } else {
      this.potency = 3;
    }
    this.description = (DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1]);
    this.isThrown = false;
    this.tips.add(new PowerTip(this.name, this.description));
	//this.rarity = AbstractPotion.PotionRarity.UNCOMMON;
  }
  
  public void use(AbstractCreature target)
  {
    target = AbstractDungeon.player;
    if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
      AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, AbstractDungeon.player, new MetallicizePower(target, this.potency), this.potency));
    }
  }
  
  public AbstractPotion makeCopy()
  {
    return new IronSkinPotion();
  }
  
  public int getPrice() {
	  return 60;
  }
}
