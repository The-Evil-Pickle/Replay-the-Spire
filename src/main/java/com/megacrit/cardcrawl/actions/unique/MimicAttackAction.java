package com.megacrit.cardcrawl.actions.unique;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.mimic.MimicCopiedAttack;
import com.megacrit.cardcrawl.vfx.*;

import basemod.ReflectionHacks;

import com.megacrit.cardcrawl.core.*;
public class MimicAttackAction extends AbstractGameAction
{
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractMonster targetMonster;
    
    public MimicAttackAction(final AbstractMonster m) {
        this.duration = 0.0f;
        this.actionType = ActionType.WAIT;
        this.targetMonster = m;
    }
    
    @Override
    public void update() {
        if (this.targetMonster != null && (this.targetMonster.intent == AbstractMonster.Intent.ATTACK || this.targetMonster.intent == AbstractMonster.Intent.ATTACK_BUFF || this.targetMonster.intent == AbstractMonster.Intent.ATTACK_DEBUFF || this.targetMonster.intent == AbstractMonster.Intent.ATTACK_DEFEND)) {
            String moveName = this.targetMonster.moveName;
            String moveDesc = "Deal !D! damage";
            if (moveName == null) {
            	moveName = "Copied Attack";
            }
            final int intentBaseDmg = (int)ReflectionHacks.getPrivate((Object)this.targetMonster, (Class)AbstractMonster.class, "intentBaseDmg");
            int intentMultiAmt = 1;
            if ((boolean)ReflectionHacks.getPrivate((Object)this.targetMonster, (Class)AbstractMonster.class, "isMultiDmg")) {
            	intentMultiAmt = (int)ReflectionHacks.getPrivate((Object)this.targetMonster, (Class)AbstractMonster.class, "intentMultiAmt");
            	moveDesc += " !M! times";
            }
            int totalDmg = intentBaseDmg * intentMultiAmt;
            int cost = 0;
            if (totalDmg > 25) {
            	cost = 3;
            }
            else if (totalDmg > 15) {
            	cost = 2;
            }
            else if (totalDmg > 8) {
            	cost = 1;
            }
            int block = 0;
            if (this.targetMonster.intent == AbstractMonster.Intent.ATTACK_DEFEND) {
            	moveDesc += ". NL Gain !B! Block";
            	switch(cost) {
            		case 0:
            			block = 4;
            			break;
            		case 1:
            			block = Math.max(5, 15 - totalDmg);
            		case 2:
            			block = Math.max(5, 25 - totalDmg);
            		default:
            			block = 10;
            	}
            }
            moveDesc += ".";
            AbstractCard c = new MimicCopiedAttack(moveName, moveDesc, cost, intentBaseDmg, intentMultiAmt, block);
        }
        else {
            AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0f, SpotWeaknessAction.TEXT[0], true));
        }
        this.isDone = true;
    }
    
    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("OpeningAction");
        TEXT = MimicAttackAction.uiStrings.TEXT;
    }
}
