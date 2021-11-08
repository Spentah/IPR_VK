package api.profile_info;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RelationPartner{
	private boolean canAccessClosed;
	private String lastName;
	private int id;
	private String firstName;
	private boolean isClosed;

	@JsonProperty("can_access_closed")
	public boolean isCanAccessClosed(){
		return canAccessClosed;
	}

	@JsonProperty("last_name")
	public String getLastName(){
		return lastName;
	}

	public int getId(){
		return id;
	}

	@JsonProperty("first_name")
	public String getFirstName(){
		return firstName;
	}

	@JsonProperty("is_closed")
	public boolean isIsClosed(){
		return isClosed;
	}
}
