package com.megacrit.cardcrawl.mod.replay.cards.replayxover.curses;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import ThMod_FnH.ThMod;

import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.core.*;
import basemod.abstracts.*;

public class Sluggish extends CustomCard
{
    public static final String ID = "Replay:Sluggish";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final int COST = 0;
    
    public Sluggish() {
        super(ID, Sluggish.NAME, "cards/replay/betaCurse.png", Sluggish.COST, Sluggish.DESCRIPTION, CardType.CURSE, CardColor.CURSE, CardRarity.CURSE, CardTarget.SELF);
        ExhaustiveVariable.setBaseValue(this, 3);
    }
    @Override
    public boolean canUse(final AbstractPlayer p, final AbstractMonster m) {
        return this.cardPlayable(m) && this.hasEnoughEnergy();
    }
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if (ThMod.Amplified(this, 1)) {
        	AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 1));
        	ExhaustiveVariable.increment(this);
        }
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new Sluggish();
    }
    
    @Override
    public void upgrade() {}
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = Sluggish.cardStrings.NAME;
        DESCRIPTION = Sluggish.cardStrings.DESCRIPTION;
    }
}
