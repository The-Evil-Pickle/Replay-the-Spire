package com.megacrit.cardcrawl.mod.replay.cards.curses;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.actions.utility.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.SetDontTriggerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
//import com.megacrit.cardcrawl.cards.CardColor;
import com.megacrit.cardcrawl.cards.CardQueueItem;
//import com.megacrit.cardcrawl.cards.CardRarity;
//import com.megacrit.cardcrawl.cards.CardTarget;
//import com.megacrit.cardcrawl.cards.CardType;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.core.*;
import basemod.*;
import basemod.abstracts.*;
import java.util.ArrayList;

public class SpreadingInfection extends CustomCard
{
    public static final String ID = "Spreading Infection";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final int COST = -2;
	private ArrayList<AbstractCard> cardsToCopy;
    
    public SpreadingInfection() {
        super("Spreading Infection", SpreadingInfection.NAME, "cards/replay/betaCurse.png", -2, SpreadingInfection.DESCRIPTION, CardType.CURSE, CardColor.CURSE, CardRarity.CURSE, CardTarget.NONE);
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if (!this.dontTriggerOnUseCard && p.hasRelic("Blue Candle")) {
            this.useBlueCandle(p);
        }
        else {
            //AbstractDungeon.actionManager.addToBottom(new UseCardAction(this));
			for (AbstractCard c : this.cardsToCopy) {
				AbstractDungeon.actionManager.addToTop(new MakeTempCardInDiscardAction(c, 1));
			}
        }
    }
	
    @Override
    public AbstractCard makeCopy() {
        return new SpreadingInfection();
    }
	
    @Override
    public void triggerWhenDrawn() {
        AbstractDungeon.actionManager.addToBottom(new SetDontTriggerAction(this, false));
    }
	
    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
		this.cardsToCopy = new ArrayList<AbstractCard>();
		for (AbstractCard c : AbstractDungeon.player.hand.group) {
			if (((c.type == AbstractCard.CardType.STATUS) || (c.type == AbstractCard.CardType.CURSE)) && c != this) {
				this.cardsToCopy.add(c.makeCopy());
			}
		}
		for (AbstractCard c : AbstractDungeon.player.limbo.group) {
			if (((c.type == AbstractCard.CardType.STATUS) || (c.type == AbstractCard.CardType.CURSE)) && c != this) {
				this.cardsToCopy.add(c.makeCopy());
			}
		}
        this.dontTriggerOnUseCard = true;
        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, true));
    }
	
    @Override
    public void upgrade() {}
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Spreading Infection");
        NAME = SpreadingInfection.cardStrings.NAME;
        DESCRIPTION = SpreadingInfection.cardStrings.DESCRIPTION;
    }
}
