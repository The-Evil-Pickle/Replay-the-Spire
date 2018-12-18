package com.megacrit.cardcrawl.mod.replay.powers;

import java.util.ArrayList;
import java.util.Collections;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.mod.replay.actions.common.ChooseAction;
import com.megacrit.cardcrawl.mod.replay.cards.status.BackFire;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.BeatOfDeathPower;
import com.megacrit.cardcrawl.powers.EnvenomPower;
import com.megacrit.cardcrawl.powers.HexPower;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import com.megacrit.cardcrawl.powers.SlowPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import basemod.ReflectionHacks;
import replayTheSpire.ReplayTheSpireMod;
import tobyspowerhouse.powers.TPH_ConfusionPower;

public class ColorShiftPower extends AbstractPower
{
    public static final String POWER_ID = "Replay:Color Shift";
    public static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private boolean justApplied;
    private AbstractColorShiftColor colorShift;
    private static final ArrayList<AbstractColorShiftColor> colorList;
    private static int turnid = 0; 
    public static Color mainShiftingColor = Color.RED;
    public static float mainShiftingCycle = 0;
    
    public ColorShiftPower(final AbstractCreature owner, int amount) {
        this.colorShift = null;
        this.name = ColorShiftPower.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.loadRegion("blur");
        this.justApplied = true;
    }
    
    public ColorShiftPower(AbstractColorShiftColor colorShift, AbstractCreature source, int amount, int turnid) {
        this.colorShift = colorShift;
    	this.name = this.colorShift.name;
        this.ID = POWER_ID + ":" + this.colorShift.name + "_" + turnid;
        if (colorShift.type == AbstractColorShiftColor.ColorShiftPowerType.BUFF) {
        	this.owner = source;
            this.justApplied = false;
        } else {
        	this.owner = AbstractDungeon.player;
        	this.type = PowerType.DEBUFF;
            this.justApplied = false;
        }
        this.amount = amount;
        this.isTurnBased = true;
        this.updateDescription();
        this.loadRegion("blur");
        this.colorShift = colorShift;
        ReflectionHacks.setPrivate(this, AbstractPower.class, "color", colorShift.color);
    }
    @Override
    public void renderIcons(final SpriteBatch sb, final float x, final float y, final Color c) {
    	if (this.colorShift == null) {
    		mainShiftingColor.r = ((0.05F * mainShiftingCycle) * 127F + 128F)/255F;
    		mainShiftingColor.g = ((0.05F * mainShiftingCycle + 2F*(((float)Math.PI)/3F)) * 127F + 128F)/255F;
    		mainShiftingColor.b = ((0.05F * mainShiftingCycle + 4F*(((float)Math.PI)/3F)) * 127F + 128F)/255F;
    		mainShiftingCycle++;
    		super.renderIcons(sb, x, y, mainShiftingColor);
    	} else {
    		super.renderIcons(sb, x, y, this.colorShift.color);//
    	}
    }
    
    @Override
    public void updateDescription() {
    	if (this.colorShift == null) {
    		this.description = ColorShiftPower.DESCRIPTIONS[0] + this.amount + ColorShiftPower.DESCRIPTIONS[1];
    	} else {
    		this.description = this.colorShift.powerDesc + this.amount + ColorShiftPower.DESCRIPTIONS[1];
    	}
    }
    
