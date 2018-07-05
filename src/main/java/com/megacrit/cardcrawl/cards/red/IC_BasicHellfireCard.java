package com.megacrit.cardcrawl.cards.red;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.vfx.combat.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.orbs.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.core.*;
import basemod.abstracts.*;

public class IC_BasicHellfireCard extends CustomCard
{
    public static final String ID = "IC_BasicHellfireCard";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final int COST = 2;
    private static final int DEFENSE_GAINED = 12;
    private static final int FLAME_DAMAGE = 4;
    
    public IC_BasicHellfireCard() {
        super("IC_BasicHellfireCard", IC_BasicHellfireCard.NAME, "cards/replay/replayBetaSkill.png", 1, IC_BasicHellfireCard.DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.SPECIAL, CardTarget.SELF);
		this.exhaust = true;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ChannelAction(new HellFireOrb()));
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new IC_BasicHellfireCard();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("IC_BasicHellfireCard");
        NAME = IC_BasicHellfireCard.cardStrings.NAME;
        DESCRIPTION = IC_BasicHellfireCard.cardStrings.DESCRIPTION;
    }
}
