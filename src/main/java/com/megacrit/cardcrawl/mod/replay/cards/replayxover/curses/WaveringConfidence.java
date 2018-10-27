package com.megacrit.cardcrawl.mod.replay.cards.replayxover.curses;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.SetDontTriggerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
//import com.megacrit.cardcrawl.cards.CardColor;
import com.megacrit.cardcrawl.cards.CardQueueItem;
//import com.megacrit.cardcrawl.cards.CardRarity;
//import com.megacrit.cardcrawl.cards.CardTarget;
//import com.megacrit.cardcrawl.cards.CardType;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.*;

import basemod.abstracts.CustomCard;
import basemod.helpers.TooltipInfo;
import replayTheSpire.ReplayTheSpireMod;

import com.megacrit.cardcrawl.dungeons.*;

import java.util.ArrayList;
import java.util.List;

import com.megacrit.cardcrawl.core.*;

public class WaveringConfidence extends CustomCard
{
    public static final String ID = "ReplayTheSpireMod:Wavering Confidence";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final int COST = -2;
    public ArrayList<TooltipInfo> tips;
    
    public WaveringConfidence() {
        super(ID, WaveringConfidence.NAME, "cards/replay/betaCurse.png", COST, WaveringConfidence.DESCRIPTION, CardType.CURSE, CardColor.CURSE, CardRarity.CURSE, CardTarget.NONE);
        this.baseMagicNumber = 0;
        this.magicNumber = this.baseMagicNumber;
        this.tips = new ArrayList<TooltipInfo>();
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        this.tips.clear();
        if (!ReplayTheSpireMod.foundmod_deciple) {
            this.tips.add(new TooltipInfo("Fibonacci", "Fibonacci effects will increase depending on the size of your hand along the fibonacci sequence: 1, 1, 2, 3, 5, 8, 13, 21, 34, 55..."));
        }
        return this.tips;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if (!this.dontTriggerOnUseCard && p.hasRelic("Blue Candle")) {
            this.useBlueCandle(p);
        }
        else {
        	AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, this.magicNumber, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
        }
    }
    public void atTurnStart() {
    	this.determineMagicNumber();
    }
    public void triggerOnOtherCardPlayed(final AbstractCard c) {
    	this.determineMagicNumber();
    }
    private void determineMagicNumber() {
    	this.baseMagicNumber = fibbonacci(AbstractDungeon.player.hand.size());
    	this.magicNumber = this.baseMagicNumber;
    	this.initializeDescription();
    }
    public static int fibbonacci(final int position) {
        return (position > 1) ? (fibbonacci(position - 1) + fibbonacci(position - 2)) : position;
    }
    @Override
    public void triggerWhenDrawn() {
    	this.determineMagicNumber();
        AbstractDungeon.actionManager.addToBottom(new SetDontTriggerAction(this, false));
    }
    public void applyPowers() {
    	this.determineMagicNumber();
        super.applyPowers();
        this.initializeDescription();
    }
    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        this.dontTriggerOnUseCard = true;
        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, true));
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new WaveringConfidence();
    }
    
    @Override
    public void upgrade() {
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = WaveringConfidence.cardStrings.NAME;
        DESCRIPTION = WaveringConfidence.cardStrings.DESCRIPTION;
    }
}
