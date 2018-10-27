package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.*;

public class QuantumEgg extends AbstractRelic
{
    public static final String ID = "Quantum Egg";
    
    public QuantumEgg() {
        super("Quantum Egg", "quantumEgg.png", RelicTier.UNCOMMON, LandingSound.SOLID);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new QuantumEgg();
    }
}
