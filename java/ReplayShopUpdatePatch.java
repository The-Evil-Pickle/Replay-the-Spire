package ReplayTheSpireMod.patches;
import com.megacrit.cardcrawl.shop.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.rooms.*;
import ReplayTheSpireMod.patches.*;
import ReplayTheSpireMod.*;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.vfx.cardManip.*;
import basemod.*;
import java.util.*;

@SpirePatch(cls = "com.megacrit.cardcrawl.shop.ShopScreen", method = "update")
public class ReplayShopUpdatePatch {
	
	public static void Prefix(ShopScreen __instance) {
		if (ReplayShopInitCardsPatch.doubleCard != null) {
			ArrayList<AbstractCard> coloredCards = (ArrayList<AbstractCard>)ReflectionHacks.getPrivate((Object)__instance, (Class)ShopScreen.class, "coloredCards");
			ArrayList<AbstractCard> colorlessCards = (ArrayList<AbstractCard>)ReflectionHacks.getPrivate((Object)__instance, (Class)ShopScreen.class, "colorlessCards");
			boolean somethingHovered = false;
			AbstractCard hoveredCard = null;
			for (final AbstractCard c : coloredCards) {
				if (c.hb.hovered) {
					hoveredCard = c;
					somethingHovered = true;
					//this.moveHand(c.current_x - AbstractCard.IMG_WIDTH / 2.0f, c.current_y);
					break;
				}
			}
			if (!somethingHovered) {
				for (final AbstractCard c : colorlessCards) {
					if (c.hb.hovered) {
						hoveredCard = c;
						somethingHovered = true;
						//this.moveHand(c.current_x - AbstractCard.IMG_WIDTH / 2.0f, c.current_y);
						break;
					}
				}
			}
			/*
			if (hoveredCard != null && InputHelper.justClickedLeft) {
				hoveredCard.hb.clickStarted = true;
				ReplayTheSpireMod.logger.info("click started");
			}
			*/
			if (hoveredCard != null && (hoveredCard.hb.clicked || (hoveredCard.hb.clickStarted && InputHelper.justReleasedClickLeft))) {
				if (hoveredCard == ReplayShopInitCardsPatch.doubleCard) {
					if (AbstractDungeon.player.gold >= hoveredCard.price) {
						AbstractDungeon.topLevelEffects.add(new FastCardObtainEffect(hoveredCard.makeCopy(), hoveredCard.current_x + 40.0F, hoveredCard.current_y - 40.0F));
						ReplayShopInitCardsPatch.doubleCard = null;
					}
				}
			}
		}
	}
}