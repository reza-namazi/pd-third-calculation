package ir.pd.tp.item


import ir.pd.model.CalculationTypeAware

import java.util.function.Function

trait CalculationUnit<CALCULATION, REQUEST> implements Function<CALCULATION, CALCULATION>, CalculationTypeAware {

    abstract boolean match(REQUEST request)
}