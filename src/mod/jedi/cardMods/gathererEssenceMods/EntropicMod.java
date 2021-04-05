package mod.jedi.cardMods.gathererEssenceMods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class EntropicMod
    extends AbstractEssenceMod
{
    public EntropicMod(int potion)
    {
        baseValue = potion;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card)
    {
        return rawDescription + String.format(TEXT[3], baseValue);
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action)
    {
        for (int i = 0; i < baseValue; i++)
        {
            addToBot(new ObtainPotionAction(AbstractDungeon.returnRandomPotion(true)));
        }
    }

    @Override
    public AbstractCardModifier makeCopy()
    {
        return new EntropicMod(baseValue);
    }
}
