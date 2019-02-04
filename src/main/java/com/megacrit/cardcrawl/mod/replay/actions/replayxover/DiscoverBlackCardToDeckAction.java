package com.megacrit.cardcrawl.mod.replay.actions.replayxover;

import basemod.BaseMod;
import basemod.ReflectionHacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.ui.buttons.SkipCardButton;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;
import infinitespire.patches.CardColorEnumPatch;
import infinitespire.patches.DiscoverPatch;

public class DiscoverBlackCardToDeckAction extends AbstractGameAction {

	private boolean retrieveCard = false;
	private boolean toDraw;
	private boolean cursed;
	private AbstractCard prohibited;
	private static final AbstractCard.CardColor cardColor = CardColorEnumPatch.CardColorPatch.INFINITE_BLACK;
	

	public DiscoverBlackCardToDeckAction(AbstractCard prohibited, int amount){
		this(prohibited, amount, true);
	}
	public DiscoverBlackCardToDeckAction(AbstractCard prohibited, int amount, boolean toDraw){
		this(prohibited, amount, toDraw, false);
	}
	public DiscoverBlackCardToDeckAction(AbstractCard prohibited, int amount, boolean toDraw, boolean cursed){
		actionType = ActionType.CARD_MANIPULATION;
		duration = Settings.ACTION_DUR_FAST;
		this.amount = amount;
		this.prohibited = prohibited;
		this.toDraw = toDraw;
		this.cursed = cursed;
	}

	public DiscoverBlackCardToDeckAction(int amount){
		this(null, amount);
	}

	public DiscoverBlackCardToDeckAction(){
		this(null, 3);
	}

	@Override
	public void update() {
		if(duration == Settings.ACTION_DUR_FAST) {
			DiscoverPatch.lookingForColor = cardColor;
			DiscoverPatch.lookingForCount = amount;
			DiscoverPatch.lookingForProhibit = prohibited;

			AbstractDungeon.cardRewardScreen.discoveryOpen();
			if (this.cursed) {
				((SkipCardButton)ReflectionHacks.getPrivate(AbstractDungeon.cardRewardScreen, CardRewardScreen.class, "skipButton")).show();
			}
			tickDuration();
			return;
		}
		if(!retrieveCard) {
			if(AbstractDungeon.cardRewardScreen.discoveryCard != null) {
				AbstractCard card = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
				card.current_x = -1000f * Settings.scale;
				if(this.toDraw) {
					AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(card, Settings.WIDTH / 2f, Settings.HEIGHT / 2f, true, false));
					if (this.cursed) {
						AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(AbstractDungeon.returnRandomCurse().makeCopy(), Settings.WIDTH / 2f, Settings.HEIGHT / 2f, true, false));
					}
				}else{
					AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(card, Settings.WIDTH / 2f, Settings.HEIGHT / 2f));
					if (this.cursed) {
						AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(AbstractDungeon.returnRandomCurse().makeCopy(), Settings.WIDTH / 2f, Settings.HEIGHT / 2f));
					}
				}
				card.setCostForTurn(0);
				AbstractDungeon.cardRewardScreen.discoveryCard = null;
			}
			retrieveCard = true;
		}
		tickDuration();
	}
}