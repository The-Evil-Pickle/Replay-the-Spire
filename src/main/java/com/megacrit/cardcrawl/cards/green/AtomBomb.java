package com.megacrit.cardcrawl.cards.green;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.powers.*;
import java.util.*;
import com.megacrit.cardcrawl.core.*;
import basemod.*;
import basemod.abstracts.*;

public class AtomBomb extends CustomCard
{
    public static final String ID = "Atom Bomb";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final int DAMAGE = 60;
    private static final int POISON_AMT = 3;
    private static final int COST = 4;
    private static final int POOL = 1;
    
    public AtomBomb() {
        super("Atom Bomb", AtomBomb.NAME, "cards/replay/atomBomb.png", 4, AtomBomb.DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCard.CardColor.GREEN, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.ENEMY, 1);
        this.baseDamage = 60;
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }
    
    public void use(final AbstractPlayer abstractPlayer, final AbstractMonster abstractMonster) {
        AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DamageAction((AbstractCreature)abstractMonster, new DamageInfo((AbstractCreature)abstractPlayer, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        for (final AbstractMonster abstractMonster2 : AbstractDungeon.getMonsters().monsters) {
            AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new ApplyPowerAction((AbstractCreature)abstractMonster2, (AbstractCreature)abstractPlayer, (AbstractPower)new PoisonPower((AbstractCreature)abstractMonster2, (AbstractCreature)abstractPlayer, this.magicNumber), this.magicNumber));
        }
    }
    
    public AbstractCard makeCopy() {
        return new AtomBomb();
    }
    
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(15);
            this.upgradeMagicNumber(1);
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Atom Bomb");
        NAME = AtomBomb.cardStrings.NAME;
        DESCRIPTION = AtomBomb.cardStrings.DESCRIPTION;
    }
}
