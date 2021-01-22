package com.megacrit.cardcrawl.mod.replay.cards.replayxover.downfall;

import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;

import automaton.cardmods.EncodeMod;
import automaton.cards.AbstractBronzeCard;
import basemod.helpers.CardModifierManager;

public class M_BronzeSurvivor extends AbstractBronzeCard
{
    public static final String ID;
    
    public M_BronzeSurvivor() {
        super(M_BronzeSurvivor.ID, 1, AbstractCard.CardType.SKILL, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.SELF, AbstractCard.CardColor.GREEN);
        this.baseBlock = 8;
        this.baseMagicNumber = 1;
        this.magicNumber = 1;
        CardModifierManager.addModifier(this, new EncodeMod());
    }
    
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    	this.atb(new GainBlockAction(p, p, this.block));
        this.atb(new DiscardAction(p, p, 1, false));
    }
    
    @Override
    public void onCompile(final AbstractCard function, final boolean forGameplay) {
    	if (forGameplay) {
            this.applyToSelf(new DrawCardNextTurnPower(AbstractDungeon.player, this.magicNumber));
        }
    }
    
    @Override
    public void upp() {
        this.upgradeBlock(3);
    }
    
    static {
        ID = "m_BronzeSurvivor";
    }
}