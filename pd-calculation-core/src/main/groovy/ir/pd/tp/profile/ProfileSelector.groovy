package ir.pd.tp.profile

import ir.pd.model.Calculation
import ir.pd.model.CommonEnrichedRequest
import ir.pd.tp.item.CalculationUnit
import ir.pd.tp.profile.base.Default1400Calculation

import java.util.function.Function

class ProfileSelector implements Function<Calculation, Tuple2<Calculation, Profile<? extends CalculationUnit<Calculation, CommonEnrichedRequest>>>> {

    Collection<Profile<? extends CalculationUnit<Calculation, CommonEnrichedRequest>>> profiles = [new Default1400Calculation()]

    @Override
    Tuple2<Calculation, Profile<? extends CalculationUnit<Calculation, CommonEnrichedRequest>>> apply(Calculation calculation) {
        def profileToUse = profiles.find { it.match(calculation) }
        Tuple.tuple calculation, profileToUse
    }
}
