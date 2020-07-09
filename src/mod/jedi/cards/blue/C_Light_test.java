/*Ok so here's the thing, this card has been BASICALLY created by JohnnyDevo (MysticMod creator) by one way or another.
* I kinda got his help on the majority of this code or took parts from the way he did it in his cards (Fireball) so
* There's that. https://github.com/JohnnyDevo/The-Mystic-Project/releases for the Mystic mod.
*
*
* Ok so here's another thing. if you're reading this - something went horribly wrong. This card should have never made to actual release.
* */

package mod.jedi.cards.blue;

import basemod.AutoAdd;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.powers.ElectroPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.LockOnPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import mod.jedi.actions.EvokeWithoutRemovingSpecificOrbAction;

@AutoAdd.Ignore
public class C_Light_test
        extends CustomCard
{
    public static final String ID = "jedi:test";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final int COST = 1;
    public static final String IMG_PATH = "resources/jedi/images/cards/jedi_beta_attack.png";

    public C_Light_test()
    {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.SPECIAL, CardTarget.ENEMY);
        this.magicNumber = this.baseMagicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {

    }

    public void upgrade()
    {
        if(!this.upgraded)
        {
            upgradeName();
            this.upgradeMagicNumber(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    public CustomCard makeCopy()
    {
        return new C_Light_test();
    }
}
