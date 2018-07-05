package com.megacrit.cardcrawl.cards.colorless;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.vfx.combat.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;
import basemod.*;
import basemod.abstracts.*;

public class DarkEchoRitualCard extends CustomCard
{
    public static final String ID = "Dark Echo";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final int COST = 1;
    private static final int ATTACK_DMG = 7;
    private static final int HEAL_AMT = 2;
    private static final int POOL = 1;
    
    public DarkEchoRitualCard() {
        super("Dark Echo", DarkEchoRitualCard.NAME, "cards/replay/dark_echo.png", 1, DarkEchoRitualCard.DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.ALL_ENEMY, 1);
        this.baseDamage = 42;
        this.isMultiDamage = true;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(14);
        }
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new DarkEchoRitualCard();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Dark Echo");
        NAME = DarkEchoRitualCard.cardStrings.NAME;
        DESCRIPTION = DarkEchoRitualCard.cardStrings.DESCRIPTION;
    }
}
