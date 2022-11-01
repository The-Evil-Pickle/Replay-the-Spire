package com.megacrit.cardcrawl.mod.replay.relics;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.blue.*;
import com.megacrit.cardcrawl.cards.green.*;
import com.megacrit.cardcrawl.cards.red.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.cards.blue.ReplayOmegaCannon;
import com.megacrit.cardcrawl.mod.replay.cards.blue.ReplayRepulse;
import com.megacrit.cardcrawl.mod.replay.cards.blue.ReplaySort;
import com.megacrit.cardcrawl.mod.replay.cards.blue.SystemScan;
import com.megacrit.cardcrawl.mod.replay.cards.green.ExploitWeakness;
import com.megacrit.cardcrawl.mod.replay.cards.green.FluidMovement;
import com.megacrit.cardcrawl.mod.replay.cards.green.FromAllSides;
import com.megacrit.cardcrawl.mod.replay.cards.green.HiddenBlade;
import com.megacrit.cardcrawl.mod.replay.cards.green.PoisonSmokescreen;
import com.megacrit.cardcrawl.mod.replay.cards.green.ShivToss;
import com.megacrit.cardcrawl.mod.replay.cards.red.Hemogenesis;
import com.megacrit.cardcrawl.mod.replay.cards.red.LeadingStrike;
import com.megacrit.cardcrawl.mod.replay.cards.red.LifeLink;
import com.megacrit.cardcrawl.mod.replay.cards.red.Massacre;
import com.megacrit.cardcrawl.mod.replay.cards.red.MuscleTraining;
import com.megacrit.cardcrawl.mod.replay.cards.red.ReplayReversal;
import com.megacrit.cardcrawl.mod.replay.cards.red.ReplayStacked;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public abstract class M_MistRelic extends AbstractRelic
{
	CardColor mainColor;
	CardColor baseColor;
	int orbSlots;
	boolean hasbuilt = false;
    public M_MistRelic(String setId, String imgName, LandingSound sfx, CardColor mainColor, CardColor baseColor) {
    	this(setId, imgName, sfx, mainColor, baseColor, 0);
    }
    public M_MistRelic(String setId, String imgName, LandingSound sfx, CardColor mainColor, CardColor baseColor, int orbSlots) {
		super(setId, imgName, RelicTier.STARTER, sfx);
		this.mainColor = mainColor;
		this.baseColor = baseColor;
		this.orbSlots = orbSlots;
	}

	abstract ArrayList<AbstractCard> getNewCards();
	static boolean cardIsInPool(AbstractCard c) {
		switch (c.rarity) {
			case BASIC:
			case COMMON: {
				return (AbstractDungeon.srcCommonCardPool.findCardById(c.cardID) != null);
			}
			case UNCOMMON: {
				return (AbstractDungeon.srcUncommonCardPool.findCardById(c.cardID) != null);
			}
			case RARE: {
				return (AbstractDungeon.srcRareCardPool.findCardById(c.cardID) != null);
			}
			default: {
				return (AbstractDungeon.srcUncommonCardPool.findCardById(c.cardID) != null);
			}
		}
	}
	public void addCardsToPools() {
        for (final AbstractCard c : this.getNewCards()) {
        	if (!cardIsInPool(c)) {
        		switch (c.rarity) {
					case BASIC:
						c.rarity = AbstractCard.CardRarity.COMMON;
					case COMMON: {
						AbstractDungeon.commonCardPool.addToTop(c);
						AbstractDungeon.srcCommonCardPool.addToBottom(c);
						continue;
					}
					case UNCOMMON: {
						AbstractDungeon.uncommonCardPool.addToTop(c);
						AbstractDungeon.srcUncommonCardPool.addToBottom(c);
						continue;
					}
					case RARE: {
						AbstractDungeon.rareCardPool.addToTop(c);
						AbstractDungeon.srcRareCardPool.addToBottom(c);
						continue;
					}
					default: {
						AbstractDungeon.uncommonCardPool.addToTop(c);
						AbstractDungeon.srcUncommonCardPool.addToBottom(c);
						continue;
					}
				}
        	}
        }
		hasbuilt = true;
	}

	@Override
    public void onEquip() {
        this.addCardsToPools();
        AbstractDungeon.player.masterMaxOrbs = Math.max(this.orbSlots, AbstractDungeon.player.masterMaxOrbs);
    }
	@Override
    public void onEnterRoom(final AbstractRoom room) {
		if (!this.hasbuilt) {
			this.addCardsToPools();
			 AbstractDungeon.player.masterMaxOrbs = Math.max(this.orbSlots, AbstractDungeon.player.masterMaxOrbs);
		}
    }

    @Override
    public void atPreBattle() {
    	if (!this.hasbuilt) {
			this.addCardsToPools();
			 AbstractDungeon.player.masterMaxOrbs = Math.max(this.orbSlots, AbstractDungeon.player.masterMaxOrbs);
		}
    }
    
    
    

    public static void cardlist_Shivs(ArrayList<AbstractCard> tmpPool) {
    	tmpPool.add(new CloakAndDagger());
        tmpPool.add(new BladeDance());
        tmpPool.add(new InfiniteBlades());
        tmpPool.add(new HiddenBlade());
        tmpPool.add(new Accuracy());
        tmpPool.add(new ShivToss());
        tmpPool.add(new StormOfSteel());
    }
    public static void cardlist_Poison(ArrayList<AbstractCard> tmpPool) {
    	tmpPool.add(new Bane());
        tmpPool.add(new BouncingFlask());
        tmpPool.add(new CripplingPoison());
        tmpPool.add(new DeadlyPoison());
        tmpPool.add(new Envenom());
        tmpPool.add(new NoxiousFumes());
        tmpPool.add(new PoisonedStab());
        tmpPool.add(new PoisonSmokescreen());
    }
    public static void cardlist_Weak(ArrayList<AbstractCard> tmpPool) {
    	tmpPool.add(new SuckerPunch());
        tmpPool.add(new ExploitWeakness());
        tmpPool.add(new HeelHook());
        tmpPool.add(new LegSweep());
        tmpPool.add(new FromAllSides());
        tmpPool.add(new Malaise());
    }
    public static void cardlist_Strength(ArrayList<AbstractCard> tmpPool) {
        tmpPool.add(new DoubleTap());
        tmpPool.add(new Flex());
        tmpPool.add(new HeavyBlade());
        tmpPool.add(new Inflame());
        tmpPool.add(new LimitBreak());
        tmpPool.add(new SpotWeakness());
        tmpPool.add(new Massacre());
        tmpPool.add(new MuscleTraining());
    }
    public static void cardlist_Multihit(ArrayList<AbstractCard> tmpPool) {
        tmpPool.add(new DoubleTap());
        tmpPool.add(new SwordBoomerang());
        tmpPool.add(new Pummel());
        tmpPool.add(new Reaper());
        tmpPool.add(new TwinStrike());
        tmpPool.add(new Whirlwind());
    }
    public static void cardlist_Blood(ArrayList<AbstractCard> tmpPool) {
    	tmpPool.add(new Bloodletting());
        tmpPool.add(new BloodForBlood());
        tmpPool.add(new Combust());
        tmpPool.add(new Feed());
        tmpPool.add(new Hemokinesis());
        tmpPool.add(new Offering());
        tmpPool.add(new Reaper());
        tmpPool.add(new Rupture());

        tmpPool.add(new Hemogenesis());
        tmpPool.add(new LifeLink());
    }
    public static void cardlist_StatusRed(ArrayList<AbstractCard> tmpPool) {
    	tmpPool.add(new WildStrike());
    	tmpPool.add(new Evolve());
    	tmpPool.add(new FireBreathing());
    	tmpPool.add(new PowerThrough());
    	tmpPool.add(new RecklessCharge());
    }
    public static void cardlist_StatusBlue(ArrayList<AbstractCard> tmpPool) {
    	tmpPool.add(new Turbo());
    	tmpPool.add(new Overclock());
    	tmpPool.add(new ReplayRepulse());
    	tmpPool.add(new ReplaySort());
    	tmpPool.add(new SystemScan());
    	tmpPool.add(new ReplayOmegaCannon());
    }
    public static void cardlist_BlockRed(ArrayList<AbstractCard> tmpPool) {
    	tmpPool.add(new BodySlam());
    	tmpPool.add(new ShrugItOff());
    	tmpPool.add(new Entrench());
    	tmpPool.add(new FlameBarrier());
    	tmpPool.add(new GhostlyArmor());
    	tmpPool.add(new Metallicize());
    	tmpPool.add(new ReplayStacked());
    	tmpPool.add(new ReplayReversal());
    	tmpPool.add(new Barricade());
    	tmpPool.add(new Impervious());
    	tmpPool.add(new Juggernaut());
    }
    public static void cardlist_BlockGreen(ArrayList<AbstractCard> tmpPool) {
    	tmpPool.add(new Backflip());
    	tmpPool.add(new Deflect());
    	tmpPool.add(new DodgeAndRoll());
    	tmpPool.add(new Blur());
    	tmpPool.add(new Caltrops());
    	tmpPool.add(new Footwork());
    	tmpPool.add(new EscapePlan());
    	tmpPool.add(new FluidMovement());
    }
    
    
	@SpirePatch(cls = "com.megacrit.cardcrawl.helpers.CardLibrary", method = "initialize")
	public static class PatchThisShit {
		public static void Postfix() {
			if (AbstractDungeon.player != null) {
				for (AbstractRelic r : AbstractDungeon.player.relics) {
					if (r instanceof M_MistRelic) {
						((M_MistRelic)r).addCardsToPools();
					}
				}
			}
		}
	}
}