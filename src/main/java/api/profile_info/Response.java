package api.profile_info;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Response{

	private Country country;
	private String bdate;
	private City city;
	private int sex;
	private String lastName;
	private int relation;
	private RelationPartner relationPartner;
	private String homeTown;
	private String phone;
	private String screenName;
	private int id;
	private String firstName;
	private int bdateVisibility;
	private String status;

	public Country getCountry(){
		return country;
	}

	public String getBdate(){
		return bdate;
	}

	public City getCity(){
		return city;
	}

	public int getSex(){
		return sex;
	}

	@JsonProperty("last_name")
	public String getLastName(){
		return lastName;
	}

	public int getRelation(){
		return relation;
	}

	@JsonProperty("relation_partner")
	public RelationPartner getRelationPartner(){
		return relationPartner;
	}

	@JsonProperty("home_town")
	public String getHomeTown(){
		return homeTown;
	}

	public String getPhone(){
		return phone;
	}

	@JsonProperty("screen_name")
	public String getScreenName(){
		return screenName;
	}

	public int getId(){
		return id;
	}

	@JsonProperty("first_name")
	public String getFirstName(){
		return firstName;
	}

	@JsonProperty("bdate_visibility")
	public int getBdateVisibility(){
		return bdateVisibility;
	}

	public String getStatus(){
		return status;
	}

	@Override
	public String toString() {
		return "Response{" +
//				"response=" + response +
				"country=" + country +
				", bdate='" + bdate + '\'' +
				", city=" + city +
				", sex=" + sex +
				", lastName='" + lastName + '\'' +
				", relation=" + relation +
				", relationPartner=" + relationPartner +
				", homeTown='" + homeTown + '\'' +
				", phone='" + phone + '\'' +
				", screenName='" + screenName + '\'' +
				", id=" + id +
				", firstName='" + firstName + '\'' +
				", bdateVisibility=" + bdateVisibility +
				", status='" + status + '\'' +
				'}';
	}
}
