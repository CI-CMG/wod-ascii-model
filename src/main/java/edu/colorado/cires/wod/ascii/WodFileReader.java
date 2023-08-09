package edu.colorado.cires.wod.ascii;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;

public class WodFileReader {


  private static final int COUNTRY_CODE_SIZE = 2;
  private static final int YEAR_SIZE = 4;
  private static final int DAY_SIZE = 2;
  private static final int MONTH_SIZE = 2;
  private static final char NO_VALUE = '-';
  private static final int PROFILE_TYPE_SIZE = 1;
  private static final int NUMBER_OF_VARIABLES_SIZE = 2;
  private static final int QC_FLAG_SIZE = 1;
  private static final int CHARACTER_DATA_ENTRIES_SIZE = 1;
  private static final int CHARACTER_DATA_TYPE_SIZE = 1;
  private static final int NUMBER_OF_PI_NAMES_SIZE = 2;
  private static final int TAX_QC_FLAG_SIZE = 1;
  private static final int TAX_ORIG_FLAG_SIZE = 1;
  private static final int DEPTH_ERROR_CODE_SIZE = 1;
  private static final int ORIG_DEPTH_ERROR_FLAG_SIZE = 1;
  private static final int PROFILE_QC_FLAG_SIZE = 1;
  private static final int PROFILE_ORIG_FLAG_SIZE = 1;


  public interface CharReader {

    char readChar() throws IOException;
  }

  public static class RandomAccessFileCharReader implements CharReader {
    private final RandomAccessFile file;

    public RandomAccessFileCharReader(RandomAccessFile file) {
      this.file = file;
    }

    @Override
    public char readChar() throws IOException {
      return (char) file.readByte();
    }
  }


  private static char readOneTrim(CharReader file) throws IOException {
    char b = file.readChar();
    while (Character.isWhitespace(b)) {
      b = file.readChar();
    }
    return b;
  }

  private static char readOne(CharReader file) throws IOException {
    char b = file.readChar();
    while (Character.isWhitespace(b) && !Character.isSpaceChar(b)) {
      b = file.readChar();
    }
    return b;
  }

  private static char[] readBytes(CharReader file, int size) throws IOException {
    char[] bytes = new char[size];
    for (int i = 0; i < size; i++) {
      bytes[i] = readOne(file);
    }
    return bytes;
  }

  /*

  C41303567064US5112031934 8 744210374426193562-17227140 6:11010:12010:13011~1822058:14
  011~1822029:16011~1822029:19010:24721


   */

  public static PrimaryHeader readHeader(CharReader file) throws IOException {
    //C
    WodVersion wodVersion = getWodVersion(file);
    //4-1303
    int profile = getRequiredInt(file);
    //5-67064
    int castNumber = getRequiredInt(file);
    //US
    String countryCode = getString(file, COUNTRY_CODE_SIZE);
    //5-11203
    int cruiseNumber = getRequiredInt(file);
    //1934
    int year = getInt(file, YEAR_SIZE);
    // 8
    int month = getInt(file, MONTH_SIZE);
    // 7
    int day = getInt(file, DAY_SIZE); //TODO handle "zero" value
    //4-4-2-1037
    Double time = getDouble(file);
    //4-4-2-6193
    Double latitude = getDouble(file);
    //5-6-2--17227
    Double longitude = getDouble(file);
    //1-4
    int numberOfLevels = getRequiredInt(file);
    //0
    int profileType = getInt(file, PROFILE_TYPE_SIZE);
    // 6
    int numberOfVariables = getInt(file, NUMBER_OF_VARIABLES_SIZE);
    List<Variable> variables = new ArrayList<>(numberOfVariables);
    for (int n = 0; n < numberOfVariables; n++) {
      //1-1,1-2,1-3,1-4,1-6,1-9
      int variableCode = getRequiredInt(file);
      //0,0,0,0,0,0
      int qcFlag = getInt(file, QC_FLAG_SIZE);

      Variable variable = new Variable();
      variable.setCode(variableCode);
      variable.setQcFlag(qcFlag);
      variables.add(variable);

      //1-0,1-0,1-1,1-1,1-1,1-0
      int numberOfMetadata = getRequiredInt(file);
      List<Metadata> metadataList = new ArrayList<>(numberOfMetadata);
      for (int m = 0; m < numberOfMetadata; m++) {
        //1-8,1-8,1-8
        OptionalInt metadataCode = getInt(file);
        if (!metadataCode.isPresent()) {
          break;
        }
        //2-2-0-58,2-2-0-29,2-2-0-29
        double metadataValue = getDouble(file);
        Metadata metadata = new Metadata();
        metadata.setCode(metadataCode.getAsInt());
        metadata.setValue(metadataValue);
        metadataList.add(metadata);
      }

      variable.setMetadata(metadataList);
    }
    PrimaryHeader primaryHeader = new PrimaryHeader();
    primaryHeader.setWodVersion(wodVersion);
    primaryHeader.setProfile(profile);
    primaryHeader.setCastNumber(castNumber);
    primaryHeader.setCountryCode(countryCode);
    primaryHeader.setCruiseNumber(cruiseNumber);
    primaryHeader.setYear(year);
    primaryHeader.setMonth(month);
    primaryHeader.setDay(day);
    primaryHeader.setTime(time);
    primaryHeader.setLatitude(latitude);
    primaryHeader.setLongitude(longitude);
    primaryHeader.setNumberOfLevels(numberOfLevels);
    primaryHeader.setProfileType(profileType);
    primaryHeader.setNumberOfVariables(numberOfVariables);
    primaryHeader.setVariables(variables);
    return primaryHeader;
  }

