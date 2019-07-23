package com.megacrit.cardcrawl.mod.replay.cards.green;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;

import java.util.ArrayList;

import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.core.*;
import basemod.*;
import basemod.abstracts.*;

public class PoisonSmokescreen extends CustomCard
{
    public static final String ID = "Replay:Poison Smokescreen";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    private static final int COST = 2;
    
    public PoisonSmokescreen() {
        super(ID, PoisonSmokescreen.NAME, "cards/replay/Poison_Smokescreen.png", PoisonSmokescreen.COST, PoisonSmokescreen.DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        this.baseBlock = 6;
        this.block = this.baseBlock;
        ExhaustiveVariable.setBaseValue(this, 2);
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
    	if (this.block > 0) {
    		ArrayList<AbstractMonster> validMonsters = new ArrayList<AbstractMonster>();
            ArrayList<Integer> poisonList = new ArrayList<Integer>();
            for (AbstractMonster mm : AbstractDungeon.getMonsters().monsters) {
            	if (mm != null && !mm.isDeadOrEscaped()) {
            		validMonsters.add(mm);
            		poisonList.add(0);
            	}
            }
            for (int i=0; i < this.block; i++) {
            	int r = AbstractDungeon.miscRng.random(poisonList.size() - 1);
            	poisonList.set(r, poisonList.get(r) + 1);
            } 
            for (int i=0; i < validMonsters.size(); i++) {
            	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(validMonsters.get(i), p, new PoisonPower(validMonsters.get(i), p, poisonList.get(i)), poisonList.get(i)));
            }
    	}
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            //this.upgradeBlock(2);
            ExhaustiveVariable.upgrade(this, -3);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new PoisonSmokescreen();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = PoisonSmokescreen.cardStrings.NAME;
        DESCRIPTION = PoisonSmokescreen.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = PoisonSmokescreen.cardStrings.UPGRADE_DESCRIPTION;
    }
}
