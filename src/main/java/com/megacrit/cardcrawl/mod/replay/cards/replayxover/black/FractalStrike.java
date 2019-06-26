package com.megacrit.cardcrawl.mod.replay.cards.replayxover.black;

import infinitespire.abstracts.*;

import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.powers.NoDrawPower;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.mod.replay.actions.unique.HaulAction;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.core.*;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;

public class FractalStrike extends BlackCard
{
    private static final String ID = "Replay:Fractal Strike";
    private static final CardStrings cardStrings;
    private static final String NAME;
    private static final int COST = 0;
    private static final String DESCRIPTION;
    
    public FractalStrike() {
        super(ID, NAME, "cards/replay/fractal_strike.png", COST, DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCard.CardTarget.ENEMY);
        ExhaustiveVariable.setBaseValue(this, 2);
        this.baseDamage = 2;
        this.damage = this.baseDamage;
        this.tags.add(AbstractCard.CardTags.STRIKE);
    }
    
    public AbstractCard makeCopy() {
        return new FractalStrike();
    }
    
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            ExhaustiveVariable.upgrade(this, 1);
            this.upgradeDamage(3);
        }
    }
    
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    	AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    	AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 1));
    	AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new FractalStrike(), 1));
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = FractalStrike.cardStrings.NAME;
        DESCRIPTION = FractalStrike.cardStrings.DESCRIPTION;
    }
}
