package ir.pd.calculation

import io.vavr.collection.List
import ir.pd.model.Calculation
import ir.pd.model.CommonEnrichedRequest
import ir.pd.tp.item.base.DefaultBase1400

class GMain {
    static void main(String[] args) {

        CommonEnrichedRequest commonEnrichedRequest = CommonEnrichedRequest.builder().build()
        DefaultBase1400 a = new DefaultBase1400()
        Calculation calculation = Calculation.builder()
                .request(commonEnrichedRequest)
                .details(List.of())
                .build()
        def apply = a.apply(calculation)
        def apply1 = a.apply(apply)
        println "apply = $apply1"
    }
}
