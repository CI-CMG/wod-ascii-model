package edu.colorado.cires.wod.ascii;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PrimaryHeader {

  private WodVersion wodVersion;
  private int profile;
  private int castNumber;
  private String countryCode;
  private int cruiseNumber;
  private int year;
  private int month;
  private int day;
  private Double time;
  private Double latitude;
  private Double longitude ;
  private int numberOfLevels;
  private int profileType;
  private int numberOfVariables;
  private List<Variable> variables = new ArrayList<>(0);

  public WodVersion getWodVersion() {
    return wodVersion;
  }

  public void setWodVersion(WodVersion wodVersion) {
    this.wodVersion = wodVersion;
  }

  public int getProfile() {
    return profile;
  }

  public void setProfile(int profile) {
    this.profile = profile;
  }

  public int getCastNumber() {
    return castNumber;
  }

  public void setCastNumber(int castNumber) {
    this.castNumber = castNumber;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  public int getCruiseNumber() {
    return cruiseNumber;
  }

  public void setCruiseNumber(int cruiseNumber) {
    this.cruiseNumber = cruiseNumber;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public int getMonth() {
    return month;
  }

  public void setMonth(int month) {
    this.month = month;
  }

  public int getDay() {
    return day;
  }

  public void setDay(int day) {
    this.day = day;
  }

  public Double getTime() {
    return time;
  }

  public void setTime(Double time) {
    this.time = time;
  }

  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  public int getNumberOfLevels() {
    return numberOfLevels;
  }

  public void setNumberOfLevels(int numberOfLevels) {
    this.numberOfLevels = numberOfLevels;
  }

  public int getProfileType() {
    return profileType;
  }

  public void setProfileType(int profileType) {
    this.profileType = profileType;
  }

  public int getNumberOfVariables() {
    return numberOfVariables;
  }

  public void setNumberOfVariables(int numberOfVariables) {
    this.numberOfVariables = numberOfVariables;
  }

  public List<Variable> getVariables() {
    return variables;
  }

  public void setVariables(List<Variable> variables) {
    if(variables == null) {
      variables = new ArrayList<>(0);
    }
    this.variables = variables;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PrimaryHeader that = (PrimaryHeader) o;
    return profile == that.profile && castNumber == that.castNumber && cruiseNumber == that.cruiseNumber && year == that.year && month == that.month
        && day == that.day && numberOfLevels == that.numberOfLevels && profileType == that.profileType && numberOfVariables == that.numberOfVariables
        && wodVersion == that.wodVersion && Objects.equals(countryCode, that.countryCode) && Objects.equals(time, that.time)
        && Objects.equals(latitude, that.latitude) && Objects.equals(longitude, that.longitude) && Objects.equals(
        variables, that.variables);
  }

  @Override
  public int hashCode() {
    return Objects.hash(wodVersion, profile, castNumber, countryCode, cruiseNumber, year, month, day, time, latitude, longitude, numberOfLevels,
        profileType, numberOfVariables, variables);
  }

  @Override
  public String toString() {
    return "PrimaryHeader{" +
        "wodVersion=" + wodVersion +
        ", profile=" + profile +
        ", castNumber=" + castNumber +
        ", countryCode='" + countryCode + '\'' +
        ", cruiseNumber=" + cruiseNumber +
        ", year=" + year +
        ", month=" + month +
        ", day=" + day +
        ", time=" + time +
        ", latitude=" + latitude +
        ", longitude=" + longitude +
        ", numberOfLevels=" + numberOfLevels +
        ", profileType=" + profileType +
        ", numberOfVariables=" + numberOfVariables +
        ", variables=" + variables +
        '}';
  }
}
