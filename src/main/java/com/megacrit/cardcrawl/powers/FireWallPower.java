package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.orbs.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.*;
import org.apache.logging.log4j.*;
import com.megacrit.cardcrawl.core.*;

public class FireWallPower extends AbstractPower
{
    private static final Logger logger;
    public static final String POWER_ID = "Firewall";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    public FireWallPower(final AbstractCreature owner, final int thornsDamage) {
        this.name = FireWallPower.NAME;
        this.ID = "Firewall";
        this.owner = owner;
        this.amount = thornsDamage;
        this.updateDescription();
        this.loadRegion("flameBarrier");
    }
    
    @Override
    public void stackPower(final int stackAmount) {
        if (this.amount == -1) {
            FireWallPower.logger.info(this.name + " does not stack");
            return;
        }
        this.fontScale = 8.0f;
        this.amount += stackAmount;
        this.updateDescription();
    }
    
    @Override
    public int onAttacked(final DamageInfo info, final int damageAmount) {
        if (info.owner != null && info.owner != this.owner && info.type != DamageInfo.DamageType.HP_LOSS && info.type != DamageInfo.DamageType.THORNS && damageAmount < info.output && info.output > 0) {
            this.flash();
			AbstractDungeon.actionManager.addToTop(new ChannelAction(new HellFireOrb()));
            //AbstractDungeon.actionManager.addToTop(new DamageAction(info.owner, this.thornsInfo, AbstractGameAction.AttackEffect.FIRE));
        }
        return damageAmount;
    }
    
    @Override
    public void atStartOfTurn() {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, "Firewall"));
    }
    
    @Override
    public void updateDescription() {
        this.description = FireWallPower.DESCRIPTIONS[0] + this.amount + FireWallPower.DESCRIPTIONS[1];
    }
    
    static {
        logger = LogManager.getLogger(FireWallPower.class.getName());
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Firewall");
        NAME = FireWallPower.powerStrings.NAME;
        DESCRIPTIONS = FireWallPower.powerStrings.DESCRIPTIONS;
    }
}
