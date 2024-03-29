package jedi.cardMods;

import basemod.cardmods.ExhaustMod;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import org.apache.commons.lang3.StringUtils;

public class ExhaustJediMod
    extends ExhaustMod
{
    public String modifyDescription(String rawDescription, AbstractCard card) {
        String exhaust = GameDictionary.EXHAUST.NAMES[Settings.language == Settings.GameLanguage.RUS ? 3 : 0];
        return  rawDescription + " NL " + StringUtils.capitalize(exhaust) + LocalizedStrings.PERIOD;
    }
}
