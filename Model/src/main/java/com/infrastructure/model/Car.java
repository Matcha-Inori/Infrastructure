package com.infrastructure.model;

import java.util.Optional;

public class Car
{
    private Insurance insurance;

    public Car()
    {
        this(null);
    }

    public Car(Insurance insurance)
    {
        this.insurance = insurance;
    }

    public Insurance getInsurance()
    {
        return insurance;
    }

    public void setInsurance(Insurance insurance)
    {
        this.insurance = insurance;
    }

    public Optional<Insurance> getInsuranceOptional()
    {
        return Optional.ofNullable(this.insurance);
    }
}