  /*
  24721 8STOCS85A3 4103215:103216:5-50063217:5-50023218:273

   */

  public static CharacterDataHeader readCharacterDataHeader(CharReader file) throws IOException {
    CharacterDataHeader characterDataHeader = new CharacterDataHeader();
    OptionalInt b = getInt(file);
    if (!b.isPresent()) {
      return null;
    }
    //2-47
    characterDataHeader.setDataBytes(b.getAsInt());
    //2
    int characterDataEntries = getInt(file, CHARACTER_DATA_ENTRIES_SIZE);
    List<CharacterData> characterData = new ArrayList<>(characterDataEntries);
    for (int c = 0; c < characterDataEntries; c++) {
      //1,3
      int type = getInt(file, CHARACTER_DATA_TYPE_SIZE);
      CharacterData data = new CharacterData();
      data.setType(type);
      switch (type) {
        case 1:
        case 2:
          // 8-STOCS85A
          data.setValue(getString(file));
          break;
        case 3:
          // 4
          int numberOfPiNames = getInt(file, NUMBER_OF_PI_NAMES_SIZE);
          List<PrincipalInvestigator> principalInvestigators = new ArrayList<>(numberOfPiNames);
          for (int p = 0; p < numberOfPiNames; p++) {
            PrincipalInvestigator principalInvestigator = new PrincipalInvestigator();
            //1-0,1-0,5--5006,5--5002
            principalInvestigator.setVariableCode(getRequiredInt(file));
            //3-215,3-216,3-217,3-218
            principalInvestigator.setPiCode(getRequiredInt(file));
            principalInvestigators.add(principalInvestigator);
          }
          data.setPrincipalInvestigators(principalInvestigators);
          break;
        default:
          throw new IllegalStateException("Invalid character data type: " + type);
      }
      characterData.add(data);
    }

    characterDataHeader.setCharacterData(characterData);

    return characterDataHeader;

  }

  /*
  273
18:11,7709500110:13,4401427:14,330393:17,22076:210,22060:229,1107:291,1103:299,7702013302:3846181
   */

  public static SecondaryHeader readSecondaryHeader(CharReader file) throws IOException {
    SecondaryHeader secondaryHeader = new SecondaryHeader();
    OptionalInt b = getInt(file);
    if (!b.isPresent()) {
      return null;
    }
    //2-73
    secondaryHeader.setTotalBytesForSecondaryHeader(b.getAsInt());
    //1-8
    int entries = getRequiredInt(file);
    List<SecondaryHeaderValue> values = new ArrayList<>(entries);
    for (int s = 0; s < entries; s++) {
      SecondaryHeaderValue value = new SecondaryHeaderValue();
      //1-1
      value.setCode(getRequiredInt(file));
      value.setValue(getDouble(file));
      values.add(value);
    }
    secondaryHeader.setValues(values);

    return secondaryHeader;

  }

  /*
  384618:1
2,4421800:13,22076:14,1102:17,330103:19,22205:213,22011:216,4421037:230,7709500110:1818115508527
20012110000133312500021011060022022068002272214830228442684000230770421200000191
15507911800121100001333125000151105002103302270022022068002274411816302284426840
00230770426500000191155069459001211000013331250001511050021033011300220220680022
73319043022844268400023077042620000019116601596680012110000133312500021022016002
17110100220220680022733112830228442684000230770435700000181155088803001211000013
   */

