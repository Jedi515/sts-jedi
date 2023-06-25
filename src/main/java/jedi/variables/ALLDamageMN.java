package jedi.variables;

import basemod.abstracts.AbstractCardModifier;
import basemod.abstracts.DynamicVariable;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.CardModifierPatches;
import com.megacrit.cardcrawl.cards.AbstractCard;
import jedi.cardMods.gathererEssenceMods.ALLDamageMod;

public class ALLDamageMN
    extends DynamicVariable
{
    @Override
    public String key()
    {
        return "jedi:ALLDMN";
    }

    @Override
    public boolean isModified(AbstractCard abstractCard)
    {
        for (AbstractCardModifier mod : CardModifierPatches.CardModifierFields.cardModifiers.get(abstractCard))
        {
            if (mod instanceof ALLDamageMod)
            {
                return ((ALLDamageMod)mod).modified;
            }
        }
        return false;
    }

    @Override
    public int value(AbstractCard abstractCard)
    {
        for (AbstractCardModifier mod : CardModifierPatches.CardModifierFields.cardModifiers.get(abstractCard))
        {
            if (mod instanceof ALLDamageMod)
            {
                return ((ALLDamageMod)mod).damage[0];
            }
        }
        return 0;
    }

    @Override
    public int baseValue(AbstractCard abstractCard)
    {
        for (AbstractCardModifier mod : CardModifierPatches.CardModifierFields.cardModifiers.get(abstractCard))
        {
            if (mod instanceof ALLDamageMod)
            {
                return ((ALLDamageMod)mod).baseValue;
            }
        }
        return 0;
    }

    @Override
    public boolean upgraded(AbstractCard abstractCard)
    {
        for (AbstractCardModifier mod : CardModifierPatches.CardModifierFields.cardModifiers.get(abstractCard))
        {
            if (mod instanceof ALLDamageMod)
            {
                return ((ALLDamageMod)mod).upgraded;
            }
        }
        return false;
    }
}
