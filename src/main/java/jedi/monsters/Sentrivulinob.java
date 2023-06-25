package jedi.monsters;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import jedi.jedi;

public class Sentrivulinob
    extends AbstractMonster
{
    public static String ID = jedi.makeID(Sentrivulinob.class.getSimpleName());
    public static MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static String NAME = monsterStrings.NAME;
    public static String[] DIALOG = monsterStrings.DIALOG;
    public static String[] MOVES = monsterStrings.MOVES;

    public Sentrivulinob(String name, String id, int maxHealth, float hb_x, float hb_y, float hb_w, float hb_h, String imgUrl, float offsetX, float offsetY)
    {
        super(name, id, maxHealth, hb_x, hb_y, hb_w, hb_h, imgUrl, offsetX, offsetY);
    }

    @Override
    public void takeTurn()
    {

    }

    @Override
    protected void getMove(int i)
    {

    }
}
