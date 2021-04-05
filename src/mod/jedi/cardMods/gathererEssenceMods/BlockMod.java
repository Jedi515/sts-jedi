package mod.jedi.cardMods.gathererEssenceMods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BlockMod
    extends AbstractEssenceMod
{
    public int block;
    public BlockMod(int block)
    {
        baseValue = block;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card)
    {
        return rawDescription + TEXT[1];
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action)
    {
        addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, block));
    }

    @Override
    public void onApplyPowers(AbstractCard card)
    {
        block = card.block;
    }

    @Override
    public AbstractCardModifier makeCopy()
    {
        return new BlockMod(block);
    }
}
