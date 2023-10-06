package jedi.cards.red;

import com.evacipated.cardcrawl.mod.stslib.StSLib;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import jedi.cards.CustomJediCard;

public class BloodyHammer
        extends CustomJediCard
{
    public static final String ID = "jedi:bloodyhammer";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    public static final int COST = 1;

    public BloodyHammer()
    {
        super(ID, NAME, null, COST, DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.RARE, CardTarget.NONE);
        this.magicNumber = this.baseMagicNumber = 6;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new SelectCardsInHandAction(upgraded ? 99 : 1, EXTENDED_DESCRIPTION[0], upgraded, false,
                c ->
                    {
                        AbstractCard masterCard = StSLib.getMasterDeckEquivalent(c);
                        return ((masterCard != null) && (masterCard.canUpgrade()));
                    },
                list ->
                    {
                        list.forEach(c ->
                        {
                            if (c.canUpgrade()) c.upgrade();
                            StSLib.getMasterDeckEquivalent(c).upgrade();

                        });
                        addToTop(new LoseHPAction(p, p, list.size() * magicNumber));
                    }));
        addToBot(new VFXAction(new UpgradeShineEffect(Settings.WIDTH/2F, Settings.HEIGHT/2F)));
    }

    public void upgrade()
    {
        if(!upgraded)
        {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
