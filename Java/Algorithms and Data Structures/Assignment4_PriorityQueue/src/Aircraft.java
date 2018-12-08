
public class Aircraft {
	public String flightID;
	public int arrivalTime;
	public int landingPriority;
	
	public Aircraft(String ID, int aTime, int landP){
		flightID = ID;
		arrivalTime = aTime;
		landingPriority = landP;
	}
	
	//logical statement to identify which plane should land first
	public boolean compareTo(Aircraft comp){
		if (this.landingPriority < comp.landingPriority) {
			return true;
		}
		//if landing priorities are the same, the plane with the earlier arrival time lands first
		if (this.landingPriority == comp.landingPriority){
			if (this.arrivalTime < comp.arrivalTime){
				return true;
			}
			else if (this.arrivalTime > comp.arrivalTime){
				return false;
			}
		}
		return false;
	}
}