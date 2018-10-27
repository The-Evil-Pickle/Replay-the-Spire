package com.megacrit.cardcrawl.mod.replay.powers;

import replayTheSpire.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.actions.defect.*;
import com.megacrit.cardcrawl.mod.replay.orbs.*;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.helpers.*;

public class CrystallizerPower extends AbstractPower
{
    public static final String POWER_ID = "Crystallizer";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    public CrystallizerPower(final AbstractCreature owner, final int amount) {
        this.name = CrystallizerPower.NAME;
        this.ID = "Crystallizer";
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
		//this.img = ImageMaster.loadImage("images/powers/32/xanatos.png");
        this.loadRegion("specialist");
    }
    
	@Override
    protected void loadRegion(final String fileName) {
        this.region48 = ReplayTheSpireMod.powerAtlas.findRegion("48/" + fileName);
		this.region128 = ReplayTheSpireMod.powerAtlas.findRegion("128/" + fileName);
    }
	
    @Override
    public void updateDescription() {
        this.description = CrystallizerPower.DESCRIPTIONS[0] + this.amount + CrystallizerPower.DESCRIPTIONS[1];
    }
    
    @Override
    public void atEndOfTurn(final boolean isPlayer) {
		int emptyslots = 0;
		int filledslots = 0;
		for (final AbstractOrb o : AbstractDungeon.player.orbs) {
			if (o instanceof EmptyOrbSlot || o instanceof GlassOrb) {
				emptyslots++;
			} else {
				filledslots++;
			}
		}
		if (emptyslots >= filledslots) {
			this.flash();
			for (int i=0; i < this.amount; i++) {
				AbstractDungeon.actionManager.addToTop(new ChannelAction(new CrystalOrb()));
			}
		}
        //AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this.owner, this.owner, this.amount));
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Crystallizer");
        NAME = CrystallizerPower.powerStrings.NAME;
        DESCRIPTIONS = CrystallizerPower.powerStrings.DESCRIPTIONS;
    }
}
