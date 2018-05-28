package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.actions.*;

public class QuantumEgg extends AbstractRelic
{
    public static final String ID = "Quantum Egg";
    
    public QuantumEgg() {
        super("Quantum Egg", "quantumEgg.png", RelicTier.COMMON, LandingSound.SOLID);
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
