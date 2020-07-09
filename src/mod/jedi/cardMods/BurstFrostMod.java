package mod.jedi.cardMods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.FocusPower;

public class BurstFrostMod extends AbstractCardModifier
{
    @Override
    public float modifyBlock(float block, AbstractCard card)
    {
        if (AbstractDungeon.player.hasPower(FocusPower.POWER_ID)) return block + AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount * card.magicNumber;
        return block;
    }

    @Override
    public boolean isInherent(AbstractCard card)
    {
        return true;
    }

    @Override
    public AbstractCardModifier makeCopy()
    {
        return new BurstFrostMod();
    }
}
