// valuation is a function that given a time, it gives a payout

public class Valuation {
	private functions function;
	private int maxVal;
	
	private int firstMaxPayTime; // the first time at which you can get maxVal payment
	private int lastMaxPayTime; // the lastTime at which you can get maxVal payment
	
	private int beginPayTime; // the first time at which you get any payment
	private int endPayTime; // the last time at which you get any payment 
	// for use in triangular and trapezoidal 
	private double upSlope; 
	private double downSlope;
	
	public Valuation () {
		function = functions.SinglePoint;
		maxVal = 0;
		firstMaxPayTime = -1;
	}
	
	public Valuation(int time){
		function = functions.SinglePoint;
		maxVal = 1;
		firstMaxPayTime = time;
		lastMaxPayTime = time;
		beginPayTime = time;
		endPayTime = time;
	}

	public Valuation(int value, int time){
		function = functions.SinglePoint;
		maxVal = value;
		firstMaxPayTime = time;
		lastMaxPayTime = time;
		beginPayTime = time;
		endPayTime = time;
	}
	
	public void setSinglePoint(int value, int time){
		function = functions.SinglePoint;
		maxVal = value;
		firstMaxPayTime = time;
		beginPayTime = time;
		endPayTime = time;
	}

	public void setRectangle(int value, int firstTime, int lastTime) {
		function = functions.Rectangle;
		maxVal = value;
		this.firstMaxPayTime = firstTime;
		this.lastMaxPayTime = lastTime;
		beginPayTime = firstTime;
		endPayTime = lastTime;	
	}
	
	public void setTriangle(int value, int time, double slope){
		function = functions.Triangle;
		maxVal = value;
		firstMaxPayTime = time;
		upSlope = Math.abs(slope);
		downSlope = -upSlope;  // not sure if you can do negatives like this ??
		beginPayTime = time;
		while (getValuation(beginPayTime) > 0) {
			beginPayTime--;
		}
		beginPayTime = (beginPayTime < 0) ? 0 : beginPayTime; 
		endPayTime = time;
		while (getValuation(endPayTime) > 0) {
			endPayTime++;
		}
	}
	
	public void setTriangle(int value, int time, double upSlope, double downSlope){ 
		function = functions.Triangle;
		maxVal = value;
		firstMaxPayTime = time;
		upSlope = Math.abs(upSlope);
		downSlope = -Math.abs(downSlope); 
		beginPayTime = time;
		while (getValuation(beginPayTime) > 0) {
			beginPayTime--;
		}
		beginPayTime = (beginPayTime < 0) ? 0 : beginPayTime; 
		endPayTime = time;
		while (getValuation(endPayTime) > 0) {
			endPayTime++;
		}
	}
	
	public void setTrapezoid(int value, int firstTime, int lastTime, double slope){
		function = functions.Triangle;
		maxVal = value;
		this.firstMaxPayTime = firstTime;
		this.lastMaxPayTime = lastTime;
		upSlope = Math.abs(slope);
		downSlope = -upSlope;  // not sure if you can do negatives like this ??
		beginPayTime = firstTime;
		while (getValuation(beginPayTime) > 0) {
			beginPayTime--;
		}
		beginPayTime = (beginPayTime < 0) ? 0 : beginPayTime; 
		endPayTime = lastTime;
		while (getValuation(endPayTime) > 0) {
			endPayTime++;
		}
	}
	
	public void setTrapezoid(int value, int firstTime, int lastTime, double upSlope, double downSlope){ 
		function = functions.Triangle;
		maxVal = value;
		this.firstMaxPayTime = firstTime;
		this.lastMaxPayTime = lastTime;
		upSlope = Math.abs(upSlope);
		downSlope = -Math.abs(downSlope); 
		beginPayTime = firstTime;
		while (getValuation(beginPayTime) > 0) {
			beginPayTime--;
		}
		beginPayTime = (beginPayTime < 0) ? 0 : beginPayTime; 
		endPayTime = lastTime;
		while (getValuation(endPayTime) > 0) {
			endPayTime++;
		}
	}
	
	public int getValuation(int time) {
		switch (function) {
		case Rectangle:
			if (firstMaxPayTime <= time || time <= lastMaxPayTime) {
				return maxVal;
			}
			break;
		case SinglePoint:
			if (time == firstMaxPayTime) {
				return maxVal;
			}
			break;
		case Trapezoid:
			if (firstMaxPayTime <= time || time <= lastMaxPayTime) {
				return maxVal;
			} else if (time < firstMaxPayTime) {
				double tDiff = (double)(firstMaxPayTime - time);
				int valuation = (int)(maxVal - upSlope*tDiff);
				return (valuation <0)? 0 : valuation;
			} else {
				double tDiff = (double)(time-lastMaxPayTime);
				int valuation = (int)(maxVal + downSlope*tDiff);
				return (valuation <0)? 0 : valuation;
			}
		case Triangle:
			if (firstMaxPayTime == time) {
				return maxVal;
			} else if (time < firstMaxPayTime) {
				double tDiff = (double)(firstMaxPayTime - time);
				int valuation = (int)(maxVal - upSlope*tDiff);
				return (valuation <0)? 0 : valuation;
			} else {
				double tDiff = (double)(time-lastMaxPayTime);
				int valuation = (int)(maxVal + downSlope*tDiff);
				return (valuation <0)? 0 : valuation;
			}
		default:
			break;		
		}
		
		return 0;
	}
	
	public int getBeginPayTime() {
		return beginPayTime;
	}

	public int getEndPayTime() {
		return endPayTime;
	}
	
	
	@Override
	public String toString() {
		switch (function) {
		case Rectangle:
			return "Valuation [shape=" + function + ", maxVal=" + maxVal + ", firstTime=" + firstMaxPayTime + ", lastTime="
			+ lastMaxPayTime + "]";
			
		case SinglePoint:
			return "Valuation [shape=" + function + ", maxVal=" + maxVal + ", targetTime=" + firstMaxPayTime + "]";
			
		case Trapezoid:
			return "Valuation [shape=" + function + ", maxVal=" + maxVal + ", firstTime=" + firstMaxPayTime + ", lastTime="
			+ lastMaxPayTime + ", upSlope=" + upSlope + ", downSlope=" + downSlope + "]";
			
		case Triangle:
			return "Valuation [shape=" + function + ", maxVal=" + maxVal + ", targetTime=" + firstMaxPayTime + ", upSlope=" 
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
		return firstMaxPayTime;
	}

	/**
	 * @param firstTime the firstTime to set
	 */
	public void setFirstTime(int firstTime) {
		this.firstMaxPayTime = firstTime;
	}

	/**
	 * @return the lastTime
	 */
	public int getLastTime() {
		return lastMaxPayTime;
	}

	/**
	 * @param lastTime the lastTime to set
	 */
	public void setLastTime(int lastTime) {
		this.lastMaxPayTime = lastTime;
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
