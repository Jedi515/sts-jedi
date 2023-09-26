package jedi.cards.red;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.InstantKillAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;
import jedi.cards.CustomJediCard;

public class OneStrike
    extends CustomJediCard
{

    public static final String ID = "jedi:onestrike";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    public static final int COST = 3;


    public OneStrike()
    {
        super(ID, NAME, null, COST, DESCRIPTION, CardType.ATTACK, CardColor.RED, CardRarity.SPECIAL, CardTarget.ENEMY);
        this.tags.add(CardTags.STRIKE);
        this.exhaust = true;
        this.cantUseMessage = EXTENDED_DESCRIPTION[1];
        FleetingField.fleeting.set(this, true);
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new VFXAction(new WeightyImpactEffect(m.hb.cX, m.hb.cY)));
        addToBot(new InstantKillAction(m));
    }

    @Override
    public void upgrade()
    {
        if (!this.upgraded) {
            this.name += EXTENDED_DESCRIPTION[0];
            this.upgraded = true;
            initializeTitle();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }


    public boolean canUse(AbstractPlayer p, AbstractMonster m)
    {
        boolean canUse = super.canUse(p, m);
        if (m != null) {
            canUse = canUse && m.type != AbstractMonster.EnemyType.BOSS;
            cantUseMessage = EXTENDED_DESCRIPTION[1];
        }

        return canUse;
    }

    public AbstractCard makeCopy()
    {
        return new OneStrike();
    }

}
