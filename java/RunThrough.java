package com.megacrit.cardcrawl.cards.red;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.core.*;

public class RunThrough extends AbstractCard
{
    public static final String ID = "Run Through";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final int ATTACK_DMG = 9;
    private static final int BASE_DRAW = 1;
    private static final int POOL = 1;
    
    public RunThrough() {
        super("Run Through", RunThrough.NAME, "status/beta", "status/beta", 1, RunThrough.DESCRIPTION, CardType.ATTACK, CardColor.RED, CardRarity.COMMON, CardTarget.ENEMY, 1);
        this.baseDamage = 9;
        this.baseMagicNumber = 5;
        this.magicNumber = this.baseMagicNumber;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        AbstractDungeon.actionManager.addToBottom(new LoseBlockAction(m, p, this.magicNumber));
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new RunThrough();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(3);
            this.upgradeMagicNumber(3);
            this.rawDescription = RunThrough.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Run Through");
        NAME = RunThrough.cardStrings.NAME;
        DESCRIPTION = RunThrough.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = RunThrough.cardStrings.UPGRADE_DESCRIPTION;
    }
}
