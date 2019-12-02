package DAO;


public class StuDAOException extends Exception  {

	private static final long serialVersionUID = 100213502L;

	public StuDAOException( ) {
	}

	public StuDAOException(String arg) {
		super(arg);
	}

	public StuDAOException(Throwable arg) {
		super(arg);
	}

	public StuDAOException(String arg, Throwable arg1) {
		super(arg, arg1);
	}
}
