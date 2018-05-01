package com.megacrit.cardcrawl.gashapon;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.vfx.cardManip.*;
import com.megacrit.cardcrawl.saveAndContinue.*;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.rewards.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.red.*;
import com.megacrit.cardcrawl.cards.green.*;
import com.megacrit.cardcrawl.cards.blue.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.potions.*;
import com.megacrit.cardcrawl.screens.select.*;
import java.util.*;
import org.apache.logging.log4j.*;

public class NeowReward
{
    private static final Logger logger;
    private static final CharacterStrings characterStrings;
    public static final String[] NAMES;
    public static final String[] TEXT;
    public static final String[] UNIQUE_REWARDS;
    public String optionLabel;
    public NeowRewardType type;
    public NeowRewardDrawback drawback;
    private boolean activated;
    private int hp_bonus;
    private boolean cursed;
	public static boolean isRelicScreenOpen;
    private static final int GOLD_BONUS = 100;
    private static final int LARGE_GOLD_BONUS = 250;
	private static final int STRIKE_COUNT = 2;
	private static final int DEFEND_COUNT = 2;
	public static GenericRelicSelectScreen relicScreen = new GenericRelicSelectScreen();
    
    public NeowReward(final int category) {
        this.optionLabel = "";
        this.drawback = NeowRewardDrawback.NONE;
        this.activated = false;
        this.hp_bonus = 0;
		NeowReward.isRelicScreenOpen = false;
        this.cursed = false;
        this.hp_bonus = (int)(AbstractDungeon.player.maxHealth * 0.1f);
        switch (category) {
            case 0: {
                this.rollCardReward();
                break;
            }
            case 1: {
                this.rollNonCardReward();
                break;
            }
            case 2: {
                this.rollChaosDrawback();
                this.rollChaosReward();
                break;
            }
            case 3: {
                this.rollUniqueReward();
                break;
            }
        }
    }
    
    private void rollCardReward() {
        switch (NeowEvent.rng.random(0, 5)) {
            case 0: {
                this.type = NeowRewardType.THREE_CARDS;
                this.optionLabel += NeowReward.TEXT[0];
                break;
            }
            case 1: {
                this.type = NeowRewardType.ONE_RANDOM_RARE_CARD;
                this.optionLabel += NeowReward.TEXT[1];
                break;
            }
            case 2: {
                this.type = NeowRewardType.REMOVE_CARD;
                this.optionLabel += NeowReward.TEXT[2];
                break;
            }
            case 3: {
                this.type = NeowRewardType.UPGRADE_CARD;
                this.optionLabel += NeowReward.TEXT[3];
                break;
            }
            case 4: {
                this.type = NeowRewardType.COLORLESS_CARD;
                this.optionLabel += "[ #gObtain #ga #grandom #gcolorless #gCard ]";
                break;
            }
            default: {
                this.type = NeowRewardType.TRANSFORM_CARD;
                this.optionLabel += NeowReward.TEXT[4];
                break;
            }
        }
    }
    
    private void rollNonCardReward() {
        switch (NeowEvent.rng.random(1, 5)) {
            case 1: {
                this.type = NeowRewardType.THREE_SMALL_POTIONS;
                this.optionLabel += NeowReward.TEXT[5];
                break;
            }
            case 2: {
                this.type = NeowRewardType.RANDOM_COMMON_RELIC;
                this.optionLabel += NeowReward.TEXT[6];
                break;
            }
            case 3: {
                this.type = NeowRewardType.TEN_PERCENT_HP_BONUS;
                this.optionLabel = this.optionLabel + NeowReward.TEXT[7] + this.hp_bonus + " ]";
                break;
            }
            case 4: {
                this.type = NeowRewardType.THREE_ENEMY_KILL;
                this.optionLabel += NeowReward.TEXT[28];
                break;
            }
            default: {
                this.type = NeowRewardType.HUNDRED_GOLD;
                this.optionLabel = this.optionLabel + NeowReward.TEXT[8] + 100 + NeowReward.TEXT[9];
                break;
            }
        }
    }
    
