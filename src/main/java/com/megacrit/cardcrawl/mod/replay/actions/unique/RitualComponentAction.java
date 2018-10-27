package com.megacrit.cardcrawl.mod.replay.actions.unique;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
//import com.megacrit.cardcrawl.actions.ActionType;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.*;

public class RitualComponentAction extends AbstractGameAction
{
	private int dmg;
    
    public RitualComponentAction(final int dmg) {
        this.duration = 0.0f;
        this.actionType = ActionType.WAIT;
		this.dmg = dmg;
    }
    
    @Override
    public void update() {
        if (AbstractDungeon.player.drawPile.isEmpty()) {
            this.isDone = true;
            return;
        }
        final AbstractCard card = AbstractDungeon.player.drawPile.getTopCard();
        if (card.type == AbstractCard.CardType.ATTACK) {
            //AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.blockGain));
			AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, this.dmg, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
        }
        this.isDone = true;
    }
}
