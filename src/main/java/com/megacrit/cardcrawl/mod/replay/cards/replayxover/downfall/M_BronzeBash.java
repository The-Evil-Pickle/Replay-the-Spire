package com.megacrit.cardcrawl.mod.replay.cards.replayxover.downfall;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.ReplayGainShieldingAction;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.powers.*;

import automaton.cardmods.EncodeMod;
import automaton.cards.AbstractBronzeCard;
import basemod.helpers.CardModifierManager;

public class M_BronzeBash extends AbstractBronzeCard
{
    public static final String ID;
    
    public M_BronzeBash() {
        super(M_BronzeBash.ID, 2, AbstractCard.CardType.ATTACK, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.ENEMY, AbstractCard.CardColor.RED);
        this.baseDamage = 8;
        this.baseMagicNumber = 2;
        this.magicNumber = 2;
        this.baseBlock = 6;
        CardModifierManager.addModifier(this, new EncodeMod());
    }
    
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        this.atb(new ApplyPowerAction(m, p, new VulnerablePower(m, this.magicNumber, false), this.magicNumber));
    }
    
    @Override
    public void onCompile(final AbstractCard function, final boolean forGameplay) {
    	if (forGameplay) {
            this.atb(new ReplayGainShieldingAction(AbstractDungeon.player, AbstractDungeon.player, this.block));
        }
    }
    
    @Override
    public void upp() {
        this.upgradeMagicNumber(1);
        this.upgradeBlock(2);
    }
    
    static {
        ID = "m_Bronzebash";
    }
}