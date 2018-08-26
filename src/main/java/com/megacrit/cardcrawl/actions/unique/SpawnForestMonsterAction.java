package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.daily.*;
import com.megacrit.cardcrawl.powers.*;

public class SpawnForestMonsterAction extends AbstractGameAction
{
    private boolean used;
    private static final float DURATION = 0.1f;
    private AbstractMonster m;
    private boolean minion;
    private int targetSlot;
    
    public SpawnForestMonsterAction(final AbstractMonster m, final boolean isMinion) {
        this(m, isMinion, -99);
    }
    
    public SpawnForestMonsterAction(final AbstractMonster m, final boolean isMinion, final int slot) {
        this.used = false;
        this.actionType = ActionType.SPECIAL;
        this.duration = 0.1f;
        this.m = m;
        this.minion = isMinion;
        this.targetSlot = slot;
        if (AbstractDungeon.player.hasRelic("Philosopher's Stone")) {
            m.addPower(new StrengthPower(m, 2));
        }
    }
    
    @Override
    public void update() {
        if (!this.used) {
            this.m.init();
            this.m.applyPowers();
            if (this.targetSlot < 0) {
                AbstractDungeon.getCurrRoom().monsters.addSpawnedMonster(this.m);
            }
            else {
                AbstractDungeon.getCurrRoom().monsters.addMonster(this.targetSlot, this.m);
            }
            this.m.showHealthBar();
            if (DailyMods.negativeMods.get("Lethality")) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.m, this.m, new StrengthPower(this.m, 3), 3));
            }
            if (DailyMods.negativeMods.get("Time Dilation")) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.m, this.m, new SlowPower(this.m, 0)));
            }
            if (this.minion) {
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.m, this.m, new MinionPower(this.m)));
            }
            this.m.usePreBattleAction();
            this.used = true;
        }
        this.tickDuration();
    }
}
