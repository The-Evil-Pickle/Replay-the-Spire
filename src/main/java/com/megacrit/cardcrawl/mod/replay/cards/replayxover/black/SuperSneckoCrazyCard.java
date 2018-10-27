package com.megacrit.cardcrawl.mod.replay.cards.replayxover.black;

import infinitespire.abstracts.*;
import madsciencemod.actions.common.ShuffleTrinketAction;
import madsciencemod.powers.FuelPower;
import mysticmod.MysticMod;
import replayTheSpire.ReplayTheSpireMod;
import replayTheSpire.variables.Exhaustive;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Shiv;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import beaked.actions.ReplenishWitherAction;
import beaked.cards.AbstractWitherCard;
import blackrusemod.powers.KnivesPower;
import blackrusemod.powers.ProtectionPower;
import chronomuncher.cards.Facsimile;
import chronomuncher.orbs.*;
import chronomuncher.powers.RetainOncePower;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.mod.replay.actions.common.ModifyExhaustiveAction;
import com.megacrit.cardcrawl.mod.replay.actions.common.ReplayRefundAction;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.core.*;

import java.util.*;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.SneckoField;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.unique.ArmamentsAction;
import com.megacrit.cardcrawl.actions.utility.QueueCardAction;

public class SuperSneckoCrazyCard extends BlackCard
{
    private static final String ID = "ReplayTheSpireMod:??????????????????????";
    private static final CardStrings cardStrings;
    private static final String NAME;
    private static final int COST = -1;
    private static final String DESCRIPTION;
    private static final String[] EXTENDED_DESCRIPTION;
    public static ArrayList<SSCCEffect> effects_src;
    private ArrayList<SSCCEffect> effects;

