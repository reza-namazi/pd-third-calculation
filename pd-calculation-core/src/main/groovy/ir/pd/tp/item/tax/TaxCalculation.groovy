package ir.pd.tp.item.tax

import ir.pd.model.Calculation
import ir.pd.model.CalculationType
import ir.pd.model.CommonEnrichedRequest
import ir.pd.tp.item.BaseCalculationUnit

import static groovy.lang.Tuple.tuple
import static ir.pd.model.CalculationType.*

class TaxCalculation implements BaseCalculationUnit {

    @Override
    boolean match(CommonEnrichedRequest request) {
        true
    }

    @Override
    CalculationType calculationTyp() {
        TAX
    }

    @Override
    Calculation apply(Calculation calculation) {
        calculation.addNew(this)
                .valueExtra {
                    def val = calculation.request.tax * calculation.getSumOf(BASE, NO_CLAIM)
                    tuple val,
                            [
                                    tax         : val,
                                    base_noclaim: calculation.getSumOf(BASE, NO_CLAIM),
                                    taxRate     : calculation.request.tax
                            ]
                }
                .end()
    }
}
