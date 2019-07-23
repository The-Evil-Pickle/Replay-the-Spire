package com.megacrit.cardcrawl.mod.replay.cards.replayxover.black;

import infinitespire.abstracts.*;
import madsciencemod.actions.common.ShuffleTrinketAction;
import madsciencemod.powers.FuelPower;
import mysticmod.MysticMod;
import replayTheSpire.ReplayTheSpireMod;
import slimebound.actions.SlimeSpawnAction;
import slimebound.orbs.AttackSlime;
import slimebound.orbs.PoisonSlime;
import slimebound.orbs.ShieldSlime;
import slimebound.orbs.SlimingSlime;
import slimebound.powers.SlimedPower;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Shiv;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import HalationCode.cards.LetterOfAdmiration;
import HalationCode.cards.LetterOfLove;
import HalationCode.cards.LetterOfRespect;
import ThMod.ThMod;
import ThMod.cards.derivations.Spark;
import basemod.helpers.TooltipInfo;
import beaked.actions.ReplenishWitherAction;
import beaked.cards.AbstractWitherCard;
import beaked.cards.Inspiration;
import beaked.characters.BeakedTheCultist;
import blackbeard.actions.EquipAction;
import blackbeard.orbs.CutlassOrb;
import blackrusemod.powers.KnivesPower;
import blackrusemod.powers.ProtectionPower;
import chronomuncher.actions.RandomReplicaAction;
import chronomuncher.cards.Facsimile;
import chronomuncher.orbs.*;
import chronomuncher.powers.RetainOncePower;
import clockworkmod.actions.CreateCogInDeckAction;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.mod.replay.actions.common.ReplayRefundAction;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.monsters.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.core.*;

import java.util.*;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.actions.common.ModifyExhaustiveAction;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.SneckoField;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.unique.ArmamentsAction;
import com.megacrit.cardcrawl.actions.utility.QueueCardAction;

public class SuperSneckoCrazyCard extends BlackCard
{
    public static final String ID = "ReplayTheSpireMod:??????????????????????";
    private static final CardStrings cardStrings;
    private static final String NAME;
    private static final int COST = 0;
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
    	
