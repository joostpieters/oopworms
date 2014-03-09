package worms.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class of worms with position, direction, radius and a name.
 * 
 * @author Jens Claes
 * @author Tomas Fiers
 * @version 1.0
 *
 * @invar 	The position of the worm is a valid position. 
 * 		 	| isValidPosition(getXCoordinate(),getYCoordinate())
 * @invar	The direction of the worm is a valid direction.
 * 			| isValidDirection(getDirection())
 * @invar	The name of the worm is a valid name.
 * 			| isValidName(getName())
 * @invar	The radius of the worm is a valid radius.
 * 			| canHaveAsRadius(getRadius())
 */
public class Worm {

	/**
	 * The constant density of a worm, in kg/m³.
	 */
	public static final int DENSITY = 1062;
	
	/**
	 * Creates a new worm that is positioned at the given location, looks in the given direction, has the given radius and the given name.
	 * 
	 * @param x
	 * 			The x-coordinate of the position of the new worm (in metres)
	 * @param y
	 * 			The y-coordinate of the position of the new worm (in metres)
	 * @param direction
	 * 			The direction of the new worm (in radians)
	 * @param radius
	 * 			The radius of the new worm (in metres)
	 * @param name
	 * 			The name of the new worm
	 * @pre		The given direction is a valid direction.
	 * 			| isValidDirection(direction)
	 * @post 	The x-coordinate of the position of the new worm equals the given x-coordinate.
	 * 			| new.getXCoordinate() == x
	 * @post 	The y-coordinate of the position of the new worm equals the given y-coordinate.
	 * 			| new.getYCoordinate() == y
	 * @post	The direction of the new worm equals the given direction.
	 * 			| new.getDirection() == direction
	 * @post	The name of the new worm equals the given name
	 * 			| new.getName() == name
	 * @post	The radius of the new worm equals the given radius.
	 * 			| new.getRadius() == radius
	 * @throws IllegalArgumentException 
	 * 			When the given position is not a valid position.
	 * 			| !isValidPosition(x,y)
	 * @throws IllegalArgumentException
	 * 			When the given name is not a valid name.
	 * 			| !isValidName(name)
	 * @throws IllegalArgumentException 
	 * 			When the given radius is not a valid radius.
	 * 			| !canHaveAsRadius(radius)
>>>>>>> master
	 */
	@Raw
	public Worm(double x, double y, double direction, double radius, String name) throws IllegalArgumentException{
		//TODO complete.
		//TODO complete documentation. (throwables, pre-condities, ...)
		
		//Next check is implicit in setXPosition() and setYPosition()
		//if(!isValidPosition(x,y))
		//		throw new IllegalArgumentException("Illegal Position");

		//Implicit in setDirection:
		//assert isValidDirection(direction);
		

		//Implicit in setName:
		//if(!isValidName(name))
		//		throw new IllegalArgumentException("Illegal Name");

		//Implicit in setRadius:
		//if(!canHaveAsRadius(radius))
		//		throw new IllegalArgumentException("Illegal radius");


		setXCoordinate(x);
		setYCoordinate(y);

		setDirection(direction);
		

		setName(name);

		setRadius(radius);
	}
	
	/**
	 * Checks whether a position (with x- and y-coordinates) is a valid position.
	 * 
	 * @param x The x-coordinate of the location to check.
	 * @param y The y-coordinate of the location to check.
	 * @effect 	Check whether the x-coordinate and y-coordinate are both valid.
	 * 			| isValidXPCoordinate(x) && isValidYCoordinate(y)
	 */
	public static boolean isValidPosition(double x, double y){
		return isValidXCoordinate(x) && isValidYCoordinate(y);
	}
	
	/**
	 * Returns the x-coordinate of the current location of this worm (in metres).
	 */
	@Basic @Raw
	public double getXCoordinate(){
		return this.xCoordinate;
	}
	
	/**
	 * Checks whether the given x-coordinate is a valid x-coordinate.
	 * 
	 * @param x
	 * 			The x-coordinate to check.
	 * @return 	Whether or not x is a valid number.
	 * 			| result == !Double.isNan(x)
	 */
	public static boolean isValidXCoordinate(double x){
		return !Double.isNaN(x);
	}
	
