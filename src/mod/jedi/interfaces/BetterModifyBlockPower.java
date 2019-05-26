package mod.jedi.interfaces;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

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
