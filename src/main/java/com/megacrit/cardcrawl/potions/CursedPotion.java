package com.megacrit.cardcrawl.potions;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.vfx.combat.*;
import replayTheSpire.*;
import java.util.ArrayList;

public class CursedPotion
  extends AbstractPotion
{
  public static final String POTION_ID = "Cursed Concoction";
  private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString("Cursed Concoction");
  public static final String NAME = potionStrings.NAME;
  public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;
  
  public CursedPotion()
  {
    super(NAME, "Cursed Concoction", PotionRarity.RARE, AbstractPotion.PotionSize.BOTTLE, AbstractPotion.PotionColor.GREEN);
    this.potency = this.getPotency();
    this.description = (DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1] + (this.potency - 1) + DESCRIPTIONS[2]);
    this.isThrown = false;
    this.tips.add(new PowerTip(this.name, this.description));
	//this.rarity = AbstractPotion.PotionRarity.RARE;
  }
  
    @Override
    public int getPotency(final int ascensionLevel) {
        return (ascensionLevel < 11) ? 3 : 2;
    }
  public void use(AbstractCreature target)
  {
    target = AbstractDungeon.player;
    //AbstractDungeon.actionManager.addToBottom(new HealAction(AbstractDungeon.player, AbstractDungeon.player, this.potency));
	AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new MakeTempCardInDrawPileAction(AbstractDungeon.returnRandomCurse().makeCopy(), 1, true, false));
	AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new MakeTempCardInDiscardAction(AbstractDungeon.returnRandomCurse().makeCopy(), 1));
    AbstractDungeon.effectList.add(new FlashAtkImgEffect(target.hb.cX, target.hb.cY, AbstractGameAction.AttackEffect.SHIELD));
    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, AbstractDungeon.player, new DexterityPower(target, this.potency - 1), this.potency - 1));
	if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, AbstractDungeon.player, new StrengthPower(target, this.potency), this.potency));
	}
  }
  
  public AbstractPotion makeCopy()
  {
    return new CursedPotion();
  }
  
}
