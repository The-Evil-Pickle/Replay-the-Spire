package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.badlogic.gdx.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.*;
import java.util.*;
import com.megacrit.cardcrawl.unlock.*;

public class ApplyPowerAction extends AbstractGameAction
{
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPower powerToApply;
    private float startingDuration;
    
    public ApplyPowerAction(final AbstractCreature abstractCreature, final AbstractCreature abstractCreature2, final AbstractPower powerToApply, final int n, final boolean b, final AbstractGameAction.AttackEffect attackEffect) {
        if (Settings.FAST_MODE) {
            this.startingDuration = 0.1f;
        }
        else if (b) {
            this.startingDuration = Settings.ACTION_DUR_FASTER;
        }
        else {
            this.startingDuration = Settings.ACTION_DUR_FAST;
        }
        this.setValues(abstractCreature, abstractCreature2, n);
        this.duration = this.startingDuration;
        this.powerToApply = powerToApply;
        if (AbstractDungeon.player.hasRelic("Snake Skull") && abstractCreature2 != null && abstractCreature2.isPlayer && abstractCreature != abstractCreature2 && powerToApply.ID.equals("Poison")) {
            AbstractDungeon.player.getRelic("Snake Skull").flash();
            final AbstractPower powerToApply2 = this.powerToApply;
            ++powerToApply2.amount;
            ++this.amount;
        }
        if (powerToApply.ID.equals("Corruption")) {
            for (final AbstractCard abstractCard : AbstractDungeon.player.hand.group) {
                if (abstractCard.type == AbstractCard.CardType.SKILL) {
                    abstractCard.modifyCostForCombat(-9);
                }
            }
            for (final AbstractCard abstractCard2 : AbstractDungeon.player.drawPile.group) {
                if (abstractCard2.type == AbstractCard.CardType.SKILL) {
                    abstractCard2.modifyCostForCombat(-9);
                }
            }
            for (final AbstractCard abstractCard3 : AbstractDungeon.player.discardPile.group) {
                if (abstractCard3.type == AbstractCard.CardType.SKILL) {
                    abstractCard3.modifyCostForCombat(-9);
                }
            }
            for (final AbstractCard abstractCard4 : AbstractDungeon.player.exhaustPile.group) {
                if (abstractCard4.type == AbstractCard.CardType.SKILL) {
                    abstractCard4.modifyCostForCombat(-9);
                }
            }
        }
        this.actionType = AbstractGameAction.ActionType.POWER;
        this.attackEffect = attackEffect;
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.duration = 0.0f;
            this.startingDuration = 0.0f;
            this.isDone = true;
        }
    }
    
    public ApplyPowerAction(final AbstractCreature abstractCreature, final AbstractCreature abstractCreature2, final AbstractPower abstractPower, final int n, final boolean b) {
        this(abstractCreature, abstractCreature2, abstractPower, n, b, AbstractGameAction.AttackEffect.NONE);
    }
    
    public ApplyPowerAction(final AbstractCreature abstractCreature, final AbstractCreature abstractCreature2, final AbstractPower abstractPower) {
        this(abstractCreature, abstractCreature2, abstractPower, -1);
    }
    
    public ApplyPowerAction(final AbstractCreature abstractCreature, final AbstractCreature abstractCreature2, final AbstractPower abstractPower, final int n) {
        this(abstractCreature, abstractCreature2, abstractPower, n, false, AbstractGameAction.AttackEffect.NONE);
    }
    
    public ApplyPowerAction(final AbstractCreature abstractCreature, final AbstractCreature abstractCreature2, final AbstractPower abstractPower, final int n, final AbstractGameAction.AttackEffect attackEffect) {
        this(abstractCreature, abstractCreature2, abstractPower, n, false, attackEffect);
    }
    
    public void update() {
        if (this.shouldCancelAction()) {
            this.isDone = true;
        }
        else {
            if (this.duration == this.startingDuration) {
                if (this.powerToApply instanceof NoDrawPower && this.target.hasPower(this.powerToApply.ID)) {
                    this.isDone = true;
                    return;
                }
                if (this.source != null) {
                    final Iterator<AbstractPower> iterator = (Iterator<AbstractPower>)this.source.powers.iterator();
                    while (iterator.hasNext()) {
                        iterator.next().onApplyPower(this.powerToApply, this.target, this.source);
                    }
                }
                if (AbstractDungeon.player.hasRelic("Champion Belt") && this.source != null && this.source.isPlayer && this.target != this.source && this.powerToApply.ID.equals("Vulnerable") && !this.target.hasPower("Artifact")) {
                    AbstractDungeon.player.getRelic("Champion Belt").onTrigger(this.target);
                }
                if (this.target instanceof AbstractMonster && this.target.isDeadOrEscaped()) {
                    this.duration = 0.0f;
                    this.isDone = true;
                    return;
                }
                if (this.target.hasPower("Artifact") && this.powerToApply.type == AbstractPower.PowerType.DEBUFF) {
                    AbstractDungeon.actionManager.addToTop((AbstractGameAction)new TextAboveCreatureAction(this.target, ApplyPowerAction.TEXT[0]));
                    this.duration -= Gdx.graphics.getDeltaTime();
                    CardCrawlGame.sound.play("NULLIFY_SFX");
                    this.target.getPower("Artifact").flashWithoutSound();
                    this.target.getPower("Artifact").onSpecificTrigger();
                    return;
                }
                if (AbstractDungeon.player.hasRelic("Ginger") && this.target.isPlayer && this.powerToApply.ID.equals("Weakened")) {
                    AbstractDungeon.player.getRelic("Ginger").flash();
                    AbstractDungeon.actionManager.addToTop((AbstractGameAction)new TextAboveCreatureAction(this.target, ApplyPowerAction.TEXT[1]));
                    this.duration -= Gdx.graphics.getDeltaTime();
                    return;
                }
                if (this.target.hasPower("Reflection") && !this.source.equals(this.target) && (this.powerToApply.ID.equals("Weakened") || this.powerToApply.ID.equals("Vulnerable"))) {
                    AbstractDungeon.player.getRelic("Mirror").flash();
                    AbstractDungeon.actionManager.addToTop((AbstractGameAction)new TextAboveCreatureAction(this.target, ApplyPowerAction.TEXT[4]));
                    if (this.powerToApply.ID.equals("Vulnerable")) {
                        AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new ApplyPowerAction(this.source, this.target, (AbstractPower)new VulnerablePower(this.source, this.amount + 1, false), this.amount + 1));
                    }
                    if (this.powerToApply.ID.equals("Weakened")) {
                        AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new ApplyPowerAction(this.source, this.target, (AbstractPower)new WeakPower(this.source, this.amount + 1, false), this.amount + 1));
                    }
                }
                if (AbstractDungeon.player.hasRelic("Nullstone Periapt") && this.target.isPlayer && this.source != null && !this.source.isPlayer && this.powerToApply.type == AbstractPower.PowerType.DEBUFF && AbstractDungeon.player.getRelic("Nullstone Periapt").counter == -1) {
                    AbstractDungeon.player.getRelic("Nullstone Periapt").setCounter(0);
                    AbstractDungeon.player.getRelic("Nullstone Periapt").flash();
                    AbstractDungeon.actionManager.addToTop((AbstractGameAction)new TextAboveCreatureAction(this.target, ApplyPowerAction.TEXT[2]));
                    this.duration -= Gdx.graphics.getDeltaTime();
                    return;
                }
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect));
                boolean b = false;
                for (final AbstractPower abstractPower : this.target.powers) {
                    if (abstractPower.ID.equals(this.powerToApply.ID) && !abstractPower.ID.equals("Night Terror")) {
                        abstractPower.stackPower(this.amount);
                        abstractPower.flash();
                        if ((abstractPower instanceof StrengthPower || abstractPower instanceof DexterityPower) && this.amount <= 0) {
                            AbstractDungeon.effectList.add(new PowerDebuffEffect(this.target.hb.cX - this.target.animX, this.target.hb.cY + this.target.hb.height / 2.0f, this.powerToApply.name + ApplyPowerAction.TEXT[3]));
                        }
                        else if (this.amount > 0) {
                            if (abstractPower.type != AbstractPower.PowerType.BUFF && !(abstractPower instanceof StrengthPower) && !(abstractPower instanceof DexterityPower)) {
                                AbstractDungeon.effectList.add(new PowerDebuffEffect(this.target.hb.cX - this.target.animX, this.target.hb.cY + this.target.hb.height / 2.0f, "+" + Integer.toString(this.amount) + " " + this.powerToApply.name));
                            }
                            else {
                                AbstractDungeon.effectList.add(new PowerBuffEffect(this.target.hb.cX - this.target.animX, this.target.hb.cY + this.target.hb.height / 2.0f, "+" + Integer.toString(this.amount) + " " + this.powerToApply.name));
                            }
                        }
                        else if (abstractPower.type == AbstractPower.PowerType.BUFF) {
                            AbstractDungeon.effectList.add(new PowerBuffEffect(this.target.hb.cX - this.target.animX, this.target.hb.cY + this.target.hb.height / 2.0f, this.powerToApply.name + ApplyPowerAction.TEXT[3]));
                        }
                        else {
                            AbstractDungeon.effectList.add(new PowerDebuffEffect(this.target.hb.cX - this.target.animX, this.target.hb.cY + this.target.hb.height / 2.0f, this.powerToApply.name + ApplyPowerAction.TEXT[3]));
                        }
                        abstractPower.updateDescription();
                        b = true;
                        AbstractDungeon.onModifyPower();
                    }
                }
                if (this.powerToApply.type == AbstractPower.PowerType.DEBUFF) {
                    this.target.useFastShakeAnimation(0.5f);
                }
                if (!b) {
                    this.target.powers.add(this.powerToApply);
                    Collections.sort(this.target.powers);
                    this.powerToApply.onInitialApplication();
                    this.powerToApply.flash();
                    if (this.amount < 0 && (this.powerToApply.ID.equals("Strength") || this.powerToApply.ID.equals("Dexterity"))) {
                        AbstractDungeon.effectList.add(new PowerDebuffEffect(this.target.hb.cX - this.target.animX, this.target.hb.cY + this.target.hb.height / 2.0f, this.powerToApply.name + ApplyPowerAction.TEXT[3]));
                    }
                    else if (this.powerToApply.type == AbstractPower.PowerType.BUFF) {
                        AbstractDungeon.effectList.add(new PowerBuffEffect(this.target.hb.cX - this.target.animX, this.target.hb.cY + this.target.hb.height / 2.0f, this.powerToApply.name));
                    }
                    else {
                        AbstractDungeon.effectList.add(new PowerDebuffEffect(this.target.hb.cX - this.target.animX, this.target.hb.cY + this.target.hb.height / 2.0f, this.powerToApply.name));
                    }
                    AbstractDungeon.onModifyPower();
                    if (this.target.isPlayer) {
                        int n = 0;
                        final Iterator<AbstractPower> iterator3 = (Iterator<AbstractPower>)this.target.powers.iterator();
                        while (iterator3.hasNext()) {
                            if (iterator3.next().type == AbstractPower.PowerType.BUFF) {
                                ++n;
                            }
                        }
                        if (n >= 10) {
                            UnlockTracker.unlockAchievement("POWERFUL");
                        }
                    }
                }
            }
            this.tickDuration();
        }
    }
    
    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("ApplyPowerAction");
        TEXT = ApplyPowerAction.uiStrings.TEXT;
    }
}
