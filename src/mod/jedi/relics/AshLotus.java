package mod.jedi.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.hubris.relics.BlackHole;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.Ironclad;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gluttonmod.characters.GluttonCharacter;
import mod.jedi.util.TextureLoader;
import sts_jedi.jedi;

public class AshLotus
    extends CustomRelic
{
    public static final String ID = "jedi:ashlotus";
    public static final String PATH = "resources/jedi/images/relics/";
    public static final String OUTLINE_PATH = PATH + "outline/" + ID.substring(5) + ".png";
    public static final String IMG_PATH = PATH + ID.substring(5) + ".png";
    private static final Texture IMG = TextureLoader.getTexture(IMG_PATH);
    private static final Texture OUTLINE = TextureLoader.getTexture(OUTLINE_PATH);
    public AbstractPlayer p = AbstractDungeon.player;
    public int potency = 30;

    public AshLotus()
    {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.MAGICAL);
    }

    public void onPlayerEndTurn()
    {
        if (p.hand.size() == 0 &&
            p.drawPile.size() == 0 &&
            p.discardPile.size() == 0)
        {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, potency + p.exhaustPile.size()));
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(potency + p.exhaustPile.size()), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
        }
    }

    public String getUpdatedDescription()
    {
        return (this.DESCRIPTIONS[0] + 30 + this.DESCRIPTIONS[1]);
    }

    public boolean canSpawn()
    {
        boolean toReturn = false;

        toReturn = (AbstractDungeon.player instanceof Ironclad);

        if (jedi.isGluttonLoaded)
        {
            toReturn = (toReturn || AbstractDungeon.player instanceof GluttonCharacter);
        }

        if (jedi.isHubrisLoaded)
        {
            toReturn = (toReturn || AbstractDungeon.player.hasRelic(BlackHole.ID));
        }

        return toReturn;
    }
}
