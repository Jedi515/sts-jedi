package mod.jedi.potions;

import basemod.ReflectionHacks;
import basemod.abstracts.CustomPotion;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.EventRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.screens.DungeonMapScreen;
import mod.jedi.interfaces.OutOfCombatPotion;

import java.util.ArrayList;

public class MysteryPotion
    extends CustomPotion
    implements OutOfCombatPotion
{
    public static final String ID = "jedi:mysterypotion";

    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public MysteryPotion() {
        super(NAME, ID, PotionRarity.RARE, PotionSize.SNECKO, PotionColor.SNECKO);
        this.potency = getPotency();
        this.description = DESCRIPTIONS[0];
        this.isThrown = false;
        this.tips.add(new PowerTip(this.name, this.description));

        liquidColor = Color.GOLD.cpy();
        hybridColor = Color.CLEAR.cpy();
        spotsColor = null;
    }

    @Override
    public boolean canUse()
    {
        return Settings.isEndless || (AbstractDungeon.actNum != 4);
    }

    @Override
    public void use(AbstractCreature abstractCreature)
    {
        ArrayList<MapRoomNode> visibleMapNodes = (ArrayList<MapRoomNode>) ReflectionHacks.getPrivate(AbstractDungeon.dungeonMapScreen, DungeonMapScreen.class, "visibleMapNodes");
        for (MapRoomNode n : visibleMapNodes) {
            if (n.y == AbstractDungeon.getCurrMapNode().y+1 && !(n.room instanceof MonsterRoomBoss)) {
                n.setRoom(new EventRoom());
            }
        }
    }

    @Override
    public int getPotency(int i) {
        return 0;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new MysteryPotion();
    }
}