  public static BiologicalHeader readBiologicalHeader(CharReader file) throws IOException {
    BiologicalHeader biologicalHeader = new BiologicalHeader();
    OptionalInt b = getInt(file);
    if (!b.isPresent()) {
      return null;
    }
    biologicalHeader.setTotalBytesForBiologicalHeader(b.getAsInt());
    //1-8
    int entries = getRequiredInt(file);
    List<BiologicalHeaderValue> values = new ArrayList<>(entries);
    for (int s = 0; s < entries; s++) {
      BiologicalHeaderValue value = new BiologicalHeaderValue();
      //1-1
      value.setCode(getRequiredInt(file));
      value.setValue(getBigDecimal(file));
      values.add(value);
    }
    biologicalHeader.setValues(values);

    return biologicalHeader;


  }

  /*
  1818115508527
200:12110000:1333125000:210110600:2202206800:2272214830:228442684000:230770421200000:191
15507911800:12110000:1333125000:15110500:21033022700:2202206800:227441181630:2284426840
00:230770426500000:19:115506945900:12110000:1333125000:15110500:21033011300:2202206800:22
733190430:228442684000:230770426200000:19:1166015966800:12110000:1333125000:2102201600:2
17110100:2202206800:22733112830:228442684000:230770435700000:18:115508880300:12110000:13
33125000:2102201600:2202206800:22733112830:228442684000:230770421200000:19:115508880300:
12110000:1333125000:15110200:21033053500:2202206800:227441428030:228442684000:230770421
200000:19:115508880300:12110000:1333125000:152204300:2102203200:2202206800:22733125630:22
8442684000:230770421200000:19:115508537100:12110000:1333125000:15110200:2102201600:22022
06800:22733112830:228442684000:230770421200000:1100003328960044230900033267500222650
03312050033281000220100033289500442309000332670002227100331123003328100022025002
22900044231910033286200222900033115400332810002205000342-12300442324100332728003
32117003312560033280500
   */

  public static TaxonomicDatasets readTaxonomicDatasets(CharReader file) throws IOException {
    char bytes = readOne(file);
    if (NO_VALUE == bytes) {
      return null;
    }
    //1-8
    int numberOfDatasets = getInt(file, Character.getNumericValue(bytes));
    List<TaxonomicDataset> datasets = new ArrayList<>(numberOfDatasets);
    for (int t = 0; t < numberOfDatasets; t++) {
      //1-8
      int entries = getRequiredInt(file);
      List<TaxonomicDatasetValue> values = new ArrayList<>(entries);
      for (int x = 0; x < entries; x++) {
        TaxonomicDatasetValue value = new TaxonomicDatasetValue();
        //1-1,1-2,1-3,2-10,2-20,2-27,2-28,2-30
        value.setCode(getRequiredInt(file));
        //550-85272,110-0,331-250,110-6,220-68,221-48,442-6840,770-4212000
        value.setValue(getBigDecimal(file));
        //0,0
        value.setQcFlag(getInt(file, TAX_QC_FLAG_SIZE));
        //0,0
        value.setOriginatorFlag(getInt(file, TAX_ORIG_FLAG_SIZE));
        values.add(value);
      }
      TaxonomicDataset dataset = new TaxonomicDataset();
      dataset.setValues(values);
      datasets.add(dataset);
    }
    TaxonomicDatasets taxonomicDatasets = new TaxonomicDatasets();
    taxonomicDatasets.setTaxonomicDatasets(datasets);
    return taxonomicDatasets;
  }

  /*
  11000033289600:44230900033267500:222650
033120500:332810002201000:33289500442309000332670002227100331123003328100022025002
22900044231910033286200222900033115400332810002205000342-12300442324100332728003
32117003312560033280500
   */

  public static ProfileData readProfileData(CharReader file, int levels, List<Variable> variables) throws IOException {
    ProfileData profileData = new ProfileData();
    List<ProfileDepth> depths = new ArrayList<>(levels);
    for (int l = 0; l < levels; l++) {
      BigDecimal depth = getBigDecimal(file);
      if(depth != null) {
        ProfileDepth profileDepth = new ProfileDepth();
        profileDepth.setDepth(depth);
        profileDepth.setDepthErrorCode(getInt(file, DEPTH_ERROR_CODE_SIZE));
        profileDepth.setOriginatorDepthErrorFlag(getInt(file,ORIG_DEPTH_ERROR_FLAG_SIZE));

        Map<Integer, ProfileValue> values = new LinkedHashMap<>();
        for (Variable variable : variables) {
          ProfileValue profileValue = new ProfileValue();
          BigDecimal value = getBigDecimal(file);
          if (value == null) {
            continue;
          }
          profileValue.setValue(value);
          profileValue.setQcFlag(getInt(file, PROFILE_QC_FLAG_SIZE));
          profileValue.setOriginatorFlag(getInt(file, PROFILE_ORIG_FLAG_SIZE));
          values.put(variable.getCode(), profileValue);
        }

        profileDepth.setValues(values);
        depths.add(profileDepth);
      } else {
        throw new IllegalStateException("Depth must not be null");
      }
    }
    profileData.setDepths(depths);
    return profileData;
  }

