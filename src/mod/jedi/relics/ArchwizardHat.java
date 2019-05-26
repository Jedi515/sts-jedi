package mod.jedi.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import mod.jedi.interfaces.ModifyDamageRelic;
import mod.jedi.util.TextureLoader;

public class ArchwizardHat
    extends CustomRelic
    implements ModifyDamageRelic
{
    public static final String ID = "jedi:archwizardhat";
    public static final String PATH = "resources/jedi/images/relics/";
    public static final String OUTLINE_PATH = PATH + "outline/" + ID.substring(5) + ".png";
    public static final String IMG_PATH = PATH + ID.substring(5) + ".png";
    private static final Texture IMG = TextureLoader.getTexture(IMG_PATH);
    private static final Texture OUTLINE = TextureLoader.getTexture(OUTLINE_PATH);
    private static boolean triggered;
    public static final float efficiency = 1.25F;

    public ArchwizardHat()
    {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
        this.description = DESCRIPTIONS[0];
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        triggered = card.type == AbstractCard.CardType.ATTACK && (card.costForTurn == EnergyPanel.totalCount || card.cost == -1) && EnergyPanel.totalCount != 0;
        if (triggered)
        {
            this.flash();
        }
    }

    public float calculateCardDamageFinalRelic(AbstractCard card, AbstractMonster target, float damage)
    {
        if (((card.costForTurn == EnergyPanel.totalCount || card.cost == -1) && EnergyPanel.totalCount != 0) || triggered)
        {
            return (int)Math.ceil(damage * efficiency);
        }
        return damage;
    }

    public float applyPowersFinalRelic(AbstractCard card, float damage)
    {
        if (((card.costForTurn == EnergyPanel.totalCount || card.cost == -1) && EnergyPanel.totalCount != 0) || triggered)
        {
            return (float)Math.ceil(damage * efficiency);
        }
        return damage;
    }
}
