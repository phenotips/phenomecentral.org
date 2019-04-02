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

import java.util.Objects;

/**
 * This class acts as a public struct for patient measurements, too many fields to just pass as a List of Strings. All
 * the possible numerical fields for a patient's measurements should go here.
 */
public class CommonPatientMeasurement
{
    public float weight;

    public float armSpan;

    public float headCircumference;

    public float outerCanthalDistance;

    public float leftHandLength;

    public float rightHandLength;

    public float height;

    public float sittingHeight;

    public float philtrumLength;

    public float inntercanthalDistance;

    public float leftPalmLength;

    public float rightPalmLength;

    public float leftEarLength;

    public float palpebralFissureLength;

    public float leftFootLength;

    public float rightFootLength;

    public float rightEarLength;

    public float interpupilaryDistance;

    // Ctor
    public CommonPatientMeasurement(float weight, float armSpan, float headCircumference, float outerCanthalDistance,
        float leftHandLength, float rightHandLength, float height, float sittingHeight, float philtrumLength,
        float inntercanthalDistance, float leftPalmLength, float rightPalmLength, float leftEarLength,
        float palpebralFissureLength, float leftFootLength, float rightFootLength, float rightEarLength,
        float interpupilaryDistance)
    {
        this.weight = weight;
        this.armSpan = armSpan;
        this.headCircumference = headCircumference;
        this.outerCanthalDistance = outerCanthalDistance;
        this.leftHandLength = leftHandLength;
        this.rightHandLength = rightHandLength;
        this.height = height;
        this.sittingHeight = sittingHeight;
        this.philtrumLength = philtrumLength;
        this.inntercanthalDistance = inntercanthalDistance;
        this.leftPalmLength = leftPalmLength;
        this.rightPalmLength = rightPalmLength;
        this.leftEarLength = leftEarLength;
        this.palpebralFissureLength = palpebralFissureLength;
        this.leftFootLength = leftFootLength;
        this.rightFootLength = rightFootLength;
        this.rightEarLength = rightEarLength;
        this.interpupilaryDistance = interpupilaryDistance;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CommonPatientMeasurement that = (CommonPatientMeasurement) o;
        return Float.compare(that.weight, this.weight) == 0 &&
            Float.compare(that.armSpan, this.armSpan) == 0 &&
            Float.compare(that.headCircumference, this.headCircumference) == 0 &&
            Float.compare(that.outerCanthalDistance, this.outerCanthalDistance) == 0 &&
            Float.compare(that.leftHandLength, this.leftHandLength) == 0 &&
            Float.compare(that.rightHandLength, this.rightHandLength) == 0 &&
            Float.compare(that.height, this.height) == 0 &&
            Float.compare(that.sittingHeight, this.sittingHeight) == 0 &&
            Float.compare(that.philtrumLength, this.philtrumLength) == 0 &&
            Float.compare(that.inntercanthalDistance, this.inntercanthalDistance) == 0 &&
            Float.compare(that.leftPalmLength, this.leftPalmLength) == 0 &&
            Float.compare(that.rightPalmLength, this.rightPalmLength) == 0 &&
            Float.compare(that.leftEarLength, this.leftEarLength) == 0 &&
            Float.compare(that.palpebralFissureLength, this.palpebralFissureLength) == 0 &&
            Float.compare(that.leftFootLength, this.leftFootLength) == 0 &&
            Float.compare(that.rightFootLength, this.rightFootLength) == 0 &&
            Float.compare(that.rightEarLength, this.rightEarLength) == 0 &&
            Float.compare(that.interpupilaryDistance, this.interpupilaryDistance) == 0;
    }

    @Override
    public int hashCode()
    {
        return Objects
            .hash(this.weight, this.armSpan, this.headCircumference, this.outerCanthalDistance, this.leftHandLength,
                this.rightHandLength, this.height,
                this.sittingHeight, this.philtrumLength, this.inntercanthalDistance, this.leftPalmLength,
                this.rightPalmLength, this.leftEarLength,
                this.palpebralFissureLength, this.leftFootLength, this.rightFootLength, this.rightEarLength,
                this.interpupilaryDistance);
    }

    @Override
    public String toString()
    {
        return "CommonPatientMeasurement{" +
            "weight=" + this.weight +
            ", armSpan=" + this.armSpan +
            ", headCircumference=" + this.headCircumference +
            ", outerCanthalDistance=" + this.outerCanthalDistance +
            ", leftHandLength=" + this.leftHandLength +
            ", rightHandLength=" + this.rightHandLength +
            ", height=" + this.height +
            ", sittingHeight=" + this.sittingHeight +
            ", philtrumLength=" + this.philtrumLength +
            ", inntercanthalDistance=" + this.inntercanthalDistance +
            ", leftPalmLength=" + this.leftPalmLength +
            ", rightPalmLength=" + this.rightPalmLength +
            ", leftEarLength=" + this.leftEarLength +
            ", palpebralFissureLength=" + this.palpebralFissureLength +
            ", leftFootLength=" + this.leftFootLength +
            ", rightFootLength=" + this.rightFootLength +
            ", rightEarLength=" + this.rightEarLength +
            ", interpupilaryDistance=" + this.interpupilaryDistance +
            '}';
    }
}
