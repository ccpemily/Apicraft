package com.emily.apicraft.interfaces.genetics.conditions;

import java.util.function.Supplier;

public interface IConditionType<T extends IBeeCondition> {
    Supplier<IConditionSerializer<T>> getSerializer();
}
