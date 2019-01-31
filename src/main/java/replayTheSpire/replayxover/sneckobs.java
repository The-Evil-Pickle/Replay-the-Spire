package replayTheSpire.replayxover;

import com.megacrit.cardcrawl.cards.AbstractCard;

import replayTheSpire.ReplayTheSpireMod;
import sneckomod.SneckoMod;

public class sneckobs {
	public static void makeSneky(AbstractCard c) {
		if (!ReplayTheSpireMod.foundmod_snecko) {
			return;
		}
		c.tags.add(SneckoMod.SNEKPROOF);
		if (!c.rawDescription.contains("Snekproof")) {
			c.rawDescription = "Snekproof. NL " + c.rawDescription;
			c.initializeDescription();
		}
	}
}