    private void rollChaosReward() {
        final ArrayList<NeowRewardType> possibleRewards = new ArrayList<NeowRewardType>();
        possibleRewards.add(NeowRewardType.REMOVE_TWO);
        possibleRewards.add(NeowRewardType.ONE_RARE_RELIC);
        possibleRewards.add(NeowRewardType.THREE_RARE_CARDS);
        possibleRewards.add(NeowRewardType.TWO_FIFTY_GOLD);
        possibleRewards.add(NeowRewardType.TRANSFORM_TWO_CARDS);
        possibleRewards.add(NeowRewardType.TWENTY_PERCENT_HP_BONUS);
        //possibleRewards.add(NeowRewardType.UNCOMMON_RELIC);
        switch (this.drawback) {
            case TEN_PERCENT_HP_LOSS: {
                possibleRewards.remove(NeowRewardType.TWENTY_PERCENT_HP_BONUS);
                break;
            }
            case NO_GOLD: {
                possibleRewards.remove(NeowRewardType.TWO_FIFTY_GOLD);
                break;
            }
            case CURSE: {
                possibleRewards.remove(NeowRewardType.REMOVE_TWO);
                break;
            }
            case MORE_STRIKES_AND_DEFENDS: {
                possibleRewards.remove(NeowRewardType.REMOVE_TWO);
                break;
            }
        }
        final NeowRewardType reward = possibleRewards.get(NeowEvent.rng.random(0, possibleRewards.size() - 1));
        switch (reward) {
            case REMOVE_TWO: {
                this.type = NeowRewardType.REMOVE_TWO;
                this.optionLabel += NeowReward.TEXT[10];
                break;
            }
            case ONE_RARE_RELIC: {
                this.type = NeowRewardType.ONE_RARE_RELIC;
                this.optionLabel += NeowReward.TEXT[11];
                break;
            }
            case THREE_RARE_CARDS: {
                this.type = NeowRewardType.THREE_RARE_CARDS;
                this.optionLabel += NeowReward.TEXT[12];
                break;
            }
            case TWO_FIFTY_GOLD: {
                this.type = NeowRewardType.TWO_FIFTY_GOLD;
                this.optionLabel = this.optionLabel + NeowReward.TEXT[13] + 250 + NeowReward.TEXT[14];
                break;
            }
            case TRANSFORM_TWO_CARDS: {
                this.type = NeowRewardType.TRANSFORM_TWO_CARDS;
                this.optionLabel += NeowReward.TEXT[15];
                break;
            }
            case TWENTY_PERCENT_HP_BONUS: {
                this.type = NeowRewardType.TWENTY_PERCENT_HP_BONUS;
                this.optionLabel = this.optionLabel + NeowReward.TEXT[16] + this.hp_bonus * 2 + " ]";
                break;
            }
			case UNCOMMON_RELIC: {
				this.type = NeowRewardType.UNCOMMON_RELIC;
				this.optionLabel += "#gChoose #gan #guncommon #gRelic #gto #gobtain ]";
				break;
			}
            default: {
                this.type = NeowRewardType.ONE_RARE_RELIC;
                this.optionLabel += NeowReward.TEXT[11];
                break;
            }
        }
    }
    
