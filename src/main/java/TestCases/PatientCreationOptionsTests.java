package TestCases;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import PageObjects.CreatePatientPage;
import PageObjects.HomePage;

/**
 * This class of tests will eventually cycle through the possible options when creating a patient via
 * manual input.
 * If a change causes a section or some options to disappear, it should fail due to missing selectors.
 */
public class PatientCreationOptionsTests extends BaseTest implements CommonInfoEnums
{
    private HomePage aHomePage = new HomePage(theDriver);

    private CreatePatientPage aCreationPage = new CreatePatientPage(theDriver);

    private final List<String> checkOnsetLabels = new ArrayList<String>(Arrays.asList(
        "Unknown", "Congenital onset", "Antenatal onset", "Embryonal onset", "Fetal onset", "Neonatal onset",
        "Infantile onset", "Childhood onset", "Juvenile onset", "Adult onset", "Young adult onset",
        "Middle age onset", "Late onset"));

    private final List<String> checkInheritanceLabels = new ArrayList<String>(Arrays.asList(
        "Sporadic", "Autosomal dominant inheritance", "Sex-limited autosomal dominant",
        "Male-limited autosomal dominant", "Autosomal dominant somatic cell mutation",
        "Autosomal dominant contiguous gene syndrome", "Autosomal recessive inheritance",
        "Gonosomal inheritance", "X-linked inheritance", "X-linked dominant inheritance",
        "X-linked recessive inheritance", "Y-linked inheritance", "Multifactorial inheritance",
        "Digenic inheritance", "Oligogenic inheritance", "Polygenic inheritance",
        "Mitochondrial inheritance"));

    // Cycle through all the options on the "Patient Information" Section.
    @Test
    public void cycleThroughInfoOptions() {
        aHomePage.navigateToLoginPage()
            .loginAsUser()
            .navigateToCreateANewPatientPage()
            .toggleNthConsentBox(1)
            .toggleNthConsentBox(2)
            .toggleNthConsentBox(3)
            .toggleNthConsentBox(4)
            .updateConsent()
            .setIdentifer("Auto Cycling Options")
            .setLifeStatus("Alive")
            .setLifeStatus("Deceased");

        for (int i = 1; i <= 12; ++i) {
            if (i < 10) {
                aCreationPage.setDOB("0" + String.valueOf(i), "2019");
                aCreationPage.setDateOfDeath("0" + String.valueOf(i), "2019");
            }
            else {
                aCreationPage.setDOB(String.valueOf(i), "2019");
                aCreationPage.setDateOfDeath(String.valueOf(i), "2019");
            }
        }

        aCreationPage.setLifeStatus("Alive")
            .setGender("Male")
            .setGender("Female")
            .setGender("Other")
            .setGender("Unknown")
            .setGender("Male");

        List<String> loAgeOnsetLabels = aCreationPage.cycleThroughAgeOfOnset();
        List<String> loModeOfInheritanceLabels = aCreationPage.cycleThroughModeOfInheritance();

        Assert.assertEquals(loAgeOnsetLabels, checkOnsetLabels);
        Assert.assertEquals(loModeOfInheritanceLabels, checkInheritanceLabels);

        aCreationPage.cycleThroughModeOfInheritance();
        aCreationPage.setIndicationForReferral("Now cycle through the other sections...")
            .expandSection(SECTIONS.FamilyHistorySection);

        aCreationPage.navigateToPedigreeEditor("")
            .closeEditor("save");

    }
}
