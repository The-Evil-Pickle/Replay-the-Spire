package com.megacrit.cardcrawl.cards.red;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.*;
import com.megacrit.cardcrawl.core.*;
import basemod.*;
import basemod.abstracts.*;

public class DefyDeath extends CustomCard
{
    public static final String ID = "Defy Death";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final int COST = 5;
    private static final int DEFENSE_GAINED = 14;
    private static final int POOL = 1;
    
    public DefyDeath() {
        super("Defy Death", DefyDeath.NAME, "cards/replay/defyDeath.png", DefyDeath.COST, DefyDeath.DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.UNCOMMON, CardTarget.SELF, 1);
        this.baseBlock = DefyDeath.DEFENSE_GAINED;
    }
    
	public void updateDynamicCost() {
		this.setCostForTurn(this.cost - AbstractDungeon.player.exhaustPile.group.size());
	}
	
    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        this.updateDynamicCost();
    }
	
	@Override
	public void triggerOnOtherCardPlayed(final AbstractCard c) {
        AbstractDungeon.actionManager.addToBottom(new DefyDeathUpdateAction(this));
		/*
		this.updateDynamicCost();
		if (c.exhaust)
			this.setCostForTurn(this.cost - AbstractDungeon.player.exhaustPile.group.size() + 1);
		*/
	}
	
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
		//AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new BlurPower(p, 1), 1));
		//this.modifyCostForCombat(1);
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new DefyDeath();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(2);
			if (AbstractDungeon.currMapNode == null || AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT || AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
				this.upgradeBaseCost(4);
			} else {
				this.modifyCostForCombat(-1);
			}
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Defy Death");
        NAME = DefyDeath.cardStrings.NAME;
        DESCRIPTION = DefyDeath.cardStrings.DESCRIPTION;
    }
}
