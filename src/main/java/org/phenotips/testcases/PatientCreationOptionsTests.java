package org.phenotips.testcases;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import org.phenotips.pageobjects.CreatePatientPage;
import org.phenotips.pageobjects.HomePage;

/**
 * This class of tests will eventually cycle through the possible options when creating a patient via
 * manual input.
 * If a change causes a section or some options to disappear, it should fail due to missing selectors.
 */
public class PatientCreationOptionsTests extends BaseTest implements CommonInfoEnums
{
    private HomePage aHomePage = new HomePage(theDriver);

    private CreatePatientPage aCreationPage = new CreatePatientPage(theDriver);

    // Cycle through all the options on the "Patient Information" Section.
    @Test()
    public void cycleThroughInfoOptions() {
        final List<String> checkOnsetLabels = new ArrayList<String>(Arrays.asList(
            "Unknown", "Congenital onset", "Antenatal onset", "Embryonal onset", "Fetal onset", "Neonatal onset",
            "Infantile onset", "Childhood onset", "Juvenile onset", "Adult onset", "Young adult onset",
            "Middle age onset", "Late onset"));

        final List<String> checkInheritanceLabels = new ArrayList<String>(Arrays.asList(
            "Sporadic", "Autosomal dominant inheritance", "Sex-limited autosomal dominant",
            "Male-limited autosomal dominant", "Autosomal dominant somatic cell mutation",
            "Autosomal dominant contiguous gene syndrome", "Autosomal recessive inheritance",
            "Sex-limited autosomal recessive inheritance",
            "Gonosomal inheritance", "X-linked inheritance", "X-linked dominant inheritance",
            "X-linked recessive inheritance", "Y-linked inheritance", "Multifactorial inheritance",
            "Digenic inheritance", "Oligogenic inheritance", "Polygenic inheritance",
            "Mitochondrial inheritance"));

        aHomePage.navigateToLoginPage()
            .loginAsUser()
            .navigateToCreateANewPatientPage()
            .toggleFirstFourConsentBoxes()
            .toggleNthConsentBox(5)
            .toggleNthConsentBox(5)
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
            .closeEditor("save")
            .saveAndViewSummary()
            .logOut();

    }

    @Test()
    public void cycleThroughFamilialConditions()
    {
        final List<String> checkFamilialConditionsLabels = new ArrayList<String>(Arrays.asList(
            "Other affected relatives", "Consanguinity", "Parents with at least 3 miscarriages"));

        aHomePage.navigateToLoginPage()
            .loginAsUser()
            .navigateToAllPatientsPage()
            .sortPatientsDateDesc()
            .viewFirstPatientInTable()
            .editThisPatient()
            .expandSection(SECTIONS.FamilyHistorySection)
            .setEthnicity("Paternal", "Han Chinese")
            .setEthnicity("Maternal", "European Americans")
            .setHealthConditionsFoundInFamily("There are some conditions here: \n Bla bla bla");

        List<String> loFamilialConditions = aCreationPage.cycleThroughFamilialHealthConditions();
        Assert.assertEquals(loFamilialConditions, checkFamilialConditionsLabels);

        aCreationPage.logOut().dismissUnsavedChangesWarning();
    }

