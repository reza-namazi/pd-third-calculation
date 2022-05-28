package ir.pd.model;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class CommonEnrichedRequest {
  RawRequest rawRequest;
  BigDecimal base;
  BigDecimal noClaimRate;
  BigDecimal tax;
}
