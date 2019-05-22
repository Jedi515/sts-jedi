package mod.jedi.interfaces;

import com.megacrit.cardcrawl.cards.AbstractCard;

public interface ModifyBlockRelicInterface
{
    default float modifyBlock(AbstractCard card, float block)
    {
        return block;
    }
}
