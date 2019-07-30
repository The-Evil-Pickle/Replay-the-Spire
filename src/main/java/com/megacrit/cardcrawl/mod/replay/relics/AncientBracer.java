package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.cards.status.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.*;

public class AncientBracer
  extends AbstractRelic
{
  public static final String ID = "Replay:Antivirus Software";
  private static final int ARTIF = 1;
  
  public AncientBracer()
  {
    super(ID, "antivirusSoft.png", AbstractRelic.RelicTier.COMMON, AbstractRelic.LandingSound.CLINK);
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
		if (drawnCard.type == AbstractCard.CardType.STATUS && AbstractDungeon.player.hasPower("Artifact") && !AbstractDungeon.player.hasPower(RidThyselfOfStatusCardsPower.POWER_ID)) {
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
