package com.megacrit.cardcrawl.mod.replay.cards.replayxover.curses;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import constructmod.patches.*;
import com.megacrit.cardcrawl.dungeons.*;

import java.util.*;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.*;
import constructmod.*;
import constructmod.actions.CycleCardAction;
import constructmod.cards.AbstractCycleCard;
import constructmod.cards.PanicFire;
import constructmod.characters.TheConstruct;

import com.megacrit.cardcrawl.core.*;

public class MeltdownSequence extends AbstractCycleCard implements StartupCard
{
    public static final String ID;
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    private static final int COST = -2;
    public static final int OVERHEAT = 3;
    public static final int OVERHEAT_CONSTRUCT = 5;
    public static final int LAGGED = 2;
    public static final int LAGGED_CONSTRUCT = 1;
    public static final int EXHAUSTIVE = 3;
    public static final int EXHAUSTIVE_CONSTRUCT = 2;
    
    public MeltdownSequence() {
        super(MeltdownSequence.ID, MeltdownSequence.NAME, "cards/replay/betaCurse.png", COST, MeltdownSequence.DESCRIPTION, AbstractCard.CardType.CURSE, AbstractCard.CardColor.CURSE, AbstractCard.CardRarity.CURSE, AbstractCard.CardTarget.SELF, 1);
        if (AbstractDungeon.player == null || !(AbstractDungeon.player instanceof TheConstruct)) {
        	//this.overheat = OVERHEAT;
            this.baseMagicNumber = LAGGED;
            ExhaustiveVariable.setBaseValue(this, EXHAUSTIVE);
        } else {
        	this.overheat = OVERHEAT_CONSTRUCT;
            this.baseMagicNumber = LAGGED_CONSTRUCT;
            ExhaustiveVariable.setBaseValue(this, EXHAUSTIVE_CONSTRUCT);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
        this.magicNumber = this.baseMagicNumber;
    }
    
    public boolean atBattleStartPreDraw() {
    	AbstractCard c = new Lagged();
    	c.upgrade();
    	AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(c, this.magicNumber, true, true));
    	return true;
    }
    
    private void doEffect() {
    	AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Lagged(), 1));
    	ExhaustiveVariable.increment(this);
    	if (this.exhaust) {
    		AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(this, AbstractDungeon.player.discardPile));
    		AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
    		AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(this, AbstractDungeon.player.drawPile));
    	}
    	//AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new VulnerablePower(AbstractDungeon.player, 1, false), 1));
    }
    @Override
    public void triggerWhenDrawn() {
        if (!this.canCycle()) {
            return;
        }
        super.triggerWhenDrawn();
        this.flash();
        this.doEffect();
    }
    
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    	if ((p.hasRelic("Blue Candle")))
        {
          useBlueCandle(p);
        }
        else
        {
        	this.doEffect();
        }
    }
    
    public AbstractCard makeCopy() {
        return (AbstractCard)new MeltdownSequence();
    }
    
    public void upgrade() {}
    
    static {
        ID = "ReplayTheSpireMod:Meltdown Sequence";
        cardStrings = CardCrawlGame.languagePack.getCardStrings(MeltdownSequence.ID);
        NAME = MeltdownSequence.cardStrings.NAME;
        DESCRIPTION = MeltdownSequence.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = MeltdownSequence.cardStrings.UPGRADE_DESCRIPTION;
    }
}
