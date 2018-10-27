package com.megacrit.cardcrawl.mod.replay.actions.unique;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class GremCookAction extends AbstractGameAction
{
	
	int flexAmt;
    public GremCookAction(final AbstractCreature source, final int amount, final int flexAmt) {
        this.duration = 0.5f;
        this.source = source;
        this.amount = amount;
        this.flexAmt = flexAmt;
        this.actionType = ActionType.HEAL;
    }
    
    @Override
    public void update() {
        if (this.duration == 0.5f) {
            final ArrayList<AbstractMonster> validMonsters = new ArrayList<AbstractMonster>();
            for (final AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                if (m != this.source && m.intent != AbstractMonster.Intent.ESCAPE && !m.isDying && m.currentHealth < m.maxHealth) {
                    validMonsters.add(m);
                }
            }
            if (!validMonsters.isEmpty()) {
                this.target = validMonsters.get(AbstractDungeon.aiRng.random(validMonsters.size() - 1));
            }
            else {
            	for (final AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                    if (m != this.source && m.intent != AbstractMonster.Intent.ESCAPE && !m.isDying) {
                        validMonsters.add(m);
                    }
                }
            	if (!validMonsters.isEmpty()) {
                    this.target = validMonsters.get(AbstractDungeon.aiRng.random(validMonsters.size() - 1));
                }
                else {
                	this.target = this.source;
                }
            }
            if (this.target != null) {
                this.target.heal(this.amount);
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.target, this.source, new StrengthPower(this.target, this.flexAmt), this.flexAmt));
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.target, this.source, new DelayedLoseStrengthPower(this.target, this.flexAmt), this.flexAmt));
            }
        }
        this.tickDuration();
    }
}

