package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.*;

public class FadingSporesPower extends AbstractPower
{
    public static final String POWER_ID = "FF_Spore Cloud";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    public FadingSporesPower(final AbstractCreature owner, final int vulnAmt) {
        this.name = FadingSporesPower.NAME;
        this.ID = "FF_Spore Cloud";
        this.owner = owner;
        this.amount = vulnAmt;
        this.updateDescription();
        this.loadRegion("sporeCloud");
    }
    
    @Override
    public void updateDescription() {
        this.description = FadingSporesPower.DESCRIPTIONS[0] + this.amount + FadingSporesPower.DESCRIPTIONS[1];
    }
    
	private boolean checkForFade() {
		if (AbstractDungeon.getCurrRoom().isBattleEnding() || !this.owner.hasPower("Fading") || this.owner.getPower("Fading").amount > 1) {
			return false;
		}
		return (AbstractDungeon.actionManager.currentAction instanceof SuicideAction);
	}
	
    @Override
    public void onDeath() {
        if (!this.checkForFade()) {
            return;
        }
        CardCrawlGame.sound.play("SPORE_CLOUD_RELEASE");
        this.flashWithoutSound();
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, null, new VulnerablePower(AbstractDungeon.player, this.amount, true), this.amount));
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("FF_Spore Cloud");
        NAME = FadingSporesPower.powerStrings.NAME;
        DESCRIPTIONS = FadingSporesPower.powerStrings.DESCRIPTIONS;
    }
}