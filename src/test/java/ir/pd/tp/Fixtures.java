package ir.pd.tp;

import ir.pd.model.RawRequest;

import java.time.Instant;

public class Fixtures {

  public static RawRequest rawRequest() {
    return RawRequest.builder()
      .correlationId("corl1")
      .currentDate(Instant.parse("2020-02-02T00:00:00Z"))
      .manufactureYear(2020)
      .numberOfNoClaimYears(2)
      .build();
  }
}
