package ir.pd.tp.item.base

import ir.pd.model.Calculation
import ir.pd.model.CalculationType
import ir.pd.model.CommonEnrichedRequest
import ir.pd.tp.item.BaseCalculationUnit

import static groovy.lang.Tuple.tuple
import static ir.pd.model.CalculationType.BASE

class DefaultBase1400 implements BaseCalculationUnit {

    @Override
    Calculation apply(Calculation calculation) {
        calculation.addNew(this)
                .valueExtra { ->
                    tuple calculation.request.base,
                            [
                                    base       : calculation.request.base,
                                    vehicleType: 2
                            ]
                }
                .end()
    }

    @Override
    CalculationType calculationTyp() {
        BASE
    }

    @Override
    boolean match(CommonEnrichedRequest request) {
        true
    }
}