    @Test()
    public void cycleThroughPrenatalHistory()
    {
        final List<String> checkPrenatalConditionsLabels = new ArrayList<String>(Arrays.asList(
            "Multiple gestation", "Conception after fertility medication", "Intrauterine insemination (IUI)",
            "In vitro fertilization", "Intra-cytoplasmic sperm injection", "Gestational surrogacy",
            "Donor egg", "Donor sperm", "Hyperemesis gravidarum (excessive vomiting)",
            "Maternal hypertension", "Maternal diabetes", "Maternal fever in pregnancy",
            "Intrapartum fever", "Maternal first trimester fever", "Maternal seizures",
            "Maternal teratogenic exposure", "Toxemia of pregnancy", "Eclampsia",
            "Maternal hypertension", "Preeclampsia", "Abnormal maternal serum screening",
            "High maternal serum alpha-fetoprotein", "High maternal serum chorionic gonadotropin",
            "Low maternal serum PAPP-A", "Low maternal serum alpha-fetoprotein", "Low maternal serum chorionic gonadotropin",
            "Low maternal serum estriol", "Intrauterine growth retardation", "Mild intrauterine growth retardation",
            "Moderate intrauterine growth retardation", "Severe intrauterine growth retardation",
            "Oligohydramnios", "Polyhydramnios", "Decreased fetal movement",
            "Fetal akinesia sequence", "Increased fetal movement", "Abnormal delivery (Non-NSVD)",
            "Vaginal birth after Caesarian", "Induced vaginal delivery", "Breech presentation",
            "Complete breech presentation", "Frank breech presentation", "Incomplete breech presentation",
            "Caesarian section", "Primary Caesarian section", "Secondary Caesarian section",
            "Forceps delivery", "Ventouse delivery", "Delivery by Odon device",
            "Spontaneous abortion", "Recurrent spontaneous abortion", "Premature birth",
            "Premature birth following premature rupture of fetal membranes",
            "Premature delivery because of cervical insufficiency or membrane fragility",
            "Small for gestational age (<-2SD)", "Large for gestational age (>+2SD)", "Small birth length (<-2SD)",
            "Large birth length (>+2SD)", "Congenital microcephaly (<-3SD)", "Congenital macrocephaly (>+2SD)",
            "Neonatal respiratory distress", "Neonatal asphyxia", "Neonatal inspiratory stridor",
            "Prolonged neonatal jaundice", "Poor suck", "Neonatal hypoglycemia", "Neonatal sepsis"));

        aHomePage.navigateToLoginPage()
            .loginAsUser()
            .navigateToAllPatientsPage()
            .sortPatientsDateDesc()
            .viewFirstPatientInTable()
            .editThisPatient()
            .expandSection(SECTIONS.PrenatalHistorySection);

        List<String> loPrenatalYesNoBoxes = aCreationPage.cycleThroughPrenatalHistory();
        Assert.assertEquals(loPrenatalYesNoBoxes, checkPrenatalConditionsLabels);

        aCreationPage.cycleThroughPrenatalOptions().logOut().dismissUnsavedChangesWarning();
    }

    @Test()
    public void cycleThroughPhenotypeDetails()
    {
        final List<String> checkPhenotypeDetailsLabels = new ArrayList<String>(Arrays.asList(
            "","","Age of onset:", "Unknown", "Congenital onset", "Antenatal onset",
            "Embryonal onset", "Fetal onset", "Neonatal onset", "Infantile onset", "Childhood onset",
            "Juvenile onset", "Adult onset", "Young adult onset", "Middle age onset", "Late onset",
            "Pace of progression:", "Unknown", "Nonprogressive", "Slow progression", "Progressive",
            "Rapidly progressive", "Variable progression rate", "Severity:", "Unknown", "Borderline",
            "Mild", "Moderate", "Severe", "Profound", "Temporal pattern:", "Unknown", "Insidious onset",
            "Chronic", "Subacute", "Acute", "Spatial pattern:", "Unknown", "Generalized", "Localized", "Distal",
            "Proximal", "Laterality:", "Unknown", "Bilateral", "Unilateral", "Right", "Left", "Comments:",
            "Image / photo (optional):", "+", "Medical report (optional):", "+"));

        aHomePage.navigateToLoginPage()
            .loginAsUser()
            .navigateToAllPatientsPage()
            .sortPatientsDateDesc()
            .viewFirstPatientInTable()
            .editThisPatient()
            .expandSection(SECTIONS.ClinicalSymptomsSection)
            .addPhenotype("Small earlobe")
            .addDetailsToNthPhenotype(1);

        List<String> loPhenotypeDetailsOptions = aCreationPage.cycleThroughPhenotypeDetailsLabels();
        System.out.println(loPhenotypeDetailsOptions);
        Assert.assertEquals(loPhenotypeDetailsOptions, checkPhenotypeDetailsLabels);

        aCreationPage.logOut().dismissUnsavedChangesWarning();
    }