	@Override
    public void atEndOfTurn(final boolean isPlayer) {
		if (this.colorShift == null && !isPlayer) {
			final ChooseAction choice = new ChooseAction((AbstractCard)new Dazed(), null, "What color do you see?");
			Collections.shuffle(colorList);
			for (int i=0; i < 3; i++) {
				final AbstractColorShiftColor col = colorList.get(i);
				choice.add(col.name, col.cardDesc, () -> {
					AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(col.type == AbstractColorShiftColor.ColorShiftPowerType.BUFF ? this.owner : AbstractDungeon.player, this.owner, new ColorShiftPower(col, this.owner, this.amount, turnid)));
					this.owner.tint.changeColor(col.color.cpy());
		            return;
		        });
			}
	    	AbstractDungeon.actionManager.addToTop(choice);
	    	turnid++;
		}
        this.updateDescription();
	}

    @Override
    public void atEndOfRound() {
    	if (this.colorShift != null && this.isTurnBased) {
    		if (this.justApplied) {
                this.justApplied = false;
                return;
            }
            if (this.amount == 0) {
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
            }
            else {
                AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
            }
    	}
    }
	@Override
	public void onInitialApplication() {
		if (this.colorShift != null) {
			this.colorShift.onApply(this.owner, this.amount);
		}
	}
    @Override
    public void onRemove() {
    	if (this.colorShift != null) {
			this.colorShift.onRemove(this.owner);
		}
    }
    @Override
    public void atStartOfTurn() {
    	if (this.colorShift != null) {
			this.colorShift.onTurnStart(this.owner);
		}
    }
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = ColorShiftPower.powerStrings.NAME;
        DESCRIPTIONS = ColorShiftPower.powerStrings.DESCRIPTIONS;
        colorList = new ArrayList<AbstractColorShiftColor>();
        colorList.add(new ColorShiftYellow());
        colorList.add(new ColorShiftGreen());
        colorList.add(new ColorShiftRed());
        colorList.add(new ColorShiftOrange());
        colorList.add(new ColorShiftBlue());
        colorList.add(new ColorShiftPurple());
        colorList.add(new ColorShiftPale());
        colorList.add(new ColorShiftBronze());
        colorList.add(new ColorShiftDark());
    }
    
    public static abstract class AbstractColorShiftColor {
    	public int id;
    	public Color color;
    	public String name;
    	public String cardDesc;
    	public String powerDesc;
    	public ColorShiftPowerType type;
    	public static enum ColorShiftPowerType {
    		NONE,
    		DEBUFF,
    		BUFF
    	}
    	AbstractColorShiftColor(int id, Color color, ColorShiftPowerType type) {
    		this.id = id;
    		this.color = color;
    		this.name = ColorShiftPower.powerStrings.DESCRIPTIONS[id * 3 + 2];
    		this.cardDesc = ColorShiftPower.powerStrings.DESCRIPTIONS[id * 3 + 3];
    		this.powerDesc = ColorShiftPower.powerStrings.DESCRIPTIONS[id * 3 + 4];
    		this.type = type;
    	}
    	public void onApply(AbstractCreature c, int amt) {
    		
    	}
    	public void onRemove(AbstractCreature c) {
    		
    	}
    	public void onTurnStart(AbstractCreature c) {
    		
    	}
    }

    public static class ColorShiftYellow extends AbstractColorShiftColor {
		ColorShiftYellow() {
			super(0, Color.YELLOW, ColorShiftPowerType.DEBUFF);
		}
		public void onApply(AbstractCreature c, int amt) {
    		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(c, c, new TPH_ConfusionPower(c, amt)));
    	}
    }
    public static class ColorShiftGreen extends AbstractColorShiftColor {
		ColorShiftGreen() {
			super(1, Color.GREEN, ColorShiftPowerType.BUFF);
		}
		public void onApply(AbstractCreature c, int amt) {
    		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(c, c, new EnvenomPower(c, 1), 1));
    	}
    	public void onRemove(AbstractCreature c) {
    		AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(c, c, EnvenomPower.POWER_ID, 1));
    	}
    }
    public static class ColorShiftRed extends AbstractColorShiftColor {
		ColorShiftRed() {
			super(2, Color.RED, ColorShiftPowerType.DEBUFF);
		}
		public void onApply(AbstractCreature c, int amt) {
    		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(c, c, new VulnerablePower(c, amt, true), amt));
    	}
    }
    public static class ColorShiftOrange extends AbstractColorShiftColor {
    	public AbstractCard card;
		ColorShiftOrange() {
			super(3, Color.ORANGE, ColorShiftPowerType.DEBUFF);
			if (ReplayTheSpireMod.foundmod_hubris) {
				this.card = CardLibrary.getCopy("hubris:Acid");
			} else {
				this.card = new BackFire();
			}
			this.cardDesc = this.card.name;
			this.powerDesc = "#y" + this.card.name + this.powerDesc;
		}
		public void onTurnStart(AbstractCreature c) {
    		AbstractDungeon.actionManager.addToTop(new MakeTempCardInDrawPileAction(this.card.makeCopy(), 1, true, true));
    	}
    }
    public static class ColorShiftBlue extends AbstractColorShiftColor {
    	ColorShiftBlue() {
			super(4, Color.BLUE, ColorShiftPowerType.DEBUFF);
		}
		public void onApply(AbstractCreature c, int amt) {
    		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(c, c, new HexPower(c, 1), 1));
    	}
    	public void onRemove(AbstractCreature c) {
    		AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(c, c, HexPower.POWER_ID, 1));
    	}
    }
    public static class ColorShiftPurple extends AbstractColorShiftColor {
    	ColorShiftPurple() {
			super(5, Color.PURPLE, ColorShiftPowerType.DEBUFF);
		}
		public void onApply(AbstractCreature c, int amt) {
    		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(c, c, new SlowPower(c, 1), 1));
    	}
    	public void onRemove(AbstractCreature c) {
    		AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(c, c, SlowPower.POWER_ID, 1));
    	}
    }
    public static class ColorShiftPale extends AbstractColorShiftColor {
    	ColorShiftPale() {
			super(6, Color.LIGHT_GRAY, ColorShiftPowerType.BUFF);
		}
		public void onApply(AbstractCreature c, int amt) {
    		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(c, c, new IntangiblePower(c, amt), amt));
    	}
    }
    public static class ColorShiftBronze extends AbstractColorShiftColor {
		ColorShiftBronze() {
			super(7, new Color(80F/255F, 50F/255F, 20F/255F, 1F), ColorShiftPowerType.BUFF);
		}
		public void onApply(AbstractCreature c, int amt) {
    		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(c, c, new ThornsPower(c, 3), 3));
    	}
    	public void onRemove(AbstractCreature c) {
    		AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(c, c, ThornsPower.POWER_ID, 3));
    	}
    }
    public static class ColorShiftDark extends AbstractColorShiftColor {
		ColorShiftDark() {
			super(8, Color.DARK_GRAY, ColorShiftPowerType.BUFF);
		}
		public void onApply(AbstractCreature c, int amt) {
    		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(c, c, new BeatOfDeathPower(c, 1), 1));
    	}
    	public void onRemove(AbstractCreature c) {
    		AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(c, c, BeatOfDeathPower.POWER_ID, 1));
    	}
    }
}
