package com.megacrit.cardcrawl.cards.curses;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;
import basemod.*;
import basemod.abstracts.*;

public class SpreadingInfection extends CustomCard
{
    public static final String ID = "Spreading Infection";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final int COST = -2;
    
    public SpreadingInfection() {
        super("Spreading Infection", SpreadingInfection.NAME, "cards/replay/betaCurse.png", -2, SpreadingInfection.DESCRIPTION, CardType.CURSE, CardColor.CURSE, CardRarity.CURSE, CardTarget.NONE);
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if (p.hasRelic("Blue Candle")) {
            this.useBlueCandle(p);
        }
        else {
            //AbstractDungeon.actionManager.addToBottom(new UseCardAction(this));
			for (AbstractCard c : AbstractDungeon.player.hand.group) {
				if (((c.type == AbstractCard.CardType.STATUS) || (c.type == AbstractCard.CardType.CURSE)) && c != this) {
					AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(c.makeCopy(), 1));
				}
			}
			for (AbstractCard c : AbstractDungeon.player.limbo.group) {
				if (((c.type == AbstractCard.CardType.STATUS) || (c.type == AbstractCard.CardType.CURSE)) && c != this) {
					AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(c.makeCopy(), 1));
				}
			}
        }
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new SpreadingInfection();
    }
    
    @Override
    public void upgrade() {}
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Spreading Infection");
        NAME = SpreadingInfection.cardStrings.NAME;
        DESCRIPTION = SpreadingInfection.cardStrings.DESCRIPTION;
    }
}