	/**
	 * Sets the x-coordinate of the location of this worm.
	 * 
	 * @param x 
	 * 			The new x-coordinate of this worm (in metres).
	 * @post	The x-coordinate of this worm equals the given x-coordinate
	 * 		 	| new.getXCoordinate() == x
	 * @throws IllegalArgumentException 
	 * 			The given position is not a valid x-coordinate.
	 * 			| !isValidXCoordinate(x)
	 */
	@Raw
	private void setXCoordinate(double x) throws IllegalArgumentException{
		if (!isValidXCoordinate(x))
			throw new IllegalArgumentException("The given x-coordinate is not a valid x-coordinate.");
		this.xCoordinate = x;
	}
	private double xCoordinate;
	
	/**
	 * Returns the y-coordinate of the current location of this worm (in metres).
	 */
	@Basic @Raw
	public double getYCoordinate(){
		return this.yCoordinate;
	}
	
	/**
	 * Checks whether the given y-coordinate is a valid y-coordinate.
	 * 
	 * @param y
	 * 			The y-coordinate to check.
	 * @return 	Whether or not y is a valid number.
	 * 			| !Double.isNan(y)
	 */
	public static boolean isValidYCoordinate(double y){
		return !Double.isNaN(y);
	}
	
	/**
	 * Sets the y-coordinate of the location of this worm.
	 * 
	 * @param y
	 * 			The new y-coordinate of the location of this worm (in metres).
	 * @post	The y-coordinate of this worm equals the given y-coordinate
	 * 		 	| new.getYCoordinate() == y
	 * @throws IllegalArgumentException
	 * 			The given position is not a valid y-coordinate.
	 * 			| !isValidYCoordinate(y)
	 */
	@Raw
	private void setYCoordinate(double y) throws IllegalArgumentException{
		if (!isValidYCoordinate(y))
			throw new IllegalArgumentException("The given y-coordinate is not a valid y-coordinate.");
		this.yCoordinate = y;
	}
	private double yCoordinate;
	
	/**
	 * Returns the direction of this worm (in radians).
	 */
	@Basic @Raw
	public double getDirection(){
		return direction;
	}
	
	/**
	 * Checks whether the given direction is a valid direction.
	 * 
	 * @param direction The direction to check
	 * @return 	Whether or not direction is a valid number between 0 and 2*Math.PI
	 * 			| result == (!double.isNaN(direction) && 0<= direction && direction < 2*Math.PI)
	 */
	public static boolean isValidDirection(double direction){
		if(Double.isNaN(direction))
			return false;
		return 0 <= direction && direction < 2*Math.PI;
	}
	
	/**
	 * Sets the direction of this worm to the given direction.
	 * 
	 * @param direction
	 * 			The new direction of the worm (in radians).
	 * @pre 	The given direction is a valid direction.
	 * 			| isValidDirection(direction)
	 * @post 	The new direction of this worm equals the given direction.
	 * 			| new.getDirection() == direction
	 */
	@Raw
	private void setDirection(double direction){
		assert isValidDirection(direction);
		this.direction = direction;
	}
	private double direction;
	
	/**
	 * Returns the name of this worm.
	 */
	@Basic @Raw
	public String getName(){
		return this.name;
	}
	
	/**
	 * Checks whether or not the given name is a valid name for a worm.
	 * 
	 * @param name The name to check.
	 * @return	Whether or not the name is at least 2 characters long, starts with an uppercase letter and only contains valid characters.
	 * 			|	result == (name.length() >= 2 && Character.isUpperCase(name.charAt(0)) 
	 * 			|				&& for all Character c in name:
	 * 			|						isValidCharacterForName(c))
	 */
	public static boolean isValidName(String name){
		if(name.length() < 2)
			return false;
		if(!Character.isUpperCase(name.charAt(0)))
			return false;
		for(Character c : name.toCharArray()){
			if(!isValidCharacterForName(c))
				return false;
		}
		return true;
	}
	
	/**
	 * Checks whether or not the given character is a valid character to be used in a name for a worm.
	 * 
	 * <p>In the current version, the valid characters consist of (latin) letters (both uppercase and lowercase), quotes (both single and double) and spaces.
	 * In short the list looks like this: [A-Za-z'" ].
	 * 
	 * @param c The character to check.
	 * @return	Whether or not the character is a (latin) letter, quote or space.
	 * 			| result == (c == 32 || c == 34 || c == 39 || (65 <= c && c <= 90) || (97 <= c && c <= 122))
	 */
	public static boolean isValidCharacterForName(char c){
		//Space is allowed.
		if(c == 32)
			return true;
		//Single and double quotes are allowed.
		if(c == 34 || c == 39)
			return true;
		
		//Uppercase characters are allowed.
		if(65 <= c && c <= 90)
			return true;
		//Lowercase characters are allowed.
		if(97 <= c && c <= 122)
			return true;
		
		//All the rest isn't allowed (at the moment).
		return false;
	}
	
