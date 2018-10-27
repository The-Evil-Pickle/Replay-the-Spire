package com.megacrit.cardcrawl.mod.replay.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
//import com.megacrit.cardcrawl.actions.ActionType;
//import com.megacrit.cardcrawl.actions.AttackEffect;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.actions.utility.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.mod.replay.relics.*;
import com.megacrit.cardcrawl.vfx.combat.*;
import com.badlogic.gdx.graphics.*;

import java.util.*;

public class SearingRingAction extends AbstractGameAction
{
    private boolean firstFrame;
    private boolean firstTurn;
    public SearingRingAction(final AbstractCreature source, final DamageInfo.DamageType type, final AttackEffect effect, final boolean firstTurn) {
        this.firstFrame = true;
        this.setValues(null, source);
        this.actionType = ActionType.DAMAGE;
        this.damageType = type;
        this.attackEffect = effect;
        this.duration = Settings.ACTION_DUR_FAST;
		this.firstTurn = firstTurn;
    }
    
    @Override
    public void update() {
        if (this.firstFrame) {
            boolean playedMusic = false;
            for (int temp = AbstractDungeon.getCurrRoom().monsters.monsters.size(), i = 0; i < temp; ++i) {
                if (!AbstractDungeon.getCurrRoom().monsters.monsters.get(i).isDying && AbstractDungeon.getCurrRoom().monsters.monsters.get(i).currentHealth > 0 && !AbstractDungeon.getCurrRoom().monsters.monsters.get(i).isEscaping) {
                    if (playedMusic) {
                        AbstractDungeon.effectList.add(new FlashAtkImgEffect(AbstractDungeon.getCurrRoom().monsters.monsters.get(i).hb.cX, AbstractDungeon.getCurrRoom().monsters.monsters.get(i).hb.cY, this.attackEffect, true));
                    }
                    else {
                        playedMusic = true;
                        AbstractDungeon.effectList.add(new FlashAtkImgEffect(AbstractDungeon.getCurrRoom().monsters.monsters.get(i).hb.cX, AbstractDungeon.getCurrRoom().monsters.monsters.get(i).hb.cY, this.attackEffect));
                    }
                }
            }
            this.firstFrame = false;
        }
        this.tickDuration();
        if (this.isDone) {
			if (this.firstTurn == false && AbstractDungeon.player.currentHealth > (AbstractDungeon.player.maxHealth * RingOfSearing.HP_FLOOR_PLAYER) / 100) {
				AbstractDungeon.actionManager.addToTop(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, (RingOfSearing.HP_LOSS_PLAYER), AbstractGameAction.AttackEffect.FIRE));
			}
            for (int temp2 = AbstractDungeon.getCurrRoom().monsters.monsters.size(), j = 0; j < temp2; ++j) {
                if (!AbstractDungeon.getCurrRoom().monsters.monsters.get(j).isDeadOrEscaped() && AbstractDungeon.getCurrRoom().monsters.monsters.get(j).currentHealth > AbstractDungeon.getCurrRoom().monsters.monsters.get(j).maxHealth / 2) {
                    if (this.attackEffect == AttackEffect.POISON) {
                        AbstractDungeon.getCurrRoom().monsters.monsters.get(j).tint.color = Color.CHARTREUSE.cpy();
                        AbstractDungeon.getCurrRoom().monsters.monsters.get(j).tint.changeColor(Color.WHITE.cpy());
                    }
                    else if (this.attackEffect == AttackEffect.FIRE) {
                        AbstractDungeon.getCurrRoom().monsters.monsters.get(j).tint.color = Color.RED.cpy();
                        AbstractDungeon.getCurrRoom().monsters.monsters.get(j).tint.changeColor(Color.WHITE.cpy());
                    }
                    AbstractDungeon.getCurrRoom().monsters.monsters.get(j).damage(new DamageInfo(this.source, (AbstractDungeon.getCurrRoom().monsters.monsters.get(j).currentHealth / 10), this.damageType));
                }
            }
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
            AbstractDungeon.actionManager.addToTop(new WaitAction(0.1f));
        }
    }
}