    private void rollChaosDrawback() {
        switch (NeowEvent.rng.random(0, 4)) {
            case 0: {
                this.drawback = NeowRewardDrawback.TEN_PERCENT_HP_LOSS;
                this.optionLabel = this.optionLabel + NeowReward.TEXT[17] + this.hp_bonus + NeowReward.TEXT[18];
                break;
            }
            case 1: {
                this.drawback = NeowRewardDrawback.NO_GOLD;
                this.optionLabel += NeowReward.TEXT[19];
                break;
            }
            case 2: {
                this.drawback = NeowRewardDrawback.CURSE;
                this.optionLabel += NeowReward.TEXT[20];
                break;
            }
            case 3: {
                this.drawback = NeowRewardDrawback.FIFTY_PERCENT_DAMAGE;
                this.optionLabel += NeowReward.TEXT[21];
                break;
            }
            case 4: {
                this.drawback = NeowRewardDrawback.MORE_STRIKES_AND_DEFENDS;
                this.optionLabel = this.optionLabel + "[ #rObtain #r" + NeowReward.STRIKE_COUNT + " #rStrikes #rand #r" + NeowReward.DEFEND_COUNT + " #rDefends ";
                break;
            }
        }
    }
    
    private void rollUniqueReward() {
        switch (NeowEvent.rng.random(0, 2)) {
            default: {
                this.type = NeowRewardType.BOSS_RELIC;
                this.optionLabel += NeowReward.UNIQUE_REWARDS[0];
            }
        }
    }
    
