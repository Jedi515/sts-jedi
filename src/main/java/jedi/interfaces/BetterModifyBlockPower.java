package jedi.interfaces;

import com.megacrit.cardcrawl.cards.AbstractCard;

public interface BetterModifyBlockPower
{
    default float applyPowersBlockRelic(AbstractCard card, float block)
    {
        return block;
    }

    default float applyPowersBlockFinalRelic(AbstractCard card, float block)
    {
        return block;
    }
}
