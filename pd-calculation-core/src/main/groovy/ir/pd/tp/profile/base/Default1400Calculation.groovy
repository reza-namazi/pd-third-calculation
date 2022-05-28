package ir.pd.tp.profile.base

import ir.pd.model.Calculation
import ir.pd.tp.item.BaseCalculationUnit
import ir.pd.tp.item.base.DefaultBase1400
import ir.pd.tp.item.claim.NoClaim1400
import ir.pd.tp.item.tax.TaxCalculation
import ir.pd.tp.item.total.TotalCalculation

class Default1400Calculation implements BaseProfile {

    @Override
    boolean match(Calculation calculation) {
        //for the moment we assume there is only one valid profile. we can extend the arguments afterwards
        true
    }

    @Override
    BaseCalculationUnit[] getCalculationUnits() {
        [
                new DefaultBase1400(),
                new NoClaim1400(),
                new TaxCalculation(),
                new TotalCalculation(),
        ]
    }

}
