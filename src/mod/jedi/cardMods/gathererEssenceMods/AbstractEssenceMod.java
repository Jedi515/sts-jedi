package mod.jedi.cardMods.gathererEssenceMods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.CardModifierPatches;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public abstract class AbstractEssenceMod
    extends AbstractCardModifier
{
    public static String ID = "jedi:EssenceMods";
    public static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    public static final String[] TEXT = uiStrings.TEXT;
    public int baseValue;

    public AbstractEssenceMod()
    {
        this(0);
    }

    public AbstractEssenceMod(int value)
    {
        baseValue = value;
    }

    public static void applyEssenceMod(AbstractCard card, AbstractEssenceMod mod)
    {
        if (CardModifierPatches.CardModifierFields.cardModifiers.get(card).stream().noneMatch(cardMod -> cardMod.getClass() == mod.getClass()))
        {
            CardModifierManager.addModifier(card, mod);
        }
        else
        {
            CardModifierPatches.CardModifierFields.cardModifiers.get(card).stream().filter(cardMod -> cardMod.getClass() == mod.getClass()).forEach(cardMod -> ((AbstractEssenceMod)cardMod).baseValue += mod.baseValue);
        }
        card.applyPowers();
    }

    public static AbstractEssenceMod getMod(AbstractCard owner, String mod)
    {
        for (AbstractCardModifier m : CardModifierPatches.CardModifierFields.cardModifiers.get(owner))
        {
            if (m.getClass().getSimpleName().equals(mod)) return (AbstractEssenceMod) m;
        }
        return null;
    }

    

    protected static void addToBot(AbstractGameAction action)
    {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    protected static void addToTop(AbstractGameAction action)
    {
        AbstractDungeon.actionManager.addToTop(action);
    }
}
