package jedi.cards.ignore;

import basemod.AutoAdd;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.Strike_Red;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import jedi.cardMods.ImperfectMod;
import jedi.cards.CustomJediCard;

@AutoAdd.Ignore
public class ImperfectModCard extends CustomJediCard
{
    private static final AbstractCard fakeStrike = new Strike_Red();
//    public static final String ID;
    private static final UIStrings cardStrings = CardCrawlGame.languagePack.getUIString("jedi:ImperfectMod");
    public static final String[] TEXT = cardStrings.TEXT;
    public static final String NAME = fakeStrike.name;
    public static final String DESCRIPTION = fakeStrike.rawDescription;
    public static final int COST = -2;
    public ImperfectModCard()
    {
        super("jedi:ImperfectMod", NAME, COST, DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.ENEMY);
        setDmg(6);
        CardModifierManager.addModifier(this, new ImperfectMod(1, true));
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

    }
}
