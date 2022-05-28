package ir.pd.tp;

import io.vavr.collection.List;
import io.vavr.control.Either;
import ir.pd.model.Calculation;
import ir.pd.model.CalculationType;
import ir.pd.model.CommonEnrichedRequest;
import ir.pd.model.RawRequest;
import ir.pd.model.validator.Response;
import ir.pd.tp.enrich.Enricher;
import ir.pd.tp.enrich.SyncRawRequestEnricher;
import ir.pd.tp.profile.ProfileSelector;
import ir.pd.tp.validator.DefaultRequestValidator;
import ir.pd.tp.validator.RequestValidator;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.function.Function;

import static ir.pd.tp.Fixtures.rawRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SyncCalculationTest {

  RequestValidator requestValidator = new DefaultRequestValidator();
  Enricher<RawRequest, CommonEnrichedRequest> enricher = new SyncRawRequestEnricher();
  Function<CommonEnrichedRequest, Calculation> createCalculation = it -> Calculation.builder().request(it).details(List.empty()).build();
  SyncCalculation syncCalculation = new SyncCalculation();
  ProfileSelector profileSelector = new ProfileSelector();

  @Test
  void thatTheCalculationHasCorrectResult() {
    SyncRequestHandler syncRequestHandler = new SyncRequestHandler(requestValidator, enricher, createCalculation, syncCalculation, profileSelector);
    RawRequest rawRequest = rawRequest();

    Either<Response.InvalidResponse, Response.ValidResponse> calculation = syncRequestHandler.handle(rawRequest);

    assertTrue(calculation.isRight(), "has the right answer");
    assertEquals(4, calculation.get().result.getDetails().size(), "has 4 calculation details. {base, noClaim, tax, total}");
    assertEquals(new BigDecimal("200.0"), calculation.get().result.get(CalculationType.BASE).get());
    assertEquals(new BigDecimal("40.00"), calculation.get().result.get(CalculationType.NO_CLAIM).get());
    assertEquals(new BigDecimal("24.000"), calculation.get().result.get(CalculationType.TAX).get());
    assertEquals(new BigDecimal("264.000"), calculation.get().result.get(CalculationType.TOTAL).get());
  }

  @Test
  void thatTheValidationResultFailsWhen_noClaimYears_IsInvalid() {
    SyncRequestHandler syncRequestHandler = new SyncRequestHandler(requestValidator, enricher, createCalculation, syncCalculation, profileSelector);
    RawRequest rawRequest = rawRequest().toBuilder().numberOfNoClaimYears(999999).build();

    Either<Response.InvalidResponse, Response.ValidResponse> calculation = syncRequestHandler.handle(rawRequest);

    assertTrue(calculation.isLeft(), "has the right answer");
    assertFalse(calculation.getLeft().errors.isEmpty(), "has error result");
    assertEquals("Invalid noClaimYears value 999999. It must be between (0..100)", calculation.getLeft().errors.get(0));
  }
}
