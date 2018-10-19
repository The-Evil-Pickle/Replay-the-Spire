package com.megacrit.cardcrawl.cards.replayxover.curses;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.SetDontTriggerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import chronomuncher.actions.SwitchAction;
import chronomuncher.cards.AbstractSelfSwitchCard;
import chronomuncher.cards.AbstractSelfSwitchCard.switchCard;
import chronomuncher.patches.Enum;

public class SwitchConfidence extends AbstractSelfSwitchCard
{
    public List<switchCard> switchListInherit;
    private boolean playMe;
    public SwitchConfidence(String switchID) {
        super("SwitchConfidence", "None", null, 0, "None", AbstractCard.CardType.CURSE, AbstractCard.CardColor.CURSE, AbstractCard.CardRarity.CURSE, AbstractCard.CardTarget.NONE, SwitchConfidence.class);
        this.playMe = false;
        this.switchListInherit = Arrays.asList(new switchCard(WaveringConfidence.ID, GreaterRegret.ID, -2, 0, 0, 0, 0, 0, 0, AbstractCard.CardType.CURSE, AbstractCard.CardTarget.SELF, false, false, false, false), new switchCard(GreaterRegret.ID, WaveringConfidence.ID, -2, 0, 0, 0, 0, 0, 0, AbstractCard.CardType.CURSE, AbstractCard.CardTarget.SELF, false, false, false, false));
        for (switchCard c : this.switchListInherit) {
        	c.portraitImg = "cards/replay/betaCurse.png";
        }
        if (switchID == null) {
            switchID = WaveringConfidence.ID;
        }
        this.switchList = this.switchListInherit;
        if (this.currentID != null) {
            this.switchTo(this.currentID);
        }
        else {
            this.switchTo(switchID);
        }
        this.tags.add(Enum.SWITCH_CARD);
    }

    private void determineMagicNumber() {
    	if (AbstractDungeon.player == null) {
    		this.baseMagicNumber = 1;
    	} else {
    		if (this.playMe) {
        		this.baseMagicNumber = fibbonacci(AbstractDungeon.player.hand.size());
        	} else {
        		this.baseMagicNumber = 0;
        	}
    	}
    	this.magicNumber = this.baseMagicNumber;
    	this.initializeDescription();
    }
    public static int fibbonacci(final int position) {
        return (position > 1) ? (fibbonacci(position - 1) + fibbonacci(position - 2)) : position;
    }
    
    public SwitchConfidence() {
        this(null);
    }
    
    public void switchTo(String switchID) {
    	super.switchTo(switchID);
    	this.playMe = switchID.equals(GreaterRegret.ID);
    	this.determineMagicNumber();
    }

    @Override
    public void atTurnStart() {
        this.retain = !this.playMe;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if (!this.dontTriggerOnUseCard && p.hasRelic("Blue Candle")) {
            this.useBlueCandle(p);
        }
        else {
            AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, this.magicNumber, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
            AbstractDungeon.actionManager.addToBottom(new SwitchAction(this));
        }
    }
    
    @Override
    public void triggerWhenDrawn() {
    	this.determineMagicNumber();
        AbstractDungeon.actionManager.addToBottom(new SetDontTriggerAction(this, false));
    }
    @Override
    public void triggerOnOtherCardPlayed(final AbstractCard c) {
    	this.determineMagicNumber();
    }
    @Override
    public void applyPowers() {
    	this.determineMagicNumber();
    	super.applyPowers();
    }
    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        if (this.playMe) {
        	this.dontTriggerOnUseCard = true;
            AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, true));
        } else {
        	this.retain = true;
        	AbstractDungeon.actionManager.addToBottom(new SwitchAction(this));
        }
    }
}
