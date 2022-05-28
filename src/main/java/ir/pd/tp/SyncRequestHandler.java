package ir.pd.tp;

import io.vavr.control.Either;
import ir.pd.model.Calculation;
import ir.pd.model.CommonEnrichedRequest;
import ir.pd.model.RawRequest;
import ir.pd.model.validator.Response;
import ir.pd.tp.enrich.Enricher;
import ir.pd.tp.profile.ProfileSelector;
import ir.pd.tp.validator.RequestValidator;

import java.util.function.Function;

public class SyncRequestHandler {

  RequestValidator requestValidator;
  Enricher<RawRequest, CommonEnrichedRequest> enricher;
  Function<CommonEnrichedRequest, Calculation> createCalculation;
  SyncCalculation syncCalculation;
  ProfileSelector profileSelector;

  public SyncRequestHandler(RequestValidator requestValidator, Enricher<RawRequest, CommonEnrichedRequest> enricher, Function<CommonEnrichedRequest, Calculation> createCalculation, SyncCalculation syncCalculation, ProfileSelector profileSelector) {
    this.requestValidator = requestValidator;
    this.enricher = enricher;
    this.createCalculation = createCalculation;
    this.syncCalculation = syncCalculation;
    this.profileSelector = profileSelector;
  }

  public Either<Response.InvalidResponse, Response.ValidResponse> handle(RawRequest request) {
    return requestValidator.validate(request)
      .mapError(Response::of)
      .toEither()
      .map(enricher)
      .map(createCalculation)
      .map(profileSelector)
      .map(it -> syncCalculation.doCalculate(it.getV1(), it.getV2().getCalculationUnits()))
      .map(Response::of);
  }
}
