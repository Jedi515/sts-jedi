package jedi.cardMods;

import basemod.cardmods.EtherealMod;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import org.apache.commons.lang3.StringUtils;

public class EtherealJediMod extends EtherealMod {
    public String modifyDescription(String rawDescription, AbstractCard card) {
        String exhaust = GameDictionary.ETHEREAL.NAMES[Settings.language == Settings.GameLanguage.RUS ? 1 : 0];
        return  StringUtils.capitalize(exhaust) + LocalizedStrings.PERIOD + " NL " + rawDescription;
    }
}
