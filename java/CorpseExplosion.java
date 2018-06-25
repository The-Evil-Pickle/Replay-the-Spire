package com.megacrit.cardcrawl.cards.green;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.core.*;

public class CorpseExplosion extends AbstractCard
{
    public static final String ID = "Corpse Explosion";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    private static final int COST = 2;
    
    public CorpseExplosion() {
        super("Corpse Explosion", CorpseExplosion.NAME, null, "green/attack/corpseExplosion", CorpseExplosion.COST, CorpseExplosion.DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.RARE, CardTarget.ALL_ENEMY);
        //this.isMultiDamage = true;
		this.exhaust = true;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
		if (this.upgraded) {
			if (m.getPower("Poison") == null) {
				AbstractDungeon.effectList.add(new ThoughtBubble(p.dialogX, p.dialogY, 3.0f, CorpseExplosion.EXTENDED_DESCRIPTION[0], true));
				return;
			}
			int pAmnt = m.getPower("Poison").amount;
			AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(m, p, "Poison"));
			for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
			  AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new NecroticPoisonPower(mo, p, pAmnt), pAmnt, AbstractGameAction.AttackEffect.POISON));
			}
		} else {
			for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
				if (m.getPower("Poison") != null) {
					int pAmnt = m.getPower("Poison").amount;
					AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(m, p, "Poison"));
					AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new NecroticPoisonPower(m, p, pAmnt), pAmnt, AbstractGameAction.AttackEffect.POISON));
				}
			}
		}
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new CorpseExplosion();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
			//this.exhaust = false;
			this.target = CardTarget.ENEMY;
            this.rawDescription = CorpseExplosion.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Corpse Explosion");
        NAME = CorpseExplosion.cardStrings.NAME;
        DESCRIPTION = CorpseExplosion.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = CorpseExplosion.cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = CorpseExplosion.cardStrings.EXTENDED_DESCRIPTION;
    }
}