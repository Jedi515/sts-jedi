package jedi.cards.curses;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import jedi.cardMods.ModifyDamageOtherCardmod;
import jedi.cards.CustomJediCard;

public class Frostbite
    extends CustomJediCard
{

    public static final String ID = "jedi:frostbite";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = 1;

    public Frostbite()
    {
        super(ID, NAME, COST, DESCRIPTION, CardType.CURSE, CardColor.CURSE, CardRarity.CURSE, CardTarget.NONE);
        CardModifierManager.addModifier(this, new ModifyDamageOtherCardmod(-3));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    public void upgrade() {
    }
}
