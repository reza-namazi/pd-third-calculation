package ir.pd.tp

import groovy.util.logging.Slf4j
import ir.pd.model.Calculation
import ir.pd.model.CommonEnrichedRequest
import ir.pd.tp.item.CalculationUnit

@Slf4j
class SyncCalculation {

    Calculation doCalculate(Calculation calculation, CalculationUnit<Calculation, CommonEnrichedRequest>... calculationUnit) {

        calculationUnit
                .findAll { it.match calculation.request }
                .eachWithIndex { it, idx -> log.debug('{} : {}', idx, it.calculationTyp() as String) }
                .inject(calculation) { lastCalculation, item -> item.apply lastCalculation }
    }
}
