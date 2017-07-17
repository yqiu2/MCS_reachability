// valuation is a function that given a time, it gives a payout

public class Valuation {
	private functions function;
	private int maxVal;
	
	private int firstTime; // the first time at which you can get maxVal payment
	private int lastTime; // the lastTime at which you can get maxVal payment
	// for use in triangular and trapezoidal 
	private double upSlope; 
	private double downSlope;
	
	public Valuation () {
		function = functions.SinglePoint;
		maxVal = 0;
		firstTime = -1;
	}
	
	public Valuation(int time){
		function = functions.SinglePoint;
		maxVal = 1;
		firstTime = time;
	}

	public Valuation(int value, int time){
		function = functions.SinglePoint;
		maxVal = value;
		firstTime = time;
	}
	
	public void setSinglePoint(int value, int time){
		function = functions.SinglePoint;
		maxVal = value;
		firstTime = time;
	}

	public void setRectangle(int value, int firstTime, int lastTime) {
		function = functions.Rectangle;
		maxVal = value;
		this.firstTime = firstTime;
		this.lastTime = lastTime;
	}
	
	public void setTriangle(int value, int time, double slope){
		function = functions.Triangle;
		maxVal = value;
		firstTime = time;
		upSlope = Math.abs(slope);
		downSlope = -upSlope;  // not sure if you can do negatives like this ??
	}
	
	public void setTriangle(int value, int time, double upSlope, double downSlope){ 
		function = functions.Triangle;
		maxVal = value;
		firstTime = time;
		upSlope = Math.abs(upSlope);
		downSlope = -Math.abs(downSlope); 
	}
	
	public void setTrapezoid(int value, int firstTime, int lastTime, double slope){
		function = functions.Triangle;
		maxVal = value;
		this.firstTime = firstTime;
		this.lastTime = lastTime;
		upSlope = Math.abs(slope);
		downSlope = -upSlope;  // not sure if you can do negatives like this ??
	}
	
	public void setTrapezoid(int value, int firstTime, int lastTime, double upSlope, double downSlope){ 
		function = functions.Triangle;
		maxVal = value;
		this.firstTime = firstTime;
		this.lastTime = lastTime;
		upSlope = Math.abs(upSlope);
		downSlope = -Math.abs(downSlope); 
	}
	
	public int getValuation(int time) {
		switch (function) {
		case Rectangle:
			if (firstTime <= time || time <= lastTime) {
				return maxVal;
			}
			break;
		case SinglePoint:
			if (time == firstTime) {
				return maxVal;
			}
			break;
		case Trapezoid:
			if (firstTime <= time || time <= lastTime) {
				return maxVal;
			} else if (time < firstTime) {
				double tDiff = (double)(firstTime - time);
				int valuation = (int)(maxVal - upSlope*tDiff);
				return (valuation <0)? 0 : valuation;
			} else {
				double tDiff = (double)(time-lastTime);
				int valuation = (int)(maxVal + downSlope*tDiff);
				return (valuation <0)? 0 : valuation;
			}
		case Triangle:
			if (firstTime == time) {
				return maxVal;
			} else if (time < firstTime) {
				double tDiff = (double)(firstTime - time);
				int valuation = (int)(maxVal - upSlope*tDiff);
				return (valuation <0)? 0 : valuation;
			} else {
				double tDiff = (double)(time-lastTime);
				int valuation = (int)(maxVal + downSlope*tDiff);
				return (valuation <0)? 0 : valuation;
			}
		default:
			break;		
		}
		
		
		return 0;
	}
	
	@Override
	public String toString() {
		switch (function) {
		case Rectangle:
			return "Valuation [shape=" + function + ", maxVal=" + maxVal + ", firstTime=" + firstTime + ", lastTime="
			+ lastTime + "]";
			
		case SinglePoint:
			return "Valuation [shape=" + function + ", maxVal=" + maxVal + ", targetTime=" + firstTime + "]";
			
		case Trapezoid:
			return "Valuation [shape=" + function + ", maxVal=" + maxVal + ", firstTime=" + firstTime + ", lastTime="
			+ lastTime + ", upSlope=" + upSlope + ", downSlope=" + downSlope + "]";
			
		case Triangle:
			return "Valuation [shape=" + function + ", maxVal=" + maxVal + ", targetTime=" + firstTime + ", upSlope=" 
		+ upSlope + ", downSlope=" + downSlope + "]";
			
		default:
			return "no Valuation function specified";
		}
	}

	/**
	 * @return the function shape of valuation function 
	 */
	public functions getFunction() {
		return function;
	}

	/**
	 * @return the maxVal
	 */
	public int getMaxVal() {
		return maxVal;
	}

	/**
	 * @param maxVal the maxVal to set
	 */
	public void setMaxVal(int maxVal) {
		this.maxVal = maxVal;
	}

	/**
	 * @return the firstTime
	 */
	public int getFirstTime() {
		return firstTime;
	}

	/**
	 * @param firstTime the firstTime to set
	 */
	public void setFirstTime(int firstTime) {
		this.firstTime = firstTime;
	}

	/**
	 * @return the lastTime
	 */
	public int getLastTime() {
		return lastTime;
	}

	/**
	 * @param lastTime the lastTime to set
	 */
	public void setLastTime(int lastTime) {
		this.lastTime = lastTime;
	}

	/**
	 * @return the upSlope
	 */
	public double getUpSlope() {
		return upSlope;
	}

	/**
	 * @param upSlope the upSlope to set
	 */
	public void setUpSlope(double upSlope) {
		this.upSlope = upSlope;
	}

	/**
	 * @return the downSlope
	 */
	public double getDownSlope() {
		return downSlope;
	}

	/**
	 * @param downSlope the downSlope to set
	 */
	public void setDownSlope(double downSlope) {
		this.downSlope = downSlope;
	}	
	
}
