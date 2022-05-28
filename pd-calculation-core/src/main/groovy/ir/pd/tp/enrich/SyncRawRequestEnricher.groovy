package ir.pd.tp.enrich

import ir.pd.model.CommonEnrichedRequest
import ir.pd.model.RawRequest

class SyncRawRequestEnricher implements Enricher<RawRequest, CommonEnrichedRequest> {

    @Override
    CommonEnrichedRequest apply(RawRequest rawRequest) {
        //lets assume that we have already fetched the data from database in a blocking manner!
        CommonEnrichedRequest.builder()
                .rawRequest(rawRequest)
                .base(200.0)
                .noClaimRate(0.2)
                .tax(0.1)
                .build()
    }
}
