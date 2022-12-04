package com.company.passwordManager.model;

import java.util.Objects;

public class ValidationCode {

    private int validationCode;

    public ValidationCode() {
    }

    public ValidationCode(int validationCode) {
        this.validationCode = validationCode;
    }

    public int getValidationCode() {
        return validationCode;
    }

    public void setValidationCode(int validationCode) {
        this.validationCode = validationCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValidationCode that = (ValidationCode) o;
        return validationCode == that.validationCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(validationCode);
    }
}
