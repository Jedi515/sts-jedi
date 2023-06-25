package jedi.cards.purple;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import jedi.cards.CustomJediCard;
import jedi.interfaces.CardSeenScriedInterface;
import jedi.jedi;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class Khahiri
    extends CustomJediCard
    implements CardSeenScriedInterface
{
    public static String ID = jedi.makeID(Khahiri.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = 1;

    public Khahiri()
    {
        super(ID, NAME, null, COST, DESCRIPTION, CardType.ATTACK, CardColor.PURPLE, CardRarity.UNCOMMON, CardTarget.ENEMY);
        setDmg(8);
        setMN(3);
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeDamage(2);
            upgradeMagicNumber(1);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    @Override
    public List<TooltipInfo> getCustomTooltips()
    {
        List<TooltipInfo> tips = new ArrayList<>();
        tips.add(new TooltipInfo(StringUtils.capitalize(GameDictionary.SCRY.NAMES[0]), GameDictionary.SCRY.DESCRIPTION));
        return tips;
    }

    @Override
    public void onSeenScried()
    {
        flash();
        baseDamage += magicNumber;
    }
}
