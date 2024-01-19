package edu.colorado.cires.wod.ascii;

import edu.colorado.cires.wod.ascii.WodFileReader.CharReader;
import edu.colorado.cires.wod.ascii.model.Cast;
import edu.colorado.cires.wod.ascii.reader.BufferedCharReader;
import edu.colorado.cires.wod.ascii.reader.CastFileReader;
import edu.colorado.cires.wod.ascii.reader.RandomAccessFileCharReader;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;
import org.junit.jupiter.api.Test;

import java.io.EOFException;
import java.io.RandomAccessFile;

public class WodFileReaderTest {

  @Test
  public void testPfl() throws Exception {
    try (RandomAccessFile file = new RandomAccessFile("src/test/resources/pfl.dat", "r")) {
      CharReader reader = new RandomAccessFileCharReader(file);
      while (true) {
        PrimaryHeader primaryHeader = WodFileReader.readHeader(reader);
        CharacterDataHeader characterDataHeader = WodFileReader.readCharacterDataHeader(reader);
        SecondaryHeader secondaryHeader = WodFileReader.readSecondaryHeader(reader);
        BiologicalHeader biologicalHeader = WodFileReader.readBiologicalHeader(reader);
        TaxonomicDatasets taxonomicDatasets = null;
        if (biologicalHeader != null) {
          taxonomicDatasets = WodFileReader.readTaxonomicDatasets(reader);
        }
        ProfileData profileData = WodFileReader.readProfileData(reader, primaryHeader.getNumberOfLevels(), primaryHeader.getVariables());
      }
    } catch (EOFException e) {
      //ignore
    }
  }

  @Test
  public void testApb() throws Exception {
    try (Stream<Path> files = Files.list(Paths.get("src/test/resources/APB/OBS"))) {
      files
          .filter(file -> file.getFileName().toString().endsWith(".gz"))
          .sorted(Comparator.comparing(o -> o.getFileName().toString()))
          .forEach(file -> {
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new GZIPInputStream(new BufferedInputStream(Files.newInputStream(file)))))) {
              System.out.println("Reading " + file);
              CastFileReader reader = new CastFileReader(new BufferedCharReader(bufferedReader), null);
              while (reader.hasNext()){
                Cast cast = reader.next();
              }
            } catch (IOException e) {
              throw new RuntimeException(e);
            }
          });
    }
  }

