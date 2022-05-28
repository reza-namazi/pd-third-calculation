package ir.pd.model.validator;

import ir.pd.model.Calculation;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@EqualsAndHashCode
public abstract class Response {

  public abstract boolean isSuccess();

  public static InvalidResponse of(List<String> errors) {
    return new InvalidResponse(errors);
  }

  public static ValidResponse of(Calculation calculation) {
    return new ValidResponse(calculation);
  }

  @EqualsAndHashCode(callSuper = false)
  @ToString
  public static class ValidResponse extends Response {
    public final Calculation result;

    public ValidResponse(Calculation result) {
      this.result = result;
    }

    @Override
    public boolean isSuccess() {
      return true;
    }
  }

  @EqualsAndHashCode(callSuper = false)
  @ToString
  public static class InvalidResponse extends Response {
    public final List<String> errors;

    public InvalidResponse(List<String> errors) {
      this.errors = errors;
    }

    @Override
    public boolean isSuccess() {
      return false;
    }
  }
}

