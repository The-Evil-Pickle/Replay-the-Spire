package com.megacrit.cardcrawl.mod.replay.cards.replayxover.downfall;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import automaton.cardmods.EncodeMod;
import automaton.cards.AbstractBronzeCard;
import basemod.helpers.CardModifierManager;

public class M_BronzeAgony extends AbstractBronzeCard
{
    public static final String ID;
    
    public M_BronzeAgony() {
        super(M_BronzeAgony.ID, 0, AbstractCard.CardType.ATTACK, AbstractCard.CardRarity.SPECIAL, AbstractCard.CardTarget.ENEMY, AbstractCard.CardColor.GREEN);
        this.baseDamage = 4;
        CardModifierManager.addModifier(this, new EncodeMod());
    }
    
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    	this.atb(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
    }

    @Override
    public void triggerWhenDrawn() {
        this.addToTop(new MakeTempCardInHandAction(this.makeStatEquivalentCopy()));
    }
    
    @Override
    public void upp() {
        this.upgradeDamage(2);
    }
    
    static {
        ID = "m_BronzeAgony";
    }
}