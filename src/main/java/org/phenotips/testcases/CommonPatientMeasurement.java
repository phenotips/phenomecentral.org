package org.phenotips.testcases;

import java.util.Objects;

/**
 * This class acts as a public struct for patient measurements, too many fields to just pass as a List of Strings.
 * All the possible numerical fields for a patient's measurements should go here.
 */
public class CommonPatientMeasurement
{
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

    @Override public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CommonPatientMeasurement that = (CommonPatientMeasurement) o;
        return Float.compare(that.weight, weight) == 0 &&
            Float.compare(that.armSpan, armSpan) == 0 &&
            Float.compare(that.headCircumference, headCircumference) == 0 &&
            Float.compare(that.outerCanthalDistance, outerCanthalDistance) == 0 &&
            Float.compare(that.leftHandLength, leftHandLength) == 0 &&
            Float.compare(that.rightHandLength, rightHandLength) == 0 &&
            Float.compare(that.height, height) == 0 &&
            Float.compare(that.sittingHeight, sittingHeight) == 0 &&
            Float.compare(that.philtrumLength, philtrumLength) == 0 &&
            Float.compare(that.inntercanthalDistance, inntercanthalDistance) == 0 &&
            Float.compare(that.leftPalmLength, leftPalmLength) == 0 &&
            Float.compare(that.rightPalmLength, rightPalmLength) == 0 &&
            Float.compare(that.leftEarLength, leftEarLength) == 0 &&
            Float.compare(that.palpebralFissureLength, palpebralFissureLength) == 0 &&
            Float.compare(that.leftFootLength, leftFootLength) == 0 &&
            Float.compare(that.rightFootLength, rightFootLength) == 0 &&
            Float.compare(that.rightEarLength, rightEarLength) == 0 &&
            Float.compare(that.interpupilaryDistance, interpupilaryDistance) == 0;
    }

    @Override public int hashCode()
    {
        return Objects
            .hash(weight, armSpan, headCircumference, outerCanthalDistance, leftHandLength, rightHandLength, height,
                sittingHeight, philtrumLength, inntercanthalDistance, leftPalmLength, rightPalmLength, leftEarLength,
                palpebralFissureLength, leftFootLength, rightFootLength, rightEarLength, interpupilaryDistance);
    }

    @Override public String toString()
    {
        return "CommonPatientMeasurement{" +
            "weight=" + weight +
            ", armSpan=" + armSpan +
            ", headCircumference=" + headCircumference +
            ", outerCanthalDistance=" + outerCanthalDistance +
            ", leftHandLength=" + leftHandLength +
            ", rightHandLength=" + rightHandLength +
            ", height=" + height +
            ", sittingHeight=" + sittingHeight +
            ", philtrumLength=" + philtrumLength +
            ", inntercanthalDistance=" + inntercanthalDistance +
            ", leftPalmLength=" + leftPalmLength +
            ", rightPalmLength=" + rightPalmLength +
            ", leftEarLength=" + leftEarLength +
            ", palpebralFissureLength=" + palpebralFissureLength +
            ", leftFootLength=" + leftFootLength +
            ", rightFootLength=" + rightFootLength +
            ", rightEarLength=" + rightEarLength +
            ", interpupilaryDistance=" + interpupilaryDistance +
            '}';
    }

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
}
