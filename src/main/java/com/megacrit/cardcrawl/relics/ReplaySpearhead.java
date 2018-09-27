package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.actions.*;

public class ReplaySpearhead extends AbstractRelic
{
    public static final String ID = "ReplayTheSpireMod:Spearhead";
    
    public ReplaySpearhead() {
        super(ID, "betaRelic.png", RelicTier.UNCOMMON, LandingSound.HEAVY);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    
    @Override
    public void onPlayerEndTurn() {
        boolean triggered = false;
        for (final AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
        	if (m.hasPower(VulnerablePower.POWER_ID)) {
        		AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(null, m.getPower(VulnerablePower.POWER_ID).amount, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.FIRE));
        		AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(m, this));
        		triggered = true;
        	}
        }
        if (triggered) {
        	this.flash();
        }
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new ReplaySpearhead();
    }
}
