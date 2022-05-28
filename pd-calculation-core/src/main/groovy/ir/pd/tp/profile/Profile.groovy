package ir.pd.tp.profile

import ir.pd.model.Calculation
import ir.pd.model.CommonEnrichedRequest
import ir.pd.tp.item.CalculationUnit

interface Profile<T extends CalculationUnit> {

    boolean match(Calculation calculation)

    T[] getCalculationUnits()
}