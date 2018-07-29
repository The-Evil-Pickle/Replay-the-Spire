package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LanguidPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class FromAllSidesAction extends AbstractGameAction
{
    private boolean freeToPlayOnce;
    private int damage;
    private AbstractPlayer p;
    private DamageInfo.DamageType damageTypeForTurn;
    private int energyOnUse;
    private int bonus;
    
    public FromAllSidesAction(final AbstractPlayer p, final int damage, final DamageInfo.DamageType damageTypeForTurn, final boolean freeToPlayOnce, final int energyOnUse, final int bonus) {
        this.freeToPlayOnce = false;
        this.energyOnUse = -1;
        this.p = p;
        this.damage = damage;
        this.bonus = bonus;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.damageTypeForTurn = damageTypeForTurn;
        this.energyOnUse = energyOnUse;
    }
    
    @Override
    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }
        effect *= 2;
        effect += bonus;
        if (this.p.hasRelic("Chemical X")) {
            effect += 2;
            this.p.getRelic("Chemical X").flash();
        }
        if (effect > 0) {
            for (int i = 0; i < effect; ++i) {
            	AbstractMonster m = AbstractDungeon.getMonsters().getRandomMonster(true);
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(this.p, this.damage, this.damageTypeForTurn), AttackEffect.BLUNT_LIGHT));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, this.p, new WeakPower(m, 1, false), 1));
            }
            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }
        this.isDone = true;
    }
}
