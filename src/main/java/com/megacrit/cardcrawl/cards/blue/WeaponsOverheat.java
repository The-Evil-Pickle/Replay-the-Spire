package com.megacrit.cardcrawl.cards.blue;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.status.BackFire;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.vfx.combat.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.orbs.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.*;
import com.megacrit.cardcrawl.actions.defect.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.IronCore;
import com.megacrit.cardcrawl.core.*;
import basemod.abstracts.*;
import replayTheSpire.*;

public class WeaponsOverheat extends CustomCard
{
    public static final String ID = "WeaponsOverheat";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    
    public WeaponsOverheat() {
        super("WeaponsOverheat", WeaponsOverheat.NAME, "cards/replay/replayBetaSkill.png", 0, WeaponsOverheat.DESCRIPTION, CardType.SKILL, (AbstractDungeon.player == null) ? AbstractCard.CardColor.COLORLESS : ((AbstractDungeon.player instanceof Defect) ? AbstractCard.CardColor.RED : AbstractCard.CardColor.BLUE), (ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic(IronCore.ID)) ? CardRarity.COMMON : CardRarity.SPECIAL, CardTarget.SELF);
		this.isEthereal = true;
		this.showEvokeValue = true;
        this.showEvokeOrbCount = 1;
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
		if (p.hasPower("Focus")) {
			if (p.getPower("Focus").amount > 0) {
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FocusPower(p, -1), -1));
			} else {
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FocusPower(p, 1), 1));
			}
		}
		AbstractDungeon.actionManager.addToBottom(new CrystalOrbUpdateAction());
        AbstractDungeon.actionManager.addToBottom(new ChannelAction(new HellFireOrb()));
		if(!this.upgraded) {
			AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new BackFire(), 1));
		}
		AbstractDungeon.actionManager.addToBottom(new CrystalOrbUpdateAction());
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new WeaponsOverheat();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = WeaponsOverheat.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("WeaponsOverheat");
        NAME = WeaponsOverheat.cardStrings.NAME;
        DESCRIPTION = WeaponsOverheat.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = WeaponsOverheat.cardStrings.UPGRADE_DESCRIPTION;
    }
}
