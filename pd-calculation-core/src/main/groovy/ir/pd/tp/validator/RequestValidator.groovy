package ir.pd.tp.validator

import io.vavr.control.Validation
import ir.pd.model.RawRequest

trait RequestValidator {
    abstract Validation<List<String>, RawRequest> validate(RawRequest request)
}
