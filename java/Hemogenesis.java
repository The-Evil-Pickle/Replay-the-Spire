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

public class Hemogenesis extends AbstractCard
{
    public static final String ID = "Hemogenesis";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final int COST = 2;
    private static final int POOL = 1;
    
    public Hemogenesis() {
        this(0);
    }
    public Hemogenesis(final int upgrades) {
        super("Hemogenesis", Hemogenesis.NAME, "status/beta", "status/beta", 2, Hemogenesis.DESCRIPTION, CardType.ATTACK, CardColor.RED, CardRarity.UNCOMMON, CardTarget.ENEMY, 1);
        this.baseDamage = 3;
        this.baseBlock = 3;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
		this.timesUpgraded = upgrades;
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
        return new Hemogenesis(this.timesUpgraded);
    }
    
    @Override
    public void upgrade() {
        this.upgradeDamage(2 + (this.timesUpgraded / 2));
        this.upgradeBlock(2 + (this.timesUpgraded / 2));
        this.upgradeMagicNumber(1 + ((this.timesUpgraded + 1) / 2));
        ++this.timesUpgraded;
        this.upgraded = true;
        this.name = Hemogenesis.NAME + "+" + this.timesUpgraded;
        this.initializeTitle();
    }
    
    @Override
    public boolean canUpgrade() {
		if (AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
			return true;
        }
		return (!this.upgraded);
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
