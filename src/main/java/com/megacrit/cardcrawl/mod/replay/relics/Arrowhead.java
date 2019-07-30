package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSmithEffect;

import basemod.ReflectionHacks;
import replayTheSpire.patches.ArrowheadPatches;

public class Arrowhead extends AbstractRelic
{
    public static final String ID = "Replay:Arrowhead";
    
    public Arrowhead() {
        super(ID, "arrowhead.png", AbstractRelic.RelicTier.RARE, AbstractRelic.LandingSound.HEAVY);
    }
    
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    
    public AbstractRelic makeCopy() {
        return new Arrowhead();
    }
    
    @Override
    public void onSmith() {
    	if (ArrowheadPatches.didSecondUpgrade) {
    		AbstractDungeon.overlayMenu.cancelButton.hide();
    		ReflectionHacks.setPrivate((Object)(AbstractDungeon.gridSelectScreen), (Class)GridCardSelectScreen.class, "canCancel", (Object)false);
    	}
    }
    
}
