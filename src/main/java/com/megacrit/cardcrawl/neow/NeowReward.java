package com.megacrit.cardcrawl.neow;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.vfx.cardManip.*;
import com.megacrit.cardcrawl.saveAndContinue.*;
import com.megacrit.cardcrawl.rewards.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;
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
    private static final int GOLD_BONUS = 100;
    private static final int LARGE_GOLD_BONUS = 250;
    private NeowRewardDrawbackDef drawbackDef;

    //this class just holds the type and text for a reward
    public static class NeowRewardDef {
    	public NeowRewardType type;
    	public String desc;
    	public NeowRewardDef(NeowRewardType type, String desc) {
    		this.type = type;
    		this.desc = desc;
    	}
    }
    //same as above, but for drawbacks
    public static class NeowRewardDrawbackDef {
    	public NeowRewardDrawback type;
    	public String desc;
    	public NeowRewardDrawbackDef(NeowRewardDrawback type, String desc) {
    		this.type = type;
    		this.desc = desc;
    	}
    }
    
    public NeowReward(final int category) {
        this.optionLabel = "";
        this.drawback = NeowRewardDrawback.NONE;
        this.activated = false;
        this.hp_bonus = 0;
        this.cursed = false;
        this.hp_bonus = (int)(AbstractDungeon.player.maxHealth * 0.1f);
        
        ArrayList<NeowRewardDef> possibleRewards = getRewardOptions(category);//get list of possible reward options (also sets the drawback for chaos category)
        final NeowRewardDef reward = possibleRewards.get(NeowEvent.rng.random(0, possibleRewards.size() - 1));//select one of the rewards for the list
        if (this.drawback != NeowRewardDrawback.NONE && this.drawbackDef != null) {
        	this.optionLabel = this.optionLabel + this.drawbackDef.desc;//add text for drawback
        }
        this.optionLabel = this.optionLabel + reward.desc;//add text for reward
        this.type = reward.type;
    }

    //This function makes a list of drawbacks the NeowReward could have. Because it's a list in a separate function, mods can add their own easily.
    private ArrayList<NeowRewardDrawbackDef> getRewardDrawbackOptions() {
    	ArrayList<NeowRewardDrawbackDef> drawbackOptions = new ArrayList<NeowRewardDrawbackDef>();
    	drawbackOptions.add(new NeowRewardDrawbackDef(NeowRewardDrawback.TEN_PERCENT_HP_LOSS, NeowReward.TEXT[17] + this.hp_bonus + NeowReward.TEXT[18]));
    	drawbackOptions.add(new NeowRewardDrawbackDef(NeowRewardDrawback.NO_GOLD, NeowReward.TEXT[19]));
    	drawbackOptions.add(new NeowRewardDrawbackDef(NeowRewardDrawback.CURSE, NeowReward.TEXT[20]));
    	drawbackOptions.add(new NeowRewardDrawbackDef(NeowRewardDrawback.PERCENT_DAMAGE, NeowReward.TEXT[21] + (AbstractDungeon.player.currentHealth / 10 * 3) + NeowReward.TEXT[29] + " "));
    	return drawbackOptions;
    }
    
   //This function makes a list of rewards the NeowReward could have, and sets the drawback if the category is chaos reward. Because it's a list in a separate function, mods can add their own easily.
	private ArrayList<NeowRewardDef> getRewardOptions(final int category) {
    	ArrayList<NeowRewardDef> rewardOptions = new ArrayList<NeowRewardDef>();
    	switch (category) {
	        case 0: {//cards
	            rewardOptions.add(new NeowRewardDef(NeowRewardType.THREE_CARDS, NeowReward.TEXT[0]));
	            rewardOptions.add(new NeowRewardDef(NeowRewardType.ONE_RANDOM_RARE_CARD, NeowReward.TEXT[1]));
	            rewardOptions.add(new NeowRewardDef(NeowRewardType.REMOVE_CARD, NeowReward.TEXT[2]));
	            rewardOptions.add(new NeowRewardDef(NeowRewardType.UPGRADE_CARD, NeowReward.TEXT[3]));
	            rewardOptions.add(new NeowRewardDef(NeowRewardType.TRANSFORM_CARD, NeowReward.TEXT[4]));
	            break;
	        }
	        case 1: {//non-card
	            rewardOptions.add(new NeowRewardDef(NeowRewardType.THREE_SMALL_POTIONS, NeowReward.TEXT[5]));
	            rewardOptions.add(new NeowRewardDef(NeowRewardType.RANDOM_COMMON_RELIC, NeowReward.TEXT[6]));
	            rewardOptions.add(new NeowRewardDef(NeowRewardType.TEN_PERCENT_HP_BONUS, NeowReward.TEXT[7] + this.hp_bonus + " ]"));
	            rewardOptions.add(new NeowRewardDef(NeowRewardType.THREE_ENEMY_KILL, NeowReward.TEXT[28]));
	            rewardOptions.add(new NeowRewardDef(NeowRewardType.HUNDRED_GOLD, NeowReward.TEXT[8] + GOLD_BONUS + NeowReward.TEXT[9]));
	            break;
	        }
	        case 2: {//chaos
	        	//get drawback
	        	ArrayList<NeowRewardDrawbackDef> drawbackOptions = getRewardDrawbackOptions();
	        	this.drawbackDef = drawbackOptions.get(NeowEvent.rng.random(0, drawbackOptions.size() - 1));
	        	this.drawback = this.drawbackDef.type;
	        	//make reward options
	        	if (this.drawback != NeowRewardDrawback.CURSE) rewardOptions.add(new NeowRewardDef(NeowRewardType.REMOVE_TWO, NeowReward.TEXT[10]));
	        	rewardOptions.add(new NeowRewardDef(NeowRewardType.ONE_RARE_RELIC, NeowReward.TEXT[11]));
	        	rewardOptions.add(new NeowRewardDef(NeowRewardType.THREE_RARE_CARDS, NeowReward.TEXT[12]));
	        	if (this.drawback != NeowRewardDrawback.NO_GOLD) rewardOptions.add(new NeowRewardDef(NeowRewardType.TWO_FIFTY_GOLD, NeowReward.TEXT[13] + LARGE_GOLD_BONUS + NeowReward.TEXT[14]));
	        	rewardOptions.add(new NeowRewardDef(NeowRewardType.TRANSFORM_TWO_CARDS, NeowReward.TEXT[15]));
	        	if (this.drawback != NeowRewardDrawback.TEN_PERCENT_HP_LOSS) rewardOptions.add(new NeowRewardDef(NeowRewardType.TWENTY_PERCENT_HP_BONUS, NeowReward.TEXT[16] + this.hp_bonus * 2 + " ]"));
	            break;
	        }
	        case 3: {//boss relic
	        	rewardOptions.add(new NeowRewardDef(NeowRewardType.BOSS_RELIC, NeowReward.UNIQUE_REWARDS[0]));
	            break;
	        }
	    }
    	return rewardOptions;
    }
    
	/////////////Everything below this point I didn't touch    -Tobias
	
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
                SaveHelper.saveIfAppropriate(SaveFile.SaveType.POST_NEOW);
                this.activated = false;
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
                AbstractDungeon.player.gainGold(GOLD_BONUS);
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
            case THREE_SMALL_POTIONS: {
                CardCrawlGame.sound.play("POTION_1");
                for (int i = 0; i < 3; ++i) {
                    AbstractDungeon.getCurrRoom().addPotionToRewards(PotionHelper.getRandomPotion());
                }
                AbstractDungeon.combatRewardScreen.open();
                AbstractDungeon.getCurrRoom().rewardPopOutTimer = 0.0f;
                int remove = -1;
                for (int j = 0; j < AbstractDungeon.combatRewardScreen.rewards.size(); ++j) {
                    if (AbstractDungeon.combatRewardScreen.rewards.get(j).type == RewardItem.RewardType.CARD) {
                        remove = j;
                        break;
                    }
                }
                if (remove != -1) {
                    AbstractDungeon.combatRewardScreen.rewards.remove(remove);
                    break;
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
                AbstractDungeon.player.gainGold(LARGE_GOLD_BONUS);
                break;
            }
            case UPGRADE_CARD: {
                AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getUpgradableCards(), 1, NeowReward.TEXT[27], true, false, false, false);
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
            case PERCENT_DAMAGE: {
                AbstractDungeon.player.damage(new DamageInfo(null, AbstractDungeon.player.currentHealth / 10 * 3, DamageInfo.DamageType.HP_LOSS));
                break;
            }
        }
        CardCrawlGame.metricData.addNeowData(this.type.name(), this.drawback.name());
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
        TWO_FIFTY_GOLD, 
        TWENTY_PERCENT_HP_BONUS, 
        BOSS_RELIC;
    }
    
    public enum NeowRewardDrawback
    {
        NONE, 
        TEN_PERCENT_HP_LOSS, 
        NO_GOLD, 
        CURSE, 
        LOSE_STARTER_RELIC, 
        PERCENT_DAMAGE;
    }
}
