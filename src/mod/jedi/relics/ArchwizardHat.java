package mod.jedi.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import mod.jedi.util.TextureLoader;

public class ArchwizardHat
    extends CustomRelic
{
    public static final String ID = "jedi:archwizardhat";
    public static final String PATH = "resources/jedi/images/relics/";
    public static final String OUTLINE_PATH = PATH + "outline/" + ID.substring(5) + ".png";
    public static final String IMG_PATH = PATH + ID.substring(5) + ".png";
    private static final Texture IMG = TextureLoader.getTexture(IMG_PATH);
    private static final Texture OUTLINE = TextureLoader.getTexture(OUTLINE_PATH);
    private static boolean triggered;

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

    public int betterOnAttackedMonster(DamageInfo info, int damageAmount)
    {
        if (triggered && info.owner != null && info.type != DamageInfo.DamageType.HP_LOSS && info.type != DamageInfo.DamageType.THORNS && damageAmount > 0)
        {
            return (int)(damageAmount * 1.25F);
        }
        return damageAmount;
    }
}
