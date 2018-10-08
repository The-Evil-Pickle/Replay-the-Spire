package com.megacrit.cardcrawl.cards.curses;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;

public class MirrorMistCurse extends CustomCard
{
    public static final String ID = "MirrorMistCurse";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final int COST = -2;
    
    public MirrorMistCurse() {
        super("MirrorMistCurse", MirrorMistCurse.NAME, "cards/replay/betaCurse.png", -2, MirrorMistCurse.DESCRIPTION, CardType.CURSE, CardColor.CURSE, CardRarity.SPECIAL, CardTarget.NONE);
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
    public AbstractCard makeCopy() {
        return new MirrorMistCurse();
    }
    
    @Override
    public void upgrade() {
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("MirrorMistCurse");
        NAME = MirrorMistCurse.cardStrings.NAME;
        DESCRIPTION = MirrorMistCurse.cardStrings.DESCRIPTION;
    }
}