  public static WodVersion getWodVersion(CharReader file) throws IOException {
    char v = (char) readOneTrim(file);
    switch (v) {
      case 'A':
        return WodVersion.WOD01;
      case 'B':
        return WodVersion.WOD09;
      case 'C':
        return WodVersion.WOD18;
      default: {
        if (Character.isDigit(v)) {
          return WodVersion.WOD98;
        }
        return WodVersion.OTHER;
      }
    }
  }


  private static int asciiBytesToInt(char[] bytes) {
    int result = 0;
    int negative = 1;
    for (int i = 0; i < bytes.length; i++) {
      char b = bytes[i];
      if (' ' == b) {
        continue;
      }
      if ('-' == b) {
        negative = -1;
        continue;
      }
      int digit = Character.getNumericValue(b);
      if ((digit < 0) || (digit > 9)) {
        throw new NumberFormatException("Invalid digit '" + b + "'");
      }
      result *= 10;
      result += digit;
    }
    return negative * result;
  }

  private static double asciiBytesToDouble(char[] bytes, int precision) {
    return asciiBytesToBigDecimal(bytes, precision).doubleValue();
//    double whole = asciiBytesToInt(Arrays.copyOfRange(bytes, 0, bytes.length - precision));
//    double part = asciiBytesToInt(Arrays.copyOfRange(bytes, bytes.length - precision, bytes.length));
//    if (whole >= 0) {
//      return whole + (part * Math.pow(0.1, precision));
//    } else {
//      return whole - (part * Math.pow(0.1, precision));
//    }
  }

  private static BigDecimal asciiBytesToBigDecimal(char[] bytes, int precision) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < bytes.length - precision; i++) {
      sb.append(bytes[i]);
    }
    if (precision > 0) {
      sb.append('.');
      for (int i = bytes.length - precision; i < bytes.length; i++) {
        sb.append(bytes[i]);
      }
    }
    return new BigDecimal(sb.toString().trim());
  }


  public static String getString(CharReader file, int size) throws IOException {
    char[] bytes = readBytes(file, size);
    return new String(bytes);
  }

  public static String getString(CharReader file) throws IOException {
    int size = getInt(file, 2);
    char[] bytes = readBytes(file, size);
    return new String(bytes);
  }

  public static int getRequiredInt(CharReader file) throws IOException {
    return getInt(file).orElseThrow(() -> new IllegalStateException("int value is required, but size is zero"));
  }

  public static OptionalInt getInt(CharReader file) throws IOException {
    char c = readOne(file);
    int size = Character.getNumericValue(c);
    if (size < 0) {
      throw new IllegalArgumentException("Unable to read integer: " + c);
    }
    if (size == 0) {
      return OptionalInt.empty();
    }
    char[] bytes = readBytes(file, size);
    return OptionalInt.of(asciiBytesToInt(bytes));
  }


  public static int getInt(CharReader file, int size) throws IOException {
    char[] bytes = readBytes(file, size);
    return asciiBytesToInt(bytes);
  }

  public static Double getDouble(CharReader file) throws IOException {
    char significantDigits = readOne(file);
    if (significantDigits == NO_VALUE) {
      return null;
    } else {
      int totalDigits = Character.getNumericValue(readOne(file));
      int precision = Character.getNumericValue(readOne(file));
      char[] bytes = readBytes(file, totalDigits);
      return asciiBytesToDouble(bytes, precision);
    }

  }

  public static BigDecimal getBigDecimal(CharReader file) throws IOException {
    char significantDigits = readOne(file);
    if (significantDigits == NO_VALUE) {
      return null;
    } else {
      int totalDigits = Character.getNumericValue(readOne(file));
      int precision = Character.getNumericValue(readOne(file));
      char[] bytes = readBytes(file, totalDigits);
      return asciiBytesToBigDecimal(bytes, precision);
    }

  }


}
