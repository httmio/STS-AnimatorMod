package eatyourbeets.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class OnTargetBlockLostAction extends AnimatorAction
{
    private final DamageAction damageAction;
    private final AbstractGameAction action;
    private final int initialBlock;

    public OnTargetBlockLostAction(AbstractCreature target, AbstractGameAction action)
    {
        this.target = target;
        this.initialBlock = target.currentBlock;
        this.damageAction = null;
        this.action = action;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = action.actionType;
    }

    public OnTargetBlockLostAction(AbstractCreature target, DamageAction damageAction, AbstractGameAction action)
    {
        this.target = target;
        this.initialBlock = target.currentBlock;
        this.damageAction = damageAction;
        this.action = action;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = damageAction.actionType;
    }

    public void update()
    {
        if (this.damageAction != null && !this.damageAction.isDone)
        {
            this.damageAction.update();

            return;
        }

        if (initialBlock > 0 && target.currentBlock < initialBlock)
        {
            action.amount = initialBlock - target.currentBlock;
            AbstractDungeon.actionManager.addToBottom(action);
        }

        this.isDone = true;
    }
}
