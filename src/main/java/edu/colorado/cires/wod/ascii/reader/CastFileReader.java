package edu.colorado.cires.wod.ascii.reader;

import edu.colorado.cires.wod.ascii.BiologicalHeader;
import edu.colorado.cires.wod.ascii.CharacterData;
import edu.colorado.cires.wod.ascii.CharacterDataHeader;
import edu.colorado.cires.wod.ascii.PrimaryHeader;
import edu.colorado.cires.wod.ascii.SecondaryHeader;
import edu.colorado.cires.wod.ascii.TaxonomicDatasets;
import edu.colorado.cires.wod.ascii.WodFileReader;
import edu.colorado.cires.wod.ascii.WodFileReader.CharReader;
import edu.colorado.cires.wod.ascii.model.Attribute;
import edu.colorado.cires.wod.ascii.model.Cast;
import edu.colorado.cires.wod.ascii.model.Depth;
import edu.colorado.cires.wod.ascii.model.PrincipalInvestigator;
import edu.colorado.cires.wod.ascii.model.ProfileData;

import edu.colorado.cires.wod.ascii.model.QcAttribute;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class CastFileReader implements Iterator<Cast> {

  private static final int ORIG_CRUISE = 1;
  private static final int ORIG_STATION_CODE = 2;
  private static final int PRINCIPAL_INVESTIGATOR = 3;

  private Cast cast;
  private final CharReader charReader;
  private final String dataset;
//  private final String file;

  public CastFileReader(CharReader charReader, String dataset) throws IOException {
    this.charReader = charReader;
    this.dataset = dataset;
//    file = new RandomAccessFile(path.toFile(), "r");
//    charReader = new RandomAccessFileCharReader(file);
    cast = read();
  }

  private Cast read() throws IOException {
    try {
      PrimaryHeader primaryHeader = WodFileReader.readHeader(charReader);
      CharacterDataHeader characterDataHeader = WodFileReader.readCharacterDataHeader(charReader);
      SecondaryHeader secondaryHeader = WodFileReader.readSecondaryHeader(charReader);
      BiologicalHeader biologicalHeader = WodFileReader.readBiologicalHeader(charReader);
      TaxonomicDatasets taxonomicDatasets = null;
      if (biologicalHeader != null) {
        taxonomicDatasets = WodFileReader.readTaxonomicDatasets(charReader);
      }
      edu.colorado.cires.wod.ascii.ProfileData profileData = WodFileReader.readProfileData(charReader, primaryHeader.getNumberOfLevels(), primaryHeader.getVariables());

      String originatorsCruise = null;
      String originatorsStationCode = null;

      List<PrincipalInvestigator> principalInvestigators = new ArrayList<>();

      if (characterDataHeader != null) {
        for (CharacterData characterData : characterDataHeader.getCharacterData()) {
          switch (characterData.getType()) {
            case ORIG_CRUISE:
              originatorsCruise = characterData.getValue();
              break;
            case ORIG_STATION_CODE:
              originatorsStationCode = characterData.getValue();
              break;
            case PRINCIPAL_INVESTIGATOR:
              for (edu.colorado.cires.wod.ascii.PrincipalInvestigator principalInvestigator : characterData.getPrincipalInvestigators()) {
                principalInvestigators.add(PrincipalInvestigator.builder()
                    .setVariable(principalInvestigator.getVariableCode())
                    .setCode(principalInvestigator.getPiCode())
                    .build());
              }
              break;
            default:
              throw new IllegalStateException("Unsupported character data type: " + characterData.getType());
          }
        }
      }

      List<Attribute> attributes = secondaryHeader == null ? new ArrayList<>(0) : secondaryHeader.getValues().stream()
          .map(shv -> Attribute.builder().setCode(shv.getCode()).setValue(shv.getValue()).build())
          .collect(Collectors.toList());

      List<Attribute> bioAttributes = biologicalHeader == null ? new ArrayList<>(0) : biologicalHeader.getValues().stream()
          .map(hv -> Attribute.builder().setCode(hv.getCode()).setValue(hv.getValue().doubleValue()).build())
          .collect(Collectors.toList());

      List<List<QcAttribute>> taxDatasets = taxonomicDatasets == null ? new ArrayList<>(0) : taxonomicDatasets.getTaxonomicDatasets().stream()
          .map(td -> td.getValues().stream()
              .map(tdv -> QcAttribute.builder().setCode(tdv.getCode()).setValue(tdv.getValue().doubleValue()).setQcFlag(tdv.getQcFlag()).setOriginatorsFlag(tdv.getOriginatorFlag()).build()).collect(Collectors.toList()))
          .collect(Collectors.toList());

      List<Depth> depths = profileData.getDepths().stream()
          .map(d -> Depth.builder()
              .setDepth(d.getDepth().doubleValue())
              .setDepthErrorFlag(d.getDepthErrorCode())
              .setOriginatorsFlag(d.getOriginatorDepthErrorFlag())
              .setData(d.getValues().entrySet().stream()
                  .map(e -> ProfileData.builder()
                      .setVariable(e.getKey())
                      .setQcFlag(e.getValue().getQcFlag())
                      .setOriginatorsFlag(e.getValue().getOriginatorFlag())
                      .setValue(e.getValue().getValue().doubleValue())
                      .build())
                  .collect(Collectors.toList()))
              .build())
          .collect(Collectors.toList());

      return Cast.builder()
          .setCastNumber(primaryHeader.getCastNumber())
          .setCountry(primaryHeader.getCountryCode())
          .setCruiseNumber(primaryHeader.getCruiseNumber())
          .setDataset(dataset)
          .setOriginatorsCruise(originatorsCruise)
          .setYear((short) primaryHeader.getYear())
          .setMonth((short) primaryHeader.getMonth())
          .setDay((short) primaryHeader.getDay())
          .setTime(primaryHeader.getTime())
          .setLongitude(primaryHeader.getLongitude())
          .setLatitude(primaryHeader.getLatitude())
          .setProfileType((short) primaryHeader.getProfileType())
          .setOriginatorsStationCode(originatorsStationCode)
          .setPrincipalInvestigators(principalInvestigators)
          .setAttributes(attributes)
          .setBiologicalAttributes(bioAttributes)
          .setTaxonomicDatasets(taxDatasets)
          .setDepths(depths)
          .setVariables(primaryHeader.getVariables())
          .build();
    } catch (EOFException eof) {
      return null;
    }
  }

  @Override
  public boolean hasNext() {
    return cast != null;
  }

  @Override
  public Cast next() {
    if (cast == null) {
      throw new NoSuchElementException("No more casts");
    }
    Cast toReturn = cast;
    try {
      cast = read();
    } catch (IOException e) {
      throw new RuntimeException("Unable to read file", e);
    }
    return toReturn;
  }


}