    public static abstract class SSCCEffect {
    	public String description;
    	public SSCCEffect(String description) {
    		this.description = description;
    	}
    	public abstract void use(final AbstractPlayer p, final AbstractCard c);
    	public abstract SSCCEffect makeCopy();
    }
    public static class SSCCE_Exhaust extends SSCCEffect {
    	public SSCCE_Exhaust() {
    		super(EXTENDED_DESCRIPTION[1]);
    	}
		@Override
		public void use(AbstractPlayer p, AbstractCard c) {
			AbstractDungeon.actionManager.addToBottom(new ExhaustAction(p, p, c.magicNumber, false, true, true));
		}
		@Override
    	public SSCCEffect makeCopy() {
			return new SSCCE_Exhaust();
		}
    }
    public static class SSCCE_Shivs extends SSCCEffect {
    	public SSCCE_Shivs() {
    		super(EXTENDED_DESCRIPTION[2]);
    	}
		@Override
		public void use(AbstractPlayer p, AbstractCard c) {
			AbstractCard shiv = new Shiv();
			shiv.upgrade();
			AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(shiv, c.magicNumber));
		}
		@Override
    	public SSCCEffect makeCopy() {
			return new SSCCE_Shivs();
		}
    }
    public static class SSCCE_Orbs extends SSCCEffect {
    	public SSCCE_Orbs() {
    		super(EXTENDED_DESCRIPTION[3]);
    	}
		@Override
		public void use(AbstractPlayer p, AbstractCard c) {
			for (int i=0; i < c.magicNumber; i++) {
				AbstractDungeon.actionManager.addToBottom(new ChannelAction(AbstractOrb.getRandomOrb(true)));
			}
		}
		@Override
    	public SSCCEffect makeCopy() {
			return new SSCCE_Orbs();
		}
    }
    public static class SSCCE_Refund extends SSCCEffect {
    	public SSCCE_Refund() {
    		super(EXTENDED_DESCRIPTION[4]);
    	}
		@Override
		public void use(AbstractPlayer p, AbstractCard c) {
			AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
		}
		@Override
    	public SSCCEffect makeCopy() {
			return new SSCCE_Refund();
		}
    }
    public static class SSCCE_Servant extends SSCCEffect {
    	public SSCCE_Servant() {
    		super(EXTENDED_DESCRIPTION[5]);
    	}
		@Override
		public void use(AbstractPlayer p, AbstractCard c) {
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new KnivesPower(p, c.magicNumber), c.magicNumber));
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ProtectionPower(p, c.magicNumber), c.magicNumber));
		}
		@Override
    	public SSCCEffect makeCopy() {
			return new SSCCE_Servant();
		}
    }
    public static class SSCCE_Science extends SSCCEffect {
    	public SSCCE_Science() {
    		super(EXTENDED_DESCRIPTION[6]);
    	}
		@Override
		public void use(AbstractPlayer p, AbstractCard c) {
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FuelPower(p, c.magicNumber), c.magicNumber));
			AbstractDungeon.actionManager.addToBottom(new ShuffleTrinketAction(c.magicNumber, true, true, false));
		}
		@Override
    	public SSCCEffect makeCopy() {
			return new SSCCE_Science();
		}
    }
    public static class SSCCE_Cores extends SSCCEffect {
    	public SSCCE_Cores() {
    		super(EXTENDED_DESCRIPTION[7]);
    	}
		@Override
		public void use(AbstractPlayer p, AbstractCard c) {
			for (int i = 0; i < c.magicNumber; ++i) {
	            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(constructmod.ConstructMod.getRandomCore(), 1, true, false));
	        }
		}
		@Override
    	public SSCCEffect makeCopy() {
			return new SSCCE_Cores();
		}
    }
    public static class SSCCE_Cantrips extends SSCCEffect {
    	public SSCCE_Cantrips() {
    		super(EXTENDED_DESCRIPTION[8]);
    	}
		@Override
		public void use(AbstractPlayer p, AbstractCard c) {
			for (int i = 0; i < c.magicNumber; ++i) {
	            final AbstractCard randomCantrip = MysticMod.cantripsGroup.get(AbstractDungeon.cardRandomRng.random(MysticMod.cantripsGroup.size() - 1)).makeCopy();
	            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(randomCantrip, 1, true, true));
	        }
		}
		@Override
    	public SSCCEffect makeCopy() {
			return new SSCCE_Cantrips();
		}
    }
    public static class SSCCE_Wither extends SSCCEffect {
    	public SSCCE_Wither() {
    		super(EXTENDED_DESCRIPTION[9]);
    	}
		@Override
		public void use(AbstractPlayer p, AbstractCard c) {
			AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, c.magicNumber));
			for (AbstractCard card : p.hand.group) {
				if (card instanceof AbstractWitherCard) {
					AbstractDungeon.actionManager.addToBottom(new ReplenishWitherAction((AbstractWitherCard)card, c.magicNumber-1));
				}
			}
			for (AbstractCard card : p.discardPile.group) {
				if (card instanceof AbstractWitherCard) {
					AbstractDungeon.actionManager.addToBottom(new ReplenishWitherAction((AbstractWitherCard)card, c.magicNumber-1));
				}
			}
			for (AbstractCard card : p.hand.group) {
				if (Exhaustive.ExhaustiveFields.baseExhaustive.get(card) > 0) {
					AbstractDungeon.actionManager.addToBottom(new ModifyExhaustiveAction(card, c.magicNumber-1));
				}
			}
			for (AbstractCard card : p.discardPile.group) {
				if (Exhaustive.ExhaustiveFields.baseExhaustive.get(card) > 0) {
					AbstractDungeon.actionManager.addToBottom(new ModifyExhaustiveAction(card, c.magicNumber-1));
				}
			}
		}
		@Override
    	public SSCCEffect makeCopy() {
			return new SSCCE_Wither();
		}
    }
    public static class SSCCE_Replicate extends SSCCEffect {
    	public SSCCE_Replicate() {
    		super(EXTENDED_DESCRIPTION[10]);
    	}
		@Override
		public void use(AbstractPlayer p, AbstractCard c) {
		    AbstractCard card = new Facsimile();
		    card.freeToPlayOnce = true;
		    card.baseMagicNumber = c.magicNumber;
		    card.magicNumber = c.magicNumber;
            AbstractDungeon.player.limbo.addToTop(card);
            card.target_x = Settings.WIDTH / 2;
            card.target_y = Settings.HEIGHT / 2;
            card.targetDrawScale *= 1.2f;
            AbstractDungeon.actionManager.addToBottom(new QueueCardAction(card, AbstractDungeon.getRandomMonster()));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RetainOncePower(c.magicNumber), c.magicNumber));
		}
		@Override
    	public SSCCEffect makeCopy() {
			return new SSCCE_Replicate();
		}
    }
    public SuperSneckoCrazyCard() {
        super(ID, NAME, "cards/replay/qmark.png", COST, DESCRIPTION + EXTENDED_DESCRIPTION[0], AbstractCard.CardType.SKILL, AbstractCard.CardTarget.SELF);
        this.purgeOnUse = true;
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
        SneckoField.snecko.set(this, true);
        this.effects = new ArrayList<SSCCEffect>();
    }
    
    public AbstractCard makeCopy() {
        return new SuperSneckoCrazyCard();
    }
    
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(2);
        }
    }
    @Override
    public void triggerWhenDrawn() {
        this.effects.clear();
        ArrayList<SSCCEffect> elist = new ArrayList<SSCCEffect>();
        for (SSCCEffect e : effects_src) {
        	elist.add(e.makeCopy());
        }
        for (int i=0; i <= this.costForTurn && !elist.isEmpty(); i++) {
        	this.effects.add(elist.remove(AbstractDungeon.miscRng.random(elist.size()-1)));
        }
        this.rawDescription = DESCRIPTION;
        for (SSCCEffect e : this.effects) {
        	this.rawDescription += e.description;
        }
        this.initializeDescription();
    }
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        for (SSCCEffect effect : this.effects) {
        	effect.use(p, this);
        }
    }
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = SuperSneckoCrazyCard.cardStrings.NAME;
        DESCRIPTION = SuperSneckoCrazyCard.cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = SuperSneckoCrazyCard.cardStrings.EXTENDED_DESCRIPTION;
        effects_src = new ArrayList<SSCCEffect>();
        effects_src.add(new SSCCE_Exhaust());
        effects_src.add(new SSCCE_Shivs());
        effects_src.add(new SSCCE_Orbs());
        if (Loader.isModLoaded("BlackRuseMod")) {
            effects_src.add(new SSCCE_Servant());
        }
        if (Loader.isModLoaded("MadScienceMod")) {
            effects_src.add(new SSCCE_Science());
        }
        if (Loader.isModLoaded("constructmod")) {
            effects_src.add(new SSCCE_Cores());
        }
        if (Loader.isModLoaded("MysticMod")) {
            effects_src.add(new SSCCE_Cantrips());
        }
        if (Loader.isModLoaded("chronomuncher")) {
            effects_src.add(new SSCCE_Replicate());
        }
        if (Loader.isModLoaded("beakedthecultist-sts") || ReplayTheSpireMod.foundmod_beaked) {
            effects_src.add(new SSCCE_Wither());
        }
        effects_src.add(new SSCCE_Refund());
    }
}
