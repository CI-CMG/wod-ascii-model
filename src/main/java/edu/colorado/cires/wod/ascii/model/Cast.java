package edu.colorado.cires.wod.ascii.model;

import edu.colorado.cires.wod.ascii.Variable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cast {

  private final Integer castNumber;
  private final String country;
  private final Integer cruiseNumber;
  private final String originatorsCruise;
  private final Short year;
  private final Short month;
  private final Short day;
  private final Double time;
  private final Double longitude;
  private final Double latitude;
  private final Short profileType;
  private final String dataset;
  private final String file;
  private final String originatorsStationCode;
  //TODO use model package
  private final List<Variable> variables;
  private final List<PrincipalInvestigator> principalInvestigators;
  private final List<Attribute> attributes;
  private final List<Attribute> biologicalAttributes;
  private final List<List<QcAttribute>> taxonomicDatasets;
  private final List<Depth> depths;

  private Cast(Integer castNumber, String country, Integer cruiseNumber, String originatorsCruise, Short year, Short month, Short day, Double time,
      Double longitude, Double latitude, Short profileType, String dataset, String file, String originatorsStationCode, List<Variable> variables, List<Depth> depths,
      List<PrincipalInvestigator> principalInvestigators, List<Attribute> attributes, List<Attribute> biologicalAttributes,
      List<List<QcAttribute>> taxonomicDatasets) {
    this.castNumber = castNumber;
    this.country = country;
    this.cruiseNumber = cruiseNumber;
    this.originatorsCruise = originatorsCruise;
    this.year = year;
    this.month = month;
    this.day = day;
    this.time = time;
    this.longitude = longitude;
    this.latitude = latitude;
    this.profileType = profileType;
    this.dataset = dataset;
    this.file = file;
    this.variables = variables;
    this.originatorsStationCode = originatorsStationCode;
    this.depths = Collections.unmodifiableList(depths);
    this.principalInvestigators = Collections.unmodifiableList(principalInvestigators);
    this.attributes = Collections.unmodifiableList(attributes);
    this.biologicalAttributes = Collections.unmodifiableList(biologicalAttributes);
    this.taxonomicDatasets = Collections.unmodifiableList(taxonomicDatasets);
  }

  public Integer getCastNumber() {
    return castNumber;
  }

  public String getCountry() {
    return country;
  }

  public Integer getCruiseNumber() {
    return cruiseNumber;
  }

  public String getOriginatorsCruise() {
    return originatorsCruise;
  }

  public Short getYear() {
    return year;
  }

  public Short getMonth() {
    return month;
  }

  public Short getDay() {
    return day;
  }

  public Double getTime() {
    return time;
  }

  public Double getLongitude() {
    return longitude;
  }

  public Double getLatitude() {
    return latitude;
  }

  public Short getProfileType() {
    return profileType;
  }

  public String getOriginatorsStationCode() {
    return originatorsStationCode;
  }

  public List<Depth> getDepths() {
    return depths;
  }

  public List<PrincipalInvestigator> getPrincipalInvestigators() {
    return principalInvestigators;
  }

  public List<Attribute> getAttributes() {
    return attributes;
  }

  public List<Attribute> getBiologicalAttributes() {
    return biologicalAttributes;
  }

  public List<List<QcAttribute>> getTaxonomicDatasets() {
    return taxonomicDatasets;
  }

  public String getDataset() {
    return dataset;
  }

  public String getFile() {
    return file;
  }

  public List<Variable> getVariables() {
    return variables;
  }

  @Override
  public String toString() {
    return "Cast{" +
        "castNumber=" + castNumber +
        ", country='" + country + '\'' +
        ", cruiseNumber=" + cruiseNumber +
        ", originatorsCruise='" + originatorsCruise + '\'' +
        ", year=" + year +
        ", month=" + month +
        ", day=" + day +
        ", time=" + time +
        ", longitude=" + longitude +
        ", latitude=" + latitude +
        ", profileType=" + profileType +
        ", dataset='" + dataset + '\'' +
        ", file='" + file + '\'' +
        ", originatorsStationCode='" + originatorsStationCode + '\'' +
        ", variables=" + variables +
        ", principalInvestigators=" + principalInvestigators +
        ", attributes=" + attributes +
        ", biologicalAttributes=" + biologicalAttributes +
        ", taxonomicDatasets=" + taxonomicDatasets +
        ", depths=" + depths +
        '}';
  }

  public static Builder builder() {
    return new Builder(null);
  }

  public static Builder builder(Cast src) {
    return new Builder(src);
  }


  public static class Builder {

    private Integer castNumber;
    private String country;
    private Integer cruiseNumber;
    private String originatorsCruise;
    private Short year;
    private Short month;
    private Short day;
    private Double time;
    private Double longitude;
    private Double latitude;
    private Short profileType;
    private String dataset;
    private String file;
    private String originatorsStationCode;
    private List<Variable> variables = new ArrayList<>(0);
    private List<Depth> depths = new ArrayList<>(0);
    private List<PrincipalInvestigator> principalInvestigators = new ArrayList<>(0);
    private List<Attribute> attributes = new ArrayList<>(0);
    private List<Attribute> biologicalAttributes = new ArrayList<>(0);
    private List<List<QcAttribute>> taxonomicDatasets = new ArrayList<>(0);

    private Builder(Cast src) {
      if (src != null) {
        castNumber = src.castNumber;
        country = src.country;
        cruiseNumber = src.cruiseNumber;
        originatorsCruise = src.originatorsCruise;
        year = src.year;
        month = src.month;
        day = src.day;
        time = src.time;
        longitude = src.longitude;
        latitude = src.latitude;
        profileType = src.profileType;
        dataset = src.dataset;
        file = src.file;
        originatorsStationCode = src.originatorsStationCode;
        variables = src.variables;
        depths = src.depths;
        principalInvestigators = src.principalInvestigators;
        biologicalAttributes = src.biologicalAttributes;
        taxonomicDatasets = src.taxonomicDatasets;
      }
    }

    public Builder setFile(String file) {
      this.file = file;
      return this;
    }

    public Builder setCastNumber(Integer castNumber) {
      this.castNumber = castNumber;
      return this;
    }

    public Builder setCountry(String country) {
      this.country = country;
      return this;
    }

    public Builder setCruiseNumber(Integer cruiseNumber) {
      this.cruiseNumber = cruiseNumber;
      return this;
    }

    public Builder setDataset(String dataset) {
      this.dataset = dataset;
      return this;
    }

    public Builder setOriginatorsCruise(String originatorsCruise) {
      this.originatorsCruise = originatorsCruise;
      return this;
    }

    public Builder setYear(Short year) {
      this.year = year;
      return this;
    }

    public Builder setMonth(Short month) {
      this.month = month;
      return this;
    }

    public Builder setDay(Short day) {
      this.day = day;
      return this;
    }

    public Builder setTime(Double time) {
      this.time = time;
      return this;
    }

    public Builder setLongitude(Double longitude) {
      this.longitude = longitude;
      return this;
    }

    public Builder setLatitude(Double latitude) {
      this.latitude = latitude;
      return this;
    }

    public Builder setProfileType(Short profileType) {
      this.profileType = profileType;
      return this;
    }

    public Builder setOriginatorsStationCode(String originatorsStationCode) {
      this.originatorsStationCode = originatorsStationCode;
      return this;
    }

    public Builder setDepths(List<Depth> depths) {
      if (depths == null) {
        depths = new ArrayList<>(0);
      }
      this.depths = new ArrayList<>(depths);
      return this;
    }

    public Builder setPrincipalInvestigators(List<PrincipalInvestigator> principalInvestigators) {
      if (principalInvestigators == null) {
        principalInvestigators = new ArrayList<>(0);
      }
      this.principalInvestigators = new ArrayList<>(principalInvestigators);
      return this;
    }

    public Builder setAttributes(List<Attribute> attributes) {
      if (attributes == null) {
        attributes = new ArrayList<>(0);
      }
      this.attributes = new ArrayList<>(attributes);
      return this;
    }

    public Builder setVariables(List<Variable> variables) {
      if (variables == null) {
        variables = new ArrayList<>(0);
      }
      this.variables = new ArrayList<>(variables);
      return this;
    }

    public Builder setBiologicalAttributes(List<Attribute> biologicalAttributes) {
      if (biologicalAttributes == null) {
        biologicalAttributes = new ArrayList<>(0);
      }
      this.biologicalAttributes = new ArrayList<>(biologicalAttributes);
      return this;
    }

    public Builder setTaxonomicDatasets(List<List<QcAttribute>> taxonomicDatasets) {
      if (taxonomicDatasets == null) {
        taxonomicDatasets = new ArrayList<>(0);
      }
      this.taxonomicDatasets = new ArrayList<>(taxonomicDatasets);
      return this;
    }

    public Cast build() {
      return new Cast(
          castNumber,
          country,
          cruiseNumber,
          originatorsCruise,
          year,
          month,
          day,
          time,
          longitude,
          latitude,
          profileType,
          dataset,
          file,
          originatorsStationCode,
          variables,
          depths,
          principalInvestigators,
          attributes,
          biologicalAttributes,
          taxonomicDatasets);
    }
  }
}
