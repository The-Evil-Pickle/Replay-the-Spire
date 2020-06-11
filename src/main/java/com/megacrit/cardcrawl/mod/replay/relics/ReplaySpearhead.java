package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
//import com.megacrit.cardcrawl.relics.LandingSound;
//import com.megacrit.cardcrawl.relics.RelicTier;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.*;

public class ReplaySpearhead extends AbstractRelic
{
    public static final String ID = "ReplayTheSpireMod:Spearhead";
    
    public ReplaySpearhead() {
        super(ID, "spearhead.png", RelicTier.COMMON, LandingSound.HEAVY);
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
        		AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(AbstractDungeon.player, m.getPower(VulnerablePower.POWER_ID).amount, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.FIRE));
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
