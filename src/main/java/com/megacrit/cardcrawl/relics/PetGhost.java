package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import basemod.BaseMod;

public class PetGhost
  extends AbstractRelic
{
  public static final String ID = "Pet Ghost";
  public static final float RARE_CHANCE = 0.3F;
  
  public PetGhost()
  {
    super("Pet Ghost", "lives.png", AbstractRelic.RelicTier.COMMON, AbstractRelic.LandingSound.HEAVY);
  }
  
  public String getUpdatedDescription()
  {
    return this.DESCRIPTIONS[0];
  }
  
  public void onEnergyRecharge() 
  {
	AbstractCard c;
	switch (AbstractDungeon.cardRandomRng.random(0, 2)) {
		case 0:
			c = new GhostSwipe();
			break;
		case 1:
			c = new GhostDefend();
			break;
		default:
			boolean hasBasic = false;
			if (AbstractDungeon.player != null) {				
				for (final AbstractCard c2 : AbstractDungeon.player.drawPile.group) {
					if (c2.rarity == AbstractCard.CardRarity.BASIC || c2.cardID == "Ghost Defend" || c2.cardID == "Ghost Swipe") {
						hasBasic = true;
					}
				}
			}
			if (hasBasic) {
				c = new GhostFetch();
			} else {
				if (AbstractDungeon.cardRandomRng.randomBoolean()){
					c = new GhostSwipe();
				} else {
					c = new GhostDefend();
				}
			}
	}
	AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c, false));
    AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
  }

  @Override
  public void onEquip() {
	  BaseMod.MAX_HAND_SIZE++;
  }
  @Override
  public void onUnequip() {
	  BaseMod.MAX_HAND_SIZE--;
  }
  
  public AbstractRelic makeCopy()
  {
    return new PetGhost();
  }
}
