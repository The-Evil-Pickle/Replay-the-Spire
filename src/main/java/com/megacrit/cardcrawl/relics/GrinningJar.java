package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.colorless.*;
import com.megacrit.cardcrawl.unlock.*;
import com.megacrit.cardcrawl.core.*;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.rewards.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.vfx.cardManip.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.events.*;

public class GrinningJar extends AbstractRelic
{
    public static final String ID = "Grinning Jar";
	private static final int POWER = 1;
	private static final int SIZECOUNTER = 12;
    
    public GrinningJar() {
        super("Grinning Jar", "grinningJar.png", RelicTier.COMMON, LandingSound.SOLID);
    }
    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + GrinningJar.SIZECOUNTER + this.DESCRIPTIONS[1];
    }
    
    @Override
    public void onEquip() {
        final CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
		final AbstractCard addedcard = new PotOfGreed();
		UnlockTracker.markCardAsSeen(addedcard.cardID);
		int copies = GrinningJar.POWER;
		copies += AbstractDungeon.player.masterDeck.size() / GrinningJar.SIZECOUNTER;
		this.counter = AbstractDungeon.player.masterDeck.size() % GrinningJar.SIZECOUNTER;
		for (int i=0; i < copies; i++) {
			group.addToBottom(addedcard.makeCopy());
		}
        AbstractDungeon.gridSelectScreen.openConfirmationGrid(group, this.DESCRIPTIONS[2]);
    }
    
    @Override
    public void onObtainCard(final AbstractCard c) {
        if (c.cardID != "Pot Of Greed") {
            this.counter ++;
			if (this.counter >= GrinningJar.SIZECOUNTER) {
				this.flash();
				/*
				final CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
				final AbstractCard addedcard = new PotOfGreed();
				group.addToBottom(addedcard.makeCopy());
				AbstractDungeon.gridSelectScreen.openConfirmationGrid(group, this.DESCRIPTIONS[2]);
				//AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new PotOfGreed(), Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f));
				*/
				AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(new PotOfGreed(), Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f));
				this.counter = 0;
			}
        }
    }
	
    @Override
    public AbstractRelic makeCopy() {
        return new GrinningJar();
    }
}
