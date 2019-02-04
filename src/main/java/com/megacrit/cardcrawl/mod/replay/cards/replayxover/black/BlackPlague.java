package com.megacrit.cardcrawl.mod.replay.cards.replayxover.black;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.mod.replay.powers.DecrepitPower;
import com.megacrit.cardcrawl.mod.replay.powers.LanguidPower;
import com.megacrit.cardcrawl.mod.replay.powers.NecroticPoisonPower;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;

import basemod.helpers.TooltipInfo;
import infinitespire.abstracts.BlackCard;

public class BlackPlague extends BlackCard {
	private static final String ID = "ReplayTheSpireMod:Black Plague";

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	private static final String NAME = cardStrings.NAME;
	private static final int COST = 2;
	private static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
	private ArrayList<Integer> effects = new ArrayList<Integer>();
	private int splits;
	private static final int E_POISON = 0;
	private static final int E_NECRO = 1;
	private static final int E_LANG = 2;
	private static final int E_DEC = 3;

	public BlackPlague() {
		super(ID, NAME, "cards/replay/replayBetaSkill.png", COST, DESCRIPTION, CardType.SKILL, CardTarget.ENEMY);
		this.baseMagicNumber = 4;
		this.magicNumber = this.baseMagicNumber;
		this.exhaust = true;
        this.tips = new ArrayList<TooltipInfo>();
		this.splits = 0;
		this.effects.add(E_POISON);
		this.effects.add(E_NECRO);
		this.effects.add(E_LANG);
		this.effects.add(E_DEC);
		this.rawDescription = EXTENDED_DESCRIPTION[(this.baseMagicNumber+1)/2 > 1 ? 1 : 2];
		for (int effect : this.effects) {
			this.rawDescription = this.rawDescription + EXTENDED_DESCRIPTION[effect+3];
		}
		this.rawDescription = this.rawDescription + EXTENDED_DESCRIPTION[(this.splits < 2 && this.effects.size() > 1) ? 7 : 8];
		this.initializeDescription();
	}
	public BlackPlague(int cost, ArrayList<Integer> effects, int magic, int splits, boolean upgraded) {
		super(ID, NAME, "cards/replay/replayBetaSkill.png", cost, DESCRIPTION, CardType.SKILL, CardTarget.ENEMY);
		this.baseMagicNumber = magic;
		this.magicNumber = this.baseMagicNumber;
		this.upgraded = this.upgradedMagicNumber = upgraded;
		this.exhaust = true;
		this.splits = splits;
		this.rawDescription = EXTENDED_DESCRIPTION[(this.baseMagicNumber+1)/2 > 1 ? 1 : 2];
		this.effects = effects;
        this.tips = new ArrayList<TooltipInfo>();
		for (int effect : this.effects) {
			this.rawDescription = this.rawDescription + EXTENDED_DESCRIPTION[effect+3];
		}
		this.rawDescription = this.rawDescription + EXTENDED_DESCRIPTION[(this.splits < 2 && this.effects.size() > 1) ? 7 : 8];
		this.initializeDescription();
	}

    public ArrayList<TooltipInfo> tips;
    @Override
    public List<TooltipInfo> getCustomTooltips() {
    	this.tips.clear();
        this.tips.add(new TooltipInfo(EXTENDED_DESCRIPTION[7], EXTENDED_DESCRIPTION[0]));
        return this.tips;
    }
	@Override
	public void upgrade() {
		if(!this.upgraded) {
			this.upgradeName();
			if (this.splits > 0) {
				this.upgradeMagicNumber(1);
			} else {
				this.upgradeMagicNumber(2);
			}
		}
	}

	@Override
	public void useWithEffect(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, (this.magicNumber+1)/2));
		for (int effect : this.effects) {
			switch(effect) {
			case E_POISON:
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new PoisonPower(m, p, this.magicNumber), this.magicNumber));
				break;
			case E_NECRO:
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new NecroticPoisonPower(m, p, this.magicNumber), this.magicNumber));
				break;
			case E_LANG:
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new LanguidPower(m, this.magicNumber, false), this.magicNumber));
				break;
			case E_DEC:
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new DecrepitPower(m, this.magicNumber, false), this.magicNumber));
				break;
			}
		}
		if (this.splits < 2 && this.effects.size() > 1) {
			ArrayList<Integer> e1 = new ArrayList<Integer>();
			ArrayList<Integer> e2 = new ArrayList<Integer>();
			Collections.shuffle(this.effects);
			e1.add(this.effects.get(0));
			e2.add(this.effects.get(1));
			if (this.effects.size() > 2) {
				e1.add(this.effects.get(2));
				e2.add(this.effects.get(3));
			}
			AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new BlackPlague(Math.max(this.cost - 1, 0), e1, Math.max((this.magicNumber+1)/2, 1), this.splits+1, this.upgraded), 1));
			AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new BlackPlague(Math.max(this.cost - 1, 0), e2, Math.max((this.magicNumber+1)/2, 1), this.splits+1, this.upgraded), 1));
		}
	}

    public AbstractCard makeCopy() {
    	if (this.splits > 0) {
    		return new BlackPlague(this.cost, this.effects, this.magicNumber, this.splits, false);
    	}
        return new BlackPlague();
    }
}
