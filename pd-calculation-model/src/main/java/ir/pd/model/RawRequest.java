package ir.pd.model;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.Instant;

@Builder(toBuilder = true)
@Value
public class RawRequest {
  String correlationId;
  Instant currentDate;
  Integer manufactureYear;
  Integer numberOfNoClaimYears;
}
