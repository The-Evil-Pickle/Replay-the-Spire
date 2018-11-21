package com.megacrit.cardcrawl.mod.replay.cards.curses;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAndDeckAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.SetDontTriggerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
//import com.megacrit.cardcrawl.cards.CardColor;
import com.megacrit.cardcrawl.cards.CardQueueItem;
//import com.megacrit.cardcrawl.cards.CardRarity;
//import com.megacrit.cardcrawl.cards.CardTarget;
//import com.megacrit.cardcrawl.cards.CardType;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.core.*;
import basemod.*;
import basemod.abstracts.*;

public class Voices extends CustomCard implements StartupCard
{
    public static final String ID = "Voices";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final int COST = -2;
    
    public Voices() {
        super("Voices", Voices.NAME, "cards/replay/voices.png", -2, Voices.DESCRIPTION, CardType.CURSE, CardColor.CURSE, CardRarity.CURSE, CardTarget.NONE);
		this.exhaust = true;
		this.isEthereal = true;
		this.baseMagicNumber = 2;
		this.magicNumber = this.baseMagicNumber;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if (!this.dontTriggerOnUseCard && p.hasRelic("Blue Candle")) {
            this.useBlueCandle(p);
        }
        else {
			//AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Dazed(), this.magicNumber));
			//AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(this, AbstractDungeon.player.limbo));
			//AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(this, AbstractDungeon.player.discardPile));
            //AbstractDungeon.actionManager.addToBottom(new SetDontTriggerAction(this, false));
        }
    }
    /*
    @Override
    public void triggerWhenDrawn() {
        AbstractDungeon.actionManager.addToBottom(new SetDontTriggerAction(this, false));
    }
    
    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        this.dontTriggerOnUseCard = true;
        //AbstractDungeon.actionManager.addToBottom(new PlayWithoutDiscardingAction(this));
		AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, true));
    }
    */
    @Override
    public AbstractCard makeCopy() {
        return new Voices();
    }
    
    @Override
    public void upgrade() {
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Voices");
        NAME = Voices.cardStrings.NAME;
        DESCRIPTION = Voices.cardStrings.DESCRIPTION;
    }

	@Override
	public boolean atBattleStartPreDraw() {
		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAndDeckAction(this.makeCopy()));
		return true;
	}
}
