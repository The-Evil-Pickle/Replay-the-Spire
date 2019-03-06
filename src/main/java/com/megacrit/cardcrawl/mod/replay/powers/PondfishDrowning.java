package com.megacrit.cardcrawl.mod.replay.powers;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.actions.utility.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.mod.replay.orbs.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.mod.replay.vfx.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.vfx.combat.*;
import com.megacrit.cardcrawl.helpers.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.*;
import basemod.*;
import basemod.interfaces.CloneablePowerInterface;
import replayTheSpire.*;

public class PondfishDrowning extends AbstractPower implements CloneablePowerInterface
{
    public static final String POWER_ID = "PondfishDrowning";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
	private boolean atActiveDepth;
	private static int MINVAL = -1;
	private static int MAXVAL = 10;
    private Color color2;
    private Color redColor2;
    private Color greenColor2;
	
    public PondfishDrowning(final AbstractCreature owner) {
		this(owner, 0);
	}
    
    public PondfishDrowning(final AbstractCreature owner, final int amount) {
        this.name = PondfishDrowning.NAME;
        this.ID = "PondfishDrowning";
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.loadRegion("drowning");
        this.canGoNegative = true;
        this.redColor2 = new Color(1.0f, 0.0f, 0.0f, 1.0f);
        this.greenColor2 = new Color(0.0f, 1.0f, 0.0f, 1.0f);
    }
    
	@Override
    protected void loadRegion(final String fileName) {
        this.region48 = ReplayTheSpireMod.powerAtlas.findRegion("48/" + fileName);
		this.region128 = ReplayTheSpireMod.powerAtlas.findRegion("128/" + fileName);
    }
	
    @Override
    public void updateDescription() {
		if (this.amount < 0) {
			//this.description = PondfishDrowning.DESCRIPTIONS[1] + (this.amount * -1) + PondfishDrowning.DESCRIPTIONS[2] + PondfishDrowning.MINVAL + PondfishDrowning.DESCRIPTIONS[3] + PondfishDrowning.MAXVAL + ".";
			//this.type = PowerType.BUFF;
			this.description = PondfishDrowning.DESCRIPTIONS[0] + 0 + PondfishDrowning.DESCRIPTIONS[2] + PondfishDrowning.MINVAL + PondfishDrowning.DESCRIPTIONS[3] + PondfishDrowning.MAXVAL + ".";
		} else {
			this.description = PondfishDrowning.DESCRIPTIONS[0] + this.amount + PondfishDrowning.DESCRIPTIONS[2] + PondfishDrowning.MINVAL + PondfishDrowning.DESCRIPTIONS[3] + PondfishDrowning.MAXVAL + ".";
			//this.type = PowerType.DEBUFF;
		}
		this.checkForAbe();
    }
	
	@Override
    public void stackPower(final int stackAmount) {
        this.fontScale = 8.0f;
        if (this.amount + stackAmount >= PondfishDrowning.MAXVAL) {
            this.fontScale = 8.0f;
            this.amount = PondfishDrowning.MAXVAL;
        }
        else {
            this.fontScale = 8.0f;
            this.amount += stackAmount;
        }
    }
    
	@Override
    public void reducePower(final int reduceAmount) {
		this.fontScale = 8.0f;
        if (this.amount - reduceAmount <= PondfishDrowning.MINVAL) {
            this.fontScale = 8.0f;
            this.amount = PondfishDrowning.MINVAL;
        }
        else {
            this.fontScale = 8.0f;
            this.amount -= reduceAmount;
        }
    }
	
	private void checkForAbe() {
		if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            //this.flash();
            for (final AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                if (m != null && m.hasPower("AbePower")) {
					((AbePower)m.getPower("AbePower")).updateValue(this.amount);
				}
            }
        }
	}
	
    /*@Override
    public void onUseCard(final AbstractCard card, final UseCardAction action) {
        if (this.amount > PondfishDrowning.MINVAL && card.type == AbstractCard.CardType.SKILL) {
            this.flashWithoutSound();
            this.reducePower(1);
			this.updateDescription();
        }
    }*/
	
    @Override
    public void onAttack(final DamageInfo info, final int damageAmount, final AbstractCreature target) {
        if (damageAmount > 0 && target != this.owner && info.type == DamageInfo.DamageType.NORMAL) {
            this.flash();
            this.reducePower(1);
			this.updateDescription();
        }
    }
	
	@Override
    public int onAttacked(final DamageInfo info, final int damageAmount) {
        if (info.owner != null && info.type != DamageInfo.DamageType.HP_LOSS && info.type != DamageInfo.DamageType.THORNS && damageAmount > 0 && !this.owner.hasPower("Buffer")) {
            this.flash();
            this.stackPower(1);
			this.updateDescription();
        }
        return damageAmount;
    }
	
    @Override
    public void atEndOfTurn(final boolean isPlayer) {
		if (this.amount > 0) {
			this.flash();
			AbstractDungeon.actionManager.addToBottom(new DamageAction(this.owner, new DamageInfo(this.owner, this.amount, DamageInfo.DamageType.HP_LOSS)));
		}
		/*else if (this.amount < 0) {
			this.flash();
			AbstractDungeon.actionManager.addToBottom(new HealAction(this.owner, this.owner, this.amount * -1));
		}*/
    }
	
	@Override
    public void renderAmount(final SpriteBatch sb, final float x, final float y, Color c) {
        if (this.amount > 0) {
            if (!this.isTurnBased) {
                this.redColor2.a = c.a;
                c = this.redColor2;
            }
            FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(this.amount), x, y, this.fontScale, c);
        }
        else if (this.amount <= 0) {
            this.greenColor2.a = c.a;
            c = this.greenColor2;
            FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(this.amount), x, y, this.fontScale, c);
        }
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Drowning");
        NAME = PondfishDrowning.powerStrings.NAME;
        DESCRIPTIONS = PondfishDrowning.powerStrings.DESCRIPTIONS;
    }

	@Override
	public AbstractPower makeCopy() {
		return new PondfishDrowning(owner, amount);
	}
}
