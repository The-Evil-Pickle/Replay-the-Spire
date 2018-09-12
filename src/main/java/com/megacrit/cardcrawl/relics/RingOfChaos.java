package com.megacrit.cardcrawl.relics;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import java.util.*;
import java.lang.*;
import replayTheSpire.*;
import replayTheSpire.panelUI.*;

public class RingOfChaos
  extends ReplayAbstractRelic
{
  public static final String ID = "Ring of Chaos";
  private boolean calledTransform = true;
  static final List<String> settingStrings = new ArrayList<String>();
  public static final ReplayOptionsSetting SETTING_MODE;
  static {
	  settingStrings.add("Don't avoid changes including magic number (more variety, more bugs)");
	  settingStrings.add("Avoid changes including only magic number (compromise option)");
	  settingStrings.add("Avoid all magic number changes (less variety, full stability)");
	  settingStrings.add("Never change any values on cards with magic number (for the extremely paranoid)");
	  SETTING_MODE = new ReplayOptionsSetting("chaos_mode", "(Avoiding these changes makes RoC less buggy, but also have less interesting effect variety)", 1, settingStrings);
  }
  public RingOfChaos()
  {
    super("Ring of Chaos", "cursedBlood.png", AbstractRelic.RelicTier.BOSS, AbstractRelic.LandingSound.FLAT);
  }
  public ArrayList<String> GetSettingStrings() {
		ArrayList<String> s = new ArrayList<String>();
		s.add(this.name);
		s.add("Avoid Ring Of Chaos magic number changes?");
		return s;
	}
  public ArrayList<ReplayRelicSetting> BuildRelicSettings() {
	  ArrayList<ReplayRelicSetting> r = new ArrayList<ReplayRelicSetting>();
	  r.add(SETTING_MODE);
		return r;
	}
  public static enum ChaosUpgradeType
  {
    MAGIC, DAMAGE, BLOCK, COST;
    
    private ChaosUpgradeType() {}
  }
  
  public String getUpdatedDescription()
  {
    return this.DESCRIPTIONS[0];
  }
  
  /*
  public void onObtainCard(AbstractCard c)
  {
    if (AbstractDungeon.miscRng.randomBoolean() && chaosUpgradeCard(c)) {
		AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy()));
	}
  }
  */

  public boolean chaosUpgradeCard(AbstractCard c)
  {
	  boolean r = ChaosScrambleCard(c);
	  if (r) {
		  this.flash();
	  }
	  return r;
  }
  
  public static boolean ChaosScrambleCard(AbstractCard c)
  {
	if (c.type == AbstractCard.CardType.CURSE || c.type == AbstractCard.CardType.STATUS) {
		return false;
	}
	
	if (c.type == AbstractCard.CardType.POWER && SETTING_MODE.value != 0) {//ReplayTheSpireMod.RingOfChaos_CompatibilityMode != ReplayTheSpireMod.ChaosMagicSetting.ALWAYS
		return false;
	}
	
	ArrayList<ChaosUpgradeType> upOp = new ArrayList<ChaosUpgradeType>();
	ArrayList<ChaosUpgradeType> dwnOp = new ArrayList<ChaosUpgradeType>();
	
	if (c.baseMagicNumber > 0 && c.rawDescription.contains("!M")) {
		if (SETTING_MODE.value == 2) {//if (ReplayTheSpireMod.RingOfChaos_CompatibilityMode == ReplayTheSpireMod.ChaosMagicSetting.STRICT) {
			return false;
		}
		if (SETTING_MODE.value == 0) {//if (ReplayTheSpireMod.RingOfChaos_CompatibilityMode == ReplayTheSpireMod.ChaosMagicSetting.ALWAYS) {
			upOp.add(ChaosUpgradeType.MAGIC);
			if (c.baseMagicNumber > 1) {
				dwnOp.add(ChaosUpgradeType.MAGIC);
			}
		}
	}  
	if (c.baseDamage > -1 && c.rawDescription.contains("!D")) {
		upOp.add(ChaosUpgradeType.DAMAGE);
		if (c.baseDamage > 1) {
			dwnOp.add(ChaosUpgradeType.DAMAGE);
		}
	}
	if (c.block > -1 && c.rawDescription.contains("!B")){
		upOp.add(ChaosUpgradeType.BLOCK);
		if (c.block > 1) {
			dwnOp.add(ChaosUpgradeType.BLOCK);
		}
	}
	if (c.cost >= 0){
		AbstractCard checkCard = c.makeCopy();
		checkCard.upgrade();
		if (!c.canUpgrade() || checkCard.cost == c.cost) {
			if (c.cost != 3) {
				dwnOp.add(ChaosUpgradeType.COST);
			}
			if (c.cost > 0) {
				upOp.add(ChaosUpgradeType.COST);
			}
		}
	}
	if (dwnOp.size() > 0) {
		if (upOp.size() > 0){
			
			int prevnum = 0;
			float downmult = 0.5f;
			float downtarg = 0.5f;
			boolean upAll = false;
			ChaosUpgradeType downside = dwnOp.remove(AbstractDungeon.miscRng.random(dwnOp.size() - 1));
			if (upOp.contains(downside)) {
				upOp.remove(downside);
				if (upOp.size() <= 0) {
					return false;
				}
			}
			if (downside == ChaosUpgradeType.COST) {
				if (c.baseMagicNumber > 0 && c.rawDescription.contains("!M") && 
				(SETTING_MODE.value == 1)) {//(ReplayTheSpireMod.RingOfChaos_CompatibilityMode == ReplayTheSpireMod.ChaosMagicSetting.COST_ONLY)) {
					upOp.add(ChaosUpgradeType.MAGIC);
					if (c.baseMagicNumber > 1) {
						dwnOp.add(ChaosUpgradeType.MAGIC);
					}
				}
			}
			ChaosUpgradeType upside = upOp.remove(AbstractDungeon.miscRng.random(upOp.size() - 1));
			if (dwnOp.contains(upside)) {
				dwnOp.remove(upside);
			}
			if (upside == ChaosUpgradeType.COST) {
				if (c.baseMagicNumber > 0 && c.rawDescription.contains("!M") && 
				(SETTING_MODE.value == 1)) {//(ReplayTheSpireMod.RingOfChaos_CompatibilityMode == ReplayTheSpireMod.ChaosMagicSetting.COST_ONLY)) {
					upOp.add(ChaosUpgradeType.MAGIC);
					if (c.baseMagicNumber > 1) {
						dwnOp.add(ChaosUpgradeType.MAGIC);
					}
				}
				switch(c.cost){
					case 1:
						downtarg = 0.4f;
						break;
					case 2:
						downtarg = 0.5f;
						break;
					case 3:
						downtarg = 0.67f;
						break;
					default:
						downtarg = 0.75f;
				}
			}
			int icounter = 0;
			while (icounter < 1 || (upside == ChaosUpgradeType.COST && dwnOp.size() > 0)) {
				if (icounter > 0) {
					downside = dwnOp.remove(AbstractDungeon.miscRng.random(dwnOp.size() - 1));
				}
				icounter += 1;
				switch(downside){
					case COST:
						int baseDiff = 1;
						switch(c.cost){
							case 0:
								downmult = 0.4f;
								break;
							case 1:
								if (AbstractDungeon.miscRng.random(0, 4) == 1) {
									baseDiff = 2;
									downmult = 0.33f;
								} else {
									downmult = 0.5f;
								}
								break;
							case 2:
								downmult = 0.67f;
								break;
							default:
								downmult = 0.75f;
						}
						int diff = c.cost - c.costForTurn;
						c.cost += baseDiff;
						if (c.cost < 0) {
						  c.cost = 0;
						}
						if (c.costForTurn >= 0) {
						  c.costForTurn = (c.cost - diff);
						}
						c.upgradedCost = true;
						c.isCostModified = true;
						upAll = true;
						break;
					case MAGIC:
						switch(c.baseMagicNumber){
							case 2:
								downmult = 0.5f;
								c.baseMagicNumber += -1;
								c.magicNumber = c.baseMagicNumber;
								c.upgradedMagicNumber = true;
								break;
							case 3:
								downmult = 0.67f;
								c.baseMagicNumber += -1;
								c.magicNumber = c.baseMagicNumber;
								c.upgradedMagicNumber = true;
								break;
							default:
								prevnum = c.baseMagicNumber;
								c.baseMagicNumber += (c.baseMagicNumber / (-1.0f / downtarg));
								c.magicNumber = c.baseMagicNumber;
								c.upgradedMagicNumber = true;
								downmult = (float)prevnum / (float)c.baseMagicNumber;
						}
						break;
					case DAMAGE:
						prevnum = c.baseDamage;
						c.baseDamage += (c.baseDamage / (-1.0f / downtarg));
						c.upgradedDamage = true;
						downmult = (float)prevnum / (float)c.baseDamage;
						break;
					case BLOCK:
						prevnum = c.baseBlock;
						c.baseBlock += (c.baseBlock / (-1.0f / downtarg));
						c.upgradedBlock = true;
						downmult = (float)prevnum / (float)c.baseBlock;
						break;
				}
			}
			icounter = 0;
			while (icounter < 1 || (upAll && upOp.size() > 0)) {
				if (icounter > 0) {
					upside = upOp.remove(AbstractDungeon.miscRng.random(upOp.size() - 1));
				}
				icounter += 1;
				switch(upside){
					case COST:
						int diff = c.cost - c.costForTurn;
						int baseDiff = MathUtils.round((float)c.cost * downmult) - c.cost;
						if (baseDiff >= 0) {
							baseDiff = -1;
						}
						c.cost += baseDiff;
						if (c.cost < 0) {
						  c.cost = 0;
						}
						if (c.costForTurn > 0) {
						  c.costForTurn = (c.cost - diff);
						}
						c.upgradedCost = true;
						c.isCostModified = true;
						break;
					case MAGIC:
						prevnum = MathUtils.ceilPositive((float)c.baseMagicNumber / downmult) - c.baseMagicNumber;
						if (prevnum <= 0) {
							prevnum = 1;
						}
						c.baseMagicNumber += prevnum;
						c.magicNumber = c.baseMagicNumber;
						c.upgradedMagicNumber = true;
						break;
					case DAMAGE:
						prevnum = MathUtils.ceilPositive((float)c.baseDamage / downmult) - c.baseDamage;
						if (prevnum <= 0) {
							prevnum = 1;
						}
						c.baseDamage += prevnum;
						c.upgradedDamage = true;
						break;
					case BLOCK:
						prevnum = MathUtils.ceilPositive((float)c.baseBlock / downmult) - c.baseBlock;
						if (prevnum <= 0) {
							prevnum = 1;
						}
						c.baseBlock += prevnum;
						c.upgradedBlock = true;
						break;
				}
			}
			c.name = c.name + "?";
			return true;
		}
	}
	return false;
  }
  /*
  public void onEquip()
  {
    this.calledTransform = false;
    ArrayList<AbstractCard> upgradableCards = new ArrayList<AbstractCard>();
    for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
        upgradableCards.add(c);
    }
    Collections.shuffle(upgradableCards, new java.util.Random(AbstractDungeon.miscRng.randomLong()));
	CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    if (!upgradableCards.isEmpty()) {
	  int remaining = 1 + (upgradableCards.size() / 3);
	  for (int i = 0; i < upgradableCards.size() && remaining > 0; i++) {
		  if (chaosUpgradeCard(upgradableCards.get(i))) {
			  remaining -= 1;
			  group.addToBottom(upgradableCards.get(i).makeStatEquivalentCopy());
		  }
	  }
    }
	if (!group.group.isEmpty()){
		AbstractDungeon.gridSelectScreen.openConfirmationGrid(group, this.DESCRIPTIONS[1]);
		flash();
	}
  }
  
  public void update()
  {
    super.update();
    if ((!this.calledTransform) && (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.GRID))
    {
      this.calledTransform = true;
      AbstractDungeon.getCurrRoom().rewardPopOutTimer = 0.25F;
    }
  }
  */
  
  public AbstractRelic makeCopy()
  {
    return new RingOfChaos();
  }
}
