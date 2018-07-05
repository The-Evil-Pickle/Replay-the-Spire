package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.actions.common.*;
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
