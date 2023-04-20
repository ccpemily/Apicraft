package com.emily.apicraft.genetics.mutations.condition;

import com.emily.apicraft.interfaces.block.IBeeHousing;
import com.emily.apicraft.interfaces.genetics.mutations.IBeeCondition;
import net.minecraft.network.chat.Component;

import java.util.List;

public class ConditionTimeLimited implements IBeeCondition {
    private final int timeStart;
    private final int timeEnd;

    public ConditionTimeLimited(int timeStart, int timeEnd){
        this.timeEnd = timeEnd;
        this.timeStart = timeStart;
    }
    @Override
    public float applyModifier(IBeeHousing beeHousing, float chance) {
        int time = (int) (beeHousing.getBeeHousingLevel().getDayTime() % 24000);
        boolean inRange = time <= timeEnd && time >= timeStart;
        return inRange ? chance : 0;
    }

    @Override
    public List<Component> getConditionTooltip() {
        return null;
    }
}