    // Checks only first level depth on HPO tree. Structure is rather inconsistent, can't figure out
    //   a simple recursive function for it.
    // TODO: Maybe move that long string out of here, no need for assertion. Everything slow.
    @Test()
    public void cycleThroughAllPhenotypes()
    {
        final List<String> checkPhenotypeLabels = new ArrayList<>(Arrays.asList(
            "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "Decreased body mass index", "Eunuchoid habitus", "Failure to thrive", "Slender build", "Small for gestational age", "Weight loss", "Large for gestational age", "Obesity", "Overweight", "Asymmetric short stature", "Birth length less than 3rd percentile", "Disproportionate short stature", "Pituitary dwarfism", "Proportionate short stature", "Birth length greater than 97th percentile", "Disproportionate tall stature", "Overgrowth", "Proportionate tall stature", "Slender build", "Congenital microcephaly", "Postnatal microcephaly", "Progressive microcephaly", "Macrocephaly at birth", "Postnatal macrocephaly", "Progressive macrocephaly", "Relative macrocephaly", "Failure to thrive in infancy", "Severe failure to thrive", "Hemihypertrophy of lower limb", "Hemihypertrophy of upper limb", "Coronal craniosynostosis", "Lambdoidal craniosynostosis", "Multiple suture craniosynostosis", "Orbital craniosynostosis", "Sagittal craniosynostosis", "Incomplete cleft of the upper lip", "Median cleft lip", "Non-midline cleft lip", "Submucous cleft lip", "Cleft primary palate", "Cleft secondary palate", "Median cleft palate", "Non-midline cleft palate", "Submucous cleft of soft and hard palate", "Abnormality of the shape of the midface", "Bird-like facies", "Coarse facial features", "Craniofacial disproportion", "Doll-like facies", "Elfin facies", "Facial asymmetry", "Facial shape deformation", "Flat face", "Large face", "Moon facies", "Oval face", "Round face", "Small face", "Square face", "Triangular face", "Blindness", "Congenital visual impairment", "Cortical visual impairment", "Moderate visual impairment", "Severe visual impairment", "Abnormal corneal endothelium morphology", "Abnormal corneal epithelium morphology", "Abnormality of corneal shape", "Abnormality of corneal size", "Abnormality of corneal stroma", "Abnormality of corneal thickness", "Abnormality of the corneal limbus", "Abnormality of the curvature of the cornea", "Abnormality of the line of Schwalbe", "Cornea verticillata", "Corneal degeneration", "Corneal dystrophy", "Corneal neovascularization", "Corneal opacity", "Corneal perforation", "Decreased corneal reflex", "Decreased corneal sensation", "Epibulbar dermoid", "Chorioretinal coloboma", "Ciliary body coloboma", "Iris coloboma", "Lens coloboma", "Optic nerve coloboma", "Retinal coloboma", "Abnormal trabecular meshwork morphology", "Absent anterior chamber of the eye", "Anterior chamber cells", "Anterior chamber cyst", "Anterior chamber flare", "Anterior chamber inflammatory cells", "Anterior chamber red blood cells", "Anterior chamber synechiae", "Corneolenticular adhesion", "Deep anterior chamber", "Hypopyon", "Shallow anterior chamber", "Age-related cataract", "Capsular cataract", "Christmas tree cataract", "Congenital cataract", "Juvenile cataract", "Membranous cataract", "Polar cataract", "Presenile cataracts", "Progressive cataract", "Subcapsular cataract", "Total cataract", "Zonular cataract", "Abnormal chorioretinal morphology", "Abnormal macular morphology", "Abnormality of retinal pigmentation", "Abnormality of the retinal vasculature", "Angioid streaks of the fundus", "Aplasia/Hypoplasia of the retina", "Hypermyelinated retinal nerve fibers", "Intraretinal fluid", "Retinal coloboma", "Retinal degeneration", "Retinal detachment", "Retinal dysplasia", "Retinal dystrophy", "Retinal fold", "Retinal hamartoma", "Retinal hemorrhage", "Retinal infarction", "Retinal neoplasm", "Retinal perforation", "Retinal thinning", "Retinopathy", "Retinoschisis", "Sub-RPE deposits", "Subretinal deposits", "Subretinal fluid", "Yellow/white lesions of the retina", "Abnormality of optic chiasm morphology", "Abnormality of the optic disc", "Aplasia/Hypoplasia of the optic nerve", "Leber optic atrophy", "Marcus Gunn pupil", "Morning glory anomaly", "Optic nerve arteriovenous malformation", "Optic nerve coloboma", "Optic nerve compression", "Optic nerve dysplasia", "Optic nerve misrouting", "Optic neuritis", "Optic neuropathy", "Bilateral microphthalmos", "Unilateral microphthalmos", "Congenital nystagmus", "Divergence nystagmus", "Gaze-evoked nystagmus", "Horizontal nystagmus", "Nystagmus-induced head nodding", "Pendular nystagmus", "Rotary nystagmus", "Vertical nystagmus", "Vestibular nystagmus", "Concomitant strabismus", "Congenital strabismus", "Cyclodeviation", "Esodeviation", "Exodeviation", "Heterophoria", "Heterotropia", "Hyperdeviation", "Hypodeviation", "Incomitant strabismus", "Monocular strabismus", "Neurogenic strabismus", "Adult onset sensorineural hearing impairment", "Bilateral sensorineural hearing impairment", "Childhood onset sensorineural hearing impairment", "Congenital sensorineural hearing impairment", "High-frequency sensorineural hearing impairment", "Low-frequency sensorineural hearing impairment", "Mild neurosensory hearing impairment", "Mixed hearing impairment", "Moderate sensorineural hearing impairment", "Old-aged sensorineural hearing impairment", "Profound sensorineural hearing impairment", "Progressive sensorineural hearing impairment", "Severe sensorineural hearing impairment", "Bilateral conductive hearing impairment", "Congenital conductive hearing impairment", "Mild conductive hearing impairment", "Mixed hearing impairment", "Moderate conductive hearing impairment", "Progressive conductive hearing impairment", "Severe conductive hearing impairment", "Unilateral conductive hearing impairment", "Abnormal location of ears", "Abnormality of cartilage of external ear", "Abnormality of the auditory canal", "Abnormality of the pinna", "Abnormality of the tympanic membrane", "Aplasia/Hypoplasia of the external ear", "Bilateral external ear deformity", "External ear malformation", "Extra concha fold", "Hypertrophic auricular cartilage", "Neoplasm of the outer ear", "Polyotia", "Telangiectasia of the ear", "Unilateral external ear deformity", "Abnormality of the round window", "Abnormality of the vestibular window", "Functional abnormality of the inner ear", "Morphological abnormality of the inner ear", "Neoplasm of the inner ear", "Generalized hyperpigmentation", "Hyperpigmentation in sun-exposed areas", "Irregular hyperpigmentation", "Melasma", "Mixed hypo- and hyperpigmentation of the skin", "Progressive hyperpigmentation", "Absent skin pigmentation", "Confetti hypopigmentation pattern of lower leg skin", "Generalized hypopigmentation", "Hypomelanotic macule", "Hypopigmented skin patches", "Hypopigmented streaks", "Mixed hypo- and hyperpigmentation of the skin", "Partial albinism", "Piebaldism", "Facial capillary hemangioma", "Periocular capillary hemangioma", "Pulmonary capillary hemangiomatosis", "Angioedema", "Angiokeratoma", "Cutis marmorata", "Erythema", "Non-pruritic urticaria", "Prominent superficial blood vessels", "Subcutaneous hemorrhage", "Telangiectasia", "Urticaria", "Vasculitis in the skin", "Patent foramen ovale", "Primum atrial septal defect", "Secundum atrial septal defect", "Sinus venosus atrial septal defect", "Swiss cheese atrial septal defect", "Unroofed coronary sinus", "Gerbode ventricular septal defect", "Inlet ventricular septal defect", "Muscular ventricular septal defect", "Non-restrictive ventricular septal defect", "Perimembranous ventricular septal defect", "Restrictive ventricular septal defect", "Subarterial ventricular septal defect", "Coarctation in the transverse aortic arch", "Coarctation of abdominal aorta", "Coarctation of the descending aortic arch", "Long segment coarctation of the aorta", "Tetralogy of Fallot with absent pulmonary valve", "Tetralogy of Fallot with absent subarterial conus", "Tetralogy of Fallot with atrioventricular canal defect", "Tetralogy of Fallot with pulmonary atresia", "Tetralogy of Fallot with pulmonary stenosis", "Atrial cardiomyopathy", "Dilated cardiomyopathy", "Histiocytoid cardiomyopathy", "Hypertrophic cardiomyopathy", "Noncompaction cardiomyopathy", "Restrictive cardiomyopathy", "Right ventricular cardiomyopathy", "Takotsubo cardiomyopathy", "Abnormal electrophysiology of sinoatrial node origin", "Abnormal heart rate variability", "Bradycardia", "Cardiac arrest", "Palpitations", "Supraventricular arrhythmia", "Tachycardia", "Ventricular arrhythmia", "Central diaphragmatic hernia", "Morgagni diaphragmatic hernia", "Posterolateral diaphragmatic hernia", "Abnormal chest radiograph finding (lung)", "Abnormal lung lobation", "Abnormal pulmonary lymphatics", "Abnormal subpleural morphology", "Abnormality of the pleura", "Abnormality of the pulmonary vasculature", "Alveolar proteinosis", "Aplasia/Hypoplasia of the lungs", "Atelectasis", "Bronchogenic cyst", "Bronchopulmonary sequestration", "Chronic lung disease", "Cystic lung disease", "Emphysema", "Hemoptysis", "Hypersensitivity pneumonitis", "Interstitial pulmonary abnormality", "Intraalveolar nodular calcifications", "Lung abscess", "Neoplasm of the lung", "Pneumothorax", "Pulmonary edema", "Pulmonary fibrosis", "Pulmonary granulomatosis", "Pulmonary hemorrhage", "Pulmonary infiltrates", "Pulmonary opacity", "Pulmonary pneumatocele", "Respiratory tract infection", "Unilateral primary pulmonary dysgenesis", "Diaphyseal dysplasia", "Epiphyseal dysplasia", "Lethal skeletal dysplasia", "Metaphyseal dysplasia", "Multiple epiphyseal dysplasia", "Multiple skeletal anomalies", "Spondyloepimetaphyseal dysplasia", "Spondyloepiphyseal dysplasia", "Spondylometaphyseal dysplasia", "Bowing of limbs due to multiple fractures", "Multiple prenatal fractures", "Painless fractures due to injury", "Pathologic fracture", "Recurrent fractures", "Short lower limbs", "Forearm undergrowth", "Bilateral camptodactyly", "Camptodactyly of 2nd-5th fingers", "Congenital finger flexion contractures", "Contracture of the proximal interphalangeal joint of the 2nd finger", "Contracture of the proximal interphalangeal joint of the 3rd finger", "Contracture of the proximal interphalangeal joint of the 4th finger", "Contracture of the proximal interphalangeal joint of the 5th finger", "Contracture of the proximal interphalangeal joint of the 2nd toe", "Contracture of the proximal interphalangeal joint of the 3rd toe", "Contracture of the proximal interphalangeal joint of the 4th toe", "Contractures of the proximal interphalangeal joint of the 5th toe", "1-2 finger syndactyly", "1-3 finger syndactyly", "1-4 finger syndactyly", "1-5 finger syndactyly", "2-3 finger syndactyly", "2-4 finger syndactyly", "2-5 finger syndactyly", "3-4 finger syndactyly", "3-5 finger syndactyly", "4-5 finger syndactyly", "Cutaneous finger syndactyly", "Osseous finger syndactyly", "1-2 toe syndactyly", "1-3 toe syndactyly", "1-4 toe syndactyly", "1-5 toe syndactyly", "2-3 toe syndactyly", "2-4 toe syndactyly", "2-5 toe syndactyly", "3-4 toe syndactyly", "3-5 toe syndactyly", "4-5 toe syndactyly", "Cutaneous syndactyly of toes", "Osseous syndactyly of toes", "Preaxial foot polydactyly", "Preaxial hand polydactyly", "Postaxial foot polydactyly", "Postaxial hand polydactyly", "Hand monodactyly", "Postaxial oligodactyly", "Unilateral oligodactyly", "Foot monodactyly", "Compensatory scoliosis", "Kyphoscoliosis", "Progressive congenital scoliosis", "Thoracic scoliosis", "Thoracolumbar scoliosis", "Abnormal sacrum morphology", "Abnormal vertebral morphology", "Abnormality of the cervical spine", "Abnormality of the coccyx", "Abnormality of the curvature of the vertebral column", "Abnormality of the intervertebral disk", "Abnormality of the lumbar spine", "Abnormality of the odontoid process", "Abnormality of the thoracic spine", "Aplasia/Hypoplasia involving the vertebral column", "Atlantoaxial abnormality", "Back pain", "Reversed usual vertebral column curves", "Spinal canal stenosis", "Spinal deformities", "Spinal dysplasia", "Spinal instability", "Spinal rigidity", "Spondylolisthesis", "Spondylolysis", "Congenital contracture", "Contractures of the large joints", "Decreased cervical spine flexion due to contractures of posterior cervical muscles", "Flexion contracture of digit", "Joint contractures involving the joints of the feet", "Limb joint contracture", "Multiple joint contractures", "Progressive flexion contractures", "Restricted neck movement due to contractures", "Bilateral talipes equinovarus", "Talipes cavus equinovarus", "Proximal esophageal atresia", "Long-segment aganglionic megacolon", "Short-segment aganglionic megacolon", "Total colonic aganglionosis", "Acholic stools", "Cholestatic liver disease", "Extrahepatic cholestasis", "Intrahepatic cholestasis", "Jaundice", "Abnormal liver function tests during pregnancy", "Elevated serum alanine aminotransferase", "Elevated serum aspartate aminotransferase", "Elevated serum transaminases during infections", "Diabetic ketoacidosis", "Insulin-resistant diabetes mellitus", "Maternal diabetes", "Maturity-onset diabetes of the young", "Type I diabetes mellitus", "Type II diabetes mellitus", "Cystic renal dysplasia", "Multicystic kidney dysplasia", "Multiple renal cysts", "Multiple small medullary renal cysts", "Polycystic kidney dysplasia", "Renal cortical cysts", "Renal corticomedullary cysts", "Renal diverticulum", "Solitary renal cyst", "Congenital megaureter", "Hydroureter", "Neoplasm of the ureter", "Ureteral agenesis", "Ureteral atresia", "Ureteral duplication", "Ureteral dysgenesis", "Ureteral obstruction", "Ureterocele", "Vesicoureteral reflux", "Congenital megalourethra", "Displacement of the external urethral meatus", "Distal urethral duplication", "Neoplasm of the urethra", "Patulous urethra", "Urethral atresia", "Urethral diverticulum", "Urethral fistula", "Urethral obstruction", "Urethritis", "Urethrocele", "Urogenital sinus anomaly", "Ambiguous genitalia", "female", "Ambiguous genitalia", "male", "Gonadal tissue inappropriate for external genitalia or chromosomal sex", "Ovotestis", "True hermaphroditism", "Coronal hypospadias", "Glandular hypospadias", "Midshaft hypospadias", "Penile hypospadias", "Penoscrotal hypospadias", "Perineal hypospadias", "Scrotal hypospadias", "Bilateral cryptorchidism", "Unilateral cryptorchidism", "Mild global developmental delay", "Moderate global developmental delay", "Profound global developmental delay", "Severe global developmental delay", "Delayed ability to sit", "Delayed ability to stand", "Delayed ability to walk", "Absent speech", "Expressive language delay", "Receptive language delay", "Dyscalculia", "Dyslexia", "Impaired visuospatial constructive cognition", "Abnormal consumption behavior", "Abnormal emotion/affect behavior", "Abnormal social behavior", "Abnormal temper tantrums", "Addictive behavior", "Autistic behavior", "Delusions", "Diminished ability to concentrate", "Drooling", "Echolalia", "Hallucinations", "Hyperorality", "Impairment in personality functioning", "Inflexible adherence to routines or rituals", "Lack of insight", "Lack of spontaneous play", "Low frustration tolerance", "Mania", "Mutism", "Obsessive-compulsive behavior", "Oppositional defiant disorder", "Perseveration", "Personality changes", "Photophobia", "Pschomotor retardation", "Pseudobulbar behavioral symptoms", "Psychosis", "Restlessness", "Schizophrenia", "Self-neglect", "Short attention span", "Sleep disturbance", "Sound sensitivity", "Episodic generalized hypotonia", "Generalized hypotonia due to defect at the neuromuscular junction", "Dialeptic seizures", "Epileptic spasms", "Febrile seizures", "Focal seizures", "Generalized seizures", "Multifocal seizures", "Nocturnal seizures", "Status epilepticus", "Symptomatic seizures", "Cerebellar ataxia associated with quadrupedal gait", "Dysdiadochokinesis", "Dysmetria", "Dyssynergia", "Episodic ataxia", "Gait ataxia", "Limb ataxia", "Nonprogressive cerebellar ataxia", "Progressive cerebellar ataxia", "Spastic ataxia", "Truncal ataxia", "Axial dystonia", "Focal dystonia", "Generalized dystonia", "Hemidystonia", "Limb dystonia", "Oculogyric crisis", "Paroxysmal dystonia", "Torsion dystonia", "Choreoathetosis", "Clasp-knife sign", "Lower limb spasticity", "Opisthotonus", "Progressive spasticity", "Spastic diplegia", "Spastic dysarthria", "Spastic gait", "Spastic hemiparesis", "Spastic tetraparesis", "Spastic tetraplegia", "Spasticity of facial muscles", "Spasticity of pharyngeal muscles", "Upper limb spasticity", "Spina bifida", "Abnormal CNS myelination", "Abnormal neural tube morphology", "Abnormality of brain morphology", "Abnormality of neuronal migration", "Abnormality of the cerebrospinal fluid", "Abnormality of the glial cells", "Abnormality of the meninges", "Abnormality of the spinal cord", "Abnormality of the subarachnoid space", "Alzheimer disease", "Aplasia/Hypoplasia involving the central nervous system", "Atrophy/Degeneration affecting the central nervous system", "CNS infection", "Central nervous system cyst", "Encephalocele", "Morphological abnormality of the pyramidal tract", "Neoplasm of the central nervous system"
        ));
        aHomePage.navigateToLoginPage()
            .loginAsUser()
            .navigateToAllPatientsPage()
            .sortPatientsDateDesc()
            .viewFirstPatientInTable()
            .editThisPatient()
            .expandSection(SECTIONS.ClinicalSymptomsSection);

        List<String> loAllPhenotypes = aCreationPage.cycleThroughAllPhenotypes();
        System.out.println(loAllPhenotypes);
        Assert.assertEquals(loAllPhenotypes, checkPhenotypeLabels);

        aCreationPage.logOut().dismissUnsavedChangesWarning();
    }

