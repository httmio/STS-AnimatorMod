package eatyourbeets.actions;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import eatyourbeets.cards.animator.Entoma;

public class EntomaAction extends AnimatorAction
{
    private final Entoma entoma;

    public EntomaAction(Entoma entoma)
    {
        this.entoma = entoma;
        this.actionType = ActionType.SPECIAL;
        this.duration = 0.1F;
    }

    public void update()
    {
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
        {
            if (c.uuid.equals(entoma.uuid))
            {
                c.baseDamage = c.baseDamage + 1;
                c.isDamageModified = false;
            }
        }
        //AbstractDungeon.actionManager.addToBottom(new ModifyDamagePermanentlyAction(entoma.uuid, 1));

        AbstractDungeon.player.increaseMaxHp(2, false);
        this.isDone = true;
    }
}
