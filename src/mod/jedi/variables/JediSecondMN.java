package mod.jedi.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import mod.jedi.cards.CustomJediCard;

public class JediSecondMN
    extends DynamicVariable
{
    @Override
    public String key() {
        return "jedi:SecondMN";
    }

    @Override
    public boolean isModified(AbstractCard abstractCard) {
        if (abstractCard instanceof CustomJediCard)
        return ((CustomJediCard)abstractCard).isSecondMNModified;

        return false;
    }

    @Override
    public int value(AbstractCard abstractCard) {
        if (abstractCard instanceof CustomJediCard)
        return ((CustomJediCard)abstractCard).secondMN;

        return -1;
    }

    @Override
    public int baseValue(AbstractCard abstractCard) {
        if (abstractCard instanceof CustomJediCard)
        return ((CustomJediCard)abstractCard).baseSecondMN;

        return -1;
    }

    @Override
    public boolean upgraded(AbstractCard abstractCard) {
        if (abstractCard instanceof CustomJediCard)
        return ((CustomJediCard)abstractCard).isSecondMNUpgraded;

        return false;
    }
}