    public void update() {
        if (this.activated) {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                switch (this.type) {
                    case UPGRADE_CARD: {
                        final AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                        c.upgrade();
                        AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy()));
                        AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f));
                        break;
                    }
                    case REMOVE_CARD: {
                        CardCrawlGame.sound.play("CARD_EXHAUST");
                        AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(AbstractDungeon.gridSelectScreen.selectedCards.get(0), Settings.WIDTH / 2, Settings.HEIGHT / 2));
                        AbstractDungeon.player.masterDeck.removeCard(AbstractDungeon.gridSelectScreen.selectedCards.get(0));
                        break;
                    }
                    case REMOVE_TWO: {
                        CardCrawlGame.sound.play("CARD_EXHAUST");
                        final AbstractCard c2 = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                        final AbstractCard c3 = AbstractDungeon.gridSelectScreen.selectedCards.get(1);
                        AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c2, Settings.WIDTH / 2.0f - AbstractCard.IMG_WIDTH / 2.0f - 30.0f * Settings.scale, Settings.HEIGHT / 2));
                        AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c3, Settings.WIDTH / 2.0f + AbstractCard.IMG_WIDTH / 2.0f + 30.0f * Settings.scale, Settings.HEIGHT / 2.0f));
                        AbstractDungeon.player.masterDeck.removeCard(c2);
                        AbstractDungeon.player.masterDeck.removeCard(c3);
                        break;
                    }
                    case TRANSFORM_CARD: {
                        AbstractDungeon.transformCard(AbstractDungeon.gridSelectScreen.selectedCards.get(0));
                        AbstractDungeon.player.masterDeck.removeCard(AbstractDungeon.gridSelectScreen.selectedCards.get(0));
                        AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(AbstractDungeon.getTransformedCard(), Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f));
                        break;
                    }
                    case TRANSFORM_TWO_CARDS: {
                        final AbstractCard t1 = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                        final AbstractCard t2 = AbstractDungeon.gridSelectScreen.selectedCards.get(1);
                        AbstractDungeon.player.masterDeck.removeCard(t1);
                        AbstractDungeon.player.masterDeck.removeCard(t2);
                        AbstractDungeon.transformCard(t1);
                        AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(AbstractDungeon.getTransformedCard(), Settings.WIDTH / 2.0f - AbstractCard.IMG_WIDTH / 2.0f - 30.0f * Settings.scale, Settings.HEIGHT / 2.0f));
                        AbstractDungeon.transformCard(t2);
                        AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(AbstractDungeon.getTransformedCard(), Settings.WIDTH / 2.0f + AbstractCard.IMG_WIDTH / 2.0f + 30.0f * Settings.scale, Settings.HEIGHT / 2.0f));
                        break;
                    }
                }
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                AbstractDungeon.overlayMenu.cancelButton.hide();
                if (!Settings.isDailyRun) {
                    final SaveFile saveFile = new SaveFile(SaveFile.SaveType.POST_NEOW);
                    SaveAndContinue.save(saveFile);
                    AbstractDungeon.effectList.add(new GameSavedEffect());
                }
                this.activated = false;
            }
			if (this.type == NeowRewardType.UNCOMMON_RELIC) {
				NeowReward.relicScreen.update();
				if (NeowReward.relicScreen.relics.size() <= 0) {
					this.activated = false;
					NeowReward.isRelicScreenOpen = false;
					AbstractDungeon.overlayMenu.cancelButton.hide();
					if (!Settings.isDailyRun) {
						final SaveFile saveFile = new SaveFile(SaveFile.SaveType.POST_NEOW);
						SaveAndContinue.save(saveFile);
						AbstractDungeon.effectList.add(new GameSavedEffect());
					}
				}
			}
            if (this.cursed) {
                this.cursed = !this.cursed;
                AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(AbstractDungeon.getCardWithoutRng(AbstractCard.CardRarity.CURSE), Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f));
            }
        }
    }
    
    public void activate() {
        this.activated = true;
        switch (this.type) {
            case THREE_RARE_CARDS: {
                AbstractDungeon.cardRewardScreen.open(this.getRewardCards(true), null, NeowReward.TEXT[22]);
                break;
            }
            case HUNDRED_GOLD: {
                CardCrawlGame.sound.play("GOLD_JINGLE");
                AbstractDungeon.player.gainGold(100);
                break;
            }
            case ONE_RANDOM_RARE_CARD: {
                AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(AbstractDungeon.getCard(AbstractCard.CardRarity.RARE, NeowEvent.rng).makeCopy(), Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f));
                break;
            }
            case RANDOM_COMMON_RELIC: {
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.COMMON));
                break;
            }
            case ONE_RARE_RELIC: {
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.RARE));
                break;
            }
            case BOSS_RELIC: {
                AbstractDungeon.player.loseRelic(AbstractDungeon.player.relics.get(0).relicId);
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.BOSS));
                break;
            }
            case THREE_ENEMY_KILL: {
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, new NeowsLament());
                break;
            }
            case REMOVE_CARD: {
                AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getPurgeableCards(), 1, NeowReward.TEXT[23], false, false, false, true);
                break;
            }
            case REMOVE_TWO: {
                AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getPurgeableCards(), 2, NeowReward.TEXT[24], false, false, false, false);
                break;
            }
            case TEN_PERCENT_HP_BONUS: {
                AbstractDungeon.player.increaseMaxHp(this.hp_bonus, true);
                break;
            }
            case THREE_CARDS: {
                AbstractDungeon.cardRewardScreen.open(this.getRewardCards(false), null, CardCrawlGame.languagePack.getUIString("CardRewardScreen").TEXT[1]);
                break;
            }
            case THREE_HUGE_POTIONS: {
                CardCrawlGame.sound.play("POTION_1");
                for (int i = 0; i < 3; ++i) {
                    this.getPotion(PotionHelper.getRandomPotion(NeowEvent.rng));
                }
                break;
            }
            case THREE_SMALL_POTIONS: {
                CardCrawlGame.sound.play("POTION_1");
                for (int i = 0; i < 3; ++i) {
                    this.getPotion(PotionHelper.getRandomPotion(NeowEvent.rng));
                }
                break;
            }
            case TRANSFORM_CARD: {
                AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getPurgeableCards(), 1, NeowReward.TEXT[25], false, true, false, false);
                break;
            }
            case TRANSFORM_TWO_CARDS: {
                AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getPurgeableCards(), 2, NeowReward.TEXT[26], false, false, false, false);
                break;
            }
            case TWENTY_PERCENT_HP_BONUS: {
                AbstractDungeon.player.increaseMaxHp(this.hp_bonus * 2, true);
                break;
            }
            case TWO_FIFTY_GOLD: {
                CardCrawlGame.sound.play("GOLD_JINGLE");
                AbstractDungeon.player.gainGold(250);
                break;
            }
            case UPGRADE_CARD: {
                AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getUpgradableCards(), 1, NeowReward.TEXT[27], true, false, false, false);
                break;
            }
            case COLORLESS_CARD: {
                AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(AbstractDungeon.returnTrulyRandomColorlessCard(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                break;
            }
            case UNCOMMON_RELIC: {
                ArrayList<AbstractRelic> relics = new ArrayList<AbstractRelic>();
				final AbstractRelic r = AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.UNCOMMON);
				relics.add(r);
				final AbstractRelic r2 = AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.UNCOMMON);
				relics.add(r2);
				final AbstractRelic r3 = AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.UNCOMMON);
				relics.add(r3);
				NeowReward.relicScreen.open(relics);
				NeowReward.isRelicScreenOpen = true;
                break;
            }
        }
        switch (this.drawback) {
            case CURSE: {
                this.cursed = true;
            }
            case NO_GOLD: {
                AbstractDungeon.player.loseGold(AbstractDungeon.player.gold);
                break;
            }
            case TEN_PERCENT_HP_LOSS: {
                AbstractDungeon.player.decreaseMaxHealth(this.hp_bonus);
                break;
            }
            case FIFTY_PERCENT_DAMAGE: {
                AbstractDungeon.player.damage(new DamageInfo(null, AbstractDungeon.player.currentHealth / 2, DamageInfo.DamageType.HP_LOSS));
                break;
            }
            case MORE_STRIKES_AND_DEFENDS: {
                AbstractCard strike = new Strike_Green();
				AbstractCard defend = new Defend_Red();
				switch(AbstractDungeon.player.chosenClass) {
				case IRONCLAD:
					strike = new Strike_Red();
					defend = new Defend_Red();
					break;
				case THE_SILENT:
					strike = new Strike_Green();
					defend = new Strike_Green();
					break;
				//case AbstractPlayer.PlayerClass.CROWBOT:
					// ...
					//break;
				default:
					for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
					  //(c.cardID == "Strike_R" || c.cardID == "Strike_G" || c.cardID == "Strike_B" || c.cardID == "Defend_R" || c.cardID == "Defend_G" || c.cardID == "Defend_B" || 
					  if (c.rarity == AbstractCard.CardRarity.BASIC){
						if (c.cardID.toLowerCase().contains("strike")) {
							strike = c;
						} else if (c.cardID.toLowerCase().contains("defend")) {
							defend = c;
						}
					  }
					}
				}
				for (int i = 0; i < NeowReward.STRIKE_COUNT; i++) {
					AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(strike.makeCopy(), (Settings.WIDTH / (NeowReward.STRIKE_COUNT + 2)) * (i+2), Settings.HEIGHT / 3.0F));
				}
				for (int i = 0; i < NeowReward.DEFEND_COUNT; i++) {
					AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(defend.makeCopy(), (Settings.WIDTH / (NeowReward.DEFEND_COUNT + 2)) * (i+2), (Settings.HEIGHT * 2.0f) / 3.0F));
				}
                break;
            }
        }
    }
    
    private void getPotion(final AbstractPotion p) {
        for (int i = 0; i < 3; ++i) {
            if (AbstractDungeon.player.potions[i] instanceof PotionPlaceholder) {
                p.moveInstantly(AbstractDungeon.player.potions[i].currentX, AbstractDungeon.player.potions[i].currentY);
                p.isObtained = true;
                p.isDone = true;
                p.isAnimating = false;
                p.flash();
                AbstractDungeon.player.potions[i] = p;
                break;
            }
        }
    }
    
    public ArrayList<AbstractCard> getRewardCards(final boolean rareOnly) {
        final ArrayList<AbstractCard> retVal = new ArrayList<AbstractCard>();
        for (int numCards = 3, i = 0; i < numCards; ++i) {
            AbstractCard.CardRarity rarity = this.rollRarity();
            if (rareOnly) {
                rarity = AbstractCard.CardRarity.RARE;
            }
            AbstractCard card = null;
            switch (rarity) {
                case RARE: {
                    card = this.getCard(rarity);
                    break;
                }
                case UNCOMMON: {
                    card = this.getCard(rarity);
                    break;
                }
                case COMMON: {
                    card = this.getCard(rarity);
                    break;
                }
                default: {
                    NeowReward.logger.info("WTF?");
                    break;
                }
            }
            final int dupeCount = 0;
            while (retVal.contains(card)) {
                if (dupeCount < 4) {
                    card = this.getCard(rarity);
                }
                else {
                    switch (rarity) {
                        case RARE: {
                            card = this.getCard(AbstractCard.CardRarity.UNCOMMON);
                            continue;
                        }
                        case UNCOMMON: {
                            card = this.getCard(AbstractCard.CardRarity.COMMON);
                            continue;
                        }
                        case COMMON: {
                            card = this.getCard(AbstractCard.CardRarity.UNCOMMON);
                            continue;
                        }
                        default: {
                            card = this.getCard(AbstractCard.CardRarity.COMMON);
                            continue;
                        }
                    }
                }
            }
            retVal.add(card);
        }
        final ArrayList<AbstractCard> retVal2 = new ArrayList<AbstractCard>();
        for (final AbstractCard c : retVal) {
            retVal2.add(c.makeCopy());
        }
        return retVal2;
    }
    
    public AbstractCard.CardRarity rollRarity() {
        if (NeowEvent.rng.randomBoolean(0.33f)) {
            return AbstractCard.CardRarity.UNCOMMON;
        }
        return AbstractCard.CardRarity.COMMON;
    }
    
    public AbstractCard getCard(final AbstractCard.CardRarity rarity) {
        switch (rarity) {
            case RARE: {
                return AbstractDungeon.rareCardPool.getRandomCard(NeowEvent.rng);
            }
            case UNCOMMON: {
                return AbstractDungeon.uncommonCardPool.getRandomCard(NeowEvent.rng);
            }
            case COMMON: {
                return AbstractDungeon.commonCardPool.getRandomCard(NeowEvent.rng);
            }
            default: {
                NeowReward.logger.info("Error in getCard in Neow Reward");
                return null;
            }
        }
    }
    
    static {
        logger = LogManager.getLogger(NeowReward.class.getName());
        characterStrings = CardCrawlGame.languagePack.getCharacterString("Neow Reward");
        NAMES = NeowReward.characterStrings.NAMES;
        TEXT = NeowReward.characterStrings.TEXT;
        UNIQUE_REWARDS = NeowReward.characterStrings.UNIQUE_REWARDS;
    }
    
    public enum NeowRewardType
    {
        THREE_CARDS, 
        ONE_RANDOM_RARE_CARD, 
        REMOVE_CARD, 
        UPGRADE_CARD, 
        TRANSFORM_CARD, 
        THREE_SMALL_POTIONS, 
        RANDOM_COMMON_RELIC, 
        TEN_PERCENT_HP_BONUS, 
        HUNDRED_GOLD, 
        THREE_ENEMY_KILL, 
        REMOVE_TWO, 
        TRANSFORM_TWO_CARDS, 
        ONE_RARE_RELIC, 
        THREE_RARE_CARDS, 
        THREE_HUGE_POTIONS, 
        TWO_FIFTY_GOLD, 
        TWENTY_PERCENT_HP_BONUS,
        BOSS_RELIC, 
		COLORLESS_CARD,
		UNCOMMON_RELIC;
    }
    
    public enum NeowRewardDrawback
    {
        NONE, 
        TEN_PERCENT_HP_LOSS, 
        NO_GOLD, 
        CURSE, 
        LOSE_STARTER_RELIC, 
        FIFTY_PERCENT_DAMAGE,
		MORE_STRIKES_AND_DEFENDS;
    }
}
