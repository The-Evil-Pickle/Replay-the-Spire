package com.megacrit.cardcrawl.mod.replay.actions.unique;

import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.utility.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.mod.replay.rooms.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
//import com.megacrit.cardcrawl.actions.ActionType;
//import com.megacrit.cardcrawl.actions.AttackEffect;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.unlock.*;

import org.apache.logging.log4j.*;

public class NecroPoisonLoseHpAction extends AbstractGameAction
{
    private static final Logger logger;
    private static final float DURATION = 0.33f;
    
    public NecroPoisonLoseHpAction(final AbstractCreature target, final AbstractCreature source, final int amount, final AttackEffect effect) {
        this.setValues(target, source, amount);
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = effect;
        this.duration = 0.33f;
    }
    
    @Override
    public void update() {
        if (AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) {
            this.isDone = true;
            return;
        }
        if (this.duration == 0.33f && this.target.currentHealth > 0) {
            NecroPoisonLoseHpAction.logger.info(this.target.name + " HAS " + this.target.currentHealth + " HP.");
            this.target.damageFlash = true;
            this.target.damageFlashFrames = 4;
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect));
        }
        this.tickDuration();
        if (this.isDone) {
            if (this.target.currentHealth > 0) {
                this.target.tint.color = Color.CHARTREUSE.cpy();
                this.target.tint.changeColor(Color.WHITE.cpy());
                this.target.damage(new DamageInfo(this.source, this.amount, DamageInfo.DamageType.HP_LOSS));
            }
            final AbstractPower p = this.target.getPower("Necrotic Poison");
            if (p != null) {
                final AbstractPower abstractPower = p;
				if (abstractPower.amount > 1) {
					abstractPower.amount -= ((abstractPower.amount + 1) / 2);
				} else {
					--abstractPower.amount;
				}
                if (p.amount <= 0) {
                    this.target.powers.remove(p);
                }
                else {
                    p.updateDescription();
                }
            }
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
            AbstractDungeon.actionManager.addToTop(new WaitAction(0.1f));
        }
    }
    
    static {
        logger = LogManager.getLogger(NecroPoisonLoseHpAction.class.getName());
    }
}