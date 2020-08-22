package com.megacrit.cardcrawl.mod.replay.actions.unique;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;

import java.util.*;

public class LoomingThreatPowerAction extends AbstractGameAction
{
    private int damageAmount;
    
    public LoomingThreatPowerAction(final int damageAmount) {
        this.damageAmount = damageAmount;
    }
    
    @Override
    public void update() {
        for (final AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.selfRetain || c.retain) {
                AbstractDungeon.actionManager.addToTop(new DamageRandomEnemyAction(new DamageInfo(source, damageAmount, DamageType.THORNS), AttackEffect.BLUNT_LIGHT));
            }
        }
        this.isDone = true;
    }
}
