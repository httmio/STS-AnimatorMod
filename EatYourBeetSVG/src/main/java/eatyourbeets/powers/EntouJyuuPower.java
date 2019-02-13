package eatyourbeets.powers;

import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EntouJyuuPower extends AnimatorPower
{
    public static final String POWER_ID = CreateFullID(EntouJyuuPower.class.getSimpleName());

    public EntouJyuuPower(AbstractCreature owner, int amount)
    {
        super(owner, POWER_ID);

        this.amount = amount;
        updateDescription();
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m)
    {
        if (card.type == AbstractCard.CardType.ATTACK)
        {
            AbstractDungeon.actionManager.addToBottom(new ModifyDamageAction(card.uuid, this.amount));
        }

        this.flash();

        super.onPlayCard(card, m);
    }
}
