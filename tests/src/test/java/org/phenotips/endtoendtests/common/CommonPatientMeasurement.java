/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/
 */
package org.phenotips.endtoendtests.common;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * This class represents the values for one measurement entry on a patient form. Fields will be null when
 * no value was entered for the particular measurement.
 */
public class CommonPatientMeasurement
{
    private Float weight;

    private Float armSpan;

    private Float headCircumference;

    private Float outerCanthalDistance;

    private Float leftHandLength;

    private Float rightHandLength;

    private Float height;

    private Float sittingHeight;

    private Float philtrumLength;

    private Float innerCanthalDistance;

    private Float leftPalmLength;

    private Float rightPalmLength;

    private Float leftEarLength;

    private Float palpebralFissureLength;

    private Float leftFootLength;

    private Float rightFootLength;

    private Float rightEarLength;

    private Float interpupilaryDistance;

    @Override
    public boolean equals(Object o)
    {
        return EqualsBuilder.reflectionEquals(this, o, false);
    }

    @Override
    public int hashCode()
    {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString()
    {
        return ReflectionToStringBuilder.toString(this);
    }

    // Builder pattern instead of a giant constructor. All fields are optional and unset ones will remain null.

    public CommonPatientMeasurement withWeight(Float weight)
    {
        this.weight = weight;
        return this;
    }

    public CommonPatientMeasurement withArmSpan(Float armSpan)
    {
        this.armSpan = armSpan;
        return this;
    }

    public CommonPatientMeasurement withHeadCircumference(Float headCircumference)
    {
        this.headCircumference = headCircumference;
        return this;
    }

    public CommonPatientMeasurement withOuterCanthalDistance(Float outerCanthalDistance)
    {
        this.outerCanthalDistance = outerCanthalDistance;
        return this;
    }

    public CommonPatientMeasurement withLeftHandLength(Float leftHandLength)
    {
        this.leftHandLength = leftHandLength;
        return this;
    }

    public CommonPatientMeasurement withRightHandLength(Float rightHandLength)
    {
        this.rightHandLength = rightHandLength;
        return this;
    }

    public CommonPatientMeasurement withHeight(Float height)
    {
        this.height = height;
        return this;
    }

    public CommonPatientMeasurement withSittingHeight(Float sittingHeight)
    {
        this.sittingHeight = sittingHeight;
        return this;
    }

    public CommonPatientMeasurement withPhiltrumLength(Float philtrumLength)
    {
        this.philtrumLength = philtrumLength;
        return this;
    }

    public CommonPatientMeasurement withInnercanthalDistance(Float inntercanthalDistance)
    {
        this.innerCanthalDistance = inntercanthalDistance;
        return this;
    }

    public CommonPatientMeasurement withLeftPalmLength(Float leftPalmLength)
    {
        this.leftPalmLength = leftPalmLength;
        return this;
    }

    public CommonPatientMeasurement withRightPalmLength(Float rightPalmLength)
    {
        this.rightPalmLength = rightPalmLength;
        return this;
    }

    public CommonPatientMeasurement withLeftEarLength(Float leftEarLength)
    {
        this.leftEarLength = leftEarLength;
        return this;
    }

    public CommonPatientMeasurement withPalpebralFissureLength(Float palpebralFissureLength)
    {
        this.palpebralFissureLength = palpebralFissureLength;
        return this;
    }

    public CommonPatientMeasurement withLeftFootLength(Float leftFootLength)
    {
        this.leftFootLength = leftFootLength;
        return this;
    }

    public CommonPatientMeasurement withRightFootLength(Float rightFootLength)
    {
        this.rightFootLength = rightFootLength;
        return this;
    }

    public CommonPatientMeasurement withRightEarLength(Float rightEarLength)
    {
        this.rightEarLength = rightEarLength;
        return this;
    }

    public CommonPatientMeasurement withInterpupilaryDistance(Float interpupilaryDistance)
    {
        this.interpupilaryDistance = interpupilaryDistance;
        return this;
    }

    // Getters for all fields
    
    public Float getWeight()
    {
        return weight;
    }

    public Float getArmSpan()
    {
        return armSpan;
    }

    public Float getHeadCircumference()
    {
        return headCircumference;
    }

    public Float getOuterCanthalDistance()
    {
        return outerCanthalDistance;
    }

    public Float getLeftHandLength()
    {
        return leftHandLength;
    }

    public Float getRightHandLength()
    {
        return rightHandLength;
    }

    public Float getHeight()
    {
        return height;
    }

    public Float getSittingHeight()
    {
        return sittingHeight;
    }

    public Float getPhiltrumLength()
    {
        return philtrumLength;
    }

    public Float getInnerCanthalDistance()
    {
        return innerCanthalDistance;
    }

    public Float getLeftPalmLength()
    {
        return leftPalmLength;
    }

    public Float getRightPalmLength()
    {
        return rightPalmLength;
    }

    public Float getLeftEarLength()
    {
        return leftEarLength;
    }

    public Float getPalpebralFissureLength()
    {
        return palpebralFissureLength;
    }

    public Float getLeftFootLength()
    {
        return leftFootLength;
    }

    public Float getRightFootLength()
    {
        return rightFootLength;
    }

    public Float getRightEarLength()
    {
        return rightEarLength;
    }

    public Float getInterpupilaryDistance()
    {
        return interpupilaryDistance;
    }
}
