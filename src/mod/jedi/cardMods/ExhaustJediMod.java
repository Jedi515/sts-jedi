package mod.jedi.cardMods;

import basemod.cardmods.ExhaustMod;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import org.apache.commons.lang3.StringUtils;

public class ExhaustJediMod
    extends ExhaustMod
{
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return  rawDescription + " NL " + StringUtils.capitalize(GameDictionary.EXHAUST.NAMES[0]) + LocalizedStrings.PERIOD;
    }
}
