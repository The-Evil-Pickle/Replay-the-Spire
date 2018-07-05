package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.events.*;
import com.megacrit.cardcrawl.monsters.replay.*;
import com.megacrit.cardcrawl.core.*;

public class ForestEventAction extends AbstractGameAction
{
	
	private boolean hasBuilt;
	public static FadingForestBoss forest;
	
    public ForestEventAction() {
		this.hasBuilt = false;
    }
    
    @Override
    public void update() {
		if (!this.hasBuilt) {
			forest.imageEventText.show();
			this.hasBuilt = true;
		}
		forest.imageEventText.update();
        if (!GenericEventDialog.waitForInput && !this.isDone) {
            forest.buttonEffect(forest.imageEventText.getSelectedOption());
			this.isDone = true;
        }
    }
}
