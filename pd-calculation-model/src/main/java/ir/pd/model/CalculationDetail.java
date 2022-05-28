package ir.pd.model;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.Map;

@Value
@Builder
public class CalculationDetail {
  CalculationType calculationType;
  BigDecimal value;
  Map<String, Object> extra;
}
