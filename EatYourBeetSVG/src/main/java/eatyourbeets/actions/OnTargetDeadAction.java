package eatyourbeets.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class OnTargetDeadAction extends AnimatorAction
{
    private AbstractGameAction action;

    public OnTargetDeadAction(AbstractCreature target, AbstractGameAction action)
    {
        this.target = target;
        this.action = action;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = action.actionType;
    }

    public void update()
    {
        if (target.isDead || target.isDying)
        {
            AbstractDungeon.actionManager.addToBottom(action);
        }
        this.isDone = true;
    }
}