//  @Test
//  void test() throws Exception {
//    PrimaryHeader primaryHeader;
//    CharacterDataHeader  characterDataHeader;
//    SecondaryHeader secondaryHeader;
//    BiologicalHeader biologicalHeader;
//    TaxonomicDatasets taxonomicDatasets;
//    ProfileData profileData;
//    try(RandomAccessFile file = new RandomAccessFile("src/test/resources/classic.dat", "r")) {
//      primaryHeader = WodFileReader.readHeader(file);
//      characterDataHeader = WodFileReader.readCharacterDataHeader(file);
//      secondaryHeader = WodFileReader.readSecondaryHeader(file);
//      biologicalHeader = WodFileReader.readBiologicalHeader(file);
//      taxonomicDatasets = WodFileReader.readTaxonomicDatasets(file);
//      profileData = WodFileReader.readProfileData(file, primaryHeader.getNumberOfLevels(), primaryHeader.getNumberOfVariables());
//    }
//    PrimaryHeader expectedHeader = new PrimaryHeader();
//    expectedHeader.setWodVersion(WodVersion.WOD18);
//    expectedHeader.setProfile(1303);
//    expectedHeader.setCastNumber(67064);
//    expectedHeader.setCountryCode("US");
//    expectedHeader.setCruiseNumber(11203);
//    expectedHeader.setYear(1934);
//    expectedHeader.setMonth(8);
//    expectedHeader.setDay(7);
//    expectedHeader.setTime(10.37);
//    expectedHeader.setLatitude(61.930);
//    expectedHeader.setLongitude(-172.270);
//    expectedHeader.setNumberOfLevels(4);
//    expectedHeader.setProfileType(0);
//
//    List<Variable> variables = new ArrayList<>(6);
//    Variable variable = new Variable();
//    variable.setCode(1);
//    variable.setQcFlag(0);
//    variables.add(variable);
//
//    variable = new Variable();
//    variable.setCode(2);
//    variable.setQcFlag(0);
//    variables.add(variable);
//
//    variable = new Variable();
//    variable.setCode(3);
//    variable.setQcFlag(0);
//    variables.add(variable);
//    Metadata metadata = new Metadata();
//    metadata.setCode(8);
//    metadata.setValue(58.0);
//    variable.setMetadata(Collections.singletonList(metadata));
//
//    variable = new Variable();
//    variable.setCode(4);
//    variable.setQcFlag(0);
//    variables.add(variable);
//    metadata = new Metadata();
//    metadata.setCode(8);
//    metadata.setValue(29.0);
//    variable.setMetadata(Collections.singletonList(metadata));
//
//
//    variable = new Variable();
//    variable.setCode(6);
//    variable.setQcFlag(0);
//    variables.add(variable);
//    metadata = new Metadata();
//    metadata.setCode(8);
//    metadata.setValue(29.0);
//    variable.setMetadata(Collections.singletonList(metadata));
//
//    variable = new Variable();
//    variable.setCode(9);
//    variable.setQcFlag(0);
//    variables.add(variable);
//
//    expectedHeader.setVariables(variables);
//
//    assertEquals(expectedHeader, primaryHeader);
//
//    CharacterDataHeader expectedCharacterDataHeader = new CharacterDataHeader();
//    expectedCharacterDataHeader.setDataBytes(47);
//    List<CharacterData> characterData = new ArrayList<>(2);
//    CharacterData cData = new CharacterData();
//    cData.setType(1);
//    cData.setValue("STOCS85A");
//    characterData.add(cData);
//
//    cData = new CharacterData();
//    cData.setType(3);
//    characterData.add(cData);
//    List<PrincipalInvestigator> principalInvestigators = new ArrayList<>(4);
//    PrincipalInvestigator principalInvestigator = new PrincipalInvestigator();
//    principalInvestigator.setVariableCode(0);
//    principalInvestigator.setPiCode(215);
//    principalInvestigators.add(principalInvestigator);
//
//    principalInvestigator = new PrincipalInvestigator();
//    principalInvestigator.setVariableCode(0);
//    principalInvestigator.setPiCode(216);
//    principalInvestigators.add(principalInvestigator);
//
//    principalInvestigator = new PrincipalInvestigator();
//    principalInvestigator.setVariableCode(-5006);
//    principalInvestigator.setPiCode(217);
//    principalInvestigators.add(principalInvestigator);
//
//    principalInvestigator = new PrincipalInvestigator();
//    principalInvestigator.setVariableCode(-5002);
//    principalInvestigator.setPiCode(218);
//    principalInvestigators.add(principalInvestigator);
//
//
//    cData.setPrincipalInvestigators(principalInvestigators);
//
//    expectedCharacterDataHeader.setCharacterData(characterData);
//
//    assertEquals(expectedCharacterDataHeader, characterDataHeader);
//
//    SecondaryHeader expectedSecondaryHeader = new SecondaryHeader();
//    expectedSecondaryHeader.setTotalBytesForSecondaryHeader(73);
//    List<SecondaryHeaderValue> secondaryHeaderValues = new ArrayList<>(8);
//    SecondaryHeaderValue secondaryHeaderValue = new SecondaryHeaderValue();
//    secondaryHeaderValue.setCode(1);
//    secondaryHeaderValue.setValue(9500110D);
//    secondaryHeaderValues.add(secondaryHeaderValue);
//
//    secondaryHeaderValue = new SecondaryHeaderValue();
//    secondaryHeaderValue.setCode(3);
//    secondaryHeaderValue.setValue(1427D);
//    secondaryHeaderValues.add(secondaryHeaderValue);
//
//    secondaryHeaderValue = new SecondaryHeaderValue();
//    secondaryHeaderValue.setCode(4);
//    secondaryHeaderValue.setValue(393D);
//    secondaryHeaderValues.add(secondaryHeaderValue);
//
//    secondaryHeaderValue = new SecondaryHeaderValue();
//    secondaryHeaderValue.setCode(7);
//    secondaryHeaderValue.setValue(76D);
//    secondaryHeaderValues.add(secondaryHeaderValue);
//
//    secondaryHeaderValue = new SecondaryHeaderValue();
//    secondaryHeaderValue.setCode(10);
//    secondaryHeaderValue.setValue(60D);
//    secondaryHeaderValues.add(secondaryHeaderValue);
//
//    secondaryHeaderValue = new SecondaryHeaderValue();
//    secondaryHeaderValue.setCode(29);
//    secondaryHeaderValue.setValue(7D);
//    secondaryHeaderValues.add(secondaryHeaderValue);
//
//    secondaryHeaderValue = new SecondaryHeaderValue();
//    secondaryHeaderValue.setCode(91);
//    secondaryHeaderValue.setValue(3D);
//    secondaryHeaderValues.add(secondaryHeaderValue);
//
//    secondaryHeaderValue = new SecondaryHeaderValue();
//    secondaryHeaderValue.setCode(99);
//    secondaryHeaderValue.setValue(2013302D);
//    secondaryHeaderValues.add(secondaryHeaderValue);
//
//    expectedSecondaryHeader.setValues(secondaryHeaderValues);
//
//    assertEquals(expectedSecondaryHeader, secondaryHeader);
//
//
//    BiologicalHeader expectedBiologicalHeader= new BiologicalHeader();
//    expectedBiologicalHeader.setTotalBytesForBiologicalHeader(846);
//
//    List<BiologicalHeaderValue> biologicalHeaderValues = new ArrayList<>(8);
//    BiologicalHeaderValue biologicalHeaderValue = new BiologicalHeaderValue();
//    biologicalHeaderValue.setCode(2);
//    biologicalHeaderValue.setValue(new BigDecimal("18.00"));
//    biologicalHeaderValues.add(biologicalHeaderValue);
//
//    biologicalHeaderValue = new BiologicalHeaderValue();
//    biologicalHeaderValue.setCode(3);
//    biologicalHeaderValue.setValue(new BigDecimal("76"));
//    biologicalHeaderValues.add(biologicalHeaderValue);
//
//    biologicalHeaderValue = new BiologicalHeaderValue();
//    biologicalHeaderValue.setCode(4);
//    biologicalHeaderValue.setValue(new BigDecimal("2"));
//    biologicalHeaderValues.add(biologicalHeaderValue);
//
//    biologicalHeaderValue = new BiologicalHeaderValue();
//    biologicalHeaderValue.setCode(7);
//    biologicalHeaderValue.setValue(new BigDecimal("103"));
//    biologicalHeaderValues.add(biologicalHeaderValue);
//
//    biologicalHeaderValue = new BiologicalHeaderValue();
//    biologicalHeaderValue.setCode(9);
//    biologicalHeaderValue.setValue(new BigDecimal("0.05"));
//    biologicalHeaderValues.add(biologicalHeaderValue);
//
//    biologicalHeaderValue = new BiologicalHeaderValue();
//    biologicalHeaderValue.setCode(13);
//    biologicalHeaderValue.setValue(new BigDecimal("11"));
//    biologicalHeaderValues.add(biologicalHeaderValue);
//
//    biologicalHeaderValue = new BiologicalHeaderValue();
//    biologicalHeaderValue.setCode(16);
//    biologicalHeaderValue.setValue(new BigDecimal("10.37"));
//    biologicalHeaderValues.add(biologicalHeaderValue);
//
//    biologicalHeaderValue = new BiologicalHeaderValue();
//    biologicalHeaderValue.setCode(30);
//    biologicalHeaderValue.setValue(new BigDecimal("9500110"));
//    biologicalHeaderValues.add(biologicalHeaderValue);
//
//    expectedBiologicalHeader.setValues(biologicalHeaderValues);
//
//    assertEquals(expectedBiologicalHeader, biologicalHeader);
//
//
//    TaxonomicDatasets expectedTaxonomicDatasets = new TaxonomicDatasets();
//    List<TaxonomicDataset> datasets = new ArrayList<>(8);
//    TaxonomicDataset taxonomicDataset = new TaxonomicDataset();
//    List<TaxonomicDatasetValue> taxonomicDatasetValues = new ArrayList<>(8);
//    TaxonomicDatasetValue taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(1);
//    taxonomicDatasetValue.setValue(new BigDecimal("85272"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(2);
//    taxonomicDatasetValue.setValue(new BigDecimal("0"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(3);
//    taxonomicDatasetValue.setValue(new BigDecimal("25.0"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(10);
//    taxonomicDatasetValue.setValue(new BigDecimal("6"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(20);
//    taxonomicDatasetValue.setValue(new BigDecimal("68"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(27);
//    taxonomicDatasetValue.setValue(new BigDecimal("4.8"));
//    taxonomicDatasetValue.setQcFlag(3);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(28);
//    taxonomicDatasetValue.setValue(new BigDecimal("68.40"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(30);
//    taxonomicDatasetValue.setValue(new BigDecimal("4212000"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDataset.setValues(taxonomicDatasetValues);
//    datasets.add(taxonomicDataset);
//
//
//    taxonomicDataset = new TaxonomicDataset();
//    taxonomicDatasetValues = new ArrayList<>(9);
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(1);
//    taxonomicDatasetValue.setValue(new BigDecimal("79118"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(2);
//    taxonomicDatasetValue.setValue(new BigDecimal("0"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(3);
//    taxonomicDatasetValue.setValue(new BigDecimal("25.0"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(5);
//    taxonomicDatasetValue.setValue(new BigDecimal("5"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(10);
//    taxonomicDatasetValue.setValue(new BigDecimal("227"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(20);
//    taxonomicDatasetValue.setValue(new BigDecimal("68"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(27);
//    taxonomicDatasetValue.setValue(new BigDecimal("181.6"));
//    taxonomicDatasetValue.setQcFlag(3);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(28);
//    taxonomicDatasetValue.setValue(new BigDecimal("68.40"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(30);
//    taxonomicDatasetValue.setValue(new BigDecimal("4265000"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDataset.setValues(taxonomicDatasetValues);
//    datasets.add(taxonomicDataset);
//
//
//
//    taxonomicDataset = new TaxonomicDataset();
//    taxonomicDatasetValues = new ArrayList<>(9);
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(1);
//    taxonomicDatasetValue.setValue(new BigDecimal("69459"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(2);
//    taxonomicDatasetValue.setValue(new BigDecimal("0"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(3);
//    taxonomicDatasetValue.setValue(new BigDecimal("25.0"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(5);
//    taxonomicDatasetValue.setValue(new BigDecimal("5"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(10);
//    taxonomicDatasetValue.setValue(new BigDecimal("113"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(20);
//    taxonomicDatasetValue.setValue(new BigDecimal("68"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(27);
//    taxonomicDatasetValue.setValue(new BigDecimal("90.4"));
//    taxonomicDatasetValue.setQcFlag(3);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(28);
//    taxonomicDatasetValue.setValue(new BigDecimal("68.40"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(30);
//    taxonomicDatasetValue.setValue(new BigDecimal("4262000"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDataset.setValues(taxonomicDatasetValues);
//    datasets.add(taxonomicDataset);
//
//
//
//    taxonomicDataset = new TaxonomicDataset();
//    taxonomicDatasetValues = new ArrayList<>(9);
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(1);
//    taxonomicDatasetValue.setValue(new BigDecimal("159668"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(2);
//    taxonomicDatasetValue.setValue(new BigDecimal("0"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(3);
//    taxonomicDatasetValue.setValue(new BigDecimal("25.0"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(10);
//    taxonomicDatasetValue.setValue(new BigDecimal("16"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(17);
//    taxonomicDatasetValue.setValue(new BigDecimal("1"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(20);
//    taxonomicDatasetValue.setValue(new BigDecimal("68"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(27);
//    taxonomicDatasetValue.setValue(new BigDecimal("12.8"));
//    taxonomicDatasetValue.setQcFlag(3);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(28);
//    taxonomicDatasetValue.setValue(new BigDecimal("68.40"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(30);
//    taxonomicDatasetValue.setValue(new BigDecimal("4357000"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDataset.setValues(taxonomicDatasetValues);
//    datasets.add(taxonomicDataset);
//
//
//    taxonomicDataset = new TaxonomicDataset();
//    taxonomicDatasetValues = new ArrayList<>(8);
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(1);
//    taxonomicDatasetValue.setValue(new BigDecimal("88803"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(2);
//    taxonomicDatasetValue.setValue(new BigDecimal("0"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(3);
//    taxonomicDatasetValue.setValue(new BigDecimal("25.0"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(10);
//    taxonomicDatasetValue.setValue(new BigDecimal("16"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(20);
//    taxonomicDatasetValue.setValue(new BigDecimal("68"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(27);
//    taxonomicDatasetValue.setValue(new BigDecimal("12.8"));
//    taxonomicDatasetValue.setQcFlag(3);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(28);
//    taxonomicDatasetValue.setValue(new BigDecimal("68.40"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(30);
//    taxonomicDatasetValue.setValue(new BigDecimal("4212000"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//
//    taxonomicDataset.setValues(taxonomicDatasetValues);
//    datasets.add(taxonomicDataset);
//
//
//
//    taxonomicDataset = new TaxonomicDataset();
//    taxonomicDatasetValues = new ArrayList<>(9);
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(1);
//    taxonomicDatasetValue.setValue(new BigDecimal("88803"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(2);
//    taxonomicDatasetValue.setValue(new BigDecimal("0"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(3);
//    taxonomicDatasetValue.setValue(new BigDecimal("25.0"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(5);
//    taxonomicDatasetValue.setValue(new BigDecimal("2"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(10);
//    taxonomicDatasetValue.setValue(new BigDecimal("535"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(20);
//    taxonomicDatasetValue.setValue(new BigDecimal("68"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(27);
//    taxonomicDatasetValue.setValue(new BigDecimal("428.0"));
//    taxonomicDatasetValue.setQcFlag(3);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(28);
//    taxonomicDatasetValue.setValue(new BigDecimal("68.40"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(30);
//    taxonomicDatasetValue.setValue(new BigDecimal("4212000"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDataset.setValues(taxonomicDatasetValues);
//    datasets.add(taxonomicDataset);
//
//
//
//    taxonomicDataset = new TaxonomicDataset();
//    taxonomicDatasetValues = new ArrayList<>(9);
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(1);
//    taxonomicDatasetValue.setValue(new BigDecimal("88803"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(2);
//    taxonomicDatasetValue.setValue(new BigDecimal("0"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(3);
//    taxonomicDatasetValue.setValue(new BigDecimal("25.0"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(5);
//    taxonomicDatasetValue.setValue(new BigDecimal("43"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(10);
//    taxonomicDatasetValue.setValue(new BigDecimal("32"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(20);
//    taxonomicDatasetValue.setValue(new BigDecimal("68"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(27);
//    taxonomicDatasetValue.setValue(new BigDecimal("25.6"));
//    taxonomicDatasetValue.setQcFlag(3);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(28);
//    taxonomicDatasetValue.setValue(new BigDecimal("68.40"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(30);
//    taxonomicDatasetValue.setValue(new BigDecimal("4212000"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDataset.setValues(taxonomicDatasetValues);
//    datasets.add(taxonomicDataset);
//
//
//    taxonomicDataset = new TaxonomicDataset();
//    taxonomicDatasetValues = new ArrayList<>(9);
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(1);
//    taxonomicDatasetValue.setValue(new BigDecimal("85371"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(2);
//    taxonomicDatasetValue.setValue(new BigDecimal("0"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(3);
//    taxonomicDatasetValue.setValue(new BigDecimal("25.0"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(5);
//    taxonomicDatasetValue.setValue(new BigDecimal("2"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(10);
//    taxonomicDatasetValue.setValue(new BigDecimal("16"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(20);
//    taxonomicDatasetValue.setValue(new BigDecimal("68"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(27);
//    taxonomicDatasetValue.setValue(new BigDecimal("12.8"));
//    taxonomicDatasetValue.setQcFlag(3);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(28);
//    taxonomicDatasetValue.setValue(new BigDecimal("68.40"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDatasetValue = new TaxonomicDatasetValue();
//    taxonomicDatasetValue.setCode(30);
//    taxonomicDatasetValue.setValue(new BigDecimal("4212000"));
//    taxonomicDatasetValue.setQcFlag(0);
//    taxonomicDatasetValue.setOriginatorFlag(0);
//    taxonomicDatasetValues.add(taxonomicDatasetValue);
//
//    taxonomicDataset.setValues(taxonomicDatasetValues);
//    datasets.add(taxonomicDataset);
//
//
//    expectedTaxonomicDatasets.setTaxonomicDatasets(datasets);
//    assertEquals(expectedTaxonomicDatasets, taxonomicDatasets);
//
////TODO
////    ProfileData expectedProfileData = new ProfileData();
////    List<ProfileValue> profileDataValues = new ArrayList<>(4);
////    ProfileValue profileDataValue = new ProfileValue();
////    profileDataValue.setDepth(new BigDecimal("0"));
////    profileDataValue.setDepthErrorCode(0);
////    profileDataValue.setOriginatorDepthErrorFlag(0);
////    profileDataValue.setValue(new BigDecimal("8.96"));
////    profileDataValue.setQcFlag(0);
////    profileDataValue.setOriginatorFlag(0);
////    profileDataValues.add(profileDataValue);
////
////    profileDataValue = new ProfileValue();
////    profileDataValue.setDepth(new BigDecimal("30.90"));
////    profileDataValue.setDepthErrorCode(0);
////    profileDataValue.setOriginatorDepthErrorFlag(0);
////    profileDataValue.setValue(new BigDecimal("6.75"));
////    profileDataValue.setQcFlag(0);
////    profileDataValue.setOriginatorFlag(0);
////    profileDataValues.add(profileDataValue);
////
////    profileDataValue = new ProfileValue();
////    profileDataValue.setDepth(new BigDecimal("0.65"));
////    profileDataValue.setDepthErrorCode(0);
////    profileDataValue.setOriginatorDepthErrorFlag(0);
////    profileDataValue.setValue(new BigDecimal("20.5"));
////    profileDataValue.setQcFlag(0);
////    profileDataValue.setOriginatorFlag(0);
////    profileDataValues.add(profileDataValue);
////
////    profileDataValue = new ProfileValue();
////    profileDataValue.setDepth(new BigDecimal("8.10"));
////    profileDataValue.setDepthErrorCode(0);
////    profileDataValue.setOriginatorDepthErrorFlag(0);
////    profileDataValue.setValue(new BigDecimal("10"));
////    profileDataValue.setQcFlag(0);
////    profileDataValue.setOriginatorFlag(0);
////    profileDataValues.add(profileDataValue);
////
////    expectedProfileData.setValues(profileDataValues);
////    assertEquals(expectedTaxonomicDatasets, taxonomicDatasets);
//
//  }
}