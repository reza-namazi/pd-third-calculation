package ir.pd.tp.item.claim

import ir.pd.model.Calculation
import ir.pd.model.CalculationType
import ir.pd.model.CommonEnrichedRequest
import ir.pd.tp.item.BaseCalculationUnit

import static groovy.lang.Tuple.tuple
import static ir.pd.model.CalculationType.BASE
import static ir.pd.model.CalculationType.NO_CLAIM
import static java.math.BigDecimal.ZERO

class NoClaim1400 implements BaseCalculationUnit {

    @Override
    boolean match(CommonEnrichedRequest request) {
        true
    }

    @Override
    CalculationType calculationTyp() {
        NO_CLAIM
    }

    @Override
    Calculation apply(Calculation calculation) {
        calculation.addNew(this)
                .valueExtra {
                    def val = calculation.get BASE map { base -> calculation.request.noClaimRate * base } getOrElse ZERO
                    tuple val,
                            [
                                    noClaim     : val,
                                    noClaimYears: calculation.request.rawRequest.numberOfNoClaimYears,
                                    noClaimRate : calculation.request.noClaimRate
                            ]
                }
                .end()
    }
}