	/**
	 * Sets the name for this worm.
	 * 
	 * @param name The name to set
	 * @throws IllegalArgumentException 
	 * 			When the given name is not a valid name.
	 * 			| !isValidName(name)
	 * @post 	The name for this worm equals the given name.
	 * 			| new.getName() == name
	 */
	@Raw
	public void setName(String name) throws IllegalArgumentException{
		if(!isValidName(name))
			throw new IllegalArgumentException("Illegal Name");
		this.name = name;
	}
	private String name;

	/**
	 * Returns the radius of this worm (in metres).
	 */
	@Basic @Raw
	public double getRadius(){
		return this.radius;
	}
	
	/**
	 * Returns a lower bound on the radius of this worm.
	 * 
	 * @return A strictly positive lower bound on the radius of this worm.
	 * 		   | result > 0
	 */
	@Raw
	public double getRadiusLowerBound(){
		return 0.25;
	}
	
	/**
	 * Checks whether the given radius is a valid radius for this worm.
	 * 
	 * @param radius
	 * 			The radius to check.
	 * @return	Whether or not radius is a valid number, finite 
	 * 			and at least as big as the lower bound on radiuses 
	 * 			of this worm.
	 * 			| result == (!Double.isNaN(radius))
	 *			|			&& (radius >= this.getRadiusLowerBound()) 
     *			|			&& (radius < Double.POSITIVE_INFINITY)
	 */
	@Raw
	public boolean canHaveAsRadius(double radius){
		return  (!Double.isNaN(radius))
				&& (radius >= this.getRadiusLowerBound()) 
				&& (radius < Double.POSITIVE_INFINITY);
	}
	
	/**
	 * Sets the radius of this worm.
	 * 
	 * @param radius
	 * 			The new radius of this worm (in metres).
	 * @post	The radius of this worm is the given radius.
	 * 			| new.getRadius() == radius
	 * @throws	IllegalArgumentException
	 * 			The given radius is not a valid radius for this worm.
	 * 			| !this.canHaveAsRadius(radius)
	 */
	@Raw
	public void setRadius(double radius) throws IllegalArgumentException{
		if (!this.canHaveAsRadius(radius))
			throw new IllegalArgumentException("The given radius is not a valid radius for this worm");
		this.radius = radius;
	}
	private double radius;
	
	/**
	 * Returns the mass of this worm.
	 * 
	 * @return	The mass of this worm (in kilograms),
	 * 			assuming the worm has a spherical body with a 
	 * 			homogeneous density of Worm.DENSITY:
	 * 			m = ρ*4/3*π*r³
	 * 			| result == Worm.DENSITY*4.0/3*Math.PI*Math.pow(this.getRadius(), 3)
	 */
	public double getMass(){
		return Worm.DENSITY*4.0/3*Math.PI*Math.pow(this.getRadius(), 3);
	}
	
	public int getMaxActionPoints(){
		return (int) Math.round(this.getMass());
	}
	
	public int getActionPoints(){
		return this.actionPoints;
	}
	private int actionPoints;
	
	private void decreaseActionPoints(int amount){
		if (amount < 0)
			this.increaseActionPoints(-amount);
		else if (amount > this.getMaxActionPoints())
			this.actionPoints = 0;
		else
			this.actionPoints = this.getActionPoints() - amount;
	}
	
	private void increaseActionPoints(int amount){
		if (amount < 0)
			this.decreaseActionPoints(-amount);
		else if (this.getActionPoints() + amount > this.getMaxActionPoints())
			this.actionPoints = this.getMaxActionPoints();
		else
			this.actionPoints = this.getActionPoints() + amount;
	}
	
	private void _decreaseActionPoints(int amount) {
		this._increaseActionPoints(-amount);
	}

	private void _increaseActionPoints(int amount) {
		if (amount < -this.getActionPoints())
			this.actionPoints = 0;
		else if (amount > this.getMaxActionPoints())
			this.actionPoints = this.getMaxActionPoints();
		else
			this.actionPoints = this.getActionPoints() + amount;
	}
}
