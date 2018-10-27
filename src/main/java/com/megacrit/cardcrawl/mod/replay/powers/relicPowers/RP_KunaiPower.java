package com.megacrit.cardcrawl.mod.replay.powers.relicPowers;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.actions.utility.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;

public class RP_KunaiPower extends AbstractPower
{
    public static final String POWER_ID = "RP_KunaiPower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private int damage;
	
	
    public RP_KunaiPower(final AbstractCreature owner) {
        this.name = RP_KunaiPower.NAME;
        this.ID = "RP_KunaiPower";
        this.owner = owner;
        this.amount = 3;
		this.damage = 1;
        this.updateDescription();
        this.loadRegion("unawakened");
    }
    
    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = RP_KunaiPower.DESCRIPTIONS[0] + this.amount + RP_KunaiPower.DESCRIPTIONS[1] + this.damage + RP_KunaiPower.DESCRIPTIONS[2];
        }
        else {
            this.description = RP_KunaiPower.DESCRIPTIONS[0] + this.amount + RP_KunaiPower.DESCRIPTIONS[3] + this.damage + RP_KunaiPower.DESCRIPTIONS[2];
        }
    }
    
    @Override
    public void atStartOfTurn() {
        this.amount = 3;
    }
    
    @Override
    public void stackPower(final int stackAmount) {
        this.fontScale = 8.0f;
        this.damage += 1;
        this.updateDescription();
    }
	
    @Override
    public void onUseCard(final AbstractCard card, final UseCardAction action) {
		if (card.type == AbstractCard.CardType.ATTACK) {
			--this.amount;
			if (this.amount == 0) {
				this.flash();
				this.amount = 3;
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new DexterityPower(this.owner, 1), 1));
			}
		}
    }
	
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("RP_KunaiPower");
        NAME = RP_KunaiPower.powerStrings.NAME;
        DESCRIPTIONS = RP_KunaiPower.powerStrings.DESCRIPTIONS;
    }
}
