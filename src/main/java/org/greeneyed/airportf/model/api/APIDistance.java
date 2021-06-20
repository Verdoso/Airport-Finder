package org.greeneyed.airportf.model.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class APIDistance implements Comparable<APIDistance>
{
  private final int kilometers;
  private final int meters;

  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    if (kilometers > 0)
    {
      sb.append(kilometers);
      sb.append("km");
      if (meters > 0)
      {
        sb.append(" ");
      }
    }
    if (meters > 0)
    {
      sb.append(meters);
      sb.append("m");
    }
    return sb.toString();
  }

  @Override
  public int compareTo(APIDistance o)
  {
    return (o.kilometers * 1000 + o.meters) - (kilometers * 1000 + meters);
  }
}