    	//if the player has no wither/exhaustive cards, shuffle inspiration cards rather than replenishing those.
    	public static boolean useGenericForm() {
    		if (AbstractDungeon.player == null) {
    			return true;
    		}
    		if (AbstractDungeon.player instanceof BeakedTheCultist) {
    			return false;
    		}
    		if (AbstractDungeon.player.masterDeck != null) {
    			for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
    				if (ExhaustiveField.ExhaustiveFields.baseExhaustive.get(c) > 0 || (c instanceof AbstractWitherCard)) {
    					return false;
    				}
    			}
    		}
    		return true;
    	}
    	public SSCCE_Wither() {
    		super(EXTENDED_DESCRIPTION[useGenericForm() ? 11 : 9]);
    	}
		@Override
		public void use(AbstractPlayer p, AbstractCard c) {
			AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, c.magicNumber));
			if (useGenericForm()) {
				AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Inspiration(), c.magicNumber, true, true));
			} else {
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
					if (ExhaustiveField.ExhaustiveFields.baseExhaustive.get(card) > 0) {
						AbstractDungeon.actionManager.addToBottom(new ModifyExhaustiveAction(card, c.magicNumber-1));
					}
				}
				for (AbstractCard card : p.discardPile.group) {
					if (ExhaustiveField.ExhaustiveFields.baseExhaustive.get(card) > 0) {
						AbstractDungeon.actionManager.addToBottom(new ModifyExhaustiveAction(card, c.magicNumber-1));
					}
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
		    /*AbstractCard card = new Facsimile();
		    card.freeToPlayOnce = true;
		    card.baseMagicNumber = c.magicNumber;
		    card.magicNumber = c.magicNumber;
            card.purgeOnUse = true;
            AbstractDungeon.player.limbo.addToTop(card);
            card.target_x = Settings.WIDTH / 2;
            card.target_y = Settings.HEIGHT / 2;
            card.targetDrawScale *= 1.2f;
            AbstractDungeon.actionManager.addToBottom(new QueueCardAction(card, AbstractDungeon.getRandomMonster()));*/
			for (int i=0; i<c.magicNumber; i++) {
				AbstractDungeon.actionManager.addToBottom(new RandomReplicaAction());
			}
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RetainOncePower(c.magicNumber), c.magicNumber));
		}
		@Override
    	public SSCCEffect makeCopy() {
			return new SSCCE_Replicate();
		}
    }
    public static class SSCCE_Weapon extends SSCCEffect {
    	public SSCCE_Weapon() {
    		super(EXTENDED_DESCRIPTION[12]);
    	}
		@Override
		public void use(AbstractPlayer p, AbstractCard c) {
			AbstractDungeon.actionManager.addToBottom(new EquipAction(new CutlassOrb(4, c.magicNumber, false)));
		}
		@Override
    	public SSCCEffect makeCopy() {
			return new SSCCE_Weapon();
		}
    }
    public static class SSCCE_Sparks extends SSCCEffect {
    	public SSCCE_Sparks() {
    		super(EXTENDED_DESCRIPTION[13]);
    	}
		@Override
		public void use(AbstractPlayer p, AbstractCard c) {
			AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Spark(), c.magicNumber));
			if (ThMod.Amplified(c, 1)) {
				AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(c.makeCopy(), 1));
			}
		}
		@Override
    	public SSCCEffect makeCopy() {
			return new SSCCE_Sparks();
		}
    }
    public static class SSCCE_Cogs extends SSCCEffect {
    	public SSCCE_Cogs() {
    		super(EXTENDED_DESCRIPTION[14]);
    	}
		@Override
		public void use(AbstractPlayer p, AbstractCard c) {
			AbstractDungeon.actionManager.addToBottom(new CreateCogInDeckAction(c.magicNumber));
		}
		@Override
    	public SSCCEffect makeCopy() {
			return new SSCCE_Cogs();
		}
    }
    public static class SSCCE_Slimed extends SSCCEffect {
    	public SSCCE_Slimed() {
    		super(EXTENDED_DESCRIPTION[15]);
    	}
		@Override
		public void use(AbstractPlayer p, AbstractCard c) {
			final ArrayList<Integer> orbs = new ArrayList<Integer>();
	        orbs.add(1);
	        orbs.add(2);
	        orbs.add(3);
	        orbs.add(4);
	        final Integer o = orbs.get(AbstractDungeon.cardRng.random(orbs.size() - 1));
	        switch (o) {
	            case 1: {
	                AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new AttackSlime(), false, true));
	                break;
	            }
	            case 2: {
	                AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new ShieldSlime(), false, true));
	                break;
	            }
	            case 3: {
	                AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new SlimingSlime(), false, true));
	                break;
	            }
	            case 4: {
	                AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new PoisonSlime(), false, true));
	                break;
	            }
	        }
	        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
	        	if (m != null && !m.isDeadOrEscaped()) {
	        		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new SlimedPower(m, p, c.magicNumber), c.magicNumber));
	        	}
	        }
		}
		@Override
    	public SSCCEffect makeCopy() {
			return new SSCCE_Slimed();
		}
    }
    public static class SSCCE_Letters extends SSCCEffect {
    	public SSCCE_Letters() {
    		super(EXTENDED_DESCRIPTION[16]);
    	}
		@Override
		public void use(AbstractPlayer p, AbstractCard c) {
			ArrayList<AbstractCard> letterCards = new ArrayList<AbstractCard>();
			letterCards.add(new LetterOfAdmiration());
            letterCards.add(new LetterOfLove());
            letterCards.add(new LetterOfRespect());
			for (int i = 0; i < c.magicNumber; ++i) {
	            final AbstractCard randomCantrip = letterCards.get(AbstractDungeon.cardRandomRng.random(letterCards.size() - 1)).makeCopy();
	            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(randomCantrip, 1, true, true));
	        }
		}
		@Override
    	public SSCCEffect makeCopy() {
			return new SSCCE_Letters();
		}
    }
    private String renderRollDesc;
    private int renderRollCountdown;
    public SuperSneckoCrazyCard() {
        super(ID, NAME, "cards/replay/qmark.png", COST, DESCRIPTION + EXTENDED_DESCRIPTION[0], AbstractCard.CardType.SKILL, AbstractCard.CardTarget.SELF);
        this.purgeOnUse = true;
        this.tips = new ArrayList<TooltipInfo>();
        this.renderRollCountdown = 0;
        this.renderRollDesc = "";
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
        SneckoField.snecko.set(this, true);
        this.effects = new ArrayList<SSCCEffect>();
        if ((AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead())) {
        	final int newCost = AbstractDungeon.cardRandomRng.random(3);
            if (this.cost != newCost) {
            	this.cost = newCost;
            	this.costForTurn = this.cost;
            	this.isCostModified = true;
            }
        	this.rollEffects();
        }
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
    public void rollEffects() {
    	this.effects.clear();
        ArrayList<SSCCEffect> elist = new ArrayList<SSCCEffect>();
        for (SSCCEffect e : effects_src) {
        	elist.add(e.makeCopy());
        }
        for (int i=0; i <= Math.max(0, this.costForTurn) && !elist.isEmpty(); i++) {
        	this.effects.add(elist.remove(AbstractDungeon.miscRng.random(elist.size()-1)));
        }
        this.rawDescription = DESCRIPTION;
        for (SSCCEffect e : this.effects) {
        	this.rawDescription += e.description;
        }
        this.initializeDescription();
    }
    @Override
    public void triggerWhenDrawn() {
        this.rollEffects();
    }
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        for (SSCCEffect effect : this.effects) {
        	effect.use(p, this);
        }
    }
    public ArrayList<TooltipInfo> tips;
    @Override
    public List<TooltipInfo> getCustomTooltips() {
    	this.tips.clear();
        this.tips.add(new TooltipInfo("???????", EXTENDED_DESCRIPTION[17]));
        return this.tips;
    }
    @Override
    public void render(SpriteBatch sb) {
    	if (this.hb.hovered && this.rawDescription.equals(DESCRIPTION + EXTENDED_DESCRIPTION[0])) {
    		this.renderRollCountdown--;
    		if (this.renderRollCountdown <= 0) {
    			this.renderRollDesc = DESCRIPTION + effects_src.get((AbstractDungeon.miscRng.random(effects_src.size()-1))).description;
    			this.renderRollCountdown = 6;
    		}
    		this.rawDescription = this.renderRollDesc;
    		this.initializeDescription();
    		super.render(sb);
    		this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[0];
    		this.initializeDescription();
    	} else {
    		super.render(sb);
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
        if (Loader.isModLoaded("beakedthecultist-sts")) {
            effects_src.add(new SSCCE_Wither());
        }
        if (Loader.isModLoaded("sts-mod-the-blackbeard")) {
            effects_src.add(new SSCCE_Weapon());
        }
        if (Loader.isModLoaded("TS05_Marisa")) {
            effects_src.add(new SSCCE_Sparks());
        }
        if (Loader.isModLoaded("ClockworkMod")) {
            effects_src.add(new SSCCE_Cogs());
        }
        if (Loader.isModLoaded("Slimebound")) {
            effects_src.add(new SSCCE_Slimed());
        }
        if (Loader.isModLoaded("Halation")) {
            effects_src.add(new SSCCE_Letters());
        }
        effects_src.add(new SSCCE_Refund());
    }
}
