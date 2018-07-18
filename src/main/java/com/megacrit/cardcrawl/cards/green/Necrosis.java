package com.megacrit.cardcrawl.cards.green;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.PoisonDartsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NecroticPoisonPower;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

import basemod.abstracts.CustomCard;

public class Necrosis extends CustomCard
{
	  public static final String ID = "ReplayTheSpireMod:Necrosis";
	  private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	  public static final String NAME = cardStrings.NAME;
	  public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	  public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	  public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
	  private static final int COST = 2;
	  
	  public Necrosis()
	  {
	    super(ID, NAME, "cards/replay/replayBetaSkill.png", COST, DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCard.CardColor.GREEN, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.ALL_ENEMY);
	    this.exhaust = true;
	  }
	  
	  public void use(AbstractPlayer p, AbstractMonster m)
	  {
		  if (this.upgraded) {
				if (m.getPower("Poison") == null) {
					AbstractDungeon.effectList.add(new ThoughtBubble(p.dialogX, p.dialogY, 3.0f, EXTENDED_DESCRIPTION[0], true));
					return;
				}
				int pAmnt = m.getPower("Poison").amount;
				AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(m, p, "Poison"));
				for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
				  AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new NecroticPoisonPower(mo, p, pAmnt), pAmnt, AbstractGameAction.AttackEffect.POISON));
				}
			} else {
				for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
					if (mo.getPower("Poison") != null) {
						int pAmnt = mo.getPower("Poison").amount;
						AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(mo, p, "Poison"));
						AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new NecroticPoisonPower(mo, p, pAmnt), pAmnt, AbstractGameAction.AttackEffect.POISON));
					}
				}
			}
	  }
	  
	  public AbstractCard makeCopy()
	  {
	    return new Necrosis();
	  }
	  
	  public void upgrade()
	  {
	    if (!this.upgraded)
	    {
	      upgradeName();
	      this.target = CardTarget.ENEMY;
	      this.rawDescription = UPGRADE_DESCRIPTION;
	      initializeDescription();
	    }
	  }
	}