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
import com.megacrit.cardcrawl.mod.replay.rooms.*;

import replayTheSpire.*;
import replayTheSpire.patches.*;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import basemod.*;
import java.util.*;

@SpirePatch(cls = "com.megacrit.cardcrawl.shop.ShopScreen", method = "renderCardsAndPrices")
public class ReplayShopRenderCardsPatch {
	
	public static void Postfix(ShopScreen __instance, final SpriteBatch sb) {
		if (ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic("Blue Doll")) {
			ArrayList<AbstractCard> coloredCards = (ArrayList<AbstractCard>)ReflectionHacks.getPrivate((Object)__instance, (Class)ShopScreen.class, "coloredCards");
			ArrayList<AbstractCard> colorlessCards = (ArrayList<AbstractCard>)ReflectionHacks.getPrivate((Object)__instance, (Class)ShopScreen.class, "colorlessCards");
			for (int i=0; i < ReplayShopInitCardsPatch.dollTags.size(); i++) {
				OnSaleTag t = ReplayShopInitCardsPatch.dollTags.get(i);
				Texture tmp = OnSaleTag.img;
				OnSaleTag.img = ReplayShopInitCardsPatch.dollTagImgs.get(i);
				if (coloredCards.contains(t.card)) {
					t.render(sb);
				}
				if (colorlessCards.contains(t.card)) {
					t.render(sb);
				}
				OnSaleTag.img = tmp;
			}
		}
	}
	
}