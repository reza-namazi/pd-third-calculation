package ir.pd.tp.item.total

import ir.pd.model.Calculation
import ir.pd.model.CalculationType
import ir.pd.model.CommonEnrichedRequest
import ir.pd.tp.item.BaseCalculationUnit

import static groovy.lang.Tuple.tuple
import static ir.pd.model.CalculationType.*

class TotalCalculation implements BaseCalculationUnit {


    @Override
    boolean match(CommonEnrichedRequest request) {
        true
    }

    @Override
    CalculationType calculationTyp() {
        TOTAL
    }

    @Override
    Calculation apply(Calculation calculation) {
        calculation.addNew(this)
                .valueExtra {
                    def total = calculation.getSumOf(BASE, NO_CLAIM, TAX,)
                    tuple total,
                            [
                                    total: total
                            ]
                }
                .end()
    }
}
