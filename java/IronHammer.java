package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.cards.*;

public class IronHammer extends AbstractRelic
{
    private boolean firstTurn;
    public static final String ID = "Iron Hammer";
    
    public IronHammer() {
        super("Iron Hammer", "ironHammer.png", AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.HEAVY);
        this.firstTurn = false;
    }
    
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    
    public void atPreBattle() {
        this.firstTurn = true;
        if (!this.pulse) {
            this.beginPulse();
            this.pulse = true;
        }
    }
    
    public void onPlayerEndTurn() {
        if (this.pulse) {
            this.pulse = false;
            this.firstTurn = false;
        }
    }
    
    public void onCardDraw(final AbstractCard abstractCard) {
        if (this.firstTurn && abstractCard.canUpgrade()) {
            abstractCard.upgrade();
            abstractCard.superFlash();
            this.flash();
        }
    }
    
    public AbstractRelic makeCopy() {
        return new IronHammer();
    }
}