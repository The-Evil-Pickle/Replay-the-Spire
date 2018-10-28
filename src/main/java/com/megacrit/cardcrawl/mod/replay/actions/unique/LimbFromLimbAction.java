package com.megacrit.cardcrawl.mod.replay.actions.unique;
import com.evacipated.cardcrawl.mod.stslib.actions.common.RefundAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
//import com.megacrit.cardcrawl.actions.ActionType;
//import com.megacrit.cardcrawl.actions.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.vfx.combat.*;
public class LimbFromLimbAction extends AbstractGameAction
{
    private int damageThreshold;
    private DamageInfo info;
    private static final float DURATION = 0.1f;
    private AbstractCard card;
    
    public LimbFromLimbAction(final AbstractCreature target, final DamageInfo info, final AbstractCard card) {
        this.setValues(target, this.info = info);
        this.card = card;
        this.damageThreshold = card.magicNumber;
        this.actionType = ActionType.DAMAGE;
        this.duration = 0.1f;
    }
    
    @Override
    public void update() {
        if (this.duration == 0.1f && this.target != null) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.SLASH_VERTICAL));
            int startHp = this.target.currentHealth;
            this.target.damage(this.info);
            int damageDealt = startHp - this.target.currentHealth;
            if (damageDealt >= this.damageThreshold) {
            	AbstractDungeon.actionManager.addToTop(new DrawCardAction(AbstractDungeon.player, 2));
            	AbstractDungeon.actionManager.addToTop(new RefundAction(this.card, 2));
            }
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }
        this.tickDuration();
    }
}
