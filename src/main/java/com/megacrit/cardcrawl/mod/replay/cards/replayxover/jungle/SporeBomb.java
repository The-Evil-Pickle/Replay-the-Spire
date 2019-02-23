package com.megacrit.cardcrawl.mod.replay.cards.replayxover.jungle;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.*;

import basemod.abstracts.CustomCard;
import theAct.cards.fungalobungalofunguyfuntimes.SS_Clouding;
import theAct.cards.fungalobungalofunguyfuntimes.SS_Leeching;
import theAct.cards.fungalobungalofunguyfuntimes.SS_Toxin;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.core.*;

public class SporeBomb extends CustomCard
{
    public static final String ID = "ReplayTheSpireMod:Sporebomb";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    private static final int COST = 0;
    
    public SporeBomb() {
        super(ID, SporeBomb.NAME, "cards/replay/replayBetaSkill.png", COST, SporeBomb.DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        this.exhaust = true;
        this.baseMagicNumber = 4;
        this.magicNumber = this.baseMagicNumber;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    	for (int i=0; i < this.magicNumber; i++) {
    		if (this.upgraded) {
    			for (AbstractMonster mon : AbstractDungeon.getMonsters().monsters) {
    				if (!mon.isDeadOrEscaped()) {
    					AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mon, p, new VulnerablePower(mon, 1, false), 1));
    				}
    			}
    		} else {
    			AbstractMonster mon = AbstractDungeon.getRandomMonster();
    			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mon, p, new VulnerablePower(mon, 1, false), 1));
    		}
    	}
    	
    	ArrayList<AbstractCard> sporesList = new ArrayList<AbstractCard>();
        sporesList.add(new SS_Clouding());
        sporesList.add(new SS_Leeching());
        sporesList.add(new SS_Toxin());
        for (int i=0; i<2; i++) {
        	AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(sporesList.get(AbstractDungeon.miscRng.random(sporesList.size()-1)).makeCopy(), 1));
        }
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new SporeBomb();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = SporeBomb.cardStrings.NAME;
        DESCRIPTION = SporeBomb.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = SporeBomb.cardStrings.UPGRADE_DESCRIPTION;
    }
}
