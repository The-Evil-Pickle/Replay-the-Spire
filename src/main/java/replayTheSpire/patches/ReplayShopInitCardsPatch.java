package replayTheSpire.patches;
import com.megacrit.cardcrawl.shop.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.relics.M_MistRelic;
import com.megacrit.cardcrawl.mod.replay.relics.RingOfChaos;
import com.megacrit.cardcrawl.mod.replay.rooms.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import replayTheSpire.*;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.badlogic.gdx.graphics.*;
import basemod.*;
import java.util.*;

@SpirePatch(cls = "com.megacrit.cardcrawl.shop.ShopScreen", method = "initCards")
public class ReplayShopInitCardsPatch {
	
	public static String NORMAL_TAG = "images/npcs/sale_tag/eng.png";
	public static String DOUBLE_TAG = "images/npcs/sale_tag/2for1Tag.png";
	public static String SPECIAL_TAG = "images/npcs/sale_tag/specialEditionTag.png";
	
	public static AbstractCard doubleCard;
	public static ArrayList<OnSaleTag> dollTags;
	public static ArrayList<Texture> dollTagImgs;
	
	public static void Postfix(ShopScreen __instance) {
		
		OnSaleTag saleTag = (OnSaleTag)ReflectionHacks.getPrivate((Object)__instance, (Class)ShopScreen.class, "saleTag");
		AbstractCard saleCard = saleTag.card;
		saleCard.price *= 2;
		ReplayShopInitCardsPatch.dollTags = new ArrayList<OnSaleTag>();
		ReplayShopInitCardsPatch.dollTagImgs = new ArrayList<Texture>();
		ArrayList<String> tagList = new ArrayList<String>();
		for (int i=0; i < ReplayTheSpireMod.SETTING_TAG_NORMAL_CHANCE.value; i++) {
			tagList.add(ReplayShopInitCardsPatch.NORMAL_TAG);
		}
		for (int i=0; i < ReplayTheSpireMod.SETTING_TAG_DOUBLE_CHANCE.value; i++) {
			tagList.add(ReplayShopInitCardsPatch.DOUBLE_TAG);
		}
		if (!ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic("GoldenEgg") || ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic("Blue Doll")) {
			for (int i=0; i < ReplayTheSpireMod.SETTING_TAG_SPECIAL_CHANCE.value; i++) {
				tagList.add(ReplayShopInitCardsPatch.SPECIAL_TAG);
			}
		}
		String tagtype = tagList.get(AbstractDungeon.merchantRng.random(0, tagList.size() - 1));
		OnSaleTag.img = ImageMaster.loadImage(tagtype);
		if (tagtype == ReplayShopInitCardsPatch.DOUBLE_TAG) {
				ReplayShopInitCardsPatch.doubleCard = saleCard;
				saleCard.price *= 3;
				saleCard.price /= 4;
		} else if (tagtype == ReplayShopInitCardsPatch.SPECIAL_TAG) {
				saleCard.upgrade();
				saleCard.price *= 3;
				saleCard.price /= 4;
		} else {
				saleCard.price /= 2;
		}
		
		if (ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic("Blue Doll")) {
			saleCard.price -= 10;
			ArrayList<AbstractCard> coloredCards = (ArrayList<AbstractCard>)ReflectionHacks.getPrivate((Object)__instance, (Class)ShopScreen.class, "coloredCards");
			ArrayList<AbstractCard> colorlessCards = (ArrayList<AbstractCard>)ReflectionHacks.getPrivate((Object)__instance, (Class)ShopScreen.class, "colorlessCards");
			AbstractCard newTagCard = null;
			if (tagtype != ReplayShopInitCardsPatch.NORMAL_TAG) {
				ArrayList<AbstractCard> validTargets = new ArrayList<AbstractCard>();
				for (AbstractCard c : coloredCards) {
					if (c != newTagCard && c != saleCard) {
						validTargets.add(c);
					}
				}
				for (AbstractCard c : colorlessCards) {
					if (c != newTagCard && c != saleCard) {
						validTargets.add(c);
					}
				}
				if (validTargets.size() > 0) {
					newTagCard = validTargets.get(AbstractDungeon.merchantRng.random(0, validTargets.size() - 1));
					newTagCard.price /= 2;
					newTagCard.price -= 10;
					OnSaleTag newtag = new OnSaleTag(newTagCard);
					dollTags.add(newtag);
					ReplayShopInitCardsPatch.dollTagImgs.add(ImageMaster.loadImage(ReplayShopInitCardsPatch.NORMAL_TAG));
				}
			}
			if (tagtype != ReplayShopInitCardsPatch.DOUBLE_TAG) {
				ArrayList<AbstractCard> validTargets = new ArrayList<AbstractCard>();
				for (AbstractCard c : coloredCards) {
					if (c != newTagCard && c != saleCard) {
						validTargets.add(c);
					}
				}
				for (AbstractCard c : colorlessCards) {
					if (c != newTagCard && c != saleCard) {
						validTargets.add(c);
					}
				}
				if (validTargets.size() > 0) {
					newTagCard = validTargets.get(AbstractDungeon.merchantRng.random(0, validTargets.size() - 1));
					newTagCard.price *= 3;
					newTagCard.price /= 4;
					newTagCard.price -= 10;
					ReplayShopInitCardsPatch.doubleCard = newTagCard;
					OnSaleTag newtag = new OnSaleTag(newTagCard);
					dollTags.add(newtag);
					ReplayShopInitCardsPatch.dollTagImgs.add(ImageMaster.loadImage(ReplayShopInitCardsPatch.DOUBLE_TAG));
				}
			}
			if (tagtype != ReplayShopInitCardsPatch.SPECIAL_TAG) {
				ArrayList<AbstractCard> validTargets = new ArrayList<AbstractCard>();
				for (AbstractCard c : coloredCards) {
					if (c != newTagCard && c != saleCard) {
						validTargets.add(c);
					}
				}
				for (AbstractCard c : colorlessCards) {
					if (c != newTagCard && c != saleCard) {
						validTargets.add(c);
					}
				}
				if (validTargets.size() > 0) {
					newTagCard = validTargets.get(AbstractDungeon.merchantRng.random(0, validTargets.size() - 1));
					newTagCard.price *= 3;
					newTagCard.price /= 4;
					newTagCard.price -= 10;
					newTagCard.upgrade();
					if (ReplayTheSpireMod.SETTING_TAG_SCRAMBLE_CHANCE.testChance(AbstractDungeon.merchantRng)) {
						RingOfChaos.ChaosScrambleCard(newTagCard);
					}
					OnSaleTag newtag = new OnSaleTag(newTagCard);
					dollTags.add(newtag);
					ReplayShopInitCardsPatch.dollTagImgs.add(ImageMaster.loadImage(ReplayShopInitCardsPatch.SPECIAL_TAG));
				}
			}
		}
		
	}
	
}