package com.company.vector;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class Vector2D implements IVector {
    private double x;
    private double y;

    @Override
    public double abs() {
        return Math.pow(x * x + y * y, 1. / 2);
    }

    @Override
    public double cdot(final IVector ivector) {
        double multiX = this.getComponents()[0] * ivector.getComponents()[0];
        double multiY = this.getComponents()[1] * ivector.getComponents()[1];
        return multiX + multiY;
    }

    @Override
    public double[] getComponents() {
        double[] components = new double[2];
        components[0] = this.x;
        components[1] = this.y;
        return components;
    }

}
