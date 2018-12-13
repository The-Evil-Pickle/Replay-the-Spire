package com.megacrit.cardcrawl.mod.replay.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.events.*;
import com.megacrit.cardcrawl.mod.replay.monsters.replay.*;

import replayTheSpire.patches.RenderHandPatch;

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
			RenderHandPatch.plsDontRenderHand = true;
		}
		forest.imageEventText.update();
        if (!GenericEventDialog.waitForInput && !this.isDone) {
            forest.buttonEffect(forest.imageEventText.getSelectedOption());
            RenderHandPatch.plsDontRenderHand = false;
			this.isDone = true;
        }
    }
}
