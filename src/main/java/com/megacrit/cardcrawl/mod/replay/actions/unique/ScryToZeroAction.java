package com.megacrit.cardcrawl.mod.replay.actions.unique;

import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;

public class ScryToZeroAction extends ScryAction
{
    
    public ScryToZeroAction(final int numCards) {
        super(numCards);
    }
    
    @Override
    public void update() {
        
    	if (AbstractDungeon.getMonsters().areMonstersBasicallyDead() == false && this.duration != Settings.ACTION_DUR_FAST && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
    		for (final AbstractCard c2 : AbstractDungeon.gridSelectScreen.selectedCards) {
    			if (c2.cost > 0)
    				c2.freeToPlayOnce = true;
    		}
    	}
        
        super.update();
    }
}
