package banking.common.application;

public class EntityNotFoundResultException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2430714498277113824L;
	
	public EntityNotFoundResultException(String message, Throwable ex) {
		super(message, ex);
	}

}
