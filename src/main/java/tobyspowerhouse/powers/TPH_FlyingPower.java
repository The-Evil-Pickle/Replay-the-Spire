package tobyspowerhouse.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class TPH_FlyingPower extends AbstractPower
{
    public static final String POWER_ID = "TPH_Flight";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    int resetAmount;
    float damageMultiplier;

    public TPH_FlyingPower(final AbstractCreature owner, final int amount) {
    	this(owner, amount, 3, 0.5f);
    }
    public TPH_FlyingPower(final AbstractCreature owner, final int amount, final int resetAmount, final float damageMultiplier) {
        this.name = TPH_FlyingPower.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.resetAmount = resetAmount;
        this.damageMultiplier = damageMultiplier;
        this.updateDescription();
        this.loadRegion("flight");
        this.priority = 50;
    }
    
    @Override
    public void updateDescription() {
        this.description = TPH_FlyingPower.DESCRIPTIONS[0];
    }
    
    @Override
    public void atStartOfTurn() {
    	if (this.amount < this.resetAmount) {
    		this.amount = 3;
    	}
    }
    
    @Override
    public float atDamageFinalReceive(final float damage, final DamageInfo.DamageType type) {
        return this.calculateDamageTakenAmount(damage, type);
    }
    
    private float calculateDamageTakenAmount(final float damage, final DamageInfo.DamageType type) {
        if (type != DamageInfo.DamageType.HP_LOSS && type != DamageInfo.DamageType.THORNS) {
            return damage * this.damageMultiplier;
        }
        return damage;
    }
    
    @Override
    public int onAttacked(final DamageInfo info, final int damageAmount) {
        final Boolean willLive = this.calculateDamageTakenAmount(damageAmount, info.type) < this.owner.currentHealth;
        if (info.owner != null && info.type != DamageInfo.DamageType.HP_LOSS && info.type != DamageInfo.DamageType.THORNS && damageAmount > 0 && willLive) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));
        }
        return damageAmount;
    }
    
    /*@Override
    public void onRemove() {
        AbstractDungeon.actionManager.addToBottom(new ChangeStateAction((AbstractMonster)this.owner, "GROUNDED"));
    }*/
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("TPH_Flight");
        NAME = TPH_FlyingPower.powerStrings.NAME;
        DESCRIPTIONS = TPH_FlyingPower.powerStrings.DESCRIPTIONS;
    }
}