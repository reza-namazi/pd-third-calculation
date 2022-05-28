package ir.pd.model;

import groovy.lang.Tuple2;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.control.Option;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Supplier;

import static java.math.BigDecimal.ZERO;

@Builder(toBuilder = true)
@Value
public class Calculation {
  CommonEnrichedRequest request;
  Seq<CalculationDetail> details;

  public boolean exist(CalculationType calculationType) {
    return details
      .filter(calculationDetail -> calculationDetail.getCalculationType().equals(calculationType))
      .headOption()
      .isDefined();
  }

  public Option<BigDecimal> get(CalculationType calculationType) {
    return details
      .filter(calculationDetail -> calculationDetail.getCalculationType().equals(calculationType))
      .map(CalculationDetail::getValue)
      .headOption();
  }

  public BigDecimal getOr0(CalculationType calculationType) {
    return get(calculationType).getOrElse(ZERO);
  }

  public BigDecimal getSumOf(CalculationType... calculationType) {
    return Arrays.stream(calculationType).map(this::getOr0).reduce(BigDecimal::add).orElse(ZERO);
  }

  public CBuilder addNew(CalculationTypeAware type) {
    return new CBuilder(this, type);
  }


  static class CBuilder {
    Calculation calculation;
    CalculationTypeAware type;
    BigDecimal value;
    Option<Map<String, Object>> extra;

    public CBuilder(Calculation calculation, CalculationTypeAware type) {
      this.calculation = calculation;
      this.type = type;
    }

    public CBuilder value(Supplier<BigDecimal> calc) {
      this.value = calc.get();
      extra = Option.none();
      return this;
    }

    public CBuilder valueExtra(Supplier<Tuple2<BigDecimal, Map<String, Object>>> calc) {
      Tuple2<BigDecimal, Map<String, Object>> r = calc.get();
      value = r.getV1();
      extra = Option.of(r.getV2());
      return this;
    }

    public Calculation end() {
      return calculation.toBuilder()
        .details(calculation.details.append(CalculationDetail.builder()
          .calculationType(type.calculationTyp())
          .value(value)
          .extra(extra.getOrElse(Collections.emptyMap()))
          .build()))
        .build();
    }

  }
}
