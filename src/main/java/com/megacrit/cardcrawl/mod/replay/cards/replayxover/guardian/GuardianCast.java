package com.megacrit.cardcrawl.mod.replay.cards.replayxover.guardian;

import com.megacrit.cardcrawl.localization.*;
import guardian.*;
import guardian.cards.AbstractGuardianCard;
import guardian.patches.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.defect.AnimateOrbAction;
import com.megacrit.cardcrawl.actions.defect.EvokeWithoutRemovingOrbAction;
import com.megacrit.cardcrawl.cards.*;
import com.evacipated.cardcrawl.mod.stslib.actions.defect.EvokeSpecificOrbAction;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AlwaysRetainField;
import com.megacrit.cardcrawl.core.*;

public class GuardianCast extends AbstractGuardianCard
{
    public static final String ID = "Replay:GuardianCast";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static String DESCRIPTION = cardStrings.DESCRIPTION;
    public static String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    
    public GuardianCast() {
        super(GuardianCast.ID, GuardianCast.NAME, "cards/replay/dualcast.png", 0, GuardianCast.DESCRIPTION, CardType.SKILL, AbstractCardEnum.GUARDIAN, CardRarity.SPECIAL, CardTarget.NONE);
        this.socketCount = 2;
        this.magicNumber = this.baseMagicNumber = 2;
        this.exhaust = true;
        this.isEthereal = true;
        this.updateDescription();
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        super.use(p, m);
        this.useGems(p, m);
        if (p.orbs.size() > 0 && p.orbs.get(0) instanceof Lightning) {
        	for (int i=0; i<this.sockets.size(); i++) {
        		AbstractDungeon.actionManager.addToBottom(new AnimateOrbAction(1));
                AbstractDungeon.actionManager.addToBottom(new EvokeWithoutRemovingOrbAction(1));
        	}
        	AbstractDungeon.actionManager.addToBottom(new AnimateOrbAction(1));
		}
        for (AbstractOrb o : p.orbs) {
        	if (o != null && o instanceof Lightning) {
        		AbstractDungeon.actionManager.addToBottom(new EvokeSpecificOrbAction(o));
        	}
        }
    }
    
    public AbstractCard makeCopy() {
        return new GuardianCast();
    }
    
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            if (AlwaysRetainField.alwaysRetain.get(this) == false || this.isEthereal) {
            	this.retain = true;
                this.isEthereal = false;
                AlwaysRetainField.alwaysRetain.set(this, true);
            } else {
            	this.socketCount++;
            }
            this.updateDescription();
        }
    }
    
    @Override
    public void updateDescription() {
        if (this.socketCount > 0) {
            this.rawDescription = this.updateGemDescription(this.upgraded ? UPGRADE_DESCRIPTION : DESCRIPTION, true);
        }
        this.name = EXTENDED_DESCRIPTION[Math.min(this.sockets.size(), 5)];
        if (this.upgraded) {
        	this.name += "+";
        }
        GuardianMod.logger.info(GuardianCast.DESCRIPTION);
        this.initializeDescription();
    }
}
