package com.megacrit.cardcrawl.mod.replay.relics;

import java.util.ArrayList;

import com.megacrit.cardcrawl.mod.replay.ui.campfire.ChameleonBrewOption;
import com.megacrit.cardcrawl.mod.replay.ui.campfire.PolymerizeTransformOption;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic.LandingSound;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;

public class PocketPolymer extends AbstractRelic
{
    public static final String ID = "Replay:PocketPolymizer";
    
    public PocketPolymer() {
        super(ID, "betaRelic.png", RelicTier.RARE, LandingSound.MAGICAL);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void addCampfireOption(final ArrayList<AbstractCampfireOption> options) {
        options.add(new PolymerizeTransformOption(true));
    }
    @Override
    public AbstractRelic makeCopy() {
        return new PocketPolymer();
    }
}
