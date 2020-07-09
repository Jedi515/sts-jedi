package mod.jedi.cards.purple;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMiscAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.MarkPower;
import mod.jedi.cards.CustomJediCard;

public class NirvanicRebirth extends CustomJediCard {
    public static final String ID = makeCardId("nirvanicrebirth");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = 1;
    public static final String IMG_PATH = "resources/jedi/images/cards/jedi_beta.png";

    public NirvanicRebirth()
    {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, CardColor.PURPLE, CardRarity.RARE, CardTarget.SELF);
        this.exhaust = true;
        this.secondMN = this.baseSecondMN = 1;
        this.magicNumber = this.baseMagicNumber = this.misc = 2;
        this.tags.add(CardTags.HEALING);
        FleetingField.fleeting.set(this, true);
        selfRetain = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new HealAction(p, p, this.magicNumber));
    }

    @Override
    public void onRetained()
    {
        this.addToBot(new IncreaseMiscAction(this.uuid, this.misc, this.secondMN));
    }

    public void applyPowers()
    {
        this.baseMagicNumber = this.magicNumber = this.misc;
        super.applyPowers();
        this.initializeDescription();
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.upgradeSecondMN(1);
    }

}
