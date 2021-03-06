package worms.model;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class of entities being able to move along a ballistic trajectory
 * and having a mass.
 * 
 * @invar	The direction of the worm is a valid direction.
 * 			| isValidDirection(getDirection())
 */
public abstract class MassiveEntity extends Entity {
	/**
	 * The gravitational acceleration on earth, in m/s².
	 */
	protected static final double GRAVITATIONAL_ACCELERATION = 9.80665;

	/**
	 * Returns the constant density of this entity, in kg/m³.
	 * @note	This method should be declared 'static' (Class-method:
	 *			Density is a property of each Subclass itself, not of instances
	 *			of a Subclass) Unfortunately, Java does not support inheritance of static methods.
	 */
	@Immutable @Raw @Basic
	protected abstract int getDensity();
	
	/**
	 * Returns the mass of this entity, in kg.
	 * 
	 * @return	The mass of this worm (in kilograms),
	 * 			assuming the worm has a spherical body with a 
	 * 			homogeneous density of getDensity():
	 * 			m = ρ*4/3*π*r³
	 * 			| fuzzyEquals(result, this.getDensity()*4.0/3*PI*pow(this.getRadius(), 3))
	 */
	@Raw
	protected double getMass(){
		return this.getDensity()*4.0/3*PI*pow(this.getRadius(), 3);
	}
	
	/**
	 * Returns the direction of this entity (in radians).
	 */
	@Basic @Raw
	public double getDirection(){
		return direction;
	}
	
	/**
	 * Checks whether the given direction is a valid direction.
	 * 
	 * @param direction The direction to check
	 * @return 	Whether or not direction is a valid number between 0 and 2*PI
	 * 			| result == (!double.isNaN(direction) && 0 <= direction && direction < 2*PI)
	 */
	public static boolean isValidDirection(double direction){
		if(Double.isNaN(direction))
			return false;
		return 0 <= direction && direction < 2*PI;
	}
	
	/**
	 * Sets the direction of this entity to the given direction.
	 * 
	 * @param direction
	 * 			The new direction of the entity (in radians).
	 * @pre 	The given direction is a valid direction.
	 * 			| isValidDirection(direction)
	 * @post 	The new direction of this entity equals the given direction.
	 * 			| new.getDirection() == direction
	 */
	@Raw @Model
	protected void setDirection(double direction){
		assert isValidDirection(direction);
		this.direction = direction;
	}
	private double direction;
	
	/**
	 * Makes this entity move along a ballistic trajectory.
	 * 
	 * @param timeStep
	 * 				An elementary time interval during which it may be assumed that the worm will not completely move through a piece of impassable terrain.
	 * @post		The new x- and y-coordinate of this entity will equal the x- and y-coordinates as 
	 * 				calculated by getJumpStemp() after the jump is completed
	 * 				| new.getXCoordinate() == getJumpStep(getJumpTime()).getX()
	 * 				| &&   new.getYCoordinate() == getJumpStep(getJumpTime()).getY()
	 * @effect		After the position are set, everything that needs to be done after the jump will be done
	 * 				| handleAfterJump()
	 * @throws IllegalArgumentException
	 * 				Thrown when the timestep is negative or not a valid number
	 * 				| timestep < 0 || Double.isNaN(timeStep)
	 * @throws IllegalStateException
	 * 				Thrown when this entity can't jump from his current position.
	 * 				| !canJump()
	 * 				When the end of the jump would be on impassable terrain and wouldn't be removed.
	 * 				| !afterJumpRemove(getJumpStep(getJumpTime(timeStep))) && !getWorld().isPassablePosition(getJumpStep(getJumpTime(timeStep)), getRadius())
	 */
	public void jump(double timeStep) throws IllegalArgumentException,IllegalStateException{
		if(timeStep < 0 || Double.isNaN(timeStep))
			throw new IllegalArgumentException();
		if(!canJump())
			throw new IllegalStateException();
		Position endPosition = getJumpStep(getJumpTime(timeStep));
		if(!afterJumpRemove(endPosition) && !getWorld().isPassablePosition(endPosition, getRadius()))
			throw new IllegalStateException();
		setPosition(endPosition);
		handleAfterJump();
	}
	
	/**
	 * Checks whether or not the entity should be removed from the world
	 * if pos is the location where he just landed from a jump.
	 * 
	 * @param pos	The location to check
	 * @return	True if the entity at position pos doesn't lie in the world anymore
	 * 			| if(!getWorld().isInsideWorldBoundaries(pos, getRadius())) then result == true
	 */
	public boolean afterJumpRemove(Position pos){
		if(!getWorld().isInsideWorldBoundaries(pos, getRadius()))
			return true;
		return false;
	}
	
	/**
	 * Handles everything that needs to be handled after a jump: removing entities, damaging entities, ...
	 * 
	 * @post	The hitpoints of every worm in this world are left untouched or are decreased.
	 * 			| for each worm in getWorld().getWorms(): (new worm).gitHitPoints() <= worm.getHitPoints()
	 * @effect	The entity will be removed from the world if it should be removed from the world.
	 * 			| if(afterJumpRemove(getPosition()) || new.afterJumpRemove(getPosition())) then getWorld().removeAsEntity(this);
	 * @post	Food rations may have been eaten.
	 * 			| for each food in getWorld().getFoods():
	 * 			|	(new getWorld()).hasAsFood(food) || (new food).isTerminated()
	 * @post	This MassiveEntity may have become even more bigger.
	 * 			| new.getRadius() >= getRadius()
	 */
	public void handleAfterJump(){
		if(afterJumpRemove(getPosition()))
			getWorld().removeAsEntity(this);
	}
	
