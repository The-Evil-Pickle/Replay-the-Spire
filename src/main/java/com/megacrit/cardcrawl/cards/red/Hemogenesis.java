package com.megacrit.cardcrawl.cards.red;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.vfx.combat.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;
import basemod.*;
import basemod.abstracts.*;

public class Hemogenesis extends CustomCard
{
    public static final String ID = "Hemogenesis";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final int COST = 3;
	private int upgradeTick = 2;
    
    public Hemogenesis() {
        this(0, 2);
    }
    public Hemogenesis(final int upgrades) {
        this(upgrades, 2);
    }
    public Hemogenesis(final int upgrades, final int ticks) {
        super("Hemogenesis", Hemogenesis.NAME, "cards/replay/hemogenesis.png", COST, Hemogenesis.DESCRIPTION, CardType.ATTACK, CardColor.RED, CardRarity.RARE, CardTarget.ENEMY);
        this.baseDamage = 5;
        this.baseBlock = 5;
        this.baseMagicNumber = 5;
		this.upgradeTick = ticks;
        this.magicNumber = this.baseMagicNumber;
		this.timesUpgraded = upgrades;
		this.exhaust = true;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1f));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
		AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, this.magicNumber));
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new Hemogenesis(this.timesUpgraded, this.upgradeTick);
    }
    
    @Override
    public void upgrade() {
		this.name = Hemogenesis.NAME;
		boolean isSmithUpgrade = (AbstractDungeon.currMapNode == null || AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT || AbstractDungeon.getMonsters().areMonstersBasicallyDead());
		if (isSmithUpgrade) {
			this.upgradeTick = 3;
		}
        this.upgradeDamage(this.upgradeTick);// + this.timesUpgraded
        this.upgradeBlock(this.upgradeTick);// + this.timesUpgraded
        this.upgradeMagicNumber(this.upgradeTick);// + this.timesUpgraded
		//if (!isSmithUpgrade) {
			++this.timesUpgraded;
		//}
        this.upgraded = true;
		if (this.upgradeTick > 2) {
			this.name += "+";
		}
		if (!isSmithUpgrade) {
			int dispup = this.timesUpgraded;
			if (this.upgradeTick > 2) {
				dispup -= 1;
			}
			this.name += "+" + dispup;
		}
        this.initializeTitle();
    }
    
    @Override
    public boolean canUpgrade() {
		if (AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
			return true;
        }
		return (this.upgradeTick < 3 && !this.upgraded);
    }
	
    @Override
    public void tookDamage() {
		if (AbstractDungeon.player != null && AbstractDungeon.player.hand.contains(this)) {
			this.upgrade();
			this.superFlash();
		}
    }
	
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Hemogenesis");
        NAME = Hemogenesis.cardStrings.NAME;
        DESCRIPTION = Hemogenesis.cardStrings.DESCRIPTION;
    }
}
