package replayTheSpire.variables;

import com.megacrit.cardcrawl.cards.AbstractCard;

import basemod.abstracts.DynamicVariable;

public class MagicArithmatic {
	
	public static class MagicMinusOne extends DynamicVariable
	{
	    public String key() {
	        return "replay:M-1";
	    }
	    
	    public boolean isModified(final AbstractCard card) {
	        return card.isMagicNumberModified;
	    }
	    
	    public int value(final AbstractCard card) {
	        return card.magicNumber - 1;
	    }
	    
	    public int baseValue(final AbstractCard card) {
	        return card.baseMagicNumber - 1;
	    }
	    
	    public boolean upgraded(final AbstractCard card) {
	        return card.upgradedMagicNumber;
	    }
	}

	public static class MagicPlusOne extends DynamicVariable
	{
	    public String key() {
	        return "replay:M+1";
	    }
	    
	    public boolean isModified(final AbstractCard card) {
	        return card.isMagicNumberModified;
	    }
	    
	    public int value(final AbstractCard card) {
	        return card.magicNumber + 1;
	    }
	    
	    public int baseValue(final AbstractCard card) {
	        return card.baseMagicNumber + 1;
	    }
	    
	    public boolean upgraded(final AbstractCard card) {
	        return card.upgradedMagicNumber;
	    }
	}
	public static class MagicPlusTwo extends DynamicVariable
	{
	    public String key() {
	        return "replay:M+2";
	    }
	    
	    public boolean isModified(final AbstractCard card) {
	        return card.isMagicNumberModified;
	    }
	    
	    public int value(final AbstractCard card) {
	        return card.magicNumber + 2;
	    }
	    
	    public int baseValue(final AbstractCard card) {
	        return card.baseMagicNumber + 2;
	    }
	    
	    public boolean upgraded(final AbstractCard card) {
	        return card.upgradedMagicNumber;
	    }
	}
	public static class MagicMinusTwo extends DynamicVariable
	{
	    public String key() {
	        return "replay:M-2";
	    }
	    
	    public boolean isModified(final AbstractCard card) {
	        return card.isMagicNumberModified;
	    }
	    
	    public int value(final AbstractCard card) {
	        return card.magicNumber - 2;
	    }
	    
	    public int baseValue(final AbstractCard card) {
	        return card.baseMagicNumber - 2;
	    }
	    
	    public boolean upgraded(final AbstractCard card) {
	        return card.upgradedMagicNumber;
	    }
	}
	public static class MagicTimesTwo extends DynamicVariable
	{
	    public String key() {
	        return "replay:M*2";
	    }
	    
	    public boolean isModified(final AbstractCard card) {
	        return card.isMagicNumberModified;
	    }
	    
	    public int value(final AbstractCard card) {
	        return card.magicNumber * 2;
	    }
	    
	    public int baseValue(final AbstractCard card) {
	        return card.baseMagicNumber * 2;
	    }
	    
	    public boolean upgraded(final AbstractCard card) {
	        return card.upgradedMagicNumber;
	    }
	}
}
