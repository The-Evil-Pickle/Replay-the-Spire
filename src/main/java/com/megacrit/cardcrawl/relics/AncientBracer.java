package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.status.*;

public class AncientBracer
  extends AbstractRelic
{
  public static final String ID = "Ancient Bracer";
  private static final int ARTIF = 1;
  
  public AncientBracer()
  {
    super("Ancient Bracer", "antivirusSoft.png", AbstractRelic.RelicTier.COMMON, AbstractRelic.LandingSound.CLINK);
  }
  
  public String getUpdatedDescription()
  {
    return this.DESCRIPTIONS[0];// + AncientBracer.ARTIF + this.DESCRIPTIONS[1];
  }
  
    @Override
    public void onShuffle() {
        ++this.counter;
        if (this.counter == AncientBracer.ARTIF) {
            this.counter = 0;
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ArtifactPower(AbstractDungeon.player, 1), 1));
        }
    }
	/*
  public void atBattleStart()
  {
    flash();
    AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ArtifactPower(AbstractDungeon.player, AncientBracer.ARTIF), AncientBracer.ARTIF));
  }
  */
    @Override
    public void onVictory() {
        this.pulse = false;
    }
    @Override
    public void onEquip() {
        this.counter = 0;
    }
    @Override
    public void atTurnStart() {
        this.pulse = false;
    }
	
	@Override
	public void onCardDraw(AbstractCard drawnCard) {
		if (drawnCard.type == AbstractCard.CardType.STATUS && AbstractDungeon.player.hasPower("Artifact")) {
			if (!this.pulse) {
				AbstractDungeon.player.getPower("Artifact").onSpecificTrigger();
				this.pulse = true;
				this.flash();
			}
            this.flash();
			AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(drawnCard, AbstractDungeon.player.hand));
			if (drawnCard.cardID.equals("Void")) {
				AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
			}
			AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 1));
		}
		
	}
	
  public AbstractRelic makeCopy()
  {
    return new AncientBracer();
  }
}
