package com.megacrit.cardcrawl.actions.unique;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import com.megacrit.cardcrawl.actions.common.*;

public class EclipseSecondAction extends AbstractGameAction
{
    private int count;
    private float startingDuration;
    
    public EclipseSecondAction(final int count) {
        this.count = count;
        this.actionType = ActionType.WAIT;
        this.attackEffect = AttackEffect.FIRE;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
    }
    
    @Override
    public void update() {
        if (this.duration == this.startingDuration) {
            for (int i = 0; i < count; ++i) {
	            AbstractCard.CardRarity rarity = AbstractCard.CardRarity.COMMON;
	  			int r = MathUtils.random(20);
	  			if (r == 20)
	  			{
	  				rarity = AbstractCard.CardRarity.RARE;
	  			} else {
	  				if (r >= 15) {
	  					rarity = AbstractCard.CardRarity.UNCOMMON;
	  				}
	  			}
	  			AbstractCard c = AbstractDungeon.getCard(rarity).makeCopy();
	  			if (c.costForTurn > 0)
	  			{
	  			c.cost -= 1;
	  			c.costForTurn -= 1;
	  			c.isCostModified = true;
	  			}
	  			c.current_x = (-1000.0F * Settings.scale);
	  	        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(c, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
            }
            /*
            if (this.refund > 0 ) {
            	AbstractDungeon.actionManager.addToTop(new GainEnergyAction(this.refund));
            }*/
        }
        this.tickDuration();
    }
}
