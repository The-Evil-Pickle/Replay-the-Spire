package com.megacrit.cardcrawl.mod.replay.cards.curses;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.utility.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.cards.colorless.GhostFetch;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.mod.replay.rooms.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
//import com.megacrit.cardcrawl.cards.CardColor;
//import com.megacrit.cardcrawl.cards.CardRarity;
//import com.megacrit.cardcrawl.cards.CardTarget;
//import com.megacrit.cardcrawl.cards.CardType;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.SoulboundField;
import com.megacrit.cardcrawl.core.*;
import basemod.abstracts.*;

public class FaultyEquipment extends CustomCard
{
    public static final String ID = "ReplayTheSpireMod:Faulty Equipment";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    private static final int COST = -2;
    
    public FaultyEquipment() {
        super(ID, FaultyEquipment.NAME, "cards/replay/betaCurse.png", -2, FaultyEquipment.DESCRIPTION, CardType.CURSE, CardColor.CURSE, CardRarity.CURSE, CardTarget.NONE);
		SoulboundField.soulbound.set(this, true);
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        
    }

    public boolean canUse(final AbstractPlayer p, final AbstractMonster m) {
        return this.upgraded && this.cardPlayable(m) && this.hasEnoughEnergy();
    }
    
    @Override
    public AbstractCard makeCopy() {
    	if (this.upgraded) {
    		AbstractCard c = new FaultyEquipment();
    		c.upgrade();
    		return c;
    	}
        return new FaultyEquipment();
    }
    
	@Override
	public boolean canUpgrade() {
        return !this.upgraded && (AbstractDungeon.currMapNode == null || AbstractDungeon.getCurrRoom() == null || AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT || AbstractDungeon.getMonsters().areMonstersBasicallyDead());
    }
	
    @Override
    public void upgrade() {
    	if (!this.upgraded) {
    		this.upgradeName();
    		this.name = "Untested Repairs";
    		SoulboundField.soulbound.set(this, false);
    		FleetingField.fleeting.set(this, true);
    		this.upgradeBaseCost(0);
    		//this.type = AbstractCard.CardType.SKILL;
            this.rawDescription = FaultyEquipment.UPGRADE_DESCRIPTION;
            this.initializeDescription();
    	}
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = FaultyEquipment.cardStrings.NAME;
        DESCRIPTION = FaultyEquipment.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = FaultyEquipment.cardStrings.UPGRADE_DESCRIPTION;
    }
}
