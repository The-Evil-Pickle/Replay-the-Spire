package com.megacrit.cardcrawl.cards.curses;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.status.*;
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

public class Overencumbered extends CustomCard
{
    public static final String ID = "Overencumbered";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final int COST = -2;
    
    public Overencumbered() {
        super("Overencumbered", Overencumbered.NAME, "cards/replay/betaCurse.png", -2, Overencumbered.DESCRIPTION, CardType.CURSE, CardColor.CURSE, CardRarity.CURSE, CardTarget.NONE);
		this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if (p.hasRelic("Blue Candle")) {
            this.useBlueCandle(p);
        }
        else {
            AbstractDungeon.actionManager.addToBottom(new UseCardAction(this));
        }
    }
	
    @Override
    public void triggerWhenDrawn() {
		super.triggerWhenDrawn();
		AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(new Dazed(), this.magicNumber));
		AbstractDungeon.actionManager.addToTop(new DrawCardAction(AbstractDungeon.player, 1));
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new Overencumbered();
    }
    
    @Override
    public void upgrade() {
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Overencumbered");
        NAME = Overencumbered.cardStrings.NAME;
        DESCRIPTION = Overencumbered.cardStrings.DESCRIPTION;
    }
}