	/**
	 * Calculates the force exerted by this entity (in Newton) in a potential jump from his current position.
	 * 
	 * @return	Result is non negative
	 * 			| result >= 0
	 */
	protected abstract double getJumpForce();
	
	/**
	 * Calculates the initial velocity of this entity (in m/s) in a potential jump from his current position.
	 * 
	 * @return	The (virtual) velocity of this entity (in m/s) equals the (virtual) force exerted by this entity divided by its mass and multiplied with 0.5 seconds.
	 * 			| fuzzyEquals(result, (getJumpForce()/getMass())*0.5);
	 */
	protected double getJumpVelocity(){
		return (getJumpForce()/getMass())*0.5;
	}
	
	/**
	 * Calculates the time this entity will take to complete a potential jump from his current position.
	 * 
	 * @param timeStep
	 * 			An elementary time interval during which it may be assumed that the worm will not completely move through a piece of impassable terrain.
	 * @return	If the entity can't jump, the jump time will equal 0
	 * 			| if(!canJump()) then result == 0
	 * @return	There is no non-negative t smaller than the result, that is a multiple of timeStep and where the position of the entity after a jump of t seconds would block the jump.
	 * 			| !(for some t >= 0: Double.compare(t,result)<0 && !fuzzyEquals(t,result,timeStep) && fuzzyEquals(t%timeStep,0) && blocksJump(getJumpStep(t)))
	 * @throws	IllegalArgumentException
	 * 			Thrown when the timestep is negative or not a valid number
	 * 			| timestep < 0 || Double.isNaN(timeStep)
	 */
	public double getJumpTime(double timeStep)throws IllegalArgumentException{
		if(timeStep < 0 || Double.isNaN(timeStep))
			throw new IllegalArgumentException();
		
		if(!canJump())
			return 0;
		
		double t = 0;
		while(true){
			if(blocksJump(getJumpStep(t)))
				return t;
			t += timeStep;
		}
	}
	
	/**
	 * Checks whether or not the given position would stop/block the (virtual) jump of this entity.
	 * 
	 * @param position	The position to check.
	 * @return	False if the position is not further than getRadius() away
	 * 			| if(position.squaredDistance(getPosition()) < getRadius()*getRadius()) then result == false
	 * @return	True if the position is not within the world boundaries.
	 * 			| if(!getWorld().isInsideWorldBoundaries(position, getRadius())) then result == true
	 * @return	True if the position is a contact location or impassable terrain for the given worm.
	 * 			| if(!getWorld().isPassablePosition(position, getRadius()*1.1)) then result == true
	 * @return	False if the position doesn't overlap with any entity.
	 * 			| if(!(for some entity in getWorld().getEntities(): entity.collidesWith(position,getRadius()))) then result == false
	 */
	public boolean blocksJump(Position position){
		if(position.squaredDistance(getPosition()) < getRadius()*getRadius())
			return false;
		if(!getWorld().isInsideWorldBoundaries(position, getRadius()))
			return true;
		if(!getWorld().isPassablePosition(position, getRadius()*1.1))
			return true;
		return false;
	}
	
	/**
	 * Calculates the x- and y-coordinate of this entity in a potential jump from his current position after
	 * the given period of time t.
	 * 
	 * @param t		The time after the jump (in seconds).
	 * @return		The position where the entity will be after t seconds.
	 * 				In the current version this method uses a simplified model of terrestrial physics and considers uniform gravity with neither drag nor wind. 
	 * 				In the future this method may involve further trajectory parameters. Clients should NOT consider this postcondition as final but rather as a guideline.
	 * 				|	fuzzyEquals(result.getX(), (getXCoordinate()+(getJumpVelocity()*cos(getDirection())*t))) &&
	 * 				|	fuzzyEquals(result.getY(), (getYCoordinate()+(getJumpVelocity()*sin(getDirection())*t)-(GRAVITATIONAL_ACCELERATION/2*pow(t,2))))
	 * @throws IllegalArgumentException
	 * 				Thrown when t is not a valid number or is less than zero
	 * 				| Double.isNaN(t) || t < 0
	 * @note		This method will just return where the entity will be after t seconds IF he performs a jump. 
	 * 				This method will also return values if this entity can't jump, 
	 * 				or the values of the position where the entity would be after t seconds assuming the entity has not stopped jumping yet. 
	 * 				(if t > getJumpTime() it will just continue giving positions).
	 */
	public Position getJumpStep(double t) throws IllegalArgumentException{
		if(Double.isNaN(t) || t < 0)
			throw new IllegalArgumentException("Illegal timestep");
		
		double velocity = getJumpVelocity();
		double direction = getDirection();
		
		double newX = getXCoordinate()+(velocity*cos(direction)*t);
		double newY = getYCoordinate()+(velocity*sin(direction)*t)-(GRAVITATIONAL_ACCELERATION/2*pow(t,2));
		return new Position(newX,newY);
	}

	/**
	 * Checks whether or not this entity can jump.
	 * 
	 * @return	False if this entity is terminated.
	 * 			| if(isTerminated()) then result == false
	 * @return	False if this entity is on impassable terrain
	 * 			| if(!getWorld().isPassablePosition(getPosition(), getRadius())) then result == false
	 */
	public boolean canJump(){
		if(isTerminated())
			return false;
		if(!getWorld().isPassablePosition(getPosition(), getRadius()))
			return false;
		return true;
	}
}
