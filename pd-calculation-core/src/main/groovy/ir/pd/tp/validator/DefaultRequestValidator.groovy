package ir.pd.tp.validator

import io.vavr.collection.Seq
import io.vavr.control.Validation
import ir.pd.model.RawRequest

import static io.vavr.control.Validation.invalid
import static io.vavr.control.Validation.valid

class DefaultRequestValidator implements RequestValidator {

    @Override
    Validation<List<String>, RawRequest> validate(RawRequest request) {
        Validation
                .combine(
                        validateNumberOfNoClaimYears(request),
                        validateManufactureYear(request),
                )
                .ap((a, b) -> request)
                .mapError(Seq::toJavaList)
    }

    static Validation<String, Number> validateNumberOfNoClaimYears(RawRequest request) {
        validateRange(request.numberOfNoClaimYears, 0, 100, 'noClaimYears')
    }

    static Validation<String, Number> validateManufactureYear(RawRequest request) {
        validateRange(request.manufactureYear, 1300, 2090, 'manufactureYear')
    }

    static Validation<String, Number> validateRange(Number toCheck, Number from, Number to, String errorTag) {
        if (toCheck < from || toCheck > to) {
            invalid "Invalid ${errorTag} value ${toCheck}. It must be between (${from}..${to})" as String
        } else {
            valid toCheck
        }
    }
}
