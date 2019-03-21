package com.megacrit.cardcrawl.mod.replay.cards.replayxover.guardian;

import com.megacrit.cardcrawl.localization.*;
import guardian.*;
import guardian.cards.AbstractGuardianCard;
import guardian.patches.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;

public class GuardianBash extends AbstractGuardianCard
{
    public static final String ID = "Replay:GuardianBash";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static String DESCRIPTION = cardStrings.DESCRIPTION;
    
    public GuardianBash() {
        super(GuardianBash.ID, GuardianBash.NAME, "cards/replay/bash.png", 2, GuardianBash.DESCRIPTION, CardType.ATTACK, AbstractCardEnum.GUARDIAN, CardRarity.BASIC, CardTarget.ENEMY);
        this.baseDamage = 8;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.socketCount = 1;
        this.updateDescription();
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        super.use(p, m);
        this.useGems(p, m);
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new VulnerablePower(m, this.magicNumber, false), this.magicNumber));
    }
    
    public AbstractCard makeCopy() {
        return (AbstractCard)new GuardianBash();
    }
    
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(1);
            this.upgradeMagicNumber(1);
            ++this.socketCount;
            this.updateDescription();
        }
    }
    
    @Override
    public void updateDescription() {
        if (this.socketCount > 0) {
            this.rawDescription = this.updateGemDescription(GuardianBash.cardStrings.DESCRIPTION, true);
        }
        GuardianMod.logger.info(GuardianBash.DESCRIPTION);
        this.initializeDescription();
    }
}
