package mod.jedi.relics;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.hubris.screens.select.RelicSelectScreen;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import mod.jedi.util.TextureLoader;
import sts_jedi.jedi;

import java.util.ArrayList;

public class Command_common
    extends AbstractCommand
{
    public static final String ID = "jedi:command_common";
    public static final String PATH = "resources/jedi/images/relics/";
    public static final String OUTLINE_PATH = PATH + "outline/" + ID.substring(5) + ".png";
    public static final String IMG_PATH = PATH + ID.substring(5) + ".png";
    private static final Texture IMG = TextureLoader.getTexture(IMG_PATH);
    private static final Texture OUTLINE = TextureLoader.getTexture(OUTLINE_PATH);

    public Command_common()
    {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    protected void openRelicSelect() {
        this.relicSelected = false;
        ArrayList<AbstractRelic> relics = new ArrayList(RelicLibrary.commonList);
        if (!jedi.CommandHasCopy)
        {
            relics.removeIf((r) -> AbstractDungeon.player.hasRelic(r.relicId));
        }

        if (!jedi.CommandLocked)
        {
            relics.removeIf((r) -> UnlockTracker.isRelicLocked(r.relicId));
        }

        if (!jedi.CommandUnseen)
        {
            relics.removeIf((r) -> !UnlockTracker.isRelicSeen(r.relicId));
        }
        this.relicSelectScreen.open(relics);
    }

    public int getPrice()
    {
        return 150;
    }
}