    // Clicks on all input boxes within the Diagnosis section and tries to provide input.
    // Asserts that the PubMedIDs and Resolution Notes are hidden upon toggling "Case Solved"
    @Test()
    public void cycleThroughDiagnosis()
    {
        aHomePage.navigateToLoginPage()
            .loginAsUser()
            .navigateToAllPatientsPage()
            .sortPatientsDateDesc()
            .viewFirstPatientInTable()
            .editThisPatient()
            .expandSection(SECTIONS.DiagnosisSection);

        System.out.println("Case Solved should be False: " + aCreationPage.isCaseSolved());
        Assert.assertFalse(aCreationPage.isCaseSolved());
        Assert.assertFalse(aCreationPage.isPubMedAndResolutionBoxesClickable());

        aCreationPage.cycleThroughDiagnosisBoxes();
        System.out.println("Case Solved should be True: " + aCreationPage.isCaseSolved());
        Assert.assertTrue(aCreationPage.isCaseSolved());
        Assert.assertTrue(aCreationPage.isPubMedAndResolutionBoxesClickable());

        aCreationPage.toggleCaseSolved();
        System.out.println("Case Solved should be False: " + aCreationPage.isCaseSolved());
        Assert.assertFalse(aCreationPage.isCaseSolved());
        Assert.assertFalse(aCreationPage.isPubMedAndResolutionBoxesClickable());

        aCreationPage.logOut().dismissUnsavedChangesWarning();
    }

    // Checks that the red error message when inputting an invalid PubMed ID shows up.
    @Test()
    public void checkDiagnosisErrorMessages()
    {
        aHomePage.navigateToLoginPage()
            .loginAsUser()
            .navigateToAllPatientsPage()
            .sortPatientsDateDesc()
            .viewFirstPatientInTable()
            .editThisPatient()
            .expandSection(SECTIONS.DiagnosisSection);

        aCreationPage.toggleCaseSolved()
            .addPubMedID("This is an invalid ID");

        Assert.assertFalse(aCreationPage.isNthPubMDBoxValid(1));

        aCreationPage.removeNthPubMedID(1)
            .addPubMedID("30699054");

        Assert.assertTrue(aCreationPage.isNthPubMDBoxValid(1));

        aCreationPage.logOut().dismissUnsavedChangesWarning();
    }


}
